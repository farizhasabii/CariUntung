package com.example.fariz.cariuntung;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class submenu_detaildata extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submenu_detaildata);

        ImageView arsip=(ImageView) findViewById(R.id.btn_arsip);
        arsip.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent i = new Intent(submenu_detaildata.this,PenilaianList.class);

                startActivity(i);
            }});

        TextView arsip1=(TextView) findViewById(R.id.txt_arsip);
        arsip1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent i = new Intent(submenu_detaildata.this,PenilaianList.class);
                startActivity(i);
            }});



        ImageView lokasi=(ImageView) findViewById(R.id.btn_gedung);
        lokasi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent i = new Intent(submenu_detaildata.this,LokasiList.class);

                startActivity(i);
            }});


        TextView gedung=(TextView) findViewById(R.id.txt_bangunan);
        gedung.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent i = new Intent(submenu_detaildata.this,LokasiList.class);
                startActivity(i);
            }});

    }






}


