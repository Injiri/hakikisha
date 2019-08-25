package org.aplusscreators.hakikisha.model;

public class Order {

    private String uuid;
    private int order_id;
    private String customer_uuid;
    private String seller_uuid;
    private String product_uuid;
    private int status;

    public Order(String uuid, int order_id, String customer_uuid, String seller_uuid, String product_uuid, int status) {
        this.uuid = uuid;
        this.order_id = order_id;
        this.customer_uuid = customer_uuid;
        this.seller_uuid = seller_uuid;
        this.product_uuid = product_uuid;
        this.status = status;
    }

    public Order() {
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getCustomer_uuid() {
        return customer_uuid;
    }

    public void setCustomer_uuid(String customer_uuid) {
        this.customer_uuid = customer_uuid;
    }

    public String getSeller_uuid() {
        return seller_uuid;
    }

    public void setSeller_uuid(String seller_uuid) {
        this.seller_uuid = seller_uuid;
    }

    public String getProduct_uuid() {
        return product_uuid;
    }

    public void setProduct_uuid(String product_uuid) {
        this.product_uuid = product_uuid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
