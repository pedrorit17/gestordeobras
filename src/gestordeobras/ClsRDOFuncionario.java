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
public class ClsRDOFuncionario {
   
    private int CodFuncionario;
    private int CodRDOObra;    
    private int Codigo;    
    private String Descricao;
    private String NomeRDOObra;
    private String NomeFuncionario;    
    
    public boolean Salvar(int CodRDOObra, int CodFuncionario, String Descricao){
        Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";

        
            ConsultaSQL = "INSERT INTO rdoobra_has_funcionario ( RDOObra_idRDOObra, Funcionario_idFuncionario, Descricao)"
                    + " values (?, ?, ?)";
        
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            Query1.setInt(1, CodRDOObra);
            Query1.setInt(2, CodFuncionario);
            Query1.setString(3, Descricao);
            Query1.execute();
            
    }catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    return true;
    }
    
    public int PesquisaidFuncionario() {
        Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";
        ConsultaSQL = "SELECT idFuncionario FROM funcionario WHERE Nome = ?";
        ResultSet rs;
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            Query1.setString(1, NomeFuncionario);
            rs = Query1.executeQuery();
            if((rs.next())) {
                this.Codigo = rs.getInt("idFuncionario");

            }else return -1;
        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
            return -1;
        }
        return Codigo;
    }
    
    public boolean carregaTabela(JTable jTableRDOGeral, int CodRDOObra) {
        Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";
        ConsultaSQL = "SELECT f.Nome, h.Descricao"
                + " FROM funcionario f, rdoobra_has_funcionario h, rdoobragrupo r"
                + " WHERE h.RDOObra_idRDOObra = ? and r.idRDOObra = h.RDOObra_idRDOObra  and h.RDOObra_idRDOObra = r.idRDOObra"
                + " and h.Funcionario_idFuncionario = f.idFuncionario"
                + " order by h.RDOObra_idRDOObra";
        ResultSet rs;
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            Query1.setInt(1, CodRDOObra);
            rs = Query1.executeQuery();

            javax.swing.table.DefaultTableModel dtm = (javax.swing.table.DefaultTableModel)jTableRDOGeral.getModel();
            int cont = dtm.getRowCount(); 
            for(int i = 0; i < cont; i++) dtm.removeRow(0);
            while(rs.next()) {
                  //clsAluno objAluno = new clsAluno();
                  //objAluno.ExisteID(rs.getInt("matricula"));
              
              
                  
                      dtm.addRow(new Object[]{""+rs.getString("f.Nome"),rs.getString("h.Descricao")});
                  
                  
            }

        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
    
    public boolean Alterar(){
         Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";

        
            ConsultaSQL = "UPDATE rdoobra_has_funcionario SET Descricao = ?"
                    + " WHERE RDOObra_idRDOObra = ? and Funcionario_idFuncionario = ?";
        
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            //ordens iguais 
            Query1.setString(1, Descricao);
            Query1.setInt(2, CodRDOObra);
            Query1.setInt(3, CodFuncionario);
            Query1.execute();
            
    }catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    return true;
    }
    
    public boolean Excluir() {
        
            Connection c = ConectaBD.getconexao();

            String ConsultaSQL = "DELETE from rdoobra_has_funcionario WHERE RDOObra_idRDOObra = ? and Funcionario_idFuncionario = ?";
            try {
                PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
                Query1.setInt(1, CodRDOObra);
                Query1.setInt(2, CodFuncionario);
                Query1.execute();
                //c.commit();
            } catch (java.sql.SQLException e) {
                System.out.println(e.getMessage());
                return false;
            }
        
        return true;
    }
    
    public boolean PesquisaAtvFuncionario() {
        Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";
        ConsultaSQL = "SELECT f.Nome, h.Descricao FROM rdoobra_has_funcionario h, funcionario f"
                + " WHERE h.RDOObra_idRDOObra = ? and h.Funcionario_idFuncionario = ? ";
        ResultSet rs;
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            Query1.setInt(1, CodRDOObra);
            Query1.setInt(2, CodFuncionario);
            rs = Query1.executeQuery();
            if((rs.next())) {
                this.NomeFuncionario = rs.getString("f.Nome");
                this.Descricao = rs.getString("h.Descricao");

            }else return false;
        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
    
    /**
     * @return the CodObra
     */
    public int getCodFuncionario() {
        return CodFuncionario;
    }

    /**
     * @param CodObra the CodObra to set
     */
    public void setCodFuncionario(int CodFuncionario) {
        this.CodFuncionario = CodFuncionario;
    }

    /**
     * @return the CodRDOObra
     */
    public int getCodRDOObra() {
        return CodRDOObra;
    }

    /**
     * @param CodRDOObra the CodRDOObra to set
     */
    public void setCodRDOObra(int CodRDOObra) {
        this.CodRDOObra = CodRDOObra;
    }

    /**
     * @return the Descricao
     */
    public String getDescricao() {
        return Descricao;
    }

    /**
     * @param Descricao the Descricao to set
     */
    public void setDescricao(String Descricao) {
        this.Descricao = Descricao;
    }

    /**
     * @return the NomeObra
     */
    public String getNomeRDOObra() {
        return NomeRDOObra;
    }

    /**
     * @param NomeRDOObra the NomeObra to set
     */
    public void setNomeRDOObra(String NomeRDOObra) {
        this.NomeRDOObra = NomeRDOObra;
    }

    /**
     * @return the NomeFuncionario
     */
    public String getNomeFuncionario() {
        return NomeFuncionario;
    }

    /**
     * @param NomeFuncionario the NomeFuncionario to set
     */
    public void setNomeFuncionario(String NomeFuncionario) {
        this.NomeFuncionario = NomeFuncionario;
    }
    
}
