package game.rendering;

import static game.shared.Constants.*; // permet d'utiliser les membre de Constants sans mettre le nom de classe
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import game.logic.core.Logic;

public class Renderer2D implements GameRenderer {
    // Le dictionnaire qui stocke les images
    private static final Map<String, BufferedImage> sprites = new HashMap<>();
    private Logic Logic;

    public Renderer2D()
    {
        LoadSprites();
    }
    @Override
    public void setLogic(Logic Logic) {
        this.Logic = Logic;
    }

    private void LoadSprites()    {
        loadSprite("background","app/src/main/resources/assets/background.png");
        loadSprite("player","app/src/main/resources/assets/player.png");
    }

    public static void loadSprite(String name, String filePath) {
        try {
            BufferedImage img = ImageIO.read(new File(filePath));
            sprites.put(name, img);
            System.out.println("Sprite chargé : " + name);
        } catch (Exception e) {
            System.err.println("Impossible de charger le sprite : " + filePath);
            e.printStackTrace();
        }
    }

    public static BufferedImage getSprite(String name) {
        return sprites.get(name);
    }

    public void render(Graphics2D g) {

        // On récupère une liste générique (ex: Logic.getEntities())
        for (Renderable entity : Logic.getRenderables()) {
            drawEntity(g, entity);
        }
    }

    public void drawEntity(Graphics2D g, Renderable entity)
    {
        // 2. Calcul des facteurs d'échelle (Pixels par Mètre)
        // Monde = 100m x 56.25m
        double scaleX = WORLD_WIDTH_PIXEL / WORLD_WIDTH_METERS;
        double scaleY = WORLD_HEIGHT_PIXEL / WORLD_HEIGHT_METERS;

        BufferedImage sprite = getSprite(entity.getSpriteName());

        if (sprite != null) {
            // 1. Calcul de la taille en pixels
            int pWidth = (int) (entity.getWidth() * scaleX);
            int pHeight = (int) (entity.getHeight() * scaleY);

            // 2. Conversion de la position avec décalage pour centrer l'image par rapport au poind donné
            // On calcule le pixel du centre, puis on retire la moitié de la taille du sprite
            double HalfX = entity.getWidth()/2.0;
            double HalfY = entity.getHeight() /2.0;
            int px = (int) ((entity.getPosX()-HalfX) * scaleX);// on recentre car le point donné n'est pas le point haut gauche mais le centre
            int py = (int) ((entity.getPosY()-HalfY) * scaleY);

            // 3. Rendu dans le buffer
            g.drawImage(sprite, px, py, pWidth, pHeight, null);
        }
    }
}
