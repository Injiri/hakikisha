package org.aplusscreators.hakikisha.model;

public class PaymentReport {

    private String reportUuid;
    private String amount;
    private String orderId;
    private String dateReceived;
    private String timeReceived;
    private String customerFirstName;
    private String customerLastName;
    private String buyerUuid;
    private String comments;
    private float rating;

    public PaymentReport() {
    }

    public PaymentReport(String reportUuid, String amount, String orderId, String dateReceived, String timeReceived, String customerFirstName, String customerLastName, String comments, float rating) {
        this.reportUuid = reportUuid;
        this.amount = amount;
        this.orderId = orderId;
        this.dateReceived = dateReceived;
        this.timeReceived = timeReceived;
        this.customerFirstName = customerFirstName;
        this.customerLastName = customerLastName;
        this.comments = comments;
        this.rating = rating;
    }

    public String getBuyerUuid() {
        return buyerUuid;
    }

    public void setBuyerUuid(String buyerUuid) {
        this.buyerUuid = buyerUuid;
    }

    public String getReportUuid() {
        return reportUuid;
    }

    public String getAmount() {
        return amount;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getDateReceived() {
        return dateReceived;
    }

    public String getTimeReceived() {
        return timeReceived;
    }

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public String getComments() {
        return comments;
    }

    public float getRating() {
        return rating;
    }

    public void setReportUuid(String reportUuid) {
        this.reportUuid = reportUuid;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setDateReceived(String dateReceived) {
        this.dateReceived = dateReceived;
    }

    public void setTimeReceived(String timeReceived) {
        this.timeReceived = timeReceived;
    }

    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
