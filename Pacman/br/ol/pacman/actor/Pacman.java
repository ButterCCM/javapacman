// 
// Decompiled by Procyon v0.5.36
// 

package br.ol.pacman.actor;

import br.ol.pacman.infra.Keyboard;
import java.awt.Rectangle;
import br.ol.pacman.PacmanGame;
import br.ol.pacman.PacmanActor;

public class Pacman extends PacmanActor
{
    public int col;
    public int row;
    public int desiredDirection;
    public int direction;
    public int dx;
    public int dy;
    public long diedTime;
    
    public Pacman(final PacmanGame game) {
        super(game);
    }
    
    @Override
    public void init() {
        final String[] pacmanFrameNames = new String[30];
        for (int d = 0; d < 4; ++d) {
            for (int i = 0; i < 4; ++i) {
                pacmanFrameNames[i + 4 * d] = "/res/pacman_" + d + "_" + i + ".png";
            }
        }
        for (int j = 0; j < 14; ++j) {
            pacmanFrameNames[16 + j] = "/res/pacman_died_" + j + ".png";
        }
        this.loadFrames(pacmanFrameNames);
        this.reset();
        this.collider = new Rectangle(0, 0, 8, 8);
    }
    
    private void reset() {
        this.col = 18;
        this.row = 23;
        this.updatePosition();
        this.frame = this.frames[0];
        final int n = 0;
        this.desiredDirection = n;
        this.direction = n;
    }
    
    public void updatePosition() {
        this.x = this.col * 8 - 4 - 32 - 4;
        this.y = (this.row + 3) * 8 - 4;
    }
    
    private boolean moveToTargetPosition(final int targetX, final int targetY, final int velocity) {
        final int sx = (int)(targetX - this.x);
        final int sy = (int)(targetY - this.y);
        final int vx = (Math.abs(sx) < velocity) ? Math.abs(sx) : velocity;
        final int vy = (Math.abs(sy) < velocity) ? Math.abs(sy) : velocity;
        final int idx = vx * ((sx == 0) ? 0 : ((sx > 0) ? 1 : -1));
        final int idy = vy * ((sy == 0) ? 0 : ((sy > 0) ? 1 : -1));
        this.x += idx;
        this.y += idy;
        return sx != 0 || sy != 0;
    }
    
