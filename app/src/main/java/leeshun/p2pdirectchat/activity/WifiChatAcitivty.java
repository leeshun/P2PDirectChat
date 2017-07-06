package leeshun.p2pdirectchat.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import leeshun.p2pdirectchat.R;
import leeshun.p2pdirectchat.adapter.MessageAdapter;
import leeshun.p2pdirectchat.handler.ChatHandler;
import leeshun.p2pdirectchat.manager.ChatManager;
import leeshun.p2pdirectchat.manager.WriteManager;

public class WifiChatAcitivty extends Activity {

    private ListView listView;
    private MessageAdapter adapter;
    private List<String> messages;
    private TextView lineView;
    private Button sendButton;
    private ChatHandler handler;
    private ChatManager manager;
    private WriteManager write;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wifi_chat_acitivty);

        int type = getIntent().getIntExtra("type", -1);
        handler = new ChatHandler();
        handler.setAcitivty(this);

        manager = new ChatManager(type,handler);
        new Thread(manager).start();
        write = new WriteManager(type);
        new Thread(write).start();


        listView = (ListView) findViewById(R.id.chat_list);
        lineView = (TextView) findViewById(R.id.txtChatLine);
        sendButton = (Button) findViewById(R.id.button1);

        messages = new ArrayList<>();
        adapter = new MessageAdapter(messages,this);
        listView.setAdapter(adapter);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                write.add(lineView.getText().toString());
                pushMessage("Me:" + lineView.getText().toString());
                lineView.setText("");
            }
        });
    }

    public void pushMessage(String readMessage) {
        messages.add(readMessage);
        adapter.notifyDataSetChanged();
    }
}
