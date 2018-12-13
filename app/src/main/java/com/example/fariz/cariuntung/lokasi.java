package com.example.fariz.cariuntung;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class lokasi extends Activity {
	String ip="";
	String kode_lokasi;
	String kode_lokasi0="";

	TextView txtkode_lokasi;
	TextView txtnama_lokasi;
	TextView txtkategori;
	TextView txtjenis_lokasi;
	TextView txtk1;
	TextView txtk2;
	TextView txtk3;
	TextView txtk4;
	TextView txtk5;
	TextView txtk6;
	TextView txtk7;
	TextView txtk8;
	TextView txt_latitude;
	TextView txt_longitude;
	TextView txtalamat;
	TextView txtketerangan;
	String gambar;


	Button btnProses;
	Button btnHapus;

	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();

	private static final String TAG_SUKSES = "sukses";
	private static final String TAG_record = "record";

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
	private static final String TAG_jenis_lokasi = "jenis_lokasi";
	private static final String TAG_gambar = "gambar";
	private static final String TAG_keterangan= "keterangan";



	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lokasi);

		ip=jsonParser.getIP();


		txtkode_lokasi = (TextView) findViewById(R.id.txtkode_lokasi);
		txtnama_lokasi= (TextView) findViewById(R.id.txtnama_lokasi);
		txtkategori= (TextView) findViewById(R.id.txtkategori);
		txtjenis_lokasi= (TextView) findViewById(R.id.txtjenis_lokasi);
		txtk1= (TextView) findViewById(R.id.txtk1);
		txtk2= (TextView) findViewById(R.id.txtk2);
		txtk3= (TextView) findViewById(R.id.txtk3);
		txtk4 = (TextView) findViewById(R.id.txtk4);
		txtk5= (TextView) findViewById(R.id.txtk5);
		txtk6= (TextView) findViewById(R.id.txtk6);
		txtk7= (TextView) findViewById(R.id.txtk7);
		txtk8= (TextView) findViewById(R.id.txtk8);
		txt_latitude= (TextView) findViewById(R.id.txt_latitude);
		txt_longitude= (TextView) findViewById(R.id.txt_longitude);
		txtalamat= (TextView) findViewById(R.id.txtalamat);
		txtketerangan= (TextView) findViewById(R.id.txtketerangan);


		btnProses= (Button) findViewById(R.id.btnproses);
		btnHapus = (Button) findViewById(R.id.btnhapus);

		Intent i = getIntent();
		kode_lokasi0 = i.getStringExtra("pk");

		if(kode_lokasi0.length()>0){
			new get().execute();
			btnProses.setText("Update Data");
			btnProses.setVisibility(View.GONE);
			btnHapus.setVisibility(View.GONE);
		}
		else{
			btnProses.setText("Add New Data");
			btnHapus.setVisibility(View.GONE);
		}

		btnProses.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				kode_lokasi= txtkode_lokasi.getText().toString();
				String lnama= txtnama_lokasi.getText().toString();
				String lkategori= txtkategori.getText().toString();
				String ljenis_lokasi= txtjenis_lokasi.getText().toString();
				String lk1= txtk1.getText().toString();
				String lk2= txtk2.getText().toString();
				String lk3= txtk3.getText().toString();
				String lk4= txtk4.getText().toString();
				String lk5= txtk5.getText().toString();
				String lk6= txtk6.getText().toString();
				String lk7= txtk7.getText().toString();
				String lk8= txtk8.getText().toString();
				String llatitude= txt_latitude.getText().toString();
				String llongitude= txt_longitude.getText().toString();
				String lalamat= txtalamat.getText().toString();
				String lketerangan= txtketerangan.getText().toString();


				if(kode_lokasi.length()<1){lengkapi("kode_lokasi");}
				else if(lnama.length()<1){lengkapi("nama");}
				else if(lk1.length()<1){lengkapi("k1");}
				else if(ljenis_lokasi.length()<1){lengkapi("jenis_lokasi");}
				else if(lkategori.length()<1){lengkapi("kategori");}
				else if(lalamat.length()<1){lengkapi("alamat");}
				else if(lk4.length()<1){lengkapi("k4");}
				else if(lk5.length()<1){lengkapi("k5");}
				else if(lk3.length()<1){lengkapi("k3");}
				else if(lk2.length()<1){lengkapi("k2");}
				else if(lk6.length()<1){lengkapi("k6");}
				else if(lk7.length()<1){lengkapi("k7");}
				else if(lk8.length()<1){lengkapi("k8");}
				else if(llatitude.length()<1){lengkapi("latitude");}
				else if(llongitude.length()<1){lengkapi("longitude");}
				else if(lketerangan.length()<1){lengkapi("keterangan");}

				else{
					if(kode_lokasi0.length()>0){
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

	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
		ImageView bmImage;

		public DownloadImageTask(ImageView bmImage) {
			this.bmImage = bmImage;
		}

		protected Bitmap doInBackground(String... urls) {
			String urldisplay = urls[0];
			Bitmap mIcon11 = null;
			try {
				InputStream in = new java.net.URL(urldisplay).openStream();
				mIcon11 = BitmapFactory.decodeStream(in);
			}
			catch (Exception e) {Log.e("Error", e.getMessage());e.printStackTrace();}
			return mIcon11;
		}

		protected void onPostExecute(Bitmap result) {
			bmImage.setImageBitmap(result); }
	}



	class get extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(lokasi.this);
			pDialog.setMessage("Load data detail. Silahkan tunggu...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		protected String doInBackground(String... params) {
			int sukses;
			try {
				List<NameValuePair> params1 = new ArrayList<NameValuePair>();
				params1.add(new BasicNameValuePair("kode_lokasi", kode_lokasi0));

				String url=ip+"lokasi/lokasi_detail.php";
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
								txtkode_lokasi.setText(kode_lokasi0);
								txtnama_lokasi.setText(myJSON.getString(TAG_nama_lokasi));
								txtjenis_lokasi.setText(myJSON.getString(TAG_jenis_lokasi));
								txtk1.setText(myJSON.getString(TAG_k1));
								txtkategori.setText(myJSON.getString(TAG_kategori));
								txtk4.setText(myJSON.getString(TAG_k4));
								txtk5.setText(myJSON.getString(TAG_k5));
								txtk3.setText(myJSON.getString(TAG_k3));
								txtk2.setText(myJSON.getString(TAG_k2));
								txtk6.setText(myJSON.getString(TAG_k6));
								txtk7.setText(myJSON.getString(TAG_k7));
								txtk8.setText(myJSON.getString(TAG_k8));
								txt_latitude.setText(myJSON.getString(TAG_latitude));
								txt_longitude.setText(myJSON.getString(TAG_longitude));
								txtalamat.setText(myJSON.getString(TAG_alamat));
								txtketerangan.setText(myJSON.getString(TAG_keterangan));

								gambar=myJSON.getString(TAG_gambar);

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
		protected void onPostExecute(String file_url) {
			pDialog.dismiss();

			String arUrlFoto = ip + "ypathfile/" + gambar;
			new DownloadImageTask((ImageView) findViewById(R.id.myGambar)).execute(arUrlFoto);
		}
		}
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	

	class save extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(lokasi.this);
			pDialog.setMessage("Menyimpan data ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		protected String doInBackground(String... args) {
			kode_lokasi = txtkode_lokasi.getText().toString();
			String lnama_lokasi= txtnama_lokasi.getText().toString();
			String lk1= txtk1.getText().toString();
			String lkategori= txtkategori.getText().toString();
			String ljenis_lokasi= txtjenis_lokasi.getText().toString();
			String lk4= txtk4.getText().toString();
			String lk5= txtk5.getText().toString();
			String lk3= txtk3.getText().toString();
			String lk2= txtk2.getText().toString();
			String lk6= txtk6.getText().toString();
			String lk7= txtk7.getText().toString();
			String lk8= txtk8.getText().toString();
			String llatitude= txt_latitude.getText().toString();
			String llongitude= txt_longitude.getText().toString();
			String lalamat= txtalamat.getText().toString();
			String lketerangan= txtketerangan.getText().toString();


			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("kode_lokasi", kode_lokasi));
			params.add(new BasicNameValuePair("nama_lokasi", lnama_lokasi));
			params.add(new BasicNameValuePair("k1", lk1));
			params.add(new BasicNameValuePair("jenis_lokasi", ljenis_lokasi));
			params.add(new BasicNameValuePair("kategori", lkategori));
			params.add(new BasicNameValuePair("k3", lk3));
			params.add(new BasicNameValuePair("k4", lk4));
			params.add(new BasicNameValuePair("k5", lk5));
			params.add(new BasicNameValuePair("k2", lk2));
			params.add(new BasicNameValuePair("k6", lk6));
			params.add(new BasicNameValuePair("k7", lk7));
			params.add(new BasicNameValuePair("k8", lk8));
			params.add(new BasicNameValuePair("latitude",llatitude));
			params.add(new BasicNameValuePair("longitude",llongitude));
			params.add(new BasicNameValuePair("alamat", lalamat));
			params.add(new BasicNameValuePair("keterangan", lketerangan));


			String url=ip+"lokasi/lokasi_add.php";
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
			pDialog = new ProgressDialog(lokasi.this);
			pDialog.setMessage("Mengubah data ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		protected String doInBackground(String... args) {
			kode_lokasi = txtkode_lokasi.getText().toString();
			String lnama_lokasi= txtnama_lokasi.getText().toString();
			String lk1= txtk1.getText().toString();
			String lkategori= txtkategori.getText().toString();
			String ljenis_lokasi= txtjenis_lokasi.getText().toString();
			String lk4= txtk4.getText().toString();
			String lk5= txtk5.getText().toString();
			String lk3= txtk3.getText().toString();
			String lk2= txtk2.getText().toString();
			String lk6= txtk6.getText().toString();
			String lk7= txtk7.getText().toString();
			String lk8= txtk8.getText().toString();
			String llatitude= txt_latitude.getText().toString();
			String llongitude= txt_longitude.getText().toString();
			String lalamat= txtalamat.getText().toString();
			String lketerangan= txtketerangan.getText().toString();

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			
			params.add(new BasicNameValuePair("kode_lokasi0", kode_lokasi0));
			params.add(new BasicNameValuePair("kode_lokasi", kode_lokasi));
			params.add(new BasicNameValuePair("nama_lokasi", lnama_lokasi));
			params.add(new BasicNameValuePair("k1", lk1));
			params.add(new BasicNameValuePair("jenis_lokasi", ljenis_lokasi));
			params.add(new BasicNameValuePair("kategori", lkategori));
			params.add(new BasicNameValuePair("k3", lk3));
			params.add(new BasicNameValuePair("k4", lk4));
			params.add(new BasicNameValuePair("k5", lk5));
			params.add(new BasicNameValuePair("k2", lk2));
			params.add(new BasicNameValuePair("k6", lk6));
			params.add(new BasicNameValuePair("k7", lk7));
			params.add(new BasicNameValuePair("k8", lk8));
			params.add(new BasicNameValuePair("latitude", llatitude));
			params.add(new BasicNameValuePair("longitude", llongitude));
			params.add(new BasicNameValuePair("alamat", lalamat));
			params.add(new BasicNameValuePair("keterangan", lketerangan));

			String url=ip+"lokasi/lokasi_update.php";
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
			pDialog = new ProgressDialog(lokasi.this);
			pDialog.setMessage("Menghapus data...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		protected String doInBackground(String... args) {
			int sukses;
			try {
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("kode_lokasi", kode_lokasi0));

				String url=ip+"lokasi/lokasi_del.php";
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
