// 
// Decompiled by Procyon v0.5.36
// 

package br.ol.pacman.actor;

import java.awt.Graphics2D;
import br.ol.pacman.infra.Keyboard;
import br.ol.pacman.PacmanGame;
import br.ol.pacman.PacmanActor;

public class Title extends PacmanActor
{
    private boolean pushSpaceToStartVisible;
    
    public Title(final PacmanGame game) {
        super(game);
    }
    
    @Override
    public void init() {
        this.loadFrames("/res/title.png");
        this.x = 21.0;
        this.y = 100.0;
    }
    
    @Override
    public void updateTitle() {
        Label_0145: {
            Label_0075: {
            Label_0052:
                while (true) {
                    switch (this.instructionPointer) {
                        case 0: {
                            this.waitTime = System.currentTimeMillis();
                            this.instructionPointer = 1;
                        }
                        case 1: {
                            break Label_0052;
                        }
                        case 2: {
                            break Label_0075;
                        }
                        case 3: {
                            if (System.currentTimeMillis() - this.waitTime < 200L) {
                                return;
                            }
                            this.instructionPointer = 4;
                            break Label_0145;
                        }
                        case 4: {
                            break Label_0145;
                        }
                        default: {
                            continue;
                        }
                    }
                }
                if (System.currentTimeMillis() - this.waitTime < 500L) {
                    return;
                }
                this.instructionPointer = 2;
            }
            final double dy = 100.0 - this.y;
            this.y += dy * 0.1;
            if (Math.abs(dy) < 1.0) {
                this.waitTime = System.currentTimeMillis();
                this.instructionPointer = 3;
                return;
            }
            return;
        }
        this.pushSpaceToStartVisible = ((int)(System.nanoTime() * 7.5E-9) % 3 > 0);
        if (Keyboard.keyPressed[32]) {
            ((PacmanGame)this.game).startGame();
        }
    }
    
    @Override
    public void draw(final Graphics2D g) {
        if (!this.visible) {
            return;
        }
        super.draw(g);
        if (this.pushSpaceToStartVisible) {
            ((PacmanGame)this.game).drawText(g, "PUSH SPACE TO START", 37, 170);
        }
        ((PacmanGame)this.game).drawText(g, "PROGRAMMED BY C.C.M. 2018", 20, 240);
        ((PacmanGame)this.game).drawText(g, "ORIGINAL GAME BY NAMCO 1980", 5, 255);
    }
    
    @Override
    public void stateChanged() {
        this.visible = false;
        if (((PacmanGame)this.game).state == PacmanGame.State.TITLE) {
            this.y = -150.0;
            this.visible = true;
            this.pushSpaceToStartVisible = false;
            this.instructionPointer = 0;
        }
    }
}
