/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilitarios;

import java.io.IOException;

/**
 *
 * @author Eugenio
 */
public class ControleAcesso {
    
    public static boolean permitirAcesso(String senha) throws IOException{
        String senhaArquivo = descriptografar(LerProperties.getProp("SENHA_ACESSO"));
        return senhaArquivo.compareTo(senha)==0;
    }
    
    
     public static boolean permitirAcessoBalanco(String senha) throws IOException{
        String senhaArquivo = descriptografar(LerProperties.getProp("SENHA_AJUSTE"));
        return senhaArquivo.compareTo(senha)==0;
    }
     
    public static String criptografar(String str){
        //Cada caracter será substituido pelo seu valor ascii + posicao
        String cript = new String();
        int charVal;
        for (int i=0; i<str.length();i++){
             charVal = (int)(str.charAt(i)+i);
             cript += charVal+"-";
        }
        return cript;
    }
    
    public static String descriptografar(String cript){
        String decript = new String();
        String caracteres[] = cript.split("-");
        for (int i=0;i<caracteres.length;i++){
            decript += (char)(Integer.parseInt(caracteres[i])-i);
            
        }
        return decript;
    }
    
}
