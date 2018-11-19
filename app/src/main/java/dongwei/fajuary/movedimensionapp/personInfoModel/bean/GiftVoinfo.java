package dongwei.fajuary.movedimensionapp.personInfoModel.bean;

import java.io.Serializable;

/**
 * 作者：${神游风云（fajuary）} on 2018/2/5 16:57
 * 邮箱：fajuary@foxmail.com
 */

public class GiftVoinfo implements Serializable {
    private String picUrl;
    private int number;
    private String name;

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "GiftVoinfo{" +
                "picUrl='" + picUrl + '\'' +
                ", number=" + number +
                ", name='" + name + '\'' +
                '}';
    }
}
