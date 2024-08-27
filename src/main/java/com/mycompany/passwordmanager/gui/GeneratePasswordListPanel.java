/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.passwordmanager.gui;

import com.mycompany.passwordmanager.bo.PasswordGenerator;
import com.mycompany.passwordmanager.fix.Constants;
import com.mycompany.passwordmanager.util.smart.SystemUtil;
import javax.swing.JOptionPane;

/**
 *
 * @author Tom7668h
 */
public class GeneratePasswordListPanel extends javax.swing.JPanel {

    /** Creates new form GeneratePasswordListPanel */
    public GeneratePasswordListPanel() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlSettings = new javax.swing.JPanel();
        ckbxLowercase = new javax.swing.JCheckBox();
        ckbxUppercase = new javax.swing.JCheckBox();
        ckbxDigits = new javax.swing.JCheckBox();
        ckbxSpecialCharacters = new javax.swing.JCheckBox();
        spnrNumberCharcaters = new javax.swing.JSpinner();
        lblCharacters = new javax.swing.JLabel();
        pnlControle = new javax.swing.JPanel();
        bntSelectAll = new javax.swing.JButton();
        btnDefault = new javax.swing.JButton();
        btnCopy = new javax.swing.JButton();
        btnGenerate = new javax.swing.JButton();
        pnlPasswords = new javax.swing.JPanel();
        scpnPasswords = new javax.swing.JScrollPane();
        txrPasswords = new javax.swing.JTextArea();

        pnlSettings.setBorder(javax.swing.BorderFactory.createTitledBorder("Settings"));

        ckbxLowercase.setText("Lowercase letters (a, b, c, ...)");

        ckbxUppercase.setText("Uppercase letters (A, B, C, ...)");

        ckbxDigits.setText("Digits (1, 2, 3, ...)");

        ckbxSpecialCharacters.setText("Special character (#, %, &, ...)");

        spnrNumberCharcaters.setValue(16);

        lblCharacters.setText("Characters:");

        javax.swing.GroupLayout pnlSettingsLayout = new javax.swing.GroupLayout(pnlSettings);
        pnlSettings.setLayout(pnlSettingsLayout);
        pnlSettingsLayout.setHorizontalGroup(
            pnlSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSettingsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlSettingsLayout.createSequentialGroup()
                        .addComponent(lblCharacters)
                        .addGap(12, 12, 12)
                        .addComponent(spnrNumberCharcaters))
                    .addGroup(pnlSettingsLayout.createSequentialGroup()
                        .addGroup(pnlSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ckbxUppercase)
                            .addComponent(ckbxDigits)
                            .addComponent(ckbxSpecialCharacters)
                            .addComponent(ckbxLowercase))
                        .addGap(0, 2, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlSettingsLayout.setVerticalGroup(
            pnlSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSettingsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ckbxLowercase)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ckbxUppercase)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ckbxDigits)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ckbxSpecialCharacters)
                .addGap(11, 11, 11)
                .addGroup(pnlSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCharacters)
                    .addComponent(spnrNumberCharcaters, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlControle.setBorder(javax.swing.BorderFactory.createTitledBorder("Controle"));

        bntSelectAll.setText("Select All");
        bntSelectAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntSelectAllActionPerformed(evt);
            }
        });

        btnDefault.setText("Default");
        btnDefault.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDefaultActionPerformed(evt);
            }
        });

        btnCopy.setText("Copy");
        btnCopy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCopyActionPerformed(evt);
            }
        });

        btnGenerate.setText("Generate");
        btnGenerate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlControleLayout = new javax.swing.GroupLayout(pnlControle);
        pnlControle.setLayout(pnlControleLayout);
        pnlControleLayout.setHorizontalGroup(
            pnlControleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlControleLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlControleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnGenerate)
                    .addComponent(btnDefault, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCopy, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bntSelectAll, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlControleLayout.setVerticalGroup(
            pnlControleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlControleLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnDefault, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addComponent(bntSelectAll, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addComponent(btnCopy, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addComponent(btnGenerate, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlPasswords.setBorder(javax.swing.BorderFactory.createTitledBorder("Passwords"));

        txrPasswords.setColumns(20);
        txrPasswords.setRows(5);
        scpnPasswords.setViewportView(txrPasswords);

        javax.swing.GroupLayout pnlPasswordsLayout = new javax.swing.GroupLayout(pnlPasswords);
        pnlPasswords.setLayout(pnlPasswordsLayout);
        pnlPasswordsLayout.setHorizontalGroup(
            pnlPasswordsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPasswordsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scpnPasswords)
                .addContainerGap())
        );
        pnlPasswordsLayout.setVerticalGroup(
            pnlPasswordsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPasswordsLayout.createSequentialGroup()
                .addComponent(scpnPasswords, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlPasswords, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlSettings, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pnlControle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlSettings, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlControle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlPasswords, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void bntSelectAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntSelectAllActionPerformed
        buttonSelectAll();
    }//GEN-LAST:event_bntSelectAllActionPerformed

    private void btnDefaultActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDefaultActionPerformed
        buttonDefault();
    }//GEN-LAST:event_btnDefaultActionPerformed

    private void btnCopyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCopyActionPerformed
        buttonCopy();
    }//GEN-LAST:event_btnCopyActionPerformed

    private void btnGenerateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerateActionPerformed
        buttonGenerate();
    }//GEN-LAST:event_btnGenerateActionPerformed

    private void buttonGenerate() {
        String passwords = "";
        for (int rep = 0; rep < Constants.NUMBER_OF_GENERATED_PASSWORDS; rep++) {
            final String password = PasswordGenerator.generatePassword((int) this.spnrNumberCharcaters.getValue(),
                    this.ckbxLowercase.isSelected(),
                    this.ckbxUppercase.isSelected(),
                    this.ckbxDigits.isSelected(),
                    this.ckbxSpecialCharacters.isSelected());
            if (password == null) {
                JOptionPane.showMessageDialog(this, "No characters available to generate a password!", "No Characters Available", JOptionPane.OK_OPTION);
                break;
            }
            passwords += password + System.lineSeparator();
        }
        txrPasswords.setText(passwords);
    }

    private void buttonCopy() {
        SystemUtil.copyToClipboard(this.txrPasswords.getText());
    }

    private void buttonSelectAll() {
        selectAllCheckBoxes();
    }

    private void buttonDefault() {
        this.spnrNumberCharcaters.setValue(16);
        selectAllCheckBoxes();
    }

    private void selectAllCheckBoxes() {
        this.ckbxLowercase.setSelected(true);
        this.ckbxUppercase.setSelected(true);
        this.ckbxDigits.setSelected(true);
        this.ckbxSpecialCharacters.setSelected(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bntSelectAll;
    private javax.swing.JButton btnCopy;
    private javax.swing.JButton btnDefault;
    private javax.swing.JButton btnGenerate;
    private javax.swing.JCheckBox ckbxDigits;
    private javax.swing.JCheckBox ckbxLowercase;
    private javax.swing.JCheckBox ckbxSpecialCharacters;
    private javax.swing.JCheckBox ckbxUppercase;
    private javax.swing.JLabel lblCharacters;
    private javax.swing.JPanel pnlControle;
    private javax.swing.JPanel pnlPasswords;
    private javax.swing.JPanel pnlSettings;
    private javax.swing.JScrollPane scpnPasswords;
    private javax.swing.JSpinner spnrNumberCharcaters;
    private javax.swing.JTextArea txrPasswords;
    // End of variables declaration//GEN-END:variables
}
