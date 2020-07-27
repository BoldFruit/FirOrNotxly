package com.neuqer.fitornot.network.RequestBean;

import java.util.List;

/**
 * @author DuLong
 * @since 2019/8/2
 * email 798382030@qq.com
 */

public class UpdateClothesModel {
    /**
     * clothes : [{"price":"2520","id":"3","brand":"牛客"}]
     */
    private List<ClothesEntity> clothes;

    public void setClothes(List<ClothesEntity> clothes) {
        this.clothes = clothes;
    }

    public List<ClothesEntity> getClothes() {
        return clothes;
    }

    public class ClothesEntity {
        /**
         * price : 2520
         * id : 3
         * brand : 牛客
         */
        private String price;
        private String id;
        private String brand;

        public void setPrice(String price) {
            this.price = price;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getPrice() {
            return price;
        }

        public String getId() {
            return id;
        }

        public String getBrand() {
            return brand;
        }
    }
}
