/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import Utilitarios.ConectaBD;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import gestordeobras.ClsPedidoCompras;
import gestordeobras.ClsRDOFuncionario;
import gestordeobras.ClsRDOGrupo;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Gustavo
 */
public class JDialogCadRDOGrupo extends javax.swing.JDialog {

private static Font fonteCabecalho = new Font(Font.FontFamily.TIMES_ROMAN, 14,
			Font.BOLD);
private static Font fontePadrao = new Font(Font.FontFamily.TIMES_ROMAN, 12);
private static Font fonteNegrito = new Font(Font.FontFamily.TIMES_ROMAN,
			12, Font.BOLD);

    
    
    
    private ClsRDOGrupo objRDOGrupo;
    private ClsRDOFuncionario objRDOFuncionario;
    private ClsPedidoCompras objPC;

    private int CodObra;
    private int CodRDOObraA;
    private int CodCliente;
    private int CodRdogeral;
    private Date data = null;
    private String dataTexto;
    private String MaodeObra;
    private String QtdS;
    private int CodFuncionario;
    private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    private java.util.Date dataUtil = new java.util.Date();
    private int Qtd;
    private int CodMO;
    private Connection c = ConectaBD.getconexao();
    private String ConsultaSQL = "";
    private ResultSet rs;
    /**
     * Creates new form JDialogCadRDOGrupo
     */
    public JDialogCadRDOGrupo(javax.swing.JDialog parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setIcon();
        
        objRDOGrupo = new ClsRDOGrupo();
        objRDOFuncionario = new ClsRDOFuncionario();
        objPC = new ClsPedidoCompras();
        
        jComboBoxObra.setEnabled(false);
        GerarPDF.setEnabled(false);
        Alterar.setEnabled(false);
        AlterarGeral.setEnabled(false);
        Novo.setEnabled(false);
        Pesquisar.setEnabled(false);
        Excluir.setEnabled(false);
        jComboBoxFuncionario.setEnabled(false);
        Descricao.setEnabled(false);

        ConsultaSQL = "SELECT Nome FROM cliente";

        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            rs = Query1.executeQuery();

            while (rs.next()) {// se quisesse mais de uma colocar um while

                jComboBoxCliente.addItem(rs.getString("Nome"));

            }
        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
        }
        
        ConsultaSQL = "SELECT Nome FROM funcionario";

        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            rs = Query1.executeQuery();

