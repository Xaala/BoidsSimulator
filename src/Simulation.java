import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Simulation extends JPanel implements ActionListener
{
    private final int TIMER_DELAY = 2; //50ms
    private Timer timer;

    public int boidCount = 35;

    private Surface parent;

    List<Boid> simulatedBoids;

    public int WINDOW_WIDTH;
    public int WINDOW_HEIGHT;

    public Simulation(Surface parent){
        this.parent = parent;
        initSim();

        this.WINDOW_HEIGHT = parent.WINDOW_HEIGHT;
        this.WINDOW_WIDTH = parent.WINDOW_WIDTH;
    }

    private void initSim() {
        timer = new Timer(TIMER_DELAY, this);
        timer.start();

        simulatedBoids = new ArrayList<Boid>();

        for (int i = 0; i < boidCount; i++)
        {
//            simulatedBoids.add(new Boid(ThreadLocalRandom.current().nextInt() % parent.WINDOW_WIDTH,
//                    ThreadLocalRandom.current().nextInt() %parent.WINDOW_HEIGHT,
//                    i,
//                    new Color(ThreadLocalRandom.current().nextInt(255), ThreadLocalRandom.current().nextInt(255), ThreadLocalRandom.current().nextInt(255)),
//                    this));

            simulatedBoids.add(new Boid(ThreadLocalRandom.current().nextInt() % parent.WINDOW_WIDTH,
                    100,
                    i,
                    new Color(ThreadLocalRandom.current().nextInt(255), ThreadLocalRandom.current().nextInt(255), ThreadLocalRandom.current().nextInt(255)),
                    this));

            //System.out.println("Adding boid at 100, 0");
            //simulatedBoids.add(new Boid(300, 300, this));
        }

    }

    public void doDrawing(Graphics2D g2d)
    {
        RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHints(rh);

        for (int i = 0; i < simulatedBoids.size(); i++)
        {
            simulatedBoids.get(i).draw(g2d); //draw next frame.
        }
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {

        //Update boid position
        for (int i = 0; i < simulatedBoids.size(); i++)
        {
            simulatedBoids.get(i).calculateMove(simulatedBoids);
        }

        //Render code here?
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D gfx = (Graphics2D)g;

        doDrawing(gfx);
    }
}
