import java.awt.*;
import java.awt.geom.*;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.StrictMath.atan;


//A "Bird Like Object", contains logic for movement as well as rendering.
public class Boid
{

    private double viewRadius;
    private float viewAngle;

    private float orientation;
    private Vector2d currentVelocity;
    private Vector2d currentPosition;


    private float velocity;

    private Simulation parent;

    private int boidId;

    private float ALIGNMENT_MODIFIER_MAGNITUDE = 1;
    private float COHESION_MODIFIER_MAGNITUDE = 1;
    private float SEPARATION_MODIFIER_MAGNITUDE = 1;

    private Color bodyColor;


    public Boid(int x, int y, int boidId, Color bodyColor, Simulation parent)
    {
        this.bodyColor = bodyColor;
        currentPosition = new Vector2d(x, y);
        this.parent = parent;
        this.boidId = boidId;

        orientation = (float)Math.random() * 360; //Facing true
        velocity = 3.0f; //Default velocity of 2 px
        currentVelocity = new Vector2d(velocity, orientation);
        viewAngle = 270.0f;
        viewRadius = 75.0; //75px

    }

    public void calculateMove(List<Boid> boids)
    {

        Vector2d alignmentVelocity = processAlignment(boids);
        alignmentVelocity.scale(ALIGNMENT_MODIFIER_MAGNITUDE);
        //Vector2d cohesionVelocity = processCohesion(boids);
        //cohesionVelocity.scale(COHESION_MODIFIER_MAGNITUDE);
        Vector2d separationVelocity = processSeparation(boids); //seperation-type rules last
        separationVelocity.scale(SEPARATION_MODIFIER_MAGNITUDE);
        //processCollisionAvoidance(boids);

        processWrapAround();

        //process our new movement
        currentVelocity.add(alignmentVelocity);
        //currentVelocity.add(cohesionVelocity);
        currentVelocity.add(separationVelocity);

        currentVelocity.normalize();
        currentVelocity.scale(velocity);

        currentPosition.add(currentVelocity);

        //Update current orientation based on our currentVelocity
        orientation = (float)Math.atan2(currentVelocity.y, currentVelocity.x);
    }

    //Avoid smacking into other Boids
    private Vector2d processSeparation(List<Boid> boids)
    {
        Vector2d aggregationVector = new Vector2d(0,0);
        int neighbourCount = 0;

        for (int i = 0; i < boids.size(); i++) {
            if (i != boidId) //Skip self
            {
                if (Vector2d.distance(this.currentPosition, boids.get(i).currentPosition) < viewRadius/2) {
                    //TODO: Add better separation rule
                    System.out.println("Attempting to separate..." + boidId + " and " + i);
                    neighbourCount++;
                    aggregationVector.x += boids.get(i).currentPosition.x - currentPosition.x;
                    aggregationVector.y += boids.get(i).currentPosition.y - currentPosition.y;

                }
            }
        }

        if (neighbourCount == 0)
        {
            return currentVelocity;
        }
        else
        {
            aggregationVector.scale(1/neighbourCount); //Scale it by 1/number nearby boids.

            Vector2d returnVector = new Vector2d(aggregationVector.x, aggregationVector.y);
            returnVector.normalize();
            returnVector.x *= -1;
            returnVector.y *= -1;

            return returnVector;
        }

    }

    //Tend to travel in the same direction as other nearby Boids
    private Vector2d processAlignment(List<Boid> boids)
    {
        Vector2d returnVector = new Vector2d(0,0);
        int neighbourCount = 0;

        for (int i = 0; i < boids.size(); i++) {
            if (i != boidId) //Skip self
            {
                if (Vector2d.distance(this.currentPosition, boids.get(i).currentPosition) < viewRadius) {
                    //TODO: Add better alignment rule
//                    System.out.println("Attempting to align..." + boidId + " and " + i);
                    neighbourCount++;
                    returnVector.add(boids.get(i).currentVelocity);

                    //Adopt color.
                    this.bodyColor = boids.get(i).getBodyColor();
                }
            }
        }

        if (neighbourCount == 0)
        {
            return currentVelocity;
        }
        else
        {
            returnVector.scale(1/neighbourCount); //Scale it by 1/number nearby boids.
            returnVector.normalize(); //Normalize return vector.

            return returnVector;
        }
    }

    //Tend to move towards center of mass of Boids in viewRadius
    private Vector2d processCohesion(List<Boid> boids)
    {
        Vector2d aggregationVector = new Vector2d(currentVelocity);
        int neighbourCount = 1;

        for (int i = 0; i < boids.size(); i++) {
            if (i != boidId) //Skip self
            {
                if (Vector2d.distance(this.currentPosition, boids.get(i).currentPosition) < viewRadius) {
                    //TODO: Add better alignment rule
                    neighbourCount++;
                    aggregationVector.add(boids.get(i).currentVelocity);
                }
            }
        }

        aggregationVector.scale(1/neighbourCount); //Scale it by 1/number nearby boids.

        Vector2d returnVector = new Vector2d(aggregationVector.x, aggregationVector.y);
        returnVector.normalize();
        return returnVector;
    }

    //Avoid collision with Non-Boids in viewRadius
    private void processCollisionAvoidance(List<Boid> boids)
    {

    }

    /**
     * Contains logic to keep boid from going off screen
     */
    private void processWrapAround()
    {
        //Process top Y wrap around
        if (currentPosition.y <= 0.0f)
        {
            currentPosition.y = parent.WINDOW_HEIGHT;
        }

        //Process bottom Y wrap around
        if (currentPosition.y > parent.WINDOW_HEIGHT)
        {
            currentPosition.y = 0.0f;
        }

        //Process left X wrap around
        if (currentPosition.x <= 0.0f)
        {
            currentPosition.x = parent.WINDOW_WIDTH;
        }

        //process right X wrap around
        if (currentPosition.x > parent.WINDOW_WIDTH)
        {
            currentPosition.x = 0.0f;
        }
    }

    //Render Boid
    public void draw(Graphics2D g)
    {
        Graphics2D g2d = (Graphics2D) g.create();
        //Make shape at current x,y cordinates
        AffineTransform old = g2d.getTransform();

        g2d.setColor(bodyColor);

        //Body
        Rectangle body = new Rectangle((int)currentPosition.x, (int)currentPosition.y, 12, 12);

        //Left Wing

        //Right Wing

        //Tail

        //Direction of Movement line
        //get curret orientation
        Vector2d newTip = new Vector2d(24.0f, orientation); //Vector pointing in direction of travel
        Vector2d augmentedCurrent = new Vector2d(currentPosition);
        augmentedCurrent.x += 6;
        augmentedCurrent.y += 6;
        newTip.add(augmentedCurrent); //Add current position to it to get NT+CP
        Line2D line = new Line2D.Float(augmentedCurrent.x, augmentedCurrent.y, newTip.x, newTip.y); //Draw line from middle of body to the tip we're going in


        //Rotate shape to facingAngle

        Vector2d centerOfBoid = new Vector2d(currentPosition);
        centerOfBoid.x += 6;
        centerOfBoid.y += 6;

        //g2d.rotate(Math.toRadians(orientation), (int)centerOfBoid.x, (int)centerOfBoid.y);
        g2d.draw(body);
        g2d.fill(body);

        g2d.transform(old);

        g2d.setColor(Color.RED);
        g2d.draw(line);
        g2d.dispose();
    }

    public Color getBodyColor() {
        return bodyColor;
    }
}
