import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Surface extends JFrame
{

    public int WINDOW_WIDTH = 800;
    public int WINDOW_HEIGHT = 800;

    public Surface()
    {
        initUI();
    }

    private void initUI()
    {
        Simulation sim = new Simulation(this);
        add(sim);

        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setTitle("Boids Simulation");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable()
        {

            @Override
            public void run() {

                Surface sur = new Surface();
                sur.setVisible(true);
            }
        });
    }
}
