package com.example.fariz.cariuntung;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LokasiHasil extends ListActivity {
String ip="";
	String nama,JE,KA,LB,LT,HA,gabs;
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
String deskripsi;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview);
		arrayList = new ArrayList<HashMap<String, String>>();
		ip=jParser.getIP();

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.detectAll()
				.penaltyLog()
				.detectDiskReads()
				.detectDiskWrites()
				.detectNetwork()
				.penaltyDeath()
				.penaltyLog()
				.build();

		StrictMode.setThreadPolicy(policy);


		Intent io = getIntent();

		nama = "Budi";//io.getStringExtra("nama");
		JE= "Bangunan dijual"; //io.getStringExtra("JE");
		KA= "Junk Food";//io.getStringExtra("KA");
		LB= "300"; //io.getStringExtra("LB");
		LT= "100";//io.getStringExtra("LT");
		HA= "17000000000";//io.getStringExtra("HA");
		gabs= "KDL1811002";//io.getStringExtra("gabs");

//		new load().execute();

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("nama", nama));
		params.add(new BasicNameValuePair("JE", JE));
		params.add(new BasicNameValuePair("KA", KA));
		params.add(new BasicNameValuePair("LB", LB));
		params.add(new BasicNameValuePair("LT", LT));
		params.add(new BasicNameValuePair("HA", HA));
		params.add(new BasicNameValuePair("gabs", gabs));


		JSONObject json = jParser.makeHttpRequest(ip+"penilaiandetail/penilaianandroid.php", "GET", params);
		Log.d("show: ", json.toString());
		try {
			int sukses = json.getInt(TAG_SUKSES);
			if (sukses == 1) {
				myJSON = json.getJSONArray(TAG_record);
				for (int i = 0; i < myJSON.length(); i++) {
					JSONObject c = myJSON.getJSONObject(i);
					String kode_lokasi= c.getString("kode_lokasi");
					String kategori= c.getString("nama_lokasi")+"#Bobot: "+c.getString("bobot");
					String nama_lokasi = "Ranking: "+c.getString("ranking")+"#"+ c.getString("sbobot");

//					$record["kode_lokasi"] =$arkode[$i];
//					$record["nama_lokasi"] =$arnama[$i];
//
//					$record["kode_penilaian"] =$kode_penilaian;
//					$record["bobot"] =$arB[$i];
//					$record["sbobot"] =$arBS[$i];
//					$record["ranking"] =$i+1;
					HashMap<String, String> map = new HashMap<String, String>();
					map.put(TAG_kode_lokasi, kode_lokasi);
					map.put(TAG_nama_lokasi, nama_lokasi);
					map.put(TAG_kategori, kategori);

					arrayList.add(map);
				}
			}
		}
		catch (JSONException e) {e.printStackTrace();}

		ListAdapter adapter = new SimpleAdapter(LokasiHasil.this, arrayList,R.layout.desainlokasi_list, new String[] { TAG_kode_lokasi,TAG_nama_lokasi,TAG_kategori},new int[] { R.id.kode_k, R.id.txtNamalkp, R.id.txtDeskripsilkp});
		setListAdapter(adapter);


		ListView lv = getListView();
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String pk = ((TextView) view.findViewById(R.id.kode_k)).getText().toString();
				Intent i = new Intent(getApplicationContext(), lokasi.class);
				i.putExtra("pk", pk);
				startActivityForResult(i, 100);
			}});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == 100) {// jika result code 100
			Intent intent = getIntent();
			finish();
			startActivity(intent);
		}
	}

    
public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
		finish();
		return true;
		}
		return super.onKeyDown(keyCode, event);
		}   
	
}
