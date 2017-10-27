package Controllers;

import Model.DrawingModel;

import java.awt.event.*;

public class CanvasController extends MouseAdapter{

    DrawingModel model;

    public CanvasController(DrawingModel model){
        this.model=model;
    }


    public void mouseDragged(MouseEvent e) {
        model.setXY(e.getX(),e.getY());
        model.addShape();
    }

    public void mouseReleased(MouseEvent e){
        model.finishStroke();
    }

    public void mouseClicked(MouseEvent e){
        model.setXY(e.getX(),e.getY());
        model.carryOutHitTests();
    }


}