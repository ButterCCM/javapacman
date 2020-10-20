// 
// Decompiled by Procyon v0.5.36
// 

package br.ol.pacman.actor;

import java.awt.Rectangle;
import java.util.ArrayList;
import br.ol.pacman.PacmanGame;
import br.ol.pacman.infra.ShortestPathFinder;
import java.util.List;
import java.awt.Point;
import br.ol.pacman.PacmanActor;

public class Ghost extends PacmanActor
{
    public Pacman pacman;
    public int type;
    public Point[] initialPositions;
    public int cageUpDownCount;
    public Mode mode;
    public int dx;
    public int dy;
    public int col;
    public int row;
    public int direction;
    public int lastDirection;
    public List<Integer> desiredDirections;
    public int desiredDirection;
    public static final int[] backwardDirections;
    public long vulnerableModeStartTime;
    public boolean markAsVulnerable;
    public ShortestPathFinder pathFinder;
    private PacmanCatchedAction pacmanCatchedAction;
    private GhostCatchedAction ghostCatchedAction;
    
    static {
        backwardDirections = new int[] { 2, 3, 0, 1 };
    }
    
    public Ghost(final PacmanGame game, final Pacman pacman, final int type) {
        super(game);
        this.initialPositions = new Point[] { new Point(18, 11), new Point(16, 14), new Point(18, 14), new Point(20, 14) };
        this.mode = Mode.CAGE;
        this.direction = 0;
        this.desiredDirections = new ArrayList<Integer>();
        this.pacmanCatchedAction = new PacmanCatchedAction((PacmanCatchedAction)null);
        this.ghostCatchedAction = new GhostCatchedAction((GhostCatchedAction)null);
        this.pacman = pacman;
        this.type = type;
        this.pathFinder = new ShortestPathFinder(game.maze);
    }
    
    private void setMode(final Mode mode) {
        this.mode = mode;
        this.modeChanged();
    }
    
    @Override
    public void init() {
        final String[] ghostFrameNames = new String[16];
        for (int i = 0; i < 8; ++i) {
            ghostFrameNames[i] = "/res/ghost_" + this.type + "_" + i + ".png";
        }
        for (int i = 0; i < 4; ++i) {
            ghostFrameNames[8 + i] = "/res/ghost_vulnerable_" + i + ".png";
        }
        for (int i = 0; i < 4; ++i) {
            ghostFrameNames[12 + i] = "/res/ghost_died_" + i + ".png";
        }
        this.loadFrames(ghostFrameNames);
        this.collider = new Rectangle(0, 0, 8, 8);
        this.setMode(Mode.CAGE);
    }
    
    private int getTargetX(final int col) {
        return col * 8 - 3 - 32;
    }
    
    private int getTargetY(final int row) {
        return (row + 3) * 8 - 2;
    }
    
    public void updatePosition() {
        this.x = this.getTargetX(this.col);
        this.y = this.getTargetY(this.row);
    }
    
