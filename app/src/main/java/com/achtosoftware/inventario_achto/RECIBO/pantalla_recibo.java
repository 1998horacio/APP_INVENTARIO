/**
 * Clase para la pantalla de recibo, aqui se busca el pedido en un search y muestra
 * en un recycler los articulos que tiene el pedido buscado
 */
package com.achtosoftware.inventario_achto.RECIBO;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class pantalla_recibo extends AppCompatActivity implements View.OnKeyListener {

    /** Declaración de variables miembro de la clase
     *
     */
    private RecyclerViewAdapterRecibo adapter;
    private ArrayList<SQLreciboDetalle> originalSQLreciboDetalles;
    private RecyclerView recyclerView;
    private SearchView searchView;
    private DatabaseConnection databaseConnection;
    private TextView ProveedorTextView, buscapedido;
    private LinearLayout linearLayoutCliente, linearLayoutbuscapedido;
    private ProgressBar progresbarrecibo;

    /** Método "onCreate" que se ejecuta al crear la actividad
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_recibo);

        // Inicialización de vistas y variables miembro
        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.searchView);

        ProveedorTextView = findViewById(R.id.cliente);

        progresbarrecibo = findViewById(R.id.progressBarRecibo);
        buscapedido = findViewById(R.id.buscapedido);
        linearLayoutCliente = findViewById(R.id.recibolayoutcliente);
        linearLayoutbuscapedido = findViewById(R.id.linearlayoutbuscapedido);

        databaseConnection = new DatabaseConnection();

        // Configuración de un listener para el icono de búsqueda
        LinearLayout layoutSearch = findViewById(R.id.layoutsearch);
        layoutSearch.setOnClickListener(v -> searchView.setIconified(false));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                searchView.setFocusable(false);
                searchView.clearFocus();
                progresbarrecibo.setVisibility(View.VISIBLE);
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

            progresbarrecibo.setVisibility(View.VISIBLE);
            linearLayoutCliente.setVisibility(View.GONE);
            linearLayoutbuscapedido.setVisibility(View.GONE);

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

                String sql = "SELECT Proveedor FROM Recibo WHERE Pedido = '" + query + "'";
                String If = "SELECT Pedido FROM ReciboDet WHERE Pedido = '" + query + "'";

                String FechaRecibo = "UPDATE Recibo SET FechaRecibo = '" + fechaActual + "' WHERE Pedido IN (SELECT Pedido FROM ReciboDet WHERE Estatus = 2 GROUP BY Pedido HAVING COUNT(*) = (SELECT COUNT(*) FROM ReciboDet WHERE Pedido = Recibo.Pedido));";
                String estatusrecibo = "UPDATE Recibo SET Estatus = 2 WHERE Pedido IN (SELECT Pedido FROM ReciboDet WHERE Estatus = 2 GROUP BY Pedido HAVING COUNT(*) = (SELECT COUNT(*) FROM ReciboDet WHERE Pedido = Recibo.Pedido));";

                Statement stmt = conn.createStatement();
                stmt.executeUpdate(FechaRecibo);
                stmt.executeUpdate(estatusrecibo);

                ResultSet resultSet = databaseConnection.executeSelect(sql);

                if (resultSet.next()) {

                    result.setProveedor(resultSet.getString("Proveedor"));
                    result.setReciboDetalles(databaseConnection.getReciboDetalles(query));

                } else if (!sql.equals(If)) {

                    result.setNotFound(true);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(FilterResult result) {
            progresbarrecibo.setVisibility(View.GONE);

            if (result.isNotFound()) {

                progresbarrecibo.setVisibility(View.GONE);
                linearLayoutbuscapedido.setVisibility(View.VISIBLE);
                linearLayoutCliente.setVisibility(View.GONE);
                buscapedido.setText("¡OOPS! NO SE ENCONTRÓ EL PEDIDO\nVUELVE A BUSCAR");

            } else {

                linearLayoutbuscapedido.setVisibility(View.GONE);
                linearLayoutCliente.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.VISIBLE);
                ProveedorTextView.setText(result.getProveedor());

                originalSQLreciboDetalles = result.getReciboDetalles();
                adapter = new RecyclerViewAdapterRecibo(pantalla_recibo.this, originalSQLreciboDetalles);
                recyclerView.setLayoutManager(new LinearLayoutManager(pantalla_recibo.this));
                recyclerView.setAdapter(adapter);
                adapter.proveedor = result.getProveedor();
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


    /** Método que se ejecuta cuando la actividad se reanuda
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

    /**Clase interna que representa el resultado del filtrado
    *
     **/
    private static class FilterResult {
        private String proveedor;
        private ArrayList<SQLreciboDetalle> reciboDetalles;
        private boolean notFound;

        /**
        *Getters y setters para los miembros de la clase
         */
        public String getProveedor() {
            return proveedor;
        }
        public void setProveedor(String proveedor) {
            this.proveedor = proveedor;
        }
        public ArrayList<SQLreciboDetalle> getReciboDetalles() {
            return reciboDetalles;
        }
        public void setReciboDetalles(ArrayList<SQLreciboDetalle> reciboDetalles) {
            this.reciboDetalles = reciboDetalles;
        }
        public boolean isNotFound() {
            return notFound;
        }
        public void setNotFound(boolean notFound) {
            this.notFound = notFound;
        }
    }
}