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
 * This is the main file for the Mining Operation game.
 *
 * @author Colton Donkersgoed
 */
public class MiningOperation extends Application {

    /** initializes the scene for access elsewhere **/
    private Scene scene;
    /** Boolean to display instructions if user has not acted **/
    private boolean noClick = true;
    /** initializes the root for access elsewhere **/
    private Pane root;
    /** an array of toasts for animation **/
    private ArrayList<Toast> toasts = new ArrayList<Toast>();
    /** boolean to detect whether the rock has been hit **/
    private boolean shakeRock = false;
    /** timer **/
    private int timer = 0;
    /** initializes the X value of the rock img **/
    private int rockX = 268;
    /** initializes the Y value of the rock img **/
    private int rockY = 200;
    /** tracker for the income generated **/
    private int income;

    /** Y and X values for the income tracker **/
    private int oreCounterY = 250;
    private int oreCounterX = 228;
    /** initialization of various images used for the project **/
    private final Image minebkgd = new Image(new FileInputStream("src/assets/minebkg.gif"));
    private final Image oreBlock = new Image(new FileInputStream("src/assets/rock.PNG"));
    private final Image upgBkgd = new Image(new FileInputStream("src/assets/upgradebkg.png"));
    private final Image paperBkgd = new Image(new FileInputStream("src/assets/sheets.png"));
    private final Image sunglassesUpg = new Image(new FileInputStream("src/assets/sunglassesUpgrade.png"));
    private final Image laserUpg = new Image(new FileInputStream("src/assets/laserUpgrade.png"));
    /**
     * The below generates the rock factory for the game to run the logic with
     */
    Factory rockFactory;

    /**
     * The below creates the buttons, labels, textfield, and UpgradeImageViews
     * for the graphical representation of the game.
     */
    Button mineRockBtn, purchaseWorkerBtn, upgradePickBtn, purchaseDrillBtn;
    Label upgradesSectionLabel, buildingsSectionLabel;
    TextField score;

    UpgradeImageView laserUpgImage, sunglassesUpgImage;

    /**
     * Throws an issue
     * @throws FileNotFoundException
     */
    public MiningOperation() throws FileNotFoundException {
    }

    /**
     * Adds some mining value and creates a toast request
     */
    private void mine(){
        noClick = false;
        rockFactory.mineClick();
        toasts.add(new Toast((int) (rockX + Math.random() * 8)
                , rockY, "Text", "+" + rockFactory.getPickaxeStrength(), Color.GOLDENROD));
        shakeRock = true;
        refreshView();
    }

    /**
     * Purchases a Miner for income generation, adding it to the factory.
     * @param e
     * @throws FileNotFoundException
     */
    private void purchaseWorker(ActionEvent e) throws FileNotFoundException {
        toasts.add(new Toast(rockX, rockY, "Text", "-" + rockFactory.getWorkerCost("Miner"), Color.RED));
        rockFactory.addWorker("Miner");
    }

    /**
     * Purchases a Drill for income generation, adding it to the factory.
     * @param e
     * @throws FileNotFoundException: errors if there is no image file found.
     */
    private void purchaseDrill(ActionEvent e) throws FileNotFoundException {
        toasts.add(new Toast(rockX, rockY, "Text", "-" + rockFactory.getWorkerCost("Drill"), Color.RED));
        rockFactory.addWorker("Drill");
    }

    /**
     * Upgrades the pickaxe
     *
     * TODO: CHANGE THIS LATER
     * @param e
     */
    private void upgradePick(ActionEvent e){
        if (rockFactory.getPickaxeStrength() <= 4){
            toasts.add(new Toast(rockX, rockY, "Text", "-" + rockFactory.getPickaxeCost(), Color.RED));
            rockFactory.upgradePickaxe();
            scene.setCursor(new ImageCursor(rockFactory.getPickaxe().getImage(), 32, 32));
            refreshView();
        }
    }

