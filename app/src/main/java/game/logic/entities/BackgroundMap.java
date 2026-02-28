package Game.Object;

import Game.Common.GameConfig;
import Rendering.Renderable;

public class BackgroundMap implements Renderable {

    private double posX=0;
    private double posY=0;
    private double width=0;
    public double height=0;
    private String spriteName="background";

    public BackgroundMap(String spriteName){
        this.spriteName = spriteName;
        this.posX = GameConfig.WORLD_WIDTH_METERS/2;// on place les images en fonction de leur centre
        this.posY= GameConfig.WORLD_HEIGHT_METERS/2;
        this.width = GameConfig.WORLD_WIDTH_METERS;
        this.height = GameConfig.WORLD_HEIGHT_METERS;
    }

    @Override
    public double getPosX() { return posX; }

    @Override
    public double getPosY() {
        return posY;
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double getHeight() {
        return height;
    }

    @Override
    public String getSpriteName() {
        return spriteName;
    }
}
