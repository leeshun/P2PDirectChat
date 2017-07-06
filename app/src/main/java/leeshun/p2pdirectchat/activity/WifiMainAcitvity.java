package leeshun.p2pdirectchat.activity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import leeshun.p2pdirectchat.ListenerImpl.ItemClickListener;
import leeshun.p2pdirectchat.ListenerImpl.WifiDiscoverListener;
import leeshun.p2pdirectchat.R;
import leeshun.p2pdirectchat.adapter.WifiDeviceAdapter;
import leeshun.p2pdirectchat.manager.ClientManager;
import leeshun.p2pdirectchat.manager.ServerManager;
import leeshun.p2pdirectchat.receiver.WifiBroadReceiver;

public class WifiMainAcitvity extends AppCompatActivity {

    private final IntentFilter intentFilter = new IntentFilter();

    private List<WifiP2pDevice> devices;
    private ListView devicesListView;
    private WifiDeviceAdapter adapter;
    private WifiP2pManager manager;
    private WifiP2pManager.Channel channel;
    private WifiBroadReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wifi_main_acitvity);

        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);

        manager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        channel = (WifiP2pManager.Channel)manager.initialize(this,getMainLooper(),null);
        manager.discoverPeers(channel,new WifiDiscoverListener());

        devicesListView = (ListView) findViewById(R.id.list);

        devices = new ArrayList<>();
        adapter = new WifiDeviceAdapter(devices,this);
        devicesListView.setAdapter(adapter);

        devicesListView.setOnItemClickListener(new ItemClickListener(manager,devices,channel));

    }

    @Override
    public void onResume() {
        super.onResume();
        receiver = new WifiBroadReceiver(manager,channel,this);
        registerReceiver(receiver,intentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }


    public void displayDeviceList(WifiP2pDeviceList list) {
        devices.clear();
        devices.addAll(list.getDeviceList());
        adapter.notifyDataSetChanged();
    }

    public void openActivity(int type) {
        Intent intent = new Intent(this,WifiChatAcitivty.class);
        intent.putExtra("type",type);
        startActivity(intent);
    }
}
