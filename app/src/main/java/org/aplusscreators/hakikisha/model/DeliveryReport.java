package org.aplusscreators.hakikisha.model;

public class DeliveryReport {

    private String uuid;
    private String order_id;
    private float rating;
    private String reportType;
    private String sellerUuid;
    private String buyerUuid;
    private String comments;
    private String deliveryDate;
    private String deliveryTime;
    private String attachmentUri;

    public DeliveryReport() {
    }

    public DeliveryReport(String uuid, String order_id, float rating, String reportType, String sellerUuid, String buyerUuid, String comments, String deliveryDate, String deliveryTime) {
        this.uuid = uuid;
        this.order_id = order_id;
        this.rating = rating;
        this.reportType = reportType;
        this.sellerUuid = sellerUuid;
        this.buyerUuid = buyerUuid;
        this.comments = comments;
        this.deliveryDate = deliveryDate;
        this.deliveryTime = deliveryTime;
    }

    public String getAttachmentUri() {
        return attachmentUri;
    }

    public void setAttachmentUri(String attachmentUri) {
        this.attachmentUri = attachmentUri;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getSellerUuid() {
        return sellerUuid;
    }

    public void setSellerUuid(String sellerUuid) {
        this.sellerUuid = sellerUuid;
    }

    public String getBuyerUuid() {
        return buyerUuid;
    }

    public void setBuyerUuid(String buyerUuid) {
        this.buyerUuid = buyerUuid;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }
}
