package com.achtosoftware.inventario_achto.ActividadesPantallas;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.achtosoftware.inventario_achto.R;
import com.achtosoftware.inventario_achto.SQL.DatabaseConnection;
import com.google.android.material.textfield.TextInputEditText;

/**
 * Esta actividad representa la pantalla de inicio de sesión de la aplicación.
 * Permite al usuario ingresar su nombre de usuario y contraseña para acceder a la aplicación.
 * Verifica las credenciales ingresadas y redirige al menú de inicio si son válidas.
 */
public class inicio_sesion extends AppCompatActivity {

    // Variable estática para almacenar el nombre de usuario
    public static String username;
    private DatabaseConnection databaseConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio_sesion);

        // Inicializa la conexión a la base de datos
        databaseConnection = new DatabaseConnection();

        // Encuentra los elementos de la interfaz
        TextInputEditText usernameEditText = findViewById(R.id.inicio_sesion_user);
        TextInputEditText passwordEditText = findViewById(R.id.inicio_sesion_pass);
        Button inicio_sesion_boton = findViewById(R.id.inicio_sesion_boton);

        // Configura el listener de clic para el botón de inicio de sesión
        inicio_sesion_boton.setOnClickListener(view -> {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            // Valida las credenciales y redirige al menú de inicio si son válidas
            if (databaseConnection.validateLogin(username, password)) {
                inicio_sesion.username = username;
                startInicioMenuActivity();
            } else {
                showMessage("ERROR, CREDENCIALES INCORRECTAS"); // Muestra un mensaje de error si las credenciales son incorrectas
            }
        });
    }

    /**
     * Inicia la actividad del menú de inicio.
     */
    private void startInicioMenuActivity() {

        Intent intent = new Intent(this, inicio_menu.class);
        startActivity(intent); // Inicia la actividad del menú de inicio
        databaseConnection.CloseConnection(); // Cierra la conexión a la base de datos
    }

    /**
     * Muestra un mensaje en forma de Toast.
     */
    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
