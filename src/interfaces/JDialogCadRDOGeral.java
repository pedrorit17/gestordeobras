/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import Utilitarios.ConectaBD;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import gestordeobras.ClsMaodeObra;
import gestordeobras.ClsPedidoCompras;
import gestordeobras.ClsRDOGeral;
import java.awt.Toolkit;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import org.eclipse.persistence.internal.oxm.schema.model.Element;

/**
 *
 * @author Gustavo
 */
public class JDialogCadRDOGeral extends javax.swing.JDialog {
    
    private static Font fonteCabecalho = new Font(Font.FontFamily.TIMES_ROMAN, 14,
			Font.BOLD);
private static Font fontePadrao = new Font(Font.FontFamily.TIMES_ROMAN, 12);
private static Font fonteNegrito = new Font(Font.FontFamily.TIMES_ROMAN,
			12, Font.BOLD);


    
    private ClsRDOGeral objRDOGeral;
    private ClsMaodeObra objMaodeObra;
    private ClsPedidoCompras objPC;
    private int CodObra;
    private int CodRDOObraA;
    private int CodCliente;
    private int CodRdogeral;
    private Date data = null;
    private String dataTexto;
    private String MaodeObra;
    private String QtdS;
    private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    private java.util.Date dataUtil = new java.util.Date();
    private int Qtd;
    private int CodMO;
    private Connection c = ConectaBD.getconexao();
    private String ConsultaSQL = "";
    private ResultSet rs;
    /**
     * Creates new form JDialogCadRDOGeral
     */
    public JDialogCadRDOGeral(javax.swing.JDialog parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setIcon();
        objRDOGeral = new ClsRDOGeral();
        objMaodeObra = new ClsMaodeObra();
        objPC = new ClsPedidoCompras();

        jComboBoxObra.setEnabled(false);
        GerarPDF.setEnabled(false);
        Alterar.setEnabled(false);
        AlterarGeral.setEnabled(false);
        Novo.setEnabled(false);
        Pesquisar.setEnabled(false);
        Excluir.setEnabled(false);
        jComboBoxMObra.setEnabled(false);
        Quantidade.setEnabled(false);

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
    }

    
    public void Alterar(String Obra, String Cliente, String Responsavel, String Data) {

        jComboBoxObra.setEnabled(true);
        GerarPDF.setEnabled(true);
        Alterar.setEnabled(true);
        AlterarGeral.setEnabled(true);
        Novo.setEnabled(true);
        Pesquisar.setEnabled(true);
        Excluir.setEnabled(true);
        jComboBoxMObra.setEnabled(true);

        Quantidade.setEnabled(true);
        Salvar.setEnabled(false);
     
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

        objRDOGeral.PesquisaClimaAtv(Obra, Responsavel, dataSql);

        jComboBoxClima.setSelectedItem(objRDOGeral.getClima());
        this.Atvdesenvolvida.setText(objRDOGeral.getAtvDesenvolvida());
        this.Ocorrencia.setText(objRDOGeral.getOcorrencia());

        dataTexto = this.Data.getText();

        format.setLenient(false);
        try {
            data = format.parse(dataTexto);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        dataUtil = data;

        java.sql.Date dataSqlT = new java.sql.Date(dataUtil.getTime());

        CodRdogeral = objRDOGeral.PesquisaidRdogeral(this.Responsavel.getText(), (String) jComboBoxObra.getSelectedItem(), dataSqlT);

        CodObra = objRDOGeral.PesquisaridObra((String) jComboBoxObra.getSelectedItem());

        objMaodeObra.setMaodeObra((String) jComboBoxMObra.getSelectedItem());

        objMaodeObra.setCodRdoobra(CodRdogeral);

        objMaodeObra.carregaTabela(jTableMaodeObra);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel5 = new javax.swing.JLabel();
        Voltar = new javax.swing.JButton();
        Data = new javax.swing.JFormattedTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jComboBoxMObra = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        Quantidade = new javax.swing.JTextField();
        Novo = new javax.swing.JButton();
        Alterar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableMaodeObra = new javax.swing.JTable();
        Pesquisar = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        Excluir = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        Atvdesenvolvida = new javax.swing.JTextPane();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        Ocorrencia = new javax.swing.JTextPane();
        Salvar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jComboBoxCliente = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jComboBoxObra = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        Responsavel = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        GerarPDF = new javax.swing.JButton();
        AlterarGeral = new javax.swing.JButton();
        jComboBoxClima = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jComboBoxClima2 = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Relatório de Obra Geral");

        jLabel5.setText("Data");

        Voltar.setText("Voltar");
        Voltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VoltarActionPerformed(evt);
            }
        });

        try {
            Data.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel6.setText("Mãos de Obra");

        jComboBoxMObra.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Administrativo", "Meio Oficial Encanador", "Encanador", "Carpinteiro", "Meio oficial de Carpintaria", "Marceneiro", "Eletricista", "Meio Oficial Eletricista", "Pedreiro", "Servente de Obras", "Serralheiro", "Pintor", "Técnico de Segurança", "Encarregado de Obra", "Engenheiro Civil", "Engenheiro Eletricista", "Arquiteto" }));
        jComboBoxMObra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxMObraActionPerformed(evt);
            }
        });

        jLabel7.setText("Quantidade");

        Quantidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                QuantidadeActionPerformed(evt);
            }
        });
        Quantidade.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                QuantidadeKeyTyped(evt);
            }
        });

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

        jTableMaodeObra.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mão de Obra", "Quantidade"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTableMaodeObra);

        Pesquisar.setText("Pesquisar");
        Pesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PesquisarActionPerformed(evt);
            }
        });

        jLabel9.setText("Pesquisar antes de excluir ou alterar");

        Excluir.setText("Excluir");
        Excluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExcluirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(Quantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(52, 52, 52)
                                .addComponent(jLabel9))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jComboBoxMObra, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Novo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Alterar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Pesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Excluir))))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jComboBoxMObra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Novo)
                    .addComponent(Alterar)
                    .addComponent(Pesquisar)
                    .addComponent(Excluir))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(Quantidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel8.setText("Atividades Desenvolvidas");

        jScrollPane2.setViewportView(Atvdesenvolvida);

        jLabel10.setText("Ocorrência");

        jScrollPane3.setViewportView(Ocorrencia);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane3)
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
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
                .addContainerGap())
        );

        Salvar.setText("Salvar");
        Salvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalvarActionPerformed(evt);
            }
        });

        jLabel1.setText("Cliente");

        jComboBoxCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxClienteActionPerformed(evt);
            }
        });

        jLabel2.setText("Responsável");

        jLabel3.setText("Obra");

        jLabel4.setText("Clima");

        GerarPDF.setText("Gerar PDF");
        GerarPDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GerarPDFActionPerformed(evt);
            }
        });

        AlterarGeral.setText("Alterar");
        AlterarGeral.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AlterarGeralActionPerformed(evt);
            }
        });

        jComboBoxClima.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chuva", "Chuva Impedindo Trabalho", "Tempo Nublado", "Tempo Bom" }));

        jLabel11.setText("Manhã");

        jComboBoxClima2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chuva", "Chuva Impedindo Trabalho", "Tempo Nublado", "Tempo Bom" }));

        jLabel12.setText("Tarde");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(Salvar)
                .addGap(27, 27, 27)
                .addComponent(GerarPDF)
                .addGap(29, 29, 29)
                .addComponent(AlterarGeral, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Voltar)
                .addGap(20, 20, 20))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
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
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(Responsavel))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jComboBoxClima, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jComboBoxClima2, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(Data, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(84, 84, 84)
                                .addComponent(jLabel4))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 91, Short.MAX_VALUE)
                                .addComponent(jLabel12)
                                .addGap(56, 56, 56)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBoxClima, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBoxClima2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(Data, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(35, 35, 35))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Voltar)
                    .addComponent(AlterarGeral)
                    .addComponent(GerarPDF)
                    .addComponent(Salvar))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(770, 604));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void VoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VoltarActionPerformed
        dispose();
    }//GEN-LAST:event_VoltarActionPerformed

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

        CodRdogeral = objRDOGeral.PesquisaidRdogeral(Responsavel.getText(), (String) jComboBoxObra.getSelectedItem(), dataSql);

        Qtd = Integer.parseInt(Quantidade.getText());

        objMaodeObra.setMaodeObra((String) jComboBoxMObra.getSelectedItem());
        objMaodeObra.setQuantidade(Qtd);
        objMaodeObra.setCodRdoobra(CodRdogeral);

        if (objMaodeObra.Salvar()) {

            objMaodeObra.carregaTabela(jTableMaodeObra);

        } else {

        }

    }//GEN-LAST:event_NovoActionPerformed

    private void AlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AlterarActionPerformed

        objMaodeObra.setCodRdoobra(CodRdogeral);
        Qtd = Integer.parseInt(Quantidade.getText());
        objMaodeObra.setQuantidade(Qtd);
        objMaodeObra.setMaodeObra((String) jComboBoxMObra.getSelectedItem());

        if (objMaodeObra.Alterar()) {
            objMaodeObra.carregaTabela(jTableMaodeObra);
        }
    }//GEN-LAST:event_AlterarActionPerformed

    private void ExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExcluirActionPerformed
        objMaodeObra.setMaodeObra((String) jComboBoxMObra.getSelectedItem());

        Qtd = Integer.parseInt(Quantidade.getText());
        objMaodeObra.setQuantidade(Qtd);

        objMaodeObra.setCodRdoobra(CodRdogeral);

        if (objMaodeObra.Excluir()) {
            objMaodeObra.carregaTabela(jTableMaodeObra);

        }
    }//GEN-LAST:event_ExcluirActionPerformed

    private void PesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PesquisarActionPerformed

        if(jTableMaodeObra.getSelectedRow() != -1){
            objMaodeObra.setCodRdoobra(CodRdogeral);

            objMaodeObra.setMaodeObra(jTableMaodeObra.getValueAt(jTableMaodeObra.getSelectedRow(), 0).toString());

            Qtd = Integer.parseInt(jTableMaodeObra.getValueAt(jTableMaodeObra.getSelectedRow(), 1).toString());

            objMaodeObra.setQuantidade(Qtd);

            if (objMaodeObra.PesquisaMaodeObra()) {
                QtdS = Integer.toString(objMaodeObra.getQuantidade());
                Quantidade.setText(QtdS);
                jComboBoxMObra.setSelectedItem(objMaodeObra.getMaodeObra());

            } else {

            }
        }else{
            JOptionPane.showMessageDialog(this, "Selecione uma linha antes!!!");
        }

    }//GEN-LAST:event_PesquisarActionPerformed

    private void SalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalvarActionPerformed
               
        if (Responsavel.getText().equals("")|| Data.getText().equals("  /  /    ")){ JOptionPane.showMessageDialog(this,"Os campos data e responsável devem ser preenchidos!!!");

        }else{
        
        
        
        CodObra = objRDOGeral.PesquisaridObra((String) jComboBoxObra.getSelectedItem());

        dataTexto = Data.getText();

        format.setLenient(false);
        try {
            data = format.parse(dataTexto);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        dataUtil = data;

        java.sql.Date dataSql = new java.sql.Date(dataUtil.getTime());

        objRDOGeral.setAtvDesenvolvida(Atvdesenvolvida.getText());
        objRDOGeral.setResponsavel(Responsavel.getText());
        objRDOGeral.setOcorrencia(Ocorrencia.getText());
        objRDOGeral.setClima((String) jComboBoxClima.getSelectedItem());
        objRDOGeral.setData(dataSql);
        objRDOGeral.setCodObra(CodObra);

        if (objRDOGeral.Salvar()) {
            jComboBoxObra.setEnabled(true);
            GerarPDF.setEnabled(true);
            Alterar.setEnabled(true);
            AlterarGeral.setEnabled(true);
            Novo.setEnabled(true);
            Pesquisar.setEnabled(true);
            Excluir.setEnabled(true);
            jComboBoxMObra.setEnabled(true);
            jComboBoxObra.setEnabled(false);
            jComboBoxCliente.setEnabled(false);
            Quantidade.setEnabled(true);
            Salvar.setEnabled(false);
         
            this.Data.setEditable(false);
            this.Responsavel.setEditable(false);

        } else {

        }
        
        }
    }//GEN-LAST:event_SalvarActionPerformed

    private void jComboBoxClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxClienteActionPerformed
        jComboBoxObra.setEnabled(true);
        jComboBoxObra.removeAllItems();

        CodCliente = objRDOGeral.PesquisaridCliente((String) jComboBoxCliente.getSelectedItem());

        Connection c = ConectaBD.getconexao();
        String ConsultaSQL = "";
        ConsultaSQL = "SELECT Nome FROM obra  WHERE Cliente_idCliente = ?";
        ResultSet rs;
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
                
                paragrafo = new Paragraph("RELATÓRIO GERAL DE OBRA ", fonteCabecalho);
                paragrafo.setAlignment(1);
                documento.add(paragrafo);
                
                paragrafo = new Paragraph("Cliente: "+(String) jComboBoxCliente.getSelectedItem(), fontePadrao);

                documento.add(paragrafo);

                paragrafo = new Paragraph("Obra: "+(String) jComboBoxObra.getSelectedItem(), fontePadrao);
                documento.add(paragrafo);

                paragrafo = new Paragraph("Responsável: "+Responsavel.getText(), fontePadrao);
                documento.add(paragrafo);

                paragrafo = new Paragraph("Clima da manhã: "+(String) jComboBoxClima.getSelectedItem(), fontePadrao);
                documento.add(paragrafo);
               
                paragrafo = new Paragraph("Clima da tarde: "+(String) jComboBoxClima2.getSelectedItem(), fontePadrao);
                documento.add(paragrafo);
                
               
                paragrafo = new Paragraph("Data: "+Data.getText(), fontePadrao);
                documento.add(paragrafo);

                paragrafo = new Paragraph(" ");
                documento.add(paragrafo);

                PdfPTable tabela = new PdfPTable(2);
                PdfPCell cabecalho = new PdfPCell(new Paragraph("Mão de Obra", fonteNegrito));
                cabecalho.setColspan(1);
                tabela.addCell(cabecalho);

                cabecalho = new PdfPCell(new Paragraph("Quantidade", fonteNegrito));
                cabecalho.setColspan(1);
                tabela.addCell(cabecalho);

                int count = jTableMaodeObra.getRowCount();
                for(int x = 0; x< count; x++){
                    //for(int y = 0; y < ; y++){
                        cabecalho = new PdfPCell(new Paragraph((String) jTableMaodeObra.getValueAt(x, 0)));
                        tabela.addCell(cabecalho);
                        cabecalho = new PdfPCell(new Paragraph(Integer.toString((int) jTableMaodeObra.getValueAt(x, 1))));
                        tabela.addCell(cabecalho);

                        //}
                }

                //jTableMaodeObra.print(JTable.PrintMode.NORMAL);

                documento.add(tabela);

                paragrafo = new Paragraph(" ");
                documento.add(paragrafo);

                paragrafo = new Paragraph("Atividades Desenvolvidas: ", fontePadrao);
                documento.add(paragrafo);

                paragrafo = new Paragraph(Atvdesenvolvida.getText());
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
                Logger.getLogger(JDialogCadRDOGeral.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                documento.close();
                JOptionPane.showMessageDialog(this, "Aquivo Salvo "+file);
            }
        } else {

        }
    }//GEN-LAST:event_GerarPDFActionPerformed

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

        CodRdogeral = objRDOGeral.PesquisaidRdogeral(Responsavel.getText(), (String) jComboBoxObra.getSelectedItem(), dataSql);

        objRDOGeral.setCodigo(CodRdogeral);
        objRDOGeral.setAtvDesenvolvida(Atvdesenvolvida.getText());
        objRDOGeral.setOcorrencia(Ocorrencia.getText());
        objRDOGeral.setClima((String) jComboBoxClima.getSelectedItem());

        if (objRDOGeral.Alterar(CodRdogeral)) {

        } else {

        }
    }//GEN-LAST:event_AlterarGeralActionPerformed

    private void QuantidadeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_QuantidadeKeyTyped
