package dongwei.fajuary.movedimensionapp.videoModel.bean;

import java.io.Serializable;

/**
 * 作者：${神游风云（fajuary）} on 2018/2/5 10:59
 * 邮箱：fajuary@foxmail.com
 */

public class SpecialEffectsInfo implements Serializable {
    private int id;
    private double money;
    private int type;
    private String name;
    private String picUrl;

    private boolean selected;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return "SpecialEffectsInfo{" +
                "id=" + id +
                ", money=" + money +
                ", type=" + type +
                ", name='" + name + '\'' +
                ", picUrl='" + picUrl + '\'' +
                ", selected=" + selected +
                '}';
    }
}
