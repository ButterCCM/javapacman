// 
// Decompiled by Procyon v0.5.36
// 

package br.ol.pacman;

import br.ol.pacman.infra.Actor;

public class PacmanActor extends Actor<PacmanGame>
{
    public PacmanActor(final PacmanGame game) {
        super(game);
    }
    
    @Override
    public void update() {
        switch (((PacmanGame)this.game).getState()) {
            case INITIALIZING: {
                this.updateInitializing();
                break;
            }
            case OL_PRESENTS: {
                this.updateOLPresents();
                break;
            }
            case TITLE: {
                this.updateTitle();
                break;
            }
            case READY: {
                this.updateReady();
                break;
            }
            case READY2: {
                this.updateReady2();
                break;
            }
            case PLAYING: {
                this.updatePlaying();
                break;
            }
            case PACMAN_DIED: {
                this.updatePacmanDied();
                break;
            }
            case GHOST_CATCHED: {
                this.updateGhostCatched();
                break;
            }
            case LEVEL_CLEARED: {
                this.updateLevelCleared();
                break;
            }
            case GAME_OVER: {
                this.updateGameOver();
                break;
            }
        }
    }
    
    public void updateInitializing() {
    }
    
    public void updateOLPresents() {
    }
    
    public void updateTitle() {
    }
    
    public void updateReady() {
    }
    
    public void updateReady2() {
    }
    
    public void updatePlaying() {
    }
    
    public void updatePacmanDied() {
    }
    
    public void updateGhostCatched() {
    }
    
    public void updateLevelCleared() {
    }
    
    public void updateGameOver() {
    }
    
    public void stateChanged() {
    }
}
