import java.util.ArrayList;

public class PhysicEngine implements Engine {
    ArrayList<DynamicSprite> movingSpriteList = new ArrayList<>();
    ArrayList<SolidSprite> environnement;

    public void addToMovingSpriteList(DynamicSprite s) {
        movingSpriteList.add(s);
    }

    public void setEnvironment(ArrayList<SolidSprite> environnement) {
        this.environnement = environnement;
    }

    @Override
    public void update() {
        for (DynamicSprite s : movingSpriteList) {
            s.moveIfPossible(environnement);
        }
    }
}
