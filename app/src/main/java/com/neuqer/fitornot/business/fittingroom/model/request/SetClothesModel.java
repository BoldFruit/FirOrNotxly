package com.neuqer.fitornot.business.fittingroom.model.request;

import java.util.List;

/**
 * @author DuLong
 * @since 2019/8/2
 * email 798382030@qq.com
 */

public class SetClothesModel {

    /**
     * clothes : [{"color":"1","price":"400","pic_url":"urlurl.com","category":"4","brand":"海尔"}]
     */
    private List<ClothesEntity> clothes;

    public void setClothes(List<ClothesEntity> clothes) {
        this.clothes = clothes;
    }

    public List<ClothesEntity> getClothes() {
        return clothes;
    }

    public static class ClothesEntity {
        /**
         * color : 1
         * price : 400
         * pic_url : urlurl.com
         * category : 4
         * brand : 海尔
         */
        private String color;
        private String price;
        private String pic_url;
        private String category;
        private String brand;

        public void setColor(String color) {
            this.color = color;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public void setPic_url(String pic_url) {
            this.pic_url = pic_url;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getColor() {
            return color;
        }

        public String getPrice() {
            return price;
        }

        public String getPic_url() {
            return pic_url;
        }

        public String getCategory() {
            return category;
        }

        public String getBrand() {
            return brand;
        }
    }
}
