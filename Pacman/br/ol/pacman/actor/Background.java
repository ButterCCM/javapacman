// 
// Decompiled by Procyon v0.5.36
// 

package br.ol.pacman.actor;

import java.awt.Graphics2D;
import br.ol.pacman.PacmanGame;
import java.awt.Color;
import br.ol.pacman.PacmanActor;

public class Background extends PacmanActor
{
    private boolean showBlockedCellColor;
    private Color blockedCellColor;
    private int frameCount;
    
    public Background(final PacmanGame game) {
        super(game);
        this.showBlockedCellColor = false;
        this.blockedCellColor = new Color(255, 0, 0, 128);
    }
    
    @Override
    public void init() {
        this.loadFrames("/res/background_0.png", "/res/background_1.png");
    }
    
    @Override
    public void updateLevelCleared() {
    Label_0214:
        while (true) {
            switch (this.instructionPointer) {
                case 0: {
                    this.frameCount = 0;
                    this.waitTime = System.currentTimeMillis();
                    this.instructionPointer = 1;
                }
                case 1: {
                    if (System.currentTimeMillis() - this.waitTime < 1500L) {
                        return;
                    }
                    this.instructionPointer = 2;
                }
                case 2: {
                    this.frame = this.frames[1];
                    this.waitTime = System.currentTimeMillis();
                    this.instructionPointer = 3;
                }
                case 3: {
                    if (System.currentTimeMillis() - this.waitTime < 200L) {
                        return;
                    }
                    this.frame = this.frames[0];
                    this.waitTime = System.currentTimeMillis();
                    this.instructionPointer = 4;
                }
                case 4: {
                    if (System.currentTimeMillis() - this.waitTime < 200L) {
                        return;
                    }
                    ++this.frameCount;
                    if (this.frameCount < 5) {
                        this.instructionPointer = 2;
                        continue;
                    }
                    ((PacmanGame)this.game).broadcastMessage("hideAll");
                    this.waitTime = System.currentTimeMillis();
                    this.instructionPointer = 5;
                    break Label_0214;
                }
                case 5: {
                    break Label_0214;
                }
                default: {
                    continue;
                }
            }
        }
        if (System.currentTimeMillis() - this.waitTime >= 500L) {
            this.visible = true;
            ((PacmanGame)this.game).nextLevel();
        }
    }
    
    @Override
    public void draw(final Graphics2D g) {
        super.draw(g);
        if (this.showBlockedCellColor) {
            g.setColor(this.blockedCellColor);
            for (int row = 0; row < 31; ++row) {
                for (int col = 0; col < 36; ++col) {
                    if (((PacmanGame)this.game).maze[row][col] == 1) {
                        g.fillRect(col * 8 - 32, (row + 3) * 8, 8, 8);
                    }
                }
            }
        }
    }
    
    @Override
    public void stateChanged() {
        if (((PacmanGame)this.game).getState() == PacmanGame.State.TITLE) {
            this.visible = false;
        }
        else if (((PacmanGame)this.game).getState() == PacmanGame.State.READY) {
            this.visible = true;
        }
        else if (((PacmanGame)this.game).getState() == PacmanGame.State.LEVEL_CLEARED) {
            this.instructionPointer = 0;
        }
    }
    
    public void hideAll() {
        this.visible = false;
    }
}
