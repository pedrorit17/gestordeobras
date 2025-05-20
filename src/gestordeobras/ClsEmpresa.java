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
public class ClsEmpresa {

    private String Nome;
    private int Codigo;
    private String CNPJ;
    private String Telefone;
    private String Celular;
    private String Email;
    private int CodEndereco;
    
    
    
    public boolean Salvar(){
         Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";
        

        /*(ClsEndereco.ExisteID(Cod_Fun)!=true) 
            ConsultaSQL = "UPDATE endereço SET Codigo = ?, Rua = ?, Numero = ?, Bairro = ?,"+
                    "Cidade = ?, Estado = ?";*/
        
            ConsultaSQL = "INSERT INTO empresa (Endereco_idEndereco,Nome,CNPJ,Telefone,"
                    + "Celular,Email) values ( ?, ?, ?, ?, ?, ?)";
        
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            Query1.setInt(1, CodEndereco);
            Query1.setString(2, Nome);
            Query1.setString(3,CNPJ);
            Query1.setString(4, Telefone);
            Query1.setString(5, Celular);
            Query1.setString(6, Email);
            
            
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
    
    public boolean Alterar(String Nome){
         Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";

        
            ConsultaSQL = "UPDATE empresa SET Email = ?, Telefone = ?,"
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
        ConsultaSQL = "SELECT * FROM empresa WHERE Nome = ?";
        ResultSet rs;
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            Query1.setString(1, Nome);
            rs = Query1.executeQuery();
            if((rs.next())) {// se quisesse mais de uma colocar um while
                this.Nome = rs.getString("Nome");
                this.CNPJ = rs.getString("CNPJ");         
                this.Telefone = rs.getString("Telefone");
                this.Celular = rs.getString("Celular");
                this.Email = rs.getString("Email");
                this.CodEndereco = rs.getInt("Endereco_idEndereco");
            }else return false;
        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
    
    
    
    public int PesquisarExite(){
        Connection c = ConectaBD.getconexao();

        int i = 0;
        
        String ConsultaSQL = "";
        ConsultaSQL = "SELECT * FROM empresa";
        ResultSet rs;
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            rs = Query1.executeQuery();
            while(rs.next()) {
                  //clsAluno objAluno = new clsAluno();
                  //objAluno.ExisteID(rs.getInt("matricula"));
                  i++;
                  
            }
        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
            return -1;
        }
        return i;
    }
    
    public int PesquisarNome(String Nome){
        Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";
        ConsultaSQL = "SELECT idEmpresa FROM empresa WHERE Nome = ?";
        ResultSet rs;
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            Query1.setString(1, Nome);
            rs = Query1.executeQuery();
            if((rs.next())) {// se quisesse mais de uma colocar um while
                this.Codigo = rs.getInt("idEmpresa");
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

            String ConsultaSQL = "DELETE from empresa WHERE Nome = ?";
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
    
    public int PesquisaridEmpresa(String Nome){
        Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";
        ConsultaSQL = "SELECT Endereco_idEndereco FROM empresa WHERE Nome = ?";
        ResultSet rs;
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            Query1.setString(1, Nome);
            rs = Query1.executeQuery();
            if((rs.next())) {// se quisesse mais de uma colocar um while
                this.setCodigo(rs.getInt("Endereco_idEndereco"));
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
    
    public static boolean carregaTabela(JTable jTableFornecedor) {
        Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";
        ConsultaSQL = "SELECT f.Nome, f.Telefone, f.Celular, f.Email"
                + ",e.Cidade, e.Estado FROM empresa f, endereco e WHERE f.Endereco_idEndereco = e.idEndereco "
                + "order by f.idEmpresa";
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
                + ",e.Cidade, e.Estado FROM empresa f, endereco e WHERE f.Endereco_idEndereco = e.idEndereco AND "
                + "f.Nome LIKE ? "
                + "order by f.idEmpresa";
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

    /**
     * @return the CodEndereco
     */
    public int getCodEndereco() {
        return CodEndereco;
    }

    /**
     * @param CodEndereco the CodEndereco to set
     */
    public void setCodEndereco(int CodEndereco) {
        this.CodEndereco = CodEndereco;
    }

}
