package com.achtosoftware.inventario_achto.EMBARQUE;
/**Clase RecyclerViewAdapterEmbarque que se encarga de manejar
 * el adaptador para la lista de detalles de embarque.
 * Esta clase extiende de RecyclerView.Adapter y se utiliza para mostrar la información de los
 * detalles de embarque en una lista.
 */

import static com.achtosoftware.inventario_achto.ActividadesPantallas.inicio_sesion.username;

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
import com.achtosoftware.inventario_achto.R;
import com.google.android.material.textfield.TextInputLayout;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RecyclerViewAdapterEmbarque extends RecyclerView.Adapter<RecyclerViewAdapterEmbarque.ViewHolder> {
    private Context context;
    private ArrayList<SQLembarqueDetalle> SQLembarqueDetalles;
    public String cliente;
    /**
     * Constructor de la clase RecyclerViewAdapterEmbarque.
     *
     * @param context                el contexto de la aplicación.
     * @param SQLembarqueDetalle el ArrayList de los detalles de embarque.
     */
    public RecyclerViewAdapterEmbarque(Context context, ArrayList<SQLembarqueDetalle> SQLembarqueDetalle) {
        this.context = context;
        this.SQLembarqueDetalles = new ArrayList<>(SQLembarqueDetalle);
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

        View view = LayoutInflater.from(context).inflate(R.layout.cardview_embarque, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
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

        SQLembarqueDetalle SQLembarquedetalles = SQLembarqueDetalles.get(position);

        holder.codigo.setText(String.valueOf(SQLembarquedetalles.getCodigo()));
        holder.descripcion.setText(SQLembarquedetalles.getDescripcion());
        holder.CantSol.setText(String.valueOf(SQLembarquedetalles.getCantidadSolicitada()));
        holder.CantEmbarcar.setText(String.valueOf(SQLembarquedetalles.getCantidadEmbarque()));
        holder.CantFis.setText(String.valueOf(SQLembarquedetalles.getCantidadFisica()));
        holder.completado.setVisibility(View.GONE);
        holder.embarcar.setVisibility(View.VISIBLE);

        if (SQLembarquedetalles.getEstatus() == 1) {

            holder.Estatus.setCardBackgroundColor(Color.parseColor("#FFA500"));
            holder.completado.setVisibility(View.GONE);
            holder.cantidadAembarcar.setVisibility(View.VISIBLE);

        } else if (SQLembarquedetalles.getEstatus() == 2) {

            holder.Estatus.setCardBackgroundColor(Color.parseColor("#00E676"));
            holder.completado.setVisibility(View.VISIBLE);
            holder.embarcar.setVisibility(View.GONE);

        } else {

            holder.Estatus.setCardBackgroundColor(Color.parseColor("#FF085FEF"));
            holder.completado.setVisibility(View.GONE);
            holder.cantidadAembarcar.setVisibility(View.VISIBLE);

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

        holder.cantidadAembarcar.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                    String fecha = obtenerFechaActual();
                    String pedido = SQLembarquedetalles.getPedido();
                    String recibir = holder.cantidadAembarcar.getText().toString();
                    String codigo = holder.codigo.getText().toString();
                    int embarcar = Integer.parseInt(holder.CantEmbarcar.getText().toString());
                    int soli = Integer.parseInt(holder.CantSol.getText().toString());
                    int fisica = Integer.parseInt(holder.CantFis.getText().toString());

                    if (!recibir.isEmpty()) {

                        holder.cantidadAembarcar.setText("");
                        int cantidadAembarcar = Integer.parseInt(recibir);

                        if (cantidadAembarcar > 0 && embarcar < soli && cantidadAembarcar <= fisica) {
                            int nuevacantidaembarcar = embarcar + cantidadAembarcar;

                            if (nuevacantidaembarcar <= soli) {
                                // Llamar al procedimiento almacenado
                                ActualizarEmbarqueTask task = new ActualizarEmbarqueTask(holder, codigo, pedido, recibir, soli, embarcar, fisica, position, fecha, username);
                                task.execute();
                                return true;
                            } else if (cantidadAembarcar > soli) {
                                mostrarPopupwarning();
                            } else {
                                mostrarPopupwarning();
                            }
                        } else if (cantidadAembarcar == 0) {
                            Toast.makeText(context, "DEBE AGREGAR UN NUMERO MAYOR A 0", Toast.LENGTH_SHORT).show();
                        } else if (cantidadAembarcar > fisica) {
                            mostrarPopupwarningembarquefisico();
                        }
                    } else {
                        Toast.makeText(context, "Debe de agregar un numero", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
                return false;
            }
            private String obtenerFechaActual() {
                String formatoFecha = "yyyy-MM-dd";
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
        return SQLembarqueDetalles.size();
    }

    /**
     * Clase ViewHolder que se encarga de manejar los elementos de la vista de cada elemento en la lista.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView codigo, descripcion, CantSol, CantEmbarcar, CantFis;
        ImageView masdetalles, menosdetalles, completado;
        CardView Estatus;
        EditText cantidadAembarcar;
        TextInputLayout embarcar;
        ProgressBar embarcarporgresbar;

        /**
         * Constructor de la clase ViewHolder.
         *
         * @param itemView la vista de cada elemento en la lista.
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Estatus = itemView.findViewById(R.id.estatusarticulo);
            codigo = itemView.findViewById(R.id.codigo);
            descripcion = itemView.findViewById(R.id.descripcionarticulo);
            CantSol = itemView.findViewById(R.id.cantidadsolicitada);
            CantEmbarcar = itemView.findViewById(R.id.cantidadembarcada);
            masdetalles = itemView.findViewById(R.id.imagenmas);
            menosdetalles = itemView.findViewById(R.id.imagenmenos);
            completado = itemView.findViewById(R.id.completado);
            cantidadAembarcar = itemView.findViewById(R.id.editTextCantidadembarcar);
            embarcar = itemView.findViewById(R.id.embarcar);
            embarcarporgresbar = itemView.findViewById(R.id.progressBarembarcar);
            CantFis = itemView.findViewById(R.id.cantidadexistente);
        }
    }

    /**
     * Método que muestra un popup de advertencia.
     */
    private void mostrarPopupwarning() {
        PopupManager.mostrarPopupwarningembarque(context);
    }

    /**
     * Método que muestra un popup de advertencia para el embarque físico.
     */
    private void mostrarPopupwarningembarquefisico() {
        PopupManager.mostrarPopupwarningembarquefisico(context);
    }

    /**
     * Clase AsyncTask que se encarga de actualizar el embarque en la base de datos.
     */
    public class ActualizarEmbarqueTask extends AsyncTask<Void, Void, Void> {
        private ViewHolder holder;
        private String codigo, pedido, recibir, fecha, username;
        private int soli, embarcar, position, fisica;

        /**
         * Constructor de la clase ActualizarEmbarqueTask.
         *
         * @param holder   el ViewHolder en el que se mostrarán los datos actualizados.
         * @param codigo   el código del artículo.
         * @param pedido   el número de pedido.
         * @param recibir  la cantidad recibida.
         * @param soli     la cantidad solicitada.
         * @param embarcar la cantidad a embarcar.
         * @param fisica   la cantidad física.
         * @param position la posición del elemento en la lista.
         * @param fecha la fecha en la que se embarco el articulo.
         * @param username es el usuario que embarco el articulo.
         */
        public ActualizarEmbarqueTask(ViewHolder holder, String codigo,
                                      String pedido, String recibir, int soli, int embarcar,
                                      int fisica, int position, String fecha, String username) {
            this.holder = holder;
            this.codigo = codigo;
            this.pedido = pedido;
            this.soli = soli;
            this.embarcar = embarcar;
            this.recibir = recibir;
            this.position = position;
            this.fisica = fisica;
            this.fecha = fecha;
            this.username = username;
        }

        /**
         * Método que se ejecuta antes de realizar la actualización del embarque.
         */
        @Override
        protected void onPreExecute() {
            holder.embarcarporgresbar.setVisibility(View.VISIBLE);
            holder.embarcar.setVisibility(View.GONE);
        }

        /**
         * Método que se encarga de realizar la actualización del embarque en la base de datos.
         *
         * @param params los parámetros para la actualización del embarque.
         * @return null
         */
        @Override
        protected Void doInBackground(Void... params) {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            try (Connection conn = databaseConnection.getConnection()) {

                // Llamar al procedimiento almacenado
                String sql = "{call ActualizarEmbarqueDetalle(?, ?, ?, ?, ?, ?, ?)}";
                CallableStatement cstmt = conn.prepareCall(sql);
                cstmt.setInt(1, Integer.parseInt(codigo));
                cstmt.setString(2, pedido);
                cstmt.setInt(3, Integer.parseInt(recibir));
                cstmt.setInt(4, soli);
                cstmt.setInt(5, fisica);
                cstmt.setString(6, fecha);
                cstmt.setString(7, username);
                cstmt.executeUpdate();

                embarcar = embarcar + Integer.parseInt(recibir);
                fisica = fisica - Integer.parseInt(recibir);

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }


        /**
         * Método que se ejecuta después de realizar la actualización del embarque.
         *
         * @param aVoid
         */
        @Override
        protected void onPostExecute(Void aVoid) {
            holder.CantEmbarcar.setText(String.valueOf(embarcar));
            holder.CantFis.setText(String.valueOf(fisica));

            if (embarcar < soli) {

                holder.Estatus.setCardBackgroundColor(Color.parseColor("#FFA500"));
                holder.embarcar.setVisibility(View.VISIBLE);

            } else if (embarcar == soli) {

                holder.Estatus.setCardBackgroundColor(Color.parseColor("#00E676"));
                holder.completado.setVisibility(View.VISIBLE);
            }

            holder.embarcarporgresbar.setVisibility(View.GONE);

            SQLembarqueDetalles.get(position).setCantidadembarque(embarcar);
            SQLembarqueDetalles.get(position).setCantidadfisica(fisica);
            SQLembarqueDetalles.get(position).setEstatus(embarcar == soli ? 2 : 1);

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
}