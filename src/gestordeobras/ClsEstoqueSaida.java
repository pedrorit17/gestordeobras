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
public class ClsEstoqueSaida {

    private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    private String DataEntrada;

    
    public boolean carregaTabela(JTable jTableEstoqueEntrada) {
        Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";
        ConsultaSQL = "SELECT i.Nome, g.Nome, s.DataSaida, s.Quantidade"
                + " FROM insumo i, grupoinsumo g, saidainsumo s WHERE i.idInsumo = s.Insumo_idInsumo and "
                + "i.GrupoInsumo_idGrupoInsumo = g.idGrupoInsumo "
                + "order by i.Nome";
        ResultSet rs;
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            
            rs = Query1.executeQuery();

            javax.swing.table.DefaultTableModel dtm = (javax.swing.table.DefaultTableModel)jTableEstoqueEntrada.getModel();
            int cont = dtm.getRowCount(); 
            for(int i = 0; i < cont; i++) dtm.removeRow(0);
            while(rs.next()) {
                  //clsAluno objAluno = new clsAluno();
                  //objAluno.ExisteID(rs.getInt("matricula"));
              
                  DataEntrada = format.format(rs.getDate("s.DataSaida"));
                  
                  dtm.addRow(new Object[]{""+rs.getString("i.Nome"),rs.getString("g.Nome"),DataEntrada
                  ,rs.getDouble("s.Quantidade")});
            }

        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean carregaBuscaI(JTable jTableEstoqueEntrada, String NomeI) {
        Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";
        ConsultaSQL = "SELECT i.Nome, g.Nome, s.DataSaida, s.Quantidade"
                + " FROM insumo i, grupoinsumo g, saidainsumo s WHERE i.idInsumo = s.Insumo_idInsumo and "
                + "i.GrupoInsumo_idGrupoInsumo = g.idGrupoInsumo and i.Nome LIKE ? "
                + "order by i.Nome";
        ResultSet rs;
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            Query1.setString(1, "%"+NomeI+"%");
            rs = Query1.executeQuery();

            javax.swing.table.DefaultTableModel dtm = (javax.swing.table.DefaultTableModel)jTableEstoqueEntrada.getModel();
            int cont = dtm.getRowCount(); 
            for(int i = 0; i < cont; i++) dtm.removeRow(0);
            while(rs.next()) {
                  //clsAluno objAluno = new clsAluno();
                  //objAluno.ExisteID(rs.getInt("matricula"));
              
                  DataEntrada = format.format(rs.getDate("s.DataSaida"));
                  
                  dtm.addRow(new Object[]{""+rs.getString("i.Nome"),rs.getString("g.Nome"),DataEntrada
                  ,rs.getDouble("s.Quantidade")});
            }

        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean carregaBuscaGI(JTable jTableEstoqueEntrada, String NomeGI) {
        Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";
        ConsultaSQL = "SELECT i.Nome, g.Nome, s.DataSaida, s.Quantidade"
                + " FROM insumo i, grupoinsumo g, saidainsumo s WHERE i.idInsumo = s.Insumo_idInsumo and "
                + "i.GrupoInsumo_idGrupoInsumo = g.idGrupoInsumo and g.Nome LIKE ? "
                + "order by i.Nome";
        ResultSet rs;
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            Query1.setString(1, "%"+NomeGI+"%");
            rs = Query1.executeQuery();

            javax.swing.table.DefaultTableModel dtm = (javax.swing.table.DefaultTableModel)jTableEstoqueEntrada.getModel();
            int cont = dtm.getRowCount(); 
            for(int i = 0; i < cont; i++) dtm.removeRow(0);
            while(rs.next()) {
                  //clsAluno objAluno = new clsAluno();
                  //objAluno.ExisteID(rs.getInt("matricula"));
              
                  DataEntrada = format.format(rs.getDate("s.DataSaida"));
                  
                  dtm.addRow(new Object[]{""+rs.getString("i.Nome"),rs.getString("g.Nome"),DataEntrada
                  ,rs.getDouble("s.Quantidade")});
            }

        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    
    //Eugênio: agora essa busca será com o somatório agrupado por insumo
    public boolean carregaBuscaData(JTable jTableEstoqueEntrada, Date dataSqlI, Date dataSqlF) {
        Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";
        ConsultaSQL = "SELECT i.Nome, g.Nome, s.DataSaida, SUM(s.Quantidade) as soma"
                + " FROM insumo i, grupoinsumo g, saidainsumo s WHERE i.idInsumo = s.Insumo_idInsumo and "
                + "i.GrupoInsumo_idGrupoInsumo = g.idGrupoInsumo and s.DataSaida BETWEEN ? and ? "
                + "GROUP BY s.Insumo_idInsumo "
                + "ORDER BY i.Nome";
        ResultSet rs;
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            Query1.setDate(1, dataSqlI);
            Query1.setDate(2, dataSqlF);
            rs = Query1.executeQuery();

            javax.swing.table.DefaultTableModel dtm = (javax.swing.table.DefaultTableModel)jTableEstoqueEntrada.getModel();
            int cont = dtm.getRowCount(); 
            for(int i = 0; i < cont; i++) dtm.removeRow(0);
            while(rs.next()) {
                  //clsAluno objAluno = new clsAluno();
                  //objAluno.ExisteID(rs.getInt("matricula"));
              
                  DataEntrada = format.format(rs.getDate("s.DataSaida"));
                  
                  dtm.addRow(new Object[]{""+rs.getString("i.Nome"),rs.getString("g.Nome"),DataEntrada
                  ,rs.getDouble("soma")});
            }

        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean carregaBuscaIData(JTable jTableEstoqueEntrada, String NomeI, Date dataSqlI, Date dataSqlF) {
        Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";
        ConsultaSQL = "SELECT i.Nome, g.Nome, s.DataSaida, s.Quantidade"
                + " FROM insumo i, grupoinsumo g, saidainsumo s WHERE i.idInsumo = s.Insumo_idInsumo and "
                + "i.GrupoInsumo_idGrupoInsumo = g.idGrupoInsumo and i.Nome LIKE ? and s.DataSaida BETWEEN ? and ? "
                + "order by i.Nome";
        ResultSet rs;
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            Query1.setString(1, "%"+NomeI+"%");
            Query1.setDate(2, dataSqlI);
            Query1.setDate(3, dataSqlF);
            rs = Query1.executeQuery();

            javax.swing.table.DefaultTableModel dtm = (javax.swing.table.DefaultTableModel)jTableEstoqueEntrada.getModel();
            int cont = dtm.getRowCount(); 
            for(int i = 0; i < cont; i++) dtm.removeRow(0);
            while(rs.next()) {
                  //clsAluno objAluno = new clsAluno();
                  //objAluno.ExisteID(rs.getInt("matricula"));
              
                  DataEntrada = format.format(rs.getDate("s.DataSaida"));
                  
                  dtm.addRow(new Object[]{""+rs.getString("i.Nome"),rs.getString("g.Nome"),DataEntrada
                  ,rs.getDouble("s.Quantidade")});
            }

        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean carregaBuscaGIData(JTable jTableEstoqueEntrada, String NomeGI, Date dataSqlI, Date dataSqlF) {
        Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";
        ConsultaSQL = "SELECT i.Nome, g.Nome, s.DataSaida, s.Quantidade"
                + " FROM insumo i, grupoinsumo g, saidainsumo s WHERE i.idInsumo = s.Insumo_idInsumo and "
                + "i.GrupoInsumo_idGrupoInsumo = g.idGrupoInsumo and g.Nome LIKE ? and s.DataSaida BETWEEN ? and ? "
                + "order by i.Nome";
        ResultSet rs;
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            Query1.setString(1, "%"+NomeGI+"%");
            Query1.setDate(2, dataSqlI);
            Query1.setDate(3, dataSqlF);
            rs = Query1.executeQuery();

            javax.swing.table.DefaultTableModel dtm = (javax.swing.table.DefaultTableModel)jTableEstoqueEntrada.getModel();
            int cont = dtm.getRowCount(); 
            for(int i = 0; i < cont; i++) dtm.removeRow(0);
            while(rs.next()) {
                  //clsAluno objAluno = new clsAluno();
                  //objAluno.ExisteID(rs.getInt("matricula"));
              
                  DataEntrada = format.format(rs.getDate("s.DataSaida"));
                  
                  dtm.addRow(new Object[]{""+rs.getString("i.Nome"),rs.getString("g.Nome"),DataEntrada
                  ,rs.getDouble("s.Quantidade")});
            }

        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
    
    public boolean carregaBuscaGIeI(JTable jTableEstoqueEntrada, String NomeGI, String NomeI) {
        Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";
        ConsultaSQL = "SELECT i.Nome, g.Nome, s.DataSaida, s.Quantidade"
                + " FROM insumo i, grupoinsumo g, saidainsumo s WHERE i.idInsumo = s.Insumo_idInsumo and "
                + "i.GrupoInsumo_idGrupoInsumo = g.idGrupoInsumo and g.Nome LIKE ? and i.Nome LIKE ? "
                + "order by i.Nome";
        ResultSet rs;
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            Query1.setString(1, "%"+NomeGI+"%");
            Query1.setString(2, "%"+NomeI+"%");
            rs = Query1.executeQuery();

            javax.swing.table.DefaultTableModel dtm = (javax.swing.table.DefaultTableModel)jTableEstoqueEntrada.getModel();
            int cont = dtm.getRowCount(); 
            for(int i = 0; i < cont; i++) dtm.removeRow(0);
            while(rs.next()) {
                  //clsAluno objAluno = new clsAluno();
                  //objAluno.ExisteID(rs.getInt("matricula"));
              
                  DataEntrada = format.format(rs.getDate("s.DataSaida"));
                  
                  dtm.addRow(new Object[]{""+rs.getString("i.Nome"),rs.getString("g.Nome"),DataEntrada
                  ,rs.getDouble("s.Quantidade")});
            }

        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
    
}

