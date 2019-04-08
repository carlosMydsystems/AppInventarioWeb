package com.example.sistemas.appinventariolocaldb;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sistemas.appinventariolocaldb.Entities.Usuario;
import com.example.sistemas.appinventariolocaldb.Utilidades.Utilidad;

import java.util.ArrayList;

import static com.example.sistemas.appinventariolocaldb.Utilidades.Utilidad.CAMPO_SECUENCIA;
import static com.example.sistemas.appinventariolocaldb.Utilidades.Utilidad.TABLA_INVENTARIO;
import static com.example.sistemas.appinventariolocaldb.Utilidades.Utilidad.TABLA_PRESENTACION;

public class ConsultaLecturaActivity extends AppCompatActivity {

    ListView lvlistaInventariada;
    ConexionSQLiteHelper conn;
    ArrayList<CharSequence> listaInventario;
    View mview;
    ListView listView;
    ArrayList<CharSequence> listaConsulta;
    ArrayList<String> listadoborrado;
    Button btnbusquedaCodBarras;
    EditText etartconsulta;
    Usuario usuario;
    ImageButton ibregresarmenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_lectura);

        conn=new ConexionSQLiteHelper(getApplicationContext(),"bd_inventarios",null,1);

        lvlistaInventariada = findViewById(R.id.lvListaInventariada);
        btnbusquedaCodBarras = findViewById(R.id.btnBuscarConsulta);
        etartconsulta = findViewById(R.id.etartconsulta);
        ibregresarmenu = findViewById(R.id.ibVolverConsultaMenu);
        usuario = (Usuario)getIntent().getSerializableExtra("Usuario");

        listaInventario = new ArrayList<>();

        mview = getLayoutInflater().inflate(R.layout.listview_dialog, null);

        btnbusquedaCodBarras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                muestraListaRegistro(etartconsulta.getText().toString());
            }
        });

        ibregresarmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConsultaLecturaActivity.this,MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Usuario",usuario);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });

    }

    public void muestraListaRegistro(String codigoBarras){

        listaConsulta = new ArrayList<>();
        SQLiteDatabase db=conn.getReadableDatabase();
        String[] parametros={codigoBarras};
        String lista;
        listadoborrado = new ArrayList<>();

        try {

            final Cursor cursor=db.rawQuery("SELECT * " + " FROM " + TABLA_INVENTARIO+" WHERE "+Utilidad.CAMPO_COD_BARRA_LEC+"=?",parametros);

            cursor.moveToFirst();

            do {

                lista = "\t\t\t\t" +cursor.getString(10)+ "\t\t\t\t\t\t\t\t\t\t\t" + cursor.getString(13);

                listadoborrado.add(cursor.getString(0));
                listaConsulta.add(lista);

            } while (cursor.moveToNext());


            ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(
                    ConsultaLecturaActivity.this, android.R.layout.simple_list_item_1,listaConsulta
            );

            lvlistaInventariada.setAdapter(adapter);

            lvlistaInventariada.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                    final AlertDialog.Builder builder = new AlertDialog.Builder(ConsultaLecturaActivity.this);
                    builder.setCancelable(false);

                    listView = mview.findViewById(R.id.lvopciones);
                    ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(
                            ConsultaLecturaActivity.this, android.R.layout.simple_list_item_1,
                            getResources().getStringArray(R.array.opcionesdb));
                    adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);

                    listView.setAdapter(adapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int i, long id) {

                            switch (i) {
                                case 0:

                                    Toast.makeText(ConsultaLecturaActivity.this, "eliminar :" + listadoborrado.get(position).toString(), Toast.LENGTH_SHORT).show();
                                    EliminarRegistroLista(listadoborrado.get(position));
                                    break;

                                case 1:

                                    Intent intent = new Intent(ConsultaLecturaActivity.this,ConsultaLecturaActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("Usuario",usuario);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                    finish();

                                    break;
                            }
                        }
                    });
                    builder.setView(mview);
                    AlertDialog dialog = builder.create();
                    if (mview.getParent() != null)
                        ((ViewGroup) mview.getParent()).removeView(mview); // <- fix
                    dialog.show();
                    return true;

                }
            });

        }catch (Exception e){

            Toast.makeText(getApplicationContext(),"El Articulo no existe en codigo de barras",Toast.LENGTH_LONG).show();

        }

    }

    private void EliminarRegistroLista(String unidadMedida) {

        SQLiteDatabase db=conn.getReadableDatabase();
        String[] parametros={unidadMedida};

        try {

            Toast.makeText(this, ""+unidadMedida, Toast.LENGTH_SHORT).show();

            db.delete(TABLA_INVENTARIO,CAMPO_SECUENCIA+"=?",parametros);
            Intent intent = new Intent(ConsultaLecturaActivity.this,ConsultaLecturaActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("Usuario",usuario);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();

        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"El Articulo no existe en ConsultaInventario ",Toast.LENGTH_LONG).show();
        }
    }


}
