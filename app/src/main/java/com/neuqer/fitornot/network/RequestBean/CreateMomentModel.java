package com.neuqer.fitornot.network.RequestBean;

import java.util.List;

/**
 * @author DuLong
 * @since 2019/8/2
 * email 798382030@qq.com
 */

public class CreateMomentModel {

    /**
     * pics_url : ["cloud://clothes-94er3.636c-clothes-94er3-1259319589/o9i844xpeqNEzTN97J3cAzUN369A/1/1559535751005"]
     * content : 123456
     */
    private List<String> pics_url;
    private String content;

    public void setPics_url(List<String> pics_url) {
        this.pics_url = pics_url;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getPics_url() {
        return pics_url;
    }

    public String getContent() {
        return content;
    }
}
