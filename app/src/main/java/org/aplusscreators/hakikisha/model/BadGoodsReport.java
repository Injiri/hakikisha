package org.aplusscreators.hakikisha.model;

public class BadGoodsReport {

    private String uuid;
    private String orderId;
    private String buyerUuid;
    private String rejectReasone;
    private String deliveryDate;
    private String deliveryTime;
    private String sellerUuid;
    private String sellerName;
    private String attachmentUri;
    private float rating;
    private String comments;

    public BadGoodsReport() {
    }

    public BadGoodsReport(String uuid, String orderId, String rejectReasone, String deliveryDate, String deliveryTime, String sellerUuid, String attachmentUri, int rating, String comments) {
        this.uuid = uuid;
        this.orderId = orderId;
        this.rejectReasone = rejectReasone;
        this.deliveryDate = deliveryDate;
        this.deliveryTime = deliveryTime;
        this.sellerUuid = sellerUuid;
        this.attachmentUri = attachmentUri;
        this.rating = rating;
        this.comments = comments;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getBuyerUuid() {
        return buyerUuid;
    }

    public void setBuyerUuid(String buyerUuid) {
        this.buyerUuid = buyerUuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getRejectReasone() {
        return rejectReasone;
    }

    public void setRejectReasone(String rejectReasone) {
        this.rejectReasone = rejectReasone;
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

    public String getSellerUuid() {
        return sellerUuid;
    }

    public void setSellerUuid(String sellerUuid) {
        this.sellerUuid = sellerUuid;
    }

    public String getAttachmentUri() {
        return attachmentUri;
    }

    public void setAttachmentUri(String attachmentUri) {
        this.attachmentUri = attachmentUri;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
