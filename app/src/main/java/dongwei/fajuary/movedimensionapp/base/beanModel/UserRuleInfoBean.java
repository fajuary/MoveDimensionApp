package dongwei.fajuary.movedimensionapp.base.beanModel;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：${神游风云（fajuary）} on 2018/2/11 11:43
 * 邮箱：fajuary@foxmail.com
 */

public class UserRuleInfoBean implements Serializable {
    private int status;
    private String msg;
    private List<UserRuleData> data;

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


    public List<UserRuleData> getData() {
        return data;
    }

    public void setData(List<UserRuleData> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "UserRuleInfoBean{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
