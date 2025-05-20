/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestordeobras;

import Utilitarios.ConectaBD;
import com.sun.crypto.provider.DESCipher;
import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JTable;

/**
 *
 * @author Gustavo
 */
public class ClsInsumo {
    
    private int Codigo;
    private String Unidade;
    private Double Quantidade;
    private String Nome;
    private String Descricao;
    private int Critico;
    private int CodGrupoInsumo;
    
    public boolean Salvar(){
         Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";
        

        /*(ClsEndereco.ExisteID(Cod_Fun)!=true) 
            ConsultaSQL = "UPDATE endereço SET Codigo = ?, Rua = ?, Numero = ?, Bairro = ?,"+
                    "Cidade = ?, Estado = ?";*/
        
            ConsultaSQL = "INSERT INTO insumo (Unidade,Nome,Descricao"
                    + ",Critico,GrupoInsumo_idGrupoInsumo) values (?, ?, ?, ?, ?)";
        
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            Query1.setString(1, Unidade);
            Query1.setString(2, Nome);
            Query1.setString(3, Descricao);
            Query1.setInt(4, Critico);
            Query1.setInt(5, CodGrupoInsumo);
            
            
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
    
    public boolean Excluir(String Nome) {
        
            Connection c = ConectaBD.getconexao();

            String ConsultaSQL = "DELETE from insumo WHERE Nome = ?";
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
        ConsultaSQL = "SELECT * FROM insumo WHERE Nome = ?";
        ResultSet rs;
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            Query1.setString(1, Nome);
            rs = Query1.executeQuery();
            if((rs.next())) {// se quisesse mais de uma colocar um while
                this.Nome = rs.getString("Nome");
                this.Unidade = rs.getString("Unidade");
                this.Quantidade = rs.getDouble("Quantidade");
                this.Critico = rs.getInt("Critico");
                this.CodGrupoInsumo = rs.getInt("GrupoInsumo_idGrupoInsumo");               
                this.Descricao = rs.getString("Descricao");
            }else return false;
        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
    
    public boolean Altera(String Nome){
         Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";

       
            ConsultaSQL = "UPDATE insumo SET  Unidade = ?, Descricao = ?, Critico = ? WHERE Nome = ?";
        
          
        
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            //ordens iguais 
            Query1.setString(1, Unidade);
            Query1.setString(2, Descricao);
            Query1.setInt(3, Critico);
            Query1.setString(4, Nome);            
            Query1.execute();
            
    }catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    return true;
    }
    
    public boolean AlteraQuantidade(int CodI, Double Qtd){
         Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";

       
            ConsultaSQL = "UPDATE insumo SET  Quantidade = ? WHERE idInsumo = ?";
        
          
        
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            //ordens iguais 
            Query1.setDouble(1, Qtd);
            Query1.setInt(2, CodI);

            
            
             
            Query1.execute();
            
    }catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    return true;
    }
    
