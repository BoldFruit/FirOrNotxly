package com.neuqer.fitornot.network.ResponseBean;

/**
 * @author DuLong
 * @since 2019/8/3
 * email 798382030@qq.com
 */

public class CommentRspModel {
    /**
     * refer : null
     * fromName : Drill
     * created_at : 2019-06-03 12:22:24
     * from : o9i844xpeqNEzTN97J3cAzUN369A
     * id : 1
     * content : 好看
     */
    private String refer;
    private String fromName;
    private String created_at;
    private String from;
    private int id;
    private String content;

    public void setRefer(String refer) {
        this.refer = refer;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRefer() {
        return refer;
    }

    public String getFromName() {
        return fromName;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getFrom() {
        return from;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
