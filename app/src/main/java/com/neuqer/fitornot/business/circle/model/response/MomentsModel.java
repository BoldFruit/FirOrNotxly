package com.neuqer.fitornot.business.circle.model.response;

import java.util.List;

public class MomentsModel {

    /**
     * current_page : 1
     * data : [{"content":"人工智能","writer":"3","avatar_url":"https://wx.qlogo.cn/mmopen/vi_32/63iapJiaAdZKBCwQopjGHEZQ3icgjicWhmg0ml4cvsicnmd4LuMAId3M0wEibaHBBMkdBiaf0FCTTaI6t548d7D2nxUUQ/132","id":109,"nickname":"Drill","likes_num":0,"comments_num":8,"views_num":1233,"pic_url":"qiniu_urlrul.com","request_id":null,"tags":[{"tag_name":"烧烤","tag_type":"场景"},{"tag_name":"冬天","tag_type":"季节"},{"tag_name":"春天","tag_type":"季节"},{"tag_name":"韩系","tag_type":"风格"}],"notFollowed":true}]
     * first_page_url : https://ssl.lyzwhh.top/moment/moment?page=1
     * from : 1
     * last_page : 1
     * last_page_url : https://ssl.lyzwhh.top/moment/moment?page=1
     * next_page_url : null
     * path : https://ssl.lyzwhh.top/moment/moment
     * per_page : 24
     * prev_page_url : null
     * to : 1
     * total : 1
     */

    private int current_page;
    private String first_page_url;
    private int from;
    private int last_page;
    private String last_page_url;
    private Object next_page_url;
    private String path;
    private int per_page;
    private Object prev_page_url;
    private int to;
    private int total;
    private List<DataBean> data;

    public int getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
    }

    public String getFirst_page_url() {
        return first_page_url;
    }

    public void setFirst_page_url(String first_page_url) {
        this.first_page_url = first_page_url;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getLast_page() {
        return last_page;
    }

    public void setLast_page(int last_page) {
        this.last_page = last_page;
    }

    public String getLast_page_url() {
        return last_page_url;
    }

    public void setLast_page_url(String last_page_url) {
        this.last_page_url = last_page_url;
    }

    public Object getNext_page_url() {
        return next_page_url;
    }

    public void setNext_page_url(Object next_page_url) {
        this.next_page_url = next_page_url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getPer_page() {
        return per_page;
    }

    public void setPer_page(int per_page) {
        this.per_page = per_page;
    }

    public Object getPrev_page_url() {
        return prev_page_url;
    }

    public void setPrev_page_url(Object prev_page_url) {
        this.prev_page_url = prev_page_url;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * content : 人工智能
         * writer : 3
         * avatar_url : https://wx.qlogo.cn/mmopen/vi_32/63iapJiaAdZKBCwQopjGHEZQ3icgjicWhmg0ml4cvsicnmd4LuMAId3M0wEibaHBBMkdBiaf0FCTTaI6t548d7D2nxUUQ/132
         * id : 109
         * nickname : Drill
         * likes_num : 0
         * comments_num : 8
         * views_num : 1233
         * pic_url : qiniu_urlrul.com
         * request_id : null
         * tags : [{"tag_name":"烧烤","tag_type":"场景"},{"tag_name":"冬天","tag_type":"季节"},{"tag_name":"春天","tag_type":"季节"},{"tag_name":"韩系","tag_type":"风格"}]
         * notFollowed : 1
         */

        private String content;
        private String writer;
        private String avatar_url;
        private int id;
        private String nickname;
        private int likes_num;
        private int comments_num;
        private int views_num;
        private String pic_url;
        private Object request_id;
        private int notFollowed;
        private List<TagsBean> tags;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getWriter() {
            return writer;
        }

        public void setWriter(String writer) {
            this.writer = writer;
        }

        public String getAvatar_url() {
            return avatar_url;
        }

        public void setAvatar_url(String avatar_url) {
            this.avatar_url = avatar_url;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getLikes_num() {
            return likes_num;
        }

        public void setLikes_num(int likes_num) {
            this.likes_num = likes_num;
        }

        public int getComments_num() {
            return comments_num;
        }

        public void setComments_num(int comments_num) {
            this.comments_num = comments_num;
        }

        public int getViews_num() {
            return views_num;
        }

        public void setViews_num(int views_num) {
            this.views_num = views_num;
        }

        public String getPic_url() {
            return pic_url;
        }

        public void setPic_url(String pic_url) {
            this.pic_url = pic_url;
        }

        public Object getRequest_id() {
            return request_id;
        }

        public void setRequest_id(Object request_id) {
            this.request_id = request_id;
        }

        public int isNotFollowed() {
            return notFollowed;
        }

        public void setNotFollowed(int notFollowed) {
            this.notFollowed = notFollowed;
        }

        public List<TagsBean> getTags() {
            return tags;
        }

        public void setTags(List<TagsBean> tags) {
            this.tags = tags;
        }

        public static class TagsBean {
            /**
             * tag_name : 烧烤
             * tag_type : 场景
             */

            private String tag_name;
            private String tag_type;

            public String getTag_name() {
                return tag_name;
            }

            public void setTag_name(String tag_name) {
                this.tag_name = tag_name;
            }

            public String getTag_type() {
                return tag_type;
            }

            public void setTag_type(String tag_type) {
                this.tag_type = tag_type;
            }
        }
    }
}