    public String PesquisarNomeGrupoInsumo(){
        Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";
        ConsultaSQL = "SELECT Nome FROM grupoinsumo ORDER BY idGrupoInsumo";
        ResultSet rs;
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            Query1.setString(1, Nome);
            rs = Query1.executeQuery();
            if((rs.next())) {// se quisesse mais de uma colocar um while
                this.setNome(rs.getString("Nome"));
                /*this.Nome = rs.getString("Nome");
                this.Audio = rs.getString("Audio");
                this.Diretor = rs.getString("Diretor");
                this.Classificacao = rs.getString("Classificação");               
                this.Duracao = rs.getString("Duração");*/
            }else return "erro";
        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
            return "erro";
        }
        return getNome();
    }
    
    public String PesquisarNomeGrupoInsumo(int CodGP){
        Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";
        ConsultaSQL = "SELECT Nome FROM grupoinsumo WHERE idGrupoInsumo = ?";
        ResultSet rs;
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            Query1.setInt(1, CodGP);
            rs = Query1.executeQuery();
            if((rs.next())) {// se quisesse mais de uma colocar um while
                this.setNome(rs.getString("Nome"));
                /*this.Nome = rs.getString("Nome");
                this.Audio = rs.getString("Audio");
                this.Diretor = rs.getString("Diretor");
                this.Classificacao = rs.getString("Classificação");               
                this.Duracao = rs.getString("Duração");*/
            }else return "erro";
        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
            return "erro";
        }
        return getNome();
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
    
    public int PesquisaridGrupoInsumo(String Nome){
        Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";
        ConsultaSQL = "SELECT idGrupoInsumo FROM grupoinsumo WHERE Nome = ?";
        ResultSet rs;
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            Query1.setString(1, Nome);
            rs = Query1.executeQuery();
            if((rs.next())) {// se quisesse mais de uma colocar um while
                this.setCodigo(rs.getInt("idGrupoInsumo"));
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
    
    public int PesquisarNome(String Nome){
        Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";
        ConsultaSQL = "SELECT idInsumo FROM insumo WHERE Nome = ?";
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
    
    public static boolean carregaTabela(JTable jTableInsumo) {
        Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";
        ConsultaSQL = "SELECT Nome, Quantidade, Unidade, Critico"
                + ", Descricao FROM insumo order by idInsumo";
        ResultSet rs;
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            
            rs = Query1.executeQuery();

            javax.swing.table.DefaultTableModel dtm = (javax.swing.table.DefaultTableModel)jTableInsumo.getModel();
            int cont = dtm.getRowCount(); 
            for(int i = 0; i < cont; i++) dtm.removeRow(0);
            while(rs.next()) {
                  //clsAluno objAluno = new clsAluno();
                  //objAluno.ExisteID(rs.getInt("matricula"));
              
                  dtm.addRow(new Object[]{""+rs.getString("Nome"),rs.getString("Unidade")
                  ,rs.getString("Critico"),rs.getString("Descricao")});
            }

        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
    
    public static boolean carregaTabelaCritico(JTable jTableInsumo) {
        Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";
        ConsultaSQL = "SELECT Nome, Quantidade FROM insumo WHERE Quantidade < Critico order by idInsumo";
        ResultSet rs;
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            
            rs = Query1.executeQuery();

            javax.swing.table.DefaultTableModel dtm = (javax.swing.table.DefaultTableModel)jTableInsumo.getModel();
            int cont = dtm.getRowCount(); 
            for(int i = 0; i < cont; i++) dtm.removeRow(0);
            while(rs.next()) {
                  //clsAluno objAluno = new clsAluno();
                  //objAluno.ExisteID(rs.getInt("matricula"));
              
                  dtm.addRow(new Object[]{""+rs.getString("Nome"),rs.getString("Quantidade")});
            }

        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
    
    
    public static boolean carregaTabelaBusca(JTable jTableInsumo, String nome) {
        Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";
        ConsultaSQL = "SELECT Nome, Quantidade, Unidade, Critico"
                + ", Descricao FROM insumo WHERE Nome LIKE ? order by idInsumo";
        ResultSet rs;
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            Query1.setString(1, "%"+nome+"%");
            rs = Query1.executeQuery();

            javax.swing.table.DefaultTableModel dtm = (javax.swing.table.DefaultTableModel)jTableInsumo.getModel();
            int cont = dtm.getRowCount(); 
            for(int i = 0; i < cont; i++) dtm.removeRow(0);
            while(rs.next()) {
                  //clsAluno objAluno = new clsAluno();
                  //objAluno.ExisteID(rs.getInt("matricula"));
              
                  dtm.addRow(new Object[]{""+rs.getString("Nome"),rs.getString("Unidade")
                  ,rs.getString("Critico"),rs.getString("Descricao")});
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
     * @return the Unidade
     */
    public String getUnidade() {
        return Unidade;
    }

    /**
     * @param Unidade the Unidade to set
     */
    public void setUnidade(String Unidade) {
        this.Unidade = Unidade;
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
     * @return the Critico
     */
    public int getCritico() {
        return Critico;
    }

    /**
     * @param Critico the Critico to set
     */
    public void setCritico(int Critico) {
        this.Critico = Critico;
    }

    /**
     * @return the CodGrupoInsumo
     */
    public int getCodGrupoInsumo() {
        return CodGrupoInsumo;
    }

    /**
     * @param CodGrupoInsumo the CodGrupoInsumo to set
     */
    public void setCodGrupoInsumo(int CodGrupoInsumo) {
        this.CodGrupoInsumo = CodGrupoInsumo;
    }
    
}
