// 
// Decompiled by Procyon v0.5.36
// 

package br.ol.pacman.infra;

import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

public class Keyboard extends KeyAdapter
{
    public static boolean[] keyPressed;
    
    static {
        Keyboard.keyPressed = new boolean[256];
    }
    
    @Override
    public void keyPressed(final KeyEvent e) {
        Keyboard.keyPressed[e.getKeyCode()] = true;
    }
    
    @Override
    public void keyReleased(final KeyEvent e) {
        Keyboard.keyPressed[e.getKeyCode()] = false;
    }
}
