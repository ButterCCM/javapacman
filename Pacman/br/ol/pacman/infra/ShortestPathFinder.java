// 
// Decompiled by Procyon v0.5.36
// 

package br.ol.pacman.infra;

import java.util.ArrayList;
import java.awt.Point;
import java.util.List;

public class ShortestPathFinder
{
    public int[][] map;
    public List<Integer> path;
    private int pathIndex;
    private Point pathPosition;
    private int[] neighbors;
    
    public ShortestPathFinder(final int[][] originalMap) {
        this.path = new ArrayList<Integer>();
        this.pathPosition = new Point();
        this.neighbors = new int[] { 1, 0, -1, 0, 0, 1, 0, -1 };
        this.map = new int[originalMap.length][originalMap[0].length];
        for (int y = 0; y < this.map.length; ++y) {
            System.arraycopy(originalMap[y], 0, this.map[y], 0, this.map[0].length);
        }
    }
    
    private void clearMap() {
        for (int y = 0; y < this.map.length; ++y) {
            for (int x = 0; x < this.map[0].length; ++x) {
                if (this.map[y][x] > 0) {
                    this.map[y][x] = 0;
                }
            }
        }
    }
    
    private int getMapScore(final int x, final int y) {
        if (x < 0 || x > this.map[0].length - 1 || y < 0 || y > this.map.length - 1) {
            return -1;
        }
        return this.map[y][x];
    }
    
    public void find(final int srcX, final int srcY, final int destX, final int destY) {
        this.path.clear();
        this.clearMap();
        int score = 1;
        this.map[destY][destX] = score;
    Label_0206:
        while (true) {
            boolean foundAtLeastOne = false;
            for (int y = 0; y < this.map.length; ++y) {
                for (int x = 0; x < this.map[0].length; ++x) {
                    if (this.getMapScore(x, y) == score) {
                        foundAtLeastOne = true;
                        for (int n = 0; n < this.neighbors.length; n += 2) {
                            final int dx = x + this.neighbors[n];
                            final int dy = y + this.neighbors[n + 1];
                            if (this.getMapScore(dx, dy) == 0) {
                                this.map[dy][dx] = score + 1;
                                if (dx == srcX && dy == srcY) {
                                    this.fillPath(this.path, score + 1, dx, dy);
                                    this.pathIndex = 0;
                                    break Label_0206;
                                }
                            }
                        }
                    }
                }
            }
            if (!foundAtLeastOne) {
                break;
            }
            ++score;
        }
    }
    
    public void print() {
        for (int y = 0; y < this.map.length; ++y) {
            for (int x = 0; x < this.map[0].length; ++x) {
                String n = "000" + this.getMapScore(x, y);
                n = n.substring(n.length() - 3, n.length());
                System.out.print(String.valueOf(n) + " ");
            }
            System.out.println();
        }
    }
    
    private void fillPath(final List<Integer> path, int score, int dx, int dy) {
        int direction = 10;
        while (score > 0) {
            final int ax = (direction & 0x3) - 2;
            final int ay = (direction >> 2 & 0x3) - 2;
            direction >>= 4;
            if (this.getMapScore(dx + ax, dy + ay) == score) {
                path.add(dx += ax);
                path.add(dy += ay);
                final int k = 4 * (int)(32.0 * Math.random());
                direction = (28315 >> k | 28315 << 32 - k);
                --score;
            }
        }
    }
    
    public boolean hasNext() {
        return this.pathIndex < this.path.size() - 1;
    }
    
    public Point getNext() {
        if (!this.hasNext()) {
            return null;
        }
        this.pathPosition.setLocation(this.path.get(this.pathIndex), this.path.get(this.pathIndex + 1));
        this.pathIndex += 2;
        return this.pathPosition;
    }
}
