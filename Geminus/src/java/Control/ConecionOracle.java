package Control;


import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConecionOracle {

    public static String query;
    public static Statement stat;
    public static ResultSet rs;
    public static Connection con;


    public static boolean conectar() throws ClassNotFoundException {
        boolean r = false;
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            con = DriverManager.getConnection("jdbc:oracle:thin:@192.168.10.215:1521:XE", "geminusi4", "ep43");
            stat = con.createStatement();
            r = true;
            return r;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return r;
        }

    }
//METODOS PARA POSICIONAR EN EL RESULT SET
    // INDEPENDIENTE DE CUAL SEA 
    // LA TABLA

     public static boolean ejecuteQuery() throws SQLException {
        boolean r = false;
        try {
            rs = stat.executeQuery(query);
            r = true;
        } catch (Exception e) {
            System.out.println("Hubo un Error en Ejecute Query -> " + e);
            r = false;
        }
        return r;
    }

    public static boolean ejecuteQuery(String x) throws SQLException {
        boolean r = true;
        try {
            rs = stat.executeQuery(x);
        } catch (SQLException e) {
            System.out.println("ERROR AL HACER QUERY " + e.toString());

            r = false;
        }
        return r;
    }

    public static boolean ejecuteUpdate() {
        boolean r = true;
        try {
            stat.executeUpdate(query);
            r = true;
        } catch (SQLException e) {
            System.out.println("ERROR Al HACER UPDTAPE" + e.toString());
            r = false;
        }
        return r;
    }

    public static boolean ejecuteUpdate(String q) {
        query = q;
        return (ejecuteUpdate());
    }
    public static void cerrarConexion() {
        try {
            stat.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Error en cerrar la base de datos" + e.toString());
        }
    }

    public static void main(String[] args) throws ClassNotFoundException {
        ConecionOracle.conectar();
        
    }
}
