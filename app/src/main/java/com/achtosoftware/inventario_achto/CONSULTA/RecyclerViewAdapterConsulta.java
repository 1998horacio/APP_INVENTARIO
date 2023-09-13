/**
 * Este adaptador se utiliza para mostrar elementos de consulta en un RecyclerView y realizar búsquedas.
 */
package com.achtosoftware.inventario_achto.CONSULTA;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.achtosoftware.inventario_achto.EMBARQUE.RecyclerViewAdapterEmbarque;
import com.achtosoftware.inventario_achto.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Adaptador para mostrar elementos de consulta en un RecyclerView y realizar búsquedas.
 */
public class RecyclerViewAdapterConsulta extends RecyclerView.Adapter<RecyclerViewAdapterConsulta.ViewHolderInventario> implements Filterable {
    private Context context;
    private List<SQLconsulta> SQLconsultas;
    private List<SQLconsulta> SQLconsultasFull;

    /**
     * Constructor del adaptador.
     * @param context el contexto de la aplicación
     * @param SQLconsultas la lista de consultas a mostrar
     */
    public RecyclerViewAdapterConsulta(Context context, List<SQLconsulta> SQLconsultas) {
        this.context = context;
        this.SQLconsultas = SQLconsultas;
        this.SQLconsultasFull = new ArrayList<>(SQLconsultas);
    }

    /**
     * Crea una nueva instancia de ViewHolderInventario.
     * @param parent el ViewGroup padre
     * @param viewType el tipo de vista
     * @return una nueva instancia de ViewHolderInventario
     */
    @NonNull
    @Override
    public ViewHolderInventario onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardview_consulta1, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        view.setLayoutParams(layoutParams);
        return new RecyclerViewAdapterConsulta.ViewHolderInventario(view);
    }

    /**
     * Vincula los datos de una consulta con los elementos de la vista.
     * @param holder el ViewHolderInventario
     * @param position la posición del elemento en la lista
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolderInventario holder, int position) {
        SQLconsulta consulta = SQLconsultas.get(position);
        // Establecer los valores de los elementos de la vista con los datos de la consulta
        holder.codigo.setText(String.valueOf(consulta.getCodigoconsulta()));
        holder.descripcion.setText(consulta.getDescripcionconsulta());
        holder.cantidad.setText(consulta.getCantidadconsulta() + " unidad(es)");
        // Configurar la vista según el estado de la consulta
        if (consulta.getCantidadconsulta() > 0) {
            holder.colorEstatus.setBackgroundColor(Color.parseColor("#00E676"));
        } else if (consulta.getCantidadconsulta() == 0) {
            holder.colorEstatus.setBackgroundColor(Color.parseColor("#FF9100"));
        }
        holder.masdetalles.setOnClickListener(view -> {

            holder.masdetalles.setVisibility(View.GONE);
            holder.menosdetalles.setVisibility(View.VISIBLE);
            holder.descripcion.setMaxLines(Integer.MAX_VALUE);

        });

        holder.menosdetalles.setOnClickListener(view -> {

            holder.masdetalles.setVisibility(View.VISIBLE);
            holder.menosdetalles.setVisibility(View.GONE);
            holder.descripcion.setMaxLines(1);

        });
    }

    /**
     * Devuelve el número de elementos en la lista de consultas.
     * @return el número de elementos en la lista
     */
    @Override
    public int getItemCount() {
        return SQLconsultas.size();
    }

    /**
     * Devuelve el filtro utilizado para realizar búsquedas en la lista de consultas.
     * @return el filtro utilizado para realizar búsquedas
     */
    @Override
    public Filter getFilter() {
        return SQLconsultasFilter;
    }

    /**
     * Filtro utilizado para realizar búsquedas en la lista de consultas.
     */
    private Filter SQLconsultasFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<SQLconsulta> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(SQLconsultasFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (SQLconsulta consulta : SQLconsultasFull) {
                    if (String.valueOf(consulta.getCodigoconsulta()).toLowerCase().contains(filterPattern)) {
                        filteredList.add(consulta);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            SQLconsultas.clear();
            SQLconsultas.addAll((List<SQLconsulta>) results.values);
            notifyDataSetChanged();
        }
    };
    /**
     * ViewHolder para mostrar los elementos de consulta en el RecyclerView.
     */
    public class ViewHolderInventario extends RecyclerView.ViewHolder {
        TextView codigo, descripcion, cantidad;
        CardView colorEstatus;

        ImageView masdetalles, menosdetalles;

        /**
         * Constructor del ViewHolderInventario.
         * @param itemView la vista del elemento de consulta
         */
        public ViewHolderInventario(@NonNull View itemView) {
            super(itemView);
            codigo = itemView.findViewById(R.id.Codigo_Consulta);
            descripcion = itemView.findViewById(R.id.Descripcion_Consulta);
            cantidad = itemView.findViewById(R.id.Cantidadinventario_consulta);
            colorEstatus = itemView.findViewById(R.id.colorDeEstatus_consulta);
            masdetalles = itemView.findViewById(R.id.imagenmas);
            menosdetalles = itemView.findViewById(R.id.imagenmenos);
        }
    }
}