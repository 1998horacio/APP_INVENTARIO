package com.achtosoftware.inventario_achto.EMBARQUE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import com.achtosoftware.inventario_achto.ActividadesPantallas.inicio_menu;
import com.achtosoftware.inventario_achto.R;
import com.achtosoftware.inventario_achto.SQL.DatabaseConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
/**
 * Clase para la pantalla de cantidad de embarque, donde me muestra el los articulos filtrados por
 * el pedido
 */
public class cantidad_embarque extends AppCompatActivity implements View.OnKeyListener {

    // Declaración de variables miembro de la clase
    private RecyclerViewAdapterEmbarque adapter;
    private RecyclerView recyclerView;
    private SearchView searchView;
    private DatabaseConnection databaseConnection;
    private TextView clienteTextView, buscaPedidoTextView;
    private LinearLayout linearLayoutCliente, linearLayoutBuscaPedido;
    private ProgressBar progressBarEmbarque;

    /** Método "onCreate" que se ejecuta al crear la actividad
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_embarque);

        // Inicialización de vistas y variables

        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.searchView);
        clienteTextView = findViewById(R.id.cliente);
        progressBarEmbarque = findViewById(R.id.progressBarembarque);
        buscaPedidoTextView = findViewById(R.id.embarquepedido);
        linearLayoutCliente = findViewById(R.id.recibolayoutcliente);
        linearLayoutBuscaPedido = findViewById(R.id.linearlayoutbuscapedido);
        databaseConnection = new DatabaseConnection();

        // Configuración de un listener para el icono de búsqueda

        LinearLayout layoutSearch = findViewById(R.id.layoutsearch);
        layoutSearch.setOnClickListener(v -> searchView.setIconified(false));


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                searchView.setFocusable(false);
                searchView.clearFocus();
                progressBarEmbarque.setVisibility(View.VISIBLE);
                linearLayoutCliente.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);

                filter(query);

                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
    /** Método privado para filtrar los resultados de búsqueda
     *
     * @param query
     */
    private void filter(String query) {
        new FilterAsyncTask().execute(query);
    }

    /** Clase interna para realizar filtrado de forma asíncrona
     *
     */
    private class FilterAsyncTask extends AsyncTask<String, Void, FilterResult> {

        @Override
        protected void onPreExecute() {

            progressBarEmbarque.setVisibility(View.VISIBLE);
            linearLayoutCliente.setVisibility(View.GONE);
            linearLayoutBuscaPedido.setVisibility(View.GONE);

        }

        @Override
        protected FilterResult doInBackground(String... params) {

            String query = params[0];
            FilterResult result = new FilterResult();

            try (Connection conn = DriverManager.getConnection("jdbc:jtds:sqlserver://192.168.0.170;" +
                    "1433;" +
                    "databaseName=achto;" +
                    "user=SA;" +
                    "password=TOSCA")) {

                // Obtener la fecha actual
                Calendar calendar = Calendar.getInstance();
                Date date = calendar.getTime();

                @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String fechaActual = dateFormat.format(date);

                String sql = "SELECT Cliente FROM Embarque WHERE Pedido = '" + query + "'";
                String ifNotExists = " SELECT Pedido FROM EmbarqueDet WHERE Pedido = '" + query + "'";

                String updateFechaEmbarque = "UPDATE Embarque SET FechaEmbarque = '" + fechaActual + "' WHERE Pedido IN (SELECT Pedido FROM EmbarqueDet WHERE Estatus = 2 GROUP BY Pedido HAVING COUNT(*) = (SELECT COUNT(*) FROM EmbarqueDet WHERE Pedido = Embarque.Pedido));\n ";
                String updateEstatusEmbarque = "UPDATE Embarque SET Estatus = 2 WHERE Pedido IN (SELECT Pedido FROM EmbarqueDet WHERE Estatus = 2 GROUP BY Pedido HAVING COUNT(*) = (SELECT COUNT(*) FROM EmbarqueDet WHERE Pedido = Embarque.Pedido));\n ";

                Statement stmt = conn.createStatement();
                stmt.executeUpdate(updateFechaEmbarque);
                stmt.executeUpdate(updateEstatusEmbarque);

                ResultSet resultSet = databaseConnection.executeSelect(sql);

                if (resultSet.next()) {

                    result.setCliente(resultSet.getString("Cliente"));
                    result.setEmbarqueDetalles(databaseConnection.getembarqueDetalles(query));

                } else if (!sql.equals(ifNotExists)) {

                    result.setNotFound(true);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(FilterResult result) {
            progressBarEmbarque.setVisibility(View.GONE);

            if (result.isNotFound()) {

                linearLayoutBuscaPedido.setVisibility(View.VISIBLE);
                linearLayoutCliente.setVisibility(View.GONE);
                buscaPedidoTextView.setText("¡OOPS! NO SE ENCONTRÓ EL PEDIDO\nVUELVE A BUSCAR");

            } else {

                linearLayoutBuscaPedido.setVisibility(View.GONE);
                linearLayoutCliente.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.VISIBLE);
                clienteTextView.setText(result.getCliente());

                ArrayList<SQLembarqueDetalle> originalSQLembarqueDetalles = result.getEmbarqueDetalles();
                adapter = new RecyclerViewAdapterEmbarque(cantidad_embarque.this, originalSQLembarqueDetalles);
                recyclerView.setLayoutManager(new LinearLayoutManager(cantidad_embarque.this));
                recyclerView.setAdapter(adapter);
                adapter.cliente = result.getCliente();
            }
        }
    }

    /** Método que se ejecuta al recibir un resultado de otra actividad
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {

            String restoredQuery = data.getStringExtra("SearchQuery");

            if (restoredQuery != null && !restoredQuery.isEmpty()) {

                searchView.setQuery(restoredQuery, false);
                filter(restoredQuery);
            }
        }
    }

    /** Método que se ejecuta al presionar el botón "Atrás" del dispositivo
     *
     */
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, inicio_menu.class);
        startActivity(intent);
    }

    /** Método del listener de teclado que no se utiliza en este código
     *
     * @param view
     * @param i
     * @param keyEvent
     * @return
     */
    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        return false;
    }

    /**Método que se ejecuta cuando la actividad se reanuda
     *
     */
    public void onResume() {
        super.onResume();
        try {

            recyclerView.setVisibility(View.GONE);
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
            if (searchView.getQuery().toString() == null || searchView.getQuery().toString().equals("")) {

            } else {
                filter(searchView.getQuery().toString());
            }
        } catch (Exception e) {

        }
    }

    /** Clase interna que representa el resultado del filtrado
     *
     */
    private static class FilterResult {
        private String cliente;
        private ArrayList<SQLembarqueDetalle> embarqueDetalles;
        private boolean notFound;

        /** Getters y setters para los miembros de la clase
         *
         * @return
         */
        public String getCliente() {
            return cliente;
        }
        public void setCliente(String cliente) {
            this.cliente = cliente;
        }

        public ArrayList<SQLembarqueDetalle> getEmbarqueDetalles() {
            return embarqueDetalles;
        }
        public void setEmbarqueDetalles(ArrayList<SQLembarqueDetalle> embarqueDetalles) {
            this.embarqueDetalles = embarqueDetalles;
        }

        public boolean isNotFound() {
            return notFound;
        }
        public void setNotFound(boolean notFound) {
            this.notFound = notFound;
        }
    }
}