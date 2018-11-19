package dongwei.fajuary.movedimensionapp.videoModel.bean;

import java.io.Serializable;

/**
 * 作者：${神游风云（fajuary）} on 2018/2/11 19:50
 * 邮箱：fajuary@foxmail.com
 *
 * {
 "id":39,
 "uid":36,
 "icon":"http://thirdwx.qlogo.cn/mmopen/vi_32/DhCddpicsnLrxicVvtxYaB7e2sKRJUFU5WXAOCR8JOJbFwO8jR0zUGhibW8qcYv545JsaHCBotv63yThzClzxhe4g/132",
 "nickname":"神游风云",
 "videoId":"0084b49f68b94f9c9be464ea77c83c99",
 "imgeUrl":"http://211.149.207.217:8083/files/2018/02/11/3f4158ef-9f16-4456-ba9a-c56380ed6727.jpg",
 "blessing":"",
 "type":1,
 "addtime":1518348444,
 "specialId":0,
 "deleted":1,
 "shareCode":"ASPTHLEV",
 "title":""
 }
 */

public class UserVideoInfo implements Serializable{
    private int id;
    private int uid;
    private String icon;
    private String nickname;
    private String videoId;
    private String imgeUrl;
    private String blessing;
    private int type;
    private long addtime;
    private int specialId;
    private int deleted;
    private String shareCode;
    private String title;

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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "UserVideoInfo{" +
                "id=" + id +
                ", uid=" + uid +
                ", icon='" + icon + '\'' +
                ", nickname='" + nickname + '\'' +
                ", videoId='" + videoId + '\'' +
                ", imgeUrl='" + imgeUrl + '\'' +
                ", blessing='" + blessing + '\'' +
                ", type=" + type +
                ", addtime=" + addtime +
                ", specialId=" + specialId +
                ", deleted=" + deleted +
                ", shareCode='" + shareCode + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
