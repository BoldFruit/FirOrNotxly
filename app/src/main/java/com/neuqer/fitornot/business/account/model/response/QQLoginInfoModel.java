package com.neuqer.fitornot.business.account.model.response;

/**
 * @author DuLong
 * @since 2019/8/4
 * email 798382030@qq.com
 */

public class QQLoginInfoModel {
    /**
     * ret : 0
     * msg : sucess
     * access_token : xxxxxxxxxxxxxxxxxxxxx
     * pfkey : xxxxxxxxxxxxxxxxxxx
     * pay_token : xxxxxxxxxxxxxxxx
     * pf : openmobile_android
     * openid : xxxxxxxxxxxxxxxxxxx
     * expires_in : 7776000
     */
    private int ret;
    private String msg;
    private String access_token;
    private String pfkey;
    private String pay_token;
    private String pf;
    private String openid;
    private String expires_in;

    public void setRet(int ret) {
        this.ret = ret;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public void setPfkey(String pfkey) {
        this.pfkey = pfkey;
    }

    public void setPay_token(String pay_token) {
        this.pay_token = pay_token;
    }

    public void setPf(String pf) {
        this.pf = pf;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }

    public int getRet() {
        return ret;
    }

    public String getMsg() {
        return msg;
    }

    public String getAccess_token() {
        return access_token;
    }

    public String getPfkey() {
        return pfkey;
    }

    public String getPay_token() {
        return pay_token;
    }

    public String getPf() {
        return pf;
    }

    public String getOpenid() {
        return openid;
    }

    public String getExpires_in() {
        return expires_in;
    }
}
