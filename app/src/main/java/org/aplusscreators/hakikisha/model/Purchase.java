package org.aplusscreators.hakikisha.model;

public class Purchase {

    private String uuid;
    private String name;
    private String platform;
    private double cost;
    private int order_id;
    private String sellerUuid;
    private String description;
    private String deliveryAddress;
    private String quantity;

    public Purchase() {
    }

    public Purchase(String uuid, String name, String platform, double cost, int order_id, String sellerUuid, String description, String deliveryAddress, String quantity) {
        this.uuid = uuid;
        this.name = name;
        this.platform = platform;
        this.cost = cost;
        this.order_id = order_id;
        this.sellerUuid = sellerUuid;
        this.description = description;
        this.deliveryAddress = deliveryAddress;
        this.quantity = quantity;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getSellerUuid() {
        return sellerUuid;
    }

    public void setSellerUuid(String sellerUuid) {
        this.sellerUuid = sellerUuid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
