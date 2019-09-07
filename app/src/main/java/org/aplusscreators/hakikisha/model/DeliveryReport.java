package org.aplusscreators.hakikisha.model;

public class DeliveryReport {

    private String uuid;
    private String productId;
    private float rating;
    private String reportType;
    private String sellerPhone;
    private String sellerName;
    private String buyerUuid;
    private String comments;
    private String deliveryDate;
    private String deliveryTime;
    private String attachmentUri;
    private String status;

    public DeliveryReport() {
    }

    public DeliveryReport(String uuid, String productId, float rating, String reportType, String sellerPhone, String buyerUuid, String comments, String deliveryDate, String deliveryTime) {
        this.uuid = uuid;
        this.productId = productId;
        this.rating = rating;
        this.reportType = reportType;
        this.sellerPhone = sellerPhone;
        this.buyerUuid = buyerUuid;
        this.comments = comments;
        this.deliveryDate = deliveryDate;
        this.deliveryTime = deliveryTime;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getSellerName() {
        return sellerName;
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

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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

    public String getSellerPhone() {
        return sellerPhone;
    }

    public void setSellerPhone(String sellerPhone) {
        this.sellerPhone = sellerPhone;
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
