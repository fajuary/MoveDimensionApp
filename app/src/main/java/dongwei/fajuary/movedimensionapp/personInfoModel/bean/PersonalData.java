package dongwei.fajuary.movedimensionapp.personInfoModel.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：${神游风云（fajuary）} on 2018/2/5 16:54
 * 邮箱：fajuary@foxmail.com
 */

public class PersonalData implements Serializable{
    private int id;
    private String icon;
    private String nickname;
    private int spotLaud;
    private int ranking;
    private String phone;
    private List<GiftVoinfo> giftVoList;
    private List<GiftgiveVoinfo> giftGiveVoList;
    private List<SpotFabuloussInfo> spotFabulouss;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getSpotLaud() {
        return spotLaud;
    }

    public void setSpotLaud(int spotLaud) {
        this.spotLaud = spotLaud;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<GiftVoinfo> getGiftVoList() {
        return giftVoList;
    }

    public void setGiftVoList(List<GiftVoinfo> giftVoList) {
        this.giftVoList = giftVoList;
    }

    public List<GiftgiveVoinfo> getGiftGiveVoList() {
        return giftGiveVoList;
    }

    public void setGiftGiveVoList(List<GiftgiveVoinfo> giftGiveVoList) {
        this.giftGiveVoList = giftGiveVoList;
    }

    public List<SpotFabuloussInfo> getSpotFabulouss() {
        return spotFabulouss;
    }

    public void setSpotFabulouss(List<SpotFabuloussInfo> spotFabulouss) {
        this.spotFabulouss = spotFabulouss;
    }

    @Override
    public String toString() {
        return "PersonalData{" +
                "id=" + id +
                ", icon='" + icon + '\'' +
                ", nickname='" + nickname + '\'' +
                ", spotLaud=" + spotLaud +
                ", ranking=" + ranking +
                ", phone='" + phone + '\'' +
                ", giftVoList=" + giftVoList +
                ", giftGiveVoList=" + giftGiveVoList +
                ", spotFabulouss=" + spotFabulouss +
                '}';
    }
}
