package com.example.fariz.cariuntung;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class penilaiandetail extends Activity {
	String ip="";
	String id;
	String id0="";

	EditText txtkode_penilaian;
	EditText txtkode_lokasi;
	EditText txtrekapitulasi;
	EditText txtbobot;
	EditText txtranking;
	EditText txtketerangan;
	

	Button btnProses;
	Button btnHapus;

	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();

	private static final String TAG_SUKSES = "sukses";
	private static final String TAG_record = "record";

	private static final String TAG_kode_penilaian = "kode_penilaian";
	private static final String TAG_bobot = "bobot";
	private static final String TAG_kode_lokasi = "kode_lokasi";

	private static final String TAG_rekapitulasi = "rekapitulasi";
	private static final String TAG_ranking = "ranking";
	private static final String TAG_keterangan = "keterangan";


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.penilaian_detail);

		ip=jsonParser.getIP();

		txtkode_penilaian= (EditText) findViewById(R.id.txtkode_penilaian);
		txtbobot= (EditText) findViewById(R.id.txtbobot);
		txtkode_lokasi= (EditText) findViewById(R.id.txtkode_lokasi);
		txtrekapitulasi= (EditText) findViewById(R.id.txtrekapitulasi);
		txtranking = (EditText) findViewById(R.id.txtranking);
		txtketerangan= (EditText) findViewById(R.id.txtketerangan);

		btnProses= (Button) findViewById(R.id.btnproses);
		btnHapus = (Button) findViewById(R.id.btnhapus);

		Intent i = getIntent();
		id0 = i.getStringExtra("pk");

		if(id0.length()>0){
			new get().execute();
			btnProses.setText("Update Data");
			btnHapus.setVisibility(View.VISIBLE);
			btnProses.setVisibility(View.GONE);
		}
		else{
			btnProses.setText("Add New Data");
			btnHapus.setVisibility(View.GONE);
		}

		btnProses.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String lkode_penilaian= txtkode_penilaian.getText().toString();
				String lbobot= txtbobot.getText().toString();
				String lkode_lokasi= txtkode_lokasi.getText().toString();
				String lrekapitulasi= txtrekapitulasi.getText().toString();
				String lranking= txtranking.getText().toString();
				String lketerangan= txtketerangan.getText().toString();

				 if(lkode_penilaian.length()<1){lengkapi("kode_penilaian");}
				else if(lbobot.length()<1){lengkapi("bobot");}
				else if(lrekapitulasi.length()<1){lengkapi("nomor_hp");}
				else if(lkode_lokasi.length()<1){lengkapi("kode_lokasi");}
				else if(lranking.length()<1){lengkapi("ranking");}
				else if(lketerangan.length()<1){lengkapi("keterangan");}
				else{
					if(id0.length()>0){
						new update().execute();
					}
					else{
						new save().execute();
					}
				}//else

			}});

		btnHapus.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				new del().execute();
			}});
	}

	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	class get extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(penilaiandetail.this);
			pDialog.setMessage("Load data detail. Silahkan tunggu...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		protected String doInBackground(String... params) {
			int sukses;
			try {
				List<NameValuePair> params1 = new ArrayList<NameValuePair>();
				params1.add(new BasicNameValuePair("id", id0));

				String url=ip+"penilaiandetail/penilaiandetail_detail.php";
				Log.v("detail",url);
				JSONObject json = jsonParser.makeHttpRequest(url, "GET", params1);
				Log.d("detail", json.toString());
				sukses = json.getInt(TAG_SUKSES);
				if (sukses == 1) {
					JSONArray myObj = json.getJSONArray(TAG_record); // JSON Array
					final JSONObject myJSON = myObj.getJSONObject(0);
					runOnUiThread(new Runnable() {
						public void run() {
							try {
								txtkode_penilaian.setText(myJSON.getString(TAG_kode_penilaian));
								txtrekapitulasi.setText(myJSON.getString(TAG_rekapitulasi));
								txtbobot.setText(myJSON.getString(TAG_bobot));
								txtkode_lokasi.setText(myJSON.getString(TAG_kode_lokasi));
								txtranking.setText(myJSON.getString(TAG_ranking));
								txtketerangan.setText(myJSON.getString(TAG_keterangan));
							}
							catch (JSONException e) {e.printStackTrace();}
						}});
				}
				else{
					// jika id tidak ditemukan
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}
		protected void onPostExecute(String file_url) {pDialog.dismiss();}
	}
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	

	class save extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(penilaiandetail.this);
			pDialog.setMessage("Menyimpan data ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		protected String doInBackground(String... args) {


			String lkode_penilaian= txtkode_penilaian.getText().toString();
			String lbobot= txtbobot.getText().toString();
			String lkode_lokasi= txtkode_lokasi.getText().toString();
			String lrekapitulasi= txtrekapitulasi.getText().toString();
			String lranking= txtranking.getText().toString();

			String lketerangan= txtketerangan.getText().toString();

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("id0", id0));
			params.add(new BasicNameValuePair("id", id0));
			params.add(new BasicNameValuePair("kode_penilaian", lkode_penilaian));
			params.add(new BasicNameValuePair("bobot", lbobot));
			params.add(new BasicNameValuePair("nomor", lrekapitulasi));
			params.add(new BasicNameValuePair("kode_lokasi", lkode_lokasi));
			params.add(new BasicNameValuePair("keterangan", lketerangan));
			params.add(new BasicNameValuePair("ranking", lranking));


			String url=ip+"penilaiandetail/penilaiandetail_add.php";
			Log.v("add",url);
			JSONObject json = jsonParser.makeHttpRequest(url,"POST", params);
			Log.d("add", json.toString());
			try {
				int sukses = json.getInt(TAG_SUKSES);
				if (sukses == 1) {
					Intent i = getIntent();
					setResult(100, i);
					finish();
				} else {
					// gagal update data
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}

		protected void onPostExecute(String file_url) {pDialog.dismiss();}
	}
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	

	class update extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(penilaiandetail.this);
			pDialog.setMessage("Mengubah data ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		protected String doInBackground(String... args) {
			String lkode_penilaian= txtkode_penilaian.getText().toString();
			String lbobot= txtbobot.getText().toString();
			String lkode_lokasi= txtkode_lokasi.getText().toString();
			String lrekapitulasi= txtrekapitulasi.getText().toString();
			String lranking= txtranking.getText().toString();

			String lketerangan= txtketerangan.getText().toString();

			List<NameValuePair> params = new ArrayList<NameValuePair>();

			params.add(new BasicNameValuePair("id0", id0));
			params.add(new BasicNameValuePair("id", id0));
			params.add(new BasicNameValuePair("kode_penilaian", lkode_penilaian));
			params.add(new BasicNameValuePair("bobot", lbobot));
			params.add(new BasicNameValuePair("nomor", lrekapitulasi));
			params.add(new BasicNameValuePair("kode_lokasi", lkode_lokasi));
			params.add(new BasicNameValuePair("keterangan", lketerangan));
			params.add(new BasicNameValuePair("ranking", lranking));


			String url=ip+"penilaiandetail/penilaiandetail_update.php";
			Log.v("update",url);
			JSONObject json = jsonParser.makeHttpRequest(url,"POST", params);
			Log.d("add", json.toString());
			try {
				int sukses = json.getInt(TAG_SUKSES);
				if (sukses == 1) {
					Intent i = getIntent();
					setResult(100, i);
					finish();
				} else {
					// gagal update data
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}

		protected void onPostExecute(String file_url) {pDialog.dismiss();}
	}
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	

	class del extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(penilaiandetail.this);
			pDialog.setMessage("Menghapus data...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		protected String doInBackground(String... args) {
			int sukses;
			try {
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("id", id0));

				String url=ip+"penilaiandetail/penilaiandetail_del.php";
				Log.v("delete",url);
				JSONObject json = jsonParser.makeHttpRequest(url, "GET", params);
				Log.d("delete", json.toString());
				sukses = json.getInt(TAG_SUKSES);
				if (sukses == 1) {
					Intent i = getIntent();
					setResult(100, i);
					finish();
				}
			}
			catch (JSONException e) {e.printStackTrace();}
			return null;
		}

		protected void onPostExecute(String file_url) {pDialog.dismiss();}
	}
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	

	public void lengkapi(String item){
		new AlertDialog.Builder(this)
				.setTitle("Lengkapi Data")
				.setMessage("Silakan lengkapi data "+item +" !")
				.setNeutralButton("Tutup", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dlg, int sumthin) {
						finish();
					}}).show();
	}




	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
