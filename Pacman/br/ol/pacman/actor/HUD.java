// 
// Decompiled by Procyon v0.5.36
// 

package br.ol.pacman.actor;

import java.awt.image.ImageObserver;
import java.awt.Image;
import java.awt.Graphics2D;
import br.ol.pacman.PacmanGame;
import br.ol.pacman.PacmanActor;

public class HUD extends PacmanActor
{
    public HUD(final PacmanGame game) {
        super(game);
    }
    
    @Override
    public void init() {
        this.loadFrames("/res/pacman_life.png");
    }
    
    @Override
    public void draw(final Graphics2D g) {
        if (!this.visible) {
            return;
        }
        ((PacmanGame)this.game).drawText(g, "SCORE", 10, 1);
        ((PacmanGame)this.game).drawText(g, ((PacmanGame)this.game).getScore(), 10, 10);
        ((PacmanGame)this.game).drawText(g, "HIGH SCORE ", 78, 1);
        ((PacmanGame)this.game).drawText(g, ((PacmanGame)this.game).getHiscore(), 90, 10);
        ((PacmanGame)this.game).drawText(g, "LIVES: ", 10, 274);
        for (int lives = 0; lives < ((PacmanGame)this.game).lives; ++lives) {
            g.drawImage(this.frame, 60 + 20 * lives, 272, null);
        }
    }
    
    @Override
    public void stateChanged() {
        this.visible = (((PacmanGame)this.game).state != PacmanGame.State.INITIALIZING && ((PacmanGame)this.game).state != PacmanGame.State.OL_PRESENTS);
    }
}