    @Override
    public void updateTitle() {
        Label_0161: {
            Label_0132: {
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
                                if (System.currentTimeMillis() - this.waitTime < 3000L) {
                                    break Label_0161;
                                }
                                this.instructionPointer = 4;
                                break Label_0132;
                            }
                            case 4: {
                                break Label_0132;
                            }
                            default: {
                                continue;
                            }
                        }
                    }
                    if (System.currentTimeMillis() - this.waitTime < 3000L) {
                        break Label_0161;
                    }
                    this.instructionPointer = 2;
                }
                this.direction = 0;
                if (!this.moveToTargetPosition(250, 200, 1)) {
                    this.waitTime = System.currentTimeMillis();
                    this.instructionPointer = 3;
                }
                break Label_0161;
            }
            this.direction = 2;
            if (!this.moveToTargetPosition(-100, 200, 1)) {
                this.instructionPointer = 0;
            }
        }
        this.updateAnimation();
    }
    
    @Override
    public void updatePlaying() {
        if (!this.visible) {
            return;
        }
        if (Keyboard.keyPressed[37]) {
            this.desiredDirection = 2;
        }
        else if (Keyboard.keyPressed[39]) {
            this.desiredDirection = 0;
        }
        else if (Keyboard.keyPressed[38]) {
            this.desiredDirection = 3;
        }
        else if (Keyboard.keyPressed[40]) {
            this.desiredDirection = 1;
        }
        Label_0458: {
        Label_0269:
            while (true) {
                switch (this.instructionPointer) {
                    case 0: {
                        double angle = Math.toRadians(this.desiredDirection * 90);
                        this.dx = (int)Math.cos(angle);
                        this.dy = (int)Math.sin(angle);
                        if (((PacmanGame)this.game).maze[this.row + this.dy][this.col + this.dx] == 0) {
                            this.direction = this.desiredDirection;
                        }
                        angle = Math.toRadians(this.direction * 90);
                        this.dx = (int)Math.cos(angle);
                        this.dy = (int)Math.sin(angle);
                        if (((PacmanGame)this.game).maze[this.row + this.dy][this.col + this.dx] == -1) {
                            break Label_0458;
                        }
                        this.col += this.dx;
                        this.row += this.dy;
                        this.instructionPointer = 1;
                        break Label_0269;
                    }
                    case 1: {
                        break Label_0269;
                    }
                    default: {
                        continue;
                    }
                }
            }
            final int targetX = this.col * 8 - 4 - 32;
            final int targetY = (this.row + 3) * 8 - 4;
            final int difX = targetX - (int)this.x;
            final int difY = targetY - (int)this.y;
            this.x += ((difX == 0) ? 0 : ((difX > 0) ? 1 : -1));
            this.y += ((difY == 0) ? 0 : ((difY > 0) ? 1 : -1));
            if (difX == 0 && difY == 0) {
                this.instructionPointer = 0;
                if (this.col == 1) {
                    this.col = 34;
                    this.x = this.col * 8 - 4 - 24;
                }
                else if (this.col == 34) {
                    this.col = 1;
                    this.x = this.col * 8 - 4 - 24;
                }
            }
        }
        this.updateAnimation();
        if (((PacmanGame)this.game).isLevelCleared()) {
            ((PacmanGame)this.game).levelCleared();
        }
    }
    
    private void updateAnimation() {
        final int frameIndex = 4 * this.direction + (int)(System.nanoTime() * 2.0E-8) % 4;
        this.frame = this.frames[frameIndex];
    }
    
    @Override
    public void updatePacmanDied() {
        Label_0154: {
            Label_0082: {
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
                            break Label_0082;
                        }
                        case 3: {
                            if (System.currentTimeMillis() - this.waitTime < 1500L) {
                                return;
                            }
                            this.instructionPointer = 4;
                            break Label_0154;
                        }
                        case 4: {
                            break Label_0154;
                        }
                        default: {
                            continue;
                        }
                    }
                }
                if (System.currentTimeMillis() - this.waitTime < 2000L) {
                    return;
                }
                this.diedTime = System.currentTimeMillis();
                this.instructionPointer = 2;
            }
            final int frameIndex = 16 + (int)((System.currentTimeMillis() - this.diedTime) * 0.0075);
            this.frame = this.frames[frameIndex];
            if (frameIndex == 29) {
                this.waitTime = System.currentTimeMillis();
                this.instructionPointer = 3;
                return;
            }
            return;
        }
        ((PacmanGame)this.game).nextLife();
    }
    
    @Override
    public void updateCollider() {
        this.collider.setLocation((int)(this.x + 4.0), (int)(this.y + 4.0));
    }
    
    @Override
    public void stateChanged() {
        if (((PacmanGame)this.game).getState() == PacmanGame.State.TITLE) {
            this.x = -100.0;
            this.y = 200.0;
            this.instructionPointer = 0;
            this.visible = true;
        }
        else if (((PacmanGame)this.game).getState() == PacmanGame.State.READY) {
            this.visible = false;
        }
        else if (((PacmanGame)this.game).getState() == PacmanGame.State.READY2) {
            this.reset();
        }
        else if (((PacmanGame)this.game).getState() == PacmanGame.State.PLAYING) {
            this.instructionPointer = 0;
        }
        else if (((PacmanGame)this.game).getState() == PacmanGame.State.PACMAN_DIED) {
            this.instructionPointer = 0;
        }
        else if (((PacmanGame)this.game).getState() == PacmanGame.State.LEVEL_CLEARED) {
            this.frame = this.frames[0];
        }
    }
    
    public void showAll() {
        this.visible = true;
    }
    
    public void hideAll() {
        this.visible = false;
    }
}
