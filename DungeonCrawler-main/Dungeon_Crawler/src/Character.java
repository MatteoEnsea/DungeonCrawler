import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class  Character extends DynamicSprite{

    private int life = 20;


    public Character(Image img, double posX, double posY, double W, double H) {
        super(img, posX, posY, W, H);
    }

    /**
     * Modifie la quantité de point de vie du personnage
     * @param life nouvelle valeur pour les points de vie
     */
    public void setLife(int life) {
        this.life = life;
    }

    /**
     *
     * @return points de vie actuels
     */
    public int getLife() {
        return life;
    }

    private Timer attackTimer = new Timer();

    /**
     *
     * @param pattern
     * @param damage
     * @param renderEngine
     */
    public void attack(AttackPattern pattern, int damage, RenderEngine renderEngine){
        pattern.setParent(this);
        pattern.setDamage(damage);
        pattern.activate(5);
        renderEngine.addToRenderList(pattern);
    }
}
