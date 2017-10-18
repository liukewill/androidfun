package com.example.think.androidfun.socket;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.think.androidfun.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketClientActivity extends AppCompatActivity {

    private TextView tv_msg = null;
    private EditText ed_msg = null;
    private Button btn_send = null;
    // private Button btn_login = null;
    private static final String HOST = "172.16.76.153";
    private static final int PORT = 9999;
    private Socket socket = null;
    private BufferedReader in = null;
    private PrintWriter out = null;
    private String content = "";
    //接收线程发送过来信息，并用TextView显示
    public Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            tv_msg.setText(content);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket_client);

        tv_msg = (TextView) findViewById(R.id.TextView);
        ed_msg = (EditText) findViewById(R.id.EditText01);
        btn_send = (Button) findViewById(R.id.Button02);
        btn_send.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });



        new Thread(new Runnable() {
            @Override
            public void run() {
                connectServer();
            }
        }).start();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        new Thread(new Runnable() {
            @Override
            public void run() {
                getMsg();
            }
        }).start();
    }

    private void connectServer(){
        try {
            socket = new Socket(HOST, PORT);
            in = new BufferedReader(new InputStreamReader(socket
                    .getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                    socket.getOutputStream())), true);
            Log.e("lk","connect-success"+socket.getInetAddress());
            Log.e("lk","connect-isClosed"+socket.isClosed());
            Log.e("lk","connect-isConnected"+socket.isConnected());

        } catch (IOException ex) {
            ex.printStackTrace();
            Log.e("lk","connect-isConnected"+ex.toString());
            ShowDialog("login exception" + ex.getMessage());
        }
    }

    private void getMsg(){
        InputStream is=null;
        BufferedReader br=null;
        try{
            is=socket.getInputStream();
            br=new BufferedReader(new InputStreamReader(is));
            String line=null;
            while((line=br.readLine())!=null){
                content=line+content+"\n";
                mHandler.sendMessage(mHandler.obtainMessage());
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 读取服务器发来的信息，并通过Handler发给UI线程
     */
    private void getMessage(){
        Log.e("lk","getMessage-while-in");
        try {
            while (true) {
                Log.e("lk","connect-isClosed"+socket.isClosed());
                if (!socket.isClosed()) {
                    Log.e("lk","socket-connected:"+socket.isConnected());
                    if (socket.isConnected()) {
                        Log.e("lk","socket-isInputShutdown:"+socket.isInputShutdown());
                        if (!socket.isInputShutdown()) {
                            Log.e("lk","in-readline:"+in.readLine());
                            if ((content = in.readLine()) != null) {
                                Log.e("lk","socket-readline!=null:"+content);
                                content += "\n";
                                mHandler.sendMessage(mHandler.obtainMessage());
                                Log.e("lk","sendMessage");
                            } else {

                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            Log.e("lk","getMessage-error"+e.toString());
            e.printStackTrace();
        }
    }


    private void sendMessage(){
        String msg = ed_msg.getText().toString();
        if (socket.isConnected()) {
            if (!socket.isOutputShutdown()) {
                out.println(msg);
            }
        }
    }
    /**
     * 如果连接出现异常，弹出AlertDialog！
     */
    public void ShowDialog(String msg) {
        new AlertDialog.Builder(this).setTitle("notification").setMessage(msg)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }


}
