package leeshun.p2pdirectchat.manager;

import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import leeshun.p2pdirectchat.activity.WifiMainAcitvity;
import leeshun.p2pdirectchat.handler.ChatHandler;
import leeshun.p2pdirectchat.receiver.WifiBroadReceiver;

/**
 * Created by leeshun on 2017/7/6.
 */

public class ChatManager implements Runnable {
    private Socket socket;
    private Handler handler;
    private int type;
    private InputStream inputStream;

    public ChatManager(int type, Handler handler) {
        this.type = type;
        this.handler = handler;
    }


    @Override
    public void run() {
        if(type == 1) {
            socket =  WifiBroadReceiver.serverManager.getSocket();
        } else if (type == 2){
            socket = WifiBroadReceiver.clientManager.getSocket();
        } else {
            return;
        }
        Log.d("Socket",socket.toString());
        byte[] buffer = new byte[1024];
        int bytes;
        try {
            inputStream = socket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                bytes = inputStream.read(buffer);
                if(bytes == -1) {
                    return;
                }
                handler.obtainMessage(ChatHandler.MESSAGE_READ,bytes,-1,buffer).sendToTarget();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
