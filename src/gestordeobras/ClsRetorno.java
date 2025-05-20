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
public class ClsRetorno {
    
    private int Codigo;
    private Double Quantidade;
    private Date DataEntrada;
    private int CodInsumo;
    private String Os;
    
   public boolean Salvar(){
         Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";
        

        /*(ClsEndereco.ExisteID(Cod_Fun)!=true) 
            ConsultaSQL = "UPDATE endereço SET Codigo = ?, Rua = ?, Numero = ?, Bairro = ?,"+
                    "Cidade = ?, Estado = ?";*/
        
            ConsultaSQL = "INSERT INTO retornoinsumo (quantidade, dataretorno,insumo_idinsumo,os) values ( ?, ?, ?,?)";
        
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            Query1.setDouble(1, Quantidade);
            Query1.setDate(2, DataEntrada);
            Query1.setInt(3, CodInsumo);
            Query1.setString(4, Os);
            Query1.execute();
            
    }catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    return true;
    }
    
     public Double PesquisarQtdMovimento(int idinsumo, String Os){
        Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";
        ConsultaSQL = "SELECT sum(quantidade) FROM gestorobra.retornoinsumo WHERE Insumo_idinsumo=? and Os=?";
        ResultSet rs;
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            Query1.setInt(1, idinsumo);
            Query1.setString(2, Os);
            rs = Query1.executeQuery();
            if((rs.next())) {// se quisesse mais de uma colocar um while
                this.setQuantidade(rs.getDouble("sum(quantidade)"));
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
        System.out.println(this.Quantidade);
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
     * @param quantidade the Quantidade to set
     */
    public void setQuantidade(Double quantidade) {
        this.Quantidade = quantidade;
    }
    
    /**
     * @return the DataEntrada
     */
    public Date getDataEntrada() {
        return DataEntrada;
    }

    /**
     * @param DataEntrada the DataEntrada to set
     */
    public void setDataEntrada(Date DataEntrada) {
        this.DataEntrada = DataEntrada;
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
    
     public String getOs() {
        return Os;
    }

    /**
     * @param Os the CodInsumo to set
     */
    public void setOs(String Os) {
        this.Os = Os;
    }
    
}
