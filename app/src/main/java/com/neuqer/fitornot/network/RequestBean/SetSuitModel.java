package com.neuqer.fitornot.network.RequestBean;

import java.util.List;

/**
 * @author DuLong
 * @since 2019/8/3
 * email 798382030@qq.com
 */

public class SetSuitModel {

    /**
     * suit : [{"x":33,"y":222,"id":"9"}]
     */
    private List<SuitEntity> suit;

    public void setSuit(List<SuitEntity> suit) {
        this.suit = suit;
    }

    public List<SuitEntity> getSuit() {
        return suit;
    }

    public class SuitEntity {
        /**
         * x : 33
         * y : 222
         * id : 9
         */
        private int x;
        private int y;
        private String id;

        public void setX(int x) {
            this.x = x;
        }

        public void setY(int y) {
            this.y = y;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public String getId() {
            return id;
        }
    }
}
