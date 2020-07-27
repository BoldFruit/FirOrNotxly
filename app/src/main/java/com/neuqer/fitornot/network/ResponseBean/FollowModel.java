package com.neuqer.fitornot.network.ResponseBean;

import java.util.List;

/**
 * @author DuLong
 * @since 2019/8/3
 * email 798382030@qq.com
 */

public class FollowModel {
    /**
     * first_page_url : https://ssl.lyzwhh.top/user/allFollowed?page=1
     * path : https://ssl.lyzwhh.top/user/allFollowed
     * per_page : 30
     * total : 5
     * data : [{"to":"2"},{"to":"4"},{"to":"1"},{"to":"fakeopenid2"},{"to":"o9i844xpeqNEzTN97J3cAzUN369A"}]
     * last_page : 1
     * last_page_url : https://ssl.lyzwhh.top/user/allFollowed?page=1
     * next_page_url : null
     * from : 1
     * to : 5
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

    public class DataEntity {
        /**
         * to : 2
         */
        private String to;

        public void setTo(String to) {
            this.to = to;
        }

        public String getTo() {
            return to;
        }
    }
}
