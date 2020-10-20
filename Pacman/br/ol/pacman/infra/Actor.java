// 
// Decompiled by Procyon v0.5.36
// 

package br.ol.pacman.infra;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import java.awt.image.ImageObserver;
import java.awt.Image;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Actor<T extends Game>
{
    public static final boolean DRAW_COLLIDER = false;
    public T game;
    public double x;
    public double y;
    public boolean visible;
    public BufferedImage frame;
    public BufferedImage[] frames;
    public Rectangle collider;
    protected int instructionPointer;
    protected long waitTime;
    
    public Actor(final T game) {
        this.game = game;
    }
    
    public void init() {
    }
    
    public void update() {
    }
    
    public void draw(final Graphics2D g) {
        if (!this.visible) {
            return;
        }
        if (this.frame != null) {
            g.drawImage(this.frame, (int)this.x, (int)this.y, this.frame.getWidth(), this.frame.getHeight(), null);
        }
    }
    
    protected void loadFrames(final String... framesRes) {
        try {
            this.frames = new BufferedImage[framesRes.length];
            for (int i = 0; i < framesRes.length; ++i) {
                final String frameRes = framesRes[i];
                this.frames[i] = ImageIO.read(this.getClass().getResourceAsStream(frameRes));
            }
            this.frame = this.frames[0];
        }
        catch (IOException ex) {
            Logger.getLogger(Actor.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(-1);
        }
    }
    
    public void updateCollider() {
        if (this.collider != null) {
            this.collider.setLocation((int)this.x, (int)this.y);
        }
    }
}
