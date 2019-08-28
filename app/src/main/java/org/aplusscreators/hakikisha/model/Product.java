package org.aplusscreators.hakikisha.model;

public class Product {

    private String uuid;
    private String name;
    private String category;
    private double cost;
    private String qty;
    private String photo_uri;
    private int fragile_product;
    private int perishable;
    private int second_hand;
    private String description;
    private String attachment_uri;

    public Product() {
    }

    public Product(String uuid, String category, double cost, String qty, String photo_uri, int fragile_product, int perishable, int second_hand, String description, String attachment_uri) {
        this.uuid = uuid;
        this.category = category;
        this.cost = cost;
        this.qty = qty;
        this.photo_uri = photo_uri;
        this.fragile_product = fragile_product;
        this.perishable = perishable;
        this.second_hand = second_hand;
        this.description = description;
        this.attachment_uri = attachment_uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getPhoto_uri() {
        return photo_uri;
    }

    public void setPhoto_uri(String photo_uri) {
        this.photo_uri = photo_uri;
    }

    public int getFragile_product() {
        return fragile_product;
    }

    public void setFragile_product(int fragile_product) {
        this.fragile_product = fragile_product;
    }

    public int getPerishable() {
        return perishable;
    }

    public void setPerishable(int perishable) {
        this.perishable = perishable;
    }

    public int getSecond_hand() {
        return second_hand;
    }

    public void setSecond_hand(int second_hand) {
        this.second_hand = second_hand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAttachment_uri() {
        return attachment_uri;
    }

    public void setAttachment_uri(String attachment_uri) {
        this.attachment_uri = attachment_uri;
    }
}
