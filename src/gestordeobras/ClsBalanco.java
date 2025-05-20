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
public class ClsBalanco {
    
    private int Codigo;
    private Double estoqueantigo;
    private Double estoquenovo;
    private Date DataEntrada;
    private int CodInsumo;
    
   public boolean Salvar(){
         Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";
        

        /*(ClsEndereco.ExisteID(Cod_Fun)!=true) 
            ConsultaSQL = "UPDATE endereço SET Codigo = ?, Rua = ?, Numero = ?, Bairro = ?,"+
                    "Cidade = ?, Estado = ?";*/
        
            ConsultaSQL = "INSERT INTO balanco (idinsumo, databalanco,estoqueantigo, estoquenovo) values ( ?, ?, ?,?)";
        
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            Query1.setInt(1, CodInsumo);
            Query1.setDate(2, DataEntrada);
            Query1.setDouble(3, estoqueantigo);
            Query1.setDouble(4, estoquenovo);
            Query1.execute();
            
    }catch (java.sql.SQLException e) {
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
     * @return the Quantidade
     */
    public Double getestoqueantigo() {
        return estoqueantigo;
    }

    /**
     * @param estoqueantigo the Quantidade to set
     */
    public void setestoqueantigo(Double estoqueantigo) {
        this.estoqueantigo = estoqueantigo;
    }
    
      public Double getestoquenovo() {
        return estoquenovo;
    }

    /**
     * @param estoquenovo the Quantidade to set
     */
    public void setestoquenovo(Double estoquenovo) {
        this.estoquenovo = estoquenovo;
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
    
}
