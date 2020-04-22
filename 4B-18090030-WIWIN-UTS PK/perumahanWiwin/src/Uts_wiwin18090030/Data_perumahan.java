/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Uts_wiwin18090030;
import java.awt.HeadlessException;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.io.FileInputStream;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
/**
 *
 * @author ASUS
 */
public class Data_perumahan extends javax.swing.JFrame {
    int idBaris=0;
    String role;
    DefaultTableModel model;
    public Data_perumahan() {
        initComponents();
        
        //koneksi.sambungDB();
        aturModelTabel();
        TipePerumahan();
        showForm(false);
        showData(" ");
    }
    private void aturModelTabel(){
        Object[] kolom = {"No","Nama Perumahan","Alamat","No telepon","Email","Pengembang","Tipe perumahan"};
        model = new DefaultTableModel(kolom,0) {
        boolean[] canEdit = new boolean [] {
        false, false, false, false, false, false, false
        };
        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return canEdit [columnIndex];
        }
    };
    tabelperum.setModel(model);
    tabelperum.setRowHeight(20);
    tabelperum.getColumnModel().getColumn(0).setMinWidth(0);
    tabelperum.getColumnModel().getColumn(0).setMaxWidth(0);
    }
    private void showForm(boolean b){
        areaSplit.setDividerLocation(0.3);
        areaSplit.getLeftComponent().setVisible(b);
    }
    private void resetForm(){
        tabelperum.clearSelection();
        namaperum.setText("");
        alamat.setText("");
        notelepon.setText("");
        email.setText("");
        pengembang.setText("");
        tipePerum.setSelectedIndex(0);
        namaperum.requestFocus();
    }
    private void TipePerumahan(){
        tipePerum.removeAllItems();
        tipePerum.addItem("Pilih Tipe Perumahan");
        tipePerum.addItem("Tipe 21");
        tipePerum.addItem("Tipe 36");
        tipePerum.addItem("Tipe 45");
        tipePerum.addItem("Tipe 54");
        tipePerum.addItem("Tipe 60");
        tipePerum.addItem("Tipe 70");
        tipePerum.addItem("Tipe 120");
    }
    private void showData(String key){
        model.getDataVector().removeAllElements();
        String where = "";
        if (!key.isEmpty()) {
            where += "WHERE nama_perumahan LIKE '%" + key + "%' "
                    + "OR alamat LIKE '%" + key + "%' "
                    + "OR no_telepon LIKE '%" + key + "%' "
                    + "OR email LIKE '%" + key + "%' "
                    + "OR pengembang LIKE '%" + key + "%'"
                    + "OR tipe_perumahan LIKE '%" + key + "%'";
        }
        String sql = "SELECT * FROM data_rumah " + where;
        Connection con;
        Statement st;
        ResultSet rs;
        int baris = 0;
        try {
            con = koneksi.sambungDB();
            st = con.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                Object id = rs.getInt(1);
                Object nama_perumahan = rs.getString(2);
                Object alamat = rs.getString(3);
                Object no_telepon = rs.getString(4);
                Object email = rs.getString(5);
                Object pengembang = rs.getString(6);
                Object tipe_perumahan = rs.getString(7);

                Object[] data = {id, nama_perumahan, alamat, no_telepon, email, pengembang, tipe_perumahan };
                model.insertRow(baris, data);
                baris++;
            }
            st.close();
            con.close();
            tabelperum.revalidate();
            tabelperum.repaint();
        } catch (SQLException e) {
            System.err.println("showData(): " + e.getMessage());
        }
    }
    private void resetView(){
        resetForm();
        showForm(false);
        showData("");
        hapus.setEnabled(false);
        idBaris = 0;
    }
    private void pilihData(String n){
        hapus.setEnabled(true);

        String sql = "SELECT * FROM data_rumah WHERE id='" + n + "'";
        Connection con;
        Statement st;
        ResultSet rs;
        try {
            con = koneksi.sambungDB();
            st = con.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt(1);
                String nama_perumahan = rs.getString(2);
                String Alamat = rs.getString(3);
                String no_telepon = rs.getString(4);
                String Email = rs.getString(5);
                String Pengembang = rs.getString(6);
                Object tipe_perumahan = rs.getString(7);
                
                idBaris = id;
                namaperum.setText(nama_perumahan);
                alamat.setText(Alamat);
                notelepon.setText(no_telepon);
                email.setText(Email);
                pengembang.setText(Pengembang);
                tipePerum.setSelectedItem(tipe_perumahan);
            }
            st.close();
            con.close();
        } catch (SQLException e) {
            System.err.println("pilihData(): " + e.getMessage());
        }
    }
    private void simpanData(){
        String nama_perum = namaperum.getText();
        String Alamat = alamat.getText();
        String No_telepon = notelepon.getText();
        String Email = email.getText();
        String Pengembang = pengembang.getText();
        int Tipe_perumahan = tipePerum.getSelectedIndex();

        if (nama_perum.isEmpty() || Alamat.isEmpty() || No_telepon.isEmpty() || Email.isEmpty() || Pengembang.isEmpty() || Tipe_perumahan == 0 ) {
            JOptionPane.showMessageDialog(this, "Mohon lengkapi data!");
        } else {
            String  tipe_perum_isi = tipePerum.getSelectedItem().toString();

            String sql
                    = "INSERT INTO `data_rumah`(`nama_perumahan`, `alamat`, `no_telepon`, `email`, `pengembang`, `tipe_perumahan`)"
                    + "VALUES ('"+nama_perum+"','"+Alamat+"','"+No_telepon+"','"+Email+"','"+Pengembang+"','"+tipe_perum_isi+"')";

            Connection con;
            Statement st;
            try {
                con = koneksi.sambungDB();
                st = con.createStatement();
                st.executeUpdate(sql);
                st.close();
                con.close();

                resetView();
                JOptionPane.showMessageDialog(this,"Data telah disimpan!");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
        }
    }
    private void ubahData(){
        String nama_perumahan = namaperum.getText();
        String Alamat = alamat.getText();
        String no_telepon = notelepon.getText();
        String Email = email.getText();
        String Pengembang = pengembang.getText();
        int tipe_perumahan = tipePerum.getSelectedIndex();
        if (nama_perumahan.isEmpty() || Alamat.isEmpty() || no_telepon.isEmpty() || Email.isEmpty() || Pengembang.isEmpty()
            || tipe_perumahan == 0) {
            JOptionPane.showMessageDialog(this, "Mohon lengkapi data!");
        } else {
            String tipe_perum_isi = tipePerum.getSelectedItem().toString();
            String sql = "UPDATE data_rumah "
                    + "SET nama_perumahan=\"" + nama_perumahan + "\","
                    + "alamat=\"" + Alamat + "\","
                    + "no_telepon=\"" + no_telepon + "\","
                    + "email=\"" + Email + "\","
                    + "pengembang=\"" + Pengembang + "\","
                    + "tipe_perumahan=\"" + tipe_perum_isi + "\","
                    + "\" WHERE id=\"" + idBaris + "\"";
            Connection con;
            Statement st;
            try {
                con = koneksi.sambungDB();
                st = con.createStatement();
                st.executeUpdate(sql);
                st.close();
                con.close();

                resetView();
                JOptionPane.showMessageDialog(this, "Data telah diubah!");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
        }
    }
    private void hapusData(int baris){
        Connection con;
        Statement st;
        try {
            con = koneksi.sambungDB();
            st = con.createStatement();
            st.executeUpdate("DELETE FROM data_rumah WHERE id=" + baris);
            st.close();
            con.close();

            resetView();
            JOptionPane.showMessageDialog(this, "Data telah dihapus");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        tambah = new javax.swing.JButton();
        hapus = new javax.swing.JButton();
        txtcari = new javax.swing.JTextField();
        btncari = new javax.swing.JButton();
        areaSplit = new javax.swing.JSplitPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        namaperum = new javax.swing.JTextField();
        alamat = new javax.swing.JTextField();
        notelepon = new javax.swing.JTextField();
        email = new javax.swing.JTextField();
        pengembang = new javax.swing.JTextField();
        tipePerum = new javax.swing.JComboBox();
        logout = new javax.swing.JButton();
        simpan = new javax.swing.JButton();
        btnupload = new javax.swing.JButton();
        brosur = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelperum = new javax.swing.JTable();
        jDesktopPane2 = new javax.swing.JDesktopPane();
        unggahfoto = new javax.swing.JLabel();
        btnedit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(0, 102, 153));

        tambah.setText("Tambah data");
        tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tambahActionPerformed(evt);
            }
        });

        hapus.setText("Hapus data");
        hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hapusActionPerformed(evt);
            }
        });

        txtcari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcariActionPerformed(evt);
            }
        });
        txtcari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtcariKeyReleased(evt);
            }
        });

        btncari.setText("Cari");
        btncari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncariActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("Alamat");

        jLabel2.setText("Nama Perumahan");

        jLabel3.setText("No telepon");

        jLabel4.setText("Email");

        jLabel5.setText("Pengembang");

        jLabel6.setText("Tipe Perumahan");

        alamat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alamatActionPerformed(evt);
            }
        });

        tipePerum.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        logout.setText("Tutup");
        logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutActionPerformed(evt);
            }
        });

        simpan.setText("Simpan");
        simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simpanActionPerformed(evt);
            }
        });

        btnupload.setText("Upload");
        btnupload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnuploadActionPerformed(evt);
            }
        });

        brosur.setText("Brosur");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pengembang)
                            .addComponent(email)
                            .addComponent(notelepon)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(alamat, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(namaperum)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tipePerum, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(brosur)
                        .addGap(10, 10, 10)
                        .addComponent(btnupload))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(logout)
                        .addGap(18, 18, 18)
                        .addComponent(simpan)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(namaperum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(alamat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(notelepon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(pengembang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(tipePerum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnupload)
                    .addComponent(brosur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(simpan)
                    .addComponent(logout))
                .addContainerGap(133, Short.MAX_VALUE))
        );

        areaSplit.setLeftComponent(jPanel2);

        tabelperum.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabelperum.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelperumMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelperum);

        areaSplit.setRightComponent(jScrollPane1);

        jDesktopPane2.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jDesktopPane2Layout = new javax.swing.GroupLayout(jDesktopPane2);
        jDesktopPane2.setLayout(jDesktopPane2Layout);
        jDesktopPane2Layout.setHorizontalGroup(
            jDesktopPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(unggahfoto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
        );
        jDesktopPane2Layout.setVerticalGroup(
            jDesktopPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane2Layout.createSequentialGroup()
                .addComponent(unggahfoto, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jDesktopPane2.setLayer(unggahfoto, javax.swing.JLayeredPane.DEFAULT_LAYER);

        btnedit.setText("Edit");
        btnedit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneditActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(tambah)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(hapus)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnedit, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btncari)
                        .addGap(18, 18, 18)
                        .addComponent(txtcari, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(areaSplit, javax.swing.GroupLayout.PREFERRED_SIZE, 706, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jDesktopPane2)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tambah)
                            .addComponent(hapus)
                            .addComponent(btnedit)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtcari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btncari))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(areaSplit)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jDesktopPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tambahActionPerformed
        role = "Tambah";
        simpan.setText("Simpan");
        idBaris = 0;
        resetForm();
        showForm(true);
        hapus.setEnabled(false);
    }//GEN-LAST:event_tambahActionPerformed

    private void hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hapusActionPerformed
        if (idBaris == 0) {
            JOptionPane.showMessageDialog(this, "Pilih data yang akan"
            + "dihapus");
        }else{
            hapusData(idBaris);
        }
    }//GEN-LAST:event_hapusActionPerformed

    private void simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpanActionPerformed
       if (role.equals("Tambah")) {
            simpanData();
        }else if (role.equals("Ubah")) {
            ubahData();
        }
    }//GEN-LAST:event_simpanActionPerformed

    private void logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutActionPerformed
        resetForm();
        showForm(false);
        hapus.setEnabled(false);
        idBaris =0;
    }//GEN-LAST:event_logoutActionPerformed

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        areaSplit.setDividerLocation(0.3);
    }//GEN-LAST:event_formComponentResized

    private void tabelperumMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelperumMouseClicked
        role = "Ubah";
        int row = tabelperum.getRowCount();
        if (row > 0) {
            int sel = tabelperum.getSelectedRow();
            if (sel != -1) {
                pilihData(tabelperum.getValueAt(sel, 0).toString());
                simpan.setText("UBAH DATA");
            }
        }
    }//GEN-LAST:event_tabelperumMouseClicked

    private void txtcariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcariKeyReleased
        String key = txtcari.getText();
        showData(key);
    }//GEN-LAST:event_txtcariKeyReleased

    private void btnuploadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnuploadActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File f = chooser.getSelectedFile();
        unggahfoto.setIcon(new ImageIcon(f.toString()));
        String filename = f.getAbsolutePath();
        brosur.setText(filename);
        
        try {
            File image = new File(filename);
            FileInputStream fis = new  FileInputStream(image);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            for (int readNum; (readNum=fis.read(buf))!=-1;) {
                bos.write(buf, 0, readNum);
                
            }
            byte[] photo = bos.toByteArray();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_btnuploadActionPerformed

    private void btncariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncariActionPerformed

        // TODO add your handling code here:
    }//GEN-LAST:event_btncariActionPerformed

    private void btneditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneditActionPerformed
        
        // TODO add your handling code here:
    }//GEN-LAST:event_btneditActionPerformed

    private void alamatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alamatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_alamatActionPerformed

    private void txtcariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcariActionPerformed

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
            java.util.logging.Logger.getLogger(Data_perumahan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Data_perumahan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Data_perumahan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Data_perumahan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Data_perumahan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField alamat;
    private javax.swing.JSplitPane areaSplit;
    private javax.swing.JTextField brosur;
    private javax.swing.JButton btncari;
    private javax.swing.JButton btnedit;
    private javax.swing.JButton btnupload;
    private javax.swing.JTextField email;
    private javax.swing.JButton hapus;
    private javax.swing.JDesktopPane jDesktopPane2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton logout;
    private javax.swing.JTextField namaperum;
    private javax.swing.JTextField notelepon;
    private javax.swing.JTextField pengembang;
    private javax.swing.JButton simpan;
    private javax.swing.JTable tabelperum;
    private javax.swing.JButton tambah;
    private javax.swing.JComboBox tipePerum;
    private javax.swing.JTextField txtcari;
    private javax.swing.JLabel unggahfoto;
    // End of variables declaration//GEN-END:variables
}
