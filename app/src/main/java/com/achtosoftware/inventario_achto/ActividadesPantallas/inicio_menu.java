package com.achtosoftware.inventario_achto.ActividadesPantallas;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.achtosoftware.inventario_achto.CONSULTA.pantalla_consulta;
import com.achtosoftware.inventario_achto.EMBARQUE.cantidad_embarque;
import com.achtosoftware.inventario_achto.R;
import com.achtosoftware.inventario_achto.RECIBO.pantalla_recibo;

/**
 * Esta actividad representa el menú de inicio de la aplicación.
 * Permite al usuario acceder a diferentes funcionalidades como entrada, salida y búsqueda de productos.
 */
public class inicio_menu extends AppCompatActivity implements View.OnClickListener {

    private CardView cardrecibo, cardembarque, cardconsulta;
    private ProgressBar recibo, embarque, consulta;
    private LinearLayout linearrecibo, linearembarque, linearconsulta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio_menu);

        cardrecibo = findViewById(R.id.cardrecibo);
        cardembarque = findViewById(R.id.cardembarque);
        cardconsulta = findViewById(R.id.cardconsulta);

        recibo = findViewById(R.id.progressBarrecibo);
        embarque = findViewById(R.id.progressBarembarque);
        consulta = findViewById(R.id.progressBarconsulta);

        linearrecibo = findViewById(R.id.recibir);
        linearembarque = findViewById(R.id.embarcar);
        linearconsulta = findViewById(R.id.inventario);

    }

    /**
     * Método llamado cuando se hace clic en uno de los botones.
     * Redirige a la actividad correspondiente según el botón clicado.
     */
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.recibir:
                linearrecibo.setVisibility(View.GONE);
                recibo.setVisibility(View.VISIBLE);
                cardrecibo.setCardElevation(1);
                redirectToActivity(pantalla_recibo.class); // Redirige a la actividad de entrada
                break;
            case R.id.embarcar:
                linearembarque.setVisibility(View.GONE);
                embarque.setVisibility(View.VISIBLE);
                cardembarque.setCardElevation(1);
                redirectToActivity(cantidad_embarque.class); // Redirige a la actividad de salida
                break;
            case R.id.inventario:
                linearconsulta.setVisibility(View.GONE);
                consulta.setVisibility(View.VISIBLE);
                cardconsulta.setCardElevation(1);
                redirectToActivity(pantalla_consulta.class);
                break;
        }
    }

    /**
     * Método para redirigir a una actividad específica.
     */
    private void redirectToActivity(Class<?> activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
        finish(); // Finaliza esta actividad para no volver atrás en la navegación
    }

    @Override
    public void onBackPressed() {
        showLogoutConfirmationDialog();
    }

    private void showLogoutConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cerrar Sesión");
        builder.setMessage("¿Estás seguro de que deseas cerrar sesión?");
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Realiza aquí las acciones necesarias para cerrar sesión
                Intent intent = new Intent(inicio_menu.this, inicio_sesion.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Limpia el historial de actividades
                startActivity(intent); // Inicia la actividad de inicio de sesión
                finish(); // Cierra la actividad actual
            }
        });
        builder.setNegativeButton("No", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }


}
