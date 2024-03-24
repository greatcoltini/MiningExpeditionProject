import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
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
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


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
    private boolean noClick = true;
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
    private final Image laserUpg = new Image(new FileInputStream("src/assets/laserUpgrade.png"));
    /**
     *
     */
    Factory rockFactory;

    Button mineRockBtn, purchaseWorkerBtn, upgradePickBtn, purchaseDrillBtn;
    Label upgradesSectionLabel, buildingsSectionLabel;
    TextField score;

    UpgradeImageView laserUpgImage, sunglassesUpgImage;

    public MiningOperation() throws FileNotFoundException {
    }

    private void mine(GraphicsContext gc){
        noClick = false;
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
        if (rockFactory.getPickaxeStrength() <= 4){
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
        purchaseDrillBtn.setText("Purchase Drill : $" + rockFactory.getWorkerCost("Drill"));
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

        UpgradeImageView sunglassesUpgImage = new UpgradeImageView(442, 185, 16, 16, sunglassesUpg);
        Tooltip t = new Tooltip("Miner Sunglasses $" + rockFactory.getSpecificUpgrade("Sunglasses").getCost()
                + "\nDoubles Miner efficiency");
        Tooltip.install(sunglassesUpgImage, t);
        sunglassesUpgImage.setOnMouseEntered((event) -> {
            sunglassesUpgImage.setOpacity(0.5);});
        sunglassesUpgImage.setOnMouseExited((event) -> {sunglassesUpgImage.setOpacity(1);});
        sunglassesUpgImage.setOnMouseClicked((event) -> {purchaseUpgrade(sunglassesUpgImage, "Sunglasses");});

        UpgradeImageView laserUpgImage = new UpgradeImageView(490, 185, 16, 16, laserUpg);
        Tooltip las = new Tooltip("Laser Sights $" + rockFactory.getSpecificUpgrade("Laser").getCost()
                + "\nDoubles Drill efficiency");
        Tooltip.install(laserUpgImage, las);
        laserUpgImage.setOnMouseEntered((event) -> {
            laserUpgImage.setOpacity(0.5);});
        laserUpgImage.setOnMouseExited((event) -> {laserUpgImage.setOpacity(1);});
        laserUpgImage.setOnMouseClicked((event) -> {purchaseUpgrade(laserUpgImage, "Laser");});


        // 3. Add components to the root
        root.getChildren().addAll(canvas, mineRockBtn, score, purchaseWorkerBtn, upgradePickBtn, purchaseDrillBtn);
        root.getChildren().addAll(upgradesSectionLabel, buildingsSectionLabel, sunglassesUpgImage, laserUpgImage);

        // 4. Configure the components
        mineRockBtn.setPrefWidth(64);
        mineRockBtn.setPrefHeight(64);
        mineRockBtn.relocate(rockX, rockY);
        mineRockBtn.setCursor(Cursor.HAND);

        score.setPrefWidth(128);
        score.relocate(oreCounterX, oreCounterY);
        score.setEditable(false);

        purchaseWorkerBtn.setPrefWidth(150);
        purchaseWorkerBtn.relocate(450, 25);
        purchaseWorkerBtn.setDisable(true);

        purchaseDrillBtn.setPrefWidth(150);
        purchaseDrillBtn.relocate(450, 50);
        purchaseDrillBtn.setDisable(true);

        upgradePickBtn.setPrefWidth(150);
        upgradePickBtn.relocate(450, rockY - 32);
        upgradePickBtn.setDisable(true);
        upgradePickBtn.getStyleClass().add("bordered-title-border");

        upgradesSectionLabel.setPrefSize(150, 25);
        upgradesSectionLabel.relocate(450, 160);
        upgradesSectionLabel.getStyleClass().add("border-item");
        upgradesSectionLabel.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

        buildingsSectionLabel.setPrefSize(150, 25);
        buildingsSectionLabel.relocate(450, 0);
        buildingsSectionLabel.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));


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

                        // create toasts for income amounts
                        // draw the income generated from the workers
                        if (rockFactory.getSpecificWorkersCount("Miner") > 0){
                            Toast incomeGen = new Toast((int) (baseRockX - Math.random() * 50), baseRockY, "Text", "+" + rockFactory.getTypeIncome("Miner"), Color.GREEN);
                            toasts.add(incomeGen);
                        }

                        if (rockFactory.getSpecificWorkersCount("Drill") > 0){
                            Toast incomeGen = new Toast((int) (baseRockX + Math.random() * 20), baseRockY, "Text", "+" + rockFactory.getTypeIncome("Drill"), Color.STEELBLUE);
                            toasts.add(incomeGen);
                        }
                    }

                    refreshView();


                    gc.clearRect(0, 0, 600, 300);


                    gc.drawImage(minebkgd, 0,0, 600, 300);
                    // Redraw income
                    gc.drawImage(paperBkgd, 0, 0, 100, 200);
                    gc.strokeText("Income per Second: " + income, 235, 200);

                    // initial instructions before user clicks on the rock
                    if (noClick){
                        gc.strokeText("Click on the rock to mine ore.", 195, 115);
                    }

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
