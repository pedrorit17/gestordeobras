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
public class ClsEstoqueEntrada {
    
    private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    private String DataEntrada;

    public boolean carregaTabela(JTable jTableEstoqueEntrada) {
        Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";
        ConsultaSQL = "SELECT i.Nome, g.Nome, e.DataEntrada, e.Quantidade"
                + " FROM insumo i, grupoinsumo g, entradainsumo e WHERE i.idInsumo = e.Insumo_idInsumo and "
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
              
                  DataEntrada = format.format(rs.getDate("e.DataEntrada"));
                  
                  dtm.addRow(new Object[]{""+rs.getString("i.Nome"),rs.getString("g.Nome"),DataEntrada
                  ,rs.getDouble("e.Quantidade")});
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
        ConsultaSQL = "SELECT i.Nome, g.Nome, e.DataEntrada, e.Quantidade"
                + " FROM insumo i, grupoinsumo g, entradainsumo e WHERE i.idInsumo = e.Insumo_idInsumo and "
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
              
                  DataEntrada = format.format(rs.getDate("e.DataEntrada"));
                  
                  dtm.addRow(new Object[]{""+rs.getString("i.Nome"),rs.getString("g.Nome"),DataEntrada
                  ,rs.getDouble("e.Quantidade")});
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
        ConsultaSQL = "SELECT i.Nome, g.Nome, e.DataEntrada, e.Quantidade"
                + " FROM insumo i, grupoinsumo g, entradainsumo e WHERE i.idInsumo = e.Insumo_idInsumo and "
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
              
                  DataEntrada = format.format(rs.getDate("e.DataEntrada"));
                  
                  dtm.addRow(new Object[]{""+rs.getString("i.Nome"),rs.getString("g.Nome"),DataEntrada
                  ,rs.getDouble("e.Quantidade")});
            }

        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean carregaBuscaData(JTable jTableEstoqueEntrada, Date dataSqlI, Date dataSqlF) {
        Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";
        ConsultaSQL = "SELECT i.Nome, g.Nome, e.DataEntrada, e.Quantidade"
                + " FROM insumo i, grupoinsumo g, entradainsumo e WHERE i.idInsumo = e.Insumo_idInsumo and "
                + "i.GrupoInsumo_idGrupoInsumo = g.idGrupoInsumo and e.DataEntrada BETWEEN ? and ? "
                + "order by i.Nome";
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
              
                  DataEntrada = format.format(rs.getDate("e.DataEntrada"));
                  
                  dtm.addRow(new Object[]{""+rs.getString("i.Nome"),rs.getString("g.Nome"),DataEntrada
                  ,rs.getDouble("e.Quantidade")});
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
        ConsultaSQL = "SELECT i.Nome, g.Nome, e.DataEntrada, e.Quantidade"
                + " FROM insumo i, grupoinsumo g, entradainsumo e WHERE i.idInsumo = e.Insumo_idInsumo and "
                + "i.GrupoInsumo_idGrupoInsumo = g.idGrupoInsumo and i.Nome LIKE ? and e.DataEntrada BETWEEN ? and ? "
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
              
                  DataEntrada = format.format(rs.getDate("e.DataEntrada"));
                  
                  dtm.addRow(new Object[]{""+rs.getString("i.Nome"),rs.getString("g.Nome"),DataEntrada
                  ,rs.getDouble("e.Quantidade")});
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
        ConsultaSQL = "SELECT i.Nome, g.Nome, e.DataEntrada, e.Quantidade"
                + " FROM insumo i, grupoinsumo g, entradainsumo e WHERE i.idInsumo = e.Insumo_idInsumo and "
                + "i.GrupoInsumo_idGrupoInsumo = g.idGrupoInsumo and g.Nome LIKE ? and e.DataEntrada BETWEEN ? and ? "
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
              
                  DataEntrada = format.format(rs.getDate("e.DataEntrada"));
                  
                  dtm.addRow(new Object[]{""+rs.getString("i.Nome"),rs.getString("g.Nome"),DataEntrada
                  ,rs.getDouble("e.Quantidade")});
            }

        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    
    
}
