// 
// Decompiled by Procyon v0.5.36
// 

package br.ol.pacman.actor;

import java.awt.Graphics2D;
import br.ol.pacman.PacmanGame;
import br.ol.pacman.PacmanActor;

public class OLPresents extends PacmanActor
{
    private String text;
    private int textIndex;
    
    public OLPresents(final PacmanGame game) {
        super(game);
        this.text = "C.C.M. PRESENTS";
    }
    
    @Override
    public void updateOLPresents() {
        Label_0145: {
            Label_0110: {
                Label_0048: {
                Label_0036:
                    while (true) {
                        switch (this.instructionPointer) {
                            case 0: {
                                break Label_0036;
                            }
                            case 1: {
                                break Label_0048;
                            }
                            case 2: {
                                break Label_0110;
                            }
                            case 3: {
                                break Label_0145;
                            }
                            default: {
                                continue;
                            }
                        }
                    }
                    this.waitTime = System.currentTimeMillis();
                    this.instructionPointer = 1;
                }
                if (System.currentTimeMillis() - this.waitTime < 100L) {
                    return;
                }
                ++this.textIndex;
                if (this.textIndex < this.text.length()) {
                    this.instructionPointer = 0;
                    return;
                }
                this.waitTime = System.currentTimeMillis();
                this.instructionPointer = 2;
            }
            if (System.currentTimeMillis() - this.waitTime < 3000L) {
                return;
            }
            this.visible = false;
            this.waitTime = System.currentTimeMillis();
            this.instructionPointer = 3;
        }
        if (System.currentTimeMillis() - this.waitTime >= 1500L) {
            ((PacmanGame)this.game).setState(PacmanGame.State.TITLE);
        }
    }
    
    @Override
    public void draw(final Graphics2D g) {
        if (!this.visible) {
            return;
        }
        ((PacmanGame)this.game).drawText(g, this.text.substring(0, this.textIndex), 60, 130);
    }
    
    @Override
    public void stateChanged() {
        this.visible = false;
        if (((PacmanGame)this.game).state == PacmanGame.State.OL_PRESENTS) {
            this.visible = true;
            this.textIndex = 0;
        }
    }
}
