package dongwei.fajuary.movedimensionapp.personInfoModel.bean;

import java.io.Serializable;

/**
 * 作者：${神游风云（fajuary）} on 2018/2/5 17:09
 * 邮箱：fajuary@foxmail.com
 */

public class SpotFabuloussInfo implements Serializable {
    private int id;//记录id
    private int number;//数量
    private String spotFabulous;//点赞者
    private String addtime;//点赞时间

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getSpotFabulous() {
        return spotFabulous;
    }

    public void setSpotFabulous(String spotFabulous) {
        this.spotFabulous = spotFabulous;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    @Override
    public String toString() {
        return "SpotFabuloussInfo{" +
                "id=" + id +
                ", number=" + number +
                ", spotFabulous='" + spotFabulous + '\'' +
                ", addtime='" + addtime + '\'' +
                '}';
    }
}
