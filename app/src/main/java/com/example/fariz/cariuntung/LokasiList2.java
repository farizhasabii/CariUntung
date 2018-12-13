package com.example.fariz.cariuntung;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
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

public class LokasiList2 extends ListActivity {
String ip="";
	String nama,JE,KA,LB,LT,HA,gabs;
String kode_customer="";

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

        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean Registered = sharedPref.getBoolean("Registered", false);
        if (!Registered){
            finish();
        }else {
            kode_customer = sharedPref.getString("kode_customer", "");
        }
        Log.v("test", kode_customer);



        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()

				.detectAll()
				.penaltyLog()
				.build();
		StrictMode.setThreadPolicy(policy);

		Intent io = getIntent();
		nama = io.getStringExtra("nama");
		JE= io.getStringExtra("JE");
		KA= io.getStringExtra("KA");
		LB= io.getStringExtra("LB");
		LT= io.getStringExtra("LT");
		HA= io.getStringExtra("HA");
		gabs= io.getStringExtra("gabs");
//		new load().execute();


		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("nama", nama));
		params.add(new BasicNameValuePair("JE", JE));
		params.add(new BasicNameValuePair("KA", KA));
		params.add(new BasicNameValuePair("LB", LB));
		params.add(new BasicNameValuePair("LT", LT));
		params.add(new BasicNameValuePair("HA", HA));
		params.add(new BasicNameValuePair("gabs", gabs));
        params.add(new BasicNameValuePair("kode_customer", kode_customer));


        Log.d("SHOWME: ", "#"+nama);
        Log.d("SHOWME: ", "#"+JE);
        Log.d("SHOWME: ", "#"+KA);
        Log.d("SHOWME: ", "#"+LB);
        Log.d("SHOWME: ", "#"+LT);
        Log.d("SHOWME: ", "#"+HA);
        Log.d("SHOWME: ", "#"+gabs);

		JSONObject json = jParser.makeHttpRequest(ip+"penilaiandetail/penilaianandroid.php", "GET", params);
		Log.d("SHOWME: ", json.toString());
		try {
			Log.d("SHOWME2: ", "OK");

			int sukses = json.getInt(TAG_SUKSES);
			Log.d("SHOWME2: ", "OK"+sukses);

			if (sukses == 1) {
				myJSON = json.getJSONArray(TAG_record);
				for (int i = 0; i < myJSON.length(); i++) {
					JSONObject c = myJSON.getJSONObject(i);

					String ranking = c.getString("ranking");
					String sbobot =  c.getString("sbobot");
					String bobot = c.getString("bobot");
					String kode_lokasi= c.getString("kode_lokasi");
					String kategori=bobot;
					String nama_lokasi = c.getString("nama_lokasi");
                    deskripsi="Ranking : "+ranking+" Bobot : "+bobot;

                    Log.d("SHOWME3: ", "NL="+i+"="+nama_lokasi);

					HashMap<String, String> map = new HashMap<String, String>();
					map.put(TAG_kode_lokasi, kode_lokasi);
					map.put(TAG_nama_lokasi, nama_lokasi);
					map.put(TAG_kategori, deskripsi);

					arrayList.add(map);
				}
			}
//			else {
//				Intent i = new Intent(getApplicationContext(),lokasi.class);
//				i.putExtra("pk", "");
//				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//				startActivity(i);
//			}
		}
		catch (JSONException e) {e.printStackTrace();}

		ListAdapter adapter = new SimpleAdapter(LokasiList2.this, arrayList,R.layout.desainlokasi_list, new String[] { "kode_lokasi","nama_lokasi","kategori"},new int[] { R.id.kode_k, R.id.txtNamalkp, R.id.txtDeskripsilkp});
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


	public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 1, 0, "Add New").setIcon(R.drawable.add);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case 1:         
        	Intent i = new Intent(getApplicationContext(), lokasi.class);
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
