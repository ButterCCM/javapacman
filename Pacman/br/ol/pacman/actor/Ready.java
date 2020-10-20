// 
// Decompiled by Procyon v0.5.36
// 

package br.ol.pacman.actor;

import br.ol.pacman.PacmanGame;
import br.ol.pacman.PacmanActor;

public class Ready extends PacmanActor
{
    public Ready(final PacmanGame game) {
        super(game);
    }
    
    @Override
    public void init() {
        this.x = 88.0;
        this.y = 160.0;
        this.loadFrames("/res/ready.png");
    }
    
    @Override
    public void updateReady() {
    Label_0050:
        while (true) {
            switch (this.instructionPointer) {
                case 0: {
                    ((PacmanGame)this.game).restoreCurrentFoodCount();
                    this.waitTime = System.currentTimeMillis();
                    this.instructionPointer = 1;
                }
                case 1: {
                    break Label_0050;
                }
                default: {
                    continue;
                }
            }
        }
        if (System.currentTimeMillis() - this.waitTime >= 2000L) {
            ((PacmanGame)this.game).setState(PacmanGame.State.READY2);
        }
    }
    
    @Override
    public void updateReady2() {
    Label_0052:
        while (true) {
            switch (this.instructionPointer) {
                case 0: {
                    ((PacmanGame)this.game).broadcastMessage("showAll");
                    this.waitTime = System.currentTimeMillis();
                    this.instructionPointer = 1;
                }
                case 1: {
                    break Label_0052;
                }
                default: {
                    continue;
                }
            }
        }
        if (System.currentTimeMillis() - this.waitTime >= 2000L) {
            ((PacmanGame)this.game).setState(PacmanGame.State.PLAYING);
        }
    }
    
    @Override
    public void stateChanged() {
        this.visible = false;
        if (((PacmanGame)this.game).getState() == PacmanGame.State.READY || ((PacmanGame)this.game).getState() == PacmanGame.State.READY2) {
            this.visible = true;
            this.instructionPointer = 0;
        }
    }
}
