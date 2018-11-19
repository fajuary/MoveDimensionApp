package dongwei.fajuary.movedimensionapp.videoModel.bean;

import java.io.Serializable;

/**
 * 作者：${神游风云（fajuary）} on 2018/2/3 20:07
 * 邮箱：fajuary@foxmail.com
 */

public class RankingBean implements Serializable {
    private int status;
    private String msg;
    private RankingData data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public RankingData getData() {
        return data;
    }

    public void setData(RankingData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RankingBean{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
