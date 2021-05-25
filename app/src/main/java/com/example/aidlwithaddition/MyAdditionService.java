package com.example.aidlwithaddition;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class MyAdditionService extends Service {
    public MyAdditionService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return aidlInterface;
    }

    IAidlInterface.Stub aidlInterface = new IAidlInterface.Stub() {
        @Override
        public long add(long value1, long value2) throws RemoteException {
            return value1 + value2;
        }
    };
        }

