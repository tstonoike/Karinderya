package tentrep.karinderya;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private MarkerOptions options = new MarkerOptions();
    private ArrayList<LatLng> latlngs = new ArrayList<>();
    JSONObject json = null;
    String str = "";
    HttpResponse response;
    Context context;
    JSONArray jArray;
    String[] array1 = new String[]{};
    String[] array3 = new String[]{};
    String[] array5 = new String[]{};
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new GetTextViewData(context).execute();
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    private class GetTextViewData extends AsyncTask<Void, Void, Void> {
        public Context context;

        public GetTextViewData(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            HttpClient myClient = new DefaultHttpClient();
           HttpPost myConnection = new HttpPost("http://masarapp.azurewebsites.net/index.php");
          //  HttpPost myConnection = new HttpPost("http://127.0.0.1/masarapp/send-data.php");
            try {
                response = myClient.execute(myConnection);
                str = EntityUtils.toString(response.getEntity(), "UTF-8");

            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            try {

                jArray = new JSONArray(str);
                int total=jArray.length();
                String[] array2 = new String[total];
                String[] array4 = new String[total];
                String[] array6 = new String[total];
                for(int i=0;i<total;i++) {
                    json = jArray.getJSONObject(i);
                    array2[i] = json.getString("karinderya_lat");
                    array4[i] = json.getString("karinderya_long");
                    array6[i] = json.getString("karinderya_name");

                }
                array1=array2.clone();
                array3=array4.clone();
                array5=array6.clone();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng APC = new LatLng(14.531037, 121.021453);
        mMap.addMarker(new MarkerOptions().position(APC).title("Marker in APC"));

        for(int index=0; index<array1.length; index++)
       {
        latlngs.add(new LatLng(Float.valueOf(array1[index]), Float.valueOf(array3[index])));
        }
        for (int index=0; index<array1.length; index++)
        {
            options.position(latlngs.get(index));
            options.title(array5[index]);
            googleMap.addMarker(options);


        }

        mMap.moveCamera(CameraUpdateFactory.newLatLng(APC));
    }
}
