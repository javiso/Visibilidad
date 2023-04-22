package com.visibilidad;

import com.visibilidad.business.Visibility;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VisibilityTest {

    @Test
    @DisplayName("It should return 3 products ordered by the sequence field")
    void availabilityProductTest() {
        Visibility visibility = new Visibility();
        var visibleProducts = visibility.showProductsToSell();

        assertNotNull(visibleProducts);
        assertEquals(3, visibleProducts.size());
        assertEquals(5, visibleProducts.get(0).getId());
        assertEquals(1, visibleProducts.get(1).getId());
        assertEquals(3, visibleProducts.get(2).getId());
    }
}