import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Drill extends Worker{

    private Image img = new Image(new FileInputStream("src/assets/drillWorker.png"));

    public Drill (int x, int y) throws FileNotFoundException {
        super(x, y);
        this.setAmount(5);
    }

    public void draw(GraphicsContext gc){
        gc.drawImage(img, getX(), getY());
    }


}
