/**
 * Clase RecyclerViewAdapterRecibo que se encarga de manejar el adaptador para la lista de detalles de recibo.
 * Esta clase extiende de RecyclerView.Adapter y se utiliza para mostrar la información de los detalles de recibo en una lista.
 */
package com.achtosoftware.inventario_achto.RECIBO;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.achtosoftware.inventario_achto.ActividadesPantallas.PopupManager;
import com.achtosoftware.inventario_achto.ActividadesPantallas.inicio_sesion;
import com.achtosoftware.inventario_achto.R;
import com.google.android.material.textfield.TextInputLayout;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class RecyclerViewAdapterRecibo extends RecyclerView.Adapter<RecyclerViewAdapterRecibo.ViewHolder> {
    private Context context;
    private ArrayList<SQLreciboDetalle> SQLreciboDetalles;
    public String proveedor;

    /**
     * Constructor de la clase RecyclerViewAdapterRecibo.
     *
     * @param context           el contexto de la aplicación.
     * @param SQLreciboDetalles el ArrayList de los detalles de recibo.
     */
    public RecyclerViewAdapterRecibo(Context context, ArrayList<SQLreciboDetalle> SQLreciboDetalles) {
        this.context = context;
        this.SQLreciboDetalles = new ArrayList<>(SQLreciboDetalles);
    }

    /**
     * Método que crea una nueva instancia de ViewHolder.
     *
     * @param parent   el ViewGroup padre en el que se añadirá la nueva vista.
     * @param viewType el tipo de vista.
     * @return el ViewHolder creado.
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardview_recibo, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        view.setLayoutParams(layoutParams);
        return new ViewHolder(view);
    }

    /**
     * Método que se encarga de realizar el enlace de datos entre el ViewHolder y los datos en una posición específica.
     *
     * @param holder   el ViewHolder en el que se mostrarán los datos.
     * @param position la posición de los datos en la lista.
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        SQLreciboDetalle SQLrecibodetalle = SQLreciboDetalles.get(position);
        holder.codigo.setText(String.valueOf(SQLrecibodetalle.getCodigo()));
        holder.descripcion.setText(SQLrecibodetalle.getDescripcion());
        holder.Cantsol.setText(String.valueOf(SQLrecibodetalle.getCantidadSolicitada()));
        holder.CantFis.setText(String.valueOf(SQLrecibodetalle.getCantidadFisica()));

        if (SQLrecibodetalle.getEstatus() == 1) {
            holder.Estatus.setCardBackgroundColor(Color.parseColor("#FFA500"));
            holder.completado.setVisibility(View.GONE);
            holder.arecibir.setVisibility(View.VISIBLE);
        } else if (SQLrecibodetalle.getEstatus() == 2) {
            holder.Estatus.setCardBackgroundColor(Color.parseColor("#00E676"));
            holder.completado.setVisibility(View.VISIBLE);
            holder.arecibir.setVisibility(View.GONE);
        } else {
            holder.Estatus.setCardBackgroundColor(Color.parseColor("#FF085FEF"));
            holder.completado.setVisibility(View.GONE);
            holder.arecibir.setVisibility(View.VISIBLE);
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

        holder.cantidadArecibir.setFocusable(true);
        holder.cantidadArecibir.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {

                    String User = inicio_sesion.username;
                    String fecha = obtenerFechaActual();
                    String descripcion = holder.descripcion.getText().toString();
                    String pedido = SQLrecibodetalle.getPedido();
                    String recibir = holder.cantidadArecibir.getText().toString();
                    String codigo = holder.codigo.getText().toString();
                    int fisica = Integer.parseInt(holder.CantFis.getText().toString());
                    int soli = Integer.parseInt(holder.Cantsol.getText().toString());


                    if (!recibir.isEmpty()) {

                        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        holder.cantidadArecibir.setText("");

                        int cantidadRecibida = Integer.parseInt(recibir);


                        if (cantidadRecibida > 0 && fisica < soli) {

                            int nuevacantidadrecibida = fisica + cantidadRecibida;

                            if (nuevacantidadrecibida <= soli) {

                                ActualizarReciboDetTask task = new ActualizarReciboDetTask(holder, descripcion, pedido, recibir, codigo, fisica, soli, position, fecha, User);
                                task.execute();
                                return true;

                            } else if (cantidadRecibida > soli) {

                                mostrarPopupwarning();

                            } else {

                                mostrarPopupwarning();

                            }
                        } else if (cantidadRecibida == 0) {

                            Toast.makeText(context, "DEBE AGREGAR UN NUMERO MAYOR A 0", Toast.LENGTH_SHORT).show();

                        }
                        return true;
                    }
                    return false;
                }
                return false;
            }
            private String obtenerFechaActual() {

                String formatoFecha = "yyyy-MM-dd HH:mm:ss";
                Date fechaActual = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat(formatoFecha, Locale.getDefault());
                String fechaFormateada = dateFormat.format(fechaActual);

                return fechaFormateada;
            }
        });
    }

    /**
     * Método que devuelve la cantidad de elementos en la lista.
     *
     * @return la cantidad de elementos en la lista.
     */
    @Override
    public int getItemCount() {
        return SQLreciboDetalles.size();
    }

    /**
     * Clase ViewHolder que se encarga de manejar los elementos de la vista de cada elemento en la lista.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView codigo, descripcion, Cantsol, CantFis;
        CardView Estatus;
        ImageView masdetalles, menosdetalles, completado;
        EditText cantidadArecibir;
        TextInputLayout arecibir;
        ProgressBar progresbarrecibir;

        /**
         * Constructor de la clase ViewHolder.
         *
         * @param itemView la vista de cada elemento en la lista.
         */
        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            codigo = itemView.findViewById(R.id.codigo);
            descripcion = itemView.findViewById(R.id.descripcionarticulo);
            Cantsol = itemView.findViewById(R.id.cantidadsolicitada);
            CantFis = itemView.findViewById(R.id.cantidadrecibida);

            Estatus = itemView.findViewById(R.id.estatusarticulo);

            masdetalles = itemView.findViewById(R.id.imagenmas);
            menosdetalles = itemView.findViewById(R.id.imagenmenos);
            completado = itemView.findViewById(R.id.completado);

            cantidadArecibir = itemView.findViewById(R.id.editTextCantidad);
            arecibir = itemView.findViewById(R.id.arecibir);
            progresbarrecibir = itemView.findViewById(R.id.progressBarrecibir);

        }
    }

    /**
     * Clase ActualizarReciboDetTask que se encarga de actualizar los detalles de recibo en la base de datos.
     */
    public class ActualizarReciboDetTask extends AsyncTask<Void, Void, Void> {
        private String descripcion, pedido, recibir, codigo, fecha, User;
        private int fisica, soli, position;
        private ViewHolder holder;

        /**
         * Constructor de la clase ActualizarReciboDetTask.
         *
         * @param holder      el ViewHolder en el que se mostrarán los datos actualizados.
         * @param descripcion la descripción del artículo.
         * @param pedido      el número de pedido.
         * @param recibir     la cantidad recibida.
         * @param codigo      el código del artículo.
         * @param fisica      la cantidad física.
         * @param soli        la cantidad solicitada.
         * @param position    la posición del elemento en la lista.
         */
        public ActualizarReciboDetTask(ViewHolder holder, String descripcion, String pedido, String recibir, String codigo, int fisica, int soli, int position, String fecha, String User) {
            this.descripcion = descripcion;
            this.pedido = pedido;
            this.recibir = recibir;
            this.codigo = codigo;
            this.fisica = fisica;
            this.soli = soli;
            this.holder = holder;
            this.position = position;
            this.fecha = fecha;
            this.User = User;
        }

        /**
         * Método que se ejecuta antes de actualizar los detalles de recibo en la base de datos.
         */
        @Override
        protected void onPreExecute() {
            holder.progresbarrecibir.setVisibility(View.VISIBLE);
            holder.arecibir.setVisibility(View.GONE);
        }

        /**
         * Método que se encarga de actualizar los detalles de recibo en la base de datos.
         *
         * @param params los parámetros para actualizar los detalles de recibo.
         * @return null
         */
        @Override
        protected Void doInBackground(Void... params) {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            try (Connection conn = databaseConnection.getConnection()) {


                String sql = "EXEC ActualizarReciboDet ?, ?, ?, ?, ?, ?, ?, ?";
                CallableStatement cstmt = conn.prepareCall(sql);
                cstmt.setString(1, descripcion);
                cstmt.setString(2, pedido);
                cstmt.setString(3, recibir);
                cstmt.setString(4, codigo);
                cstmt.setInt(5, fisica);
                cstmt.setInt(6, soli);
                cstmt.setString(7, fecha);
                cstmt.setString(8, User);
                cstmt.executeUpdate();

                fisica = fisica + Integer.parseInt(recibir);

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * Método que se ejecuta después de actualizar los detalles de recibo en la base de datos.
         *
         * @param aVoid
         */
        @Override
        protected void onPostExecute(Void aVoid) {
            holder.CantFis.setText(String.valueOf(fisica));
            if (fisica < soli) {

                holder.Estatus.setCardBackgroundColor(Color.parseColor("#FFA500"));
                holder.arecibir.setVisibility(View.VISIBLE);

            } else if (fisica == soli) {

                holder.completado.setVisibility(View.VISIBLE);
                holder.arecibir.setVisibility(View.GONE);
                holder.Estatus.setCardBackgroundColor(Color.parseColor("#00E676"));

            }

            holder.progresbarrecibir.setVisibility(View.GONE);
            // Actualiza la lista SQLreciboDetalles con los datos actualizados
            SQLreciboDetalles.get(position).setCantidadFisica(fisica);
            SQLreciboDetalles.get(position).setEstatus(fisica == soli ? 2 : 1);
            // Notifica al adaptador que los datos han cambiado
            notifyDataSetChanged();
        }
    }
    /**
     * Clase DatabaseConnection que se encarga de establecer la conexión a la base de datos.
     */
    public class DatabaseConnection {
        private static final String DB_URL = "jdbc:jtds:sqlserver://192.168.0.170:1433;databaseName=achto";
        private static final String USERNAME = "SA";
        private static final String PASSWORD = "TOSCA";
        /**
         * Método que establece la conexión a la base de datos.
         *
         * @return la conexión establecida.
         * @throws SQLException si ocurre un error al establecer la conexión.
         */
        public Connection getConnection() throws SQLException {
            return DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
        }
    }
    /**
     * Método que muestra un popup de advertencia.
     */
    private void mostrarPopupwarning() {
        PopupManager.mostrarPopupwarning(context);
    }
}