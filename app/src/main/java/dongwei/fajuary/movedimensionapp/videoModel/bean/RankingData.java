package dongwei.fajuary.movedimensionapp.videoModel.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：${神游风云（fajuary）} on 2018/2/3 20:08
 * 邮箱：fajuary@foxmail.com
 */

public class RankingData implements Serializable {
    private List<PrizesInfo> prizes;
    private List<UsersInfo> users;

    public List<PrizesInfo> getPrizes() {
        return prizes;
    }

    public void setPrizes(List<PrizesInfo> prizes) {
        this.prizes = prizes;
    }

    public List<UsersInfo> getUsers() {
        return users;
    }

    public void setUsers(List<UsersInfo> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "RankingData{" +
                "prizes=" + prizes +
                ", users=" + users +
                '}';
    }
}
