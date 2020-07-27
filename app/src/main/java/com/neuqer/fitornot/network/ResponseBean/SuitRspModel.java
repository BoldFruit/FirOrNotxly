package com.neuqer.fitornot.network.ResponseBean;

import java.util.List;

/**
 * @author DuLong
 * @since 2019/8/3
 * email 798382030@qq.com
 */

public class SuitRspModel {
    /**
     * owner : o9i84491UEXlCWwl9HAq2LQSEMzs
     * total_price : 0
     * updated_at : 2019-06-12 20:20:47
     * count : 0
     * created_at : 2019-06-12 20:20:47
     * id : 16
     * per_price : 0
     * clothes : [{"x":60,"y":10,"id":42,"pic_url":["dds"],
     * "url":"cloud://clothes-94er3.636c-clothes-94er3-1259319589/o9i84491UEXlCWwl9HAq2LQSEMzs/1/1560337924172"}]
     */
    private String owner;
    private int total_price;
    private String updated_at;
    private int count;
    private String created_at;
    private int id;
    private int per_price;
    private List<ClothesEntity> clothes;

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPer_price(int per_price) {
        this.per_price = per_price;
    }

    public void setClothes(List<ClothesEntity> clothes) {
        this.clothes = clothes;
    }

    public String getOwner() {
        return owner;
    }

    public int getTotal_price() {
        return total_price;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public int getCount() {
        return count;
    }

    public String getCreated_at() {
        return created_at;
    }

    public int getId() {
        return id;
    }

    public int getPer_price() {
        return per_price;
    }

    public List<ClothesEntity> getClothes() {
        return clothes;
    }

    public class ClothesEntity {
        /**
         * x : 60
         * y : 10
         * id : 42
         * pic_url : ["dds"]
         * url : cloud://clothes-94er3.636c-clothes-94er3-1259319589/o9i84491UEXlCWwl9HAq2LQSEMzs/1/1560337924172
         */
        private int x;
        private int y;
        private int id;
        private List<String> pic_url;
        private String url;

        public void setX(int x) {
            this.x = x;
        }

        public void setY(int y) {
            this.y = y;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setPic_url(List<String> pic_url) {
            this.pic_url = pic_url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getId() {
            return id;
        }

        public List<String> getPic_url() {
            return pic_url;
        }

        public String getUrl() {
            return url;
        }
    }
}
