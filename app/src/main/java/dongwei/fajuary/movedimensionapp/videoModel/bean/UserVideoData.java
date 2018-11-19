package dongwei.fajuary.movedimensionapp.videoModel.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：${神游风云（fajuary）} on 2018/2/11 19:49
 * 邮箱：fajuary@foxmail.com
 */

/**
 * totalCount":28,"pageSize":10,"pageNo":1,"list
 */

public class UserVideoData implements Serializable {
    private int totalCount;
    private int pageSize;
    private int pageNo;
    private List<UserVideoInfo> list;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public List<UserVideoInfo> getList() {
        return list;
    }

    public void setList(List<UserVideoInfo> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "UserVideoData{" +
                "totalCount=" + totalCount +
                ", pageSize=" + pageSize +
                ", pageNo=" + pageNo +
                ", list=" + list +
                '}';
    }
}