    /**
     * Purchases the upgrade for the factory, or sends a toast if it fails.
     * @param img: the image for the upgrade
     * @param upgradeName: the name of the upgrade
     */
    private void purchaseUpgrade(ImageView img, String upgradeName){
        int missing_price = rockFactory.getSpecificUpgrade(upgradeName).getCost() - rockFactory.getScore();
        if (missing_price <= 0){
            rockFactory.addUpgrade(upgradeName);
            root.getChildren().remove(img);
        }
        else {
            toasts.add(new Toast(rockX, rockY, "Text", "Need " + missing_price, Color.PURPLE));
        }

    }

    /**
     * Refreshes all the GUI elements that update based on various parameters
     */
    private void refreshView(){
        checkEnablers();
        upgradePickBtn.setText("Upgrade Pick : $" + rockFactory.getPickaxeCost());
        purchaseWorkerBtn.setText("Purchase Worker : $" + rockFactory.getWorkerCost("Miner"));
        purchaseDrillBtn.setText("Purchase Drill : $" + rockFactory.getWorkerCost("Drill"));
        score.setText(String.valueOf(rockFactory.getScore()));
    }

    /**
     * Checks the states of each of the purchase buttons
     */
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

        generateUpgrades();



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
        mineRockBtn.setOnAction((event) -> mine());
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



        // generates the timer to redraw the canvas every 1/10 of a second
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
                            Toast incomeGen = new Toast((int) (rockX - Math.random() * 50), rockY, "Text", "+" + rockFactory.getTypeIncome("Miner"), Color.GREEN);
                            toasts.add(incomeGen);
                        }

                        if (rockFactory.getSpecificWorkersCount("Drill") > 0){
                            Toast incomeGen = new Toast((int) (rockX + Math.random() * 20), rockY, "Text", "+" + rockFactory.getTypeIncome("Drill"), Color.STEELBLUE);
                            toasts.add(incomeGen);
                        }
                    }

                    refreshView();


                    gc.clearRect(0, 0, 600, 300);


                    gc.drawImage(minebkgd, 0,0, 600, 300);
                    // Redraw income
                    gc.drawImage(paperBkgd, 0, 0, 100, 200);
                    gc.strokeText("Income per Second: " + income, oreCounterX, 290);

                    // initial instructions before user clicks on the rock
                    if (noClick){
                        gc.strokeText("Click on the rock to mine ore.", 195, 115);
                    }

                    if (shakeRock) {
                        gc.drawImage(oreBlock, rockX + Math.random(), rockY + Math.random());
                        shakeRock = false;
                    } else {
                        gc.drawImage(oreBlock, rockX, rockY);
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
     * This method generates the upgrade icons and functionality on the GUI
     */
    private void generateUpgrades(){
        sunglassesUpgImage = new UpgradeImageView(442, 185, 16, 16, sunglassesUpg);
        Tooltip t = new Tooltip("Miner Sunglasses $" + rockFactory.getSpecificUpgrade("Sunglasses").getCost()
                + "\nDoubles Miner efficiency");
        Tooltip.install(sunglassesUpgImage, t);
        sunglassesUpgImage.setOnMouseEntered((event) -> {
            sunglassesUpgImage.setOpacity(0.5);});
        sunglassesUpgImage.setOnMouseExited((event) -> {sunglassesUpgImage.setOpacity(1);});
        sunglassesUpgImage.setOnMouseClicked((event) -> {purchaseUpgrade(sunglassesUpgImage, "Sunglasses");});

        laserUpgImage = new UpgradeImageView(490, 185, 16, 16, laserUpg);
        Tooltip las = new Tooltip("Laser Sights $" + rockFactory.getSpecificUpgrade("Laser").getCost()
                + "\nDoubles Drill efficiency");
        Tooltip.install(laserUpgImage, las);
        laserUpgImage.setOnMouseEntered((event) -> {
            laserUpgImage.setOpacity(0.5);});
        laserUpgImage.setOnMouseExited((event) -> {laserUpgImage.setOpacity(1);});
        laserUpgImage.setOnMouseClicked((event) -> {purchaseUpgrade(laserUpgImage, "Laser");});

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
