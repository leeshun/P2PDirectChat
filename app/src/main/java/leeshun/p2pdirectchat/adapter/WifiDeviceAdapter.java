package leeshun.p2pdirectchat.adapter;

import android.content.Context;
import android.net.wifi.p2p.WifiP2pDevice;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by leeshun on 2017/7/6.
 */

public class WifiDeviceAdapter extends BaseAdapter {
    private List<WifiP2pDevice> list;
    private Context context;

    public WifiDeviceAdapter(List<WifiP2pDevice> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView = new TextView(context);
        textView.setText(list.get(position).deviceName + "\n"
                + list.get(position).deviceAddress);
        textView.setTextSize(28);
        return textView;
    }
}
