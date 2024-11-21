import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Main implements GameLevelManager {

    public static Level levelManager;
    private static GameState gameState = GameState.TitleScreen;
    public static ArrayList<AttackPattern> attackPatterns;

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


        renderEngine = new RenderEngine();

        Character hero = new Character(ImageIO.read(new File("./img/heroTileSheetLowRes.png")),
                200, 300, 48, 50);
        AttackPattern sword = new AttackPattern(ImageIO.read(new File("./img/sword_swipe.png")),
                200,300,69,45, renderEngine); sword.isWalking = false;


        Character enemy = new Character(ImageIO.read(new File("./img/enemyTileSheetLowRes.png")),
                200, 300, 48, 50);
        AttackPattern claw = new AttackPattern(ImageIO.read(new File("./img/claw_swipe.png")),
                200,300,69,45, renderEngine); claw.isWalking = false;

        attackPatterns = new ArrayList<AttackPattern>();
        attackPatterns.add(sword);
        attackPatterns.add(claw);

        physicEngine = new PhysicEngine();
        gameEngine = new GameEngine(hero, sword, enemy, claw, this, renderEngine);
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
        physicEngine.setAttackPatterns(attackPatterns);
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

