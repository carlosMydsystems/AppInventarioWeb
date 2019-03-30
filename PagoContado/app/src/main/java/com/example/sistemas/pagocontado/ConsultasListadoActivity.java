package com.example.sistemas.pagocontado;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.sistemas.pagocontado.entidades.Usuario;

import java.util.ArrayList;
import java.util.Calendar;

public class ConsultasListadoActivity extends AppCompatActivity {

    Calendar calendar;
    Integer year,month,dayOfMonth;
    DatePickerDialog datePickerDialog;
    Usuario usuario;
    ListView lvConsultaTipo;
    ArrayList<String> listaTipoConsulta;
    ImageButton ibRetornoMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultas_listado);

        listaTipoConsulta = new ArrayList<>();
        usuario = (Usuario)getIntent().getSerializableExtra("Usuario");
        lvConsultaTipo = findViewById(R.id.lvConsultasTipo);
        listaTipoConsulta.add("Consulta Pedido");
        listaTipoConsulta.add("Consulta Promociones");
        listaTipoConsulta.add("Consulta Stock");
        listaTipoConsulta.add("Consulta Precios");
        ibRetornoMenu = findViewById(R.id.ibRetornoMenu);
        ibRetornoMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConsultasListadoActivity.this,MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Usuario",usuario);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });

        ListadoAlmacenActivity.CustomListAdapter listAdapter = new ListadoAlmacenActivity.
                CustomListAdapter(ConsultasListadoActivity.this, R.layout.custom_list, listaTipoConsulta);
        lvConsultaTipo.setAdapter(listAdapter);

        lvConsultaTipo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0: // Editar producto

                        calendar = Calendar.getInstance();
                        year = calendar.get(Calendar.YEAR);
                        month = calendar.get(Calendar.MONTH);
                        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                        datePickerDialog = new DatePickerDialog(ConsultasListadoActivity.this,
                                new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                                        String fecha = FormatoDiaMes(day) + "/" + FormatoDiaMes(month + 1) + "/" + year;
                                        Intent intent = new Intent(ConsultasListadoActivity.this,ConsutlasActivity.class);
                                        intent.putExtra("fecha",fecha);
                                        Bundle bundle = new Bundle();
                                        bundle.putSerializable("Usuario",usuario);
                                        intent.putExtras(bundle);
                                        startActivity(intent);
                                        finish();

                                    }
                                }, year, month, dayOfMonth);
                        datePickerDialog.show();
                        break;

                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:

                        break;
                }
            }
        });
    }

    private String FormatoDiaMes(Integer valor) {

        String ValorString;

        if (valor<=9){
            ValorString = "0"+valor.toString();
        }else{
            ValorString = valor.toString();
        }
        return ValorString;
    }
}
