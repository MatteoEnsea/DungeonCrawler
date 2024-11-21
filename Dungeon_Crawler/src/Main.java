import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Main implements GameLevelManager {

    public static Level levelManager;
    private static GameState gameState = GameState.TitleScreen;

    public static void setGameState(GameState State) {
        gameState = State;
    }

    JFrame displayZoneFrame;

    RenderEngine renderEngine;
    GameEngine gameEngine;
    PhysicEngine physicEngine;


    public Main() throws Exception {

        displayZoneFrame = new JFrame("Java Labs");
        displayZoneFrame.setSize(1920, 1080);
        displayZoneFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        DynamicSprite hero = new DynamicSprite(ImageIO.read(new File("./img/heroTileSheetLowRes.png")),
                200, 300, 48, 50);

        renderEngine = new RenderEngine();
        physicEngine = new PhysicEngine();
        gameEngine = new GameEngine(hero, this);
        levelManager = new Level();

        physicEngine.setRenderEngine(renderEngine);
        physicEngine.setGameEngine(gameEngine);

        Timer renderTimer = new Timer(50, (time) -> renderEngine.update(gameState));
        Timer gameTimer = new Timer(50, (time) -> gameEngine.update(gameState));
        Timer physicTimer = new Timer(50, (time) -> physicEngine.update(gameState));

        renderTimer.start();
        gameTimer.start();
        physicTimer.start();

        displayZoneFrame.getContentPane().add(renderEngine);
        displayZoneFrame.setVisible(true);

        Playground level = levelManager.loadCurrentLevel();
        renderEngine.addToRenderList(level.getSpriteList());
        renderEngine.addToRenderList(hero);
        physicEngine.addToMovingSpriteList(hero);
        physicEngine.setEnvironment(level.getSolidSpriteList());
        physicEngine.setCurrentPlayground(level);
        renderEngine.setHero(hero);

        displayZoneFrame.addKeyListener(gameEngine);


    }



    public static void main(String[] args) throws Exception {
        Main main = new Main();
    }

    // Methode pour charger le premier niveau apres le game over
    @Override
    public void loadLevel(int levelIndex) throws Exception {
        Level.setCurrentLevelIndex(levelIndex);
        Playground level = levelManager.loadCurrentLevel();


        renderEngine.clearRenderList();
        renderEngine.addToRenderList(level.getSpriteList());
        renderEngine.addToRenderList(gameEngine.hero);


        physicEngine.setEnvironment(level.getSolidSpriteList());
        physicEngine.setCurrentPlayground(level);

        gameEngine.hero.setPosition(200, 300);
    }

    // Méthode pour reset le level apres un game over
    @Override
    public void resetLevel() throws Exception {
        loadLevel(0);
    }
}

