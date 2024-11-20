import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameEngine implements Engine, KeyListener {
    public GameState gameState = GameState.TitleScreen;
    public final DynamicSprite hero;
    private final RenderEngine renderEngine;

    public DynamicSprite getHero() {
        return hero;
    }

    public GameEngine(DynamicSprite hero, RenderEngine renderEngine) {
        this.hero = hero;
        this.renderEngine = renderEngine;
    }

    @Override
    public void update() {
        if (this.hero.life <= 0){
            gameState = GameState.GameOver;

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
                        gameState = GameState.Paused;
                        renderEngine.setGameState(gameState);
                        break;
                    case KeyEvent.VK_SPACE:
                        hero.life = hero.life - 1;
                }
                break;
            case TitleScreen:
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    gameState = GameState.Running;
                    renderEngine.setGameState(gameState);
                }
            case GameOver:
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    gameState = GameState.TitleScreen;
                    renderEngine.setGameState(gameState);
                }
            case Paused:
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    gameState = GameState.Running;
                    renderEngine.setGameState(gameState);
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

