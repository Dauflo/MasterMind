/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mastermind;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/**
 *
 * @author alois
 */
public class ClueGraphic extends JPanel {

    int[] clues;

    public ClueGraphic() {
        clues = new int[Game.cols];
        for (int i = 0; i < clues.length; i++) {
            clues[i] = -1;
        }
    }

    public void redraw() {
        repaint();
    }

    public void addClues(int[] newClues) {
        clues = newClues;
        System.out.println("After");
        for (int i = 0; i < clues.length; i++) {
            System.out.print(clues[i] + " ");
        }System.out.println("");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.

        Graphics2D g2d = (Graphics2D) g;

        //Background
        g2d.setStroke(new BasicStroke(1));
        g2d.setColor(Color.GRAY);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        //Clues
        for (int i = 0; i < clues.length; i++) {
            switch (clues[i]) {
                case -1:
                    g2d.setColor(Color.GRAY);
                    break;
                case 0:
                    g2d.setColor(Color.GRAY);
                    break;
                case 1:
                    g2d.setColor(Color.BLACK);
                    break;
                case 2:
                    g2d.setColor(Color.WHITE);
                    break;
            }

            g2d.fillOval(i * (getWidth() / 4), getHeight() / 2, 10, 10);
        }
    }

}
