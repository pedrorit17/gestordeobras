/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestordeobras;

import Utilitarios.ConectaBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JTable;

/**
 *
 * @author Gustavo
 */
public class ClsGrupoInsumo {
    
    private int Codigo;
    private String Nome;
    private String Sigla;
    
    public boolean Salvar(){
         Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";
        

        
        
            ConsultaSQL = "INSERT INTO grupoinsumo (Nome,Sigla"
                    + ") values ( ?, ?)";
        
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            Query1.setString(1, Nome);
            Query1.setString(2, Sigla);
            
            
             
            Query1.execute();
            
    }catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    return true;
    }
    
    public boolean Alterar(String Nome1){
         Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";

        
            ConsultaSQL = "UPDATE grupoinsumo SET Nome = ?, Sigla = ?"
                    + " WHERE Nome = ?";
        
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            //ordens iguais 
            Query1.setString(1, Nome);
            Query1.setString(2, Sigla);
            Query1.setString(3, Nome1);
            Query1.execute();
            
    }catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    return true;
    }
    
    public boolean Excluir(String Nome) {
        
            Connection c = ConectaBD.getconexao();

            String ConsultaSQL = "DELETE from grupoinsumo WHERE Nome = ?";
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
    
     public boolean ExcluirInsumo(int Codigo) {
        
            Connection c = ConectaBD.getconexao();

            String ConsultaSQL = "DELETE from insumo WHERE idInsumo = ?";
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
     
      
    
    public boolean ExcluirPedido(int Codigo) {
        
            Connection c = ConectaBD.getconexao();

            String ConsultaSQL = "DELETE from pedidocompras WHERE Insumo_idInsumo = ?";
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
    
    public boolean ExcluirEntrada(int Codigo) {
        
            Connection c = ConectaBD.getconexao();

            String ConsultaSQL = "DELETE from entradainsumo WHERE Insumo_idInsumo = ?";
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
    
    public boolean ExcluirSaida(int Codigo) {
        
            Connection c = ConectaBD.getconexao();

            String ConsultaSQL = "DELETE from saidainsumo WHERE Insumo_idInsumo = ?";
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
    
    public boolean Pesquisar(String Nome){
        Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";
        ConsultaSQL = "SELECT * FROM grupoinsumo WHERE Nome = ?";
        ResultSet rs;
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            Query1.setString(1, Nome);
            rs = Query1.executeQuery();
            if((rs.next())) {// se quisesse mais de uma colocar um while
                this.Nome = rs.getString("Nome");
                this.Sigla = rs.getString("Sigla");
                
            }else return false;
        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
    
    public int Pesquisarid(String Nome){
        Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";
        ConsultaSQL = "SELECT i.idInsumo FROM grupoinsumo g, insumo i WHERE g.Nome = ? and g.idGrupoInsumo = i.GrupoInsumo_idGrupoInsumo";
        ResultSet rs;
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            Query1.setString(1, Nome);
            rs = Query1.executeQuery();
            if((rs.next())) {// se quisesse mais de uma colocar um while
                this.Codigo = rs.getInt("i.idInsumo");
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
    
    public static boolean carregaTabela(JTable jTableFornecedor) {
        Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";
        ConsultaSQL = "SELECT Nome, Sigla FROM grupoinsumo order by idGrupoInsumo";
        ResultSet rs;
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            
            rs = Query1.executeQuery();

            javax.swing.table.DefaultTableModel dtm = (javax.swing.table.DefaultTableModel)jTableFornecedor.getModel();
            int cont = dtm.getRowCount(); 
            for(int i = 0; i < cont; i++) dtm.removeRow(0);
            while(rs.next()) {
                  //clsAluno objAluno = new clsAluno();
                  //objAluno.ExisteID(rs.getInt("matricula"));
              
                  dtm.addRow(new Object[]{""+rs.getString("Nome"),rs.getString("Sigla")});
            }

        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
    
    public int PesquisarNome(String Nome){
        Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";
        ConsultaSQL = "SELECT idGrupoInsumo FROM grupoinsumo WHERE Nome = ?";
        ResultSet rs;
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            Query1.setString(1, Nome);
            rs = Query1.executeQuery();
            if((rs.next())) {// se quisesse mais de uma colocar um while
                this.Codigo = rs.getInt("idGrupoInsumo");
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
    
    public static boolean carregaTabelaBusca(JTable jTableFornecedores, String nome) {
        Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";
        ConsultaSQL = "SELECT Nome, Sigla FROM grupoinsumo WHERE Nome LIKE ? order by idGrupoInsumo";
        ResultSet rs;
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            Query1.setString(1, "%"+nome+"%");
            rs = Query1.executeQuery();

            javax.swing.table.DefaultTableModel dtm = (javax.swing.table.DefaultTableModel)jTableFornecedores.getModel();
            int cont = dtm.getRowCount(); 
            for(int i = 0; i < cont; i++) dtm.removeRow(0);
            while(rs.next()) {
                  //clsAluno objAluno = new clsAluno();
                  //objAluno.ExisteID(rs.getInt("matricula"));
              
                  dtm.addRow(new Object[]{""+rs.getString("Nome"),rs.getString("Sigla")});
            }

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
     * @return the Nome
     */
    public String getNome() {
        return Nome;
    }

    /**
     * @param Nome the Nome to set
     */
    public void setNome(String Nome) {
        this.Nome = Nome;
    }

    /**
     * @return the Sigla
     */
    public String getSigla() {
        return Sigla;
    }

    /**
     * @param Sigla the Sigla to set
     */
    public void setSigla(String Sigla) {
        this.Sigla = Sigla;
    }

    
}
