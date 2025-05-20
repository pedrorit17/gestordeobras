/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilitarios;

/**
 *
 * @author Eugênio
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class LerProperties {

    private static Properties properties;

    public static String getProp(String atributo) throws IOException {

        String retorno = null;
        properties = new Properties();

        String path = new File("config.properties").getCanonicalPath();
        FileInputStream file = new FileInputStream(path);
        properties.load(file);

        System.out.println(file);
        if (properties != null) {
            retorno = properties.getProperty(atributo);
        }

        return retorno;
    }
}
