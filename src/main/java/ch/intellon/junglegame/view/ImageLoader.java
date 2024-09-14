package ch.intellon.junglegame.view;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageLoader {
    public static BufferedImage loadImage(String filePath) {
        try {
            System.out.println("Versuche, Bild von Datei zu laden: " + filePath);
            File imageFile = new File(filePath);
            if (!imageFile.exists()) {
                System.err.println("Datei nicht gefunden: " + filePath);
                return null;
            }
            BufferedImage image = ImageIO.read(imageFile);
            if (image == null) {
                System.err.println("Bild konnte nicht geladen werden: " + filePath);
            } else {
                System.out.println("Bild erfolgreich geladen: " + filePath);
            }
            return image;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}