package com.commerce.constant;
public enum ComplaintType {
    DELIVERY("Delivery"),
    PRODUCT_QUALITY("Product Quality"),
    PRICING("Pricing"),
    OTHERS("Others");

    private final String displayName;

    ComplaintType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
