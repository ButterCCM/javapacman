// 
// Decompiled by Procyon v0.5.36
// 

package br.ol.pacman.infra;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyListener;
import java.awt.Dimension;
import java.awt.image.BufferStrategy;
import java.awt.Canvas;

public class Display extends Canvas
{
    private Game game;
    private boolean running;
    private BufferStrategy bs;
    
    public Display(final Game game) {
        this.game = game;
        final int sx = (int)(game.screenSize.width * game.screenScale.getX());
        final int sy = (int)(game.screenSize.height * game.screenScale.getY());
        this.setPreferredSize(new Dimension(sx, sy));
        this.addKeyListener(new Keyboard());
    }
    
    public void start() {
        if (this.running) {
            return;
        }
        this.createBufferStrategy(3);
        this.bs = this.getBufferStrategy();
        this.game.init();
        this.running = true;
        final Thread thread = new Thread(new MainLoop((MainLoop)null));
        thread.start();
    }
    
    public void update() {
        this.game.update();
    }
    
    public void draw(final Graphics2D g) {
        this.game.draw(g);
    }
    
    private class MainLoop implements Runnable
    {
        @Override
        public void run() {
            final long desiredFrameRateTime = 16L;
            long currentTime = System.currentTimeMillis();
            long lastTime = currentTime - desiredFrameRateTime;
            long unprocessedTime = 0L;
            boolean needsRender = false;
            while (Display.this.running) {
                currentTime = System.currentTimeMillis();
                unprocessedTime += currentTime - lastTime;
                lastTime = currentTime;
                while (unprocessedTime >= desiredFrameRateTime) {
                    unprocessedTime -= desiredFrameRateTime;
                    Display.this.update();
                    needsRender = true;
                }
                if (needsRender) {
                    final Graphics2D g = (Graphics2D)Display.this.bs.getDrawGraphics();
                    g.setBackground(Color.BLACK);
                    g.clearRect(0, 0, Display.this.getWidth(), Display.this.getHeight());
                    g.scale(Display.this.game.screenScale.getX(), Display.this.game.screenScale.getY());
                    Display.this.draw(g);
                    g.dispose();
                    Display.this.bs.show();
                    needsRender = false;
                }
                else {
                    try {
                        Thread.sleep(1L);
                    }
                    catch (InterruptedException ex) {}
                }
            }
        }
    }
}
