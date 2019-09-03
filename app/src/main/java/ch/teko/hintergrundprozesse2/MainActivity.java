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
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = "MainActivity";
    MyService myService;
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
            unbindService(connection);
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
        Intent intent = new Intent(this, MyService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onResume() {
        Log.d(LOG_TAG, "onResume() called");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d(LOG_TAG, "onPause() called");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d(LOG_TAG, "onStop() called");
        super.onStop();
        if (isBound) {
            unbindService(connection);
            isBound = false;
        }
    }

    @Override
    protected void onDestroy() {
        Log.d(LOG_TAG, "onDestroy() called");
        super.onDestroy();
    }

    public void onButtonOkClicked(View view) {
        Log.d(LOG_TAG, "onButtonOkClicked() called");

        if(isBound) {
            locationUserInput = findViewById(R.id.editText_location);
            myService.getTransportsJson(locationUserInput.getText().toString());

            //            todo:
//                Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
//                intent.putExtra("transportsJsonStr", transportsJsonStr);
//                startActivity(intent);


        } else {
//            locationUserInput = findViewById(R.id.editText_location); //todo for test
//            transportsJsonStr = myService.getTransportsJsonStr(locationUserInput.getText().toString()); //todo for test
//            Log.d(LOG_TAG, transportsJsonStr);  //todo for test
//            Toast.makeText(getApplicationContext(),
//                    "not connected to MyService. please refresh the page", Toast.LENGTH_LONG).show();
        }
    }

    //todo
    // test onButton method
    public void onButtonNumClicked(View view) {
        Log.d(LOG_TAG, "onButtonNumClicked() called");
        if(isBound) {
            int num = myService.getRandom();
            Log.d(LOG_TAG, "data from service = " + num);
            Toast.makeText(getApplicationContext(), "Data from MyService: " + num, Toast.LENGTH_LONG).show();
        } else {
            int num = myService.getRandom();                            //todo for test
            Log.d(LOG_TAG, "data from service = " + num);         //todo for test
            Toast.makeText(getApplicationContext(),
                    "not connected to MyService. please refresh the page", Toast.LENGTH_LONG).show();
        }
    }

    //todo
    // test onButton method
    public void onButtonCityClicked(View view) {
        Log.d(LOG_TAG, "onButtonCityClicked() called");

        if(isBound) {
            String city = myService.getCity();
            Log.d(LOG_TAG, "data from service = " + city);
            Toast.makeText(getApplicationContext(), "Data from MyService: " + city, Toast.LENGTH_LONG).show();
        } else {
            String city = myService.getCity();                          //todo for test
            Log.d(LOG_TAG, "data from service = " + city);        //todo for test
            Toast.makeText(getApplicationContext(),
                    "not connected to MyService. please refresh the page", Toast.LENGTH_LONG).show();
        }
    }

    //todo
    // test onButton method
    public void onButtonUnbindClicked(View view) {
        Log.d(LOG_TAG, "onButtonUnbindClicked() called");
        if(isBound) {
            unbindService(connection);
            isBound = false;
        } else {
            Toast.makeText(getApplicationContext(), "already unbound", Toast.LENGTH_LONG).show();
        }
    }
}
