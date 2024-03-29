package ch.teko.hintergrundprozesse2;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = "MainActivity";
    MyService myService;
    Intent serviceIntent;
    boolean isBound = false;
    EditText locationUserInput;

    public ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder binder) {
            Log.d(LOG_TAG, "onServiceConnected() called from ServiceConnection object");
            myService = ((MyService.MyBinder)binder).getService();
            isBound = true;
            Log.d(LOG_TAG, "isBound = " + isBound);
        }

        @Override
        public void onServiceDisconnected(ComponentName className) {
            Log.d(LOG_TAG, "onServiceDisconnected() called from ServiceConnection object");
//            unbindService(this);
            myService = null;
            isBound = false;
            Log.d(LOG_TAG, "isBound = " + isBound);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreate() called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        Log.d(LOG_TAG, "onStart() called");
        super.onStart();
        serviceIntent = new Intent(this, MyService.class);
        bindService(serviceIntent, connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        Log.d(LOG_TAG, "onStop() called");
        super.onStop();
        if (isBound) {
            unbindService(connection); // MyService, onDestroy() called
            isBound = false;
        }
    }

    // process user input
    public void onButtonOkClicked(View view) {
        Log.d(LOG_TAG, "onButtonOkClicked() called");
        Log.d(LOG_TAG, "isBound = " + isBound);

        if(!isBound) {
            bindService(serviceIntent, connection, Context.BIND_AUTO_CREATE);
        }

        // get transportsJsonStr from user input from bound Service
        locationUserInput = findViewById(R.id.editText_location);
        String transportsJsonStr = myService.getTransportsJson(locationUserInput.getText().toString());
        Log.d(LOG_TAG, "transportsJsonStr from MyService: " + transportsJsonStr);

        // send transportsJasonStr to ResultActivity
        Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
        intent.putExtra("transportsJsonStr", transportsJsonStr);
        startActivity(intent);
    }
}
