package dongwei.fajuary.movedimensionapp.videoModel.bean;

import java.io.Serializable;

/**
 * 作者：${神游风云（fajuary）} on 2018/2/5 10:57
 * 邮箱：fajuary@foxmail.com
 * 特效
 */

public class SpecialEffectsBean implements Serializable {
    private int status;
    private String msg;
    private SpecialEffectsData data;

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

    public SpecialEffectsData getData() {
        return data;
    }

    public void setData(SpecialEffectsData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "SpecialEffectsBean{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
