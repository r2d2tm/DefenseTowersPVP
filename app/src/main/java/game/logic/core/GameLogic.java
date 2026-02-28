package Game.Object;

import Game.Common.GameConfig;
import Rendering.Renderable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameLogic {

    private int nbPlayer = 333;

    // Liste de tous les objets qui doivent être dessinés
    private List<Renderable> renderables = new ArrayList<>();
    List<PlayerTest> playerTests = new ArrayList<PlayerTest>();

    public GameLogic(){

        BackgroundMap bg = new BackgroundMap("background");
        renderables.add(bg);

        PlayerTest p;
        for (int i = 0; i < nbPlayer/3; i++) {

            p = new PlayerTest("warrior");
            playerTests.add(p);
            renderables.add(p);
            setRandomDestination(p);

            p = new PlayerTest("scout");
            playerTests.add(p);
            renderables.add(p);
            setRandomDestination(p);

            p = new PlayerTest("tank");
            playerTests.add(p);
            renderables.add(p);
            setRandomDestination(p);
        }
    }

    public void update() {

        for (int i = 0; i < nbPlayer; i++) {
            PlayerTest p = playerTests.get(i);
            boolean isArrived = p.forwardToDestination();
            if( isArrived)
            {
                setRandomDestination(p);
            }
        }
    }

    public void setRandomDestination(PlayerTest p)
    {
        Random rand = new Random();

        // Génère un nombre entre 0.0 et GameConfig.WORLD_W (100.0)
        double randomX = p.getWidth()/2 + rand.nextDouble() * (GameConfig.WORLD_WIDTH_METERS-p.getWidth());

        // Génère un nombre entre 0.0 et GameConfig.WORLD_H (56.25)
        double randomY = p.getHeight()/2 + rand.nextDouble() * (GameConfig.WORLD_HEIGHT_METERS-p.getHeight());
        p.setDestination(randomX, randomY);
    }

    public PlayerTest getPlayerTest(int index) {
        return playerTests.get(index);
    }

    public int getNbPlayer() {
        return nbPlayer;
    }

    public void setNbPlayer(int nbPlayer) {
        this.nbPlayer = nbPlayer;
    }

    public List<Renderable> getRenderables() {
        return renderables;
    }
}
