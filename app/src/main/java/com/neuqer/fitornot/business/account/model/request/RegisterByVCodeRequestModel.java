package com.neuqer.fitornot.business.account.model.request;

/**
 * @author DuLong
 * @since 2019/9/10
 * email 798382030@qq.com
 */

public class RegisterByVCodeRequestModel {
    /**
     * phone : 13081860884
     * VCode : 51627
     */
    private long phone;
    private String VCode;

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public void setVCode(String VCode) {
        this.VCode = VCode;
    }

    public long getPhone() {
        return phone;
    }

    public String getVCode() {
        return VCode;
    }
}
