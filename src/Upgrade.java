public class Upgrade {
    private String type;
    private int multiplier;
    private String name;

    public Upgrade(String name, int multiplier, String type){
        this.name = name;
        this.multiplier = multiplier;
        this.type = type;
    }

    public int getMultiplier(){
        return this.multiplier;
    }

    public String getType(){
        return this.type;
    }


}
