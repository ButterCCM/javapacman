// 
// Decompiled by Procyon v0.5.36
// 

package br.ol.pacman.actor;

import java.awt.Rectangle;
import br.ol.pacman.PacmanGame;
import br.ol.pacman.PacmanActor;

public class Point extends PacmanActor
{
    private Pacman pacman;
    
    public Point(final PacmanGame game, final Pacman pacman) {
        super(game);
        this.pacman = pacman;
    }
    
    @Override
    public void init() {
        this.loadFrames("/res/point_0.png", "/res/point_1.png", "/res/point_2.png", "/res/point_3.png");
        this.collider = new Rectangle(0, 0, 4, 4);
    }
    
    private void updatePosition(final int col, final int row) {
        this.x = col * 8 - 4 - 32;
        this.y = (row + 3) * 8 + 1;
    }
    
    @Override
    public void updateGhostCatched() {
    Label_0151:
        while (true) {
            switch (this.instructionPointer) {
                case 0: {
                    this.updatePosition(((PacmanGame)this.game).catchedGhost.col, ((PacmanGame)this.game).catchedGhost.row);
                    this.pacman.visible = false;
                    ((PacmanGame)this.game).catchedGhost.visible = false;
                    final int frameIndex = ((PacmanGame)this.game).currentCatchedGhostScoreTableIndex;
                    this.frame = this.frames[frameIndex];
                    ((PacmanGame)this.game).addScore(((PacmanGame)this.game).catchedGhostScoreTable[frameIndex]);
                    final PacmanGame pacmanGame = (PacmanGame)this.game;
                    ++pacmanGame.currentCatchedGhostScoreTableIndex;
                    this.waitTime = System.currentTimeMillis();
                    this.instructionPointer = 1;
                }
                case 1: {
                    break Label_0151;
                }
                default: {
                    continue;
                }
            }
        }
        if (System.currentTimeMillis() - this.waitTime >= 500L) {
            this.pacman.visible = true;
            this.pacman.updatePosition();
            ((PacmanGame)this.game).catchedGhost.visible = true;
            ((PacmanGame)this.game).catchedGhost.updatePosition();
            ((PacmanGame)this.game).catchedGhost.died();
            ((PacmanGame)this.game).setState(PacmanGame.State.PLAYING);
        }
    }
    
    @Override
    public void stateChanged() {
        this.visible = false;
        if (((PacmanGame)this.game).getState() == PacmanGame.State.GHOST_CATCHED) {
            this.visible = true;
            this.instructionPointer = 0;
        }
    }
    
    public void hideAll() {
        this.visible = false;
    }
}
