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
public class ClsRDOGeral {
 
    private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    private String DataRDO;
    private String Clima;
    private String Responsavel;
    private Date Data;
    private String AtvDesenvolvida;
    private int CodObra;
    private int Codigo;
    private int CodMaodeObra;
    private String MaodeObra;
    private int Quantidade;
    private String Ocorrencia;
    
    public boolean Salvar(){
        Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";

        /*(ClsEndereco.ExisteID(Cod_Fun)!=true) 
            ConsultaSQL = "UPDATE endereço SET Codigo = ?, Rua = ?, Numero = ?, Bairro = ?,"+
                    "Cidade = ?, Estado = ?";*/
        
            ConsultaSQL = "INSERT INTO rdoobra ( Clima, Responsavel, Data, "
                    + "AtvDesenvolvida, obra_idobra, Ocorrencia) values (?, ?, ?, ?, ?, ?)";
        
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            Query1.setString(1, Clima);
            Query1.setString(2, Responsavel);
            Query1.setDate(3, Data);
            Query1.setString(4, AtvDesenvolvida);
            Query1.setInt(5, CodObra);
            Query1.setString(6, Ocorrencia);

            Query1.execute();
            
    }catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    return true;
    }
    
    public int ExisteRDO(int CodObra){
        Connection c = ConectaBD.getconexao();

        int i = 0;
        
        String ConsultaSQL = "";
        ConsultaSQL = "SELECT * FROM rdoobra r, obra o WHERE r.obra_idobra = ?";
        ResultSet rs;
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            Query1.setInt(1, CodObra);
            rs = Query1.executeQuery();

            
            
            
            while(rs.next()) {
                  //clsAluno objAluno = new clsAluno();
                  //objAluno.ExisteID(rs.getInt("matricula"));
                  if(rs.getInt("o.idobra") == CodObra){
                      i++;
                  }
                  
                  
            }  
            } catch (java.sql.SQLException e) {
                System.out.println(e.getMessage());
                return -1;
            }
        
        return i;
    }
    
    
    
    public boolean Excluir() {
        
            Connection c = ConectaBD.getconexao();

            String ConsultaSQL = "DELETE from rdoobra WHERE idRDOObra = ?";
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
    public boolean ExcluirMaodeObra() {
        
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
    
    public boolean Alterar(int Codigo){
         Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";

        
            ConsultaSQL = "UPDATE rdoobra SET Clima = ?, AtvDesenvolvida = ?, Ocorrencia = ?"
                    + " WHERE idRDOObra = ?";
        
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            //ordens iguais 
            Query1.setString(1, Clima);
            Query1.setString(2, AtvDesenvolvida);
            Query1.setString(3, Ocorrencia);
            Query1.setInt(4, Codigo);
            Query1.execute();
            
    }catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    return true;
    }
    
    
    
    public int PesquisaridObra(String Nome){
        Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";
        ConsultaSQL = "SELECT idobra FROM obra WHERE Nome = ?";
        ResultSet rs;
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            Query1.setString(1, Nome);
            rs = Query1.executeQuery();
            if((rs.next())) {
                this.Codigo = rs.getInt("idobra");

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
        ConsultaSQL = "SELECT idCliente FROM Cliente WHERE Nome = ?";
        ResultSet rs;
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            Query1.setString(1, Nome);
            rs = Query1.executeQuery();
            if((rs.next())) {
                this.Codigo = rs.getInt("idCliente");

            }else return -1;
        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
            return -1;
        }
        return Codigo;
    }
    
    
    
    public String PesquisarObra(int CodObra){
        Connection c = ConectaBD.getconexao();

        String Obra;
        
        String ConsultaSQL = "";
        ConsultaSQL = "SELECT Nome FROM obra WHERE  = ?";
        ResultSet rs;
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            Query1.setInt(1, CodObra);
            rs = Query1.executeQuery();
            if((rs.next())) {
                Obra = rs.getString("idObra");

            }else return "erro";
        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
            return "erro";
        }
        return Obra;
    }
    
    
    public boolean carregaTabela(JTable jTableRDOGeral) {
        Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";
        ConsultaSQL = "SELECT o.Nome, c.Nome, r.Responsavel, r.Data"
                + " FROM obra o, rdoobra r, cliente c WHERE o.idobra = r.obra_idobra and c.idCliente = o.Cliente_idCliente "
                + "order by r.idRDOObra desc";
        ResultSet rs;
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            
            rs = Query1.executeQuery();

            javax.swing.table.DefaultTableModel dtm = (javax.swing.table.DefaultTableModel)jTableRDOGeral.getModel();
            int cont = dtm.getRowCount(); 
            for(int i = 0; i < cont; i++) dtm.removeRow(0);
            while(rs.next()) {
                  //clsAluno objAluno = new clsAluno();
                  //objAluno.ExisteID(rs.getInt("matricula"));
              
                  DataRDO = format.format(rs.getDate("r.Data"));
                  
                  dtm.addRow(new Object[]{""+rs.getString("o.Nome"),rs.getString("c.Nome"),rs.getString("r.Responsavel")
                  ,DataRDO});
            }

        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
    
    public int PesquisaidRdogeral(String Responsavel, String Obra, Date dataSql) {
        Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";
        ConsultaSQL = "SELECT r.idRDOObra FROM rdoobra r, obra o WHERE r.Responsavel = ? and o.Nome = ? and r.Data = ?";
        ResultSet rs;
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            Query1.setString(1, Responsavel);
            Query1.setString(2, Obra);
            Query1.setDate(3, dataSql);
            rs = Query1.executeQuery();
            if((rs.next())) {
                this.Codigo = rs.getInt("r.idRDOObra");

            }else return -1;
        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
            return -1;
        }
        return Codigo;
    }
    
    public boolean PesquisaClimaAtv(String Obra, String Responsavel, Date dataSql) {
        Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";
        ConsultaSQL = "SELECT r.AtvDesenvolvida, r.Clima, r.Ocorrencia FROM rdoobra r, obra o WHERE r.Responsavel = ? and o.Nome = ? and r.Data = ?";
        ResultSet rs;
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            Query1.setString(1, Responsavel);
            Query1.setString(2, Obra);
            Query1.setDate(3, dataSql);
            rs = Query1.executeQuery();
            if((rs.next())) {
                this.AtvDesenvolvida = rs.getString("r.AtvDesenvolvida");
                this.Clima = rs.getString("r.Clima");
                this.Ocorrencia = rs.getString("r.Ocorrencia");

            }else return false;
        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
    
    public boolean CarregaObra(JTable jTableRDOGeral,String Nome) {
        Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";
        ConsultaSQL = "SELECT o.Nome, c.Nome, r.Responsavel, r.Data"
                + " FROM obra o, rdoobra r, cliente c WHERE o.idobra = r.obra_idobra and c.idCliente = o.Cliente_idCliente "
                + "and o.Nome LIKE ? "
                + "order by r.idRDOObra desc";
        ResultSet rs;
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            Query1.setString(1, "%"+Nome+"%");
            rs = Query1.executeQuery();

            javax.swing.table.DefaultTableModel dtm = (javax.swing.table.DefaultTableModel)jTableRDOGeral.getModel();
            int cont = dtm.getRowCount(); 
            for(int i = 0; i < cont; i++) dtm.removeRow(0);
            while(rs.next()) {
                  //clsAluno objAluno = new clsAluno();
                  //objAluno.ExisteID(rs.getInt("matricula"));
              
                  DataRDO = format.format(rs.getDate("r.Data"));
                  
                  dtm.addRow(new Object[]{""+rs.getString("o.Nome"),rs.getString("c.Nome"),rs.getString("r.Responsavel")
                  ,DataRDO});
            }

        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean CarregaCliente(JTable jTableRDOGeral, String Nome) {
        Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";
        ConsultaSQL = "SELECT o.Nome, c.Nome, r.Responsavel, r.Data"
                + " FROM obra o, rdoobra r, cliente c WHERE o.idobra = r.obra_idobra and c.idCliente = o.Cliente_idCliente "
                + "and c.Nome LIKE ? "
                + "order by r.idRDOObra desc";
        ResultSet rs;
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            Query1.setString(1, "%"+Nome+"%");
            rs = Query1.executeQuery();

            javax.swing.table.DefaultTableModel dtm = (javax.swing.table.DefaultTableModel)jTableRDOGeral.getModel();
            int cont = dtm.getRowCount(); 
            for(int i = 0; i < cont; i++) dtm.removeRow(0);
            while(rs.next()) {
                  //clsAluno objAluno = new clsAluno();
                  //objAluno.ExisteID(rs.getInt("matricula"));
              
                  DataRDO = format.format(rs.getDate("r.Data"));
                  
                  dtm.addRow(new Object[]{""+rs.getString("o.Nome"),rs.getString("c.Nome"),rs.getString("r.Responsavel")
                  ,DataRDO});
            }

        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean CarregaData(JTable jTableRDOGeral, Date dataSqlI, Date dataSqlF) {
        Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";
        ConsultaSQL = "SELECT o.Nome, c.Nome, r.Responsavel, r.Data"
                + " FROM obra o, rdoobra r, cliente c WHERE o.idobra = r.obra_idobra and c.idCliente = o.Cliente_idCliente "
                + "and r.Data BETWEEN ? and ? "
                + "order by r.idRDOObra desc";
        ResultSet rs;
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            Query1.setDate(1, dataSqlI);
            Query1.setDate(2, dataSqlF);
            rs = Query1.executeQuery();

            javax.swing.table.DefaultTableModel dtm = (javax.swing.table.DefaultTableModel)jTableRDOGeral.getModel();
            int cont = dtm.getRowCount(); 
            for(int i = 0; i < cont; i++) dtm.removeRow(0);
            while(rs.next()) {
                  //clsAluno objAluno = new clsAluno();
                  //objAluno.ExisteID(rs.getInt("matricula"));
              
                  DataRDO = format.format(rs.getDate("r.Data"));
                  
                  dtm.addRow(new Object[]{""+rs.getString("o.Nome"),rs.getString("c.Nome"),rs.getString("r.Responsavel")
                  ,DataRDO});
            }

        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
    
    /**
     * @return the Clima
     */
    public String getClima() {
        return Clima;
    }

    /**
     * @param Clima the Clima to set
     */
    public void setClima(String Clima) {
        this.Clima = Clima;
    }

    /**
     * @return the Responsavel
     */
    public String getResponsavel() {
        return Responsavel;
    }

    /**
     * @param Responsavel the Responsavel to set
     */
    public void setResponsavel(String Responsavel) {
        this.Responsavel = Responsavel;
    }

    /**
     * @return the Data
     */
    public Date getData() {
        return Data;
    }

    /**
     * @param Data the Data to set
     */
    public void setData(Date Data) {
        this.Data = Data;
    }

    /**
     * @return the AtvDesenvolvida
     */
    public String getAtvDesenvolvida() {
        return AtvDesenvolvida;
    }

    /**
     * @param AtvDesenvolvida the AtvDesenvolvida to set
     */
    public void setAtvDesenvolvida(String AtvDesenvolvida) {
        this.AtvDesenvolvida = AtvDesenvolvida;
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
     * @return the Ocorrencia
     */
    public String getOcorrencia() {
        return Ocorrencia;
    }

    /**
     * @param Ocorrencia the Ocorrencia to set
     */
    public void setOcorrencia(String Ocorrencia) {
        this.Ocorrencia = Ocorrencia;
    }
    
}
