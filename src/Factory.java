import java.sql.Array;
import java.util.ArrayList;

public class Factory {
    private int score;
    private int workerCount;
    private ArrayList<Upgrade> upgrades;
    private ArrayList<Worker> workers;
    private Pickaxe pickaxe;
    private int multiplier;


    public Factory(Pickaxe pickaxe){
        this.score = 0;
        this.multiplier = 1;
        this.pickaxe = pickaxe;
        this.workers = new ArrayList<Worker>();
    }

    public void mineClick(){
        this.score += this.pickaxe.getStrength() * multiplier;
    }

    public void miningOperation(){
        for (Worker worker: workers){
            this.score += worker.mine();
        }
    }

    public void addUpgrade(Upgrade upgrade){
        this.upgrades.add(upgrade);
    }

    public void setPickaxe(Pickaxe pickaxe){
        this.pickaxe = pickaxe;
    }

    public Worker addWorker(){
        this.decreaseScore(this.getWorkerCost());
        Worker newWorker = new Worker(40, 40 + 10 * this.getWorkers().size());
        this.workers.add(newWorker);
        return newWorker;
    }

    public synchronized ArrayList<Worker> getWorkers() {
        return workers;
    }

    public int getScore(){
        return this.score;
    }

    public int getWorkerCount(){
        return this.workers.size();
    }

    public int getWorkerCost(){
        return 10 + (25 * this.workers.size());
    }

    public int getUpgradeCost(){
        return 5 + (25 * this.getPickaxeStrength());
    }

    public void decreaseScore(int amount){
        this.score -= amount;
    }

    public int getPickaxeStrength(){
        return this.pickaxe.getStrength();
    }

}
