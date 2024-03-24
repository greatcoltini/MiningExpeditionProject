import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * The Brain for the Factory game.
 * The Factory class contains references to Upgrades, Workers, and Pickaxe types.
 *
 * Within the Factory, all of our processing occurs.
 */
public class Factory {
    private final Pickaxe[] pickaxes = {
            new Pickaxe(1, "Hand", 0, null),
            new Pickaxe(2, "Stone", 25, new Image(new FileInputStream("src/assets/rpg-items-all/stone pick.png"))),
            new Pickaxe(3, "Bronze", 50, new Image(new FileInputStream("src/assets/rpg-items-all/bronze pick.png"))),
            new Pickaxe(4, "Iron", 100, new Image(new FileInputStream("src/assets/rpg-items-all/iron pick.png"))),
            new Pickaxe(5, "Steel", 200, new Image(new FileInputStream("src/assets/rpg-items-all/steel pick.png")))
    };

    private final Upgrade[] possibleUpgrades = {
            new Upgrade("Sunglasses", 2, "Miner"),
            new Upgrade("Hardhat", 2, "Miner"),
            new Upgrade("Laser", 2, "Drill", 300)
    };

    private int score;
    private int workerCount;
    private int income;
    private ArrayList<Upgrade> upgrades;
    private ArrayList<Worker> workers;
    private Pickaxe pickaxe;
    private int multiplier;


    public Factory() throws FileNotFoundException {
        this.score = 0;
        this.multiplier = 1;
        this.pickaxe = pickaxes[0];
        this.workers = new ArrayList<Worker>();
        this.upgrades = new ArrayList<Upgrade>();
    }

    public void mineClick(){
        this.score += this.pickaxe.getStrength() * multiplier;
    }

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

    public void addUpgrade(String upgradeName){
        for (Upgrade upgrade: possibleUpgrades){
            if (upgrade.getName().equalsIgnoreCase(upgradeName)){
                this.score -= upgrade.getCost();
                this.upgrades.add(upgrade);
            }
        }
    }

    public void upgradePickaxe(){
        this.score -= pickaxes[getPickaxeStrength()].getCost();
        this.pickaxe = pickaxes[getPickaxeStrength()];
    }

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

    public synchronized ArrayList<Worker> getWorkers() {
        return workers;
    }

    public int getScore(){
        return this.score;
    }

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

    public int getSpecificWorkersCount(String type){
        int count = 0;
        for (Worker worker: workers){
            if (worker.getType().equalsIgnoreCase(type)){
                count += 1;
            }
        }

        return count;
    }

    public int getWorkerCost(String type){
        if (type.equalsIgnoreCase("Miner")){
            return 10 + (25 * getSpecificWorkersCount(type));
        }
        else if (type.equalsIgnoreCase("Drill")) {
            return 100 + (50 * getSpecificWorkersCount(type));
        }

        return 0;

    }

    public int getPickaxeCost(){
        return getNextPickaxe().getCost();
    }

    public void decreaseScore(int amount){
        this.score -= amount;
    }

    public int getPickaxeStrength(){
        return this.pickaxe.getStrength();
    }

    public Pickaxe getPickaxe(){
        return this.pickaxe;
    }

    public Pickaxe getNextPickaxe(){
        return pickaxes[this.getPickaxeStrength()];
    }

    public boolean hasNextPickaxe(){
        return pickaxes[this.getPickaxeStrength() - 1] != null;
    }

    public Upgrade[] getPossibleUpgrades(){
        return this.possibleUpgrades;
    }

    public Upgrade getSpecificUpgrade(String upgradeName){
        for (Upgrade upgrade: possibleUpgrades){
            if (upgrade.getName().equalsIgnoreCase(upgradeName)){
                return upgrade;
            }
        }
        return null;
    }

}
