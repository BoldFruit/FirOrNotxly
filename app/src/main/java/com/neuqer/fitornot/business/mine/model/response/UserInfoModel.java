package com.neuqer.fitornot.business.mine.model.response;

/**
 * Author: Kingtous
 * Date: 2019/9/26
 * Email: me@kingtous.cn
 */

public class UserInfoModel {


    /**
     * user_id : 18
     * phone : 15180596882
     * avatar_url : https://cdn.lyzwhh.top/avatar.jpg
     * nickname : 未填写
     * height : 点击填写
     * weight : 点击填写
     * signature : 点击填写
     * liked : 0
     * age : 未填写
     * followers : 0
     * following : 0
     */

    private int user_id;
    private String phone;
    private String avatar_url;
    private String nickname;
    private String height;
    private String weight;
    private String signature;
    private int liked;
    private String age;
    private int followers;
    private int following;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public int getLiked() {
        return liked;
    }

    public void setLiked(int liked) {
        this.liked = liked;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
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
