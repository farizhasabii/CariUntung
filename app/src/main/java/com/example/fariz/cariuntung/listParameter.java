package com.example.fariz.cariuntung;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class listParameter extends AppCompatActivity {

    ArrayList<DataModel> dataModels;
    ListView listView;
    private CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listcheckbox);

        Button analisa=(Button) findViewById(R.id.btnAnalisa);
        analisa.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent i = new Intent(listParameter.this,LokasiListParameter.class);

                startActivity(i);
            }});
        listView = (ListView) findViewById(R.id.listView);

        dataModels = new ArrayList<>();

        dataModels.add(new DataModel("Daya Tampung Parkir", false));
        dataModels.add(new DataModel("Jarak ke Pasar", false));
        dataModels.add(new DataModel("Banyak Rival Usaha", false));
        dataModels.add(new DataModel("Banyak Penduduk", false));
        dataModels.add(new DataModel("Jarak ke Jalan Besar", false));



        adapter = new CustomAdapter(dataModels, getApplicationContext());

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                DataModel dataModel= dataModels.get(position);
                dataModel.checked = !dataModel.checked;
                adapter.notifyDataSetChanged();


            }
        });
    }
}
