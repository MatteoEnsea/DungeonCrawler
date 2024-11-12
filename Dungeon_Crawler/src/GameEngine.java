import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameEngine implements Engine, KeyListener {
    GameState gameState = GameState.TitleScreen;
    private final DynamicSprite hero;

    public GameEngine(DynamicSprite hero) {
        this.hero = hero;
    }

    @Override
    public void update() {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                this.hero.direction = Direction.WEST;
                this.hero.speed = 5;
                break;
            case KeyEvent.VK_RIGHT:
                this.hero.direction = Direction.EAST;
                this.hero.speed = 5;
                break;
            case KeyEvent.VK_UP:
                this.hero.direction = Direction.NORTH;
                this.hero.speed = 5;
                break;
            case KeyEvent.VK_DOWN:
                this.hero.direction = Direction.SOUTH;
                this.hero.speed = 5;
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

