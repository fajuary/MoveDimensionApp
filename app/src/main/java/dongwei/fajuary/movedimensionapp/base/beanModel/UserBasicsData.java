package dongwei.fajuary.movedimensionapp.base.beanModel;

import java.io.Serializable;

/**
 * 作者：${神游风云（fajuary）} on 2018/2/3 16:15
 * 邮箱：fajuary@foxmail.com
 *
 * "icon":"icon",
 "nickname":"nickname",
 "balance":5,
 "phone":""
 */

public class UserBasicsData implements Serializable {
    private String icon;//头像
    private String nickname;//昵称
    private String balance;//余额
    private String phone;//手机号

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

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "UserBasicsData{" +
                "icon='" + icon + '\'' +
                ", nickname='" + nickname + '\'' +
                ", balance='" + balance + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
