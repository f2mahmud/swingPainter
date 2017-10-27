package Views;

import Model.DrawingModel;

import javax.swing.*;
import java.awt.*;

public class StatusBarView extends JPanel {

    DrawingModel model;

    JLabel numberOfStrokes;
    JLabel onFocus;


    public StatusBarView(DrawingModel model){

        this.model = model;

        numberOfStrokes = new JLabel(0 + " stroke");
        onFocus = new JLabel();

        numberOfStrokes.setAlignmentY(SwingConstants.LEFT);

        this.add(numberOfStrokes);

        model.addView(new BaseView() {
            @Override
            public void updateView() {
                repaint();
            }
        });
    }


    public void paintComponent(Graphics graphics){

        //TODO::Merge the 2 JLabels into 1
        super.paintComponent(graphics);
        int strokes = model.shapes.size();
        numberOfStrokes.setText(strokes + " strokes");

        if(strokes == 1){
            numberOfStrokes.setText(strokes + " stroke");
        }

        if(model.getShapeInFocus() != -1){
            Others.Shape shapeInFocus = model.shapes.get(model.getShapeInFocus());
            onFocus.setText(", Selection (" + shapeInFocus.getNumberOfPoints() + " points, scale : " +
                    (float)shapeInFocus.getScale()/10 + ", rotation " + shapeInFocus.getRotation() + ")");
            this.add(onFocus);
        }
        else{
            onFocus.setText("");
        }

    }

}
