package tentrep.karinderya;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
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
import java.util.HashMap;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback ,GoogleMap.OnMarkerClickListener {
    private MarkerOptions options = new MarkerOptions();
    private ArrayList<LatLng> latlngs = new ArrayList<>();
    HashMap<String, String> markerMap = new HashMap<String, String>();
    String globe = null;
    JSONObject json = null;
    String str = "";
    HttpResponse response;
    Context context;
    JSONArray jArray;
    String[] array1 = new String[]{};
    String[] array3 = new String[]{};
    String[] array5 = new String[]{};
    String[] array7 = new String[]{};

    Bundle bundle = new Bundle();
    //String[] array7 = new String[]{};
    public int num=0;
    public int index3=0;
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
          //HttpPost myConnection = new HttpPost("http://masarapp.azurewebsites.net/index.php");
           HttpPost myConnection = new HttpPost("http://192.168.1.12:80/masarapp/send-data.php");
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
                String[] array8 = new String[total];

                for(int i=0;i<total;i++) {
                    json = jArray.getJSONObject(i);
                    array2[i] = json.getString("karinderya_lat");
                    array4[i] = json.getString("karinderya_long");
                    array6[i] = json.getString("karinderya_name");
                    array8[i] = json.getString("karindeya_id");

                }
                array1=array2.clone();
                array3=array4.clone();
                array5=array6.clone();
                array7=array8.clone();

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
        googleMap.setOnMarkerClickListener(this);


        LatLng APC = new LatLng(14.531037, 121.021453);
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

        mMap.setOnMarkerClickListener(this);
    }
    @Override
    public boolean onMarkerClick(Marker loc) {
        Intent intent = new Intent(this, stars.class);

       for (index3=0; index3 < array1.length; index3++) {
            if (loc.getTitle().equals(array5[index3])){
                bundle.putString("id", array7[index3]);
              /*  bundle.putString("star1", array7[index3]);
                bundle.putString("star2", array9[index3]);
                bundle.putString("star3", array11[index3]);*/
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }

        return true;
    }
}


