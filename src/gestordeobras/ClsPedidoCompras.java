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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.JTable;

/**
 *
 * @author Gustavo
 */
public class ClsPedidoCompras {   

    private int Codigo;
    private Double PrecoUnitario;
    private Double Quantidade;
    private Date DataCompra;
    private Date DataEntrega;
    private int CodInsumo;
    private int CodFornecedor;
    private int ConfirmaEntrega;
    private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    private String DataE;
    private String DataC;
    private String Nome;
    private String CNPJ;
    private String Telefone;
    private String Rua;
    private String Numero;
    private String Bairro;
    private String Cidade;
    private String Estado;    
    private String Descricao;    
    
    
    public boolean Salvar(){
         Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";
        

        /*(ClsEndereco.ExisteID(Cod_Fun)!=true) 
            ConsultaSQL = "UPDATE endereço SET Codigo = ?, Rua = ?, Numero = ?, Bairro = ?,"+
                    "Cidade = ?, Estado = ?";*/
        
            ConsultaSQL = "INSERT INTO pedidocompras (PrecoUnitario, Quantidade, DataCompra"
                    + ",DataEntrega,Insumo_idInsumo, Fornecedor_idFornecedor, ConfirmaEntrega, Descricao) values ( ?, ?, ?, ?, ?, ?, 1, ?)";
        
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            Query1.setDouble(1, PrecoUnitario);
            Query1.setDouble(2, Quantidade);
            Query1.setDate(3, DataCompra);
            Query1.setDate(4, DataEntrega);
            Query1.setInt(5, CodInsumo);
            Query1.setInt(6, CodFornecedor);
            Query1.setString(7, Descricao);
            
            
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
      
    public boolean Excluir() {
        
            Connection c = ConectaBD.getconexao();

            String ConsultaSQL = "DELETE from pedidocompras WHERE Fornecedor_idFornecedor = ?"
                    + " and Insumo_idInsumo = ? and Quantidade = ? and DataEntrega = ?";
            try {
                PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
                Query1.setInt(1, CodFornecedor);
                Query1.setInt(2, CodInsumo);
                Query1.setDouble(3, Quantidade);
                Query1.setDate(4, DataEntrega);                
                Query1.execute();
                //c.commit();
            } catch (java.sql.SQLException e) {
                System.out.println(e.getMessage());
                return false;
            }
        return true;
    }
    
    public boolean Alterar(int CodF, Date DataE, int CodI, Double Qtd){
         Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";

       
            ConsultaSQL = "UPDATE pedidocompras SET  ConfirmaEntrega = 2 WHERE Fornecedor_idFornecedor = ? and"
                    + " DataEntrega = ? and Insumo_idInsumo = ? and Quantidade = ?";
        
            /*ConsultaSQL = "INSERT INTO endereço (Codigo,Rua,Numero,Bairro,Cidade,"
                    + "Estado) values (?, ?, ?, ?, ?, ?)";*/
        
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            //ordens iguais 
            Query1.setInt(1, CodF);
            Query1.setDate(2, DataE);
            Query1.setInt(3, CodI);
            Query1.setDouble(4, Qtd);
            
            
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
    
    public boolean PesquisaEmpresa(){
        Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";
        ConsultaSQL = "SELECT e.Nome,e.Telefone,e.CNPJ,n.Rua,n.Numero,n.Bairro"
                + ",n.Cidade,n.Estado FROM empresa e, endereco n WHERE e.Endereco_idEndereco = n.idEndereco";
        ResultSet rs;
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            rs = Query1.executeQuery();
            if((rs.next())) {// se quisesse mais de uma colocar um while
                this.CNPJ = rs.getString("e.CNPJ");
                this.Nome = rs.getString("e.Nome");
                this.Telefone = rs.getString("e.Telefone");
                this.Rua = rs.getString("n.Rua");
                this.Numero = rs.getString("n.Numero");               
                this.Bairro = rs.getString("n.Bairro");
                this.Cidade = rs.getString("n.Cidade");
                this.Estado = rs.getString("n.Estado");
            }else return false;
        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
    
    public int PesquisaridInsumo(String Nome){
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
    
    public int PesquisaridFornecedor(String Nome){
        Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";
        ConsultaSQL = "SELECT idFornecedor FROM fornecedor WHERE Nome = ?";
        ResultSet rs;
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            Query1.setString(1, Nome);
            rs = Query1.executeQuery();
            if((rs.next())) {// se quisesse mais de uma colocar um while
                this.setCodigo(rs.getInt("idFornecedor"));
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
    
    
    
    public boolean carregaTabelaInicio(JTable jTableFornecedor) {
        Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";
        ConsultaSQL = "SELECT f.Nome, p.DataEntrega, i.Nome, p.Quantidade "
                + "FROM fornecedor f, insumo i, pedidocompras p "
                + "WHERE p.ConfirmaEntrega = 1 and f.idFornecedor = p.Fornecedor_idFornecedor "
                + "AND i.idInsumo = p.Insumo_idInsumo order by p.DataEntrega";
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
                  
                
                  
                  DataE = format.format(rs.getDate("p.DataEntrega"));
                  dtm.addRow(new Object[]{""+rs.getString("f.Nome"),DataE,rs.getString("i.Nome")
                  ,rs.getString("p.Quantidade")});
            }

        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
    
    public boolean carregaTabelaPedidoCompras(JTable jTableFornecedor) {
        Connection c = ConectaBD.getconexao();

        String ConsultaSQL = "";
        ConsultaSQL = "SELECT f.Nome, p.DataEntrega, i.Nome, p.Quantidade, p.DataCompra , p.Descricao "
                + "FROM fornecedor f, insumo i, pedidocompras p "
                + "WHERE f.idFornecedor = p.Fornecedor_idFornecedor "
                + "AND i.idInsumo = p.Insumo_idInsumo order by p.DataEntrega";
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
                  DataE = format.format(rs.getDate("p.DataEntrega"));
                  DataC = format.format(rs.getDate("p.DataCompra"));
                  dtm.addRow(new Object[]{""+rs.getString("i.Nome"),rs.getString("p.Quantidade"),rs.getString("f.Nome"),DataC
                  ,DataE,rs.getString("p.Descricao")});
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
     * @return the PrecoUnitario
     */
    public Double getPrecoUnitario() {
        return PrecoUnitario;
    }

    /**
     * @param PrecoUnitario the PrecoUnitario to set
     */
    public void setPrecoUnitario(Double PrecoUnitario) {
        this.PrecoUnitario = PrecoUnitario;
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
     * @return the DataCompra
     */
    public Date getDataCompra() {
        return DataCompra;
    }

    /**
     * @param DataCompra the DataCompra to set
     */
    public void setDataCompra(Date DataCompra) {
        this.DataCompra = DataCompra;
    }

    /**
     * @return the DataEntrega
     */
    public Date getDataEntrega() {
        return DataEntrega;
    }

    /**
     * @param DataEntrega the DataEntrega to set
     */
    public void setDataEntrega(Date DataEntrega) {
        this.DataEntrega = DataEntrega;
    }

    /**
     * @return the CodInsumo
     */
    public int getCodInsumo() {
        return CodInsumo;
    }

    /**
     * @param CodInsumo the CodInsumo to set
     */
    public void setCodInsumo(int CodInsumo) {
        this.CodInsumo = CodInsumo;
    }

    /**
     * @return the CodFornecedor
     */
    public int getCodFornecedor() {
        return CodFornecedor;
    }

    /**
     * @param CodFornecedor the CodFornecedor to set
     */
    public void setCodFornecedor(int CodFornecedor) {
        this.CodFornecedor = CodFornecedor;
    }

    /**
     * @return the ConfirmaEntrega
     */
    public int getConfirmaEntrega() {
        return ConfirmaEntrega;
    }

    /**
     * @param ConfirmaEntrega the ConfirmaEntrega to set
     */
    public void setConfirmaEntrega(int ConfirmaEntrega) {
        this.ConfirmaEntrega = ConfirmaEntrega;
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
     * @return the Rua
     */
    public String getRua() {
        return Rua;
    }

    /**
     * @param Rua the Rua to set
     */
    public void setRua(String Rua) {
        this.Rua = Rua;
    }

    /**
     * @return the Numero
     */
    public String getNumero() {
        return Numero;
    }

    /**
     * @param Numero the Numero to set
     */
    public void setNumero(String Numero) {
        this.Numero = Numero;
    }

    /**
     * @return the Bairro
     */
    public String getBairro() {
        return Bairro;
    }

    /**
     * @param Bairro the Bairro to set
     */
    public void setBairro(String Bairro) {
        this.Bairro = Bairro;
    }

    /**
     * @return the Cidade
     */
    public String getCidade() {
        return Cidade;
    }

    /**
     * @param Cidade the Cidade to set
     */
    public void setCidade(String Cidade) {
        this.Cidade = Cidade;
    }

    /**
     * @return the Estado
     */
    public String getEstado() {
        return Estado;
    }

    /**
     * @param Estado the Estado to set
     */
    public void setEstado(String Estado) {
        this.Estado = Estado;
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
    
}
