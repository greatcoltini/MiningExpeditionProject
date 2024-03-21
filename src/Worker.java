import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Worker {
    private static int totalMined;
    private int x, y;
    private int size;
    private int amount;


    public Worker(int x, int y){
        this.x = x;
        this.y = y;
        this.size = 10;
        this.amount = 1;
    }

    public void draw(GraphicsContext gc){
        gc.setFill(Color.PURPLE);
        gc.fillOval(x, y, this.size, this.size);
        gc.setFill(Color.BLACK);
    }

    public int mine(){
        totalMined += this.amount;
        return this.amount;

    }

}
