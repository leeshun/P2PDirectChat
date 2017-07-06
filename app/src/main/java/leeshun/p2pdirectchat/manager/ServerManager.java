package leeshun.p2pdirectchat.manager;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import leeshun.p2pdirectchat.activity.WifiMainAcitvity;

/**
 * Created by leeshun on 2017/7/6.
 */

public class ServerManager extends AsyncTask<String,Void,String>{
    private final String TAG = "P2PChat";
    private final int PORT = 23333;
    private ServerSocket serverSocket;
    private Socket socket;
    private WifiMainAcitvity acitvity;


    @Override
    protected String doInBackground(String... params) {
        try {
            serverSocket = new ServerSocket(PORT);
            socket = serverSocket.accept();
            acitvity.openActivity(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void setAcitvity(WifiMainAcitvity acitvity) {
        this.acitvity = acitvity;
    }

    public Socket getSocket() {
        return socket;
    }

       /* @Override
        public void run() {
            try {
                socket = serverSocket.accept();
                acitvity.openActivity(1);

            } catch (IOException e) {
                Log.e(TAG,"accept exception ",e);
            }
        }*/
}
