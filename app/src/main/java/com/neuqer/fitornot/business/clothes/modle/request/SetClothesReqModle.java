package com.neuqer.fitornot.business.clothes.modle.request;

import java.util.List;

/**
 * @author DuLong
 * @since 2019/9/28
 * email 798382030@qq.com
 */

public class SetClothesReqModle {
    /**
     * clothes : {"color":"1","price":"400","pic_url":"urlurl.com","category":"上衣-卫衣","brand":"测试","remarks":"好看",
     * "tags":[{"tag_name":"烧烤","tag_type":"场景"},{"tag_name":"冬天","tag_type":"季节"},{"tag_name":"春天","tag_type":"季节"},
     * {"tag_name":"韩系","tag_type":"风格"}]}
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
         * color : 1
         * price : 400
         * pic_url : urlurl.com
         * category : 上衣-卫衣
         * brand : 测试
         * remarks : 好看
         * tags : [{"tag_name":"烧烤","tag_type":"场景"},{"tag_name":"冬天","tag_type":"季节"},{"tag_name":"春天",
         * "tag_type":"季节"},{"tag_name":"韩系","tag_type":"风格"}]
         */
        private String color;
        private String price;
        private String pic_url;
        private String category;
        private String brand;
        private String remarks;
        private List<TagsEntity> tags;

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

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public void setTags(List<TagsEntity> tags) {
            this.tags = tags;
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

        public String getRemarks() {
            return remarks;
        }

        public List<TagsEntity> getTags() {
            return tags;
        }

        public static class TagsEntity {
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
