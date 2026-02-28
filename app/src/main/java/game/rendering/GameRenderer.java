package game.rendering;


import game.logic.core.Logic;
import java.awt.*;

public interface GameRenderer {

    void render(Graphics2D g);

    void setLogic(Logic Logic);
}
