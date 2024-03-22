import javafx.application.Application;
import javafx.application.Platform;
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
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


import javax.tools.Tool;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

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

    private Scene scene;
    private ArrayList<Toast> toasts = new ArrayList<Toast>();
    private boolean shakeRock = false;
    private int timer = 0;
    private int rockX = 268;
    private int baseRockX = 268;
    private int rockY = 118;
    private int baseRockY = 118;
    private int oreCounterY = 160;
    private int oreCounterX = 228;
    private final Image oreBlock = new Image(new FileInputStream("src/assets/rock.PNG"));
    private final Image upgBkgd = new Image(new FileInputStream("src/assets/upgradebkg.png"));
    private final Image paperBkgd = new Image(new FileInputStream("src/assets/sheets.png"));
    private final Image sunglassesUpg = new Image(new FileInputStream("src/assets/sunglassesupg.png"));
    /**
     *
     */
    Factory rockFactory;

    Button mineRockBtn, purchaseWorkerBtn, upgradePickBtn;
    Label upgradesSectionLabel, buildingsSectionLabel;
    TextField score;

    public MiningOperation() throws FileNotFoundException {
    }

    private void mine(GraphicsContext gc){
        rockFactory.mineClick();
        toasts.add(new Toast((int) (baseRockX + Math.random() * 8)
                , baseRockY, "Text", "+" + rockFactory.getPickaxeStrength(), Color.GOLDENROD));
        shakeRock = true;
        refreshView();
    }

    private void purchaseWorker(ActionEvent e){
        toasts.add(new Toast(baseRockX, baseRockY, "Text", "-" + rockFactory.getWorkerCost(), Color.RED));
        rockFactory.addWorker();

    }

    private void upgradePick(ActionEvent e){
        rockFactory.upgradePickaxe();
        scene.setCursor(new ImageCursor(rockFactory.getPickaxe().getImage(), 32, 32));
        refreshView();
    }

    private void refreshView(){
        checkEnablers();
        purchaseWorkerBtn.setTooltip(new Tooltip("Costs " + rockFactory.getWorkerCost()));
        score.setText(String.valueOf(rockFactory.getScore()));
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
    public void start(Stage stage) throws FileNotFoundException {
        stage.setTitle("Section 1.4 Animated!"); // window title here
        Canvas canvas = new Canvas(600, 300); // canvas size here
        Pane root = new Pane();
        scene = new Scene(root, 600, 300);
        scene.getStylesheets().add(Objects.requireNonNull(this.getClass().getResource("miningoperation.css")).toExternalForm());

        // 1. Create the model
        rockFactory = new Factory();

        // 2. Create the GUI components
        mineRockBtn = new Button("Mine!");
        score = new TextField();
        purchaseWorkerBtn = new Button("Purchase Miner!");
        upgradePickBtn = new Button("Upgrade Pickaxe!");
        upgradesSectionLabel = new Label("Upgrades");
        buildingsSectionLabel = new Label("Buildings");


        // 3. Add components to the root
        root.getChildren().addAll(canvas, mineRockBtn, score, purchaseWorkerBtn, upgradePickBtn);
        root.getChildren().addAll(upgradesSectionLabel, buildingsSectionLabel);

        // 4. Configure the components
        mineRockBtn.setPrefWidth(64);
        mineRockBtn.setPrefHeight(64);
        mineRockBtn.relocate(rockX, rockY);
        mineRockBtn.setCursor(Cursor.HAND);

        score.setPrefWidth(128);
        score.relocate(oreCounterX, oreCounterY);
        score.setEditable(false);

        purchaseWorkerBtn.setPrefWidth(128);
        purchaseWorkerBtn.relocate(450, rockY);
        purchaseWorkerBtn.setDisable(true);
        purchaseWorkerBtn.setTooltip(new Tooltip("Costs " + rockFactory.getWorkerCost()));

        upgradePickBtn.setPrefWidth(128);
        upgradePickBtn.relocate(450, rockY - 32);
        upgradePickBtn.setDisable(true);
        upgradePickBtn.getStyleClass().add("bordered-title-border");

        upgradesSectionLabel.setPrefSize(200, 50);
        upgradesSectionLabel.relocate(450, 150);
        upgradesSectionLabel.getStyleClass().add("border-item");

        buildingsSectionLabel.setPrefSize(200, 50);
        buildingsSectionLabel.relocate(450, 0);

        // 5. Add Listeners
        stage.setScene(scene);
        stage.show();
        GraphicsContext gc = canvas.getGraphicsContext2D();
        mineRockBtn.setOnAction((event) -> mine(gc));
        upgradePickBtn.setOnAction(this::upgradePick);
        purchaseWorkerBtn.setOnAction(this::purchaseWorker);

        Timer gameLoop = new Timer();
        gameLoop.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    // Game logic and UI updates here
                    // Ensure that UI updates are done on the JavaFX Application Thread
                    if (timer >= 10) {
                        rockFactory.miningOperation();
                        timer = 0;
                    }

                    refreshView();


                    gc.clearRect(0, 0, 600, 300);
                    // Redraw income
                    gc.drawImage(paperBkgd, 0, 0, 100, 200);
                    gc.strokeText("Income per Second: " + rockFactory.getIncome(), 235, 200);

                    if (shakeRock) {
                        gc.drawImage(oreBlock, rockX + Math.random(), rockY + Math.random());
                        shakeRock = false;
                    } else {
                        gc.drawImage(oreBlock, baseRockX, baseRockY);
                    }

                    gc.drawImage(upgBkgd, 410, 185, 32, 32);
                    gc.drawImage(rockFactory.getNextPickaxe().getImage(), 410, 185, 32, 32);

                    gc.drawImage(upgBkgd, 442, 185, 32, 32);
                    gc.drawImage(sunglassesUpg, 442, 185, 32, 32);


                    mineRockBtn.setOpacity(0);

                    // Draw upgrades
                    for (Worker worker : rockFactory.getWorkers()) {
                        worker.draw(gc);
                    }

                    // Draw Toasts

                    toasts.removeIf(Toast::getExpired);
                    for (Toast toast : toasts){
                        toast.draw(gc);
                    }

                    timer += 1;
                });
            }
        }, 0, 100);

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
