package com.example.fariz.cariuntung;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Menuutama extends AppCompatActivity {
String kode_customer,nama_customer,email;
    String	myLati="-6.353370";
    String	myLongi="106.832349";
    String myPosisi="Dufan ancol";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menuutama);

        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean Registered = sharedPref.getBoolean("Registered", false);
        if (!Registered){
            finish();
        }else {
            kode_customer = sharedPref.getString("kode_customer", "");
            nama_customer = sharedPref.getString("nama_customer", "");
            email = sharedPref.getString("email", "");
        }
        Log.v("test", kode_customer);




        ImageView   profil=(ImageView) findViewById(R.id.btn_profil);
        profil.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
               Intent i = new Intent(Menuutama.this,profil.class);
                i.putExtra("kode_customer",kode_customer);

               startActivity(i);
            }});

        TextView profil1=(TextView) findViewById(R.id.txt_profil);
        profil1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent i = new Intent(Menuutama.this,profil.class);
                i.putExtra("kode_customer",kode_customer);
                startActivity(i);
            }});



        ImageView   map=(ImageView) findViewById(R.id.btn_map);
        map.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent i = new Intent(Menuutama.this,Maps.class);
                i.putExtra("myLati", myLati);
                i.putExtra("myLongi", myLongi);
                i.putExtra("myPosisi", myPosisi);
                startActivity(i);
            }});
        TextView map1=(TextView) findViewById(R.id.txtmap);
        map1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent i = new Intent(Menuutama.this,Maps.class);
                i.putExtra("kode_customer",kode_customer);
                startActivity(i);
            }});



        ImageView   logout=(ImageView) findViewById(R.id.btn_exit);
        logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent i = new Intent(Menuutama.this,Login.class);
                finish();
                startActivity(i);





            }});
        ImageView   arsip=(ImageView) findViewById(R.id.btn_arsip);
        arsip.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent i = new Intent(Menuutama.this,submenu_detaildata.class);
                startActivity(i);
            }});
        TextView arsip1=(TextView) findViewById(R.id.txt_arsip);
        arsip1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent i = new Intent(Menuutama.this,submenu_detaildata.class);
                i.putExtra("kode_customer",kode_customer);
                startActivity(i);
            }});




        ImageView   search=(ImageView) findViewById(R.id.btn_search);
        search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent i = new Intent(Menuutama.this,Cari_Tempat.class);
                startActivity(i);
            }});

        TextView cari=(TextView) findViewById(R.id.txt_cari);
        cari.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent i = new Intent(Menuutama.this,Cari_Tempat.class);
                i.putExtra("kode_customer",kode_customer);
                startActivity(i);
            }});


        ImageView   about=(ImageView) findViewById(R.id.btn_about);
        about.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent i = new Intent(Menuutama.this,about.class);
                startActivity(i);
            }});

        TextView about1=(TextView) findViewById(R.id.txt_about);
        about1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent i = new Intent(Menuutama.this,about.class);
                i.putExtra("kode_customer",kode_customer);
                startActivity(i);
            }});


        LocationManager locationManager;
        String context = Context.LOCATION_SERVICE;
        locationManager = (LocationManager)getSystemService(context);

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        String provider = locationManager.getBestProvider(criteria, true);


    }

    private final LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            updateWithNewLocation(location);
        }

        public void onProviderDisabled(String provider){
            updateWithNewLocation(null);
        }

        public void onProviderEnabled(String provider){ }
        public void onStatusChanged(String provider, int status,
                                    Bundle extras){ }
    };

    private void updateWithNewLocation(Location location) {
        double latitude=Double.parseDouble(myLati);
        double longitude=Double.parseDouble(myLongi);
        String addressString = "No address found";

        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            Geocoder gc = new Geocoder(this, Locale.getDefault());
            try {
                List<Address> addresses = gc.getFromLocation(latitude, longitude, 1);
                StringBuilder sb = new StringBuilder();
                if (addresses.size() > 0) {
                    Address address = addresses.get(0);

                    for (int i = 0; i < address.getMaxAddressLineIndex(); i++)
                        sb.append(address.getAddressLine(i)).append("\n");

                    sb.append(address.getLocality()).append("\n");
                    sb.append(address.getPostalCode()).append("\n");
                    sb.append(address.getCountryName());
                }
                addressString = sb.toString();
            } catch (IOException e) {}
        } else {
            myLati="-6.353370";
            myLongi="106.832349";
            addressString="Lp2m Aray Jkt";
        }

        myPosisi=addressString;
        myLati=String.valueOf(latitude);
        myLongi=String.valueOf(longitude);


    }

//        ImageView   kuliner=(ImageView)findViewById(R.id.kuliner);
//        kuliner.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View arg0) {
//                Intent i = new Intent(Menuutama.this,LokasiList.class);
//                startActivity(i);
//            }});
//
//        ImageView   kategori=(ImageView)findViewById(R.id.kategori);
//        kategori.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View arg0) {
//                Intent i = new Intent(Menuutama.this,PenilaianList.class);
//                startActivity(i);
//            }});
//
//        ImageView   rekomendasi=(ImageView)findViewById(R.id.rekomendasi);
//        rekomendasi.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View arg0) {
//                Intent i = new Intent(Menuutama.this,penilaiandetailList.class);
//                startActivity(i);
//            }});
    }

