package ch.teko.hintergrundprozesse2;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


public class ResultActivity extends AppCompatActivity {

    private static final String LOG_TAG = "ResultActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreate() called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
    }

    @Override
    protected void onStart() {
        Log.d(LOG_TAG, "onStart() called");
        super.onStart();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.d(LOG_TAG, "onActivityResult() called");
        super.onActivityResult(requestCode, resultCode, data);
//        ArrayList<Transport> transportList = (ArrayList)data.getExtras().getSerializable("transportList");
        //        todo: test
//        for (int i = 0; i < transportList.size(); i++) {
//            Log.d(LOG_TAG, transportList.get(i).getId());
//            Log.d(LOG_TAG, transportList.get(i).getName());
//            Log.d(LOG_TAG, transportList.get(i).getScore());
//            Log.d(LOG_TAG, transportList.get(i).getCoordinate_type());
//            Log.d(LOG_TAG, transportList.get(i).getCoordinate_x());
//            Log.d(LOG_TAG, transportList.get(i).getCoordinate_y());
//            Log.d(LOG_TAG, transportList.get(i).getDistance());
//            Log.d(LOG_TAG, transportList.get(i).getIcon());
//            Log.d(LOG_TAG, " ");
//        }
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
    }

    @Override
    protected void onDestroy() {
        Log.d(LOG_TAG, "onDestroy() called");
        super.onDestroy();
    }
}
