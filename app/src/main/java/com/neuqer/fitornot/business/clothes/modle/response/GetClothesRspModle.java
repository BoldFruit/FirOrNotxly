package com.neuqer.fitornot.business.clothes.modle.response;

import java.io.Serializable;
import java.util.List;

/**
 * @author DuLong
 * @since 2019/9/28
 * email 798382030@qq.com
 */

public class GetClothesRspModle implements Serializable{
    /**
     * first_page_url : https://ssl.lyzwhh.top/clothes/clothes?page=1
     * path : https://ssl.lyzwhh.top/clothes/clothes
     * per_page : 15
     * total : 1
     * data : [{"owner":"21","color":"1","updated_at":"2019-09-28 14:41:12","price":"未填写","count":0,
     * "created_at":"2019-09-28 14:41:12","id":308,"pic_url":"urlurl.com","category":"上衣-卫衣","brand":"测试",
     * "remarks":"好看","tags":[{"tag_name":"烧烤","tag_type":"场景"},{"tag_name":"冬天","tag_type":"季节"},{"tag_name":"春天",
     * "tag_type":"季节"},{"tag_name":"韩系","tag_type":"风格"}]}]
     * last_page : 1
     * last_page_url : https://ssl.lyzwhh.top/clothes/clothes?page=1
     * next_page_url : null
     * from : 1
     * to : 1
     * prev_page_url : null
     * current_page : 1
     */
    private String first_page_url;
    private String path;
    private int per_page;
    private int total;
    private List<DataEntity> data;
    private int last_page;
    private String last_page_url;
    private String next_page_url;
    private int from;
    private int to;
    private String prev_page_url;
    private int current_page;

    public void setFirst_page_url(String first_page_url) {
        this.first_page_url = first_page_url;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setPer_page(int per_page) {
        this.per_page = per_page;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public void setLast_page(int last_page) {
        this.last_page = last_page;
    }

    public void setLast_page_url(String last_page_url) {
        this.last_page_url = last_page_url;
    }

    public void setNext_page_url(String next_page_url) {
        this.next_page_url = next_page_url;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public void setPrev_page_url(String prev_page_url) {
        this.prev_page_url = prev_page_url;
    }

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
    }

    public String getFirst_page_url() {
        return first_page_url;
    }

    public String getPath() {
        return path;
    }

    public int getPer_page() {
        return per_page;
    }

    public int getTotal() {
        return total;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public int getLast_page() {
        return last_page;
    }

    public String getLast_page_url() {
        return last_page_url;
    }

    public String getNext_page_url() {
        return next_page_url;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public String getPrev_page_url() {
        return prev_page_url;
    }

    public int getCurrent_page() {
        return current_page;
    }

    public static class DataEntity implements Serializable{
        /**
         * owner : 21
         * color : 1
         * updated_at : 2019-09-28 14:41:12
         * price : 未填写
         * count : 0
         * created_at : 2019-09-28 14:41:12
         * id : 308
         * pic_url : urlurl.com
         * category : 上衣-卫衣
         * brand : 测试
         * remarks : 好看
         * tags : [{"tag_name":"烧烤","tag_type":"场景"},{"tag_name":"冬天","tag_type":"季节"},{"tag_name":"春天",
         * "tag_type":"季节"},{"tag_name":"韩系","tag_type":"风格"}]
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
        private String remarks;
        private List<TagsEntity> tags;

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

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public void setTags(List<TagsEntity> tags) {
            this.tags = tags;
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

        public String getRemarks() {
            return remarks;
        }

        public List<TagsEntity> getTags() {
            return tags;
        }

        public static class TagsEntity implements Serializable{
            /**
             * tag_name : 烧烤
             * tag_type : 场景
             */
            private String tag_name;
            private String tag_type;

            public void setTag_name(String tag_name) {
                this.tag_name = tag_name;
            }

            public void setTag_type(String tag_type) {
                this.tag_type = tag_type;
            }

            public String getTag_name() {
                return tag_name;
            }

            public String getTag_type() {
                return tag_type;
            }
        }
    }
}
