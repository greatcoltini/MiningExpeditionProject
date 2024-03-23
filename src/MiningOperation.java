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
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


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
    private Pane root;
    private ArrayList<Toast> toasts = new ArrayList<Toast>();
    private boolean shakeRock = false;
    private int timer = 0;
    private int rockX = 268;
    private int income;
    private int baseRockX = 268;
    private int rockY = 118;
    private int baseRockY = 118;
    private int oreCounterY = 160;
    private int oreCounterX = 228;
    private final Image minebkgd = new Image(new FileInputStream("src/assets/minebkg.gif"));
    private final Image oreBlock = new Image(new FileInputStream("src/assets/rock.PNG"));
    private final Image upgBkgd = new Image(new FileInputStream("src/assets/upgradebkg.png"));
    private final Image paperBkgd = new Image(new FileInputStream("src/assets/sheets.png"));
    private final Image sunglassesUpg = new Image(new FileInputStream("src/assets/sunglassesUpgrade.png"));
    /**
     *
     */
    Factory rockFactory;

    Button mineRockBtn, purchaseWorkerBtn, upgradePickBtn, purchaseDrillBtn;
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

    private void purchaseWorker(ActionEvent e) throws FileNotFoundException {
        toasts.add(new Toast(baseRockX, baseRockY, "Text", "-" + rockFactory.getWorkerCost("Miner"), Color.RED));
        rockFactory.addWorker("Miner");
    }

    private void purchaseDrill(ActionEvent e) throws FileNotFoundException {
        toasts.add(new Toast(baseRockX, baseRockY, "Text", "-" + rockFactory.getWorkerCost("Drill"), Color.RED));
        rockFactory.addWorker("Drill");
    }

    private void upgradePick(ActionEvent e){
        if (rockFactory.hasNextPickaxe()){
            toasts.add(new Toast(baseRockX, baseRockY, "Text", "-" + rockFactory.getPickaxeCost(), Color.RED));
            rockFactory.upgradePickaxe();
            scene.setCursor(new ImageCursor(rockFactory.getPickaxe().getImage(), 32, 32));
            refreshView();
        }
    }

    private void purchaseUpgrade(ImageView img, String upgradeName){
        int missing_price = rockFactory.getSpecificUpgrade(upgradeName).getCost() - rockFactory.getScore();
        if (missing_price <= 0){
            rockFactory.addUpgrade(upgradeName);
            root.getChildren().remove(img);
        }
        else {
            toasts.add(new Toast(baseRockX, baseRockY, "Text", "Need " + missing_price, Color.PURPLE));
        }

    }

    private void refreshView(){
        checkEnablers();
        upgradePickBtn.setText("Upgrade Pick : $" + rockFactory.getPickaxeCost());
        purchaseWorkerBtn.setText("Purchase Worker : $" + rockFactory.getWorkerCost("Miner"));
        score.setText(String.valueOf(rockFactory.getScore()));
    }

    private void checkEnablers(){
        upgradePickBtn.setDisable(rockFactory.getScore() < rockFactory.getPickaxeCost());
        purchaseWorkerBtn.setDisable(rockFactory.getScore() < rockFactory.getWorkerCost("Miner"));
        purchaseDrillBtn.setDisable(rockFactory.getScore() < rockFactory.getWorkerCost("Drill"));
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
        root = new Pane();
        scene = new Scene(root, 600, 300);
        scene.getStylesheets().add(Objects.requireNonNull(this.getClass().getResource("miningoperation.css")).toExternalForm());

        // 1. Create the model
        rockFactory = new Factory();

        // 2. Create the GUI components
        mineRockBtn = new Button("Mine!");
        score = new TextField();
        purchaseWorkerBtn = new Button("Purchase Miner!");
        purchaseDrillBtn = new Button("Purchase Drill!");
        upgradePickBtn = new Button("Upgrade Pickaxe!");
        upgradesSectionLabel = new Label("Upgrades");
        buildingsSectionLabel = new Label("Buildings");


        ImageView test = new ImageView(sunglassesUpg);
        test.setPickOnBounds(true);
        test.relocate(442, 185);
        test.resize(16, 16);
        Tooltip t = new Tooltip("Miner Sunglasses $" + rockFactory.getSpecificUpgrade("Sunglasses").getCost()
        + "\nDoubles Miner efficiency");
        Tooltip.install(test, t);
        test.setOnMouseEntered((event) -> {
            test.setOpacity(0.5);});
        test.setOnMouseExited((event) -> {test.setOpacity(1);});
        test.setOnMouseClicked((event) -> {purchaseUpgrade(test, "Sunglasses");});

        // 3. Add components to the root
        root.getChildren().addAll(canvas, mineRockBtn, score, purchaseWorkerBtn, upgradePickBtn, purchaseDrillBtn);
        root.getChildren().addAll(upgradesSectionLabel, buildingsSectionLabel, test);

        // 4. Configure the components
        mineRockBtn.setPrefWidth(64);
        mineRockBtn.setPrefHeight(64);
        mineRockBtn.relocate(rockX, rockY);
        mineRockBtn.setCursor(Cursor.HAND);

        score.setPrefWidth(128);
        score.relocate(oreCounterX, oreCounterY);
        score.setEditable(false);

        purchaseWorkerBtn.setPrefWidth(150);
        purchaseWorkerBtn.relocate(450, rockY);
        purchaseWorkerBtn.setDisable(true);

        purchaseDrillBtn.setPrefWidth(150);
        purchaseDrillBtn.relocate(450, rockY - 75);
        purchaseDrillBtn.setDisable(true);

        upgradePickBtn.setPrefWidth(150);
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
        purchaseWorkerBtn.setOnAction(e -> {
            try {
                purchaseWorker(e);
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });
        purchaseDrillBtn.setOnAction(e -> {
            try {
                purchaseDrill(e);
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });




        Timer gameLoop = new Timer();
        gameLoop.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    // Game logic and UI updates here
                    // Ensure that UI updates are done on the JavaFX Application Thread
                    if (timer >= 10) {
                        income = rockFactory.miningOperation();
                        timer = 0;
                    }

                    refreshView();


                    gc.clearRect(0, 0, 600, 300);
                    gc.drawImage(minebkgd, 0,0, 600, 300);
                    // Redraw income
                    gc.drawImage(paperBkgd, 0, 0, 100, 200);
                    gc.strokeText("Income per Second: " + income, 235, 200);

                    if (shakeRock) {
                        gc.drawImage(oreBlock, rockX + Math.random(), rockY + Math.random());
                        shakeRock = false;
                    } else {
                        gc.drawImage(oreBlock, baseRockX, baseRockY);
                    }

                    gc.drawImage(upgBkgd, 410, 185, 32, 32);
                    gc.drawImage(rockFactory.getNextPickaxe().getImage(), 410, 185, 32, 32);

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
