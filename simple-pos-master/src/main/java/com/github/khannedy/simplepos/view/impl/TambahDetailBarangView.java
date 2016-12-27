/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.khannedy.simplepos.view.impl;

import com.github.khannedy.simplepos.entity.master.Barang;
import com.github.khannedy.simplepos.entity.master.DetailBarang;
import com.github.khannedy.simplepos.manager.SpringManager;
import com.github.khannedy.simplepos.service.DetailBarangService;
import com.github.khannedy.simplepos.validator.ValidatorException;
import com.github.khannedy.simplepos.validator.impl.DetailBarangValidator;
import com.github.khannedy.simplepos.view.DialogView;
import com.github.khannedy.simplepos.view.FormApp;
import java.awt.Window;
import java.util.Date;
import org.springframework.dao.DataAccessException;

/**
 *
 * @author Eko Kurniawan Khannedy
 */
public class TambahDetailBarangView extends DialogView {

    private DetailBarang detailBarang;

    private Barang barang;

    /**
     * Creates new form TambahDetailBarangView
     */
    public TambahDetailBarangView(FormApp formApp) {
        super(formApp);
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldBarang = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldKode = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jFormattedTextFieldHargaBeli = new javax.swing.JFormattedTextField();
        jFormattedTextFieldHargaJual = new javax.swing.JFormattedTextField();
        jLabel6 = new javax.swing.JLabel();
        jFormattedTextFieldStok = new javax.swing.JFormattedTextField();
        jPanel2 = new javax.swing.JPanel();
        jButtonSimpan = new javax.swing.JButton();
        jButtonBatal = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(jLabel1.getFont().deriveFont(jLabel1.getFont().getStyle() | java.awt.Font.BOLD, 24));
        jLabel1.setText("Tambah Detail Barang Baru");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Data Barang"));

        jLabel2.setText("Barang :");

        jTextFieldBarang.setEnabled(false);

        jLabel3.setText("Kode :");

        jLabel4.setText("Harga Beli :");

        jLabel5.setText("Harga Jual");

        jFormattedTextFieldHargaBeli.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        jFormattedTextFieldHargaBeli.setText("0");

        jFormattedTextFieldHargaJual.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        jFormattedTextFieldHargaJual.setText("0");

        jLabel6.setText("Stok :");

        jFormattedTextFieldStok.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        jFormattedTextFieldStok.setText("0");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldBarang)
                    .addComponent(jTextFieldKode)
                    .addComponent(jFormattedTextFieldHargaBeli)
                    .addComponent(jFormattedTextFieldHargaJual)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jFormattedTextFieldStok))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldKode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jFormattedTextFieldHargaBeli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jFormattedTextFieldHargaJual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jFormattedTextFieldStok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButtonSimpan.setText("Simpan");
        jButtonSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSimpanActionPerformed(evt);
            }
        });
        jPanel2.add(jButtonSimpan);

        jButtonBatal.setText("Batal");
        jButtonBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBatalActionPerformed(evt);
            }
        });
        jPanel2.add(jButtonBatal);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 119, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-510)/2, (screenSize.height-437)/2, 510, 437);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBatalActionPerformed
        dispose();
    }//GEN-LAST:event_jButtonBatalActionPerformed

    private void jButtonSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSimpanActionPerformed
        detailBarang.setId(jTextFieldKode.getText());
        detailBarang.setTerakhirDirubah(new Date());
        detailBarang.setWaktuDibuat(new Date());
        detailBarang.setHargaBeli(Long.valueOf(jFormattedTextFieldHargaBeli.getValue().toString()));
        detailBarang.setHargaJual(Long.valueOf(jFormattedTextFieldHargaJual.getValue().toString()));
        detailBarang.setStok(Integer.valueOf(jFormattedTextFieldStok.getValue().toString()));
        detailBarang.setBarang(barang);

        DetailBarangValidator validator = SpringManager.getInstance().getBean(DetailBarangValidator.class);
        DetailBarangService service = SpringManager.getInstance().getBean(DetailBarangService.class);

        try {
            validator.validate(detailBarang);
            service.save(detailBarang);
            dispose();
        } catch (ValidatorException ex) {
            showWarning(ex.getMessage());
        } catch (DataAccessException ex) {
            showError(ex.getRootCause().getMessage());
        }
    }//GEN-LAST:event_jButtonSimpanActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonBatal;
    private javax.swing.JButton jButtonSimpan;
    private javax.swing.JFormattedTextField jFormattedTextFieldHargaBeli;
    private javax.swing.JFormattedTextField jFormattedTextFieldHargaJual;
    private javax.swing.JFormattedTextField jFormattedTextFieldStok;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField jTextFieldBarang;
    private javax.swing.JTextField jTextFieldKode;
    // End of variables declaration//GEN-END:variables

    @Override
    public void display(Window formApp, Object parameter) {
        barang = (Barang) parameter;
        detailBarang = new DetailBarang();

        jTextFieldBarang.setText(barang.getNama());

        setLocationRelativeTo(formApp);
        setVisible(true);
    }
}
