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
import com.xnc.idonate.controller.NotificationManager;
import com.xnc.idonate.controller.PersonAccessor;
import com.xnc.idonate.controller.ResourceAccessor;
import com.xnc.idonate.model.Blood;
import com.xnc.idonate.model.Constants;
import com.xnc.idonate.model.Organ;
import com.xnc.idonate.model.Person;
import com.xnc.idonate.model.Person.Sex;
import com.xnc.idonate.model.Resource;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author Heitor
 */
public class PersonDialog extends javax.swing.JDialog {
    private Database database;
    private DefaultListModel dlm;
    private List<Resource> resourceList;
    private String hospitalID;
    private String personCPF;
    private NotificationManager nmanager;

    /**
     * Creates new form PersonEditor
     */
    public PersonDialog(java.awt.Frame parent, boolean modal,
            String hospitalID, String personCPF) {
        super(parent, modal);
        initComponents();
        dlm = new DefaultListModel();
        resourceList = new ArrayList<>();
        this.hospitalID = hospitalID;
        this.personCPF = personCPF;
        
        try {
            this.nmanager = new NotificationManager();
        }
        catch(IOException e) {
            e.printStackTrace();
        }

        database = new Database(
                Constants.DatabaseUser, Constants.DatabasePassword, Constants.DatabaseName);

        if (personCPF != null)
            fetchPersonData();

        try {
            MaskFormatter cpfMask = new MaskFormatter("###.###.###-##");
            cpfMask.setPlaceholderCharacter('0');

            formattedTextFieldCPF.setFormatterFactory(
                    new DefaultFormatterFactory(cpfMask));
            
            MaskFormatter numberMask = new MaskFormatter("##########");
            
            formattedTextFieldResourceID.setFormatterFactory(
                    new DefaultFormatterFactory(numberMask));

            MaskFormatter phoneMask = new MaskFormatter("(##) #####-####");
            phoneMask.setPlaceholderCharacter('0');

            formattedTextFieldPhone.setFormatterFactory(
                    new DefaultFormatterFactory(phoneMask));
        } catch (ParseException exception) {
            exception.printStackTrace();
        }
    }

