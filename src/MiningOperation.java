import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.layout.Border;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


import javax.tools.Tool;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Use this template to create simple animations in FX. Change the name of the
 * class and put your own name as author below. Change the size of the canvas
 * and the window title where marked and add your drawing code in the animation
 * method where shown.
 *
 * @author YOUR NAME
 */
public class MiningOperation extends Application {

    /**
     *
     */
    private final Pickaxe[] pickaxes = {new Pickaxe(2, "Bronze"),
                                  new Pickaxe(3, "Iron")};
    private Scene scene;
    private int rockX = 268;
    private int rockY = 118;
    private int oreCounterY = 160;
    private int oreCounterX = 268;
    private final Image oreBlock = new Image(new FileInputStream("src/assets/rock.PNG"));
    private final Image bronzePick = new Image(new FileInputStream("src/assets/rpg-items-all/bronze pick.png"));

    /**
     *
     */
    Factory rockFactory;

    Button mineRockBtn, purchaseWorkerBtn, upgradePickBtn;
    Label upgradesSectionLabel;
    TextField score, passiveIncomeLabel;

    public MiningOperation() throws FileNotFoundException {
    }

    private void mine(){
        rockFactory.mineClick();
        refreshView();
    }

    private void purchaseWorker(ActionEvent e, GraphicsContext gc){
        Worker toDraw = rockFactory.addWorker();
        toDraw.draw(gc);
    }

    private void upgradePick(){
        scene.setCursor(new ImageCursor(bronzePick, 32, 32));
        rockFactory.decreaseScore(rockFactory.getUpgradeCost());
        rockFactory.setPickaxe(pickaxes[rockFactory.getPickaxeStrength() - 1]);
        refreshView();
    }

    private void refreshView(){
        checkEnablers();
        score.setText(String.valueOf(rockFactory.getScore()));
        passiveIncomeLabel.setText("Passive Income: " + rockFactory.getWorkerCount());
    }

    private void checkEnablers(){
        upgradePickBtn.setDisable(rockFactory.getScore() < rockFactory.getUpgradeCost());
        purchaseWorkerBtn.setDisable(rockFactory.getScore() < rockFactory.getWorkerCost());
    }

    /**
     * Sets up the stage and starts the main thread. Your drawing code should
     * NOT go here.
     *
     * @param stage The first stage
     */
    @Override
    public void start(Stage stage) {
        stage.setTitle("Section 1.4 Animated!"); // window title here
        Canvas canvas = new Canvas(600, 300); // canvas size here
        Pane root = new Pane();
        scene = new Scene(root, 600, 300);
        scene.getStylesheets().add("miningoperation.css");

        // 1. Create the model
        Pickaxe starting_pick = new Pickaxe(1, "Basic");

        rockFactory = new Factory(starting_pick);

        // 2. Create the GUI components
        mineRockBtn = new Button("Mine!");
        score = new TextField();
        purchaseWorkerBtn = new Button("Purchase Miner!");
        upgradePickBtn = new Button("Upgrade Pickaxe!");
        upgradesSectionLabel = new Label("Upgrades and Purchases");
        passiveIncomeLabel = new TextField("Passive Income: 0");


        // 3. Add components to the root
        root.getChildren().addAll(canvas, mineRockBtn, score, purchaseWorkerBtn, upgradePickBtn);
        root.getChildren().addAll(upgradesSectionLabel, passiveIncomeLabel);

        // 4. Configure the components
        mineRockBtn.setPrefWidth(64);
        mineRockBtn.setPrefHeight(64);
        mineRockBtn.relocate(rockX, rockY);
        mineRockBtn.setCursor(Cursor.HAND);

        score.setPrefWidth(64);
        score.relocate(oreCounterX, oreCounterY);
        score.setEditable(false);

        purchaseWorkerBtn.setPrefWidth(128);
        purchaseWorkerBtn.relocate(450, rockY);
        purchaseWorkerBtn.setDisable(true);
        Tooltip workerPrice = new Tooltip("Costs 100");
        Tooltip.install(purchaseWorkerBtn, workerPrice);

        upgradePickBtn.setPrefWidth(128);
        upgradePickBtn.relocate(450, rockY - 32);
        upgradePickBtn.setDisable(true);
        upgradePickBtn.getStyleClass().add("bordered-title-border");

        upgradesSectionLabel.setPrefSize(200, 50);
        upgradesSectionLabel.relocate(450, 0);
        upgradesSectionLabel.getStyleClass().add("border-item");

        passiveIncomeLabel.setPrefWidth(128);
        passiveIncomeLabel.relocate(oreCounterX - 32, oreCounterY + 20);
        passiveIncomeLabel.setEditable(false);

        // 5. Add Listeners
        stage.setScene(scene);
        stage.show();
        GraphicsContext gc = canvas.getGraphicsContext2D();
        mineRockBtn.setOnAction((event) -> mine());
        upgradePickBtn.setOnAction((event) -> upgradePick());
        purchaseWorkerBtn.setOnAction((event) -> purchaseWorker(null, gc));

        Timer gameLoop = new Timer();
        gameLoop.schedule(new TimerTask(){
            @Override
            public void run(){
                // get the number of workers
                // increment the money once per second for each worker
                rockFactory.miningOperation();
                refreshView();
            }
        }, 0, 1000);


        // This code starts a "thread" which will run your animation
        Thread t = new Thread(() -> {
            animate(gc);
        });
        t.start();
    }

    /**
     * Animation thread. This is where you put your animation code.
     *
     * @param gc The drawing surface
     */
    public void animate(GraphicsContext gc) {
        // YOUR CODE HERE!

        gc.drawImage(oreBlock, rockX, rockY);
        gc.drawImage(bronzePick, 410, rockY - 34, 32, 32);
        mineRockBtn.setOpacity(0);

    }

    /**
     * Use this method instead of Thread.sleep(). It handles the possible
     * exception by catching it, because re-throwing it is not an option in this
     * case.
     *
     * @param duration Pause time in milliseconds.
     */
    public static void pause(int duration) {
        try {
            Thread.sleep(duration);
        } catch (InterruptedException ex) {
        }
    }

    /**
     * Exits the app completely when the window is closed. This is necessary to
     * kill the animation thread.
     */
    @Override
    public void stop() {
        System.exit(0);
    }

    /**
     * Launches the app
     *
     * @param args unused
     */
    public static void main(String[] args) {
        launch(args);
    }
}
