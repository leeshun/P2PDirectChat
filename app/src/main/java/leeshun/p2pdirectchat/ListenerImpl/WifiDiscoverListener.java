package leeshun.p2pdirectchat.ListenerImpl;

import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;
import android.widget.Toast;


/**
 * Created by leeshun on 2017/7/6.
 */

public class WifiDiscoverListener implements WifiP2pManager.ActionListener {
    private final String TAG = "P2PChat";
    @Override
    public void onSuccess() {
        //Toast.makeText(null,"Wifi Discovery",Toast.LENGTH_SHORT).show();
        Log.d(TAG,"Wifi discovery success");
    }

    @Override
    public void onFailure(int reason) {
        Log.d(TAG,"Wifi discovery fail -" + reason);
    }
}
