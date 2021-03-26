package com.example.pear;

class MainData {
   private String timestamp,restaurant_name, restaurant_image,restaurant_location;
   Double grand_total;
   String total_info;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getRestaurant_name() {
        return restaurant_name;
    }

    public void setRestaurant_name(String restaurant_name) {
        this.restaurant_name = restaurant_name;
    }

    public String getRestaurant_image() {
        return restaurant_image;
    }

    public void setRestaurant_image(String restaurant_image) {
        this.restaurant_image = restaurant_image;
    }

    public String getRestaurant_location() {
        return restaurant_location;
    }

    public void setRestaurant_location(String restaurant_location) {
        this.restaurant_location = restaurant_location;
    }

    public Double getGrand_total() {
        return grand_total;
    }

    public void setGrand_total(Double grand_total) {
        this.grand_total = grand_total;
    }

    public String getTotal_info() {
        return total_info;
    }

    public void setTotal_info(String total_info) {
        this.total_info = total_info;
    }
}