String caracteres="0987654321";// lista de caracters que não devem ser aceitos
if(!caracteres.contains(evt.getKeyChar()+"")){// se o caracter que gerou o evento estiver não estiver na lista
evt.consume();//aciona esse propriedade para eliminar a ação do evento





}


        // TODO add your handling code here:
    }//GEN-LAST:event_QuantidadeKeyTyped

    private void QuantidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_QuantidadeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_QuantidadeActionPerformed

    private void jComboBoxMObraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxMObraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxMObraActionPerformed

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
            java.util.logging.Logger.getLogger(JDialogCadRDOGeral.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialogCadRDOGeral.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialogCadRDOGeral.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialogCadRDOGeral.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDialogCadRDOGeral dialog = new JDialogCadRDOGeral(new javax.swing.JDialog(), true);
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
    private javax.swing.JTextPane Atvdesenvolvida;
    private javax.swing.JFormattedTextField Data;
    private javax.swing.JButton Excluir;
    private javax.swing.JButton GerarPDF;
    private javax.swing.JButton Novo;
    private javax.swing.JTextPane Ocorrencia;
    private javax.swing.JButton Pesquisar;
    private javax.swing.JTextField Quantidade;
    private javax.swing.JTextField Responsavel;
    private javax.swing.JButton Salvar;
    private javax.swing.JButton Voltar;
    private javax.swing.JComboBox<String> jComboBoxCliente;
    private javax.swing.JComboBox<String> jComboBoxClima;
    private javax.swing.JComboBox<String> jComboBoxClima2;
    private javax.swing.JComboBox<String> jComboBoxMObra;
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
    private javax.swing.JTable jTableMaodeObra;
    // End of variables declaration//GEN-END:variables

    private void setIcon() {
         setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("images.png")));
    }
}
