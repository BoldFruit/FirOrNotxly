package com.neuqer.fitornot.network.ResponseBean;

import java.util.List;

/**
 * @author DuLong
 * @since 2019/8/3
 * email 798382030@qq.com
 */

public class DetailedInfoModel {
    /**
     * userInfo : {"avatar_url":"https://wx.qlogo
     * .cn/mmopen/vi_32/mJYicLxIRVRlUAEhC7rsbxXLWsMGcRElrzE1075l5LFA2uxS17iacl2bUjbSlNlJtIwApHl9tlJ4iaHaSkDvVtDRw/132",
     * "signature":"点击填写",
     * "openid":"o9i844xvJ4C2WiLe3ZAwz3nSWe3g",
     * "nickname":"。",
     * "liked":0}
     * moment :
     * {"first_page_url":"https://ssl.lyzwhh.top/user/othersInfo/o9i844xvJ4C2WiLe3ZAwz3nSWe3g?page=1",
     * "path":"https://ssl.lyzwhh.top/user/othersInfo/o9i844xvJ4C2WiLe3ZAwz3nSWe3g",
     * "per_page":24,
     * "total":2,
     * "data":
     * [{"avatar_url":"https://wx.qlogo
     * .cn/mmopen/vi_32/mJYicLxIRVRlUAEhC7rsbxXLWsMGcRElrzE1075l5LFA2uxS17iacl2bUjbSlNlJtIwApHl9tlJ4iaHaSkDvVtDRw/132",
     * "comments_num":0,
     * "nickname":"。",
     * "pics_url":["cloud://clothes-94er3.636c-clothes-94er3-1259319589
     * /o9i844xvJ4C2WiLe3ZAwz3nSWe3g/4/1560052279084"],"writer":"o9i844xvJ4C2WiLe3ZAwz3nSWe3g","id":68,"likes_num":2,
     * "content":"大家看看这双鞋怎么样？想买"},{"avatar_url":"https://wx.qlogo
     * .cn/mmopen/vi_32/mJYicLxIRVRlUAEhC7rsbxXLWsMGcRElrzE1075l5LFA2uxS17iacl2bUjbSlNlJtIwApHl9tlJ4iaHaSkDvVtDRw/132
     * ","comments_num":1,"nickname":"。","pics_url":["cloud://clothes-94er3.636c-clothes-94er3-1259319589
     * /o9i844xvJ4C2WiLe3ZAwz3nSWe3g/1/1560051686709"],"writer":"o9i844xvJ4C2WiLe3ZAwz3nSWe3g","id":66,"likes_num":1,
     * "content":"空军一号太好看了吧，美哭！"}],"last_page":1,"last_page_url":"https://ssl.lyzwhh
     * .top/user/othersInfo/o9i844xvJ4C2WiLe3ZAwz3nSWe3g?page=1","next_page_url":null,"from":1,"to":2,
     * "prev_page_url":null,"current_page":1}
     */
    private UserInfoEntity userInfo;
    private MomentEntity moment;

    public void setUserInfo(UserInfoEntity userInfo) {
        this.userInfo = userInfo;
    }

    public void setMoment(MomentEntity moment) {
        this.moment = moment;
    }

    public UserInfoEntity getUserInfo() {
        return userInfo;
    }

    public MomentEntity getMoment() {
        return moment;
    }

    public class UserInfoEntity {
        /**
         * avatar_url : https://wx.qlogo
         * .cn/mmopen/vi_32/mJYicLxIRVRlUAEhC7rsbxXLWsMGcRElrzE1075l5LFA2uxS17iacl2bUjbSlNlJtIwApHl9tlJ4iaHaSkDvVtDRw
         * /132
         * signature : 点击填写
         * openid : o9i844xvJ4C2WiLe3ZAwz3nSWe3g
         * nickname : 。
         * liked : 0
         */
        private String avatar_url;
        private String signature;
        private String openid;
        private String nickname;
        private int liked;

