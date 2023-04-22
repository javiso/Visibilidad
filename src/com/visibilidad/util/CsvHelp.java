package com.visibilidad.util;

import com.visibilidad.model.Product;
import com.visibilidad.model.Size;
import com.visibilidad.model.Stock;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CsvHelp {

    private static final String SEPARATOR = ", ";

    private static <A> List<A> loadCsvFile(final Function<String[], A> function, final String path) {
        List<A> listResult = new ArrayList<>();

        try(BufferedReader bufferRead = new BufferedReader(new FileReader(path))) {
            String line = bufferRead.readLine();

            while (line != null) {
                String[] fields = line.split(SEPARATOR);
                listResult.add(function.apply(fields));
                line = bufferRead.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return listResult;
    }

    public static List<Product> loadProductFile(final String path) {
        Function<String[], Product> productFunction = productLine -> new Product(Integer.parseInt(productLine[0]), Integer.parseInt(productLine[1]));
        return CsvHelp.loadCsvFile(productFunction, path);
    }

    public static List<Size> loadSizeFile(final String path) {
        Function<String[], Size> sizeFunction = sizeLine -> new Size(Integer.parseInt(sizeLine[0]), Integer.parseInt(sizeLine[1]), Boolean.parseBoolean(sizeLine[2]), Boolean.parseBoolean(sizeLine[3]));
        Comparator<Size> comparatorSize = Comparator.comparingInt(Size::getProductId)
                .thenComparing(Size::isSpecial)
                .thenComparing(Size::isBackSoon)
                .reversed();
        return CsvHelp.loadCsvFile(sizeFunction, path).stream().sorted(comparatorSize).collect(Collectors.toList());
    }

    public static List<Stock> loadStockFile(final String path) {
        Function<String[], Stock> stockFunction = stockLine -> new Stock(Integer.parseInt(stockLine[0]), Integer.parseInt(stockLine[1]));
        return CsvHelp.loadCsvFile(stockFunction, path);
    }
}