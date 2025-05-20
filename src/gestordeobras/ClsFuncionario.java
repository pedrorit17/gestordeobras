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
public class ClsFuncionario {
    
    private int Codigo;
    private String Nome;
    private String CPF;
    private String FuncaoCargo;
    private String Telefone;
    private String Celular;
    
    
    public boolean Salvar(){
         Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";
        

        
        
            ConsultaSQL = "INSERT INTO funcionario (Nome, CPF, FuncaoCargo"
                    + ", Telefone, Celular) values ( ?, ?, ?, ?, ?)";
        
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            Query1.setString(1, Nome);
            Query1.setString(2, CPF);
            Query1.setString(3, FuncaoCargo);
            Query1.setString(4, Telefone);
            Query1.setString(5, Celular);         
            
            
            Query1.execute();
            
    }catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    return true;
    }
    
    public boolean Excluir(String CPF) {
        
            Connection c = ConectaBD.getconexao();

            String ConsultaSQL = "DELETE from funcionario WHERE CPF = ?";
            try {
                PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
                Query1.setString(1, CPF);
                Query1.execute();
                //c.commit();
            } catch (java.sql.SQLException e) {
                System.out.println(e.getMessage());
                return false;
            }
        return true;
    }
    
    public boolean ExcluirRDO(int CodRDO) {
        
            Connection c = ConectaBD.getconexao();

            String ConsultaSQL = "DELETE from rdoobragrupo WHERE idRDOObra = ?";
            try {
                PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
                Query1.setInt(1, CodRDO);
                Query1.execute();
                //c.commit();
            } catch (java.sql.SQLException e) {
                System.out.println(e.getMessage());
                return false;
            }
        return true;
    }
    
    public boolean ExcluirRDOLigacaoFuncionario(int CodRDO) {
        
            Connection c = ConectaBD.getconexao();

            String ConsultaSQL = "DELETE from rdoobra_has_funcionario WHERE RDOObra_idRDOObra = ?";
            try {
                PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
                Query1.setInt(1, CodRDO);
                Query1.execute();
                //c.commit();
            } catch (java.sql.SQLException e) {
                System.out.println(e.getMessage());
                return false;
            }
        return true;
    }
    
    public boolean Alterar(String CPF){
         Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";

        
            ConsultaSQL = "UPDATE funcionario SET FuncaoCargo = ?, Telefone = ?,"
                    + "Celular = ? WHERE CPF = ?";
        
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            //ordens iguais 
            Query1.setString(1, FuncaoCargo);
            Query1.setString(2, Telefone);
            Query1.setString(3, Celular);
            Query1.setString(4, CPF);
            Query1.execute();
            
    }catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    return true;
    }
    
    public boolean Pesquisar(String CPF){
        Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";
        ConsultaSQL = "SELECT * FROM funcionario WHERE CPF = ?";
        ResultSet rs;
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            Query1.setString(1, CPF);
            rs = Query1.executeQuery();
            if((rs.next())) {// se quisesse mais de uma colocar um while
                this.Nome = rs.getString("Nome");
                this.FuncaoCargo = rs.getString("FuncaoCargo");
                this.CPF = rs.getString("CPF");          
                this.Telefone = rs.getString("Telefone");
                this.Celular = rs.getString("Celular");
            }else return false;
        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
    
    public int PesquisaridFuncionario(String CPF){
        Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";
        ConsultaSQL = "SELECT idFuncionario FROM funcionario WHERE CPF = ?";
        ResultSet rs;
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            Query1.setString(1, CPF);
            rs = Query1.executeQuery();
            if((rs.next())) {// se quisesse mais de uma colocar um while
                this.setCodigo(rs.getInt("idFuncionario"));
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
    
    public int PesquisarCPF(String CPF){
        Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";
        ConsultaSQL = "SELECT idFuncionario FROM funcionario WHERE CPF = ?";
        ResultSet rs;
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            Query1.setString(1, CPF);
            rs = Query1.executeQuery();
            if((rs.next())) {// se quisesse mais de uma colocar um while
                this.setCodigo(rs.getInt("idFuncionario"));
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
    
    public static boolean carregaTabela(JTable jTableFuncionarios) {
        Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";
        ConsultaSQL = "SELECT Nome, CPF, Telefone, Celular, FuncaoCargo FROM funcionario order by idFuncionario";
        ResultSet rs;
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            
            rs = Query1.executeQuery();

            javax.swing.table.DefaultTableModel dtm = (javax.swing.table.DefaultTableModel)jTableFuncionarios.getModel();
            int cont = dtm.getRowCount(); 
            for(int i = 0; i < cont; i++) dtm.removeRow(0);
            while(rs.next()) {
                  //clsAluno objAluno = new clsAluno();
                  //objAluno.ExisteID(rs.getInt("matricula"));
              
                  dtm.addRow(new Object[]{""+rs.getString("Nome"),rs.getString("CPF"),rs.getString("Telefone"),rs.getString("Celular")
                  ,rs.getString("FuncaoCargo")});
            }

        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
    
    public static boolean carregaTabelaBusca(JTable jTableFuncionarios, String Nome) {
        Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";
        ConsultaSQL = "SELECT Nome, CPF, Telefone, Celular, FuncaoCargo FROM funcionario WHERE Nome LIKE ? order by idFuncionario";
        ResultSet rs;
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            Query1.setString(1, "%"+Nome+"%");
            rs = Query1.executeQuery();

            javax.swing.table.DefaultTableModel dtm = (javax.swing.table.DefaultTableModel)jTableFuncionarios.getModel();
            int cont = dtm.getRowCount(); 
            for(int i = 0; i < cont; i++) dtm.removeRow(0);
            while(rs.next()) {
                  //clsAluno objAluno = new clsAluno();
                  //objAluno.ExisteID(rs.getInt("matricula"));
              
                  dtm.addRow(new Object[]{""+rs.getString("Nome"),rs.getString("CPF"),rs.getString("Telefone"),rs.getString("Celular")
                  ,rs.getString("FuncaoCargo")});
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
     * @return the CPF
     */
    public String getCPF() {
        return CPF;
    }

    /**
     * @param CPF the CPF to set
     */
    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    /**
     * @return the FuncaoCargo
     */
    public String getFuncaoCargo() {
        return FuncaoCargo;
    }

    /**
     * @param FuncaoCargo the FuncaoCargo to set
     */
    public void setFuncaoCargo(String FuncaoCargo) {
        this.FuncaoCargo = FuncaoCargo;
    }

    /**
     * @return the Telefone
     */
    public String getTelefone() {
        return Telefone;
    }

    /**
     * @param Telefone the Telefone to set
     */
    public void setTelefone(String Telefone) {
        this.Telefone = Telefone;
    }

    /**
     * @return the Celular
     */
    public String getCelular() {
        return Celular;
    }

    /**
     * @param Celular the Celular to set
     */
    public void setCelular(String Celular) {
        this.Celular = Celular;
    }
    
}
