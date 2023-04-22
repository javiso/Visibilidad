package com.visibilidad.business;

import com.visibilidad.model.Product;
import com.visibilidad.model.Size;
import com.visibilidad.model.Stock;
import com.visibilidad.util.CsvHelp;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

public class Visibility {

    public static final String PRODUCT = "./resource/product.csv";
    public static final String SIZE = "./resource/size.csv";
    public static final String STOCK = "./resource/stock.csv";

    private static final List<Product> products;
    private static final Map<Integer, Integer> mapStocks;
    private static final List<Size> sizes;

    static {
        products = CsvHelp.loadProductFile(PRODUCT);
        sizes = CsvHelp.loadSizeFile(SIZE);
        mapStocks = CsvHelp.loadStockFile(STOCK).stream().collect(Collectors.toMap(Stock::getSizeId, Stock::getQuantity));
    }

    public List<Product> showProductsToSell() {
        ArrayList<Product> productsToBeShown = new ArrayList<>();
        Map<Integer, List<Size>> mapSize = sizes.stream().collect(groupingBy(Size::getProductId));

        mapSize.forEach((productId, listSize) -> {
            Size sizeFirst = listSize.get(0);

            if(sizeFirst.isSpecial()) {
                if(isAvailabilityProductsBySpecialClothing(listSize)) {
                    productsToBeShown.add(findProduct(productId));
                }
            } else if(sizeFirst.isBackSoon() || sizes.stream().allMatch(size -> mapStocks.get(size.getId()) != 0)){
                productsToBeShown.add(findProduct(productId));
            }
        });

        return productsToBeShown.stream().sorted(Comparator.comparingInt(Product::getSequence)).collect(Collectors.toList());
    }

    private boolean isAvailabilityProductsBySpecialClothing(final List<Size> sizes) {
        boolean previousSpecial = false;

        for(Size size : sizes) {
            if(size.isBackSoon() || mapStocks.get(size.getId()) != 0) {
                if(previousSpecial) {
                    if(!size.isSpecial()) {
                        return true;
                    }
                } else {
                    previousSpecial = size.isSpecial();
                }
            }
        }

        return false;
    }

    private Product findProduct(final int productId) {
        return products.stream().filter(product -> product.getId() == productId).findFirst().orElseThrow();
    }
}