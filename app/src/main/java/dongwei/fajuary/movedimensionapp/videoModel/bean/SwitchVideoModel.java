package dongwei.fajuary.movedimensionapp.videoModel.bean;

/**
 * 作者：${神游风云（fajuary）} on 2018/2/2 10:52
 * 邮箱：fajuary@foxmail.com
 */

public class SwitchVideoModel {
    private String url;
    private String name;

    public SwitchVideoModel(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