        public void setAvatar_url(String avatar_url) {
            this.avatar_url = avatar_url;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public void setOpenid(String openid) {
            this.openid = openid;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public void setLiked(int liked) {
            this.liked = liked;
        }

        public String getAvatar_url() {
            return avatar_url;
        }

        public String getSignature() {
            return signature;
        }

        public String getOpenid() {
            return openid;
        }

        public String getNickname() {
            return nickname;
        }

        public int getLiked() {
            return liked;
        }
    }

    public class MomentEntity {
        /**
         * first_page_url : https://ssl.lyzwhh.top/user/othersInfo/o9i844xvJ4C2WiLe3ZAwz3nSWe3g?page=1
         * path : https://ssl.lyzwhh.top/user/othersInfo/o9i844xvJ4C2WiLe3ZAwz3nSWe3g
         * per_page : 24
         * total : 2
         * data : [{"avatar_url":"https://wx.qlogo
         * .cn/mmopen/vi_32/mJYicLxIRVRlUAEhC7rsbxXLWsMGcRElrzE1075l5LFA2uxS17iacl2bUjbSlNlJtIwApHl9tlJ4iaHaSkDvVtDRw
         * /132","comments_num":0,"nickname":"。",
         * "pics_url":["cloud://clothes-94er3.636c-clothes-94er3-1259319589/o9i844xvJ4C2WiLe3ZAwz3nSWe3g/4
         * /1560052279084"],"writer":"o9i844xvJ4C2WiLe3ZAwz3nSWe3g","id":68,"likes_num":2,"content":"大家看看这双鞋怎么样？想买"},
         * {"avatar_url":"https://wx.qlogo
         * .cn/mmopen/vi_32/mJYicLxIRVRlUAEhC7rsbxXLWsMGcRElrzE1075l5LFA2uxS17iacl2bUjbSlNlJtIwApHl9tlJ4iaHaSkDvVtDRw
         * /132","comments_num":1,"nickname":"。",
         * "pics_url":["cloud://clothes-94er3.636c-clothes-94er3-1259319589/o9i844xvJ4C2WiLe3ZAwz3nSWe3g/1
         * /1560051686709"],"writer":"o9i844xvJ4C2WiLe3ZAwz3nSWe3g","id":66,"likes_num":1,"content":"空军一号太好看了吧，美哭！"}]
         * last_page : 1
         * last_page_url : https://ssl.lyzwhh.top/user/othersInfo/o9i844xvJ4C2WiLe3ZAwz3nSWe3g?page=1
         * next_page_url : null
         * from : 1
         * to : 2
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
             * avatar_url : https://wx.qlogo
             * .cn/mmopen/vi_32/mJYicLxIRVRlUAEhC7rsbxXLWsMGcRElrzE1075l5LFA2uxS17iacl2bUjbSlNlJtIwApHl9tlJ4iaHaSkDvVtDRw/132
             * comments_num : 0
             * nickname : 。
             * pics_url : ["cloud://clothes-94er3.636c-clothes-94er3-1259319589/o9i844xvJ4C2WiLe3ZAwz3nSWe3g/4/1560052279084"]
             * writer : o9i844xvJ4C2WiLe3ZAwz3nSWe3g
             * id : 68
             * likes_num : 2
             * content : 大家看看这双鞋怎么样？想买
             */
            private String avatar_url;
            private int comments_num;
            private String nickname;
            private List<String> pics_url;
            private String writer;
            private int id;
            private int likes_num;
            private String content;

            public void setAvatar_url(String avatar_url) {
                this.avatar_url = avatar_url;
            }

            public void setComments_num(int comments_num) {
                this.comments_num = comments_num;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public void setPics_url(List<String> pics_url) {
                this.pics_url = pics_url;
            }

            public void setWriter(String writer) {
                this.writer = writer;
            }

            public void setId(int id) {
                this.id = id;
            }

            public void setLikes_num(int likes_num) {
                this.likes_num = likes_num;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getAvatar_url() {
                return avatar_url;
            }

            public int getComments_num() {
                return comments_num;
            }

            public String getNickname() {
                return nickname;
            }

            public List<String> getPics_url() {
                return pics_url;
            }

            public String getWriter() {
                return writer;
            }

            public int getId() {
                return id;
            }

            public int getLikes_num() {
                return likes_num;
            }

            public String getContent() {
                return content;
            }
        }
    }
}
