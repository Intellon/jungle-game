package ch.intellon.junglegame;

import ch.intellon.junglegame.view.ImageLoader;

import java.awt.image.BufferedImage;

public class ResourceTest {
    public static void main(String[] args) {
        String[] paths = {
                "src/main/resources/images/panther.png",
        };

        for (String path : paths) {
            BufferedImage img = ImageLoader.loadImage(path);
            if (img == null) {
                System.err.println("Failed to load image: " + path);
            } else {
                System.out.println("Successfully loaded image: " + path);
            }
        }
    }
}