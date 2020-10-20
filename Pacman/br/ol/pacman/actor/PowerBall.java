// 
// Decompiled by Procyon v0.5.36
// 

package br.ol.pacman.actor;

import br.ol.pacman.infra.Actor;
import java.awt.Rectangle;
import br.ol.pacman.PacmanGame;
import br.ol.pacman.PacmanActor;

public class PowerBall extends PacmanActor
{
    private int col;
    private int row;
    private boolean eated;
    
    public PowerBall(final PacmanGame game, final int col, final int row) {
        super(game);
        this.col = col;
        this.row = row;
    }
    
    @Override
    public void init() {
        this.loadFrames("/res/powerBall.png");
        this.x = this.col * 8 + 1 - 32;
        this.y = (this.row + 3) * 8 + 1;
        this.collider = new Rectangle(0, 0, 4, 4);
        this.eated = true;
    }
    
    @Override
    public void update() {
        this.visible = (!this.eated && (int)(System.nanoTime() * 7.5E-9) % 2 == 0);
        if (this.eated || ((PacmanGame)this.game).getState() == PacmanGame.State.PACMAN_DIED) {
            return;
        }
        if (((PacmanGame)this.game).checkCollision(this, Pacman.class) != null) {
            this.eated = true;
            this.visible = false;
            ((PacmanGame)this.game).addScore(50);
            ((PacmanGame)this.game).startGhostVulnerableMode();
        }
    }
    
    @Override
    public void stateChanged() {
        if (((PacmanGame)this.game).getState() == PacmanGame.State.TITLE || ((PacmanGame)this.game).getState() == PacmanGame.State.LEVEL_CLEARED || ((PacmanGame)this.game).getState() == PacmanGame.State.GAME_OVER) {
            this.eated = true;
        }
        else if (((PacmanGame)this.game).getState() == PacmanGame.State.READY) {
            this.eated = false;
            this.visible = true;
        }
    }
    
    public void hideAll() {
        this.visible = false;
    }
}
