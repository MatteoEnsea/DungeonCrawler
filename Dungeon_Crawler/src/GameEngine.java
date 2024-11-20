import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.logging.Level;

public class GameEngine implements Engine, KeyListener {
    public GameState gameState;
    public final DynamicSprite hero;

    public DynamicSprite getHero() {
        return hero;
    }

    public GameEngine(DynamicSprite hero) {
        this.hero = hero;
    }

    @Override
    public void update(GameState gameState) {
        this.gameState = gameState;
        switch (gameState) {
            default:
                break;
            case Running:
                if (this.hero.life <= 0) {
                    Main.setGameState(GameState.GameOver);
                    hero.setLife(20);
                    LevelManager.setCurrentLevelIndex(0);
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
                        hero.life = hero.life - 1;
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
        this.hero.speed = 0;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}

