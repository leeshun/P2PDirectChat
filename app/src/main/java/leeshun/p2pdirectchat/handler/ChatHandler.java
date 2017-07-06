package leeshun.p2pdirectchat.handler;

import android.os.Handler;
import android.os.Message;

import leeshun.p2pdirectchat.activity.WifiChatAcitivty;

/**
 * Created by leeshun on 2017/7/6.
 */

public class ChatHandler extends Handler {
    public static final int MESSAGE_READ = 0x400 + 2;

    private WifiChatAcitivty acitivty;

    public void setAcitivty(WifiChatAcitivty acitivty) {
        this.acitivty = acitivty;
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case MESSAGE_READ:
                byte[]  readBuf = (byte[]) msg.obj;
                String readMessage = new String(readBuf, 0, msg.arg1);
                acitivty.pushMessage(readMessage);
                break;
        }
    }

}
