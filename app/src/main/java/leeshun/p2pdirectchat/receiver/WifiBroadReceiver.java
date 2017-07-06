package leeshun.p2pdirectchat.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;

import leeshun.p2pdirectchat.activity.WifiChatAcitivty;
import leeshun.p2pdirectchat.activity.WifiMainAcitvity;
import leeshun.p2pdirectchat.manager.ClientManager;
import leeshun.p2pdirectchat.manager.ServerManager;

/**
 * Created by leeshun on 2017/7/6.
 */

public class WifiBroadReceiver extends BroadcastReceiver {
    public static ServerManager serverManager = new ServerManager();
    public static ClientManager clientManager = new ClientManager();

    private final String TAG = "P2PChat";
    private WifiP2pManager manager;
    private WifiP2pManager.Channel channel;
    private WifiMainAcitvity acitvity;

    public WifiBroadReceiver(WifiP2pManager manager, WifiP2pManager.Channel channel, WifiMainAcitvity acitvity) {
        super();
        this.manager = manager;
        this.channel = channel;
        this.acitvity = acitvity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        Log.d(TAG,action);
        if(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            onStateChanged(intent);
        } else if(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
            onConnectionChanged(intent);
        } else if(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
            onPeersChanged();
        } else if(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {
            onDeviceChanged();
        }
    }

    private void onStateChanged(Intent intent) {
        int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE,-1);
        if(state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
            Log.d(TAG,"Wifi Enable");
        }
        else {
            Log.d(TAG,"Wifi Disable");
        }
    }

    private void onConnectionChanged(Intent intent) {
        if(manager == null) {

            return;
        }

        NetworkInfo info = intent.getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO);
        Log.d("Connected","" + info.isConnected());
        if(info.isConnected()) {
            Log.d("Connected","" + info.isConnected());
            manager.requestConnectionInfo(channel, new WifiP2pManager.ConnectionInfoListener() {
                @Override
                public void onConnectionInfoAvailable(WifiP2pInfo info) {
                    Log.d("info","" + info.isGroupOwner);
                    if(info.isGroupOwner) {
                        WifiBroadReceiver.serverManager.setAcitvity(acitvity);
                        WifiBroadReceiver.serverManager.execute();
                    } else {
                        WifiBroadReceiver.clientManager.setAddress(info.groupOwnerAddress);
                        WifiBroadReceiver.clientManager.setAcitvity(acitvity);
                        WifiBroadReceiver.clientManager.execute();
                    }
                }
            });
        }
    }

    private void onPeersChanged() {
        if (manager == null) {
            return;
        }

        manager.requestPeers(channel, new WifiP2pManager.PeerListListener() {
            @Override
            public void onPeersAvailable(WifiP2pDeviceList peers) {
                acitvity.displayDeviceList(peers);
            }
        });
    }

    private void onDeviceChanged() {
        Log.d(TAG,"deviced has changed");
    }
}
