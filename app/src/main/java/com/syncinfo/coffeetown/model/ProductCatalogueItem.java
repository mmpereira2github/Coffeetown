package com.syncinfo.coffeetown.model;

/**
 * Created by mmartins on 2017-12-30.
 */

public class ProductCatalogueItem {
    private ProductCatalogue catalogue;
    private int id;
    private Product product;
    private ProductCategory category;
    private double price;

    public ProductCatalogueItem(ProductCatalogue catalogue, int id, Product product, ProductCategory category, double price) {
        this.catalogue = catalogue;
        this.id = id;
        this.product = product;
        this.category = category;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ProductCatalogue getCatalogue() {
        return catalogue;
    }

    public void setCatalogue(ProductCatalogue catalogue) {
        this.catalogue = catalogue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }
}
