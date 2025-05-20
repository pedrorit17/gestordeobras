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
import javax.swing.JTable;

/**
 *
 * @author Gustavo
 */
public class ClsMaodeObra {

    
    private int Codigo;
    private int CodMaodeObra;
    private int CodRdoobra;
    private int CodObra;
    private String MaodeObra;
    private int Quantidade;
    
    public boolean Salvar(){
        Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";

        
            ConsultaSQL = "INSERT INTO maodeobra (MaodeObra, Quantidade, RDOObra_idRDOObra) VALUES (?, ?, ?)";
        
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            Query1.setString(1, MaodeObra);
            Query1.setInt(2, Quantidade);
            Query1.setInt(3, CodRdoobra);

            Query1.execute();
            
    }catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    return true;
    }   

    public boolean Alterar(){
         Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";

        
            ConsultaSQL = "UPDATE maodeobra SET Quantidade = ?"
                    + " WHERE RDOObra_idRDOObra = ? and MaodeObra = ?";
        
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            //ordens iguais 
            Query1.setInt(1, Quantidade);
            Query1.setInt(2, CodRdoobra);
            Query1.setString(3, MaodeObra);
            Query1.execute();
            
    }catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    return true;
    }
    
    public boolean Excluir() {
        
            Connection c = ConectaBD.getconexao();

            String ConsultaSQL = "DELETE from maodeobra WHERE Quantidade = ? and MaodeObra = ? and RDOObra_idRDOObra = ?";
            try {
                PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
                Query1.setInt(1, Quantidade);
                Query1.setString(2, MaodeObra);
                Query1.setInt(3, CodRdoobra);
                Query1.execute();
                //c.commit();
            } catch (java.sql.SQLException e) {
                System.out.println(e.getMessage());
                return false;
            }
        
        return true;
    }
    
    public boolean carregaTabela(JTable jTableRDOGeral) {
        Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";
        ConsultaSQL = "SELECT m.MaodeObra, m.Quantidade"
                + " FROM maodeobra m, rdoobra r"
                + " WHERE m.RDOObra_idRDOObra = ? and r.idRDOObra = m.RDOObra_idRDOObra "
                + " order by m.MaodeObra";
        ResultSet rs;
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            Query1.setInt(1, CodRdoobra);
            rs = Query1.executeQuery();

            javax.swing.table.DefaultTableModel dtm = (javax.swing.table.DefaultTableModel)jTableRDOGeral.getModel();
            int cont = dtm.getRowCount(); 
            for(int i = 0; i < cont; i++) dtm.removeRow(0);
            while(rs.next()) {
                  //clsAluno objAluno = new clsAluno();
                  //objAluno.ExisteID(rs.getInt("matricula"));
              
              
                  
                      dtm.addRow(new Object[]{""+rs.getString("m.MaodeObra"),rs.getInt("m.Quantidade")});
                  
                  
            }

        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
    
    public boolean PesquisaMaodeObra() {
        Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";
        ConsultaSQL = "SELECT m.MaodeObra, m.Quantidade FROM rdoobra r, maodeobra m"
                + " WHERE m.RDOObra_idRDOObra = ? and m.MaodeObra = ? and m.Quantidade = ? ";
        ResultSet rs;
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            Query1.setInt(1, CodRdoobra);
            Query1.setString(2, MaodeObra);
            Query1.setInt(3, Quantidade);
            rs = Query1.executeQuery();
            if((rs.next())) {
                this.MaodeObra = rs.getString("m.MaodeObra");
                this.Quantidade = rs.getInt("m.Quantidade");

            }else return false;
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
     * @return the CodMaodeObra
     */
    public int getCodMaodeObra() {
        return CodMaodeObra;
    }

    /**
     * @param CodMaodeObra the CodMaodeObra to set
     */
    public void setCodMaodeObra(int CodMaodeObra) {
        this.CodMaodeObra = CodMaodeObra;
    }

    /**
     * @return the CodRdoobra
     */
    public int getCodRdoobra() {
        return CodRdoobra;
    }

    /**
     * @param CodRdoobra the CodRdoobra to set
     */
    public void setCodRdoobra(int CodRdoobra) {
        this.CodRdoobra = CodRdoobra;
    }

    /**
     * @return the CodObra
     */
    public int getCodObra() {
        return CodObra;
    }

    /**
     * @param CodObra the CodObra to set
     */
    public void setCodObra(int CodObra) {
        this.CodObra = CodObra;
    }

    /**
     * @return the MaodeObra
     */
    public String getMaodeObra() {
        return MaodeObra;
    }

    /**
     * @param MaodeObra the MaodeObra to set
     */
    public void setMaodeObra(String MaodeObra) {
        this.MaodeObra = MaodeObra;
    }

    /**
     * @return the Quantidade
     */
    public int getQuantidade() {
        return Quantidade;
    }

    /**
     * @param Quantidade the Quantidade to set
     */
    public void setQuantidade(int Quantidade) {
        this.Quantidade = Quantidade;
    }
       
}
