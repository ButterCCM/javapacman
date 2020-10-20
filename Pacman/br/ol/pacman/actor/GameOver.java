// 
// Decompiled by Procyon v0.5.36
// 

package br.ol.pacman.actor;

import br.ol.pacman.PacmanGame;
import br.ol.pacman.PacmanActor;

public class GameOver extends PacmanActor
{
    public GameOver(final PacmanGame game) {
        super(game);
    }
    
    @Override
    public void init() {
        this.x = 77.0;
        this.y = 160.0;
        this.loadFrames("/res/gameover.png");
    }
    
    @Override
    public void updateGameOver() {
    Label_0040:
        while (true) {
            switch (this.instructionPointer) {
                case 0: {
                    this.waitTime = System.currentTimeMillis();
                    this.instructionPointer = 1;
                }
                case 1: {
                    break Label_0040;
                }
                default: {
                    continue;
                }
            }
        }
        if (System.currentTimeMillis() - this.waitTime >= 3000L) {
            ((PacmanGame)this.game).returnToTitle();
        }
    }
    
    @Override
    public void stateChanged() {
        this.visible = false;
        if (((PacmanGame)this.game).state == PacmanGame.State.GAME_OVER) {
            this.visible = true;
            this.instructionPointer = 0;
        }
    }
}
