package net.lucode.hackware.magicindicator.model;

/**
 * Created by yc on 2021/9/10
 **/
public class TabTwoFloorModel {
    private String title = "";
    private String subTitle = "";

    public TabTwoFloorModel(){

    }

    public TabTwoFloorModel(String title, String subTitle){
        this.title = title;
        this.subTitle = subTitle;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

