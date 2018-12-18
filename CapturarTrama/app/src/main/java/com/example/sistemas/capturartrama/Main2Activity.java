package com.example.sistemas.capturartrama;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.sistemas.capturartrama.Entidades.HojaRuta;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    ArrayList<String> lvp1,lvp2,lvp3,lvp4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        String Aux = getIntent().getExtras().getString("E");
        lvp1 = getIntent().getStringArrayListExtra("Lista");
       // ArrayList<HojaRuta> lista = (ArrayList<HojaRuta> ) getIntent().getSerializableExtra("Lista");
        Intent intent = new Intent(Main2Activity.this,SegundoActivity.class);
/*
        Bundle bundle = new Bundle();
        bundle.putSerializable("Lista", lista);
        intent.putExtras(bundle);
        */
        Bundle bundle = new Bundle();
        bundle.putSerializable("Lista", lvp1);
        intent.putExtras(bundle);




        intent.putExtra("E",Aux);





        startActivity(intent);
        finish();
    }
}
