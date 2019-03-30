package com.example.sistemas.pagocontado;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sistemas.tomapedidos.Entidades.Promociones;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {

    String indice;
    private PromocionesActivity.OnRefreshViewListner mrefreshlistener;

    public ArrayList<Product> listProducts;
    private Context context;

    public ListAdapter(Context context, ArrayList<Product> listProducts) {
        this.context = context;
        this.listProducts = listProducts;
    }

    @Override
    public int getCount() {
        return listProducts.size();
    }

    @Override
    public Product getItem(int position) {
        return listProducts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView
            , ViewGroup parent)
    {

        ArrayList<Promociones> listaPromocionesElegidas;
         View row;
        final ListViewHolder listViewHolder;
        if(convertView == null)
            {
                LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = layoutInflater.inflate(R.layout.activity_custom_listview,parent,false);
                listViewHolder = new ListViewHolder();
                listViewHolder.ivProduct = row.findViewById(R.id.tvdescripcion);
                listViewHolder.tvPrice = row.findViewById(R.id.tvPrice);
                listViewHolder.btnPlus = row.findViewById(R.id.ib_addnew);
                listViewHolder.edTextQuantity = row.findViewById(R.id.editTextQuantity);
                listViewHolder.btnMinus = row.findViewById(R.id.ib_remove);
                listViewHolder.tvidpromociones = row.findViewById(R.id.tvIdPromocion);  // Identificador
                listViewHolder.tvCodArticulo = row.findViewById(R.id.tvCodArticulo);
                listViewHolder.tvunidadpromocion = row.findViewById(R.id.tvUnidadPromocion);
                listViewHolder.tvtasadescuento = row.findViewById(R.id.tvTasaElegida);
                listViewHolder.tvprecio = row.findViewById(R.id.tvUnidadPromocion);
                listViewHolder.tvPresentacionSelect = row.findViewById(R.id.tvPresentacionSelect);
                listViewHolder.tvEquivalencia = row.findViewById(R.id.tvEquivalencia);
                listViewHolder.tvPrecioUni = row.findViewById(R.id.tvPrecioUni);
                listViewHolder.tvObservacionSeleccion = row.findViewById(R.id.tvObservacionSeleccion);

                row.setTag(listViewHolder);
            }
        else{
                row=convertView;
                listViewHolder= (ListViewHolder) row.getTag();
            }

        final Product products = getItem(position);
        listViewHolder.ivProduct.setText(products.ProductImage);
        listViewHolder.tvPrice.setText(products.ProductPrice+"");
        listViewHolder.edTextQuantity.setText(products.CartQuantity+"");
        listViewHolder.tvidpromociones.setText(products.ProductName);
        listViewHolder.tvCodArticulo.setText(products.ProductIdArticulo);
        listViewHolder.tvunidadpromocion.setText(products.UnidadProducto);
        listViewHolder.tvPresentacionSelect.setText(products.Presentacion);
        listViewHolder.tvEquivalencia.setText(products.Equivalencia);
        listViewHolder.tvPrecioUni.setText(products.PrecioUni);
        listViewHolder.tvObservacionSeleccion.setText(products.ObservacionSeleccion);
        listViewHolder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                indice = listViewHolder.tvidpromociones.getText().toString();
                updateQuantity(position,listViewHolder.edTextQuantity,1);
                updateQuantityPrice(position,listViewHolder.tvPrice,-1, indice);

            }
        });

        listViewHolder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                indice = listViewHolder.tvidpromociones.getText().toString();

                if (listViewHolder.edTextQuantity.getText().toString().equals("0")){
                }else {
                    updateQuantityPrice(position,listViewHolder.tvPrice,1,indice);
                }
                updateQuantity(position,listViewHolder.edTextQuantity,-1);

            }
        });

        for (int i = 0; i<getCount();i++){

            Product producto = getItem(i);
            listaPromocionesElegidas = new ArrayList<>();

            if (producto.CartQuantity>0){

            }
        }
        return row;
    }

    private void updateQuantity(int position, EditText edTextQuantity, int value) {
        Product products = getItem(position);

        if(value > 0 )
        {
            if (products.ProductPrice >0){
                products.CartQuantity = products.CartQuantity + 1;
            }
        } // Logica del boton positivo
        else
        {
            if(products.CartQuantity > 0)
            {
                products.CartQuantity = products.CartQuantity - 1;
            }
        }  //  Boton Negaativo
        edTextQuantity.setText(products.CartQuantity+"");
    }
    private void updateQuantityPrice(int position, TextView tvPrice, int value, String indice) {

        String Value;
        Product products = getItem(position);

        if(value > 0)
        {
            if(products.CartQuantity >=  0) {
                products.ProductPrice = products.ProductPrice + 1;
            }
        } // Boton negativo
        else
        {
            if(products.ProductPrice > 0)
            {
                products.ProductPrice = products.ProductPrice - 1;
            }
        }  // Boton Positivo

        tvPrice.setText(products.ProductPrice+"");

        for (int i = 0; i<getCount();i++){
            Product producto = getItem(i);

            if(producto.ProductName.equals(indice)){
                producto.ProductPrice = Double.valueOf(products.ProductPrice+"");

           }
        }

    }

    private void updateQuantityPriceAll(int position, TextView tvPrice, int value) {
        Product products = getItem(position);

        tvPrice.setText(products.ProductPrice+"");
    }

    private void recorrePromocionesSelecciones(){

        ArrayList<Product> listaPromociionSelectivo = new ArrayList<>();

        for (int i = 0; i < getCount(); i++){

            Product producto = new Product();
            producto = getItem(i);
            listaPromociionSelectivo.add(producto);

        }



    }
}