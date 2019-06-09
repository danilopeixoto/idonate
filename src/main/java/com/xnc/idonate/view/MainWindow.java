// Copyright (c) 2019, Bruno Caputo, Danilo Peixoto and Heitor Toledo.
// All rights reserved.
//
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions are met:
//
// * Redistributions of source code must retain the above copyright notice, this
//   list of conditions and the following disclaimer.
//
// * Redistributions in binary form must reproduce the above copyright notice,
//   this list of conditions and the following disclaimer in the documentation
//   and/or other materials provided with the distribution.
//
// * Neither the name of the copyright holder nor the names of its
//   contributors may be used to endorse or promote products derived from
//   this software without specific prior written permission.
//
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
// AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
// IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
// DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
// FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
// DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
// SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
// CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
// OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
// OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

package com.xnc.idonate.view;

import com.xnc.idonate.controller.Database;
import com.xnc.idonate.controller.PersonAccessor;
import com.xnc.idonate.model.Constants;
import com.xnc.idonate.model.Credentials;
import com.xnc.idonate.model.Person;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class MainWindow extends javax.swing.JFrame implements FocusListener {
    private DefaultTableModel dtm;
    private List<Person> personList;
    private Database database;

    private String hospitalID;

    /**
     * Creates new form IDonateViewer
     */
    public MainWindow(String hospitalID) {
        database = new Database(
                Credentials.DatabaseUser, Credentials.DatabasePassword, Credentials.DatabaseName);
        
        this.initComponents();
        dtm = (DefaultTableModel)tablePeople.getModel();
        personList = new ArrayList<>();
        this.hospitalID = hospitalID;
        frameInitialization();
        
        DocumentListener documentListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateFieldState();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateFieldState();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateFieldState();
            }

            protected void updateFieldState() {
                String text = textFieldSearch.getText();

                if (text.isEmpty() || text.equals(Constants.SearchPlaceholder)) {
                    frameInitialization();
                } else {
                    String query = text.toLowerCase();

                    List<Person> temp = personList.stream()
                            .filter(p -> p.getName().toLowerCase().contains(query)
                            || p.getCPF().toLowerCase().contains(query))
                            .collect(Collectors.toList());

                    dtm.setRowCount(0);

                    for (int i = 0; i < temp.size(); i++) {
                        Object[] rowData = {
                            temp.get(i).getName(),
                            temp.get(i).getCPF(),
                            temp.get(i).getPhone()
                        };
                        
                        dtm.addRow(rowData);
                    }
                }
            }
        };

        textFieldSearch.getDocument().addDocumentListener(documentListener);
        textFieldSearch.addFocusListener(this);
        
        boolean hasSelection = tablePeople.getSelectedRow() != -1;
        
        buttonEdit.setEnabled(hasSelection);
        buttonRemove.setEnabled(hasSelection);
        
        tablePeople.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {
                    boolean hasSelection = tablePeople.getSelectedRow() != -1;
                    
                    buttonEdit.setEnabled(hasSelection);
                    buttonRemove.setEnabled(hasSelection);
                }
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel5 = new javax.swing.JPanel();
        labelIcon1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        textFieldSearch = new javax.swing.JTextField();
        buttonClear = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        buttonAdd = new javax.swing.JButton();
        buttonEdit = new javax.swing.JButton();
        buttonRemove = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablePeople = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("iDonate");
        setResizable(false);

        jPanel5.setLayout(new java.awt.GridBagLayout());

        labelIcon1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/plus.png"))); // NOI18N
        labelIcon1.setMaximumSize(new java.awt.Dimension(300, 300));
        labelIcon1.setMinimumSize(new java.awt.Dimension(300, 300));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(labelIcon1, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("iDonate");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jLabel2, gridBagConstraints);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        textFieldSearch.setPreferredSize(new java.awt.Dimension(335, 26));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(6, 5, 5, 5);
        jPanel1.add(textFieldSearch, gridBagConstraints);

        buttonClear.setText("Limpar");
        buttonClear.setPreferredSize(new java.awt.Dimension(88, 32));
        buttonClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonClearActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 7, 5, 5);
        jPanel1.add(buttonClear, gridBagConstraints);

        jPanel9.setLayout(new java.awt.GridLayout(1, 3, 10, 5));

        buttonAdd.setText("Adicionar");
        buttonAdd.setPreferredSize(new java.awt.Dimension(88, 32));
        buttonAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAddActionPerformed(evt);
            }
        });
        jPanel9.add(buttonAdd);

        buttonEdit.setText("Editar");
        buttonEdit.setPreferredSize(new java.awt.Dimension(88, 32));
        buttonEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonEditActionPerformed(evt);
            }
        });
        jPanel9.add(buttonEdit);

        buttonRemove.setText("Remover");
        buttonRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonRemoveActionPerformed(evt);
            }
        });
        jPanel9.add(buttonRemove);

        tablePeople.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nome", "CPF", "Telefone"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tablePeople);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 452, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void buttonEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonEditActionPerformed
        int index = tablePeople.getSelectedRow();
        String cpf = (String)tablePeople.getModel().getValueAt(index, 1);
        
        PersonDialog.main(null, this, true, hospitalID, cpf);
    }//GEN-LAST:event_buttonEditActionPerformed

    private void buttonClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonClearActionPerformed
        this.textFieldSearch.setText(Constants.SearchPlaceholder);
    }//GEN-LAST:event_buttonClearActionPerformed

    private void buttonAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAddActionPerformed
        PersonDialog.main(null, this, true, hospitalID, null);
    }//GEN-LAST:event_buttonAddActionPerformed

    private void buttonRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonRemoveActionPerformed
        int index = tablePeople.getSelectedRow();
        String cpf = (String)tablePeople.getModel().getValueAt(index, 1);

        try {
            PersonAccessor pa = new PersonAccessor(database);
            pa.remove(cpf);
            frameInitialization();
            
            String query = textFieldSearch.getText();
            
            if (!query.equals(Constants.SearchPlaceholder))
                textFieldSearch.setText(Constants.SearchPlaceholder);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Houve um erro ao acessar o banco de dados. Tente novamente.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_buttonRemoveActionPerformed

    public void frameInitialization() {
        dtm.setRowCount(0);
        
        PersonAccessor acessor = new PersonAccessor(database);
        
        try {
            personList = acessor.getAll(hospitalID);
        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(
                    this,
                    "Houve um erro ao acessar o banco de dados. Tente novamente.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            
            return;
        }

        for (int i = 0; i < personList.size(); i++) {
            Object[] rowData = {
                personList.get(i).getName(),
                personList.get(i).getCPF(),
                personList.get(i).getPhone()
            };
            
            dtm.addRow(rowData);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[], String hospitalID) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainWindow(hospitalID).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonAdd;
    private javax.swing.JButton buttonClear;
    private javax.swing.JButton buttonEdit;
    private javax.swing.JButton buttonRemove;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelIcon1;
    private javax.swing.JTable tablePeople;
    private javax.swing.JTextField textFieldSearch;
    // End of variables declaration//GEN-END:variables

    @Override
    public void focusGained(FocusEvent e) {
        if (textFieldSearch.getText().equals(Constants.SearchPlaceholder)) {
            textFieldSearch.setText(Constants.EmptyString);
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (textFieldSearch.getText().equals(Constants.EmptyString)) {
            textFieldSearch.setText(Constants.SearchPlaceholder);
        }
    }
}