package com.cenkkaraboa.kuponuygulamas.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("refUrl")
    @Expose
    private String refUrl;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("istLike")
    @Expose
    private String istLike;
    @SerializedName("istView")
    @Expose
    private String istView;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("priceOld")
    @Expose
    private String priceOld;
    @SerializedName("discountRate")
    @Expose
    private String discountRate;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("buyDescription")
    @Expose
    private String buyDescription;
    @SerializedName("optSpecial")
    @Expose
    private String optSpecial;
    @SerializedName("optlowPrice")
    @Expose
    private String optlowPrice;
    @SerializedName("optNew")
    @Expose
    private String optNew;
    @SerializedName("optKing")
    @Expose
    private String optKing;
    @SerializedName("optGift")
    @Expose
    private String optGift;
    @SerializedName("optSuper")
    @Expose
    private String optSuper;
    @SerializedName("fDate")
    @Expose
    private String fDate;
    @SerializedName("cDate")
    @Expose
    private String cDate;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("userLike")
    @Expose
    private Object userLike;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRefUrl() {
        return refUrl;
    }

    public void setRefUrl(String refUrl) {
        this.refUrl = refUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIstLike() {
        return istLike;
    }

    public void setIstLike(String istLike) {
        this.istLike = istLike;
    }

    public String getIstView() {
        return istView;
    }

    public void setIstView(String istView) {
        this.istView = istView;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPriceOld() {
        return priceOld;
    }

    public void setPriceOld(String priceOld) {
        this.priceOld = priceOld;
    }

    public String getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(String discountRate) {
        this.discountRate = discountRate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBuyDescription() {
        return buyDescription;
    }

    public void setBuyDescription(String buyDescription) {
        this.buyDescription = buyDescription;
    }

    public String getOptSpecial() {
        return optSpecial;
    }

    public void setOptSpecial(String optSpecial) {
        this.optSpecial = optSpecial;
    }

    public String getOptlowPrice() {
        return optlowPrice;
    }

    public void setOptlowPrice(String optlowPrice) {
        this.optlowPrice = optlowPrice;
    }

    public String getOptNew() {
        return optNew;
    }

    public void setOptNew(String optNew) {
        this.optNew = optNew;
    }

    public String getOptKing() {
        return optKing;
    }

    public void setOptKing(String optKing) {
        this.optKing = optKing;
    }

    public String getOptGift() {
        return optGift;
    }

    public void setOptGift(String optGift) {
        this.optGift = optGift;
    }

    public String getOptSuper() {
        return optSuper;
    }

    public void setOptSuper(String optSuper) {
        this.optSuper = optSuper;
    }

    public String getFDate() {
        return fDate;
    }

    public void setFDate(String fDate) {
        this.fDate = fDate;
    }

    public String getCDate() {
        return cDate;
    }

    public void setCDate(String cDate) {
        this.cDate = cDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getUserLike() {
        return userLike;
    }

    public void setUserLike(Object userLike) {
        this.userLike = userLike;
    }

}