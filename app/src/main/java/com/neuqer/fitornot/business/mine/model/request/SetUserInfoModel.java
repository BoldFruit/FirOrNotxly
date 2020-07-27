package com.neuqer.fitornot.business.mine.model.request;

/**
 * Author: Kingtous
 * Since: 2019-09-26
 * Email: me@kingtous.cn
 */
public class SetUserInfoModel {

    /**
     * data : {"height":123,"weight":123,"signature":"Kingtous Test","hide_figure":1,"age":21,"nickname":"Kingtous","avatar_url":"https://kingtous.cn"}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * height : 123
         * weight : 123
         * signature : Kingtous Test
         * hide_figure : 1
         * age : 21
         * nickname : Kingtous
         * avatar_url : https://kingtous.cn
         */

        private int height;
        private int weight;
        private String signature;
        private int hide_figure;
        private int age;
        private String nickname;
        private String avatar_url;

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public int getHide_figure() {
            return hide_figure;
        }

        public void setHide_figure(int hide_figure) {
            this.hide_figure = hide_figure;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getAvatar_url() {
            return avatar_url;
        }

        public void setAvatar_url(String avatar_url) {
            this.avatar_url = avatar_url;
        }
    }
}
