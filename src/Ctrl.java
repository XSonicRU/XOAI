import java.awt.*;
import java.awt.event.InputEvent;
import java.util.Random;
import java.util.Vector;

class Ctrl {//Class used for recognition of the field
    final private int[] coordxx = new int[]{827, 953, 1075, 828, 951, 1076, 827, 947, 1074}; // X screen coordinates
    final private int[] coordyx = new int[]{250, 250, 250, 371, 371, 371, 498, 498, 498};
    final private int[] coordxo = new int[]{827, 950, 1075, 828, 951, 1076, 827, 947, 1074}; //O screen coordinates
    final private int[] coordyo = new int[]{215, 215, 215, 339, 339, 339, 462, 462, 462};
    private Robot r;

    Ctrl(int swait, int swork) throws AWTException, InterruptedException {
        r = new Robot();
        Random ra = new Random();
        Vector<Integer> v;
        Thread.sleep(swait * 1000);
        Timer t = new Timer();
        t.start();
        while (t.time < swork) {
            Thread.sleep(5);
            presspos(ra.nextInt(9));
        }
        t.interrupt();

    }

    private Vector<Integer> getFreeSpaces() {
        Vector<Integer> res = new Vector<>();
        for (int i = 0; i < 9; i++) {
            if (getpos(i) == 0) {
                res.add(i);
            }
        }
        return res;
    }

    private void presspos(int pos) {
        r.mouseMove(coordxx[pos], coordyx[pos]);
        r.mousePress(InputEvent.getMaskForButton(1));
        r.mouseRelease(InputEvent.getMaskForButton(1));
    }

    private int getpos(int pos) {
        Color c = r.getPixelColor(coordxx[pos], coordyx[pos]);
        Color c1 = r.getPixelColor(coordxo[pos], coordyo[pos]);
        if (c.getRed() == 0 && c.getGreen() == 0 && c.getBlue() == 0) {
            if (c1.getRed() == 255 && c1.getGreen() == 255 && c1.getBlue() == 255) {
                return 2;
            } else {
                return 0;
            }
        } else {
            return 1;
        }
    }
}

class Timer extends Thread {
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
