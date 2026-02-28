// inspiré de  - https://stackoverflow.com/a/1963684
// on sépare la couche Logic du rendering
// la gameloop et le render sont appelés ici


package game.rendering;

import static game.shared.Constants.*; // permet d'utiliser les membre de Constants sans mettre le nom de classe



import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import game.logic.core.Logic;

public class GameWindow extends JFrame implements Runnable {
    private boolean isRunning = true;
    private Canvas canvas;
    private BufferStrategy strategy;
    private BufferedImage bufferedFullImage;
    private Graphics2D gfx;
    private Graphics2D graphics;
    private GameRenderer currentRenderer;

    private Logic Logic;

    private GraphicsConfiguration config =
            GraphicsEnvironment.getLocalGraphicsEnvironment()
                    .getDefaultScreenDevice()
                    .getDefaultConfiguration();


    // create a hardware accelerated image
    public final BufferedImage create(final int width, final int height,
                                      final boolean alpha) {
        return config.createCompatibleImage(width, height, alpha
                ? Transparency.TRANSLUCENT : Transparency.OPAQUE);
    }

    // Setup
    public GameWindow() {
        Logic = new Logic();
        // définition du renderer (2D ou Isometric)
        currentRenderer = new Renderer2D();
        currentRenderer.setLogic(Logic);// pour permettre au renderer de trouver les objet à afficher

        // JFrame initialisation
        setTitle("Towers PvP");
        addWindowListener(new FrameClose());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
       // setSize( WORLD_WIDTH_PIXEL,WORLD_HEIGHT_PIXEL);




        // Canvas
        canvas = new Canvas(config);
        canvas.setSize( WORLD_WIDTH_PIXEL,WORLD_HEIGHT_PIXEL);
        add(canvas, 0); // add canvas to frame

        pack();
        setResizable(false);
        setVisible(true);

        // Background & Buffer
        bufferedFullImage = create( WORLD_WIDTH_PIXEL,WORLD_HEIGHT_PIXEL, false);
        canvas.createBufferStrategy(2);
        do {
            strategy = canvas.getBufferStrategy();
        } while (strategy == null);//attente que le buffer strategy est bien initialisé

        System.out.println("Le jeu est initialisé !");
        Thread threadGame = new Thread(this);
        threadGame.start();
    }

    private class FrameClose extends WindowAdapter {
        @Override
        public void windowClosing(final WindowEvent e) {
            isRunning = false;
        }
    }

    // Screen and buffer stuff
    private Graphics2D getBuffer() {
        if (graphics == null) {
            try {
                graphics = (Graphics2D) strategy.getDrawGraphics();
            } catch (IllegalStateException e) {
                return null;
            }
        }
        return graphics;
    }

    private boolean updateScreen() {
        graphics.dispose();
        graphics = null;
        try {
            strategy.show();
            Toolkit.getDefaultToolkit().sync();
            return (!strategy.contentsLost());

        } catch (NullPointerException e) {
            return true;

        } catch (IllegalStateException e) {
            return true;
        }
    }

    public void run() {
        gfx = (Graphics2D) bufferedFullImage.getGraphics();
        System.out.println("start game loop");
        
        //on nomme la boucle mainLoop pour utiliser le nom dans le break
        mainLoop:
        while (isRunning) {
            long renderStart = System.nanoTime();

            // Update game Logic
            Logic.update();

            // Update Graphics
            do {
                Graphics2D graphicBuffer = getBuffer();
                if (!isRunning) {
                    break mainLoop;
                }
                currentRenderer.render(gfx); // c'est ici qu'on render nos objets
                
                //dessin de l'image
                graphicBuffer.drawImage(bufferedFullImage, 0, 0, null);
                graphicBuffer.dispose();

            } while (!updateScreen());

            // Better do some FPS limiting here
            long renderTimeMs = (System.nanoTime() - renderStart) / 1000000;
            try {
                Thread.sleep(Math.max(0, DELAY_BETWEEN_FRAMES - renderTimeMs));
            } catch (InterruptedException e) {
                Thread.interrupted();
                break;
            }
            renderTimeMs = (System.nanoTime() - renderStart) / 1000000;

        }
        dispose(); // dispose frame
    }


}
