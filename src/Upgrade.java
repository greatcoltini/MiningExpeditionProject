/**
 *
 * @author Colton Donkersgoed
 *
 * TODO: Docstring
 */
public class Upgrade {
    /** **/
    private String type;
    /** **/
    private int multiplier;
    /** **/
    private String name;
    /** **/
    private int cost;

    /**
     *
     * @param name
     * @param multiplier
     * @param type
     */
    public Upgrade(String name, int multiplier, String type){
        this.name = name;
        this.multiplier = multiplier;
        this.type = type;
        this.cost = 100;
    }

    /**
     *
     * @param name
     * @param multiplier
     * @param type
     * @param cost
     */
    public Upgrade(String name, int multiplier, String type, int cost){
        this.name = name;
        this.multiplier = multiplier;
        this.type = type;
        this.cost = cost;
    }

    /**
     *
     * @return
     */
    public int getMultiplier(){
        return this.multiplier;
    }

    /**
     *
     * @return
     */
    public String getType(){
        return this.type;
    }

    /**
     *
     * @return
     */
    public String getName(){
        return this.name;
    }

    /**
     *
     * @return
     */
    public int getCost(){
        return this.cost;
    }


}
