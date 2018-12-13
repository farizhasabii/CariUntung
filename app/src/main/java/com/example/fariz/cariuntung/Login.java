package com.example.fariz.cariuntung;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Login extends Activity {
	
	
     EditText txtusername,txtpassword;
     String ip="";
     int sukses;
     private ProgressDialog pDialog;
	    JSONParser jsonParser = new JSONParser();

	    private static final String TAG_SUKSES = "sukses";
	    private static final String TAG_record = "record";
	    
	    String kode_customer="",nama_customer,email;
	    
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ip=jsonParser.getIP();
        
        
        txtusername=(EditText)findViewById(R.id.txtusername);
        txtpassword=(EditText)findViewById(R.id.txtpassword);


        Button btnLogin= (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user=txtusername.getText().toString();
                String pass=txtpassword.getText().toString();
                if(user.length()<1){lengkapi("username");}
                else if(pass.length()<1){lengkapi("password");}
                else{
                	new ceklogin().execute();
                         }
                       
                  }

        });


        Button txtBuat=(Button)findViewById(R.id.btnReg);
		txtBuat.setOnClickListener(new View.OnClickListener() {
		public void onClick(View arg0) {
			Intent i = new Intent(Login.this,registrasi.class);
            i.putExtra("pk", "");
            startActivity(i);
		}});
		
		


    }
    public void gagal(){
        new AlertDialog.Builder(this)
                .setTitle("Gagal Login")
                .setMessage("Silakan Cek Account Anda Kembali")
                .setNeutralButton("Tutup", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dlg, int sumthin) {

                    }})
                .show();
    }
  
    public void sukses(String item,String ex){
        new AlertDialog.Builder(this)
                .setTitle("Sukses "+ex)
                .setMessage(ex+" data "+item+" Berhasil")
                .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dlg, int sumthin) {
                        Intent i = new Intent(Login.this,Menuutama.class);
                        startActivity(i);
                        // finish();
                    }})
                .show();
    }



    class ceklogin extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Login.this);
            pDialog.setMessage("Proses Login...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        protected String doInBackground(String... params) {
          
            try {
            	String	username=txtusername.getText().toString().trim();
           		String	password=txtpassword.getText().toString().trim();
           		
                List<NameValuePair> myparams = new ArrayList<NameValuePair>();
                myparams.add(new BasicNameValuePair("username", username));
                myparams.add(new BasicNameValuePair("password", password));

                String url=ip+"customer/customer_login.php";
                Log.v("detail",url);
                JSONObject json = jsonParser.makeHttpRequest(url, "GET", myparams);
                Log.d("detail", json.toString());
                sukses = json.getInt(TAG_SUKSES);
                if (sukses == 1) {
                    JSONArray myObj = json.getJSONArray(TAG_record); // JSON Array
                    final JSONObject myJSON = myObj.getJSONObject(0);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            try {

                            	kode_customer=myJSON.getString("kode_customer");
                            	nama_customer=myJSON.getString("nama_customer");
                            	email=myJSON.getString("email");
                                    }
                            catch (JSONException e) {e.printStackTrace();}
                        }});
                }
              
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
        @SuppressLint("NewApi")
		protected void onPostExecute(String file_url) {
        	
        	pDialog.dismiss();
	        Log.v("SUKSES",kode_customer);

        	if(sukses==1){
		        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(Login.this);
		        SharedPreferences.Editor editor = sharedPref.edit();
		        editor.putBoolean("Registered", true);
		        editor.putString("kode_customer", kode_customer);
		        editor.putString("nama_customer", nama_customer);
		        editor.putString("email", email);
		        editor.apply();
		        Intent i = new Intent(getApplicationContext(),Menuutama.class);
		        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		        startActivity(i);
		        finish();
        	}
        	else{
        		gagal("Login");
        	}
        }
    }

   

        public void lengkapi(String item){
	    	new AlertDialog.Builder(this)
			.setTitle("Lengkapi Data")
			.setMessage("Silakan lengkapi data "+item)
			.setNeutralButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dlg, int sumthin) {
				}})
			.show();
	    }

	

	  public void gagal(String item){
	    	new AlertDialog.Builder(this)
			.setTitle("Gagal Login")
			.setMessage("Login "+item+" ,, Gagal")
			.setNeutralButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dlg, int sumthin) {
				}})
			.show();
	    }


	  public boolean onKeyDown(int keyCode, KeyEvent event) {
	        if (keyCode == KeyEvent.KEYCODE_BACK) {
	        	finish();
	                return true;
	        }
	    return super.onKeyDown(keyCode, event);
	}  
        
	}  
