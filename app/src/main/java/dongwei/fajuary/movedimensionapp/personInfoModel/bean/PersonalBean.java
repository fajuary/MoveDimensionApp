package dongwei.fajuary.movedimensionapp.personInfoModel.bean;

import java.io.Serializable;

/**
 * 作者：${神游风云（fajuary）} on 2018/2/5 16:54
 * 邮箱：fajuary@foxmail.com
 */

public class PersonalBean  implements Serializable{
    private int status;
    private String msg;
    private PersonalData data;

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

    public PersonalData getData() {
        return data;
    }

    public void setData(PersonalData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "PersonalBean{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
