/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mastermind;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;

/**
 *
 * @author alois
 */
public class Game extends javax.swing.JFrame {

    /**
     * Creates new form Game
     */
    static private int nbTry;
    static public final int cols = 4;
    private int currentTry = 1;

    JButton[][] displayBoard;
    int[][] gestionBoard;

    int[] solutions;

    int[][] clues;
    ClueGraphic[] displayClues;

    public Game(int nbTry) {
        initComponents();
        this.setResizable(false);

        this.nbTry = nbTry;

        setup();
        solGeneration();
    }

    ActionListener listen = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            int i = 0, j = 0;
            boolean found = false;
            for (i = 0; i < cols; i++) {
                for (j = 0; j < nbTry; j++) {
                    if (ae.getSource() == displayBoard[i][j]) {
                        found = true;
                        break;
                    }
                }
                if (found) {
                    break;
                }
            }
            if (j == nbTry - currentTry) {
                displayColor(i, j);
            }

        }
    };

    private void setup() {
        gestionBoard = new int[cols][nbTry];
        displayBoard = new JButton[cols][nbTry];
        solutions = new int[cols];

        clues = new int[nbTry][cols];
        displayClues = new ClueGraphic[nbTry];

        //Init gestionBoard
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < nbTry; j++) {
                gestionBoard[i][j] = -1;
            }
        }

        //Init displayBoard
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < nbTry; j++) {
                displayBoard[i][j] = new JButton();
                displayBoard[i][j].setBackground(Color.BLACK);
                displayBoard[i][j].setSize(jPanel1.getWidth() / cols, jPanel1.getHeight() / nbTry);
                jPanel1.add(displayBoard[i][j]);
                displayBoard[i][j].setLocation(i * jPanel1.getWidth() / cols, j * jPanel1.getHeight() / nbTry);
                displayBoard[i][j].addActionListener(listen);
            }
        }

        //Init clues
        for (int i = 0; i < nbTry; i++) {
            displayClues[i] = new ClueGraphic();
            displayClues[i].setBorder(BorderFactory.createLineBorder(Color.WHITE));
            displayClues[i].setSize(jPanel3.getWidth(), jPanel3.getHeight() / nbTry);
            jPanel3.add(displayClues[i]);
            displayClues[i].setLocation(0, i * jPanel3.getHeight() / nbTry);
            displayClues[i].redraw();
        }
    }

    private void solGeneration() {
        for (int i = 0; i < solutions.length; i++) {
            solutions[i] = (int) (Math.random() * 4);
        }
    }

    private void algo(int current) {
        for (int i = 0; i < cols; i++) {
            for (int j = i; j < cols + i; j++) {
                if (gestionBoard[i][nbTry - current] % 4 == solutions[j % cols] && i == j) {
                    clues[nbTry - current][i] = 2;
                    break;
                } else if (gestionBoard[i][nbTry - current] % 4 == solutions[j % cols]) {
                    clues[nbTry - current][i] = 1;
                    break;
                } else {
                    clues[nbTry - current][i] = 0;
                }
            }
        }

        displayClues[nbTry - current].addClues(clues[nbTry - current]);
        displayClues[nbTry - current].redraw();
    }

    private void win(int current) {
        boolean win = true;
        for (int i = 0; i < cols; i++) {
            if (solutions[i] != gestionBoard[i][nbTry - current]) {
                win = false;
                break;
            }
        }

        if (win) {
            javax.swing.JOptionPane.showMessageDialog(this, "You win");
            System.exit(1);
        }
    }

    private void loose() {
        if (currentTry == nbTry) {
            javax.swing.JOptionPane.showMessageDialog(this, "You loose");
            revealSolution();
            System.exit(1);
        }
    }
    
    private void revealSolution() {
        javax.swing.JOptionPane.showMessageDialog(this, "The solution was : " + solutionString(solutions) + "!");
    }
    
    private String solutionString(int[] sol) {
        String Sol = "";
        String[] stringSol = {"Blue", "Red", "Green", "Yellow"};
        for (int i = 0; i < sol.length; i++) {
            Sol += stringSol[sol[i]] + " ";
        }
        return Sol;
    }

    private void submit() {
        if (fullTile(currentTry)) {
            algo(currentTry);
            win(currentTry);
            loose();
            currentTry++;
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "Complete all tiles");
        }
    }

    private boolean fullTile(int current) {
        boolean notTile = true;
        for (int i = 0; i < cols; i++) {
            if (gestionBoard[i][nbTry - current] == -1) {
                notTile = false;
                break;
            }
        }

        return notTile;
    }

    private void displayColor(int x, int y) {
        gestionBoard[x][y] = (gestionBoard[x][y] + 1) % 4;
        switch (gestionBoard[x][y]) {
            case 0:
                displayBoard[x][y].setBackground(Color.BLUE);
                break;
            case 1:
                displayBoard[x][y].setBackground(Color.RED);
                break;
            case 2:
                displayBoard[x][y].setBackground(Color.GREEN);
                break;
            case 3:
                displayBoard[x][y].setBackground(Color.YELLOW);
                break;
        }
        repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Game");

        jPanel1.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                jPanel1ComponentResized(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 253, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 401, Short.MAX_VALUE)
        );

        jButton1.setText("Submit");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 72, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>                        

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {                                      
        submit();
    }                                     

    private void jPanel1ComponentResized(java.awt.event.ComponentEvent evt) {                                         
        // TODO add your handling code here:
    }                                        

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
            java.util.logging.Logger.getLogger(Game.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Game.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Game.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Game.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Game(nbTry).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    // End of variables declaration                   
}
