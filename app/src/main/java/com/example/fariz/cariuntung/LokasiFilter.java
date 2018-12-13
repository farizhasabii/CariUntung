package com.example.fariz.cariuntung;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class LokasiFilter extends Activity {
    String pilih="";
    String[]arKode;String[]arKode2;
    int[]arCK;
    String[]arNama;
    String[]arJenis;
    int[]arGB;
    int jd=0;
String gabs="";
    String ip="";
String nama,JE,KA,LB,LT,HA;
    private ProgressDialog pDialog;
    JSONParser jParser = new JSONParser();
    JSONArray myJSON = null;

    ArrayList<HashMap<String, String>> arrayList;
    private static final String TAG_SUKSES = "sukses";
    private static final String TAG_record = "record";

    private static final String TAG_kode_lokasi = "kode_lokasi";
    private static final String TAG_kategori = "kategori";
    private static final String TAG_nama_lokasi = "nama_lokasi";
    private static final String TAG_jenis_lokasi= "jenis_lokasi";
    Button btnAnalisa;

    // The context variable to use in whole activity in place of
    // "HomeActivity.this".
    private Context mContext;

    // The FruitList Adapter to bind the views and show in listview.
    private LokasiAdapter mLokasiAdapter;

    // The ArrayList of Fruits<com.example.fariz.latihancheckbox.LokasiItem>.
    private ArrayList<LokasiItem> mFruitList;

    // The ListView of fruit message list.
    private ListView mFruitListview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.lokasifilter);
        mContext = LokasiFilter.this;

        Intent i = getIntent();

        nama = i.getStringExtra("nama");
        JE= i.getStringExtra("JE");
        KA= i.getStringExtra("KA");
        LB= i.getStringExtra("LB");
        LT= i.getStringExtra("LT");
        HA= i.getStringExtra("HA");

        ip=jParser.getIP();



        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build();
        StrictMode.setThreadPolicy(policy);
        baca();
        initView();
        mFruitList = LokasiDataManager.getFruitItemList(arGB,arKode,arNama,arJenis);
        populateFruitList(mFruitList);



        btnAnalisa=(Button) findViewById(R.id.btnAnalisa);
        btnAnalisa.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                String gab="";
                gabs="";
                for(int h=0;h<jd;h++){
                    if(arCK[h]==1) {
                        gab+=h+"."+arNama[h]+"("+arKode2[h]+")#";
                        gabs+=arKode2[h]+",";
                    }
                }//for
                pesan(gab);
            }});
    }

    public void pesan(String item){
        new AlertDialog.Builder(this)
                .setTitle("Pilihan Anda")
                .setMessage(item)
                .setNeutralButton("Tutup", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dlg, int sumthin) {
                Intent i = new Intent(LokasiFilter.this,LokasiList2.class);
                i.putExtra("gabs",gabs);
                        i.putExtra("nama",nama);
                        i.putExtra("JE",JE);
                        i.putExtra("HA",HA);
                        i.putExtra("KA",KA);
                        i.putExtra("LB",LB);
                        i.putExtra("LT",LT);
                startActivity(i);
                finish();
                    }}).show();
    }


    void baca(){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        //ip+"lokasi/lokasi_show.php"

        params.add(new BasicNameValuePair("jenis", JE));
        params.add(new BasicNameValuePair("kategori", KA));
        params.add(new BasicNameValuePair("harga", HA));
        params.add(new BasicNameValuePair("LB", LB));
        params.add(new BasicNameValuePair("LT", LT));

        JSONObject json = jParser.makeHttpRequest(ip+"lokasi/lokasi_show2.php", "GET", params);
        try {
            int sukses = json.getInt(TAG_SUKSES);
            if (sukses == 1) {
                myJSON = json.getJSONArray(TAG_record);
                jd=myJSON.length();
                arKode=new String[jd];
                arNama=new String[jd];
                arJenis=new String[jd];
                arKode2=new String[jd];
                arGB=new int[jd];
                arCK=new int[jd];
                for (int i = 0; i < jd; i++) {
                    JSONObject c = myJSON.getJSONObject(i);
                    String kode_lokasi= c.getString(TAG_kode_lokasi);
                    String kategori= c.getString(TAG_kategori);
                    String nama_lokasi = c.getString(TAG_nama_lokasi);
                    String jenis_lokasi = c.getString(TAG_jenis_lokasi);

                    arGB[i]=R.drawable.kota;
                    arKode[i]=kode_lokasi;
                    arNama[i]=nama_lokasi;
                    arJenis[i]=jenis_lokasi;
                    arCK[i]=0;
                    arKode2[i]="";
                }
            }
        }
        catch (JSONException e) {e.printStackTrace();}
    }
    private void initView() {
        mFruitListview = (ListView) findViewById(R.id.fruit_listview);
        mFruitListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adpView, View view,  int position, long itemId) {
                Log.d("TEST", "onItemClick");
                if (mLokasiAdapter != null) {
                    LokasiItem item = mLokasiAdapter.getItem(position);
                    AppUtils.showToast(mContext, "@"+getString(R.string.item_click) + item.getFruitname());
                }
            }
        });

    }

//    public void viewClickListener(View view) {
//        if (R.id.back_arrow_view == view.getId()) {
//            finish();
//        }
//    }

    private LokasiAdapter.OnFruitItemClickListener listListener = new LokasiAdapter.OnFruitItemClickListener() {

        @Override
        public void onCheckboxClicked(int position, LokasiItem item) {
            item.setCheckboxChecked(!item.isCheckboxChecked());
            mLokasiAdapter.notifyDataSetChanged();
            String NM=item.getFruitname();
            String NL=getString(R.string.nama_lokasi);
            Log.d("NM","NM="+NM);
            Log.d("NL","NL="+NL);

            if (item.isCheckboxChecked()) {
                for(int h=0;h<jd;h++){
                    if(NM.equalsIgnoreCase(arNama[h])) {
                        arCK[h] = 1;
                        arKode2[h]=arKode[h];
                        break;
                    }
                }//for
                AppUtils.showToast(mContext, NL+ "-"+NM + " "+getString(R.string.is_checked));
            } else {
                for(int h=0;h<jd;h++){
                    if(NM.equalsIgnoreCase(arNama[h])) {
                        arCK[h] = 0;
                        arKode2[h]="";
                        break;
                    }
                }//for
                AppUtils.showToast(mContext, NL+"-"+ NM + " " + getString(R.string.is_unchecked));
            }

        }

    };

    private void populateFruitList(ArrayList<LokasiItem> list) {
        if (list != null && list.size() > 0) {
            if (mLokasiAdapter == null) {
                mLokasiAdapter = new LokasiAdapter(mContext, mFruitList);
                mLokasiAdapter.setOnFruitClickListener(listListener);
            }
            mFruitListview.setAdapter(mLokasiAdapter);
        } else {
            mLokasiAdapter.setItemlist(mFruitList);
            AppUtils.showToast(mContext,  getString(R.string.no_message_list_found));
        }
    }

    @Override
    protected void onDestroy() {
        if (mContext != null) {
            mLokasiAdapter = null;
            mFruitList = null;
            mFruitListview = null;
            mContext = null;
            super.onDestroy();
        }
    }
}
