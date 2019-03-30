package com.example.sistemas.appinventarioweb.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.sistemas.appinventarioweb.Entities.RegistroInventario;
import com.example.sistemas.appinventarioweb.R;
import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<RegistroInventario> implements View.OnClickListener {

    private ArrayList<RegistroInventario> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView tvCantidadInventario;
        TextView tvFecha;
        TextView tvHora;
    }

    public CustomAdapter(ArrayList<RegistroInventario> data, Context context) {
        super(context, R.layout.row_item, data);
        this.dataSet = data;
        this.mContext=context;

    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        RegistroInventario dataModel=(RegistroInventario) object;

    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        RegistroInventario dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item, parent, false);
            viewHolder.tvCantidadInventario = (TextView) convertView.findViewById(R.id.tvCantidadRegistroInventario);
            viewHolder.tvFecha = (TextView) convertView.findViewById(R.id.tvFechaRegistroInventario);
            viewHolder.tvHora = (TextView) convertView.findViewById(R.id.tvHoraRegistroInventario);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }


        viewHolder.tvCantidadInventario.setText(dataModel.getCantidad());
        viewHolder.tvFecha.setText(dataModel.getFecha());
        viewHolder.tvHora.setText(dataModel.getHora());

        // Return the completed view to render on screen
        return convertView;
    }
}
