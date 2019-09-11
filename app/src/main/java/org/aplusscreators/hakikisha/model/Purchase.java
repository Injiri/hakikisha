package org.aplusscreators.hakikisha.model;

public class Purchase {

    private String uuid;
    private String name;
    private String platform;
    private double cost;
    private int order_id;
    private String sellerUuid;
    private String sellerPhone;
    private String sellerEmail;
    private String description;
    private String deliveryAddress;
    private String quantity;
    private String buyerUuid;
    private String status;
    private String paymentMethod;
    private String deliveryOption;

    public Purchase() {
    }

    public Purchase(String uuid, String name, String platform, double cost, int order_id, String sellerUuid, String sellerPhone, String sellerEmail, String description, String deliveryAddress, String quantity, String buyerUuid, String status, String paymentMethod) {
        this.uuid = uuid;
        this.name = name;
        this.platform = platform;
        this.cost = cost;
        this.order_id = order_id;
        this.sellerUuid = sellerUuid;
        this.sellerPhone = sellerPhone;
        this.sellerEmail = sellerEmail;
        this.description = description;
        this.deliveryAddress = deliveryAddress;
        this.quantity = quantity;
        this.buyerUuid = buyerUuid;
        this.status = status;
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBuyerUuid() {
        return buyerUuid;
    }

    public void setBuyerUuid(String buyerUuid) {
        this.buyerUuid = buyerUuid;
    }

    public String getSellerPhone() {
        return sellerPhone;
    }

    public void setSellerPhone(String sellerPhone) {
        this.sellerPhone = sellerPhone;
    }

    public String getSellerEmail() {
        return sellerEmail;
    }

    public void setSellerEmail(String sellerEmail) {
        this.sellerEmail = sellerEmail;
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

    public void setDeliveryOption(String deliveryOption) {
        this.deliveryOption = deliveryOption;
    }

    public String getDeliveryOption() {
        return deliveryOption;
    }
}
