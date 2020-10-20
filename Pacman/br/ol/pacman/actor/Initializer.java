// 
// Decompiled by Procyon v0.5.36
// 

package br.ol.pacman.actor;

import br.ol.pacman.PacmanGame;
import br.ol.pacman.PacmanActor;

public class Initializer extends PacmanActor
{
    public Initializer(final PacmanGame game) {
        super(game);
    }
    
    @Override
    public void updateInitializing() {
        Label_0067: {
            Label_0044: {
            Label_0032:
                while (true) {
                    switch (this.instructionPointer) {
                        case 0: {
                            break Label_0032;
                        }
                        case 1: {
                            break Label_0044;
                        }
                        case 2: {
                            break Label_0067;
                        }
                        default: {
                            continue;
                        }
                    }
                }
                this.waitTime = System.currentTimeMillis();
                this.instructionPointer = 1;
            }
            if (System.currentTimeMillis() - this.waitTime < 3000L) {
                return;
            }
            this.instructionPointer = 2;
        }
        ((PacmanGame)this.game).setState(PacmanGame.State.OL_PRESENTS);
    }
}
