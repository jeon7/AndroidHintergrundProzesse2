package ch.teko.hintergrundprozesse2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


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

        Intent intent = getIntent();
        ArrayList<Transport> transportsList = processIntent(intent);
        displayTransports(transportsList);
    }

    private ArrayList<Transport> processIntent(Intent intent) {
        Log.d(LOG_TAG, "processIntent() called");

        ArrayList<Transport> transportsList = null;

        if (intent != null) {
            String transportsJsonStr = intent.getExtras().getString("transportsJsonStr");
            Log.d(LOG_TAG, transportsJsonStr);

            transportsList = parseJson(transportsJsonStr);

            //        todo: for test
            for (int i = 0; i < transportsList.size(); i++) {
                Log.d(LOG_TAG, transportsList.get(i).getId());
                Log.d(LOG_TAG, transportsList.get(i).getName());
                Log.d(LOG_TAG, transportsList.get(i).getScore());
                Log.d(LOG_TAG, transportsList.get(i).getCoordinate_type());
                Log.d(LOG_TAG, transportsList.get(i).getCoordinate_x());
                Log.d(LOG_TAG, transportsList.get(i).getCoordinate_y());
                Log.d(LOG_TAG, transportsList.get(i).getDistance());
                Log.d(LOG_TAG, transportsList.get(i).getIcon());
                Log.d(LOG_TAG, String.valueOf(transportsList.get(i).getDrawableId()));
                Log.d(LOG_TAG, " ");
            }
        }

        return transportsList;
    }

    private ArrayList<Transport> parseJson(String transportsJsonStr) {
        Log.d(LOG_TAG, "parseJson() called");
        ArrayList<Transport> transportList = new ArrayList();

        try {
            JSONObject jsonObject = new JSONObject(transportsJsonStr);
            JSONArray jsonArray = jsonObject.getJSONArray("stations");

            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject stationInfo = jsonArray.getJSONObject(i);
                //todo test
                Log.d(LOG_TAG, stationInfo.toString());

                String id = stationInfo.getString("id");
                String name = stationInfo.getString("name");
                String score = stationInfo.getString("score");

                String coordinate_type = stationInfo.getJSONObject("coordinate").getString("type");
                String coordinate_x = stationInfo.getJSONObject("coordinate").getString("x");
                String coordinate_y = stationInfo.getJSONObject("coordinate").getString("y");

                String distance = stationInfo.getString("distance");
                String icon = stationInfo.getString("icon");

                int drawableId;

                // set icon for view
                int drawableIdTrain = R.drawable.train;
                int drawableIdBus = R.drawable.bus;
                int drawableIdTram = R.drawable.tram;
                int drawableIdUnknown = R.drawable.question_mark;

                if(icon.equals("train")) {
                    drawableId = drawableIdTrain;
                } else if(icon.equals("bus")) {
                    drawableId = drawableIdBus;
                } else if(icon.equals("tram")) {
                    drawableId = drawableIdTram;
                } else {
                    drawableId = drawableIdUnknown;
                }

                Transport transport = new Transport(id,name, score, coordinate_type, coordinate_x, coordinate_y,
                        distance, icon, drawableId);
                transportList.add(transport);

//                todo: for test
                Log.d(LOG_TAG, "\nid: " + id +
                        "\nname: " + name +
                        "\nscore: " + score +
                        "\ncoordinate_type: " + coordinate_type +
                        "\ncoordinate_x: " + coordinate_x +
                        "\ncoordinate_y: " + coordinate_y +
                        "\ndistance: " + distance +
                        "\nicon: " + icon +
                        "\ndrawableId: " + drawableId);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return transportList;
    }

    // 리사이클러뷰에 LinearLayoutManager 객체 지정.
    private void displayTransports(ArrayList<Transport> transportsList) {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        MyAdpater myAdpater = new MyAdpater(transportsList);
        recyclerView.setAdapter(myAdpater);
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
