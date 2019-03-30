package com.example.sistemas.appinventariolocaldb;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sistemas.appinventariolocaldb.Entities.Inventario;
import com.example.sistemas.appinventariolocaldb.Entities.Usuario;
import com.example.sistemas.appinventariolocaldb.Utilidades.Utilidad;

import java.util.ArrayList;
import java.util.Calendar;

import static com.example.sistemas.appinventariolocaldb.Utilidades.Utilidad.CAMPO_CODIGO_ARTICULO;
import static com.example.sistemas.appinventariolocaldb.Utilidades.Utilidad.CAMPO_CODIGO_BARRA_1;
import static com.example.sistemas.appinventariolocaldb.Utilidades.Utilidad.CAMPO_CODIGO_BARRA_2;
import static com.example.sistemas.appinventariolocaldb.Utilidades.Utilidad.CAMPO_CODIGO_BARRA_3;
import static com.example.sistemas.appinventariolocaldb.Utilidades.Utilidad.CAMPO_DESCRIPCION_ARTICULO;
import static com.example.sistemas.appinventariolocaldb.Utilidades.Utilidad.CAMPO_EQUIVALENCIA;
import static com.example.sistemas.appinventariolocaldb.Utilidades.Utilidad.CAMPO_FECHA;
import static com.example.sistemas.appinventariolocaldb.Utilidades.Utilidad.CAMPO_MARCA;
import static com.example.sistemas.appinventariolocaldb.Utilidades.Utilidad.CAMPO_UNIDAD_MEDIDA;
import static com.example.sistemas.appinventariolocaldb.Utilidades.Utilidad.TABLA_ARTICULOS;
import static com.example.sistemas.appinventariolocaldb.Utilidades.Utilidad.TABLA_INVENTARIO;
import static com.example.sistemas.appinventariolocaldb.Utilidades.Utilidad.TABLA_INV_CAB_MOVIL;
import static com.example.sistemas.appinventariolocaldb.Utilidades.Utilidad.TABLA_PRESENTACION;
import static com.example.sistemas.appinventariolocaldb.Utilidades.Utilidad.TABLA_VTA_BARRAS_PRESENTACION;

public class RegistroDBActivity extends AppCompatActivity {

