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
import java.text.SimpleDateFormat;
import javax.swing.JTable;

/**
 *
 * @author Gustavo
 */
public class ClsObra {

    

    
    
    private int Codigo;
    private int CodCliente;
    private String Cliente;
    private String Nome;
    private String Descricao;
    private Date DataI;
    private Date DataF;
    private String DataUtil;
    private String DataInicio;
    private String DataTermino;
    private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    
    public boolean Salvar(){
         Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";
        

        
        
            ConsultaSQL = "INSERT INTO obra (Nome,Descricao,Cliente_idCliente,"
                    + "DataInicio,DataTermino) values ( ?, ?, ?, ?, ?)";
        
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            Query1.setString(1, Nome);
            Query1.setString(2, Descricao);
            Query1.setInt(3, CodCliente);
            Query1.setDate(4, DataI);
            Query1.setDate(5, DataF);
            
            Query1.execute();
            
    }catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    return true;
    }
          
    public boolean Excluir(String Nome) {
        
            Connection c = ConectaBD.getconexao();

            String ConsultaSQL = "DELETE from obra WHERE Nome = ?";
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
    
    public boolean ExcluirRDOObra(int Codigo) {
        
            Connection c = ConectaBD.getconexao();

            String ConsultaSQL = "DELETE from rdoobra WHERE obra_idobra = ?";
            try {
                PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
                Query1.setInt(1, Codigo);
                //c.commit();
                Query1.execute();
            } catch (java.sql.SQLException e) {
                System.out.println(e.getMessage());
                return false;
            }
        return true;
    }
    
    public boolean ExcluirRDOGrupo(int Codigo) {
        
            Connection c = ConectaBD.getconexao();

            String ConsultaSQL = "DELETE from rdoobragrupo WHERE obra_idobra = ?";
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
    
    public boolean ExcluirFuncionariosRDO(int CodObra) {
        
            Connection c = ConectaBD.getconexao();

            String ConsultaSQL = "DELETE from rdoobra_has_funcionario WHERE RDOObra_idRDOObra = ?";
            try {
                PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
                Query1.setInt(1, CodObra);
                Query1.execute();
                //c.commit();
            } catch (java.sql.SQLException e) {
                System.out.println(e.getMessage());
                return false;
            }
        
        return true;
    }
    
    public boolean ExcluirMaodeObraRDO(int CodObra) {
        
            Connection c = ConectaBD.getconexao();

            String ConsultaSQL = "DELETE from maodeobra WHERE RDOObra_idRDOObra = ?";
            try {
                PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
                Query1.setInt(1, CodObra);
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

        
            ConsultaSQL = "UPDATE obra SET Descricao = ? WHERE Nome = ?";
        
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            //ordens iguais 
            Query1.setString(1, Descricao);
            Query1.setString(2, Nome);
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
        ConsultaSQL = "SELECT o.Nome, c.Nome, o.Descricao, o.DataInicio"
                + " ,o.DataTermino FROM obra o, cliente c WHERE o.Nome = ? and c.idCliente = o.Cliente_idCliente";
        ResultSet rs;
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            Query1.setString(1, Nome);
            rs = Query1.executeQuery();
            if((rs.next())) {// se quisesse mais de uma colocar um while
                this.Nome = rs.getString("o.Nome");
                this.Descricao = rs.getString("o.Descricao");
                this.DataI = rs.getDate("o.DataInicio");
                this.DataF = rs.getDate("o.DataTermino");
                this.Cliente = rs.getString("c.Nome");   
                
                setDataInicio(format.format(DataI));
                setDataTermino(format.format(DataF));
            }else return false;
        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
    
    public boolean carregaTabela(JTable jTableObra) {
        Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";
        ConsultaSQL = "SELECT o.Nome, c.Nome, o.Descricao, o.DataTermino"
                + " FROM obra o, cliente c WHERE o.Cliente_idCliente = c.idCliente "
                + "order by o.idobra";
        ResultSet rs;
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            
            rs = Query1.executeQuery();

            javax.swing.table.DefaultTableModel dtm = (javax.swing.table.DefaultTableModel)jTableObra.getModel();
            int cont = dtm.getRowCount(); 
            for(int i = 0; i < cont; i++) dtm.removeRow(0);
            while(rs.next()) {
                  //clsAluno objAluno = new clsAluno();
                  //objAluno.ExisteID(rs.getInt("matricula"));
                  
                  DataUtil = format.format(rs.getDate("o.DataTermino"));
              
                  dtm.addRow(new Object[]{""+rs.getString("o.Nome"),rs.getString("c.Nome"),rs.getString("o.Descricao")
                  ,DataUtil});
            }

        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
    
    public boolean carregaTabelaBusca(JTable jTableObra, String nome) {
        Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";
        ConsultaSQL = "SELECT o.Nome, c.Nome, o.Descricao, o.DataTermino"
                + " FROM obra o, cliente c WHERE o.Nome LIKE ? "
                + "order by o.idobra";
        ResultSet rs;
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            Query1.setString(1, "%"+nome+"%");
            rs = Query1.executeQuery();

            javax.swing.table.DefaultTableModel dtm = (javax.swing.table.DefaultTableModel)jTableObra.getModel();
            int cont = dtm.getRowCount(); 
            for(int i = 0; i < cont; i++) dtm.removeRow(0);
            while(rs.next()) {
                  //clsAluno objAluno = new clsAluno();
                  //objAluno.ExisteID(rs.getInt("matricula"));
              
                  DataUtil = format.format(rs.getDate("o.DataTermino"));
              
                  dtm.addRow(new Object[]{""+rs.getString("c.Nome"),rs.getString("c.Nome"),rs.getString("o.Descricao")
                  ,DataUtil});
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
        ConsultaSQL = "SELECT idobra FROM obra WHERE Nome = ?";
        ResultSet rs;
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            Query1.setString(1, Nome);
            rs = Query1.executeQuery();
            if((rs.next())) {// se quisesse mais de uma colocar um while
                this.Codigo = rs.getInt("idobra");
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
    
    public int PesquisaridCliente(String Nome){
        Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";
        ConsultaSQL = "SELECT idCliente FROM cliente WHERE Nome = ?";
        ResultSet rs;
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            Query1.setString(1, Nome);
            rs = Query1.executeQuery();
            if((rs.next())) {// se quisesse mais de uma colocar um while
                this.Codigo = rs.getInt("idCliente");
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
     * @return the Cliente
     */
    public String getCliente() {
        return Cliente;
    }

    /**
     * @param Cliente the Cliente to set
     */
    public void setCliente(String Cliente) {
        this.Cliente = Cliente;
    }

    /**
     * @return the DataI
     */
    public Date getDataI() {
        return DataI;
    }

    /**
     * @param DataI the DataI to set
     */
    public void setDataI(Date DataI) {
        this.DataI = DataI;
    }

    /**
     * @return the DataF
     */
    public Date getDataF() {
        return DataF;
    }

    /**
     * @param DataF the DataF to set
     */
    public void setDataF(Date DataF) {
        this.DataF = DataF;
    }
    
    /**
     * @return the CodCliente
     */
    public int getCodCliente() {
        return CodCliente;
    }

    /**
     * @param CodCliente the CodCliente to set
     */
    public void setCodCliente(int CodCliente) {
        this.CodCliente = CodCliente;
    }
    
    /**
     * @return the DataInicio
     */
    public String getDataInicio() {
        return DataInicio;
    }

    /**
     * @param DataInicio the DataInicio to set
     */
    public void setDataInicio(String DataInicio) {
        this.DataInicio = DataInicio;
    }

    /**
     * @return the DataTermino
     */
    public String getDataTermino() {
        return DataTermino;
    }

    /**
     * @param DataTermino the DataTermino to set
     */
    public void setDataTermino(String DataTermino) {
        this.DataTermino = DataTermino;
    }
    
}
