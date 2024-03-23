public class Upgrade {
    private String type;
    private int multiplier;
    private String name;
    private int cost;

    public Upgrade(String name, int multiplier, String type){
        this.name = name;
        this.multiplier = multiplier;
        this.type = type;
        this.cost = 100;
    }

    public Upgrade(String name, int multiplier, String type, int cost){
        this.name = name;
        this.multiplier = multiplier;
        this.type = type;
        this.cost = cost;
    }

    public int getMultiplier(){
        return this.multiplier;
    }

    public String getType(){
        return this.type;
    }

    public String getName(){
        return this.name;
    }

    public int getCost(){
        return this.cost;
    }


}
