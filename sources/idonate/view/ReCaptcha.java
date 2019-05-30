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
package idonate.view;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;
import java.util.stream.Stream;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author Heitor
 */
public class ReCaptcha extends javax.swing.JFrame {
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
    public ReCaptcha() throws IOException {
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
                    System.out.println("Wrong!");
                    return;
            }
        }
        
        System.out.println("Right!");
    }
    
    public void clickOnFigure(final int index) {
        this.figureStatus[index] = this.figureStatus[index].toggleClick();
    }
    
    private ReFigure[] loadRecaptchaFigures() throws IOException {
        final Random rng = new Random();
        final String dir = "C:\\recaptcha\\";
        final Stream<Path> dirs = Files.list(new File(dir).toPath());
        final String dirNumber = Integer.toString(rng.nextInt((int)dirs.count()));
        
        int i = 0;
        final ReFigure[] figures = new ReFigure[this.size * this.size];
        for (final File file : new File(dir + dirNumber).listFiles()) {
            figures[i++] = new ReFigure(file);
        }
        
        return figures;
    }
    
    private void makeRecaptcha() throws IOException {
        this.recaptchaPanel.setLayout(new GridLayout(this.size, this.size));
        
        final ReFigure[] figures = this.loadRecaptchaFigures();
        
        for (int i = 0; i < this.size * this.size; ++i) {
            final int index = i;
            final ReFigure figure = figures[i];
            if (figure.isSolution()) {
                this.figureStatus[i] = FigureStatus.IS_SOLUTION_UNCLICKED;
            } else {
                this.figureStatus[i] = FigureStatus.IS_NOT_SOLUTION_UNCLICKED;
            }
            
            final JLabel label = new JLabel(figure);
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
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

        selectAllSquareslabel = new javax.swing.JLabel();
        foodLabel = new javax.swing.JLabel();
        ifNoneLabel = new javax.swing.JLabel();
        recaptchaPanel = new javax.swing.JPanel();
        skipButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        selectAllSquareslabel.setText("Selecione todos os quadrados com");

        foodLabel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        foodLabel.setText("comida");

        ifNoneLabel.setText("Caso não tenha, clique em pular");

        javax.swing.GroupLayout recaptchaPanelLayout = new javax.swing.GroupLayout(recaptchaPanel);
        recaptchaPanel.setLayout(recaptchaPanelLayout);
        recaptchaPanelLayout.setHorizontalGroup(
            recaptchaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        recaptchaPanelLayout.setVerticalGroup(
            recaptchaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 395, Short.MAX_VALUE)
        );

        skipButton.setText("Pular");
        skipButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                skipButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(recaptchaPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(selectAllSquareslabel)
                                    .addComponent(ifNoneLabel))
                                .addGap(0, 235, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(foodLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(skipButton)
                        .addGap(46, 46, 46))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(selectAllSquareslabel)
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(foodLabel)
                    .addComponent(skipButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ifNoneLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(recaptchaPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
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
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ReCaptcha.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ReCaptcha.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ReCaptcha.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ReCaptcha.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            try {
                new ReCaptcha().setVisible(true);
            } catch (IOException e) {
                System.out.println(e.getMessage());
                System.exit(-1);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel foodLabel;
    private javax.swing.JLabel ifNoneLabel;
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
