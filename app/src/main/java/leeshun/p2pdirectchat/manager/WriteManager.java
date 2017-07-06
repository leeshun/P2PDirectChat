package leeshun.p2pdirectchat.manager;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import leeshun.p2pdirectchat.receiver.WifiBroadReceiver;

/**
 * Created by leeshun on 2017/7/7.
 */

public class WriteManager implements Runnable {
    private Socket socket;
    private int type;
    private OutputStream outputStream;
    private BlockingQueue<String> queue;

    public WriteManager(int type) {
        this.type = type;
        queue = new LinkedBlockingQueue<>();
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

        try {
            outputStream = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            if(queue.size() > 0) {
                try {
                    outputStream.write(queue.take().toString().getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void add(String string) {
        queue.add(string);
    }
}
