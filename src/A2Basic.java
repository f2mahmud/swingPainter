import Controllers.CanvasController;
import Model.DrawingModel;
import Views.CanvasView;
import Views.StatusBarView;
import Views.ToolbarView;

import javax.swing.*;
import java.awt.*;


public class A2Basic {

    public static void main(String[] args) {

        //TODO:: Figure out how to remove bounding box
        //TODO:: Add Scroll

        DrawingModel model = new DrawingModel();
        ToolbarView toolbarView = new ToolbarView(model);
        StatusBarView statusBarView = new StatusBarView(model);
        CanvasController canvasController = new CanvasController(model);
        CanvasView canvasView = new CanvasView(model,canvasController);

        JFrame frame = new JFrame("A2Basic");

        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(toolbarView, BorderLayout.NORTH);
        frame.getContentPane().add(canvasView, BorderLayout.CENTER);
        frame.getContentPane().add(statusBarView, BorderLayout.SOUTH);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.pack();
        frame.setSize(800,600);

        frame.setVisible(true);

    }


}
