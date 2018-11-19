package dongwei.fajuary.movedimensionapp.videoModel.bean;

import java.io.Serializable;

/**
 * 作者：${神游风云（fajuary）} on 2018/2/3 20:09
 * 邮箱：fajuary@foxmail.com
 */

public class PrizesInfo implements Serializable {
    private int id;
    private int picId;
    private int number;
    private long addtime;
    private int level;
    private int status;

    private String img;
    private String awardName;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPicId() {
        return picId;
    }

    public void setPicId(int picId) {
        this.picId = picId;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public long getAddtime() {
        return addtime;
    }

    public void setAddtime(long addtime) {
        this.addtime = addtime;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getAwardName() {
        return awardName;
    }

    public void setAwardName(String awardName) {
        this.awardName = awardName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "PrizesInfo{" +
                "id=" + id +
                ", picId=" + picId +
                ", number=" + number +
                ", addtime=" + addtime +
                ", level=" + level +
                ", status=" + status +
                ", img='" + img + '\'' +
                ", awardName='" + awardName + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
