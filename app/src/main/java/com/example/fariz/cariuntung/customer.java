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

public class customer extends Activity {
	String ip="";
	String kode_customer;
	String kode_customer0="";

	EditText txtkode_customer;
	EditText txtnama_customer;
	EditText txtalamat;
	EditText txtnomor_hp;
	EditText txtemail;
	EditText txtstatus;
	EditText txtusername;
	EditText txtpassword;

	Button btnProses;
	Button btnHapus;

	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();

	private static final String TAG_SUKSES = "sukses";
	private static final String TAG_record = "record";

	private static final String TAG_nama_customer = "nama_customer";
	private static final String TAG_nomor_hp = "nomor_hp";
	private static final String TAG_email = "email";
	private static final String TAG_alamat = "alamat";
	private static final String TAG_status = "status";
	private static final String TAG_username = "username";
	private static final String TAG_password = "password";



	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registrasi);

		ip=jsonParser.getIP();
		callMarquee();

		txtkode_customer = (EditText) findViewById(R.id.txtkode_customer);
		txtnama_customer= (EditText) findViewById(R.id.txtnama_customer);
		txtemail= (EditText) findViewById(R.id.txtemail);
		txtalamat= (EditText) findViewById(R.id.txtalamat);
		txtnomor_hp= (EditText) findViewById(R.id.txtnomor_hp);
		txtusername = (EditText) findViewById(R.id.txtusername);
		txtpassword= (EditText) findViewById(R.id.txtpassword);
		txtstatus= (EditText) findViewById(R.id.txtstatus);

		btnProses= (Button) findViewById(R.id.btnproses);
		btnHapus = (Button) findViewById(R.id.btnhapus);

		Intent i = getIntent();
		kode_customer0 = i.getStringExtra("pk");

		if(kode_customer0.length()>0){
			new get().execute();
			btnProses.setText("Update Data");
			btnHapus.setVisibility(View.VISIBLE);
		}
		else{
			btnProses.setText("Add New Data");
			btnHapus.setVisibility(View.GONE);
		}

		btnProses.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				kode_customer= txtkode_customer.getText().toString();
				String lnama_customer= txtnama_customer.getText().toString();
				String lemail= txtemail.getText().toString();
				String lalamat= txtalamat.getText().toString();
				String lnomor_hp= txtnomor_hp.getText().toString();
				String lusername= txtusername.getText().toString();
				String lpassword= txtpassword.getText().toString();
				String lstatus= txtstatus.getText().toString();

				if(kode_customer.length()<1){lengkapi("kode_customer");}
				else if(lnama_customer.length()<1){lengkapi("nama_customer");}
				else if(lemail.length()<1){lengkapi("email");}
				else if(lnomor_hp.length()<1){lengkapi("nomor_hp");}
				else if(lalamat.length()<1){lengkapi("alamat");}
				else if(lusername.length()<1){lengkapi("username");}
				else if(lpassword.length()<1){lengkapi("password");}
				else if(lstatus.length()<1){lengkapi("status");}
				else{
					if(kode_customer0.length()>0){
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
			pDialog = new ProgressDialog(customer.this);
			pDialog.setMessage("Load data detail. Silahkan tunggu...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		protected String doInBackground(String... params) {
			int sukses;
			try {
				List<NameValuePair> params1 = new ArrayList<NameValuePair>();
				params1.add(new BasicNameValuePair("kode_customer", kode_customer0));

				String url=ip+"customer/customer_detail.php";
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
								txtkode_customer.setText(kode_customer0);
								txtnama_customer.setText(myJSON.getString(TAG_nama_customer));
								txtnomor_hp.setText(myJSON.getString(TAG_nomor_hp));
								txtemail.setText(myJSON.getString(TAG_email));
								txtalamat.setText(myJSON.getString(TAG_alamat));
								txtusername.setText("Username : " +myJSON.getString(TAG_username));
								txtpassword.setText(myJSON.getString(TAG_password));
								txtstatus.setText(myJSON.getString(TAG_status));
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
			pDialog = new ProgressDialog(customer.this);
			pDialog.setMessage("Menyimpan data ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		protected String doInBackground(String... args) {
			kode_customer = txtkode_customer.getText().toString();


			String lnama_customer= txtnama_customer.getText().toString();
			String lemail= txtemail.getText().toString();
			String lalamat= txtalamat.getText().toString();
			String lnomor_hp= txtnomor_hp.getText().toString();
			String lusername= txtusername.getText().toString();
			String lpassword= txtpassword.getText().toString();
			String lstatus= txtstatus.getText().toString();

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("kode_customer", kode_customer));
			params.add(new BasicNameValuePair("nama_customer", lnama_customer));
			params.add(new BasicNameValuePair("email", lemail));
			params.add(new BasicNameValuePair("nomor_hp", lnomor_hp));
			params.add(new BasicNameValuePair("alamat", lalamat));
			params.add(new BasicNameValuePair("status", lstatus));
			params.add(new BasicNameValuePair("username", lusername));
			params.add(new BasicNameValuePair("password", lpassword));

			String url=ip+"customer/customer_add.php";
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
			pDialog = new ProgressDialog(customer.this);
			pDialog.setMessage("Mengubah data ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		protected String doInBackground(String... args) {
			kode_customer = txtkode_customer.getText().toString();
			String lnama_customer= txtnama_customer.getText().toString();
			String lemail= txtemail.getText().toString();
			String lalamat= txtalamat.getText().toString();
			String lnomor_hp= txtnomor_hp.getText().toString();
			String lusername= txtusername.getText().toString();
			String lpassword= txtpassword.getText().toString();
			String lstatus= txtstatus.getText().toString();

			List<NameValuePair> params = new ArrayList<NameValuePair>();

				params.add(new BasicNameValuePair("kode_customer", kode_customer));
			params.add(new BasicNameValuePair("nama_customer", lnama_customer));
			params.add(new BasicNameValuePair("email", lemail));
			params.add(new BasicNameValuePair("nomor_hp", lnomor_hp));
			params.add(new BasicNameValuePair("alamat", lalamat));
			params.add(new BasicNameValuePair("status", lstatus));
			params.add(new BasicNameValuePair("username", lusername));
			params.add(new BasicNameValuePair("password", lpassword));

			String url=ip+"customer/customer_update.php";
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
			pDialog = new ProgressDialog(customer.this);
			pDialog.setMessage("Menghapus data...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		protected String doInBackground(String... args) {
			int sukses;
			try {
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("kode_customer", kode_customer0));

				String url=ip+"customer/customer_del.php";
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
		String kata="Selamat Datang @lp2maray.com Aplikasi Android  "+stgl+"/"+sjam+" #";
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
