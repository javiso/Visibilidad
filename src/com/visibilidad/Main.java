package com.visibilidad;

public class Main {

    public static void main(String[] args) {
        Visibility visibility = new Visibility();
        visibility.showProductsToSell().forEach(p -> System.out.println(p.getId()));
    }
}