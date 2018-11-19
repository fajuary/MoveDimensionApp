package dongwei.fajuary.movedimensionapp.videoModel.bean;

import java.io.Serializable;

/**
 * 作者：${神游风云（fajuary）} on 2018/2/11 20:33
 * 邮箱：fajuary@foxmail.com
 */

public class EnterpriseVideoBean implements Serializable {
    private int status;
    private String msg;
    private EnterpriseVideoData data;

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

    public EnterpriseVideoData getData() {
        return data;
    }

    public void setData(EnterpriseVideoData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "EnterpriseVideoBean{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
