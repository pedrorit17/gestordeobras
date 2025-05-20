/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestordeobras;

import Utilitarios.ConectaBD;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Gustavo
 */
public class ClsEndereco {

    
    private int Codigo;
    private String Rua;
    private String Bairro;
    private String Numero;
    private String Cidade;
    private String Estado;
    private String CEP;
    
    

    public boolean Salvar(){
         Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";

        /*(ClsEndereco.ExisteID(Cod_Fun)!=true) 
            ConsultaSQL = "UPDATE endereço SET Codigo = ?, Rua = ?, Numero = ?, Bairro = ?,"+
                    "Cidade = ?, Estado = ?";*/
        
            ConsultaSQL = "INSERT INTO endereco (Rua,Numero,Bairro,Cidade,"
                    + "Estado,CEP) values (?, ?, ?, ?, ?, ?)";
        
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            Query1.setString(1, Rua);
            Query1.setString(2, Numero);
            Query1.setString(3, Bairro);
            Query1.setString(4, Cidade);
            Query1.setString(5, Estado);
            Query1.setString(6, CEP);
            
            
             /*if (Codigo > 0) {
                Query1.setInt(9, Codigo);
            }*/
            Query1.execute();
            //c.commit();
                                  
            /*if (ClsEndereco.ExisteID(Cod_Fun)==false) {
                ConsultaSQL = "SELECT Codigo FROM endereço " +
                        "WHERE Rua = ? ORDER BY Codigo DESC";
                        Query1 = c.prepareStatement(ConsultaSQL);
                        Query1.setString(2,Rua);
                        ResultSet rs = Query1.executeQuery();
                        if(rs.next()){
                            Codigo = rs.getInt("codigo");
                 }
            }*/
    }catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    return true;
    }
    
    
    public boolean Alterar(int Codigo){
         Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";

        
            ConsultaSQL = "UPDATE endereco SET Rua = ?, Numero = ?,"
                    + " Bairro = ?, Cidade = ?, Estado = ?, CEP = ? WHERE idEndereco = ?";
        
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            //ordens iguais 
            Query1.setString(1, Rua);
            Query1.setString(2, Numero);
            Query1.setString(3, Bairro);
            Query1.setString(4, Cidade);
            Query1.setString(5, Estado);
            Query1.setString(6, CEP);
            Query1.setInt(7, Codigo);
            Query1.execute();
            
    }catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    return true;
    }
    
    public boolean Pesquisar(int Codigo){
        Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";
        ConsultaSQL = "SELECT * FROM endereco WHERE idEndereco = ?";
        ResultSet rs;
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            Query1.setInt(1, Codigo);
            rs = Query1.executeQuery();
            if((rs.next())) {// se quisesse mais de uma colocar um while
                this.Rua = rs.getString("Rua");
                this.Bairro = rs.getString("Bairro");
                this.Numero = rs.getString("Numero");
                this.Cidade = rs.getString("Cidade");
                this.Estado = rs.getString("Estado");               
                this.CEP = rs.getString("CEP");
            }else return false;
        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
    
    public int PesquisaridEndereco(){
        Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";
        ConsultaSQL = "SELECT idEndereco FROM endereco ORDER BY idEndereco DESC LIMIT 1";
        ResultSet rs;
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            //Query1.setString(1, Numero);
            rs = Query1.executeQuery();
            if((rs.next())) {// se quisesse mais de uma colocar um while
                this.Codigo = rs.getInt("idEndereco");
                /*this.Nome = rs.getString("Nome");
                this.Audio = rs.getString("Audio");
                this.Diretor = rs.getString("Diretor");
                this.Classificacao = rs.getString("Classificação");               
                this.Duracao = rs.getString("Duração");*/
            }else return -1;
        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
            return -1;
        }
        return Codigo;
    }
    
    public boolean Excluir(int Codigo) {
        
            Connection c = ConectaBD.getconexao();

            String ConsultaSQL = "DELETE from endereco WHERE idEndereco = ?";
            try {
                PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
                Query1.setInt(1, Codigo);
                Query1.execute();
                //c.commit();
            } catch (java.sql.SQLException e) {
                System.out.println(e.getMessage());
                return false;
            }
        
        return true;
    }
   
    /**
     * @return the Codigo
     */
    public int getCodigo() {
        return Codigo;
    }

    /**
     * @param Codigo the Codigo to set
     */
    public void setCodigo(int Codigo) {
        this.Codigo = Codigo;
    }

    /**
     * @return the Rua
     */
    public String getRua() {
        return Rua;
    }

    /**
     * @param Rua the Rua to set
     */
    public void setRua(String Rua) {
        this.Rua = Rua;
    }

    /**
     * @return the Bairro
     */
    public String getBairro() {
        return Bairro;
    }

    /**
     * @param Bairro the Bairro to set
     */
    public void setBairro(String Bairro) {
        this.Bairro = Bairro;
    }

    /**
     * @return the Numero
     */
    public String getNumero() {
        return Numero;
    }

    /**
     * @param Numero the Numero to set
     */
    public void setNumero(String Numero) {
        this.Numero = Numero;
    }

    /**
     * @return the Cidade
     */
    public String getCidade() {
        return Cidade;
    }

    /**
     * @param Cidade the Cidade to set
     */
    public void setCidade(String Cidade) {
        this.Cidade = Cidade;
    }

    /**
     * @return the Estado
     */
    public String getEstado() {
        return Estado;
    }

    /**
     * @param Estado the Estado to set
     */
    public void setEstado(String Estado) {
        this.Estado = Estado;
    }

    /**
     * @return the CEP
     */
    public String getCEP() {
        return CEP;
    }

    /**
     * @param CEP the CEP to set
     */
    public void setCEP(String CEP) {
        this.CEP = CEP;
    }
}
