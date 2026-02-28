package game.logic.core;

// permet d'utiliser les membre de Constants sans mettre le nom de classe
import static game.shared.Constants.WORLD_HEIGHT_METERS;
import static game.shared.Constants.WORLD_WIDTH_METERS;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import game.logic.entities.BackgroundMap;
import game.logic.entities.PlayerPoc;
import game.rendering.Renderable;

public class Logic {

    private int nbPlayer = 333;

    // Liste de tous les objets qui doivent être dessinés
    private List<Renderable> renderables = new ArrayList<>();
    List<PlayerPoc> playerTests = new ArrayList<PlayerPoc>();

    public Logic(){

        BackgroundMap bg = new BackgroundMap("background");
        renderables.add(bg);

        PlayerPoc p;
        for (int i = 0; i < nbPlayer/3; i++) {

            p = new PlayerPoc("warrior");
            playerTests.add(p);
            renderables.add(p);
            setRandomDestination(p);

            p = new PlayerPoc("scout");
            playerTests.add(p);
            renderables.add(p);
            setRandomDestination(p);

            p = new PlayerPoc("tank");
            playerTests.add(p);
            renderables.add(p);
            setRandomDestination(p);
        }
    }

    public void update() {

        for (int i = 0; i < nbPlayer; i++) {
            PlayerPoc p = playerTests.get(i);
            boolean isArrived = p.forwardToDestination();
            if( isArrived)
            {
                setRandomDestination(p);
            }
        }
    }

    public void setRandomDestination(PlayerPoc p)
    {
        Random rand = new Random();

        // Génère un nombre entre 0.0 et WORLD_W (100.0)
        double randomX = p.getWidth()/2 + rand.nextDouble() * (WORLD_WIDTH_METERS-p.getWidth());

        // Génère un nombre entre 0.0 et WORLD_H (56.25)
        double randomY = p.getHeight()/2 + rand.nextDouble() * (WORLD_HEIGHT_METERS-p.getHeight());
        p.setDestination(randomX, randomY);
    }

    public PlayerPoc getPlayerTest(int index) {
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
