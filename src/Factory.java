import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * The Brain for the Factory game.
 * The Factory class contains references to Upgrades, Workers, and Pickaxe types.
 * Within the Factory, all of our processing occurs.
 *
 * @author Colton Donkersgoed
 */
public class Factory {
    /** An array of Pickaxes **/
    private final Pickaxe[] pickaxes = {
            new Pickaxe(1, "Hand", 0, null),
            new Pickaxe(2, "Stone", 25, new Image(new FileInputStream("src/assets/rpg-items-all/stone pick.png"))),
            new Pickaxe(3, "Bronze", 50, new Image(new FileInputStream("src/assets/rpg-items-all/bronze pick.png"))),
            new Pickaxe(4, "Iron", 100, new Image(new FileInputStream("src/assets/rpg-items-all/iron pick.png"))),
            new Pickaxe(5, "Steel", 200, new Image(new FileInputStream("src/assets/rpg-items-all/steel pick.png")))
    };

    /** An array of possible upgrades **/
    private final Upgrade[] possibleUpgrades = {
            new Upgrade("Sunglasses", 2, "Miner"),
            new Upgrade("Hardhat", 2, "Miner"),
            new Upgrade("Laser", 2, "Drill", 300)
    };

    /** The score that the factory has **/
    private int score;
    /** The income that the factory generates **/
    private int income;
    /** An arraylist of upgrades **/
    private ArrayList<Upgrade> upgrades;
    /** An arraylist of workers **/
    private ArrayList<Worker> workers;
    /** The active Pickaxe **/
    private Pickaxe pickaxe;
    /** The multiplier **/
    private int multiplier;


    /**
     * Creates a Factory instance
     * @throws FileNotFoundException
     */
    public Factory() throws FileNotFoundException {
        this.score = 0;
        this.multiplier = 1;
        this.pickaxe = pickaxes[0];
        this.workers = new ArrayList<Worker>();
        this.upgrades = new ArrayList<Upgrade>();
    }

    /**
     * Generates income from the click the user does, based on the pickaxe strength.
     */
    public void mineClick(){
        this.score += this.pickaxe.getStrength() * multiplier;
    }

    /**
     * The main operation, this is called when we generate income for the factory.
     *
     * @return int: returns an integer representing the amount of income generated
     */
    public int miningOperation(){
        this.income = 0;
        for (Worker worker: workers){
            int current_mult = 1;
            for (Upgrade upgrade: upgrades){
                if (upgrade.getType().equalsIgnoreCase(worker.getType())){
                    current_mult *= upgrade.getMultiplier();
                }
            }
            this.income += worker.mine() * current_mult;
        }
        this.score += this.income;
        return this.income;
    }

    /**
     * Adds an upgrade to the factory, based on the given upgrade name.
     * @param upgradeName: a String representing the upgrade name.
     */
    public void addUpgrade(String upgradeName){
        for (Upgrade upgrade: possibleUpgrades){
            if (upgrade.getName().equalsIgnoreCase(upgradeName)){
                this.score -= upgrade.getCost();
                this.upgrades.add(upgrade);
            }
        }
    }

    /**
     * Upgrades a specific pickaxe, and subtracts the score
     */
    public void upgradePickaxe(){
        this.score -= pickaxes[getPickaxeStrength()].getCost();
        this.pickaxe = pickaxes[getPickaxeStrength()];
    }

    /**
     * Adds a specific type of worker to the factory's array.
     * @param type: The type of Worker to add to the job.
     * @throws FileNotFoundException: Throws an error if we cannot locate the image.
     */
    public void addWorker(String type) throws FileNotFoundException {
        this.decreaseScore(this.getWorkerCost(type));
        Worker newWorker = null;

        if (type.equalsIgnoreCase("Miner")){
            newWorker = new Miner(40, 40 + 10 * this.getSpecificWorkersCount(type));
        }
        else if (type.equalsIgnoreCase("Drill")){
            newWorker = new Drill(60, 40 + 10 * getSpecificWorkersCount(type));
        }
        this.workers.add(newWorker);

    }

    /**
     * Returns an array of Workers.
     * @return ArrayList<Worker> of workers
     */
    public synchronized ArrayList<Worker> getWorkers() {
        return workers;
    }

    /**
     * Returns the current score for the player.
     * @return int value of the current amount of money
     */
    public int getScore(){
        return this.score;
    }

    /**
     * Gets a specific type of income from the factory, based on the requested type.
     * @param type: A String representing the type of the income requested.
     * @return int representing the income from the workers of a given type
     */
    public int getTypeIncome(String type){
        int income = 0;

        income = getSpecificWorkersCount(type);

        for (Worker worker: workers){
            if (worker.getType().equalsIgnoreCase(type)){
                income *= worker.getAmount();
                break;
            }
        }

        for (Upgrade upgrade: upgrades){
            if (upgrade.getType().equalsIgnoreCase(type)){
                income *= upgrade.getMultiplier();
            }
        }

        return income;
    }

    /**
     * Gets the count of the specific workers, based on the requested type.
     * @param type: String representing the type of workers
     * @return int representing the number of a specific type of workers
     */
    public int getSpecificWorkersCount(String type){
        int count = 0;
        for (Worker worker: workers){
            if (worker.getType().equalsIgnoreCase(type)){
                count += 1;
            }
        }

        return count;
    }

    /**
     * Returns the cost of the next worker of a specific type.
     * @param type: a String representing the type of worker requested
     * @return int representing the cost of the next worker.
     */
    public int getWorkerCost(String type){
        if (type.equalsIgnoreCase("Miner")){
            return 10 + (25 * getSpecificWorkersCount(type));
        }
        else if (type.equalsIgnoreCase("Drill")) {
            return 100 + (50 * getSpecificWorkersCount(type));
        }

        return 0;

    }

    /**
     * Returns the cost of the next Pickaxe
     * @return
     */
    public int getPickaxeCost(){
        return getNextPickaxe().getCost();
    }

    /**
     * Decreases the score; dependent on a purchase.
     * @param amount
     */
    public void decreaseScore(int amount){
        this.score -= amount;
    }

    /**
     * Returns the strength of the pickaxe
     * @return an integer value representing how much each click is worth.
     */
    public int getPickaxeStrength(){
        return this.pickaxe.getStrength();
    }

    /**
     * Returns a Pickaxe object representing the pick the player currently has.
     * @return Pickaxe object representing the current pickaxe
     */
    public Pickaxe getPickaxe(){
        return this.pickaxe;
    }

    /**
     * Returns the next possible pickaxe, from our array of pickaxe objects
     * @return Pickaxe object representing the next possible purchaseable pickaxe
     */
    public Pickaxe getNextPickaxe(){
        return pickaxes[this.getPickaxeStrength()];
    }

    public Upgrade[] getPossibleUpgrades(){
        return this.possibleUpgrades;
    }

    /**
     * Returns a specific upgrade when called, or null if there is no relevant Upgrade
     * @param upgradeName: a String representing the name of the upgrade.
     * @return Upgrade object with the corresponding name
     */
    public Upgrade getSpecificUpgrade(String upgradeName){
        for (Upgrade upgrade: possibleUpgrades){
            if (upgrade.getName().equalsIgnoreCase(upgradeName)){
                return upgrade;
            }
        }
        return null;
    }

}
