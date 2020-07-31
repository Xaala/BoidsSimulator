import java.lang.Math.*;

import static java.lang.Math.*;
import static java.lang.StrictMath.abs;

public class Vector2d
{
    public float x;
    public float y;

    public Vector2d(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public Vector2d(Vector2d other)
    {
        this.x = other.x;
        this.y = other.y;
    }

    public Vector2d(float mag, float angle)
    {
        x = (float)(mag * Math.cos(angle));
        y = (float)(mag * Math.sin(angle));
    }

    public void setCord(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public boolean equals(Vector2d other)
    {
        return (this.x == other.x && this.y == other.y);
    }

    public void normalize()
    {
        double length = sqrt(x*x + y*y);

        if (length != 0.0)
        {
            float s = 1.0f / (float)length;
            x = x*s;
            y = y*s;
        }
    }

    public void scale(float mag)
    {
        this.x = x * mag;
        this.y = y * mag;
    }

    public void add(Vector2d other)
    {
        this.x = this.x + other.x;
        this.y = this.y + other.y;

    }

    public float findAngleBetween(Vector2d other)
    {
        //TODO: Implement this at some point
        return 0.0f;
    }

    public float findAngleOfApproach(Vector2d other)
    {
        double adjacentSide = abs(this.x - other.x) ;;
        double oppositeSide = abs(this.y - other.y) ; //oposite side is delta( current.y, pacp.y);

        return (float)tan(adjacentSide/oppositeSide);
    }

    public static double distance(Vector2d a, Vector2d b)
    {
        float v0 = b.x - a.x;
        float v1 = b.y - a.y;

        return sqrt(v0*v0 + v1*v1);
    }


}



