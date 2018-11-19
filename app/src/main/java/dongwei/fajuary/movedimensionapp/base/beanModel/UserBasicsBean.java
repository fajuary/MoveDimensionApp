package dongwei.fajuary.movedimensionapp.base.beanModel;

import java.io.Serializable;

/**
 * 作者：${神游风云（fajuary）} on 2018/2/3 16:15
 * 邮箱：fajuary@foxmail.com
 */

public class UserBasicsBean implements Serializable {
    private int status;
    private String msg;
    private UserBasicsData data;

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

    public UserBasicsData getData() {
        return data;
    }

    public void setData(UserBasicsData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "UserBasicsBean{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
