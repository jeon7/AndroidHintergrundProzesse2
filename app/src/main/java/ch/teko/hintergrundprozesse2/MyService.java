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
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

public class MyService extends Service {
    private static final String LOG_TAG = "MyService";
    private final String defaultUrl = "https://transport.opendata.ch/v1/locations?query=";
    IBinder myBinder = new MyBinder(); // binder given to client

    String urlStr; // for communication thread
    String transportsJsonStr = "transportsJsonStr";

    //     inner Class used for the client binder
    public class MyBinder extends Binder {
        String LOG_TAG = "MyBinder in MyService";

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
    public IBinder onBind(Intent intent) {
        Log.d(LOG_TAG, "onBind() called");
        return myBinder;
    }

    // Method for client
    public String getTransportsJson(String locationUserInput) {
        Log.d(LOG_TAG, "getTransportsJsonStr() called");
        Log.d(LOG_TAG, "locationUserInput = " + locationUserInput);

        urlStr = defaultUrl + locationUserInput;
        Log.d(LOG_TAG, "url = " + urlStr);


        Thread communicationThread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    InputStream inputStream = new URL(urlStr).openStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

                    StringBuffer transportsJsonBuffer = new StringBuffer();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        transportsJsonBuffer.append(line);
                    }

                    transportsJsonStr = transportsJsonBuffer.toString();

                }catch(UnknownHostException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                Log.d(LOG_TAG, transportsJsonStr);
            }
        });

         communicationThread.start();

         // *** wait until the communicationThread to get transportsJsonStr from url
        try {
            communicationThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return transportsJsonStr;
    }
}
