package org.aplusscreators.hakikisha.model;

public class Payment {

    private String uuid;
    private int amount;
    private int order_id;
    private String received_date;
    private String received_time;
    private String customer_uuid;
    private String comments;
    private int rating;

    public Payment(String uuid, int amount, int order_id, String received_date, String received_time, String customer_uuid, String comments, int rating) {
        this.uuid = uuid;
        this.amount = amount;
        this.order_id = order_id;
        this.received_date = received_date;
        this.received_time = received_time;
        this.customer_uuid = customer_uuid;
        this.comments = comments;
        this.rating = rating;
    }

    public Payment() {
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getReceived_date() {
        return received_date;
    }

    public void setReceived_date(String received_date) {
        this.received_date = received_date;
    }

    public String getReceived_time() {
        return received_time;
    }

    public void setReceived_time(String received_time) {
        this.received_time = received_time;
    }

    public String getCustomer_uuid() {
        return customer_uuid;
    }

    public void setCustomer_uuid(String customer_uuid) {
        this.customer_uuid = customer_uuid;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
