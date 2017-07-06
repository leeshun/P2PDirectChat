package leeshun.p2pdirectchat.ListenerImpl;

import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager;
import android.view.View;
import android.widget.AdapterView;

import java.util.List;

/**
 * Created by leeshun on 2017/7/6.
 */

public class ItemClickListener implements AdapterView.OnItemClickListener {
    private WifiP2pManager manager;
    private List<WifiP2pDevice> devices;
    private WifiP2pManager.Channel channel;

    public ItemClickListener(WifiP2pManager manager, List<WifiP2pDevice> devices, WifiP2pManager.Channel channel) {
        this.manager = manager;
        this.devices = devices;
        this.channel = channel;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final WifiP2pDevice device = devices.get(position);
        WifiP2pConfig config = new WifiP2pConfig();
        config.deviceAddress = device.deviceAddress;
        if(manager == null) {
            return;
        }
        manager.connect(channel,config,new WIfiConnectListener());
    }
}