    private void fetchPersonData() {
        PersonAccessor pa = new PersonAccessor(database);
        
        try {
            Person person = pa.get(personCPF);

            if (person == null) {
                JOptionPane.showMessageDialog(
                        this,
                        "Não foi possível carregar informações da pessoa.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);

                this.dispose();
            }
            else {
                formattedTextFieldCPF.setValue(person.getCPF());
                textFieldName.setText(person.getName());
                textFieldAddress.setText(person.getAddress());
                formattedTextFieldPhone.setValue(person.getPhone());
                textFieldEmail.setText(person.getEmail());
                spinnerAge.setValue(person.getAge());
                
                radioButtonMale.setSelected(person.getSex() != Sex.Female);
                radioButtonFemale.setSelected(person.getSex() == Sex.Female);
                
                spinnerMass.setValue(person.getWeight());
                comboBoxBloodType.setSelectedIndex(Utility.bloodTypeToComboBoxIndex(person.getBloodType()));
                textAreaMedicalConditions.setText(person.getMedicalConditions());
            }
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Houve um erro ao acessar o banco de dados. Tente novamente.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
        /*------
        
        if (personCPF ) {
            ResourceAccessor acessor = new ResourceAccessor(database);
        }
        
        try {
            resourceList = acessor.getAll(hosp);
        } catch (SQLException ex) {
            System.out.println("Error [MainWindow.frameInitialization]: " + ex.getMessage());
        }

        for (int i = 0; i < pessoas.size(); i++) {
            String r = pessoas.get(i).getName() + " - " + pessoas.get(i).getCPF() + " - " + pessoas.get(i).getPhone();
            dlm.add(i, r);
        }
        
        listPeople.setModel(dlm);*/
    }

    public void updateResourceList() {
        dlm.clear();
        
        for (int i = 0; i < resourceList.size(); i++) {
            Resource resource = resourceList.get(i);
            String r = null;

            if (resource.getType() == Resource.ResourceType.Blood) {
                Blood blood = (Blood)resource;

                r = resource.getID() + " | Sangue | " + Utility.bloodTypeToString(blood.getBloodType()) + " | "
                        + resource.getDonationDate().toString();
            } else if (resource.getType() == Resource.ResourceType.Organ) {
                Organ organ = (Organ)resource;

                r = resource.getID() + " | Orgão | " + Utility.organTypeToString(organ.getOrganType()) + " | "
                        + resource.getDonationDate().toString();
            } else
                r = resource.getID() + " | Medula Óssea | "
                        + resource.getDonationDate().toString();

            dlm.add(i, r);
        }
        listResources.setModel(dlm);
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

        buttonGroupSex = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        labelIcon = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        formattedTextFieldCPF = new javax.swing.JFormattedTextField();
        textFieldName = new javax.swing.JTextField();
        textFieldAddress = new javax.swing.JTextField();
        formattedTextFieldPhone = new javax.swing.JFormattedTextField();
        textFieldEmail = new javax.swing.JTextField();
        spinnerAge = new javax.swing.JSpinner();
        jPanel6 = new javax.swing.JPanel();
        radioButtonMale = new javax.swing.JRadioButton();
        radioButtonFemale = new javax.swing.JRadioButton();
        spinnerMass = new javax.swing.JSpinner();
        comboBoxBloodType = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        textAreaMedicalConditions = new javax.swing.JTextArea();
        tabbedPane = new javax.swing.JTabbedPane();
        panelDonor = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        listResources = new javax.swing.JList<>();
        jPanel7 = new javax.swing.JPanel();
        buttonAdd = new javax.swing.JButton();
        buttonEdit = new javax.swing.JButton();
        buttonRemove = new javax.swing.JButton();
        panelAcceptor = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        formattedTextFieldResourceID = new javax.swing.JFormattedTextField();
        buttonNotify = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        buttonDone = new javax.swing.JButton();
        buttonCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("iDonate");
        setResizable(false);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        labelIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/plus.png"))); // NOI18N
        labelIcon.setMaximumSize(new java.awt.Dimension(300, 300));
        labelIcon.setMinimumSize(new java.awt.Dimension(300, 300));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(labelIcon, gridBagConstraints);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("iDonate");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel1, gridBagConstraints);

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jPanel4.setLayout(new java.awt.GridBagLayout());

        jPanel5.setLayout(new java.awt.GridBagLayout());

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("CPF");
        jLabel3.setMaximumSize(new java.awt.Dimension(20, 14));
        jLabel3.setMinimumSize(new java.awt.Dimension(150, 14));
        jLabel3.setPreferredSize(new java.awt.Dimension(120, 26));
        jPanel5.add(jLabel3, new java.awt.GridBagConstraints());

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Nome");
        jLabel2.setMaximumSize(new java.awt.Dimension(20, 14));
        jLabel2.setMinimumSize(new java.awt.Dimension(150, 14));
        jLabel2.setPreferredSize(new java.awt.Dimension(120, 26));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jLabel2, gridBagConstraints);

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Endereço");
        jLabel4.setMaximumSize(new java.awt.Dimension(20, 14));
        jLabel4.setMinimumSize(new java.awt.Dimension(150, 14));
        jLabel4.setPreferredSize(new java.awt.Dimension(120, 26));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jLabel4, gridBagConstraints);

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Telefone");
        jLabel5.setMaximumSize(new java.awt.Dimension(20, 14));
        jLabel5.setMinimumSize(new java.awt.Dimension(150, 14));
        jLabel5.setPreferredSize(new java.awt.Dimension(120, 26));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jLabel5, gridBagConstraints);

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("E-Mail");
        jLabel6.setMaximumSize(new java.awt.Dimension(20, 14));
        jLabel6.setMinimumSize(new java.awt.Dimension(150, 14));
        jLabel6.setPreferredSize(new java.awt.Dimension(120, 26));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jLabel6, gridBagConstraints);

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Idade");
        jLabel7.setMaximumSize(new java.awt.Dimension(20, 14));
        jLabel7.setMinimumSize(new java.awt.Dimension(150, 14));
        jLabel7.setPreferredSize(new java.awt.Dimension(120, 26));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jLabel7, gridBagConstraints);

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Sexo");
        jLabel8.setMaximumSize(new java.awt.Dimension(20, 14));
        jLabel8.setMinimumSize(new java.awt.Dimension(150, 14));
        jLabel8.setPreferredSize(new java.awt.Dimension(120, 26));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jLabel8, gridBagConstraints);

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Massa");
        jLabel9.setMaximumSize(new java.awt.Dimension(20, 14));
        jLabel9.setMinimumSize(new java.awt.Dimension(150, 14));
        jLabel9.setPreferredSize(new java.awt.Dimension(120, 26));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jLabel9, gridBagConstraints);

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Tipo Sanguíneo");
        jLabel10.setMaximumSize(new java.awt.Dimension(20, 14));
        jLabel10.setMinimumSize(new java.awt.Dimension(150, 14));
        jLabel10.setPreferredSize(new java.awt.Dimension(120, 26));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jLabel10, gridBagConstraints);

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Condições Médicas");
        jLabel11.setMaximumSize(new java.awt.Dimension(20, 14));
        jLabel11.setMinimumSize(new java.awt.Dimension(150, 14));
        jLabel11.setPreferredSize(new java.awt.Dimension(120, 26));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jLabel11, gridBagConstraints);

        formattedTextFieldCPF.setPreferredSize(new java.awt.Dimension(250, 26));
        formattedTextFieldCPF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                formattedTextFieldCPFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(formattedTextFieldCPF, gridBagConstraints);

        textFieldName.setPreferredSize(new java.awt.Dimension(250, 26));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(textFieldName, gridBagConstraints);

        textFieldAddress.setPreferredSize(new java.awt.Dimension(250, 26));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(textFieldAddress, gridBagConstraints);

        formattedTextFieldPhone.setPreferredSize(new java.awt.Dimension(250, 26));
        formattedTextFieldPhone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                formattedTextFieldPhoneActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(formattedTextFieldPhone, gridBagConstraints);

        textFieldEmail.setPreferredSize(new java.awt.Dimension(250, 26));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(textFieldEmail, gridBagConstraints);

        spinnerAge.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));
        spinnerAge.setPreferredSize(new java.awt.Dimension(250, 26));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(spinnerAge, gridBagConstraints);

        jPanel6.setLayout(new java.awt.GridLayout(1, 0, 40, 0));

        buttonGroupSex.add(radioButtonMale);
        radioButtonMale.setSelected(true);
        radioButtonMale.setText("Masculino");
        radioButtonMale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioButtonMaleActionPerformed(evt);
            }
        });
        jPanel6.add(radioButtonMale);

        buttonGroupSex.add(radioButtonFemale);
        radioButtonFemale.setText("Feminino");
        radioButtonFemale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioButtonFemaleActionPerformed(evt);
            }
        });
        jPanel6.add(radioButtonFemale);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jPanel6, gridBagConstraints);

        spinnerMass.setModel(new javax.swing.SpinnerNumberModel(0.0f, 0.0f, null, 0.01f));
        spinnerMass.setPreferredSize(new java.awt.Dimension(250, 26));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(spinnerMass, gridBagConstraints);

        comboBoxBloodType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "A+", "A-", "AB+", "AB-", "B+", "B-", "O+", "O-" }));
        comboBoxBloodType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBoxBloodTypeActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(comboBoxBloodType, gridBagConstraints);

        textAreaMedicalConditions.setColumns(20);
        textAreaMedicalConditions.setRows(5);
        jScrollPane2.setViewportView(textAreaMedicalConditions);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jScrollPane2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(jPanel5, gridBagConstraints);

        panelDonor.setLayout(new java.awt.GridBagLayout());

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("Recursos");
        jLabel12.setMaximumSize(new java.awt.Dimension(1, 1));
        jLabel12.setMinimumSize(new java.awt.Dimension(150, 16));
        jLabel12.setPreferredSize(new java.awt.Dimension(150, 16));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.ipady = 131;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 5, 6);
        panelDonor.add(jLabel12, gridBagConstraints);

        jScrollPane3.setPreferredSize(new java.awt.Dimension(150, 147));

        listResources.setMinimumSize(new java.awt.Dimension(150, 36));
        listResources.setPreferredSize(new java.awt.Dimension(150, 36));
        jScrollPane3.setViewportView(listResources);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panelDonor.add(jScrollPane3, gridBagConstraints);

        jPanel7.setLayout(new java.awt.GridLayout(1, 3, 10, 5));

        buttonAdd.setText("Adicionar");
        buttonAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAddActionPerformed(evt);
            }
        });
        jPanel7.add(buttonAdd);

        buttonEdit.setText("Editar");
        buttonEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonEditActionPerformed(evt);
            }
        });
        jPanel7.add(buttonEdit);

        buttonRemove.setText("Remover");
        buttonRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonRemoveActionPerformed(evt);
            }
        });
        jPanel7.add(buttonRemove);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panelDonor.add(jPanel7, gridBagConstraints);

        tabbedPane.addTab("Doador", panelDonor);

        panelAcceptor.setLayout(new java.awt.GridBagLayout());

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("Identificação de Recurso");
        jLabel13.setMaximumSize(new java.awt.Dimension(20, 14));
        jLabel13.setMinimumSize(new java.awt.Dimension(150, 14));
        jLabel13.setPreferredSize(new java.awt.Dimension(150, 26));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panelAcceptor.add(jLabel13, gridBagConstraints);

        formattedTextFieldResourceID.setPreferredSize(new java.awt.Dimension(150, 26));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(7, 7, 5, 5);
        panelAcceptor.add(formattedTextFieldResourceID, gridBagConstraints);

        buttonNotify.setText("Notificar");
        buttonNotify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonNotifyActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
        panelAcceptor.add(buttonNotify, gridBagConstraints);

        tabbedPane.addTab("Receptor", panelAcceptor);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(tabbedPane, gridBagConstraints);

        jScrollPane1.setViewportView(jPanel4);

        jPanel2.setLayout(new java.awt.GridLayout(1, 2, 5, 10));

        buttonDone.setText("Concluir");
        buttonDone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonDoneActionPerformed(evt);
            }
        });
        jPanel2.add(buttonDone);

        buttonCancel.setText("Cancelar");
        buttonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCancelActionPerformed(evt);
            }
        });
        jPanel2.add(buttonCancel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 473, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formattedTextFieldCPFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_formattedTextFieldCPFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_formattedTextFieldCPFActionPerformed

    private void formattedTextFieldPhoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_formattedTextFieldPhoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_formattedTextFieldPhoneActionPerformed

    private void radioButtonMaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioButtonMaleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_radioButtonMaleActionPerformed

    private void radioButtonFemaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioButtonFemaleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_radioButtonFemaleActionPerformed

    private void comboBoxBloodTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBoxBloodTypeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboBoxBloodTypeActionPerformed

    private void buttonEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonEditActionPerformed
        int index = listResources.getSelectedIndex();
        ResourceDialog.main(null, null, true, this, resourceList, index);
    }//GEN-LAST:event_buttonEditActionPerformed

    private void buttonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCancelActionPerformed
        this.dispose();
    }//GEN-LAST:event_buttonCancelActionPerformed

    private void buttonAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAddActionPerformed
        ResourceDialog.main(null, null, true, this, resourceList, null);
    }//GEN-LAST:event_buttonAddActionPerformed

    private boolean isEmpty() {
        return this.textFieldAddress.getText().isEmpty()
                || this.spinnerAge.getValue().toString().isEmpty()
                || ((String)this.formattedTextFieldCPF.getValue()).isEmpty()
                || this.textFieldEmail.getText().isEmpty()
                || this.spinnerMass.getValue().toString().isEmpty()
                || this.textFieldName.getText().isEmpty()
                || ((String)this.formattedTextFieldPhone.getValue()).isEmpty();
    }

    private void buttonDoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonDoneActionPerformed
        if (this.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Um ou mais dos campos obrigatórios não estão preenchidos.");
                return;
            }

            try {
                PersonAccessor pa = new PersonAccessor(database);
                boolean status = pa.has(this.formattedTextFieldCPF.getText());

                if (status) {
                    JOptionPane.showMessageDialog(this, "CPF já cadastrado.");
                    return;
                }
            }catch (SQLException ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "Houve um erro ao acessar o banco de dados. Tente novamente.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);

                return;
            }

            if (!this.textFieldEmail.getText().matches(Constants.EmailRegex)) {
                JOptionPane.showMessageDialog(this, "Email inválido.");
                return;
            }

            System.out.println(this.formattedTextFieldCPF.getText());

            Person person = new Person();
            person.setCPF(this.formattedTextFieldCPF.getText());
            person.setName(this.textFieldName.getText());
            person.setAddress(this.textFieldAddress.getText());
            person.setPhone(this.formattedTextFieldPhone.getText());
            person.setEmail(this.textFieldEmail.getText());
            person.setAge(Integer.parseInt(this.spinnerAge.getValue().toString()));
            person.setSex(this.radioButtonFemale.isSelected() ? Sex.Female : Sex.Male);
            person.setWeight(Float.parseFloat(this.spinnerMass.getValue().toString()));
            person.setBloodType(Utility.comboBoxIndexToBloodType(this.comboBoxBloodType.getSelectedIndex()));
            person.setMedicalConditions(this.textAreaMedicalConditions.getText());
            person.setHospitalID(hospitalID);

            for (Resource resource : resourceList)
                resource.setDonorCPF(person.getCPF());

            try {
                PersonAccessor pa = new PersonAccessor(database);
                pa.add(person);

                ResourceAccessor ra = new ResourceAccessor(database);

                for (Resource resource : resourceList) {
                    ra.add(resource);
                }
            } catch (SQLException exception) {
                JOptionPane.showMessageDialog(
                        this,
                        "Houve um erro ao acessar o banco de dados. Tente novamente.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);

                exception.printStackTrace();
                return;
            }
        
        this.dispose();
        
        MainWindow window = (MainWindow)getParent();
        window.frameInitialization();
    }//GEN-LAST:event_buttonDoneActionPerformed

    private void buttonRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonRemoveActionPerformed
        int index = listResources.getSelectedIndex();
        resourceList.remove(index);
        updateResourceList();
    }//GEN-LAST:event_buttonRemoveActionPerformed

    private void buttonNotifyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonNotifyActionPerformed
        String text = (String)formattedTextFieldResourceID.getValue();
        
        if (text.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this, "Campo não preenchido.");
        }
        else {
            try {
                ResourceAccessor ra = new ResourceAccessor(database);
                Resource resource = ra.get(Integer.parseInt(text));
                
                PersonAccessor pa = new PersonAccessor(database);
                Person person = pa.get(resource.getDonorCPF());

                nmanager.sendDefaultSmsToDonor(person, resource);
            }
            catch (SQLException e) {
                JOptionPane.showMessageDialog(
                    this,
                    "Houve um erro ao acessar o banco de dados. Tente novamente.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_buttonNotifyActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[], java.awt.Frame parent, boolean modal,
            String hospitalID, String personCPF) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */

        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                PersonDialog dialog = new PersonDialog(parent, modal, hospitalID, personCPF);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        dialog.dispose();
                    }
                });
                
                dialog.setLocationRelativeTo(parent);
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonAdd;
    private javax.swing.JButton buttonCancel;
    private javax.swing.JButton buttonDone;
    private javax.swing.JButton buttonEdit;
    private javax.swing.ButtonGroup buttonGroupSex;
    private javax.swing.JButton buttonNotify;
    private javax.swing.JButton buttonRemove;
    private javax.swing.JComboBox<String> comboBoxBloodType;
    private javax.swing.JFormattedTextField formattedTextFieldCPF;
    private javax.swing.JFormattedTextField formattedTextFieldPhone;
    private javax.swing.JFormattedTextField formattedTextFieldResourceID;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel labelIcon;
    private javax.swing.JList<String> listResources;
    private javax.swing.JPanel panelAcceptor;
    private javax.swing.JPanel panelDonor;
    private javax.swing.JRadioButton radioButtonFemale;
    private javax.swing.JRadioButton radioButtonMale;
    private javax.swing.JSpinner spinnerAge;
    private javax.swing.JSpinner spinnerMass;
    private javax.swing.JTabbedPane tabbedPane;
    private javax.swing.JTextArea textAreaMedicalConditions;
    private javax.swing.JTextField textFieldAddress;
    private javax.swing.JTextField textFieldEmail;
    private javax.swing.JTextField textFieldName;
    // End of variables declaration//GEN-END:variables
}
