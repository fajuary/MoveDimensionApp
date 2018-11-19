package dongwei.fajuary.movedimensionapp.videoModel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


/**
 * Created 张朋飞 on 2016/11/2.
 * user 864598192
 */
public class FinalReceiver extends BroadcastReceiver {
    public PersonInfoInterfce personInfoInterfce;
    private String orderID;
    private int specialEffectsInt;

    public FinalReceiver(String orderID,int specialEffectsInt) {
        this.orderID=orderID;
        this.specialEffectsInt=specialEffectsInt;
        this.personInfoInterfce = VideoGenerationActivity.interfce;
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        if ( null!= personInfoInterfce){
            personInfoInterfce.perSonInfo(orderID,specialEffectsInt);
        }
    }

    public interface PersonInfoInterfce {
         void perSonInfo(String orderID,int specialEffectsInt);
    }

}
