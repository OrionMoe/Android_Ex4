package cn.edu.hqu.cst.android.lzt.ex4;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{

    BindService.MyBinder binder;
    ServiceConnection conn=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            System.out.println("--Service Connected--");
            binder=(BindService.MyBinder) service;
            TextView txtServiceStatus = findViewById(R.id.txtstatus);
            txtServiceStatus.setText("Service Connected");
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
            System.out.println("--Service Disconnected--");
            TextView txtServiceStatus = findViewById(R.id.txtstatus);
            txtServiceStatus.setText("Service Disconnected");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnBindService = findViewById(R.id.bind);
        Button btnUnbindService = findViewById(R.id.unbind);
        Button btnGetStatus = findViewById(R.id.status);

        TextView txtServiceStatus = findViewById(R.id.txtstatus);

        btnBindService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(MainActivity.this,BindService.class);
                    bindService(intent,conn, Service.BIND_AUTO_CREATE);
                    txtServiceStatus.setText("Service Bound");
            }
        });
        btnUnbindService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(MainActivity.this,BindService.class);
                    unbindService(conn);
                    txtServiceStatus.setText("Service Unbound");
            }
        });
        btnGetStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(MainActivity.this,BindService.class);
                    txtServiceStatus.setText("count: " + binder.getCount());
            }
        });
    }
}