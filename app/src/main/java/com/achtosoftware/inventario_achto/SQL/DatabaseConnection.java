/**
 * Clase para gestionar la conexión y operaciones con la base de datos.
 */
package com.achtosoftware.inventario_achto.SQL;

import android.os.StrictMode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.achtosoftware.inventario_achto.CONSULTA.SQLconsulta;
import com.achtosoftware.inventario_achto.EMBARQUE.SQLembarqueDetalle;

import com.achtosoftware.inventario_achto.RECIBO.SQLreciboDetalle;

/**
 * Clase que maneja la conexión y operaciones con la base de datos.
 */
public class DatabaseConnection {

    public static String CONNECTION_STRING = "//192.168.0.170;databaseName=achto;user=SA;password=TOSCA"; // Cambia la dirección IP según tu configuración
    private Connection connection;

    public static String message = "";

    public DatabaseConnection() {
    }

    /**
     * Abre una conexión con la base de datos.
     *
     * @return Verdadero si la conexión se estableció correctamente, falso si no.
     */
    public boolean OpenConnection() {
        message = "";
        try {
            StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(threadPolicy);

            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();

            String strSQl = "jdbc:jtds:sqlserver:" + CONNECTION_STRING;

            connection = DriverManager.getConnection(strSQl);

        } catch (SQLException ex) {
            message = "Error de conexión con la base de datos";
        } catch (Exception ex) {
            message = ex.getMessage();
        }
        return connection != null;
    }

    /**
     * Valida el inicio de sesión de un usuario.
     * @param username Nombre de usuario.
     * @param password Contraseña del usuario.
     * @return Verdadero si las credenciales son válidas, falso si no.
     */
    public boolean validateLogin(String username, String password) {
        try {
            if (OpenConnection()) {
                String query = "SELECT * FROM Usuarios WHERE Usuario = ? AND Password = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, username);
                statement.setString(2, password);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CloseConnection();
        }
        return false;
    }

    /**
     * Cierra la conexión con la base de datos.
     */
    public void CloseConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Obtiene una lista de detalles de recibo desde la base de datos.
     *
     * @return Lista de detalles de recibo.
     */

    //Se utiliza un bloque "try-with-resources" para asegurarse de que los objetos
    //PreparedStatement y ResultSet se cierren automáticamente después de su uso.
    public ArrayList<SQLreciboDetalle> getReciboDetalles(String Pedido) {
        ArrayList<SQLreciboDetalle> detalles = new ArrayList<>();
        try {
            if (OpenConnection()) {
                String sql = "SELECT Pedido, Codigo, Descripcion, CantidadSolicitada, CantidadFisica, Estatus, Fecha, Observaciones FROM ReciboDet WHERE Pedido = ?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, Pedido);
                    try (ResultSet resultSet = statement.executeQuery()) {
                        while (resultSet.next()) {
                            SQLreciboDetalle detalle = new SQLreciboDetalle();
                            detalle.setPedido(resultSet.getString("Pedido"));
                            detalle.setCodigo(resultSet.getInt("Codigo"));
                            detalle.setDescripcion(resultSet.getString("Descripcion"));
                            detalle.setCantidadSolicitada(resultSet.getInt("CantidadSolicitada"));
                            detalle.setCantidadFisica(resultSet.getInt("CantidadFisica"));
                            detalle.setEstatus(resultSet.getInt("Estatus"));
                            detalle.setFecha(resultSet.getTimestamp("Fecha"));
                            detalle.setObservaciones(resultSet.getString("Observaciones"));
                            detalles.add(detalle);
                        }
                    }
                }
                CloseConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return detalles;
    }

    /**
     * Obtiene la lista de inventario.
     *
     * @return Lista de inventario.
     */
    //Se utiliza un bloque "try-with-resources" para asegurarse de que los objetos
    //PreparedStatement y ResultSet se cierren automáticamente después de su uso.
    public ArrayList<SQLconsulta> getConsulta(){
        ArrayList<SQLconsulta> inventario = new ArrayList<>();
        try {
            if (OpenConnection()){
                String sql = "SELECT Cantidad, Codigo, Descripcion FROM Inventario";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    try (ResultSet resultSet = statement.executeQuery()) {
                        while (resultSet.next()){
                            SQLconsulta consulta = new SQLconsulta();
                            consulta.setCantidadconsulta(resultSet.getInt("Cantidad"));
                            consulta.setCodigoconsulta(resultSet.getInt("Codigo"));
                            consulta.setDescripcionconsulta(resultSet.getString("Descripcion"));
                            inventario.add(consulta);
                        }
                    }
                }
                CloseConnection();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return inventario;
    }

    /**
     * Obtiene una lista de detalles de embarque desde la base de datos.
     *
     * @return Lista de detalles de embarque.
     */

    //Se utiliza un bloque "try-with-resources" para asegurarse de que los objetos
    //PreparedStatement y ResultSet se cierren automáticamente después de su uso.
    public ArrayList<SQLembarqueDetalle> getembarqueDetalles(String Pedido) {
        ArrayList<SQLembarqueDetalle> detalles = new ArrayList<>();
        try {
            if (OpenConnection()) {
                String sql = "SELECT Pedido, Codigo, Descripcion, CantidadSolicitada, CantidadEmbarcar, Estatus, Fecha, CantidadFisica, Observaciones FROM EmbarqueDet WHERE Pedido = ?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, Pedido);
                    try (ResultSet resultSet = statement.executeQuery()) {
                        while (resultSet.next()) {
                            SQLembarqueDetalle detalle = new SQLembarqueDetalle();
                            detalle.setPedido(resultSet.getString("Pedido"));
                            detalle.setCodigo(resultSet.getInt("Codigo"));
                            detalle.setDescripcion(resultSet.getString("Descripcion"));
                            detalle.setCantidadSolicitada(resultSet.getInt("CantidadSolicitada"));
                            detalle.setCantidadembarque(resultSet.getInt("CantidadEmbarcar"));
                            detalle.setEstatus(resultSet.getInt("Estatus"));
                            detalle.setCantidadfisica(resultSet.getInt("CantidadFisica"));
                            detalle.setObservaciones(resultSet.getString("Observaciones"));
                            detalle.setFecha(resultSet.getTimestamp("Fecha"));
                            detalles.add(detalle);
                        }
                    }
                }
                CloseConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return detalles;
    }

    /**
     * Método que ejecuta una consulta SELECT en la base de datos y retorna el resultado como un ResultSet.
     *
     * @param query La consulta SELECT a ejecutar
     */
    public ResultSet executeSelect(String query) {

        ResultSet resultSet = null;

        try {
            boolean isConnected = OpenConnection();

            if(connection != null && isConnected){

                System.out.println("QUERY - BD: "+ query);
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                resultSet = preparedStatement.executeQuery();

                //Si de la ejecución del query se obtuvo información, entonces se retornará el resultset con el contenido
                if ( resultSet != null ){
                    return resultSet;
                }else {
                }
                preparedStatement.close();//cerramos preparedStatement
            }else {
            }
            resultSet.close();//cerramos resultSet
        }
        catch (Exception ex){
            ex.printStackTrace();
            System.err.println ("No se puede conectar al servidor de la base de datos");
            System.out.println("----- xx "+ex.getMessage());
        }
        return resultSet;
    }

}
