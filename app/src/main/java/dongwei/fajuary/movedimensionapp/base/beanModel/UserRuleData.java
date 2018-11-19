package dongwei.fajuary.movedimensionapp.base.beanModel;

import java.io.Serializable;

/**
 * 作者：${神游风云（fajuary）} on 2018/2/11 11:44
 * 邮箱：fajuary@foxmail.com
 */

public class UserRuleData implements Serializable {
    private String name;
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "UserRuleData{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
