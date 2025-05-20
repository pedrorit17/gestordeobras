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
public class ClsEstoque {
    
    private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    private String DataCompra;
    
    
    public static boolean carregaTabelaAtual(JTable jTableFornecedor) {
        Connection c = ConectaBD.getconexao();

        
        String ConsultaSQL = "";
        ConsultaSQL = "SELECT i.Descricao, i.Unidade, i.Quantidade, g.Nome "
                + "FROM insumo i, grupoinsumo g WHERE i.GrupoInsumo_idGrupoInsumo = g.idGrupoInsumo "
                + "order by i.Nome";
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
                  
                  
              
                  dtm.addRow(new Object[]{""+rs.getString("i.Descricao"),rs.getString("g.Nome"),rs.getString("i.Quantidade")
                  ,rs.getString("i.Unidade")});
            }

        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
    
    public boolean carregaTabelaPedidos(JTable jTableFornecedor) {
        Connection c = ConectaBD.getconexao();

        double PU,Qtd, Total;
        
        String ConsultaSQL = "";
        ConsultaSQL = "SELECT f.Nome, i.Descricao, i.Unidade, p.PrecoUnitario, p.Quantidade, p.DataCompra, p.Descricao "
                + "FROM fornecedor f, insumo i, pedidocompras p WHERE f.idFornecedor = p.Fornecedor_idFornecedor and"
                + " i.idInsumo = p.Insumo_idInsumo "
                + "order by f.Nome";
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
                  
                  Qtd = rs.getDouble("p.Quantidade");
                  PU = rs.getDouble("p.PrecoUnitario");
                  Total = Qtd*PU;
              
                  DataCompra = format.format(rs.getDate("p.DataCompra"));
              
                  dtm.addRow(new Object[]{""+rs.getString("f.Nome"),rs.getString("i.Descricao"),DataCompra,rs.getString("i.Unidade"),
                  rs.getString("p.PrecoUnitario"),rs.getString("p.Quantidade"),Total,rs.getString("p.Descricao")});
            }

        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
    
