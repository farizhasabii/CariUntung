package com.example.fariz.cariuntung;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
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

public class penilaiandetailList extends ListActivity {
String ip="";
String deskripsi,kp;
	private ProgressDialog pDialog;
	JSONParser jParser = new JSONParser();
	JSONArray myJSON = null;
	
	ArrayList<HashMap<String, String>> arrayList;
	private static final String TAG_SUKSES = "sukses";
	private static final String TAG_record = "record";

	private static final String TAG_id = "id";
	private static final String TAG_kode_penilaian = "kode_penilaian";
	private static final String TAG_bobot = "bobot";
	private static final String TAG_kode_lokasi = "kode_lokasi";
	private static final String TAG_nama_lokasi = "nama_lokasi";
	private static final String TAG_kategori = "kategori";
	private static final String TAG_rekapitulasi = "rekapitulasi";
	private static final String TAG_ranking = "ranking";
	private static final String TAG_keterangan = "keterangan";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.penilaiandetail_list);
		arrayList = new ArrayList<HashMap<String, String>>();
		ip=jParser.getIP();

		Intent io = getIntent();
		kp = io.getStringExtra("pk");

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.detectAll()
				.penaltyLog()
				.build();
		StrictMode.setThreadPolicy(policy);


//		new load().execute();

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("kode_penilaian", kp));

		JSONObject json = jParser.makeHttpRequest(ip+"penilaiandetail/penilaiandetail_show.php", "GET", params);
		Log.d("show: ", json.toString());
		try {
			int sukses = json.getInt(TAG_SUKSES);
			if (sukses == 1) {
				myJSON = json.getJSONArray(TAG_record);
				for (int i = 0; i < myJSON.length(); i++) {
					JSONObject c = myJSON.getJSONObject(i);
					String id= c.getString(TAG_id);
					String kode_penilaian= c.getString(TAG_kode_penilaian);
					String kode_lokasi = c.getString(TAG_kode_lokasi);
					String nama_lokasi = c.getString(TAG_nama_lokasi);
					String kategori = c.getString(TAG_kategori);

					String rekapitulasi = c.getString(TAG_rekapitulasi);
					String nomorhp = c.getString(TAG_bobot);
					String keterangan = c.getString(TAG_keterangan);
					String ranking = c.getString(TAG_ranking);
					deskripsi="Kat : "+kategori+",  rek : "+rekapitulasi+", Hsl :"+ranking;

					HashMap<String, String> map = new HashMap<String, String>();
					map.put(TAG_id, id);
					map.put(TAG_nama_lokasi, nama_lokasi);
					map.put(TAG_keterangan, deskripsi);



					arrayList.add(map);
				}
			} else {
				Intent i = new Intent(getApplicationContext(),penilaiandetail.class);
				i.putExtra("pk", "");
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
			}
		}
		catch (JSONException e) {e.printStackTrace();}

		ListAdapter adapter = new SimpleAdapter(penilaiandetailList.this, arrayList,R.layout.desain_list, new String[] { TAG_id,
				TAG_nama_lokasi, TAG_keterangan},new int[] { R.id.kode_k, R.id.txtNamalkp, R.id.txtDeskripsilkp});
		setListAdapter(adapter);


		ListView lv = getListView();
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String pk = ((TextView) view.findViewById(R.id.kode_k)).getText().toString();
				Intent i = new Intent(getApplicationContext(), penilaiandetail.class);
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


	public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 1, 0, "Add New").setIcon(R.drawable.add);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case 1:         
        	Intent i = new Intent(getApplicationContext(), penilaiandetail.class);
			i.putExtra("pk", "");
			startActivityForResult(i, 100);
            return true;
        }
        return false;
    }

    
public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
		finish();
		return true;
		}
		return super.onKeyDown(keyCode, event);
		}   
	
}
