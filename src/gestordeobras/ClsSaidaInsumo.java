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
public class ClsSaidaInsumo {

   
    private int Codigo;
    private Double Quantidade;
    private Date DataSaida;
    private int CodInsumo;
    private String Os;
    
   public boolean Salvar(){
         Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";
        

        /*(ClsEndereco.ExisteID(Cod_Fun)!=true) 
            ConsultaSQL = "UPDATE endereço SET Codigo = ?, Rua = ?, Numero = ?, Bairro = ?,"+
                    "Cidade = ?, Estado = ?";*/
        
            ConsultaSQL = "INSERT INTO saidainsumo (Quantidade, DataSaida, Insumo_idInsumo,os) values (?, ?, ?,?)";
        
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            Query1.setDouble(1, Quantidade);
            Query1.setDate(2, DataSaida);
            Query1.setInt(3, CodInsumo);
            Query1.setString(4,Os);
            Query1.execute();
            
    }catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    return true;
    }
    
   public int PesquisaridGrupoInsumo(String Nome){
        Connection c = ConectaBD.getconexao();

        int Codigo;
        
        String ConsultaSQL = "";
        ConsultaSQL = "SELECT idGrupoInsumo FROM grupoinsumo WHERE Nome = ?";
        ResultSet rs;
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            Query1.setString(1, Nome);
            rs = Query1.executeQuery();
            if((rs.next())) {// se quisesse mais de uma colocar um while
                Codigo = rs.getInt("idGrupoInsumo");
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
   
    public boolean Excluir(String Nome) {
        
            Connection c = ConectaBD.getconexao();

            String ConsultaSQL = "DELETE from saidainsumo WHERE Nome = ?";
            try {
                PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
                Query1.setString(1, Nome);
                Query1.execute();
                //c.commit();
            } catch (java.sql.SQLException e) {
                System.out.println(e.getMessage());
                return false;
            }
        return true;
    }
    
    public boolean ReduzQuantidadeInsumo(int CodInsumo, double Qtd) {
        
            Connection c = ConectaBD.getconexao();

            String ConsultaSQL = "UPDATE insumo SET Quantidade = ? WHERE idInsumo = ?";
            try {
                PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
                Query1.setDouble(1, Qtd);
                Query1.setInt(2, CodInsumo);
                Query1.execute();
                //c.commit();
            } catch (java.sql.SQLException e) {
                System.out.println(e.getMessage());
                return false;
            }
        return true;
    }
    
    
    public int PesquisarIdInsumo(String Nome){
        Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";
        ConsultaSQL = "SELECT idInsumo FROM insumo WHERE Nome LIKE ?";
        ResultSet rs;
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            Query1.setString(1, Nome);
            rs = Query1.executeQuery();
            if((rs.next())) {// se quisesse mais de uma colocar um while
                this.setCodigo(rs.getInt("idInsumo"));
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
        return getCodigo();
    }
    
     public Double PesquisarQtdInsumo(int CodInsumo){
        Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";
        ConsultaSQL = "SELECT Quantidade FROM insumo WHERE idInsumo = ?";
        ResultSet rs;
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            Query1.setInt(1, CodInsumo);
            rs = Query1.executeQuery();
            if((rs.next())) {// se quisesse mais de uma colocar um while
                this.setQuantidade(rs.getDouble("Quantidade"));
                /*this.Nome = rs.getString("Nome");
                this.Audio = rs.getString("Audio");
                this.Diretor = rs.getString("Diretor");
                this.Classificacao = rs.getString("Classificação");               
                this.Duracao = rs.getString("Duração");*/
            }else return -1.1;
        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
            return -1.1;
        }
        return getQuantidade();
    }
    
     public Double PesquisarQtdMovimento(int idinsumo, String Os){
        Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";
        ConsultaSQL = "SELECT quantidade FROM saidainsumo WHERE Insumo_idinsumo=? and Os=?";
        ResultSet rs;
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            Query1.setInt(1, idinsumo);
            Query1.setString(2, Os);
            rs = Query1.executeQuery();
            if((rs.next())) {// se quisesse mais de uma colocar um while
                this.setQuantidade(rs.getDouble("Quantidade"));
                /*this.Nome = rs.getString("Nome");
                this.Audio = rs.getString("Audio");
                this.Diretor = rs.getString("Diretor");
                this.Classificacao = rs.getString("Classificação");               
                this.Duracao = rs.getString("Duração");*/
            }else return -1.0;
        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
            return -1.0;
        }
        return getQuantidade();
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
     * @return the Quantidade
     */
    public Double getQuantidade() {
        return Quantidade;
    }

    /**
     * @param Quantidade the Quantidade to set
     */
    public void setQuantidade(Double Quantidade) {
        this.Quantidade = Quantidade;
    }

    /**
     * @return the DataSaida
     */
    public Date getDataSaida() {
        return DataSaida;
    }

    /**
     * @param DataSaida the DataSaida to set
     */
    public void setDataSaida(Date DataSaida) {
        this.DataSaida = DataSaida;
    }

    /**
     * @return the CodInsumo
     */
    public int getCodInsumo() {
        return CodInsumo;
    }

    /**
     * @param CodInsumo the CodInsumo to set
     */
    public void setCodInsumo(int CodInsumo) {
        this.CodInsumo = CodInsumo;
    }
    
    /**
     * @return the CodInsumo
     */
    public String getOs() {
        return Os;
    }

    /**
     * @param Os
     */
    public void setOs(String Os) {
        this.Os = Os;
    }
    
}
