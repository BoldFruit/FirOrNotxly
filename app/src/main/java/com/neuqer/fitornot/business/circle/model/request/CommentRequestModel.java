package com.neuqer.fitornot.business.circle.model.request;

public class CommentRequestModel {
    /**
     * comment : {"content":"一一得一","to":"4"}
     */

    private CommentBean comment;

    public CommentBean getComment() {
        return comment;
    }

    public void setComment(CommentBean comment) {
        this.comment = comment;
    }

    public static class CommentBean {
        /**
         * content : 一一得一
         * to : 4
         */

        private String content;
        private String to;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }
    }
}