package Views;

import Model.DrawingModel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.text.NumberFormat;

public class ToolbarView extends JPanel {

    private DrawingModel model;

    private JLabel scaleFactor = new JLabel("0.0");
    private JLabel rotateAngle = new JLabel("0");
    private final JLabel rotate = new JLabel("Rotate");
    private final JLabel scale = new JLabel("Scale");
    private JButton deleteButton = new JButton("Delete");
    private JSlider scaleSlider = new JSlider(5, 20);
    private JSlider rotationSlider = new JSlider(-180, 180);

    private NumberFormat formatter = NumberFormat.getNumberInstance();

    public ToolbarView(DrawingModel model){

        super();
        this.model = model;

        this.layoutView();

        this.model.addView(new BaseView() {
            public void updateView() {

                if(model.getShapeInFocus() == -1) {
                    updateValues(false);
                }
                else{
                    updateValues(true);
                }

            }

        });

        this.setUpWidgets();
        updateValues(false);

    }


    private void setUpWidgets(){

        this.rotationSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int base = rotationSlider.getValue();
                rotateAngle.setText(String.valueOf(base));
                model.doRotate(base);
            }
        });

        this.scaleSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int base = scaleSlider.getValue();
                scaleFactor.setText(String.valueOf((float) base/10));
                model.doScale(base);
            }
        });

        this.deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.removeShape();
            }
        });

    }

    private void layoutView(){

//        this.setLayout(new FlowLayout(FlowLayout.LEFT,-50,5));
        this.add(deleteButton);
        this.setLayout(new FlowLayout(FlowLayout.LEFT,50,5));
        this.add(this.groupComponents(scaleSlider,scale ,scaleFactor));
        this.add(this.groupComponents(rotationSlider,rotate, rotateAngle));

        Dimension d = this.scaleSlider.getPreferredSize();
        d.width = 200;
        this.scaleSlider.setPreferredSize(d);
        this.rotationSlider.setPreferredSize(d);

    }

    private void updateValues(boolean state){

        scale.setEnabled(state);
        rotateAngle.setEnabled(state);
        scaleFactor.setEnabled(state);
        rotate.setEnabled(state);
        deleteButton.setEnabled(state);
        scaleSlider.setEnabled(state);
        rotationSlider.setEnabled(state);

        if(state){

            Others.Shape shapeInFocus = model.shapes.get(model.getShapeInFocus());

            scaleFactor.setText(formatter.format((float)shapeInFocus.getScale()/10));
            if(shapeInFocus.getScale() == 10){
                scaleFactor.setText("1.0");
            }else if(shapeInFocus.getScale() == 20){
                scaleFactor.setText("2.0");
            }
            scaleSlider.setValue(((shapeInFocus.getScale()*10))/10);
            rotateAngle.setText(formatter.format(shapeInFocus.getRotation()));
            rotationSlider.setValue(shapeInFocus.getRotation());

        }else{

            scaleFactor.setText("1.0");
            scaleSlider.setValue(1);
            rotateAngle.setText("0");
            rotationSlider.setValue(0);

        }
    }

    private Box groupComponents(JSlider slider,JLabel label, JLabel value){
        Box group = Box.createHorizontalBox();
        group.add(label);
        group.add(slider);
        group.add(value);

        Dimension d = slider.getPreferredSize();
        slider.setPreferredSize(d);

        return  group;
    }

}
