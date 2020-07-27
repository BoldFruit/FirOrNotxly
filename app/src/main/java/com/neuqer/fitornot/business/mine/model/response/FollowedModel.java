package com.neuqer.fitornot.business.mine.model.response;

import java.util.List;

public class FollowedModel {


    /**
     * current_page : 1
     * data : [{"user_id":"1","nickname":"小叮当","avatar_url":"https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTL08IV2SbwPwUz6lhPSzot8Kz3AFkt2WA558aYfbesL4qfTlvAgKQ2HPqagHq2ial9ib380rA72OZcg/132","followers":0,"following":0}]
     * first_page_url : https://ssl.lyzwhh.top/user/allFollowed?page=1
     * from : 1
     * last_page : 1
     * last_page_url : https://ssl.lyzwhh.top/user/allFollowed?page=1
     * next_page_url : null
     * path : https://ssl.lyzwhh.top/user/allFollowed
     * per_page : 30
     * prev_page_url : null
     * to : 1
     * total : 1
     */

    private int current_page;
    private String first_page_url;
    private int from;
    private int last_page;
    private String last_page_url;
    private Object next_page_url;
    private String path;
    private int per_page;
    private Object prev_page_url;
    private int to;
    private int total;
    private List<DataBean> data;

    public int getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
    }

    public String getFirst_page_url() {
        return first_page_url;
    }

    public void setFirst_page_url(String first_page_url) {
        this.first_page_url = first_page_url;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getLast_page() {
        return last_page;
    }

    public void setLast_page(int last_page) {
        this.last_page = last_page;
    }

    public String getLast_page_url() {
        return last_page_url;
    }

    public void setLast_page_url(String last_page_url) {
        this.last_page_url = last_page_url;
    }

    public Object getNext_page_url() {
        return next_page_url;
    }

    public void setNext_page_url(Object next_page_url) {
        this.next_page_url = next_page_url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getPer_page() {
        return per_page;
    }

    public void setPer_page(int per_page) {
        this.per_page = per_page;
    }

    public Object getPrev_page_url() {
        return prev_page_url;
    }

    public void setPrev_page_url(Object prev_page_url) {
        this.prev_page_url = prev_page_url;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * user_id : 1
         * nickname : 小叮当
         * avatar_url : https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTL08IV2SbwPwUz6lhPSzot8Kz3AFkt2WA558aYfbesL4qfTlvAgKQ2HPqagHq2ial9ib380rA72OZcg/132
         * followers : 0
         * following : 0
         */

        private String user_id;
        private String nickname;
        private String avatar_url;
        private int followers;
        private int following;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
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

        public int getFollowers() {
            return followers;
        }

        public void setFollowers(int followers) {
            this.followers = followers;
        }

        public int getFollowing() {
            return following;
        }

        public void setFollowing(int following) {
            this.following = following;
        }
    }
}
