package com.example.sistemas.sqliteinventarios;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sistemas.sqliteinventarios.Entities.Inventario;
import com.example.sistemas.sqliteinventarios.Utilities.Utilidad;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RegistroUsuariosActivity extends AppCompatActivity {

    EditText campoId,campoNombre,campoTelefono;
    String url;
    ArrayList<Inventario> listaProductosInventario;
    Inventario inventario;
    Button btnregistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuarios);


        campoId= (EditText) findViewById(R.id.campoId);
        campoNombre= (EditText) findViewById(R.id.campoNombre);
        campoTelefono= (EditText) findViewById(R.id.campoTelefono);

    }

    public void onClick(View view) {

        ConsultarListaInventario();
    }


    private void registrarInventarios(ArrayList<Inventario> listaproductosinventario) {
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this,"bd_inventarios",null,1);

        SQLiteDatabase db=conn.getWritableDatabase();

        for (int i = 0;i<listaproductosinventario.size();i++){

            ContentValues values=new ContentValues();
            values.put(Utilidad.CAMPO_ID_INVENTARIO,listaproductosinventario.get(i).getId());
            values.put(Utilidad.CAMPO_CODIGO_PRODUCTO,listaproductosinventario.get(i).getCodigoProducto());
            values.put(Utilidad.CAMPO_DESCRIPCION_PRODUCTO,listaproductosinventario.get(i).getDescripcion());
            values.put(Utilidad.CAMPO_MARCA_PRODUCTO,listaproductosinventario.get(i).getMarca());
            values.put(Utilidad.CAMPO_UNIDAD_PRODUCTO,listaproductosinventario.get(i).getUnidad());
            values.put(Utilidad.CAMPO_ID_USUARIO,listaproductosinventario.get(i).getIdUsuario());

            Long idResultante=db.insert(Utilidad.TABLA_INVENTARIO,Utilidad.CAMPO_ID_INVENTARIO,values);

            Toast.makeText(getApplicationContext(),"Id Registro: "+idResultante,Toast.LENGTH_SHORT).show();
        }

        db.close();

    }

    private void ConsultarListaInventario() {

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        url =  "http://www.mydsystems.com/pruebaDaniel/ConsultaInventario.php";
        listaProductosInventario = new ArrayList<>();

        StringRequest stringRequest=new StringRequest(Request.Method.GET, url ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("producto");
                            if (success){
                                for(int i=0;i<jsonArray.length();i++) {

                                    inventario = new Inventario();
                                    jsonObject = jsonArray.getJSONObject(i);
                                    inventario.setId(jsonObject.getString("id"));
                                    inventario.setCodigoProducto(jsonObject.getString("cod_Producto"));
                                    inventario.setDescripcion(jsonObject.getString("DescripcionProducto"));
                                    inventario.setMarca(jsonObject.getString("Marca"));
                                    inventario.setUnidad(jsonObject.getString("Unidad"));
                                    inventario.setIdUsuario(jsonObject.getString("Usuario_id"));
                                    listaProductosInventario.add(inventario);

                                }

                                registrarInventarios(listaProductosInventario);




                                /*
                                CargaInventarioDB();

                                Intent intent =  new Intent(MainActivity.this,RegistroDBActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("Usuario",usuario);
                                intent.putExtras(bundle);
                                startActivity(intent);
                                finish();
*/
                            }else {

                                AlertDialog.Builder builder = new AlertDialog.Builder(RegistroUsuariosActivity.this);
                                builder.setMessage("No se pudo encontrar los tipos de pago correspondientes");
                                builder.create().show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

    private void CargaInventarioDB() {
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this,"bd_inventarios",null,1);

        SQLiteDatabase db=conn.getWritableDatabase();

        for (int i = 0;i<listaProductosInventario.size();i++){

            ContentValues values=new ContentValues();
            values.put(Utilidad.CAMPO_ID_INVENTARIO,listaProductosInventario.get(i).getId().toString());
            values.put(Utilidad.CAMPO_CODIGO_PRODUCTO,listaProductosInventario.get(i).getCodigoProducto().toString());
            values.put(Utilidad.CAMPO_DESCRIPCION_PRODUCTO,listaProductosInventario.get(i).getDescripcion().toString());
            values.put(Utilidad.CAMPO_CANTIDAD,listaProductosInventario.get(i).toString());
            values.put(Utilidad.CAMPO_UNIDAD_PRODUCTO,listaProductosInventario.get(i).getUnidad().toString());
            values.put(Utilidad.CAMPO_ID_USUARIO,listaProductosInventario.get(i).getIdUsuario().toString());

            Long idResultante=db.insert(Utilidad.TABLA_INVENTARIO,Utilidad.CAMPO_ID_INVENTARIO,values);

            Toast.makeText(getApplicationContext(),"Id Registro: "+idResultante,Toast.LENGTH_SHORT).show();
        }
        db.close();

    }
}
