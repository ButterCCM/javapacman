// 
// Decompiled by Procyon v0.5.36
// 

package br.ol.pacman.infra;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class BitmapFontRenderer
{
    public BufferedImage bitmapFontImage;
    public BufferedImage[] letters;
    public int letterWidth;
    public int letterHeight;
    public int letterVerticalSpacing;
    public int letterHorizontalSpacing;
    
    public BitmapFontRenderer(final String fontRes, final int cols, final int rows) {
        this.letterVerticalSpacing = 0;
        this.letterHorizontalSpacing = 0;
        this.loadFont(fontRes, cols, rows);
    }
    
    public void drawText(final Graphics2D g, final String text, final int x, final int y) {
        if (this.letters == null) {
            return;
        }
        int px = 0;
        int py = 0;
        for (int i = 0; i < text.length(); ++i) {
            final int c = text.charAt(i);
            if (c == 10) {
                py += this.letterHeight + this.letterVerticalSpacing;
                px = 0;
            }
            else if (c != 13) {
                final Image letter = this.letters[c];
                g.drawImage(letter, px + x, py + y + 1, null);
                px += this.letterWidth + this.letterHorizontalSpacing;
            }
        }
    }
    
    private void loadFont(final String filename, final Integer cols, final Integer rows) {
        try {
            this.loadFont(this.bitmapFontImage = ImageIO.read(this.getClass().getResourceAsStream(filename)), cols, rows);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    
    private void loadFont(final BufferedImage image, final Integer cols, final Integer rows) {
        final int lettersCount = cols * rows;
        this.bitmapFontImage = image;
        this.letters = new BufferedImage[lettersCount];
        this.letterWidth = this.bitmapFontImage.getWidth() / cols;
        this.letterHeight = this.bitmapFontImage.getHeight() / rows;
        for (int y = 0; y < rows; ++y) {
            for (int x = 0; x < cols; ++x) {
                this.letters[y * cols + x] = new BufferedImage(this.letterWidth, this.letterHeight, 2);
                final Graphics2D ig = (Graphics2D)this.letters[y * cols + x].getGraphics();
                ig.drawImage(this.bitmapFontImage, 0, 0, this.letterWidth, this.letterHeight, x * this.letterWidth, y * this.letterHeight, x * this.letterWidth + this.letterWidth, y * this.letterHeight + this.letterHeight, null);
            }
        }
    }
}
