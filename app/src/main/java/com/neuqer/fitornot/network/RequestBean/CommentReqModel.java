package com.neuqer.fitornot.network.RequestBean;

/**
 * @author DuLong
 * @since 2019/8/3
 * email 798382030@qq.com
 */

public class CommentReqModel {
    /**
     * comment : {"to":"1","content":"一一得一"}
     */
    private CommentEntity comment;

    public void setComment(CommentEntity comment) {
        this.comment = comment;
    }

    public CommentEntity getComment() {
        return comment;
    }

    public class CommentEntity {
        /**
         * to : 1
         * content : 一一得一
         */
        private String to;
        private String content;

        public void setTo(String to) {
            this.to = to;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTo() {
            return to;
        }

        public String getContent() {
            return content;
        }
    }
}
