package com.neuqer.fitornot.business.fittingroom.model.request;

/**
 * @author DuLong
 * @since 2019/10/6
 * email 798382030@qq.com
 */

public class CreateMomentReqModle {
    /**
     * suit_id : 27
     * content : 人工智能
     */
    private int suit_id;
    private String content;

    public void setSuit_id(int suit_id) {
        this.suit_id = suit_id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getSuit_id() {
        return suit_id;
    }

    public String getContent() {
        return content;
    }
}
