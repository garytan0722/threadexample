package com.genglun.main_thread;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tv1,tv2,tv3;
    HandlerThread handlerthread;
    Handler handler2;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==1){
                tv1.setText(msg.arg1+"");
            }
            if(msg.what==2){
                tv2.setText(msg.arg1+"");
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv1=(TextView)findViewById(R.id.textView);
        tv2=(TextView)findViewById(R.id.textView2);
        tv3=(TextView)findViewById(R.id.textView3);
        Button butn=(Button)findViewById(R.id.button);
        Button butn1=(Button)findViewById(R.id.button2);
        Button butn2=(Button)findViewById(R.id.button3);
        butn.setOnClickListener(this);
        butn1.setOnClickListener(this);
        butn2.setOnClickListener(this);
        //handlerthread
        handlerthread=new HandlerThread("123456");
        handlerthread.start();
        handler2=new Handler(handlerthread.getLooper());//給handler2對應的Looper

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button:
                Workthread thread=new Workthread();
                thread.start();
                break;
            case R.id.button2:
                handler2.post(new Myrun());
                break;
            case R.id.button3:
                AsyncTask task =new AsyncTask();
                task.execute();
                break;
        }

    }
    class Workthread extends Thread{
        @Override
        public void run() {
            super.run();
            for(int i=0;i<10000;i++){
                Message msg=handler.obtainMessage();//在原有的queue裡找現有的msg
                msg.what=1;
                msg.arg1=i;
                handler.sendMessage(msg);
                Log.d("mylog:","Number:"+i);
            }
        }
    }
    class Myrun implements Runnable{

        @Override
        public void run() {
            for(int i=0;i<10000;i++){
                Message msg=handler.obtainMessage();//在原有的queue裡找現有的msg
                msg.what=2;
                msg.arg1=i;
                handler.sendMessage(msg);
                Log.d("mylog2:","Number:"+i);
            }
        }
    }
    class AsyncTask extends android.os.AsyncTask<Void,Integer,Void>//1.帶進入async的參數 2.執行中的值 3.執行結束後回傳得值
    {


        @Override
        protected Void doInBackground(Void... voids) {
            for(int i=0;i<1000;i++){
                publishProgress(i);
            }
            return null;
        }
        @Override
        protected void onPreExecute() {//前置作業
            super.onPreExecute();
        }
        @Override
        protected void onProgressUpdate(Integer... values) {//執行中
            super.onProgressUpdate(values);
                        tv3.setText(values[0] + "");
        }

        @Override
        protected void onPostExecute(Void aVoid) {//執行完
            super.onPostExecute(aVoid);
        }
    }
}
