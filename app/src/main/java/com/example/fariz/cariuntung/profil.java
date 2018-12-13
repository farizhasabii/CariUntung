package com.example.fariz.cariuntung;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
import java.util.List;

public class profil extends Activity {
	String ip="";
	String kode_customer;
	String kode_customer0="";

	TextView txtkode_customer;
	TextView txtnama_customer;
	TextView txtalamat;
	TextView txtnomor_hp;
	TextView txtemail;
	TextView txtstatus;
	TextView txtusername;
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



	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profil__customer);

		ip = jsonParser.getIP();

		txtnama_customer = (TextView) findViewById(R.id.txtnama_customer);
		txtemail = (TextView) findViewById(R.id.txtemail);
		txtalamat = (TextView) findViewById(R.id.txtalamat);
		txtnomor_hp = (TextView) findViewById(R.id.txtnomor_hp);
		txtusername = (TextView) findViewById(R.id.txtusername);
		txtstatus = (TextView) findViewById(R.id.txtstatus);

		Intent i = getIntent();
		kode_customer0 = i.getStringExtra("kode_customer");
		if (kode_customer0.length() > 0) {
			new profil.get().execute();
		}


		Button update = (Button) findViewById(R.id.btnupdate);
		update.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				Intent i = new Intent(profil.this, updateprofil.class);
				i.putExtra("pk",kode_customer0);
				startActivity(i);
			}
		});

	}






	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	class get extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(profil.this);
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

				String url=ip+"registrasi/customer_detail.php";
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
								txtnama_customer.setText(myJSON.getString(TAG_nama_customer));
								txtnomor_hp.setText("Nomor HP : "+myJSON.getString(TAG_nomor_hp));
								txtemail.setText(myJSON.getString(TAG_email));
								txtalamat.setText("Alamat : "+myJSON.getString(TAG_alamat));
								txtusername.setText("Username : " +myJSON.getString(TAG_username));
								txtstatus.setText("Status : "+myJSON.getString(TAG_status));
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


//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	



	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
