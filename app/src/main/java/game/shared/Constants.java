package game.logic.common;

public class Constants {

    public static final long FPS_MAX = 60;
    public static final long DELAY_BETWEEN_FRAMES = (long) (1.0 / FPS_MAX * 1000); // en millisec
    public static final int WORLD_WIDTH_PIXEL = 1280;
    public static final int WORLD_HEIGHT_PIXEL = 720;
    public static final double WORLD_WIDTH_METERS = 100.0;
    public static final double WORLD_HEIGHT_METERS = WORLD_HEIGHT_PIXEL * WORLD_WIDTH_METERS / WORLD_WIDTH_PIXEL;


}
