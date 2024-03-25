/**
 * This is an implementation of an Upgrade class.
 * The upgrade class will be used for multiplying the production rate
 * of a specified type of worker.
 *
 * @author Colton Donkersgoed
 *
 */
public class Upgrade {
    /** String representing the type **/
    private String type;
    /** int representing the multiplier for the upg **/
    private int multiplier;
    /** String representing the name **/
    private String name;
    /** int representing the cost **/
    private int cost;

    /**
     *  Creates an instance of the Upgrade class, containing the name, multiplier, and
     *  the type. Defaults the cost to 100.
     * @param name: String representing the name of the upgrade.
     * @param multiplier: int representing the multiplier
     * @param type: String representing the worker class type
     */
    public Upgrade(String name, int multiplier, String type){
        this.name = name;
        this.multiplier = multiplier;
        this.type = type;
        this.cost = 100;
    }

    /**
     * Initializes an instance of the Upgrade class with a specified cost.
     * @param name: String representing the name of the upgrade.
     * @param multiplier: int representing the multiplier of the upgrade.
     * @param type: String representing the type of worker that benefits.
     * @param cost: int representing the cost of the upgrade.
     */
    public Upgrade(String name, int multiplier, String type, int cost){
        this.name = name;
        this.multiplier = multiplier;
        this.type = type;
        this.cost = cost;
    }

    /**
     * Returns the multiplier for the upgrade.
     * @return int - the multiplier
     */
    public int getMultiplier(){
        return this.multiplier;
    }

    /**
     * Returns the type of the upgrade.
     * @return String representing which worker type the upgrade benefits.
     */
    public String getType(){
        return this.type;
    }

    /**
     * Returns the name of the upgrade.
     * @return String representing the name of the upgrade.
     */
    public String getName(){
        return this.name;
    }

    /**
     * Returns the cost of the upgrade.
     * @return int representing the cost of the upgrade.
     */
    public int getCost(){
        return this.cost;
    }


}
