package com.example.sistemas.pagocontado;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sistemas.pagocontado.entidades.ClienteSucursal;
import com.example.sistemas.pagocontado.entidades.Clientes;
import com.example.sistemas.pagocontado.entidades.FormaPago;
import com.example.sistemas.pagocontado.entidades.Usuario;

import java.util.ArrayList;
import java.util.List;

public class ListadoAlmacenActivity extends AppCompatActivity {

    ListView lvAlmacenes;
    ArrayList<String> listaalmacen;
    Clientes cliente;
    Usuario usuario;
    ArrayList<FormaPago> listaFormasPago;
    Button btnretornolistadoalmacen;
    String indice;
    ArrayList<ClienteSucursal> listaClienteSucursal;
    static ArrayList<ClienteSucursal> listaClienteSucursalBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_almacen);

        cliente  = new Clientes();
        cliente = (Clientes)getIntent().getSerializableExtra("Cliente");
        usuario = (Usuario) getIntent().getSerializableExtra("Usuario");
        listaClienteSucursal = (ArrayList<ClienteSucursal>) getIntent().getSerializableExtra("listaClienteSucursal");

        if (listaClienteSucursal==null){

            listaClienteSucursal = listaClienteSucursalBack;

        }
        indice = getIntent().getStringExtra("Ind");
        //listaFormasPago = (ArrayList<FormaPago>) getIntent().getSerializableExtra("ListaFomasPago");

        // se hace la insercion del codigo en duro

        listaalmacen =  new ArrayList<>();
        listaalmacen.add("T02");
        listaalmacen.add("T04");
        listaalmacen.add("T10");
        listaalmacen.add("T11");
        listaalmacen.add("T12");
        listaalmacen.add("T14");
        listaalmacen.add("CD1");
        listaalmacen.add("CD2");
        listaalmacen.add("CD3");
        listaalmacen.add("CD4");

        lvAlmacenes = findViewById(R.id.lvAlmacenes);
        btnretornolistadoalmacen = findViewById(R.id.btnRetornoListadoAlmacen);

        btnretornolistadoalmacen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =  new Intent(ListadoAlmacenActivity.this,MostrarClienteActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Cliente",cliente);
                intent.putExtras(bundle);
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable("Usuario",usuario);
                intent.putExtras(bundle1);
                Bundle bundle2 = new Bundle();
                bundle2.putSerializable("listaClienteSucursal",listaClienteSucursal);
                intent.putExtras(bundle2);
                startActivity(intent);
                finish();
            }
        });

        CustomListAdapter listAdapter= new CustomListAdapter(ListadoAlmacenActivity.this , R.layout.custom_list , listaalmacen);
        lvAlmacenes.setAdapter(listAdapter);

        lvAlmacenes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent =  new Intent(ListadoAlmacenActivity.this,ListadoFormaPagoActivity.class);

                Bundle bundle = new Bundle();
                bundle.putSerializable("Cliente",cliente);
                intent.putExtras(bundle);
                intent.putExtra("Almacen",listaalmacen.get(position));
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable("Usuario",usuario);
                intent.putExtras(bundle1);
                Bundle bundle2 = new Bundle();
                bundle2.putSerializable("listaClienteSucursal",listaClienteSucursal);
                intent.putExtras(bundle2);
                startActivity(intent);
                finish();
            }
        });
    }
    public static class CustomListAdapter extends ArrayAdapter<String> {

        private Context mContext;
        private int id;
        private List<String> items ;

        public CustomListAdapter(Context context, int textViewResourceId , List<String> list)
        {
            super(context, textViewResourceId, list);
            mContext = context;
            id = textViewResourceId;
            items = list ;
        }

        @Override
        public View getView(int position, View v, ViewGroup parent)
        {
            View mView = v ;
            if(mView == null){
                LayoutInflater vi = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                mView = vi.inflate(id, null);
            }

            TextView text = (TextView) mView.findViewById(R.id.textView);

            if(items.get(position) != null)
            {
                text.setTextColor(Color.BLACK);
                text.setText(items.get(position));
                text.setTextSize(15);
                int color = Color.argb(10, 0, 20, 255);
                text.setBackgroundColor(color);
            }

            return mView;
        }
    }
}
