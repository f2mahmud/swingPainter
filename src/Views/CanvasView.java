package Views;

import Controllers.CanvasController;
import Model.DrawingModel;

import javax.swing.*;
import java.awt.*;

public class CanvasView extends JPanel{

    DrawingModel model;

    public CanvasView(DrawingModel model, CanvasController controller){

        this.model = model;

        this.addMouseListener(controller);
        this.addMouseMotionListener(controller);

        this.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));

        model.addView(new BaseView() {
            public void updateView() {
                repaint();
            }
        });


    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2));
        for(int i = 0; i < model.shapes.size(); i++){
            model.shapes.get(i).draw(g2);
        }
    }



}
