package com.neuqer.fitornot.business.circle.model.response;

public class CommentsModel {

    /**
     * id : 1
     * from : 3
     * refer : null
     * content : 好看
     * created_at : 2019-06-03 12:22:24
     * fromName : Drill
     * avatar_url : https://wx.qlogo.cn/mmopen/vi_32/63iapJiaAdZKBCwQopjGHEZQ3icgjicWhmg0ml4cvsicnmd4LuMAId3M0wEibaHBBMkdBiaf0FCTTaI6t548d7D2nxUUQ/132
     */

    private int id;
    private String from;
    private Object refer;
    private String content;
    private String created_at;
    private String fromName;
    private String avatar_url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public Object getRefer() {
        return refer;
    }

    public void setRefer(Object refer) {
        this.refer = refer;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }
}
