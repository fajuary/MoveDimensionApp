package dongwei.fajuary.movedimensionapp.personInfoModel.bean;

import java.io.Serializable;

/**
 * 作者：${神游风云（fajuary）} on 2018/2/5 17:00
 * 邮箱：fajuary@foxmail.com
 */

public class GiftgiveVoinfo implements Serializable {
    private String giverName;
    private String giftName;
    private int number;
    private String addtime;
    private int spotLaud;

    public String getGiverName() {
        return giverName;
    }

    public void setGiverName(String giverName) {
        this.giverName = giverName;
    }

    public String getGiftName() {
        return giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public int getSpotLaud() {
        return spotLaud;
    }

    public void setSpotLaud(int spotLaud) {
        this.spotLaud = spotLaud;
    }

    @Override
    public String toString() {
        return "GiftgiveVoinfo{" +
                "giverName='" + giverName + '\'' +
                ", giftName='" + giftName + '\'' +
                ", number=" + number +
                ", addtime='" + addtime + '\'' +
                ", spotLaud=" + spotLaud +
                '}';
    }
}
