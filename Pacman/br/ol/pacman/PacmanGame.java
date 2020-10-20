// 
// Decompiled by Procyon v0.5.36
// 

package br.ol.pacman;

import java.util.Iterator;
import br.ol.pacman.infra.Actor;
import br.ol.pacman.actor.HUD;
import br.ol.pacman.actor.GameOver;
import br.ol.pacman.actor.Ready;
import br.ol.pacman.actor.Point;
import br.ol.pacman.actor.PowerBall;
import br.ol.pacman.actor.Food;
import br.ol.pacman.actor.Background;
import br.ol.pacman.actor.Title;
import br.ol.pacman.actor.OLPresents;
import br.ol.pacman.actor.Initializer;
import br.ol.pacman.actor.Pacman;
import java.awt.geom.Point2D;
import java.awt.Dimension;
import br.ol.pacman.actor.Ghost;
import br.ol.pacman.infra.Game;

public class PacmanGame extends Game
{
    public int[][] maze;
    public State state;
    public int lives;
    public int score;
    public int hiscore;
    public Ghost catchedGhost;
    public int currentCatchedGhostScoreTableIndex;
    public final int[] catchedGhostScoreTable;
    public int foodCount;
    public int currentFoodCount;
    
    public PacmanGame() {
        this.maze = new int[][] { { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1, 3, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 3, 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, { 1, 0, 0, 0, 2, 2, 2, 2, 2, 2, 2, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 2, 2, 2, 2, 2, 2, 2, 0, 0, 0, 1 }, { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1, 3, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 3, 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 } };
        this.state = State.INITIALIZING;
        this.lives = 3;
        this.currentCatchedGhostScoreTableIndex = 0;
        this.catchedGhostScoreTable = new int[] { 200, 400, 800, 1600 };
        this.screenSize = new Dimension(224, 288);
        this.screenScale = new Point2D.Double(2.0, 2.0);
    }
    
    public State getState() {
        return this.state;
    }
    
    public void setState(final State state) {
        if (this.state != state) {
            this.state = state;
            this.broadcastMessage("stateChanged");
        }
    }
    
    public void addScore(final int point) {
        this.score += point;
        if (this.score > this.hiscore) {
            this.hiscore = this.score;
        }
    }
    
    public String getScore() {
        String scoreStr = "0000000" + this.score;
        scoreStr = scoreStr.substring(scoreStr.length() - 7, scoreStr.length());
        return scoreStr;
    }
    
    public String getHiscore() {
        String hiscoreStr = "0000000" + this.hiscore;
        hiscoreStr = hiscoreStr.substring(hiscoreStr.length() - 7, hiscoreStr.length());
        return hiscoreStr;
    }
    
    @Override
    public void init() {
        this.addAllObjs();
        this.initAllObjs();
    }
    
    private void addAllObjs() {
        final Pacman pacman = new Pacman(this);
        this.actors.add(new Initializer(this));
        this.actors.add(new OLPresents(this));
        this.actors.add(new Title(this));
        this.actors.add(new Background(this));
        this.foodCount = 0;
        for (int row = 0; row < 31; ++row) {
            for (int col = 0; col < 36; ++col) {
                if (this.maze[row][col] == 1) {
                    this.maze[row][col] = -1;
                }
                else if (this.maze[row][col] == 2) {
                    this.maze[row][col] = 0;
                    this.actors.add(new Food(this, col, row));
                    ++this.foodCount;
                }
                else if (this.maze[row][col] == 3) {
                    this.maze[row][col] = 0;
                    this.actors.add(new PowerBall(this, col, row));
                }
            }
        }
        for (int i = 0; i < 4; ++i) {
            this.actors.add(new Ghost(this, pacman, i));
        }
        this.actors.add(pacman);
        this.actors.add(new Point(this, pacman));
        this.actors.add(new Ready(this));
        this.actors.add(new GameOver(this));
        this.actors.add(new HUD(this));
    }
    
    private void initAllObjs() {
        for (final Actor actor : this.actors) {
            actor.init();
        }
    }
    
    public void restoreCurrentFoodCount() {
        this.currentFoodCount = this.foodCount;
    }
    
    public boolean isLevelCleared() {
        return this.currentFoodCount == 0;
    }
    
    public void startGame() {
        this.setState(State.READY);
    }
    
    public void startGhostVulnerableMode() {
        this.currentCatchedGhostScoreTableIndex = 0;
        this.broadcastMessage("startGhostVulnerableMode");
    }
    
    public void ghostCatched(final Ghost ghost) {
        this.catchedGhost = ghost;
        this.setState(State.GHOST_CATCHED);
    }
    
    public void nextLife() {
        --this.lives;
        if (this.lives == 0) {
            this.setState(State.GAME_OVER);
        }
        else {
            this.setState(State.READY2);
        }
    }
    
    public void levelCleared() {
        this.setState(State.LEVEL_CLEARED);
    }
    
    public void nextLevel() {
        this.setState(State.READY);
    }
    
    public void returnToTitle() {
        this.lives = 3;
        this.score = 0;
        this.setState(State.TITLE);
    }
    
    public enum State
    {
        INITIALIZING("INITIALIZING", 0), 
        OL_PRESENTS("OL_PRESENTS", 1), 
        TITLE("TITLE", 2), 
        READY("READY", 3), 
        READY2("READY2", 4), 
        PLAYING("PLAYING", 5), 
        PACMAN_DIED("PACMAN_DIED", 6), 
        GHOST_CATCHED("GHOST_CATCHED", 7), 
        LEVEL_CLEARED("LEVEL_CLEARED", 8), 
        GAME_OVER("GAME_OVER", 9);
        
        private State(final String name, final int ordinal) {
        }
    }
}