    //Eugênio 03/01/2018
    //Nome: nome do grupo
    //Descr: descrição do item
    public boolean carregaTabelaBusca(JTable jTableEstoque, String nome, String descr) {
        Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";
        ConsultaSQL = "SELECT i.Descricao, g.Nome, i.Quantidade, i.Unidade"
                + " FROM grupoinsumo g, insumo i WHERE i.GrupoInsumo_idGrupoInsumo = g.idGrupoInsumo AND "
                + " g.Nome LIKE ? AND"
                + " i.Descricao LIKE ?"
                + " order by g.idGrupoInsumo";
        ResultSet rs;
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            Query1.setString(1, "%"+nome+"%");
            Query1.setString(2, "%"+descr+"%");
            rs = Query1.executeQuery();

            javax.swing.table.DefaultTableModel dtm = (javax.swing.table.DefaultTableModel)jTableEstoque.getModel();
            int cont = dtm.getRowCount(); 
            for(int i = 0; i < cont; i++) dtm.removeRow(0);
            while(rs.next()) {
                  //clsAluno objAluno = new clsAluno();
                  //objAluno.ExisteID(rs.getInt("matricula"))
                  
                  dtm.addRow(new Object[]{""+rs.getString("i.Descricao"),rs.getString("g.Nome"),rs.getString("i.Quantidade")
                  ,rs.getString("i.Unidade")});
            }

        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
    
    public boolean carregaTabelaBuscaF(JTable jTableEstoque, String NomeF) {
        Connection c = ConectaBD.getconexao();

        double PU,Qtd, Total;
        
        String ConsultaSQL = "";
        ConsultaSQL = "SELECT f.Nome, i.Descricao, i.Unidade, p.PrecoUnitario, p.Quantidade, p.DataCompra,p.Descricao "
                + "FROM fornecedor f, insumo i, pedidocompras p WHERE f.idFornecedor = p.Fornecedor_idFornecedor and"
                + " i.idInsumo = p.Insumo_idInsumo and f.Nome LIKE ? "
                + "order by f.Nome";
        ResultSet rs;
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            Query1.setString(1, "%"+NomeF+"%");
            rs = Query1.executeQuery();

            javax.swing.table.DefaultTableModel dtm = (javax.swing.table.DefaultTableModel)jTableEstoque.getModel();
            int cont = dtm.getRowCount(); 
            for(int i = 0; i < cont; i++) dtm.removeRow(0);
            while(rs.next()) {
                  //clsAluno objAluno = new clsAluno();
                  //objAluno.ExisteID(rs.getInt("matricula"));
                  
                  Qtd = rs.getDouble("p.Quantidade");
                  PU = rs.getDouble("p.PrecoUnitario");
                  Total = Qtd*PU;
              
                  DataCompra = format.format(rs.getDate("p.DataCompra"));
              
                  dtm.addRow(new Object[]{""+rs.getString("f.Nome"),rs.getString("i.Descricao"),DataCompra,rs.getString("i.Unidade"),
                  rs.getString("p.PrecoUnitario"),rs.getString("p.Quantidade"),Total,rs.getString("p.Descricao")});
            }

        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    
        
    public boolean carregaTabelaBuscaFDIDF(JTable jTablePedidos, Date DataInicio, Date DataFim) {
        Connection c = ConectaBD.getconexao();

        double PU,Qtd, Total;
        
        String ConsultaSQL = "";
        ConsultaSQL = "SELECT f.Nome, i.Descricao, i.Unidade, p.PrecoUnitario, p.Quantidade, p.DataCompra, p.Descricao "
                + "FROM fornecedor f, insumo i, pedidocompras p WHERE f.idFornecedor = p.Fornecedor_idFornecedor and"
                + " i.idInsumo = p.Insumo_idInsumo and DataCompra BETWEEN ? and ? "
                + "order by f.Nome";
        ResultSet rs;
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            Query1.setDate(1, DataInicio);
            Query1.setDate(2, DataFim);
            rs = Query1.executeQuery();

            javax.swing.table.DefaultTableModel dtm = (javax.swing.table.DefaultTableModel)jTablePedidos.getModel();
            int cont = dtm.getRowCount(); 
            for(int i = 0; i < cont; i++) dtm.removeRow(0);
            while(rs.next()) {
                  //clsAluno objAluno = new clsAluno();
                  //objAluno.ExisteID(rs.getInt("matricula"));
                  
                  Qtd = rs.getDouble("p.Quantidade");
                  PU = rs.getDouble("p.PrecoUnitario");
                  Total = Qtd*PU;
                  
                  DataCompra = format.format(rs.getDate("p.DataCompra"));
              
                  dtm.addRow(new Object[]{""+rs.getString("f.Nome"),rs.getString("i.Descricao"),DataCompra,rs.getString("i.Unidade"),
                  rs.getString("p.PrecoUnitario"),rs.getString("p.Quantidade"),Total,rs.getString("p.Descricao")});
            }

        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean carregaTabelaBuscaFAll(JTable jTablePedidos, String Nome, Date DataInicio, Date DataFim) {
        Connection c = ConectaBD.getconexao();

        double PU,Qtd, Total;
        
        String ConsultaSQL = "";
        ConsultaSQL = "SELECT f.Nome, i.Descricao, i.Unidade, p.PrecoUnitario, p.Quantidade, p.DataCompra, p.Descricao "
                + "FROM fornecedor f, insumo i, pedidocompras p WHERE f.idFornecedor = p.Fornecedor_idFornecedor and"
                + " i.idInsumo = p.Insumo_idInsumo and f.Nome LIKE ? and DataCompra BETWEEN ? and ? "
                + "order by f.Nome";
        ResultSet rs;
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            Query1.setString(1, "%"+Nome+"%");
            Query1.setDate(2, DataInicio);
            Query1.setDate(3, DataFim);
            rs = Query1.executeQuery();

            javax.swing.table.DefaultTableModel dtm = (javax.swing.table.DefaultTableModel)jTablePedidos.getModel();
            int cont = dtm.getRowCount(); 
            for(int i = 0; i < cont; i++) dtm.removeRow(0);
            while(rs.next()) {
                  //clsAluno objAluno = new clsAluno();
                  //objAluno.ExisteID(rs.getInt("matricula"));
                  
                  Qtd = rs.getDouble("p.Quantidade");
                  PU = rs.getDouble("p.PrecoUnitario");
                  Total = Qtd*PU;
              
                  DataCompra = format.format(rs.getDate("p.DataCompra"));
              
                  dtm.addRow(new Object[]{""+rs.getString("f.Nome"),rs.getString("i.Descricao"),DataCompra,rs.getString("i.Unidade"),
                  rs.getString("p.PrecoUnitario"),rs.getString("p.Quantidade"),Total,rs.getString("p.Descricao")});
            }

        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean carregaTabelaBuscaI(JTable jTablePedidos, String NomeI) {
        Connection c = ConectaBD.getconexao();

        double PU,Qtd, Total;
        
        String ConsultaSQL = "";
        ConsultaSQL = "SELECT f.Nome, i.Descricao, i.Unidade, p.PrecoUnitario, p.Quantidade, p.DataCompra, p.Descricao "
                + "FROM fornecedor f, insumo i, pedidocompras p WHERE f.idFornecedor = p.Fornecedor_idFornecedor and"
                + " i.idInsumo = p.Insumo_idInsumo and i.Nome LIKE ? "
                + "order by f.Nome";
        ResultSet rs;
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            Query1.setString(1, "%"+NomeI+"%");
            rs = Query1.executeQuery();

            javax.swing.table.DefaultTableModel dtm = (javax.swing.table.DefaultTableModel)jTablePedidos.getModel();
            int cont = dtm.getRowCount(); 
            for(int i = 0; i < cont; i++) dtm.removeRow(0);
            while(rs.next()) {
                  //clsAluno objAluno = new clsAluno();
                  //objAluno.ExisteID(rs.getInt("matricula"));
                  
                  Qtd = rs.getDouble("p.Quantidade");
                  PU = rs.getDouble("p.PrecoUnitario");
                  Total = Qtd*PU;
                  
                  DataCompra = format.format(rs.getDate("p.DataCompra"));
              
                  dtm.addRow(new Object[]{""+rs.getString("f.Nome"),rs.getString("i.Descricao"),DataCompra,rs.getString("i.Unidade"),
                  rs.getString("p.PrecoUnitario"),rs.getString("p.Quantidade"),Total,rs.getString("p.Descricao")});
            }

        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean carregaTabelaBuscaFIAll(JTable jTablePedidos, String NomeF, Date DataInicio, Date DataFim, String NomeI) {
        Connection c = ConectaBD.getconexao();

        double PU,Qtd, Total;
        
        String ConsultaSQL = "";
        ConsultaSQL = "SELECT f.Nome, i.Descricao, i.Unidade, p.PrecoUnitario, p.Quantidade, p.DataCompra, p.Descricao "
                + "FROM fornecedor f, insumo i, pedidocompras p WHERE f.idFornecedor = p.Fornecedor_idFornecedor and"
                + " i.idInsumo = p.Insumo_idInsumo and f.Nome LIKE ? and DataCompra BETWEEN ? and ? and i.Nome LIKE ? "
                + "order by f.Nome";
        ResultSet rs;
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            Query1.setString(1, "%"+NomeF+"%");
            Query1.setDate(2, DataInicio);
            Query1.setDate(3, DataFim);
            Query1.setString(4, NomeI);
            rs = Query1.executeQuery();

            javax.swing.table.DefaultTableModel dtm = (javax.swing.table.DefaultTableModel)jTablePedidos.getModel();
            int cont = dtm.getRowCount(); 
            for(int i = 0; i < cont; i++) dtm.removeRow(0);
            while(rs.next()) {
                  //clsAluno objAluno = new clsAluno();
                  //objAluno.ExisteID(rs.getInt("matricula"));
                  
                  Qtd = rs.getDouble("p.Quantidade");
                  PU = rs.getDouble("p.PrecoUnitario");
                  Total = Qtd*PU;
                  
                  DataCompra = format.format(rs.getDate("p.DataCompra"));
              
                  dtm.addRow(new Object[]{""+rs.getString("f.Nome"),rs.getString("i.Descricao"),DataCompra,rs.getString("i.Unidade"),
                  rs.getString("p.PrecoUnitario"),rs.getString("p.Quantidade"),Total,rs.getString("p.Descricao")});
            }

        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean carregaTabelaBuscaIF(JTable jTablePedidos, String NomeF, String NomeI) {
        Connection c = ConectaBD.getconexao();

        double PU,Qtd, Total;
        
        String ConsultaSQL = "";
        ConsultaSQL = "SELECT f.Nome, i.Descricao, i.Unidade, p.PrecoUnitario, p.Quantidade, p.DataCompra, p.Descricao "
                + "FROM fornecedor f, insumo i, pedidocompras p WHERE f.idFornecedor = p.Fornecedor_idFornecedor and"
                + " i.idInsumo = p.Insumo_idInsumo and f.Nome LIKE ? and i.Nome LIKE ? "
                + "order by f.Nome";
        ResultSet rs;
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            Query1.setString(1, "%"+NomeF+"%");
            Query1.setString(2, "%"+NomeI+"%");
            rs = Query1.executeQuery();

            javax.swing.table.DefaultTableModel dtm = (javax.swing.table.DefaultTableModel)jTablePedidos.getModel();
            int cont = dtm.getRowCount(); 
            for(int i = 0; i < cont; i++) dtm.removeRow(0);
            while(rs.next()) {
                  //clsAluno objAluno = new clsAluno();
                  //objAluno.ExisteID(rs.getInt("matricula"));
                  
                  Qtd = rs.getDouble("p.Quantidade");
                  PU = rs.getDouble("p.PrecoUnitario");
                  Total = Qtd*PU;
              
                  DataCompra = format.format(rs.getDate("p.DataCompra"));
                  
                  dtm.addRow(new Object[]{""+rs.getString("f.Nome"),rs.getString("i.Descricao"),DataCompra,rs.getString("i.Unidade"),
                  rs.getString("p.PrecoUnitario"),rs.getString("p.Quantidade"),Total,rs.getString("p.Descricao")});
            }

        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean carregaTabelaBuscaIAll(JTable jTablePedidos, String NomeI, Date DataInicio, Date DataFim) {
        Connection c = ConectaBD.getconexao();

        double PU,Qtd, Total;
        
        String ConsultaSQL = "";
        ConsultaSQL = "SELECT f.Nome, i.Descricao, i.Unidade, p.PrecoUnitario, p.Quantidade, p.DataCompra, p.Descricao "
                + "FROM fornecedor f, insumo i, pedidocompras p WHERE f.idFornecedor = p.Fornecedor_idFornecedor and"
                + " i.idInsumo = p.Insumo_idInsumo and i.Nome LIKE ? and DataCompra BETWEEN ? and ? "
                + "order by f.Nome";
        ResultSet rs;
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            Query1.setString(1, "%"+NomeI+"%");
            Query1.setDate(2, DataInicio);
            Query1.setDate(3, DataFim);
            rs = Query1.executeQuery();

            javax.swing.table.DefaultTableModel dtm = (javax.swing.table.DefaultTableModel)jTablePedidos.getModel();
            int cont = dtm.getRowCount(); 
            for(int i = 0; i < cont; i++) dtm.removeRow(0);
            while(rs.next()) {
                  //clsAluno objAluno = new clsAluno();
                  //objAluno.ExisteID(rs.getInt("matricula"));
                  
                  Qtd = rs.getDouble("p.Quantidade");
                  PU = rs.getDouble("p.PrecoUnitario");
                  Total = Qtd*PU;
                  
                  DataCompra = format.format(rs.getDate("p.DataCompra"));
                  
                  dtm.addRow(new Object[]{""+rs.getString("f.Nome"),rs.getString("i.Descricao"),DataCompra,rs.getString("i.Unidade"),
                  rs.getString("p.PrecoUnitario"),rs.getString("p.Quantidade"),Total,rs.getString("p.Descricao")});
            }

        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
    
    
}
