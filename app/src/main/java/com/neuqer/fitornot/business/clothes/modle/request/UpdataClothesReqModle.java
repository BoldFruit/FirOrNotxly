package com.neuqer.fitornot.business.clothes.modle.request;

/**
 * @author DuLong
 * @since 2019/10/7
 * email 798382030@qq.com
 */

public class UpdataClothesReqModle {
    /**
     * clothes : {"id":"7","brand":"牛客","remarks":"实惠啊"}
     */
    private ClothesEntity clothes;

    public void setClothes(ClothesEntity clothes) {
        this.clothes = clothes;
    }

    public ClothesEntity getClothes() {
        return clothes;
    }

    public static class ClothesEntity {
        /**
         * id : 7
         * brand : 牛客
         * remarks : 实惠啊
         */
        private String id;
        private String brand;
        private String remarks;

        public void setId(String id) {
            this.id = id;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public String getId() {
            return id;
        }

        public String getBrand() {
            return brand;
        }

        public String getRemarks() {
            return remarks;
        }
    }
}