            while (rs.next()) {// se quisesse mais de uma colocar um while

                jComboBoxFuncionario.addItem(rs.getString("Nome"));

            }
        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
        }
        
    }

    
    public void Alterar(String Obra, String Cliente, String Responsavel, String Data){
        jComboBoxObra.setEnabled(true);
        GerarPDF.setEnabled(true);
        Alterar.setEnabled(true);
        AlterarGeral.setEnabled(true);
        Novo.setEnabled(true);
        Pesquisar.setEnabled(true);
        Excluir.setEnabled(true);
        jComboBoxFuncionario.setEnabled(true);

        Descricao.setEnabled(true);
        Salvar.setEnabled(false);
//        SalvarVoltar.setEnabled(false);
        this.Data.setEditable(false);
        this.Responsavel.setEditable(false);

        //jComboBoxCliente.removeAllItems();
        //jComboBoxObra.removeAllItems();
        jComboBoxCliente.setSelectedItem(Cliente);
        jComboBoxObra.setSelectedItem(Obra);

        jComboBoxObra.setEnabled(false);
        jComboBoxCliente.setEnabled(false);

        this.Responsavel.setText(Responsavel);
        this.Data.setText(Data);

        dataTexto = Data;

        format.setLenient(false);
        try {
            data = format.parse(dataTexto);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        dataUtil = data;

        java.sql.Date dataSql = new java.sql.Date(dataUtil.getTime());

        objRDOGrupo.PesquisaClimaAtv(Obra, Responsavel, dataSql);

        jComboBoxClima.setSelectedItem(objRDOGrupo.getClima());
        this.AtvDesenvolvida.setText(objRDOGrupo.getAtvDesenvolvida());
        this.Ocorrencia.setText(objRDOGrupo.getOcorrencia());

        dataTexto = this.Data.getText();

        format.setLenient(false);
        try {
            data = format.parse(dataTexto);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        dataUtil = data;

        java.sql.Date dataSqlT = new java.sql.Date(dataUtil.getTime());

        CodRdogeral = objRDOGrupo.PesquisaidRdogeral(this.Responsavel.getText(), (String) jComboBoxObra.getSelectedItem(), dataSqlT);

        CodObra = objRDOGrupo.PesquisaridObra((String) jComboBoxObra.getSelectedItem());
        
        objRDOFuncionario.carregaTabela(jTableAtv, CodRdogeral);
        
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        AlterarGeral = new javax.swing.JButton();
        Responsavel = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jComboBoxClima = new javax.swing.JComboBox<>();
        Voltar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        Data = new javax.swing.JFormattedTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        AtvDesenvolvida = new javax.swing.JTextPane();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        Ocorrencia = new javax.swing.JTextPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jComboBoxFuncionario = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        Novo = new javax.swing.JButton();
        Alterar = new javax.swing.JButton();
        Excluir = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableAtv = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        Descricao = new javax.swing.JTextPane();
        Pesquisar = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jComboBoxCliente = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        Salvar = new javax.swing.JButton();
        jComboBoxObra = new javax.swing.JComboBox<>();
        GerarPDF = new javax.swing.JButton();
        jComboBoxClima1 = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Relatório de Grupo de Obra");

        jLabel3.setText("Obra");

        AlterarGeral.setText("Alterar");
        AlterarGeral.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AlterarGeralActionPerformed(evt);
            }
        });

        jLabel4.setText("Clima");

        jComboBoxClima.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chuva", "Chuva Impedindo Trabalho", "Tempo Nublado", "Tempo Bom" }));

        Voltar.setText("Voltar");
        Voltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VoltarActionPerformed(evt);
            }
        });

        jLabel5.setText("Data");

        try {
            Data.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel8.setText("Atividades Desenvolvidas");

        jScrollPane2.setViewportView(AtvDesenvolvida);

        jLabel10.setText("Ocorrências");

        jScrollPane4.setViewportView(Ocorrencia);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane4)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel10))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE))
        );

        jLabel6.setText("Funcionário");

        jComboBoxFuncionario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxFuncionarioActionPerformed(evt);
            }
        });

        jLabel7.setText("Descrição");

        Novo.setText("Novo");
        Novo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NovoActionPerformed(evt);
            }
        });

        Alterar.setText("Alterar");
        Alterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AlterarActionPerformed(evt);
            }
        });

        Excluir.setText("Excluir");
        Excluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExcluirActionPerformed(evt);
            }
        });

        jTableAtv.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Funcionário", "Descrição"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTableAtv);

        jScrollPane3.setViewportView(Descricao);

        Pesquisar.setText("Pesquisar");
        Pesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PesquisarActionPerformed(evt);
            }
        });

        jLabel9.setText("Favor pesquisar antes de excluir ou alterar");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jComboBoxFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(Novo)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(Alterar)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(Pesquisar)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                                        .addComponent(Excluir))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(jLabel9)
                                        .addGap(49, 49, 49)))))
                        .addContainerGap(11, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 443, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jComboBoxFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Novo)
                    .addComponent(Alterar)
                    .addComponent(Excluir)
                    .addComponent(Pesquisar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel1.setText("Cliente");

        jComboBoxCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxClienteActionPerformed(evt);
            }
        });

        jLabel2.setText("Responsável");

        Salvar.setText("Salvar");
        Salvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalvarActionPerformed(evt);
            }
        });

        GerarPDF.setText("Gerar PDF");
        GerarPDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GerarPDFActionPerformed(evt);
            }
        });

        jComboBoxClima1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chuva", "Chuva Impedindo Trabalho", "Tempo Nublado", "Tempo Bom" }));

        jLabel11.setText("Manhã");

        jLabel12.setText("Tarde");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel1)
                                                .addComponent(jLabel3))
                                            .addGap(37, 37, 37)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jComboBoxObra, 0, 155, Short.MAX_VALUE)
                                                .addComponent(jComboBoxCliente, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel5)
                                            .addGap(26, 26, 26)
                                            .addComponent(Data, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(jLabel11)
                                                .addComponent(jLabel2))
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                    .addComponent(Responsavel))
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(jLabel12)
                                                    .addGap(54, 54, 54)))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(91, 91, 91)
                                        .addComponent(jLabel4))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jComboBoxClima1, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jComboBoxClima, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(Salvar)
                        .addGap(39, 39, 39)
                        .addComponent(GerarPDF)
                        .addGap(48, 48, 48)
                        .addComponent(AlterarGeral, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Voltar)
                        .addGap(20, 20, 20))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jComboBoxCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBoxObra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(19, 19, 19)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(Responsavel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(jLabel12))
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBoxClima, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBoxClima1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(Data, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(61, 61, 61)))
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Voltar)
                    .addComponent(AlterarGeral)
                    .addComponent(GerarPDF)
                    .addComponent(Salvar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(779, 604));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void VoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VoltarActionPerformed
        dispose();
    }//GEN-LAST:event_VoltarActionPerformed

    private void jComboBoxClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxClienteActionPerformed
        jComboBoxObra.setEnabled(true);
        jComboBoxObra.removeAllItems();

        CodCliente = objRDOGrupo.PesquisaridCliente((String) jComboBoxCliente.getSelectedItem());

        Connection c = ConectaBD.getconexao();
        String ConsultaSQL = "";
        ConsultaSQL = "SELECT Nome FROM obra  WHERE Cliente_idCliente = ?";
        ResultSet rs;
        ArrayList<String> list = new ArrayList<String>();
        try {
            PreparedStatement Query1 = c.prepareStatement(ConsultaSQL);
            Query1.setInt(1, CodCliente);
            rs = Query1.executeQuery();

            while (rs.next()) {// se quisesse mais de uma colocar um while

                jComboBoxObra.addItem(rs.getString("Nome"));

            }
        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_jComboBoxClienteActionPerformed

    private void SalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalvarActionPerformed

        
        if (Responsavel.getText().equals("")|| Data.getText().equals("  /  /    ")){ JOptionPane.showMessageDialog(this,"Os campos data e responsável devem ser preenchidos!!!");

        }else{



        CodObra = objRDOGrupo.PesquisaridObra((String) jComboBoxObra.getSelectedItem());

        dataTexto = Data.getText();

        format.setLenient(false);
        try {
            data = format.parse(dataTexto);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        dataUtil = data;

        java.sql.Date dataSql = new java.sql.Date(dataUtil.getTime());

        objRDOGrupo.setAtvDesenvolvida(AtvDesenvolvida.getText());
        objRDOGrupo.setResponsavel(Responsavel.getText());
        objRDOGrupo.setClima((String) jComboBoxClima.getSelectedItem());
        objRDOGrupo.setData(dataSql);
        objRDOGrupo.setCodObra(CodObra);
        objRDOGrupo.setOcorrencia(Ocorrencia.getText());

        if (objRDOGrupo.Salvar()) {
            jComboBoxObra.setEnabled(true);
            GerarPDF.setEnabled(true);
            Alterar.setEnabled(true);
            AlterarGeral.setEnabled(true);
            Novo.setEnabled(true);
            Pesquisar.setEnabled(true);
            Excluir.setEnabled(true);
            jComboBoxFuncionario.setEnabled(true);
            jComboBoxObra.setEnabled(false);
            jComboBoxCliente.setEnabled(false);
            Descricao.setEnabled(true);
            Salvar.setEnabled(false);
//            SalvarVoltar.setEnabled(false);
            this.Data.setEditable(false);
            this.Responsavel.setEditable(false);

        } else {
            
        }
        }
    }//GEN-LAST:event_SalvarActionPerformed

    private void AlterarGeralActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AlterarGeralActionPerformed
        dataTexto = Data.getText();

        format.setLenient(false);
        try {
            data = format.parse(dataTexto);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        dataUtil = data;

        java.sql.Date dataSql = new java.sql.Date(dataUtil.getTime());

        CodRdogeral = objRDOGrupo.PesquisaidRdogeral(Responsavel.getText(), (String) jComboBoxObra.getSelectedItem(), dataSql);

        objRDOGrupo.setCodigo(CodRdogeral);
        objRDOGrupo.setAtvDesenvolvida(AtvDesenvolvida.getText());
        objRDOGrupo.setClima((String) jComboBoxClima.getSelectedItem());
        objRDOGrupo.setOcorrencia(Ocorrencia.getText());

        if (objRDOGrupo.Alterar(CodRdogeral)) {

        } else {

        }
    }//GEN-LAST:event_AlterarGeralActionPerformed

    private void GerarPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GerarPDFActionPerformed
        MessageFormat header = new MessageFormat("Relatorio de Obra");
        MessageFormat footer = new MessageFormat("Pagina{0,number,integer}");
        
        Document documento = new Document();

        objPC.PesquisaEmpresa();
        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setSelectedFile(new File("arquivo.pdf"));
        fileChooser.setDialogTitle("Salvar Relatorio de Obra");
        int status = fileChooser.showOpenDialog(null);
        if (status == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {

                PdfWriter.getInstance(documento, new FileOutputStream(file));

                documento.open();
                
                   Image imagem=Image.getInstance(getClass().getResource("ufmg.jpg"));
               imagem.scaleToFit(500,400);
               documento.add(imagem);

               Paragraph  paragrafo = new Paragraph(" ");
                documento.add(paragrafo);
                
                paragrafo = new Paragraph("RELATÓRIO  DE OBRA POR GRUPO ", fonteCabecalho);
                paragrafo.setAlignment(1);
                documento.add(paragrafo);
                
                
               
                paragrafo = new Paragraph("Cliente: "+(String) jComboBoxCliente.getSelectedItem(), fontePadrao);

                documento.add(paragrafo);

                paragrafo = new Paragraph("Obra: "+(String) jComboBoxObra.getSelectedItem(), fontePadrao);
                documento.add(paragrafo);

                paragrafo = new Paragraph("Responsável: "+Responsavel.getText(), fontePadrao);
                documento.add(paragrafo);

                paragrafo = new Paragraph("Clima da manhã: "+(String) jComboBoxClima1.getSelectedItem(), fontePadrao);
                documento.add(paragrafo);
                
                 paragrafo = new Paragraph("Clima da tarde: "+(String) jComboBoxClima.getSelectedItem(), fontePadrao);
                documento.add(paragrafo);

                paragrafo = new Paragraph("Data: "+Data.getText(),fontePadrao);
                documento.add(paragrafo);

                paragrafo = new Paragraph(" ");
                documento.add(paragrafo);

                PdfPTable tabela = new PdfPTable(2);
                PdfPCell cabecalho = new PdfPCell(new Paragraph("Funcionário", fonteNegrito));
                cabecalho.setColspan(1);
                tabela.addCell(cabecalho);
                
                cabecalho = new PdfPCell(new Paragraph("Atividade", fonteNegrito));
                cabecalho.setColspan(1);
                tabela.addCell(cabecalho);
                
                
                
                int count = jTableAtv.getRowCount();
                for(int x = 0; x< count; x++){
                    //for(int y = 0; y < ; y++){
                        cabecalho = new PdfPCell(new Paragraph((String) jTableAtv.getValueAt(x, 0)));
                        tabela.addCell(cabecalho);
                        cabecalho = new PdfPCell(new Paragraph((String) jTableAtv.getValueAt(x, 1)));
                        tabela.addCell(cabecalho);
                        
                    //}
                }
                
                //jTableMaodeObra.print(JTable.PrintMode.NORMAL);

                documento.add(tabela);
                
                paragrafo = new Paragraph(" ");
                documento.add(paragrafo);
                
                paragrafo = new Paragraph("Atividades Desenvolvidas: ", fontePadrao);
                documento.add(paragrafo);
                
                paragrafo = new Paragraph(AtvDesenvolvida.getText());
                documento.add(paragrafo);
                
                paragrafo = new Paragraph(" ");
                documento.add(paragrafo);
                
                paragrafo = new Paragraph("Ocorrências: ", fontePadrao);
                documento.add(paragrafo);

                paragrafo = new Paragraph(Ocorrencia.getText());
                documento.add(paragrafo);
                
            } catch (FileNotFoundException ex) {
                
            } catch (DocumentException ex) {
                
            } catch (IOException ex) {
                Logger.getLogger(JDialogCadRDOGrupo.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                documento.close();
                JOptionPane.showMessageDialog(this, "Aquivo Salvo "+file);
            }
        } else {

        }
    }//GEN-LAST:event_GerarPDFActionPerformed

    private void NovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NovoActionPerformed
        dataTexto = Data.getText();

        format.setLenient(false);
        try {
            data = format.parse(dataTexto);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        dataUtil = data;

        java.sql.Date dataSql = new java.sql.Date(dataUtil.getTime());

        CodRdogeral = objRDOGrupo.PesquisaidRdogeral(Responsavel.getText(), (String) jComboBoxObra.getSelectedItem(), dataSql);
        
        objRDOFuncionario.setNomeFuncionario((String) jComboBoxFuncionario.getSelectedItem());
        
        CodFuncionario = objRDOFuncionario.PesquisaidFuncionario();
        
        objRDOFuncionario.setDescricao(Descricao.getText());
        
        
        if(objRDOFuncionario.Salvar(CodRdogeral, CodFuncionario, Descricao.getText())){
            objRDOFuncionario.carregaTabela(jTableAtv, CodRdogeral);
        }else{
            
        }
        
    }//GEN-LAST:event_NovoActionPerformed

    private void PesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PesquisarActionPerformed
        if(jTableAtv.getSelectedRow() != -1){
            
            objRDOFuncionario.setCodRDOObra(CodRdogeral);

            objRDOFuncionario.setNomeFuncionario(jTableAtv.getValueAt(jTableAtv.getSelectedRow(), 0).toString());

            CodFuncionario = objRDOFuncionario.PesquisaidFuncionario();

            objRDOFuncionario.setCodFuncionario(CodFuncionario);

        
            if(objRDOFuncionario.PesquisaAtvFuncionario()){
                jComboBoxFuncionario.setSelectedItem(objRDOFuncionario.getNomeFuncionario());
                Descricao.setText(objRDOFuncionario.getDescricao());
            }
        }else{
            JOptionPane.showMessageDialog(this, "Selecione uma linha antes!!!");
        }
        
    }//GEN-LAST:event_PesquisarActionPerformed

    private void AlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AlterarActionPerformed
        
        objRDOFuncionario.setCodRDOObra(CodRdogeral);

        objRDOFuncionario.setCodFuncionario(CodFuncionario);
        objRDOFuncionario.setDescricao(Descricao.getText());
        
        if(objRDOFuncionario.Alterar()){
            objRDOFuncionario.carregaTabela(jTableAtv, CodRdogeral);
        }
        
    }//GEN-LAST:event_AlterarActionPerformed

    private void ExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExcluirActionPerformed
        
        objRDOFuncionario.setCodRDOObra(CodRdogeral);

        objRDOFuncionario.setCodFuncionario(CodFuncionario);
        
        if(objRDOFuncionario.Excluir()){
            objRDOFuncionario.carregaTabela(jTableAtv, CodRdogeral);
        }
        
    }//GEN-LAST:event_ExcluirActionPerformed

    private void jComboBoxFuncionarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxFuncionarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxFuncionarioActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JDialogCadRDOGrupo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialogCadRDOGrupo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialogCadRDOGrupo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialogCadRDOGrupo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDialogCadRDOGrupo dialog = new JDialogCadRDOGrupo(new javax.swing.JDialog(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Alterar;
    private javax.swing.JButton AlterarGeral;
    private javax.swing.JTextPane AtvDesenvolvida;
    private javax.swing.JFormattedTextField Data;
    private javax.swing.JTextPane Descricao;
    private javax.swing.JButton Excluir;
    private javax.swing.JButton GerarPDF;
    private javax.swing.JButton Novo;
    private javax.swing.JTextPane Ocorrencia;
    private javax.swing.JButton Pesquisar;
    private javax.swing.JTextField Responsavel;
    private javax.swing.JButton Salvar;
    private javax.swing.JButton Voltar;
    private javax.swing.JComboBox<String> jComboBoxCliente;
    private javax.swing.JComboBox<String> jComboBoxClima;
    private javax.swing.JComboBox<String> jComboBoxClima1;
    private javax.swing.JComboBox<String> jComboBoxFuncionario;
    private javax.swing.JComboBox<String> jComboBoxObra;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTableAtv;
    // End of variables declaration//GEN-END:variables

    private void setIcon() {
         setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("images.png")));
    }
}
