import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 *
 * @author Colton Donkersgoed
 */
public class Miner extends Worker{

    public Miner(int x, int y){
        super(x, y);

    }

    public void draw(GraphicsContext gc){
        gc.setFill(Color.PURPLE);
        gc.fillOval(getX(), getY(), getSize(), getSize());
        gc.setFill(Color.BLACK);
    }

    public String getType(){
        return "Miner";
    }

}
