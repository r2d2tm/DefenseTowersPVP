package Game.Object;

import Game.Common.GameConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.File;
import Rendering.Renderable;

public class PlayerTest implements Renderable {
    private String Name = "";
    private  double posX, posY; // Utiliser double pour plus de précision en 3D plus tard
    private double height, width; // dimensions en metre du joueur
    public double speed = 5.0; // 5m/s
    public double destX,destY;

    // Getters pour que le moteur de rendu puisse lire la position
    public double getPosX() {
        return posX;
    }
    public double getPosY() {
        return posY;
    }

    public PlayerTest(String name){
        LoadInitConfig(name);
    }

    private void LoadInitConfig(String configName) {
        try {
            // Lecture du fichier JSON situé dans le dossier Configs
            File configFile = new File("Configs/PlayerTest.json");

            // Utilisation d'une bibliothèque comme Jackson (ObjectMapper)
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(configFile);
            JsonNode playersNode = rootNode.path("players");

            // Recherche du joueur par son nom
            for (JsonNode node : playersNode) {
                if (node.path("name").asText().equals(configName)) {
                    this.posX = node.path("posX").asDouble();
                    this.posY = node.path("posY").asDouble();
                    this.width = node.path("width").asDouble();
                    this.height = node.path("height").asDouble();
                    this.speed = node.path("speed").asDouble();

                    // On initialise la destination sur la position de départ par défaut
                    this.destX = this.posX;
                    this.destY = this.posY;

                    System.out.println("Configuration chargée pour : " + configName);
                    break;
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la lecture du fichier de config JSON");
            e.printStackTrace();
        }
    }

    public void setDestination(double x, double y) {
        destX = x;
        destY = y;

        //System.out.println("x="+x + "  y=" + y);
    }

    public boolean forwardToDestination() {
        // à la vitesse de speed , quelle distance parcourt-on ?  produit en croix
        // 5m = 1 sec  donc en 16ms je parcours 5*16 / 1000  note 60fps = 16ms pour chaque frame

        double disanceParcourue = speed * GameConfig.DELAY_BETWEEN_FRAMES / 1000;



        // Je suis a un point x1,y1 et je vais vers un point x2,y2.
        // On peut représenter un triangle rectange entre les deux point où la disance parcourue est l'hypotenuse
        double triangleSideX = destX - posX;
        double triangleSideY = destY - posY;
        double triangleHypothenuse = Math.hypot(triangleSideX, triangleSideY);

        // Sécurité : si on est déjà sur place ou presque (évite division par zéro)
        if (triangleHypothenuse < 0.001) {
            posX = destX;
            posY = destY;
            return true;
        }

        if( disanceParcourue<triangleHypothenuse ) { // on n'a pas dépassé notre cible
            // à cette vitesse on ne peut parcourir qu'une fraction de l'hypothénuse
            double ratio = disanceParcourue / triangleHypothenuse;
            posX += triangleSideX * ratio; // on applique le nouveau posX
            posY += triangleSideY * ratio; // on applique le nouveau Y
            return  false; // pas arrivé à destination
        }
        else {
            posX = destX;
            posY = destY;
            return true; // arrivé à destination
        }
    }

    // getter setters
    public double getHeight() {
        return height;
    }

    @Override
    public String getSpriteName() {
        return "player";
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }
}