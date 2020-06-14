package com.yxm.customview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.ref.WeakReference;

/**
 * Created by yxm on 2020/3/28
 *
 * @function
 */
public class HandlerActivity extends AppCompatActivity {

    private MyHandler mHandler = new MyHandler(this);


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x11:
                    //获取子线程传过来的数据，需要强转类型
                    String result = (String) msg.obj;
                    //更新UI操作
                    break;
                default:
                    break;
            }
        }
    };

    private TextView mTextView;

    private void sendMessageInWorkThread() {
        new Thread(() -> {
            Message message = mHandler.obtainMessage(0x11);
            message.obj = "123";
            //通过子线程发送消息
            mHandler.sendMessage(message);
        }).start();
    }

    private Handler handler1;

    private void createHandlerInWorkThread(){
        new Thread(() ->{
            Looper.prepare();
            Handler handler1 = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    Toast.makeText(HandlerActivity.this, (String)msg.obj, Toast.LENGTH_SHORT).show();
                }
            };
        }).start();

        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = handler.obtainMessage();
            message.obj = "456";
            handler1.sendMessage(message);
        });
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, HandlerActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_test);
        mTextView = findViewById(R.id.text);
        createHandlerInWorkThread();
    }

    public static class MyHandler extends Handler {
        private final WeakReference<HandlerActivity> mActivity;

        private MyHandler(HandlerActivity activity) {
            this.mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            HandlerActivity activity = mActivity.get();
            if (activity != null) {
                switch (msg.what) {
                    case 0x11:
                        //获取子线程传过来的数据，需要强转类型
                        String result = (String) msg.obj;
                        //更新UI
                        activity.mTextView.setText(result);
                        break;
                    default:
                        break;
                }
            }
        }
    }
}