    private void updatePosition(final int col, final int row) {
        this.col = col;
        this.row = row;
        this.updatePosition();
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
    
    private boolean moveToGridPosition(final int col, final int row, final int velocity) {
        final int targetX = this.getTargetX(col);
        final int targetY = this.getTargetY(row);
        return this.moveToTargetPosition(targetX, targetY, velocity);
    }
    
    private void adjustHorizontalOutsideMovement() {
        if (this.col == 1) {
            this.col = 34;
            this.x = this.getTargetX(this.col);
        }
        else if (this.col == 34) {
            this.col = 1;
            this.x = this.getTargetX(this.col);
        }
    }
    
    @Override
    public void updateTitle() {
        int frameIndex = 0;
        this.x = this.pacman.x + 17.0 + 17 * this.type;
        this.y = 200.0;
        if (this.pacman.direction == 0) {
            frameIndex = 8 + (int)(System.nanoTime() * 1.0E-8) % 2;
        }
        else if (this.pacman.direction == 2) {
            frameIndex = 2 * this.pacman.direction + (int)(System.nanoTime() * 1.0E-8) % 2;
        }
        this.frame = this.frames[frameIndex];
    }
    
    @Override
    public void updatePlaying() {
        switch (this.mode) {
            case CAGE: {
                this.updateGhostCage();
                break;
            }
            case NORMAL: {
                this.updateGhostNormal();
                break;
            }
            case VULNERABLE: {
                this.updateGhostVulnerable();
                break;
            }
            case DIED: {
                this.updateGhostDied();
                break;
            }
        }
        this.updateAnimation();
    }
    
    public void updateAnimation() {
        int frameIndex = 0;
        switch (this.mode) {
            case CAGE:
            case NORMAL: {
                frameIndex = 2 * this.direction + (int)(System.nanoTime() * 1.0E-8) % 2;
                if (!this.markAsVulnerable) {
                    break;
                }
            }
            case VULNERABLE: {
                if (System.currentTimeMillis() - this.vulnerableModeStartTime > 5000L) {
                    frameIndex = 8 + (int)(System.nanoTime() * 2.0E-8) % 4;
                    break;
                }
                frameIndex = 8 + (int)(System.nanoTime() * 1.0E-8) % 2;
                break;
            }
            case DIED: {
                frameIndex = 12 + this.direction;
                break;
            }
        }
        this.frame = this.frames[frameIndex];
    }
    
    private void updateGhostCage() {
    Label_0378:
        while (true) {
            switch (this.instructionPointer) {
                case 0: {
                    final Point initialPosition = this.initialPositions[this.type];
                    this.updatePosition(initialPosition.x, initialPosition.y);
                    this.x -= 4.0;
                    this.cageUpDownCount = 0;
                    if (this.type == 0) {
                        this.instructionPointer = 6;
                        continue;
                    }
                    if (this.type == 2) {
                        this.instructionPointer = 2;
                        continue;
                    }
                    this.instructionPointer = 1;
                }
                case 1: {
                    if (this.moveToTargetPosition((int)this.x, 138, 1)) {
                        return;
                    }
                    this.instructionPointer = 2;
                }
                case 2: {
                    if (this.moveToTargetPosition((int)this.x, 130, 1)) {
                        return;
                    }
                    ++this.cageUpDownCount;
                    if (this.cageUpDownCount <= this.type * 2) {
                        this.instructionPointer = 1;
                        return;
                    }
                    this.instructionPointer = 3;
                }
                case 3: {
                    if (this.moveToTargetPosition((int)this.x, 134, 1)) {
                        return;
                    }
                    this.instructionPointer = 4;
                }
                case 4: {
                    if (this.moveToTargetPosition(105, 134, 1)) {
                        return;
                    }
                    this.instructionPointer = 5;
                }
                case 5: {
                    if (this.moveToTargetPosition(105, 110, 1)) {
                        return;
                    }
                    if ((int)(2.0 * Math.random()) == 0) {
                        this.instructionPointer = 7;
                        continue;
                    }
                    this.instructionPointer = 6;
                }
                case 6: {
                    if (this.moveToTargetPosition(109, 110, 1)) {
                        return;
                    }
                    this.desiredDirection = 0;
                    this.lastDirection = 0;
                    this.updatePosition(18, 11);
                    this.instructionPointer = 8;
                    continue;
                }
                case 7: {
                    if (this.moveToTargetPosition(101, 110, 1)) {
                        return;
                    }
                    this.desiredDirection = 2;
                    this.lastDirection = 2;
                    this.updatePosition(17, 11);
                    this.instructionPointer = 8;
                    break Label_0378;
                }
                case 8: {
                    break Label_0378;
                }
                default: {
                    continue;
                }
            }
        }
        this.setMode(Mode.NORMAL);
    }
    
    private void updateGhostNormal() {
        if (this.checkVulnerableModeTime() && this.markAsVulnerable) {
            this.setMode(Mode.VULNERABLE);
            this.markAsVulnerable = false;
        }
        if (this.type == 0 || this.type == 1) {
            this.updateGhostMovement(true, this.pacman.col, this.pacman.row, 1, this.pacmanCatchedAction, 0, 1, 2, 3);
        }
        else {
            this.updateGhostMovement(false, 0, 0, 1, this.pacmanCatchedAction, 0, 1, 2, 3);
        }
    }
    
    private void updateGhostVulnerable() {
        if (this.markAsVulnerable) {
            this.markAsVulnerable = false;
        }
        this.updateGhostMovement(true, this.pacman.col, this.pacman.row, 1, this.ghostCatchedAction, 2, 3, 0, 1);
        if (!this.checkVulnerableModeTime()) {
            this.setMode(Mode.NORMAL);
        }
    }
    
    private boolean checkVulnerableModeTime() {
        return System.currentTimeMillis() - this.vulnerableModeStartTime <= 8000L;
    }
    
    private void updateGhostDied() {
    Label_0233:
        while (true) {
            switch (this.instructionPointer) {
                case 0: {
                    this.pathFinder.find(this.col, this.row, 18, 11);
                    this.instructionPointer = 1;
                }
                case 1: {
                    if (!this.pathFinder.hasNext()) {
                        this.instructionPointer = 3;
                        continue;
                    }
                    final Point nextPosition = this.pathFinder.getNext();
                    this.col = nextPosition.x;
                    this.row = nextPosition.y;
                    this.instructionPointer = 2;
                }
                case 2: {
                    if (this.moveToGridPosition(this.col, this.row, 4)) {
                        break Label_0233;
                    }
                    if (this.row == 11 && (this.col == 17 || this.col == 18)) {
                        this.instructionPointer = 3;
                        continue;
                    }
                    this.instructionPointer = 1;
                    continue;
                }
                case 3: {
                    if (!this.moveToTargetPosition(105, 110, 4)) {
                        this.instructionPointer = 4;
                        continue;
                    }
                    break Label_0233;
                }
                case 4: {
                    if (!this.moveToTargetPosition(105, 134, 4)) {
                        this.instructionPointer = 5;
                        continue;
                    }
                    break Label_0233;
                }
                case 5: {
                    this.setMode(Mode.CAGE);
                    this.instructionPointer = 4;
                    break Label_0233;
                }
                default: {
                    continue;
                }
            }
        }
    }
    
    private void updateGhostMovement(final boolean useTarget, final int targetCol, final int targetRow, final int velocity, final Runnable collisionWithPacmanAction, final int... desiredDirectionsMap) {
        this.desiredDirections.clear();
        if (useTarget) {
            if (targetCol - this.col > 0) {
                this.desiredDirections.add(desiredDirectionsMap[0]);
            }
            else if (targetCol - this.col < 0) {
                this.desiredDirections.add(desiredDirectionsMap[2]);
            }
            if (targetRow - this.row > 0) {
                this.desiredDirections.add(desiredDirectionsMap[1]);
            }
            else if (targetRow - this.row < 0) {
                this.desiredDirections.add(desiredDirectionsMap[3]);
            }
        }
        if (this.desiredDirections.size() > 0) {
            final int selectedChaseDirection = (int)(this.desiredDirections.size() * Math.random());
            this.desiredDirection = this.desiredDirections.get(selectedChaseDirection);
        }
    Label_0475:
        while (true) {
            switch (this.instructionPointer) {
                case 0: {
                    if ((this.row == 14 && this.col == 1 && this.lastDirection == 2) || (this.row == 14 && this.col == 34 && this.lastDirection == 0)) {
                        this.adjustHorizontalOutsideMovement();
                    }
                    double angle = Math.toRadians(this.desiredDirection * 90);
                    this.dx = (int)Math.cos(angle);
                    this.dy = (int)Math.sin(angle);
                    if (useTarget && ((PacmanGame)this.game).maze[this.row + this.dy][this.col + this.dx] == 0 && this.desiredDirection != Ghost.backwardDirections[this.lastDirection]) {
                        this.direction = this.desiredDirection;
                    }
                    else {
                        do {
                            this.direction = (int)(4.0 * Math.random());
                            angle = Math.toRadians(this.direction * 90);
                            this.dx = (int)Math.cos(angle);
                            this.dy = (int)Math.sin(angle);
                        } while (((PacmanGame)this.game).maze[this.row + this.dy][this.col + this.dx] == -1 || this.direction == Ghost.backwardDirections[this.lastDirection]);
                    }
                    this.col += this.dx;
                    this.row += this.dy;
                    this.instructionPointer = 1;
                }
                case 1: {
                    break Label_0475;
                }
                default: {
                    continue;
                }
            }
        }
        if (!this.moveToGridPosition(this.col, this.row, velocity)) {
            this.lastDirection = this.direction;
            this.instructionPointer = 0;
        }
        if (collisionWithPacmanAction != null && this.checkCollisionWithPacman()) {
            collisionWithPacmanAction.run();
        }
    }
    
    @Override
    public void updateGhostCatched() {
        if (this.mode == Mode.DIED) {
            this.updateGhostDied();
            this.updateAnimation();
        }
    }
    
    @Override
    public void updatePacmanDied() {
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
        if (System.currentTimeMillis() - this.waitTime >= 1500L) {
            this.visible = false;
            this.setMode(Mode.CAGE);
            this.updateAnimation();
        }
        this.updateAnimation();
    }
    
    @Override
    public void updateLevelCleared() {
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
                        return;
                    }
                    default: {
                        continue;
                    }
                }
            }
            this.waitTime = System.currentTimeMillis();
            this.instructionPointer = 1;
        }
        if (System.currentTimeMillis() - this.waitTime >= 1500L) {
            this.visible = false;
            this.setMode(Mode.CAGE);
            this.updateAnimation();
            this.instructionPointer = 2;
        }
    }
    
    private boolean checkCollisionWithPacman() {
        this.pacman.updateCollider();
        this.updateCollider();
        return this.pacman.collider.intersects(this.collider);
    }
    
    @Override
    public void updateCollider() {
        this.collider.setLocation((int)(this.x + 4.0), (int)(this.y + 4.0));
    }
    
    private void modeChanged() {
        this.instructionPointer = 0;
    }
    
    @Override
    public void stateChanged() {
        if (((PacmanGame)this.game).getState() == PacmanGame.State.TITLE) {
            this.updateTitle();
            this.visible = true;
        }
        else if (((PacmanGame)this.game).getState() == PacmanGame.State.READY) {
            this.visible = false;
        }
        else if (((PacmanGame)this.game).getState() == PacmanGame.State.READY2) {
            this.setMode(Mode.CAGE);
            this.updateAnimation();
            final Point initialPosition = this.initialPositions[this.type];
            this.updatePosition(initialPosition.x, initialPosition.y);
            this.x -= 4.0;
        }
        else if (((PacmanGame)this.game).getState() == PacmanGame.State.PLAYING && this.mode != Mode.CAGE) {
            this.instructionPointer = 0;
        }
        else if (((PacmanGame)this.game).getState() == PacmanGame.State.PACMAN_DIED) {
            this.instructionPointer = 0;
        }
        else if (((PacmanGame)this.game).getState() == PacmanGame.State.LEVEL_CLEARED) {
            this.instructionPointer = 0;
        }
    }
    
    public void showAll() {
        this.visible = true;
    }
    
    public void hideAll() {
        this.visible = false;
    }
    
    public void startGhostVulnerableMode() {
        this.vulnerableModeStartTime = System.currentTimeMillis();
        this.markAsVulnerable = true;
    }
    
    public void died() {
        this.setMode(Mode.DIED);
    }
    
    public enum Mode
    {
        CAGE("CAGE", 0), 
        NORMAL("NORMAL", 1), 
        VULNERABLE("VULNERABLE", 2), 
        DIED("DIED", 3);
        
        private Mode(final String name, final int ordinal) {
        }
    }
    
    private class PacmanCatchedAction implements Runnable
    {
        @Override
        public void run() {
            ((PacmanGame)Ghost.this.game).setState(PacmanGame.State.PACMAN_DIED);
        }
    }
    
    private class GhostCatchedAction implements Runnable
    {
        @Override
        public void run() {
            ((PacmanGame)Ghost.this.game).ghostCatched(Ghost.this);
        }
    }
}
