package dongwei.fajuary.movedimensionapp.videoModel.bean;

import java.io.Serializable;

/**
 * 作者：${神游风云（fajuary）} on 2018/2/5 09:50
 * 邮箱：fajuary@foxmail.com
 */

public class UsersInfo implements Serializable {
    private String icon;
    private String nickname;
    private int spotLaud;

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

    @Override
    public String toString() {
        return "UsersInfo{" +
                "icon='" + icon + '\'' +
                ", nickname='" + nickname + '\'' +
                ", spotLaud=" + spotLaud +
                '}';
    }
}
