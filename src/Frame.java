import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;

public class Frame extends JFrame{
	private Canvas canvas;
	private JPanel bottomPanel, subPanelA, subPanelB, gConstantPanel;
	private JButton pause, clear, createDust;
	private JComboBox collisionType, drawType;
	private JCheckBox boundaries, showV, showPaths;
	private JSlider gConstant;
	private JTextField gConstantField, credit;
	
	public Frame(){
		super("Gravitation Simulation");
		Refs.setFrame(this);
		this.setResizable(false);
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}});
		
		canvas = new Canvas(1024, 660);
		this.getContentPane().add(canvas, BorderLayout.CENTER);
		bottomPanel = new JPanel();
		
		
		
		//subPanelA
		subPanelA = new JPanel();
		subPanelA.setLayout(new GridLayout(2, 3));
		
		pause = new JButton("Pause");
		subPanelA.add(pause);
		pause.addMouseListener(new MListener(MListener.PAUSE));
		
		String[] types1 = {"Absorb", "Collide*", "Noncollide*"};
		collisionType = new JComboBox(types1);
		subPanelA.add(collisionType);
		
		createDust = new JButton("Create Dust");
		subPanelA.add(createDust);
		createDust.addMouseListener(new MListener(MListener.CREATE_DUST));
		
		clear = new JButton("Clear");
		subPanelA.add(clear);
		clear.addMouseListener(new MListener(MListener.CLEAR));
		
		String[] types2 = {"Ball", "Black Hole"};
		drawType = new JComboBox(types2);
		subPanelA.add(drawType);
		
		bottomPanel.add(subPanelA);
		
		
		
		//gConstantPanel
		gConstantPanel = new JPanel();
		gConstantPanel.setLayout(new GridLayout(2, 1));
		
		gConstantField = new JTextField("Gravitation Constant");
		gConstantField.setEditable(false);
		gConstantField.setSelectionColor(Color.GREEN);
		gConstantPanel.add(gConstantField);
		
		gConstant = new JSlider(JSlider.HORIZONTAL, 1, 10, 1);
		gConstant.setMajorTickSpacing(1);
		gConstant.setSnapToTicks(true);
		gConstant.setPaintLabels(false);
		gConstant.setPaintTicks(true);
		gConstant.setPaintTrack(true);
		gConstant.setValue(5);
		gConstantPanel.add(gConstant);
		
		bottomPanel.add(gConstantPanel);
		
		
		//subPanelB
		subPanelB = new JPanel();
		subPanelB.setLayout(new GridLayout(3, 1));
		
		boundaries = new JCheckBox("Boundaries");
		subPanelB.add(boundaries);
		
		showV = new JCheckBox("Velocity");
		subPanelB.add(showV);
		
		showPaths = new JCheckBox("Paths");
		subPanelB.add(showPaths);
		
		bottomPanel.add(subPanelB);
		
		
		
		
		credit = new JTextField("Created by Evan Williams, 15-23 March 2012");
		credit.setEditable(false);
		bottomPanel.add(credit);
		
		
		this.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
		this.pack();
		this.setLocationRelativeTo(null);
		canvas.requestFocus();
		this.setVisible(true);
	}
	
	public int getSelectedCollisionType(){
		return collisionType.getSelectedIndex();
	}
	
	public int getSelectedDrawType(){
		return drawType.getSelectedIndex();
	}
	
	public boolean isBound(){
		return boundaries.isSelected();
	}
	
	public boolean showVelocity(){
		return showV.isSelected();
	}
	
	public boolean showPaths(){
		return showPaths.isSelected();
	}
	
	public double getGravitationConstant(){
		return .1*gConstant.getValue();
	}
}