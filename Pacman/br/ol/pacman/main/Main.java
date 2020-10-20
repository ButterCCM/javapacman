// 
// Decompiled by Procyon v0.5.36
// 

package main;

import javax.swing.SwingUtilities;
import br.ol.pacman.infra.Game;
import java.awt.Component;
import javax.swing.JFrame;
import br.ol.pacman.infra.Display;
import br.ol.pacman.PacmanGame;

public class Main
{
    public static void main(final String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                final Game game = new PacmanGame();
                final Display view = new Display(game);
                final JFrame frame = new JFrame();
                frame.setTitle("Pacman");
                frame.setDefaultCloseOperation(3);
                frame.getContentPane().add(view);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                view.requestFocus();
                view.start();
            }
        });
    }
}
