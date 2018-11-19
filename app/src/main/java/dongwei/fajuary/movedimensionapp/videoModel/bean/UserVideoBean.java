package dongwei.fajuary.movedimensionapp.videoModel.bean;

import java.io.Serializable;

/**
 * 作者：${神游风云（fajuary）} on 2018/2/11 19:47
 * 邮箱：fajuary@foxmail.com
 */

public class UserVideoBean implements Serializable {
    private int status;
    private String msg;
    private UserVideoData data;

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

    public UserVideoData getData() {
        return data;
    }

    public void setData(UserVideoData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "UserVideoBean{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
