package com.example.fariz.cariuntung;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class penilaian extends Activity {
	String ip="";
	String kode_penilaian;
	String kode_penilaian0="";

	EditText txtKode_penilaian;
	EditText txttanggal;
	EditText txtkode_customer;
	EditText txtkategori;
	EditText txtjenis_lokasi;
	EditText txtcatatan;
	

	Button btnProses;
	Button btnHapus;

	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();

	private static final String TAG_SUKSES = "sukses";
	private static final String TAG_record = "record";

	private static final String TAG_tanggal = "tanggal";
	private static final String TAG_kategori = "kategori";
	private static final String TAG_catatan = "catatan";
	private static final String TAG_kode_penilaian = "kode_penilaian";
	private static final String TAG_jenis_lokasi = "jenis_lokasi";
	private static final String TAG_kode_customer = "kode_customer";
	


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.penilaian);

		ip=jsonParser.getIP();
		callMarquee();

		txtKode_penilaian = (EditText) findViewById(R.id.txtkode_penilaian);
		txttanggal= (EditText) findViewById(R.id.txttanggal);
		txtjenis_lokasi= (EditText) findViewById(R.id.txtjenis_lokasi);
		txtkode_customer= (EditText) findViewById(R.id.txtkode_customer);
		txtkategori= (EditText) findViewById(R.id.txtkategori);
		txtcatatan= (EditText) findViewById(R.id.txtcatatan);


		btnProses= (Button) findViewById(R.id.btnproses);
		btnHapus = (Button) findViewById(R.id.btnhapus);

		Intent i = getIntent();
		kode_penilaian0 = i.getStringExtra("pk");

		if(kode_penilaian0.length()>0){
			new get().execute();
			btnProses.setText("Update Data");
			btnHapus.setVisibility(View.GONE);
		}
		else{
			btnProses.setText("Add New Data");
			btnHapus.setVisibility(View.GONE);
		}

		btnProses.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				kode_penilaian= txtKode_penilaian.getText().toString();
				String ltanggal= txttanggal.getText().toString();
				String ljenis_lokasi= txtjenis_lokasi.getText().toString();
				String lkode_customer= txtkode_customer.getText().toString();
				String lkategori= txtkategori.getText().toString();
				String lcatatan= txtcatatan.getText().toString();


				if(kode_penilaian.length()<1){lengkapi("kode_penilaian");}
				else if(ltanggal.length()<1){lengkapi("tanggal");}
				else if(ljenis_lokasi.length()<1){lengkapi("jenis_lokasi");}
				else if(lkategori.length()<1){lengkapi("nomor_hp");}
				else if(lkode_customer.length()<1){lengkapi("kode_customer");}
				else if(lcatatan.length()<1){lengkapi("catatan");}

				else{
					if(kode_penilaian0.length()>0){
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
			pDialog = new ProgressDialog(penilaian.this);
			pDialog.setMessage("Load data detail. Silahkan tunggu...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		protected String doInBackground(String... params) {
			int sukses;
			try {
				List<NameValuePair> params1 = new ArrayList<NameValuePair>();
				params1.add(new BasicNameValuePair("kode_penilaian", kode_penilaian0));

				String url=ip+"penilaian/penilaian_detail.php";
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
								txtKode_penilaian.setText(kode_penilaian0);
								txttanggal.setText(myJSON.getString(TAG_tanggal));
								txtkategori.setText(myJSON.getString(TAG_kategori));
								txtjenis_lokasi.setText(myJSON.getString(TAG_jenis_lokasi));
								txtkode_customer.setText(myJSON.getString(TAG_kode_customer));
								txtcatatan.setText(myJSON.getString(TAG_catatan));

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
			pDialog = new ProgressDialog(penilaian.this);
			pDialog.setMessage("Menyimpan data ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		protected String doInBackground(String... args) {
			kode_penilaian = txtKode_penilaian.getText().toString();


			String ltanggal= txttanggal.getText().toString();
			String ljenis_lokasi= txtjenis_lokasi.getText().toString();
			String lkode_customer= txtkode_customer.getText().toString();
			String lkategori= txtkategori.getText().toString();
			String lcatatan= txtcatatan.getText().toString();


			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("kode_penilaian0", kode_penilaian0));
			params.add(new BasicNameValuePair("kode_penilaian", kode_penilaian));
			params.add(new BasicNameValuePair("tanggal", ltanggal));
			params.add(new BasicNameValuePair("jenis_lokasi", ljenis_lokasi));
			params.add(new BasicNameValuePair("nomor", lkategori));
			params.add(new BasicNameValuePair("kode_customer", lkode_customer));

			params.add(new BasicNameValuePair("catatan", lcatatan));


			String url=ip+"penilaian/penilaian_add.php";
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
			pDialog = new ProgressDialog(penilaian.this);
			pDialog.setMessage("Mengubah data ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		protected String doInBackground(String... args) {
			kode_penilaian = txtKode_penilaian.getText().toString();
			String ltanggal= txttanggal.getText().toString();
			String ljenis_lokasi= txtjenis_lokasi.getText().toString();
			String lkode_customer= txtkode_customer.getText().toString();
			String lkategori= txtkategori.getText().toString();
			String lcatatan= txtcatatan.getText().toString();


			List<NameValuePair> params = new ArrayList<NameValuePair>();

			params.add(new BasicNameValuePair("kode_penilaian0", kode_penilaian0));
			params.add(new BasicNameValuePair("kode_penilaian", kode_penilaian));
			params.add(new BasicNameValuePair("tanggal", ltanggal));
			params.add(new BasicNameValuePair("jenis_lokasi", ljenis_lokasi));
			params.add(new BasicNameValuePair("nomor", lkategori));
			params.add(new BasicNameValuePair("kode_customer", lkode_customer));

			params.add(new BasicNameValuePair("catatan", lcatatan));


			String url=ip+"penilaian/penilaian_update.php";
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
			pDialog = new ProgressDialog(penilaian.this);
			pDialog.setMessage("Menghapus data...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		protected String doInBackground(String... args) {
			int sukses;
			try {
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("kode_penilaian", kode_penilaian0));

				String url=ip+"penilaian/penilaian_del.php";
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


	void callMarquee(){
		Calendar cal = Calendar.getInstance();
		int jam = cal.get(Calendar.HOUR);
		int menit= cal.get(Calendar.MINUTE);
		int detik= cal.get(Calendar.SECOND);

		int tgl= cal.get(Calendar.DATE);
		int bln= cal.get(Calendar.MONTH);
		int thn= cal.get(Calendar.YEAR);

		String stgl= String.valueOf(tgl)+"-"+ String.valueOf(bln)+"-"+ String.valueOf(thn);
		String sjam= String.valueOf(jam)+":"+ String.valueOf(menit)+":"+ String.valueOf(detik);

		TextView txtMarquee=(TextView)findViewById(R.id.txtMarquee);
		txtMarquee.setSelected(true);
		String kata="Selamat Datang di Aplikasi Android  "+stgl+"/"+sjam+" #";
		String kalimat= String.format("%1$s", TextUtils.htmlEncode(kata));
		txtMarquee.setText(Html.fromHtml(kalimat+kalimat+kalimat));
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
