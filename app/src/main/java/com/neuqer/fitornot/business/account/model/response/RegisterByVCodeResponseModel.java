package com.neuqer.fitornot.business.account.model.response;

/**
 * @author DuLong
 * @since 2019/9/11
 * email 798382030@qq.com
 */

public class RegisterByVCodeResponseModel {


    /**
     * user_Id : 11
     * token :
     */
    private int user_Id;
    private String token;

    public void setUser_Id(int user_Id) {
        this.user_Id = user_Id;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getUser_Id() {
        return user_Id;
    }

    public String getToken() {
        return token;
    }
}
