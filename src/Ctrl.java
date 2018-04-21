import java.awt.*;
import java.awt.event.InputEvent;
import java.util.Random;

class Ctrl {//Class used for recognition of the field and other stuff
    final private int[] coordxx = new int[]{827, 953, 1075, 828, 951, 1076, 827, 947, 1074}; // X screen coordinates
    final private int[] coordyx = new int[]{250, 250, 250, 371, 371, 371, 498, 498, 498};
    final private int[] coordxo = new int[]{827, 950, 1075, 828, 951, 1076, 827, 947, 1074}; //O screen coordinates
    final private int[] coordyo = new int[]{215, 215, 215, 339, 339, 339, 462, 462, 462};
    private Robot r; //Robot for recognition stuff

    Ctrl(int swait, int swork) throws AWTException, InterruptedException { //Constructor that makes all the work
        r = new Robot(); //Initialization of Robot
        Random ra = new Random(); //Random initialization
        Thread.sleep(swait * 1000); //Wait swait seconds before start
        Timer t = new Timer(); //Timer initialization for time count
        t.start(); //Starting timer
        while (t.time < swork) { //While time isnt over
            Thread.sleep(5); //Some slowdown to avoid lags
            presspos(ra.nextInt(9)); //press in random cell of the field
        }
        t.interrupt(); //stop the timer for programm to finish

    }

    private void presspos(int pos) { //Method for pressing cells
        r.mouseMove(coordxx[pos], coordyx[pos]); //move mouse to a cell
        r.mousePress(InputEvent.getMaskForButton(1)); //Press a cell
        r.mouseRelease(InputEvent.getMaskForButton(1)); //Release a mouse for the next press
    }

    private int getpos(int pos) { //Method for getting state of pos cell
        Color c = r.getPixelColor(coordxx[pos], coordyx[pos]); //Take middle color of pos cell
        Color c1 = r.getPixelColor(coordxo[pos], coordyo[pos]); //Take upper color of pos cell
        //Upper color is for 0 recognition, without it its impossible to detect whether its a 0 or X or its empty
        if (c.getRed() == 0 && c.getGreen() == 0 && c.getBlue() == 0) { // if middle of cell is black
            if (c1.getRed() == 255 && c1.getGreen() == 255 && c1.getBlue() == 255) { //if upper of cell is white
                return 2; //2 is for 0 cell
            } else { //if upper of cell is dark
                return 0; //0 is for empty cell
            }
        } else { //if middle of cell is white
            return 1; //1 is for X cell
        }
    }
}

class Timer extends Thread { //Timer in another thread for time counting
    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(1000);
                time++;
            }
        } catch (InterruptedException e) {
        }
    }

    int time = 0;
}
