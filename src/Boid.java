import java.awt.*;
import java.awt.geom.*;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

//A "Bird Like Object", contains logic for movement as well as rendering.
public class Boid
{

    private double viewRadius;
    private float viewAngle;

    private float orientation;
    private Vector2d currentPosition;
    private float velocity;

    private Simulation parent;


    public Boid(int x, int y, Simulation parent)
    {
        currentPosition = new Vector2d(x, y);
        this.parent = parent;

        orientation = (float)Math.random() * 360; //Facing true
        velocity = 2.0f; //Default velocity of 2 px
        viewAngle = 270.0f;
        viewRadius = 50.0; //50px
    }

    public void calculateMove()
    {
        System.out.println("Calculating move.");

        processSeparation();
        processAlignment();
        processCohesion();
        processCollisionAvoidance();
        processWrapAround();

    }

    //Avoid smacking into other Boids
    private void processSeparation()
    {
        //Check if any boids are nearby within our field of view
        if (false)
        {

        }
        //if not keep going in the same direction
        else
        {
            Vector2d movement = new Vector2d(velocity, orientation);
            currentPosition.add(movement);
        }
    }

    //Tend to travel in the same direction as other nearby Boids
    private void processAlignment()
    {

    }

    //Tend to move towards center of mass of Boids in viewRadius
    private void processCohesion()
    {

    }

    //Avoid collision with Non-Boids in viewRadius
    private void processCollisionAvoidance()
    {

    }

    /**
     * Contains logic to keep boid from going off screen
     */
    private void processWrapAround()
    {
        System.out.println("Near Boarder Current Coords:");
        System.out.println("X: " + currentPosition.x);
        System.out.println("Y: " + currentPosition.y);
//        System.out.println("WINDOW_WIDTH: " + parent.WINDOW_WIDTH);
//        System.out.println("WINDOW_HEIGHT: " + parent.WINDOW_HEIGHT);
        //Process top Y wrap around
        if (currentPosition.y <= 0.0f)
        {
            System.out.println("U");
            currentPosition.y = parent.WINDOW_HEIGHT;
        }

        //Process bottom Y wrap around
        if (currentPosition.y > parent.WINDOW_HEIGHT)
        {
            System.out.println("D");
            currentPosition.y = 0.0f;
        }

        //Process left X wrap around
        if (currentPosition.x <= 0.0f)
        {
            System.out.println("L");
            currentPosition.x = parent.WINDOW_WIDTH;
        }

        //process right X wrap around
        if (currentPosition.x > parent.WINDOW_WIDTH)
        {
            System.out.println("R");
            currentPosition.x = 0.0f;
        }
    }

    //Render Boid
    public void draw(Graphics2D g)
    {
        Graphics2D g2d = (Graphics2D) g.create();
        //Make shape at current x,y cordinates
        AffineTransform old = g2d.getTransform();

        g2d.setColor(Color.BLUE);

        //Body
        Rectangle body = new Rectangle((int)currentPosition.x, (int)currentPosition.y, 12, 12);


        //Left Wing

        //Right Wing

        //Tail

        //Direction of Movement line
        Vector2d newTip = new Vector2d(12.0f, orientation);
        Line2D line = new Line2D.Float(currentPosition.x+6, currentPosition.y+6, newTip.x, newTip.y);
        //Rotate shape to facingAngle
        g2d.rotate(Math.toRadians(orientation), (int)currentPosition.x+6, (int)currentPosition.y+6);
        g2d.draw(body);
        g2d.fill(body);

        g2d.transform(old);
        g2d.draw(line);
        g2d.dispose();
    }

}
