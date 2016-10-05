package sample.com.earthquake;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import adapter.EarthquakeAdapter;
import model.EarthQuakeJsonAPI;
import model.features;
import okhttp3.OkHttpClient;
import rest.EarthquakeRest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG= MainActivity.class.getName();
    private static final String BASE_URL= "http://earthquake.usgs.gov";
    private ArrayList<features> earthquakelist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(LOG_TAG,"before called api");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient())
                .build();
        //get shared preference values
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        String minMagnitude=sharedPreferences.getString(getString(R.string.settings_min_magnitude_key),
                getString(R.string.settings_min_magnitude_default));
        String orderby= sharedPreferences.getString(getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default));

        EarthquakeRest earthquakeRest=retrofit.create(EarthquakeRest.class);

        Call<EarthQuakeJsonAPI> call= earthquakeRest.loadEartquakes("geojson",orderby,minMagnitude);
        Log.d(LOG_TAG,"just called api");
        call.enqueue(new Callback<EarthQuakeJsonAPI>() {
            @Override
            public void onResponse(Call<EarthQuakeJsonAPI> call, Response<EarthQuakeJsonAPI> response) {
                Log.d(LOG_TAG, "inside response");
                EarthQuakeJsonAPI earthQuakeJsonAPI = response.body();
                earthquakelist = earthQuakeJsonAPI.getEarthQuakeList();
                updateUI();
            }

            @Override
            public void onFailure(Call<EarthQuakeJsonAPI> call, Throwable t) {

            }
        });

    }
   void updateUI(){
       ListView listView= (ListView) findViewById(R.id.list);
       EarthquakeAdapter earthquakeAdapter=new EarthquakeAdapter(this,earthquakelist);
       listView.setAdapter(earthquakeAdapter);
       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               features feature=earthquakelist.get(position);
               features.properties earthquake=feature.getProperties();
               Uri earthquakeUri= Uri.parse(earthquake.getUrl());
               Intent websiteIntent= new Intent(Intent.ACTION_VIEW,earthquakeUri);
               startActivity(websiteIntent);
           }
       });
   }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
