package com.neuqer.fitornot.business.fittingroom.model.response;

import java.util.List;

/**
 * @author DuLong
 * @since 2019/8/2
 * email 798382030@qq.com
 */

public class GetClothesRspModel {


    /**
     * a : [{"owner":"o9i84491UEXlCWwl9HAq2LQSEMzs","color":"1","updated_at":"2019-07-30 19:27:29","price":"400",
     * "count":0,"created_at":"2019-07-30 19:27:29","id":70,"pic_url":"urlurl.com","category":"1","brand":"da"}]
     * b : [{"owner":"o9i84491UEXlCWwl9HAq2LQSEMzs","color":"1","updated_at":"2019-07-30 19:27:29","price":"400",
     * "count":0,"created_at":"2019-07-30 19:27:29","id":70,"pic_url":"urlurl.com","category":"1","brand":"da"}]
     * c : [{"owner":"o9i84491UEXlCWwl9HAq2LQSEMzs","color":"1","updated_at":"2019-07-30 19:27:29","price":"400",
     * "count":0,"created_at":"2019-07-30 19:27:29","id":70,"pic_url":"urlurl.com","category":"1","brand":"da"}]
     * d : [{"owner":"o9i84491UEXlCWwl9HAq2LQSEMzs","color":"1","updated_at":"2019-07-30 19:27:29","price":"400",
     * "count":0,"created_at":"2019-07-30 19:27:29","id":70,"pic_url":"urlurl.com","category":"1","brand":"da"}]
     */
    private List<AEntity> a;
    private List<BEntity> b;
    private List<CEntity> c;
    private List<DEntity> d;

    public void setA(List<AEntity> a) {
        this.a = a;
    }

    public void setB(List<BEntity> b) {
        this.b = b;
    }

    public void setC(List<CEntity> c) {
        this.c = c;
    }

    public void setD(List<DEntity> d) {
        this.d = d;
    }

    public List<AEntity> getA() {
        return a;
    }

    public List<BEntity> getB() {
        return b;
    }

    public List<CEntity> getC() {
        return c;
    }

    public List<DEntity> getD() {
        return d;
    }

    public class AEntity {
        /**
         * owner : o9i84491UEXlCWwl9HAq2LQSEMzs
         * color : 1
         * updated_at : 2019-07-30 19:27:29
         * price : 400
         * count : 0
         * created_at : 2019-07-30 19:27:29
         * id : 70
         * pic_url : urlurl.com
         * category : 1
         * brand : da
         */
        private String owner;
        private String color;
        private String updated_at;
        private String price;
        private int count;
        private String created_at;
        private int id;
        private String pic_url;
        private String category;
        private String brand;

        public void setOwner(String owner) {
            this.owner = owner;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public void setPrice(String price) {
            this.price = price;
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

        public void setPic_url(String pic_url) {
            this.pic_url = pic_url;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getOwner() {
            return owner;
        }

        public String getColor() {
            return color;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public String getPrice() {
            return price;
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

    public class BEntity {
        /**
         * owner : o9i84491UEXlCWwl9HAq2LQSEMzs
         * color : 1
         * updated_at : 2019-07-30 19:27:29
         * price : 400
         * count : 0
         * created_at : 2019-07-30 19:27:29
         * id : 70
         * pic_url : urlurl.com
         * category : 1
         * brand : da
         */
        private String owner;
        private String color;
        private String updated_at;
        private String price;
        private int count;
        private String created_at;
        private int id;
        private String pic_url;
        private String category;
        private String brand;

        public void setOwner(String owner) {
            this.owner = owner;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public void setPrice(String price) {
            this.price = price;
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

        public void setPic_url(String pic_url) {
            this.pic_url = pic_url;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getOwner() {
            return owner;
        }

        public String getColor() {
            return color;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public String getPrice() {
            return price;
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

    public class CEntity {
        /**
         * owner : o9i84491UEXlCWwl9HAq2LQSEMzs
         * color : 1
         * updated_at : 2019-07-30 19:27:29
         * price : 400
         * count : 0
         * created_at : 2019-07-30 19:27:29
         * id : 70
         * pic_url : urlurl.com
         * category : 1
         * brand : da
         */
        private String owner;
        private String color;
        private String updated_at;
        private String price;
        private int count;
        private String created_at;
        private int id;
        private String pic_url;
        private String category;
        private String brand;

        public void setOwner(String owner) {
            this.owner = owner;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public void setPrice(String price) {
            this.price = price;
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

        public void setPic_url(String pic_url) {
            this.pic_url = pic_url;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getOwner() {
            return owner;
        }

        public String getColor() {
            return color;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public String getPrice() {
            return price;
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

    public class DEntity {
        /**
         * owner : o9i84491UEXlCWwl9HAq2LQSEMzs
         * color : 1
         * updated_at : 2019-07-30 19:27:29
         * price : 400
         * count : 0
         * created_at : 2019-07-30 19:27:29
         * id : 70
         * pic_url : urlurl.com
         * category : 1
         * brand : da
         */
        private String owner;
        private String color;
        private String updated_at;
        private String price;
        private int count;
        private String created_at;
        private int id;
        private String pic_url;
        private String category;
        private String brand;

        public void setOwner(String owner) {
            this.owner = owner;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public void setPrice(String price) {
            this.price = price;
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

        public void setPic_url(String pic_url) {
            this.pic_url = pic_url;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getOwner() {
            return owner;
        }

        public String getColor() {
            return color;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public String getPrice() {
            return price;
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
