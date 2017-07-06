package leeshun.p2pdirectchat.manager;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

import leeshun.p2pdirectchat.activity.WifiChatAcitivty;
import leeshun.p2pdirectchat.activity.WifiMainAcitvity;

/**
 * Created by leeshun on 2017/7/6.
 */

public class ClientManager extends AsyncTask<String,Void,String>{
    private final String TAG = "P2PChat";
    private final int PORT = 23333;
    private InetAddress address;
    private Socket socket;
    private WifiMainAcitvity acitvity;



    public void setAcitvity(WifiMainAcitvity acitvity) {
        this.acitvity = acitvity;
    }

    public void setAddress(InetAddress address) {
        this.address = address;
    }

    public Socket getSocket() {
        return socket;
    }


       /* @Override
        public void run() {
            socket = new Socket();
            try {
                socket.connect(new InetSocketAddress(address.getHostAddress(),PORT),5000);

                acitvity.openActivity(2);
            } catch (IOException e) {
                Log.e(TAG,"connect exception ",e);
            }
        }*/

    @Override
    protected String doInBackground(String... params) {
        socket = new Socket();
        try {
            socket.bind(null);
            Log.d("Address",address.getHostAddress());
            socket.connect(new InetSocketAddress(address.getHostAddress(),PORT),5000);

            acitvity.openActivity(2);
        } catch (IOException e) {
            Log.e(TAG,"connect exception ",e);
        }
        return null;
    }
}
