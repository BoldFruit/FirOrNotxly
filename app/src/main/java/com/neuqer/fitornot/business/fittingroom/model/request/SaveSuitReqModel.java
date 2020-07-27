package com.neuqer.fitornot.business.fittingroom.model.request;

import java.util.List;

/**
 * @author DuLong
 * @since 2019/10/5
 * email 798382030@qq.com
 */

public class SaveSuitReqModel {


    /**
     * suit : {"clothes_ids":[1,2,3],"background":"**这个背景还没定用什么方式,反正是个string","title":"我的第一个搭配","category":"休闲",
     * "clothes":"qiniu_urlrul.com","remarks":"不容易染上烧烤的味道","tags":[{"tag_name":"烧烤","tag_type":"场景"},
     * {"tag_name":"冬天","tag_type":"季节"},{"tag_name":"春天","tag_type":"季节"},{"tag_name":"韩系","tag_type":"风格"}]}
     */
    private SuitEntity suit;

    public void setSuit(SuitEntity suit) {
        this.suit = suit;
    }

    public SuitEntity getSuit() {
        return suit;
    }

    public static class SuitEntity {
        /**
         * clothes_ids : [1,2,3]
         * background : **这个背景还没定用什么方式,反正是个string
         * title : 我的第一个搭配
         * category : 休闲
         * clothes : qiniu_urlrul.com
         * remarks : 不容易染上烧烤的味道
         * tags : [{"tag_name":"烧烤","tag_type":"场景"},{"tag_name":"冬天","tag_type":"季节"},{"tag_name":"春天",
         * "tag_type":"季节"},{"tag_name":"韩系","tag_type":"风格"}]
         */
        private List<Integer> clothes_ids;
        private String background;
        private String title;
        private String category;
        private String clothes;
        private String remarks;
        private List<TagsEntity> tags;

        public void setClothes_ids(List<Integer> clothes_ids) {
            this.clothes_ids = clothes_ids;
        }

        public void setBackground(String background) {
            this.background = background;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public void setClothes(String clothes) {
            this.clothes = clothes;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public void setTags(List<TagsEntity> tags) {
            this.tags = tags;
        }

        public List<Integer> getClothes_ids() {
            return clothes_ids;
        }

        public String getBackground() {
            return background;
        }

        public String getTitle() {
            return title;
        }

        public String getCategory() {
            return category;
        }

        public String getClothes() {
            return clothes;
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
