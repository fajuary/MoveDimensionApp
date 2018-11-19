package dongwei.fajuary.movedimensionapp.personInfoModel.bean;

import java.io.Serializable;

/**
 * 作者：${神游风云（fajuary）} on 2018/2/3 18:11
 * 邮箱：fajuary@foxmail.com
 */

public class MyYearVideoBean implements Serializable {
    private int status;
    private String msg;
    private MyYearVideoData data;

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

    public MyYearVideoData getData() {
        return data;
    }

    public void setData(MyYearVideoData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "MyYearVideoBean{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
