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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.stream.Stream;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.border.Border;

/**
 *
 * @author Heitor
 */
public class CaptchaForm extends javax.swing.JFrame {
    private final int size = 4;
    
    private final FigureStatus[] figureStatus;
    
    private enum FigureStatus {
        IS_NOT_SOLUTION_CLICKED,
        IS_NOT_SOLUTION_UNCLICKED,
        IS_SOLUTION_CLICKED,
        IS_SOLUTION_UNCLICKED;
        
        public FigureStatus toggleClick() {
            switch (this) {
                case IS_NOT_SOLUTION_CLICKED:
                    return FigureStatus.IS_NOT_SOLUTION_UNCLICKED;
                case IS_NOT_SOLUTION_UNCLICKED:
                    return FigureStatus.IS_NOT_SOLUTION_CLICKED;
                case IS_SOLUTION_CLICKED:
                    return FigureStatus.IS_SOLUTION_UNCLICKED;
                case IS_SOLUTION_UNCLICKED:
                    return FigureStatus.IS_SOLUTION_CLICKED;
                default:
                    throw new IllegalArgumentException();
            }
        }
    }

    /**
     * Creates new form ReCaptcha
     * @throws java.io.IOException
     */
    public CaptchaForm() throws IOException {
        this.figureStatus = new FigureStatus[this.size * this.size];
        this.initComponents();
        this.makeRecaptcha();
    }

    public void checkSolution() {
        for (final FigureStatus fs : this.figureStatus) {
            switch (fs) {
                case IS_NOT_SOLUTION_UNCLICKED:
                case IS_SOLUTION_CLICKED:
                    break;
                default:
                    return;
            }
        }
        
        MainWindow.main(null);
        this.dispose();
    }

    public void clickOnFigure(final int index) {
        this.figureStatus[index] = this.figureStatus[index].toggleClick();
    }

    private ArrayList<ReFigure> loadRecaptchaFigures() throws IOException {
        final Random rng = new Random();
        final String dir = "recaptcha/";
        final String path = this.getClass().getClassLoader().getResource(dir).getPath();
        final Stream<Path> dirs = Files.list(new File(path).toPath());
        final String dirNumber = Integer.toString(rng.nextInt((int)dirs.count()));
        
        ArrayList<ReFigure> figures = new ArrayList(this.size * this.size);
        
        for (final File file : new File(path + dirNumber).listFiles())
            figures.add(new ReFigure(file));
        
        Collections.shuffle(figures);

        return figures;
    }

    private void makeRecaptcha() throws IOException {
        final Color grey =  Color.decode("#3C3F41");
        
        final Border defaultBorder = BorderFactory.createLineBorder(grey, 2);
        final Border blueBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(grey, 2),
                BorderFactory.createLineBorder(Color.decode("#00baff"), 4));
        
        this.recaptchaPanel.setLayout(new GridLayout(this.size, this.size));
        this.recaptchaPanel.setBorder(defaultBorder);
        
        ArrayList<ReFigure> figures = this.loadRecaptchaFigures();
        
        for (int i = 0; i < this.size * this.size; ++i) {
            final int index = i;
            final ReFigure figure = figures.get(i);
            if (figure.isSolution()) {
                this.figureStatus[i] = FigureStatus.IS_SOLUTION_UNCLICKED;
            } else {
                this.figureStatus[i] = FigureStatus.IS_NOT_SOLUTION_UNCLICKED;
            }

            final JLabel label = new JLabel(figure);
            
            label.setBorder(defaultBorder);
            
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(final MouseEvent e) {
                    switch (figureStatus[index]) {
                        case IS_NOT_SOLUTION_UNCLICKED:
                        case IS_SOLUTION_UNCLICKED:
                            label.setBorder(blueBorder);
                            break;
                        default:
                            label.setBorder(defaultBorder);
                            break;
                    }

                    clickOnFigure(index);
                    checkSolution();
                }
            });

            this.recaptchaPanel.add(label);
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
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        selectAllSquareslabel = new javax.swing.JLabel();
        foodLabel = new javax.swing.JLabel();
        ifNoneLabel = new javax.swing.JLabel();
        skipButton = new javax.swing.JButton();
        recaptchaPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("iDonate");
        setResizable(false);

        jPanel2.setLayout(new java.awt.GridBagLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/attention.png"))); // NOI18N
        jLabel1.setText("jLabel1");
        jLabel1.setMaximumSize(new java.awt.Dimension(64, 64));
        jLabel1.setMinimumSize(new java.awt.Dimension(64, 64));
        jLabel1.setPreferredSize(new java.awt.Dimension(64, 64));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 6, 5, 6);
        jPanel2.add(jLabel1, gridBagConstraints);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        selectAllSquareslabel.setText("Selecione todos os quadrados com:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 0, 4);
        jPanel1.add(selectAllSquareslabel, gridBagConstraints);

        foodLabel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        foodLabel.setText("comida");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 0, 4);
        jPanel1.add(foodLabel, gridBagConstraints);

        ifNoneLabel.setText("Caso não tenha, clique em pular.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 0, 4);
        jPanel1.add(ifNoneLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
        jPanel2.add(jPanel1, gridBagConstraints);

        skipButton.setText("Pular");
        skipButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                skipButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(7, 5, 6, 5);
        jPanel2.add(skipButton, gridBagConstraints);

        recaptchaPanel.setPreferredSize(new java.awt.Dimension(400, 400));

        javax.swing.GroupLayout recaptchaPanelLayout = new javax.swing.GroupLayout(recaptchaPanel);
        recaptchaPanel.setLayout(recaptchaPanelLayout);
        recaptchaPanelLayout.setHorizontalGroup(
            recaptchaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        recaptchaPanelLayout.setVerticalGroup(
            recaptchaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 394, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(7, Short.MAX_VALUE)
                .addComponent(recaptchaPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(90, Short.MAX_VALUE)
                .addComponent(recaptchaPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 394, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(406, Short.MAX_VALUE)))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void skipButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_skipButtonActionPerformed
        this.checkSolution();
    }//GEN-LAST:event_skipButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(final String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            try {
                new CaptchaForm().setVisible(true);
            } catch (IOException e) {
                System.out.println(e.getMessage());
                System.exit(-1);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel foodLabel;
    private javax.swing.JLabel ifNoneLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel recaptchaPanel;
    private javax.swing.JLabel selectAllSquareslabel;
    private javax.swing.JButton skipButton;
    // End of variables declaration//GEN-END:variables

    private class ReFigure extends ImageIcon {
        private final boolean isSolution;
        
        public ReFigure(final File file) throws IOException {
            super(
                new ImageIcon(ImageIO.read(file))
                    .getImage()
                    .getScaledInstance(100, 100, Image.SCALE_DEFAULT));
            this.isSolution = file.getName().startsWith("f");
        }
        
        public boolean isSolution() {
            return this.isSolution;
        }
    }
}
