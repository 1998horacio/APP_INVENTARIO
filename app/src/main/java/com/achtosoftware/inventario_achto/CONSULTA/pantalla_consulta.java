/**
 * Esta clase representa la pantalla de consulta de la aplicación.
 * Muestra una lista de elementos consultados
 */
package com.achtosoftware.inventario_achto.CONSULTA;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.achtosoftware.inventario_achto.ActividadesPantallas.inicio_menu;
import com.achtosoftware.inventario_achto.R;
import com.achtosoftware.inventario_achto.SQL.DatabaseConnection;

import java.util.List;

public class pantalla_consulta extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerViewAdapterConsulta adapter;
    private List<SQLconsulta> consultaList;
    private DatabaseConnection dbConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_consulta);

        // Inicializar la conexión a la base de datos
        dbConnection = new DatabaseConnection();

        // Configurar el RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Obtener la lista de elementos consultados desde la base de datos
        consultaList = dbConnection.getConsulta();

        // Configurar el adaptador para el RecyclerView
        adapter = new RecyclerViewAdapterConsulta(this, consultaList);
        recyclerView.setAdapter(adapter);

        // Configurar la barra de búsqueda
        SearchView searchView = findViewById(R.id.searchView);

        LinearLayout layoutSearch = findViewById(R.id.layoutsearch);
        layoutSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mostrar la barra de búsqueda al hacer clic en el icono de búsqueda
                searchView.setIconified(false);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Aplicar el filtro de búsqueda en la lista de elementos
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        // Navegar de vuelta al menú principal al presionar el botón Atrás
        Intent intent = new Intent(this, inicio_menu.class);
        startActivity(intent);
    }

    public void onClick(View view) {
    }
}
