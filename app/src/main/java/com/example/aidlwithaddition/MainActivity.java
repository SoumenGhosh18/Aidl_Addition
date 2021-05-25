package com.example.aidlwithaddition;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    IAidlInterface aidlInterface;
    AdditionServiceConnection mconnection;
    EditText value1;
    EditText value2;
    long v1, v2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        doBindService();
    }

    private void doBindService() {
        mconnection = new AdditionServiceConnection();
        Intent intent = new Intent();
        intent.setClassName("com.example.aidlwithaddition", com.example.aidlwithaddition.MyAdditionService.class.getName());
     bindService(intent,mconnection,BIND_AUTO_CREATE);
    }

    public void myClick(View view) {
        check();
    }
    private void check() {
        TextView textViewresult = findViewById(R.id.textView);
        value1 = findViewById(R.id.ed1);
        value2 = findViewById(R.id.ed2);

        v1 = Integer.parseInt(value1.getText().toString());
        v2 = Integer.parseInt(value2.getText().toString());

        long fnl = 0;
        boolean status = false;
        if (value1.getText().toString().isEmpty() || value2.getText().toString().isEmpty())
        {
            Toast.makeText(getApplicationContext(),"plz enter all the field",Toast.LENGTH_LONG).show();
            status=true;
        }
        if (status) {


            if (value1.getText().toString().length() >=9 ||value2.getText().toString().length() >=9 ) {
                textViewresult.setText(" " + "please enter less value");
            } else {
                try {
                    fnl = aidlInterface.add(v1, v2);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                textViewresult.setText(" " + fnl);
            }
        }
        return;
    }
    public class AdditionServiceConnection implements ServiceConnection{

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

            aidlInterface = IAidlInterface.Stub.asInterface(iBinder);
            Toast.makeText(MainActivity.this,"Its connected",Toast.LENGTH_LONG).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

            aidlInterface = null;
            Toast.makeText(MainActivity.this,"Its Disconnected",Toast.LENGTH_LONG).show();

        }
    }
}