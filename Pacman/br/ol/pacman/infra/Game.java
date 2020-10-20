// 
// Decompiled by Procyon v0.5.36
// 

package br.ol.pacman.infra;

import java.lang.reflect.Method;
import java.awt.Graphics2D;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.awt.geom.Point2D;
import java.awt.Dimension;

public class Game
{
    public Dimension screenSize;
    public Point2D screenScale;
    public List<Actor> actors;
    public BitmapFontRenderer bitmapFontRenderer;
    
    public Game() {
        this.actors = new ArrayList<Actor>();
        this.bitmapFontRenderer = new BitmapFontRenderer("/res/font8x8.png", 16, 16);
    }
    
    public void init() {
    }
    
    public void update() {
        for (final Actor actor : this.actors) {
            actor.update();
        }
    }
    
    public void draw(final Graphics2D g) {
        for (final Actor actor : this.actors) {
            actor.draw(g);
        }
    }
    
    public <T> T checkCollision(final Actor a1, final Class<T> type) {
        a1.updateCollider();
        for (final Actor a2 : this.actors) {
            a2.updateCollider();
            if (a1 != a2 && type.isInstance(a2) && a1.collider != null && a2.collider != null && a1.visible && a2.visible && a2.collider.intersects(a1.collider)) {
                return type.cast(a2);
            }
        }
        return null;
    }
    
    public void broadcastMessage(final String message) {
        for (final Actor obj : this.actors) {
            try {
                final Method method = obj.getClass().getMethod(message, (Class<?>[])new Class[0]);
                if (method == null) {
                    continue;
                }
                method.invoke(obj, new Object[0]);
            }
            catch (Exception ex) {}
        }
    }
    
    public void drawText(final Graphics2D g, final String text, final int x, final int y) {
        this.bitmapFontRenderer.drawText(g, text, x, y);
    }
}
