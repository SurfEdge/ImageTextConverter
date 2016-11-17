package imagetextconverter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Font;

/**
 *
 * @author RamithaA
 */
public class ImageTextConverter {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        
        printNameToImage("Chamath Palihawadana", new File("template.jpg"));
 
    }
    
    private static void printNameToImage (String fullName, File templateImageFile) throws IOException{
        
        final BufferedImage image = ImageIO.read(templateImageFile);
        Graphics g = image.getGraphics();
        FontRenderContext frc = new FontRenderContext(null, true, true);
        
        // The border dimensions of the complete String
        Rectangle r = new Rectangle(image.getWidth(), image.getHeight());        
        Font font = g.getFont().deriveFont(115f);      
        Rectangle2D r2D = font.getStringBounds(fullName, frc);
        int rWidth = (int) Math.round(r2D.getWidth());
        int rHeight = (int) Math.round(r2D.getHeight());
        int rX = (int) Math.round(r2D.getX());
        int rY = (int) Math.round(r2D.getY());

        // Common characteristics
        g.setColor(Color.black);
        g.setFont(font);

        if (rWidth < (image.getWidth() - 200)) {
            // Process if the name can be added in one line
            int a = (r.width / 2) - (rWidth / 2) - rX;
            int b = ((r.height / 2) - (rHeight / 2) - rY - 50);
            
            g.drawString(fullName, r.x + a, r.y + b);
        } else {
            // Process if the name cannot be added in one line
            String[] parts = fullName.split(" ");
            String part1 = parts[0];
            String part2 = parts[1];
            fullName = part1 + "\n" + part2;

            // Border dimensions of String part1
            Rectangle2D rectForStrPart1 = font.getStringBounds(part1, frc);
            int rWidthForStr1 = (int) Math.round(rectForStrPart1.getWidth());
            int rXForStr1 = (int) Math.round(rectForStrPart1.getX());

            // Border dimensions of String part2
            Rectangle2D rectForStrPart2 = font.getStringBounds(part2, frc);
            int rWidthForStr2 = (int) Math.round(rectForStrPart2.getWidth());
            int rXForStr2 = (int) Math.round(rectForStrPart2.getX());

            // Location for string part 1
            int a1 = (r.width / 2) - (rWidthForStr1 / 2) - rXForStr1;
            int b1 = ((r.height / 2) - (rHeight / 2) - rY - 140);

            // Location for string part 2
            int a2 = (r.width / 2) - (rWidthForStr2 / 2) - rXForStr2;
            int b2 = ((r.height / 2) - (rHeight / 2) - rY - 20);

            g.drawString(part1, r.x + a1, r.y + b1);
            g.drawString(part2, r.x + a2, r.y + b2);
        }
        g.dispose();
        ImageIO.write(image, "jpg", new File("test.jpg"));
    }

}
