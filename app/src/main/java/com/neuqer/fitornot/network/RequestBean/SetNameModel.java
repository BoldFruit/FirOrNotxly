package com.neuqer.fitornot.network.RequestBean;

/**
 * @author DuLong
 * @since 2019/8/3
 * email 798382030@qq.com
 */

public class SetNameModel {
    /**
     * data : {"avatar_url":"https://timgsa.baidu
     * .com/timg?image&quality=80&size=b9999_10000&sec=1559370842820&di=675393119471806412895c2fbc3b72fa&imgtype=0
     * &src=http%3A%2F%2Fimg5.duitang.com%2Fuploads%2Fitem%2F201402%2F27%2F20140227171316_8Gw5y.thumb.600_0.jpeg",
     * "nickname":"舒克"}
     */
    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public class DataEntity {
        /**
         * avatar_url : https://timgsa.baidu
         * .com/timg?image&quality=80&size=b9999_10000&sec=1559370842820&di=675393119471806412895c2fbc3b72fa&imgtype
         * =0&src=http%3A%2F%2Fimg5.duitang.com%2Fuploads%2Fitem%2F201402%2F27%2F20140227171316_8Gw5y.thumb.600_0.jpeg
         * nickname : 舒克
         */
        private String avatar_url;
        private String nickname;

        public void setAvatar_url(String avatar_url) {
            this.avatar_url = avatar_url;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getAvatar_url() {
            return avatar_url;
        }

        public String getNickname() {
            return nickname;
        }
    }
}
