// 
// Decompiled by Procyon v0.5.36
// 

package br.ol.pacman.actor;

import java.awt.Color;
import java.awt.Graphics2D;
import br.ol.pacman.infra.Actor;
import java.awt.Rectangle;
import br.ol.pacman.PacmanGame;
import br.ol.pacman.PacmanActor;

public class Food extends PacmanActor
{
    private int col;
    private int row;
    
    public Food(final PacmanGame game, final int col, final int row) {
        super(game);
        this.col = col;
        this.row = row;
    }
    
    @Override
    public void init() {
        this.loadFrames("/res/food.png");
        this.x = this.col * 8 + 3 - 32;
        this.y = (this.row + 3) * 8 + 3;
        this.collider = new Rectangle(0, 0, 2, 2);
    }
    
    @Override
    public void updatePlaying() {
        if (((PacmanGame)this.game).checkCollision(this, Pacman.class) != null) {
            this.visible = false;
            final PacmanGame pacmanGame = (PacmanGame)this.game;
            --pacmanGame.currentFoodCount;
            ((PacmanGame)this.game).addScore(10);
        }
    }
    
    @Override
    public void draw(final Graphics2D g) {
        if (!this.visible) {
            return;
        }
        g.setColor(Color.WHITE);
        g.fillRect((int)this.x, (int)this.y, 2, 2);
    }
    
    @Override
    public void stateChanged() {
        if (((PacmanGame)this.game).getState() == PacmanGame.State.TITLE) {
            this.visible = false;
        }
        else if (((PacmanGame)this.game).getState() == PacmanGame.State.READY) {
            this.visible = true;
        }
    }
    
    public void hideAll() {
        this.visible = false;
    }
}
