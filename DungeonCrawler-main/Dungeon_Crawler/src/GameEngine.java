import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;

public class GameEngine implements Engine, KeyListener {
    public GameState gameState;
    public final Character hero;
    public final AttackPattern sword;
    public final Character enemy;
    public final AttackPattern claw;
    private final GameLevelManager levelManager;
    private RenderEngine renderEngine;
    private ArrayList<Integer> movementKey;

    public GameEngine(Character hero, AttackPattern sword, Character enemy, AttackPattern claw, GameLevelManager levelManager, RenderEngine renderEngine) {
        this.hero = hero;
        this.sword = sword;
        this.enemy = enemy;
        this.claw = claw;
        this.levelManager = levelManager;
        this.renderEngine=renderEngine;
        this.movementKey = new ArrayList<>(Arrays.asList(KeyEvent.VK_UP,KeyEvent.VK_DOWN,KeyEvent.VK_LEFT,KeyEvent.VK_RIGHT));
    }



    @Override
    public void update(GameState gameState) {
        this.gameState = gameState;
        switch (gameState) {
            default:
                break;
            case Running:
                if (this.hero.getLife() <= 0) {
                    Main.setGameState(GameState.GameOver);
                    hero.setLife(20);
                    try {
                        levelManager.resetLevel();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            break;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (gameState) {
            case Running:
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        this.hero.direction = Direction.WEST;
                        this.hero.speed = 10;
                        break;
                    case KeyEvent.VK_RIGHT:
                        this.hero.direction = Direction.EAST;
                        this.hero.speed = 10;
                        break;
                    case KeyEvent.VK_UP:
                        this.hero.direction = Direction.NORTH;
                        this.hero.speed = 10;
                        break;
                    case KeyEvent.VK_DOWN:
                        this.hero.direction = Direction.SOUTH;
                        this.hero.speed = 10;
                        break;
                    case KeyEvent.VK_ESCAPE:
                        Main.setGameState(GameState.Paused);
                        break;
                    case KeyEvent.VK_SPACE:
                        hero.setLife(hero.getLife() - 1);
                        break;
                    case KeyEvent.VK_SHIFT:
                        hero.attack(sword,5,renderEngine);
                        break;
                }
                break;
            case TitleScreen:
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    Main.setGameState(GameState.Running);
                }
            case GameOver:
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    Main.setGameState(GameState.TitleScreen);
                }
            case Paused:
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    Main.setGameState(GameState.Running);
                }
        }
    }


    @Override
    public void keyReleased(KeyEvent e) {
        if(movementKey.contains(e.getKeyCode())){
            hero.speed = 0;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}

