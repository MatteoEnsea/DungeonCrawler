import java.awt.*;

public class AttackPattern extends DynamicSprite{
    private int damage;
    private Character parent;
    private boolean isActive = false;
    private int particleTick=0;
    private RenderEngine renderEngine;

    public AttackPattern(Image img, double posX, double posY, double W, double H, RenderEngine renderEngine) {
        super(img, posX, posY, W, H);
        this.renderEngine=renderEngine;
    }

    /**
     * Réassigne la quantité de dégat provoqué par l'attaque en cas de contact.
     * @param damage entier >=0
     */
    public void setDamage(int damage){
        this.damage=damage;
    }

    /**
     * Déclare l'entité à l'origine de l'attaque, afin d'éviter les dégats sur soit-même (ou éventuellement ses alliés).
     * @param parent personnage attaquant
     */
    public void setParent(Character parent){
        this.parent=parent;
        this.direction = parent.direction;
        int x_offset = 0;
        int y_offset = 0;
        switch (direction){
            case EAST:
                x_offset += (int)parent.width;
                break;
            case WEST:
                x_offset -= (int)this.width;
                break;
            case NORTH:
                y_offset -= (int)this.height;
                break;
            case SOUTH:
                y_offset += (int)parent.height;
                break;
        }
        this.x = parent.x + x_offset;
        this.y = parent.y + y_offset;
    }

    /**
     * Permet d'activer le comportement de l'attaque (movement, hitbox, dégats)
     * @param ticks nombre de frame avant désactivation de l'attaque
     */
    public void activate(int ticks) {
        this.isActive = true;

    }

    /**
     * Diminue le compte à rebour du temps de vie de la particule, puis la désactive à 0
     */
    public void decay(){
        if(this.particleTick>=0){
            this.particleTick--;
        } else if (this.particleTick==0) {
            this.deactivate();
        }
    }

    /**
     * Désactive l'attaque
     */
    public void deactivate(){
        this.isActive = false;
        this.particleTick =-1;
        renderEngine.removeFromRenderList(this);
    }

    /**
     * @return état de l'attaque
     */
    public boolean isActive(){
        return this.isActive;
    }

    /**
     *
     * @return personnage à l'origine de l'attaque
     */
    public Character getParent(){
        return this.parent;
    }

}
