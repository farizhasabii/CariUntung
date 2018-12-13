package com.example.fariz.cariuntung;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class Cari_Tempat extends AppCompatActivity {
Spinner spn_jenis,spn_harga,spn_kategori,spn_luasbangunan,spn_luastanah;
    EditText ct_nama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cari__tempat);
        spn_jenis = (Spinner)findViewById(R.id.spn_jenis);
        spn_harga = (Spinner)findViewById(R.id.spn_harga);
        spn_kategori = (Spinner)findViewById(R.id.spn_kategori);
        spn_luasbangunan = (Spinner)findViewById(R.id.spn_luasbangunan);
        spn_luastanah = (Spinner)findViewById(R.id.spn_luastanah);


        ct_nama= (EditText)findViewById(R.id.ct_nama);


        Button lokasi=(Button) findViewById(R.id.cari);
        lokasi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                String nama=ct_nama.getText().toString();
                String JE=spn_jenis.getSelectedItem().toString();
                String HA=spn_harga.getSelectedItem().toString();
                String KA=spn_kategori.getSelectedItem().toString();
                String LB=spn_luasbangunan.getSelectedItem().toString();
                String LT=spn_luastanah.getSelectedItem().toString();

                Intent i = new Intent(Cari_Tempat.this,LokasiFilter.class);
                i.putExtra("nama",nama);
                i.putExtra("JE",JE);
                i.putExtra("HA",HA);
                i.putExtra("KA",KA);
                i.putExtra("LB",LB);
                i.putExtra("LT",LT);
                startActivity(i);
            }});



    }
}
