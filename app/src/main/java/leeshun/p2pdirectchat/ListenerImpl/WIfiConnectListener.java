package leeshun.p2pdirectchat.ListenerImpl;

import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;

/**
 * Created by leeshun on 2017/7/6.
 */

public class WIfiConnectListener implements WifiP2pManager.ActionListener {
    private final String TAG = "P2PChat";
    @Override
    public void onSuccess() {
        Log.d(TAG,"Wifi connect success");
    }

    @Override
    public void onFailure(int reason) {
        Log.d(TAG,"Wifi connect fail -" + reason);
    }
}
