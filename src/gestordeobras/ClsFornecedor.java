/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestordeobras;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JTable;
import Utilitarios.ConectaBD;
import java.sql.Date;

/**
 *
 * @author Gustavo
 */
public class ClsFornecedor {

    /**
     * @return the Cod_End
     */
   
    private int Codigo;
    private int Cod_End;
    private String Nome;
    private String CNPJ;
    private int Tipo;
    private Date DataNasc;
    private String RG;
    private String Telefone;
    private String Celular;
    private String Email;
    
    

    public boolean Salvar(){
         Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";
        

        
        
            ConsultaSQL = "INSERT INTO fornecedor (Endereco_idEndereco,Nome,CNPJ,Tipo,"
                    + "DataNasc,RG,Telefone,Celular,Email) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            Query1.setInt(1, getCod_End());
            Query1.setString(2, getNome());
            Query1.setString(3, getCNPJ());
            Query1.setInt(4, getTipo());
            Query1.setDate(5, getDataNasc());
            Query1.setString(6, getRG());
            Query1.setString(7, getTelefone());
            Query1.setString(8, getCelular());
            Query1.setString(9, getEmail());
            
            
            
            Query1.execute();
            
    }catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    return true;
    }
    
    public boolean Excluir(String Nome) {
        
            Connection c = ConectaBD.getconexao();

            String ConsultaSQL = "DELETE from fornecedor WHERE Nome = ?";
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
    
    public boolean Alterar(String Nome){
         Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";

        
            ConsultaSQL = "UPDATE fornecedor SET Email = ?, Telefone = ?,"
                    + "Celular = ? WHERE Nome = ?";
        
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            //ordens iguais 
            Query1.setString(1, Email);
            Query1.setString(2, Telefone);
            Query1.setString(3, Celular);
            Query1.setString(4, Nome);
            Query1.execute();
            
    }catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    return true;
    }
    
    public boolean Pesquisar(String Nome){
        Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";
        ConsultaSQL = "SELECT * FROM fornecedor WHERE Nome = ?";
        ResultSet rs;
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            Query1.setString(1, Nome);
            rs = Query1.executeQuery();
            if((rs.next())) {// se quisesse mais de uma colocar um while
                this.Nome = rs.getString("Nome");
                this.CNPJ = rs.getString("CNPJ");
                this.Tipo = rs.getInt("Tipo");
                this.DataNasc = rs.getDate("DataNasc");
                this.RG = rs.getString("RG");               
                this.Telefone = rs.getString("Telefone");
                this.Celular = rs.getString("Celular");
                this.Email = rs.getString("Email");
                this.Cod_End = rs.getInt("Endereco_idEndereco");
            }else return false;
        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
    
    public int PesquisaridFornecedor(String Nome){
        Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";
        ConsultaSQL = "SELECT Endereco_idEndereco FROM fornecedor WHERE Nome = ?";
        ResultSet rs;
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            Query1.setString(1, Nome);
            rs = Query1.executeQuery();
            if((rs.next())) {// se quisesse mais de uma colocar um while
                this.Codigo = rs.getInt("Endereco_idEndereco");
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
    
    public int PesquisarNome(String Nome){
        Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";
        ConsultaSQL = "SELECT Endereco_idEndereco FROM fornecedor WHERE Nome = ?";
        ResultSet rs;
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            Query1.setString(1, Nome);
            rs = Query1.executeQuery();
            if((rs.next())) {// se quisesse mais de uma colocar um while
                this.Codigo = rs.getInt("Endereco_idEndereco");
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
        ConsultaSQL = "SELECT f.Nome, f.Telefone, f.Celular, f.Email"
                + ",e.Cidade, e.Estado FROM fornecedor f, endereco e WHERE f.Endereco_idEndereco = e.idEndereco "
                + "order by f.idFornecedor";
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
              
                  dtm.addRow(new Object[]{""+rs.getString("f.Nome"),rs.getString("f.Telefone"),rs.getString("f.Celular")
                  ,rs.getString("f.Email"),rs.getString("e.Cidade"),rs.getString("e.Estado")});
            }

        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
    
    public static boolean carregaTabelaBusca(JTable jTableFornecedores, String nome) {
        Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";
        ConsultaSQL = "SELECT f.Nome, f.Telefone, f.Celular, f.Email"
                + ",e.Cidade, e.Estado FROM fornecedor f, endereco e WHERE f.Endereco_idEndereco = e.idEndereco AND "
                + "f.Nome LIKE ? "
                + "order by f.idFornecedor";
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
              
                  dtm.addRow(new Object[]{""+rs.getString("f.Nome"),rs.getString("f.Telefone"),rs.getString("f.Celular")
                  ,rs.getString("f.Email"),rs.getString("e.Cidade"),rs.getString("e.Estado")});
            }

        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
    
    
    public int getCodigo() {
        return Codigo;
    }

    public void setCodigo(int Codigo) {
        this.Codigo = Codigo;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String Nome) {
        this.Nome = Nome;
    }

     public int getCod_End() {
        return Cod_End;
    }

    /**
     * @param Cod_End the Cod_End to set
     */
    public void setCod_End(int Cod_End) {
        this.Cod_End = Cod_End;
    }

    /**
     * @return the CNPJ
     */
    public String getCNPJ() {
        return CNPJ;
    }

    /**
     * @param CNPJ the CNPJ to set
     */
    public void setCNPJ(String CNPJ) {
        this.CNPJ = CNPJ;
    }

    /**
     * @return the Tipo
     */
    public int getTipo() {
        return Tipo;
    }

    /**
     * @param Tipo the Tipo to set
     */
    public void setTipo(int Tipo) {
        this.Tipo = Tipo;
    }

    /**
     * @return the DataNasc
     */
    public Date getDataNasc() {
        return DataNasc;
    }

    /**
     * @param DataNasc the DataNasc to set
     */
    public void setDataNasc(Date DataNasc) {
        this.DataNasc = DataNasc;
    }

    /**
     * @return the RG
     */
    public String getRG() {
        return RG;
    }

    /**
     * @param RG the RG to set
     */
    public void setRG(String RG) {
        this.RG = RG;
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

    /**
     * @return the Email
     */
    public String getEmail() {
        return Email;
    }

    /**
     * @param Email the Email to set
     */
    public void setEmail(String Email) {
        this.Email = Email;
    }
}
