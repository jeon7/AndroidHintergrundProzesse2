package ch.teko.hintergrundprozesse2;


import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;


public class MyService extends Service {
    private static final String LOG_TAG = "MyService";
    private final String defaultUrl = "http://transport.opendata.ch/v1/locations?query=";
    IBinder myBinder = new MyBinder(); // binder given to client

    private String urlStr;
    String locationUserInput; // todo for test

    //     inner Class used for the client binder
    public class MyBinder extends Binder {
        private static final String LOG_TAG = "MyBinder in MyService";

        public MyBinder(){
            Log.d(LOG_TAG, "MyBinder() created");
        }
        MyService getService() {
            Log.d(LOG_TAG, "getService() called");
            return MyService.this;
        }
    }

    public MyService() {
        Log.d(LOG_TAG, "MyService() created");
    }

    @Override
    public void onCreate() {
        Log.d(LOG_TAG,"onCreate() called");
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(LOG_TAG, "onBind() called");

        //todo later google map
//        Uri geouri = Uri.parse("geo:" + transportList.get(0).getCoordinate_x() + transportList.get(0).getCoordinate_y());
//        Intent geomap = new Intent(Intent.ACTION_VIEW, geouri);
//        geomap.setPackage("com.google.android.apps.maps");

        return myBinder;
    }

    @Override
    // never called, due to bindService() without startService()
    //todo for test => delete
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(LOG_TAG,"onStartCommand() called");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d(LOG_TAG,"onDestroy() called");
        super.onDestroy();
    }

    // Method for client
    //      todo, if user input is not for transportAPI
    public void getTransportsJson(String locationUserInput) {
        Log.d(LOG_TAG, "getTransportsJsonStr() called");
        Log.d(LOG_TAG, "locationUserInput = " + locationUserInput);

//        todo ??
        urlStr = defaultUrl + locationUserInput;

        new Thread(new Runnable() {
            @Override
            public void run() {
                String transportsJsonStr = "transportsJsonStr";
                //todo make connection work
//                try {
//                    InputStream inputStream = new URL(urlStr).openStream();
//                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
//
//                    StringBuffer transportsJsonBuffer = new StringBuffer();
//                    String line;
//                    while ((line = bufferedReader.readLine()) != null) {
//                        transportsJsonBuffer.append(line);
//                    }
//                    transportsJsonStr = transportsJsonBuffer.toString();
//
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                    //todo
//                    transportsJsonStr = "MalformedURLException";
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    //todo
//                    transportsJsonStr = "IOException";
//                } catch (NullPointerException e) {
//                    e.printStackTrace();
//                    //todo
//                    transportsJsonStr = "NullPointerException";
//                }
                Log.d(LOG_TAG, transportsJsonStr);
            }
        }).start();
    }
    // test Method for client
    //todo for test
    int getRandom() {
        return new Random().nextInt(100);
    }

    // test Method for client
    //todo for test
    String getCity() {
        return locationUserInput;
    }
}
