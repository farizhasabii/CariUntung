package com.example.fariz.cariuntung;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Maps extends FragmentActivity implements OnMapReadyCallback {
    String myLati="-6.189287",myLongi="106.768952",myPosisi="myPosisi",tujuan,jarak;
    private GoogleMap mMap;

    String ip="";

    private ProgressDialog pDialog;
    JSONParser jParser = new JSONParser();
    JSONArray myJSON = null;

    ArrayList<HashMap<String, String>> arrayList;
    private static final String TAG_SUKSES = "sukses";
    private static final String TAG_record = "record";

    private static final String TAG_kode_lokasi = "kode_lokasi";
    private static final String TAG_jenis_lokasi= "jenis_lokasi";
    private static final String TAG_nama_lokasi = "nama_lokasi";
    private static final String TAG_k1 = "k1";
    private static final String TAG_k2 = "k2";
    private static final String TAG_kategori = "kategori";
    private static final String TAG_k3 = "k3";
    private static final String TAG_k4 = "k4";
    private static final String TAG_k5 = "k5";
    private static final String TAG_k6 = "k6";
    private static final String TAG_k7 = "k7";
    private static final String TAG_k8 = "k8";
    private static final String TAG_latitude = "latitude";
    private static final String TAG_longitude = "longitude";
    private static final String TAG_alamat = "alamat";
    private static final String TAG_gambar = "gambar";
    private static final String TAG_keterangan= "keterangan";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);



        Intent io = this.getIntent();

      //  myLati=io.getStringExtra("myLati");
        //myLongi=io.getStringExtra("myLongi");
        //myPosisi=io.getStringExtra("myPosisi");


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);










    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        LatLng pusat = new LatLng(Double.parseDouble(myLati), Double.parseDouble(myLongi));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pusat, 11));


        arrayList = new ArrayList<HashMap<String, String>>();
        ip=jParser.getIP();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build();
        StrictMode.setThreadPolicy(policy);


//		new load().execute();

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        JSONObject json = jParser.makeHttpRequest(ip+"lokasi/lokasi_show.php", "GET", params);
        Log.d("show: ", json.toString());
        try {
            int sukses = json.getInt(TAG_SUKSES);
            if (sukses == 1) {
                myJSON = json.getJSONArray(TAG_record);
                for (int i = 0; i < myJSON.length(); i++) {
                    JSONObject c = myJSON.getJSONObject(i);
                    String kode_lokasi= c.getString(TAG_kode_lokasi);
                    String kategori= c.getString(TAG_kategori);
                    String nama_lokasi = c.getString(TAG_nama_lokasi);
                    String latitude = c.getString(TAG_latitude);
                    String longitude = c.getString(TAG_longitude);


                    LatLng poss = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                    mMap.addMarker(new MarkerOptions().position(poss).title(nama_lokasi));
                  //  mMap.moveCamera(CameraUpdateFactory.newLatLng(poss));


                }
            } else {
                Intent i = new Intent(getApplicationContext(),lokasi.class);
                i.putExtra("pk", "");
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        }
        catch (JSONException e) {e.printStackTrace();}



    }
}
