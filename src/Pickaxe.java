import javafx.scene.image.Image;

public class Pickaxe {
    private int strength;
    private String name;
    private int cost;
    private Image image;

    public Pickaxe (int strength, String name, int cost, Image image){
        this.strength = strength;
        this.name = name;
        this.cost = cost;
        this.image = image;
    }

    public int getStrength() {
        return strength;
    }

    public String getName() {
        return name;
    }

    public int getCost(){
        return this.cost;
    }

    public Image getImage(){
        return this.image;
    }
}
