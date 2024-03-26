package assignment05_000921540;

import javafx.scene.image.Image;

/**
 * This class is an implementation of a Pickaxe.
 *
 * @author Colton Donkersgoed
 */
public class Pickaxe {
    /** Initialization of the variables for the pickaxe class **/
    /** The strength of the pickaxe, in integer, or how much income is generated per click **/
    private int strength;
    /** The name of the pickaxe **/
    private String name;
    /** The cost of the pickaxe **/
    private int cost;
    /** The image to represent the pickaxe **/
    private Image image;

    /**
     * A constructor for the pickaxe, initializing it with a given strength, name,
     * cost, and image
     * @param strength: an integer value representing how much income is generated per click
     * @param name: a String value giving a name to the pickaxe
     * @param cost: the cost of the pickaxe for purchase
     * @param image: an image associated with the pickaxe
     */
    public Pickaxe (int strength, String name, int cost, Image image){
        this.strength = strength;
        this.name = name;
        this.cost = cost;
        this.image = image;
    }

    /**
     * Returns the strength of the pickaxe.
     * @return integer value representing the strength
     */
    public int getStrength() {
        return strength;
    }

    /**
     * Returns the name of the pickaxe.
     * @return String value representing the name of the pickaxe
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the cost of the pickaxe
     * @return integer value representing the cost of the pickaxe
     */
    public int getCost(){
        return this.cost;
    }

    /**
     * Returns the image representing the pickaxe
     * @return image corresponding to the pickaxe
     */
    public Image getImage(){
        return this.image;
    }
}
