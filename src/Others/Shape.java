package Others;

import javax.vecmath.*;
import java.awt.*;
import java.util.ArrayList;

public class Shape {

    private int shapeId;
    private int leftMostPoint;
    private int rightMostPoint;
    private int upperMostPoint;
    private int lowerMostPoint;
    private int scale = 10;
    private int rotation = 0;
    private int centreX;
    private int centreY;

    private int[] XPoints, YPoints;
    private int nPoints = 0;

    ArrayList<Point2d> points;

    private boolean finished = false;
    public boolean isFocused = false;

    public Shape(int id){
        this.shapeId = id;
        points = new ArrayList<Point2d>();
    }

    public int getScale(){
        return scale;
    }

    public int getRotation(){
        return rotation;
    }

    public void setScale(int scale){
        this.scale = scale;
    }

    public void setRotation(int angle){
        this.rotation = angle;
    }

    public void setUpCentres(){
        centreX = leftMostPoint + ((rightMostPoint - leftMostPoint) / 2);
        centreY = upperMostPoint + ((lowerMostPoint - upperMostPoint) / 2);
    }

    public int getNumberOfPoints(){
        return points.size();
    }

    // add a point to end of shape
    public void addPoint(Point2d p) {
        points.add(p);
        cachePointsArray();
    }

    public boolean isFinished(){
        return finished;
    }

    public void setFinished(boolean status){
        finished = status;
    }

    void cachePointsArray() {
        XPoints = new int[points.size()];
        YPoints = new int[points.size()];
        for (int i=0; i < points.size(); i++) {
            XPoints[i] = (int)points.get(i).x;
            YPoints[i] = (int)points.get(i).y;
        }
        nPoints = points.size();
    }

    private void updateBoundingBox(){
        //TODO:: Make strokes responsive once rotaion and scale has been made
        leftMostPoint = (leftMostPoint * 10)  / scale;
        rightMostPoint = (rightMostPoint / 10) * scale;
        upperMostPoint = (upperMostPoint * 10) / scale;
        lowerMostPoint = (lowerMostPoint /10) * scale;

    }

    public void setExtremePoints(int x, int y){
        if(points.size() == 1){
            leftMostPoint = x - 5;
            rightMostPoint = x + 5;
            upperMostPoint = y - 5;
            lowerMostPoint = y + 5;
        }

        if(x > rightMostPoint){
            rightMostPoint = x + 5;
        }

        if(x < leftMostPoint){
            leftMostPoint = x - 5;
        }

        if (y > lowerMostPoint){
            lowerMostPoint = y + 5;
        }
        if(y < upperMostPoint){
            upperMostPoint = y - 5;
        }

    }

    // Taken from closestPoint.cpp in class
    static Point2d closestPoint(Point2d M, Point2d P0, Point2d P1) {
        Vector2d v = new Vector2d();
        v.sub(P1,P0); // v = P2 - P1

        // early out if line is less than 1 pixel long
        if (v.lengthSquared() < 0.5)
            return P0;

        Vector2d u = new Vector2d();
        u.sub(M,P0); // u = M - P1

        // scalar of vector projection ...
        double s = u.dot(v) / v.dot(v);

        // find point for constrained line segment
        if (s < 0)
            return P0;
        else if (s > 1)
            return P1;
        else {
            Point2d I = P0;
            Vector2d w = new Vector2d();
            w.scale(s, v); // w = s * v
            I.add(w); // I = P1 + w
            return I;
        }
    }

    public boolean hitTest(double x, double y) {
        if ((x > rightMostPoint && x < leftMostPoint) || (y > lowerMostPoint && y < upperMostPoint)) {
            return false;
        }
        Point2d clickedPoint = new Point2d(x,y);
        Point2d closestPoint = new Point2d(0,0);
        for(int i = 0; i < points.size() - 1; i++){
            closestPoint = closestPoint(closestPoint,points.get(i),points.get(i+1));
            if(clickedPoint.distance(closestPoint) <= 5){
                return true;
            }
        }

        this.isFocused = false;
        return false;
    }


    public void draw(Graphics2D g2){

        Graphics g = g2.create();
        Graphics2D g2a= (Graphics2D) g;

        g2a.translate(centreX,centreY);
        g2a.rotate((float) Math.toRadians(rotation));
        g2a.scale((float)scale/10,(float)scale/10);
        g2a.translate(-centreX,-centreY);

        updateBoundingBox();

        if(isFocused){
            g2a.setColor(Color.YELLOW);
            g2a.setStroke(new BasicStroke(6));
            g2a.drawPolyline(XPoints, YPoints, nPoints);
        }

        g2a.setColor(Color.BLACK);
        g2a.setStroke(new BasicStroke(2));
        g2a.drawPolyline(XPoints, YPoints, nPoints);

        g2a.dispose();
        g.dispose();

    }

}
