package Rendering;

import Game.Object.GameLogic;
import Game.Object.PlayerTest;

import java.awt.*;

public interface GameRenderer {

    void render(Graphics2D g);

    void setGameLogic(GameLogic gameLogic);
}
