package dongwei.fajuary.movedimensionapp.videoModel.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：${神游风云（fajuary）} on 2018/2/5 10:58
 * 邮箱：fajuary@foxmail.com
 */

public class SpecialEffectsData implements Serializable {
    private double balance;
    private List<SpecialEffectsInfo> list;

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public List<SpecialEffectsInfo> getList() {
        return list;
    }

    public void setList(List<SpecialEffectsInfo> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "SpecialEffectsData{" +
                "balance=" + balance +
                ", list=" + list +
                '}';
    }
}
