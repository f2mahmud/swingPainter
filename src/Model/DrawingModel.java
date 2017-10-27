package Model;

import java.util.ArrayList;
import java.util.List;

import Others.Shape;
import Views.*;

import javax.vecmath.Point2d;

public class DrawingModel {

    private List<BaseView> views = new ArrayList<BaseView>();
    public List<Shape> shapes = new ArrayList<Shape>();

    private int x;
    private int y;
    private int numberOfShapes = 0;

    private int shapeInFocus = -1;
    private int latestId = 0;

    public DrawingModel(){};

    public void setXY(int xCoordinate, int yCoordinate){
        x = xCoordinate;
        y = yCoordinate;

        updateAllViews();
    }

    public void addView(BaseView view){
        views.add(view);
    }

    public int getShapeInFocus(){
        return shapeInFocus;
    }

    public void addShape(){

        if(numberOfShapes == 0 || (numberOfShapes > 0 && shapes.get(numberOfShapes-1).isFinished())) {
            Shape shape = new Shape(numberOfShapes);
            shapes.add(numberOfShapes, shape);
            latestId++;
            numberOfShapes++;
        }

        shapes.get(numberOfShapes-1).addPoint(new Point2d(x,y));
        shapes.get(numberOfShapes-1).setExtremePoints(x,y);
        updateAllViews();

    }

    public void finishStroke(){
        if(numberOfShapes > 0 && !shapes.get(numberOfShapes-1).isFinished()){
            shapes.get(numberOfShapes-1).setFinished(true);
            shapes.get(numberOfShapes-1).setUpCentres();
        }
    }

    public void removeShape(){

        shapes.remove(shapeInFocus);
        shapeInFocus = -1;
        updateAllViews();
        latestId = latestId+1;
        numberOfShapes--;

    }

    public void carryOutHitTests(){
        boolean shapeFound = false;
        for (int i = shapes.size()-1; i >= 0; i--){

            if(shapeFound){
                shapes.get(i).isFocused = false;
                continue;
            }
            if(shapes.get(i).hitTest(x,y)){
                shapes.get(i).isFocused = true;
                this.shapeInFocus = i;
                shapeFound = true;
            }

        }

        if(!shapeFound){
            this.shapeInFocus = -1;
        }
        updateAllViews();
    }

    public void doRotate(int angle) {
        if (shapeInFocus != -1) {
            shapes.get(shapeInFocus).setRotation(angle);
            updateAllViews();
        }
    }

    public void doScale(int factor) {
        if (shapeInFocus != -1) {
            shapes.get(shapeInFocus).setScale(factor);
            updateAllViews();
        }
    }

    private void updateAllViews(){
        for(BaseView view: this.views){
            view.updateView();
        }
    }




}
