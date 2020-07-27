package com.neuqer.fitornot.network.RequestBean;

/**
 * @author DuLong
 * @since 2019/8/3
 * email 798382030@qq.com
 */

public class UserInfoReqModel {
    /**
     * figure : {"hide_figure":1}
     * signature : 四五六四五七
     */
    private FigureEntity figure;
    private String signature;

    public void setFigure(FigureEntity figure) {
        this.figure = figure;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public FigureEntity getFigure() {
        return figure;
    }

    public String getSignature() {
        return signature;
    }

    public class FigureEntity {
        /**
         * hide_figure : 1
         */
        private int hide_figure;

        public void setHide_figure(int hide_figure) {
            this.hide_figure = hide_figure;
        }

        public int getHide_figure() {
            return hide_figure;
        }
    }
}
