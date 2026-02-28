package Rendering;

import Game.Common.GameConfig;
import Game.Object.GameLogic;
import Game.Object.PlayerTest;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Renderer2D implements GameRenderer {
    // Le dictionnaire qui stocke les images
    private static final Map<String, BufferedImage> sprites = new HashMap<>();
    private GameLogic gameLogic;

    public Renderer2D()
    {
        LoadSprites();
    }
    @Override
    public void setGameLogic(GameLogic gameLogic) {
        this.gameLogic = gameLogic;
    }

    private void LoadSprites()    {
        loadSprite("background","ressources/background.png");
        loadSprite("player","ressources/player.png");
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

        // On récupère une liste générique (ex: gameLogic.getEntities())
        for (Renderable entity : gameLogic.getRenderables()) {
            drawEntity(g, entity);
        }
    }

    public void drawEntity(Graphics2D g, Renderable entity)
    {
        // 2. Calcul des facteurs d'échelle (Pixels par Mètre)
        // Monde = 100m x 56.25m
        double scaleX = GameConfig.WORLD_WIDTH_PIXEL / GameConfig.WORLD_WIDTH_METERS;
        double scaleY = GameConfig.WORLD_HEIGHT_PIXEL / GameConfig.WORLD_HEIGHT_METERS;

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
