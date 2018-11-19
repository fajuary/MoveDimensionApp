package dongwei.fajuary.movedimensionapp.personInfoModel.bean;

import java.io.Serializable;

/**
 * 作者：${神游风云（fajuary）} on 2018/2/3 18:14
 * 邮箱：fajuary@foxmail.com
 */

/**
 *  "id": 26,
 "uid": 8,
 "videoId": "78f520f844624152ae6c8d0084204f19",
 "imgeUrl": "http://video.dongweinet.com/image/cover/BEE531C94E264F9F88D59F9D19B1481D-6-2.png?auth_key=1517311422-0-0-dea9028c0011883845a41a5b9cfb28dd",
 "blessing": "",
 "type": 1,
 "addtime": 1517307837,
 "specialId": 3,
 "deleted": 1,
 "shareCode": "V5LUXLU5"
 */

public class MyYearVideoInfo implements Serializable {
    private int id;//视频id
    private int uid;//用户id
    private String videoId;//阿里视频id
    private String imgeUrl;//封面
    private String blessing;
    private int type;
    private long addtime;
    private int specialId;
    private int deleted;
    private String shareCode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getImgeUrl() {
        return imgeUrl;
    }

    public void setImgeUrl(String imgeUrl) {
        this.imgeUrl = imgeUrl;
    }

    public String getBlessing() {
        return blessing;
    }

    public void setBlessing(String blessing) {
        this.blessing = blessing;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getAddtime() {
        return addtime;
    }

    public void setAddtime(long addtime) {
        this.addtime = addtime;
    }

    public int getSpecialId() {
        return specialId;
    }

    public void setSpecialId(int specialId) {
        this.specialId = specialId;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public String getShareCode() {
        return shareCode;
    }

    public void setShareCode(String shareCode) {
        this.shareCode = shareCode;
    }

    @Override
    public String toString() {
        return "MyYearVideoInfo{" +
                "id=" + id +
                ", uid=" + uid +
                ", videoId='" + videoId + '\'' +
                ", imgeUrl='" + imgeUrl + '\'' +
                ", blessing='" + blessing + '\'' +
                ", type=" + type +
                ", addtime=" + addtime +
                ", specialId=" + specialId +
                ", deleted=" + deleted +
                ", shareCode='" + shareCode + '\'' +
                '}';
    }
}
