package ch.teko.hintergrundprozesse2;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;


public class ResultActivity extends AppCompatActivity {

    private static final String LOG_TAG = "ResultActivity";
    private RecyclerView recyclerView;
    private MyAdpater myAdapter;
    private RecyclerView.LayoutManager layoutManager;
    public ArrayList<Transport> transportsList;

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
        processIntent(intent);
    }

    private void processIntent(Intent intent) {
        Log.d(LOG_TAG, "processIntent() called");

        if (intent != null) {
            String transportsJsonStr = intent.getExtras().getString("transportsJsonStr");
            Log.d(LOG_TAG, transportsJsonStr);

            parseJson(transportsJsonStr);
            displayTransports(transportsList);

            myAdapter.setOnItemClickListner(new MyAdpater.OnItemClickListener() {
                @Override
                public void onItemClick(View v,int pos){

                    Transport transport = transportsList.get(pos);
                    String name = transport.getName();
                    String coordinate_x = transport.getCoordinate_x();
                    String coordinate_y = transport.getCoordinate_y();

                    Log.d(LOG_TAG, "name: " + name);
                    Log.d(LOG_TAG, "coordinate_x: " + coordinate_x);
                    Log.d(LOG_TAG, "coordinate_y: " + coordinate_y);

                    Uri gmmIntentUri = Uri.parse("geo:" + coordinate_x + "," + coordinate_y + "?q=" +
                                                Uri.encode(name));
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    if (mapIntent.resolveActivity(getPackageManager()) != null &&
                        coordinate_x != null &&
                        coordinate_y != null) {
                        startActivity(mapIntent);
                    }
                }
            });
        }
    }

    private void parseJson(String transportsJsonStr) {
        Log.d(LOG_TAG, "parseJson() called");
        transportsList = new ArrayList();

        try {
            JSONObject jsonObject = new JSONObject(transportsJsonStr);
            JSONArray jsonArray = jsonObject.getJSONArray("stations");

            // user input is wrong. no transport information from the API
            if(jsonArray.length() == 0){
                Transport transport = new Transport("your input is wrong. please go back and enter a valid location.");
                transportsList.add(transport);
            }

            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject stationInfo = jsonArray.getJSONObject(i);
                Log.d(LOG_TAG, stationInfo.toString());

                String id = stationInfo.getString("id");
                String name = stationInfo.getString("name");
                String score = stationInfo.getString("score");

                String coordinate_type = stationInfo.getJSONObject("coordinate").getString("type");
                String coordinate_x = stationInfo.getJSONObject("coordinate").getString("x");
                String coordinate_y = stationInfo.getJSONObject("coordinate").getString("y");

                String distance = stationInfo.getString("distance");
                String icon;

                // set icon for view
                int drawableIdTrain = R.drawable.train;
                int drawableIdBus = R.drawable.bus;
                int drawableIdTram = R.drawable.tram;
                int drawableIdUnknown = R.drawable.question_mark;
                int drawableId;

                if(stationInfo.has("icon")) {
                    icon = stationInfo.getString("icon");

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
                    transportsList.add(transport);

                    Log.d(LOG_TAG, "\nid: " + id +
                            "\nname: " + name +
                            "\nscore: " + score +
                            "\ncoordinate_type: " + coordinate_type +
                            "\ncoordinate_x: " + coordinate_x +
                            "\ncoordinate_y: " + coordinate_y +
                            "\ndistance: " + distance +
                            "\nicon: " + icon +
                            "\ndrawableId: " + drawableId);
                } else {
                    icon = "no json object";
                    drawableId = drawableIdUnknown;

                    Transport transport = new Transport(id,name, score, coordinate_type, coordinate_x, coordinate_y,
                            distance, icon, drawableId);
                    transportsList.add(transport);

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
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // 리사이클러뷰에 LinearLayoutManager 객체 지정.
    private void displayTransports(ArrayList<Transport> transportsList) {
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        myAdapter = new MyAdpater(transportsList);
        recyclerView.setAdapter(myAdapter);
    }

}