    Usuario usuario;
    TextView tvdetallearticulo,tvcontrol,textView3,tvcodarticulo, tvnombreusuario,tvconteo;
    Button btnbuscar,btngrabar;
    EditText etarticulo,etcantidad;
    String insert,fechayhora;
    Inventario inventario;
    ConexionSQLiteHelper conn;
    Integer year,month,dayOfMonth,hour,minute;
    ImageButton ibvolverregistromenu;

    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_db);

        conn=new ConexionSQLiteHelper(getApplicationContext(),"bd_inventarios",null,1);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        fechayhora = ""+dayOfMonth+"/"+month+"/"+dayOfMonth+    + hour +":"+minute;
        inventario = new Inventario();
        etarticulo = findViewById(R.id.etArticulo);
        etcantidad = findViewById(R.id.etCantidad);
        tvdetallearticulo = findViewById(R.id.tvDetalle);
        btnbuscar = findViewById(R.id.btnBuscar);
        btngrabar = findViewById(R.id.btnGrabar);
        tvcontrol = findViewById(R.id.tvControl);
        textView3 = findViewById(R.id.textView3);
        tvconteo = findViewById(R.id.tvConteo);
        tvcodarticulo = findViewById(R.id.tvCodArticulo);
        tvnombreusuario = findViewById(R.id.tvNombreUsuario);
        ibvolverregistromenu = findViewById(R.id.ibVolverRegistroMenu);

        textView3.setVisibility(View.GONE);
        etcantidad.setVisibility(View.GONE);
        btngrabar.setVisibility(View.GONE);
        tvconteo.setVisibility(View.GONE);
        usuario = (Usuario)getIntent().getSerializableExtra("Usuario");

        tvnombreusuario.setText(usuario.getNombre());

        ibvolverregistromenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RegistroDBActivity.this,MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Usuario",usuario);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });

        btnbuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (etarticulo.getText().toString().equals("")){

                    AlertDialog.Builder builder = new AlertDialog.Builder(RegistroDBActivity.this);
                    builder.setTitle("ATENCION...  !")
                            .setMessage("Por favor ingrese un valor valido de Codigo de barra")
                            .setPositiveButton("Aceptar",null);
                    builder.create()
                            .show();

                }else{

                    textView3.setVisibility(View.VISIBLE);
                    etcantidad.setVisibility(View.VISIBLE);
                    btngrabar.setVisibility(View.VISIBLE);
                    tvconteo.setVisibility(View.VISIBLE);

                    ConsultaCodigoBarras(etarticulo.getText().toString().trim());

                }

            }
        });

        btngrabar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etarticulo.equals("")||etarticulo ==null){

                    AlertDialog.Builder builder = new AlertDialog.Builder(RegistroDBActivity.this);
                    builder.setTitle("ATENCION !")
                            .setMessage("Por favor ingrese un valor valido de Codigo de barra")
                            .setPositiveButton("Aceptar",null);
                    builder.create()
                            .show();

                }else{

                    registrarInventario();

                }

            }
        });
    }

    private void ConsultaArticulo(String codigoArticulo) {


        SQLiteDatabase db=conn.getReadableDatabase();
        String[] parametros={codigoArticulo};

        try {
            Cursor cursor=db.rawQuery("SELECT * " + " FROM " + TABLA_ARTICULOS+" WHERE "+Utilidad.CAMPO_CODIGO_ARTICULO+"=?",parametros);
            cursor.moveToFirst();

            tvcodarticulo.setText(cursor.getString(3));
            tvdetallearticulo.setText(cursor.getString(1));
            Toast.makeText(this, "undbarraleida es : ", Toast.LENGTH_SHORT).show();
            inventario.setCodArticulo(cursor.getString(0));
            inventario.setAlmacen(usuario.getCodAlmacen());
            inventario.setConteo(usuario.getConteo());
            inventario.setFecha(usuario.getLlave());
            inventario.setCodUsuario(usuario.getUser());
            inventario.setUnidadMedidaOrigen(cursor.getString(2));
            inventario.setEquivalencia(cursor.getString(3));


            ConsultaInventariogeneral(inventario.getUndBarraLec());
            Double factorDouble = Double.valueOf(inventario.getEquiBarraLec())/Double.valueOf(inventario.getEquivalencia());
            String factor = factorDouble.toString();
            inventario.setFactor(factor);
            tvconteo.setText("Conteo : "+inventario.getConteo());
            textView3.setText(inventario.getUndBarraLec()+" - "+ inventario.getFactor());

        }catch (Exception e){

            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(),"Consulta Articulo ",Toast.LENGTH_LONG).show();

        }
    }

    private void ConsultaInventariogeneral(String unidadMedida) {

        SQLiteDatabase db=conn.getReadableDatabase();
        String[] parametros={unidadMedida};

        try {

            Cursor cursor=db.rawQuery("SELECT * " + " FROM " + TABLA_PRESENTACION+" WHERE "+Utilidad.CAMPO_UNIDAD_MEDIDA_PRESENTACION+"=?",parametros);
            cursor.moveToFirst();
            inventario.setEquiBarraLec(cursor.getString(2).trim());
            etcantidad.requestFocus();
            ConsultaLlave();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"Consulta Inventario general ",Toast.LENGTH_LONG).show();
            etarticulo.requestFocus();
        }
    }
    private void ConsultaLlave() {

        SQLiteDatabase db=conn.getReadableDatabase();

        try {

            Cursor cursor=db.rawQuery("SELECT * " + " FROM " + TABLA_INV_CAB_MOVIL,null);
            cursor.moveToFirst();
            inventario.setFecha(cursor.getString(0).trim());
            inventario.setAlmacen(cursor.getString(1).trim());
            inventario.setConteo(cursor.getString(2).trim());
            inventario.setEstado(cursor.getString(3));
            etcantidad.requestFocus();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"Consulta llave ",Toast.LENGTH_LONG).show();
            etarticulo.requestFocus();
        }
    }

    private void registrarInventario(){

        inventario.setCodUsuario(usuario.getUser());
        inventario.setCantDigitadaLec(etcantidad.getText().toString());
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        fechayhora = ""+CambiaFormato(dayOfMonth)+"/"+CambiaFormato(month)+"/"+year+"\t "+
                CambiaFormato(hour) +":"+CambiaFormato(minute);
        inventario.setFechayhora(fechayhora);

        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this,"bd_inventarios",null,1);
        SQLiteDatabase db=conn.getWritableDatabase();

        insert = "INSERT INTO " + Utilidad.TABLA_INVENTARIO + " ( " +

                Utilidad.CAMPO_FECHA + " , " +
                Utilidad.CAMPO_ALMACEN + " , " +
                Utilidad.CAMPO_CONTEO + " , " +
                Utilidad.CAMPO_COD_ARTICULO + " , " +
                Utilidad.CAMPO_UND_MEDIDA_ORIG + " , " +
                Utilidad.CAMPO_EQU_MEDIDA_ORIG + " , " +
                Utilidad.CAMPO_UND_BARRA_LEC + " , " +
                Utilidad.CAMPO_COD_BARRA_LEC + " , " +
                Utilidad.CAMPO_EQU_BARRA_LEC + " , " +
                Utilidad.CAMPO_CAN_DIGITADA_LEC + " , " +
                Utilidad.CAMPO_FACTOR + " , " +
                Utilidad.CAMPO_COD_USUARIO_INVENTARIO + " , " +
                Utilidad.CAMPO_FCH_CREACION  +" ) " +

                "VALUES " + "('"+

                inventario.getFecha()+"','" +
                inventario.getAlmacen() + "','" +
                inventario.getConteo() + "','" +
                inventario.getCodArticulo() + "','" +
                inventario.getUnidadMedidaOrigen() + "','" +
                inventario.getEquivalencia() + "','" +
                inventario.getUndBarraLec() + "','" +
                inventario.getCodBarraLec() + "','" +
                inventario.getEquiBarraLec() + "','" +
                inventario.getCantDigitadaLec() + "','" +
                inventario.getFactor() + "','" +
                inventario.getCodUsuario() + "','" +
                inventario.getFechayhora()

                +  "')" ;

        String trama2 = inventario.getFecha()+"','" +
                inventario.getAlmacen() + "','" +
                inventario.getConteo() + "','" +
                inventario.getCodArticulo() + "','" +
                inventario.getUnidadMedidaOrigen() + "','" +
                inventario.getEquivalencia() + "','" +
                inventario.getUndBarraLec() + "','" +
                inventario.getCodBarraLec() + "','" +
                inventario.getEquiBarraLec() + "','" +
                inventario.getCantDigitadaLec() + "','" +
                inventario.getFactor() + "','" +
                inventario.getCodUsuario() + "','" +
                inventario.getFechayhora();

        String trama = inventario.getFecha()+"|"+//
                inventario.getAlmacen()+"|"+//
                inventario.getConteo() +"|"+//
                inventario.getCodArticulo()+"|"+
                inventario.getUnidadMedidaOrigen()+"|"+//
                inventario.getEquivalencia()+"|"+//
                inventario.getUndBarraLec()+"|"+//
                inventario.getCodBarraLec()+"|"+
                inventario.getEquiBarraLec()+"|"+//
                etcantidad.getText().toString().trim()+ "|"+
                inventario.getFactor()+"|"+
                inventario.getCodUsuario();

        Toast.makeText(this, "trama : " + trama, Toast.LENGTH_SHORT).show();

        db.execSQL(insert);
        db.close();

        LimpiarCampos();
        OcultaGrabar();

    }

    private void OcultaGrabar() {

        etcantidad.setVisibility(View.GONE);
        btngrabar.setVisibility(View.GONE);
        textView3.setVisibility(View.GONE);
        tvconteo.setVisibility(View.GONE);

    }

    private void LimpiarCampos() {

        etarticulo.requestFocus();
        etarticulo.setText("");
        tvdetallearticulo.setText("");
        etcantidad.setText("");

    }

    private void ConsultaCodigoBarras(String codigoBarras) {

        SQLiteDatabase db=conn.getReadableDatabase();
        String[] parametros={codigoBarras};

        try {

            Cursor cursor=db.rawQuery("SELECT * " + " FROM " + TABLA_VTA_BARRAS_PRESENTACION+" WHERE "+Utilidad.CAMPO_COD_BARRA+"=?",parametros);
            cursor.moveToFirst();

            inventario.setCodArticulo(cursor.getString(0));
            inventario.setCodBarraLec(cursor.getString(1));
            inventario.setUndBarraLec(cursor.getString(2));

            ConsultaArticulo(inventario.getCodArticulo().trim());

        }catch (Exception e){

            Toast.makeText(getApplicationContext(),"El codigo de Barras ingresado no Existe",Toast.LENGTH_LONG).show();
            textView3.setVisibility(View.GONE);
            etcantidad.setVisibility(View.GONE);
            btngrabar.setVisibility(View.GONE);
            tvconteo.setVisibility(View.GONE);
            etarticulo.requestFocus();

        }
    }

    public String CambiaFormato(Integer valor){

        String nuevoValor = ""+valor;
        if (valor<=9){
            nuevoValor = "0"+valor;
        }
        return nuevoValor;

    }

    private void CargaLLaves() {

        SQLiteDatabase db=conn.getReadableDatabase();

        try {

            Cursor cursor=db.rawQuery("SELECT * " + " FROM " + TABLA_INV_CAB_MOVIL,null);
            cursor.moveToFirst();
            inventario.setFecha(cursor.getString(0));
            inventario.setAlmacen(cursor.getString(1));
            inventario.setConteo(cursor.getString(2));
            inventario.setEstado(cursor.getString(3));

        }catch (Exception e){

            Toast.makeText(getApplicationContext(),"Carga llaves ",Toast.LENGTH_LONG).show();
        }
    }
}
