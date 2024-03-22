import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

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
            new Upgrade("Hardhat", 2, "Miner")
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

    public void addWorker(){
        this.decreaseScore(this.getWorkerCost());
        Worker newWorker = new Miner(40, 40 + 10 * this.getWorkers().size());
        this.workers.add(newWorker);
    }

    public synchronized ArrayList<Worker> getWorkers() {
        return workers;
    }

    public int getScore(){
        return this.score;
    }

    public int getIncome(){
        return getWorkerCount();
    }

    public int getWorkerCount(){
        return this.workers.size();
    }

    public int getWorkerCost(){
        return 10 + (25 * this.workers.size());
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
        return pickaxes[this.getPickaxeStrength()] != null;
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
