/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilitarios;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Gustavo
 */
public class ConectaBD {

    private static Connection c = null;
    private static String Usuario;
    private static String Senha;
    private static String url;

    private ConectaBD() {
    }

    private static boolean lerArquivoConf() {

        try {
            url = LerProperties.getProp("URL");
            Usuario = LerProperties.getProp("USUARIO");
            Senha = ControleAcesso.descriptografar(LerProperties.getProp("SENHA"));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "\nNão foi possível encontrar o arquivo de configuração.\n\n" + e.getMessage() + "\n");
            return false;
        }
        return true;
    }

    public static Connection getconexao() {

        if (lerArquivoConf()) {

            if (c == null) {

                try {
                    Class.forName("com.mysql.jdbc.Driver");
                } catch (ClassNotFoundException cnfe) {
                    System.out.println("Couldn't find the driver!");
                    System.out.println("Let's print a stack trace, and exit.");
                    System.exit(1);
                }

                System.out.println("Registered the driver ok, so let's make a connection.");

                try {
                    // The second and third arguments are the username and password,
                    // respectively. They should be whatever is necessary to connect
                    // to the database.
                    // url = "jdbc:mysql://localhost:3306/gestorobra";
                    c = DriverManager.getConnection(url, Usuario, Senha);
                } catch (SQLException se) {
                    JOptionPane.showMessageDialog(null, "\nNão foi possível conectar ao servidor de banco de dados.\n\n" + se.getMessage() + "\n");
                    System.exit(1);
                } catch (Exception e) {
                    System.out.println("sei la = " + e.getMessage());
                    System.exit(1);
                }

                if (c != null) {
                    System.out.println("Hooray! We connected to the database!");
                } else {
                    System.out.println("We should never get here.");
                }
            }
        }
        return c;
    }

    public static String getSenha() {
        return Senha;
    }

    public static void setSenha(String mSenha) {
        Senha = mSenha;
    }

    public static String getUsuario() {
        return Usuario;
    }

    public static void setUsuario(String mUsuario) {
        Usuario = mUsuario;
    }

    public static boolean isConnected() {
        if (c == null) {
            return false;
        } else {
            return true;
        }
    }

}
