package com.example.fariz.cariuntung;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class LokasiFilterBU extends Activity {
    String pilih="";
    String[]arKode;
    String[]arNama;
    String[]arJenis;
    int[]arGB;


    String ip="";

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
        mContext = LokasiFilterBU.this;

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
    }

    void baca(){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        //ip+"lokasi/lokasi_show.php"
        JSONObject json = jParser.makeHttpRequest(ip+"lokasi/lokasi_show.php", "GET", params);
        try {
            int sukses = json.getInt(TAG_SUKSES);
            if (sukses == 1) {
                myJSON = json.getJSONArray(TAG_record);
                int jd=myJSON.length();
                arKode=new String[jd];
                arNama=new String[jd];
                arJenis=new String[jd];
                arGB=new int[jd];

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
                    AppUtils.showToast(mContext, getString(R.string.item_click) + item.getFruitname());
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
            if (item.isCheckboxChecked()) {
                AppUtils.showToast(mContext, getString(R.string.nama_lokasi) + item.getFruitname() + getString(R.string.is_checked));
            } else {
                AppUtils.showToast(mContext, getString(R.string.nama_lokasi) + item.getFruitname() + getString(R.string.is_unchecked));
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
