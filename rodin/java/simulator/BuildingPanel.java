package simulator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import simulator.CableEngine.CABLE_COMMAND;
import simulator.Controller.DOOR_POSITION;
import simulator.DoorEngine.DOOR_COMMAND;

import static simulator.CableEngine.CABLE_COMMAND.*;
import static simulator.DoorEngine.DOOR_COMMAND.*;
import static simulator.Controller.DOOR_POSITION.*;


/**
 * @author lvoisin
 *
 * A Swing container that renders a Building
 */
class BuildingPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final static int FLOOR_HEIGHT = 120;
	private final static int FLOOR_WIDTH = 120;
	private final static int FLOOR_BASE = 49;
	
	private final static int ENGINE_DIAMETER = 30;
	private final static int ENGINE_HEIGHT = 60;
	private final static int ENGINE_WIDTH = 60;

	private final static int ELEVATOR_HEIGHT = 100;
	private final static int ELEVATOR_WIDTH = 60;
	private final static int ELEVATOR_DEPTH = 13;
	
	private final static int LEFT = 0;
	private final static int CENTER = 1;
	private final static int RIGHT = 2;
	
	private final static Dimension size = new Dimension(2 * FLOOR_WIDTH + ELEVATOR_WIDTH,
														(Controller.LAST_FLOOR + 1) * FLOOR_HEIGHT + ENGINE_HEIGHT + 10);
	private final static int cableX = FLOOR_WIDTH + ELEVATOR_WIDTH / 2;
	private final static int cableUpperY = ENGINE_DIAMETER / 2;
	
	private final static int cableArrowX = FLOOR_WIDTH + ENGINE_WIDTH / 3;
	private final static int cableArrowTop = 5;
	private final static int cableArrowBot = 5 + ENGINE_HEIGHT / 3;
	
	private final static int engineDelay = 200;
	private final static int cableEngineIncrement = 10;
	private final static int doorEngineIncrement = 10;
	
    private final static Insets buttonMargin = new Insets(2, 2, 2, 2); 
    private final static Insets floorButtonMargin = new Insets(2, 6, 2, 6); 
    
	private Elevator elevator;
	private CableEngine cableEngine;
	private DoorEngine doorEngine;
	
	private Timer engineAnimation;
	
	private JButton upButtons[];
	private JButton downButtons[];
	private JButton floorButtons[];
	
    public BuildingPanel(final Controller controller) {
        super();
        controller.setViewer(this);

        cableEngine = new CableEngine();
        cableEngine.setViewer(this);
        cableEngine.setStatus(CABLE_STOP);
        
        doorEngine = new DoorEngine();
        doorEngine.setViewer(this);
        doorEngine.setStatus(DOOR_STOP);

        elevator = new Elevator(cableEngine, doorEngine);
        elevator.setViewer(this);
        
        setLayout(null);
        setMinimumSize(size);
        setMaximumSize(size);
        setPreferredSize(size);
        
        engineAnimation = new Timer(engineDelay, null);
        engineAnimation.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (cableEngine.getCableStatus() == CABLE_WIND) {
                	elevator.move(cableEngineIncrement);
                }
                else if (cableEngine.getCableStatus() == CABLE_UNWIND) {
                	elevator.move(- cableEngineIncrement);
                }
                if (doorEngine.getDoorStatus() == DOOR_OPEN) {
                	elevator.open(doorEngineIncrement);
                }
                else if (doorEngine.getDoorStatus() == DOOR_CLOSE) {
                	elevator.open(- doorEngineIncrement);
                }
                if (   elevator.getPercentAbove() == 0
                	|| elevator.getPercentOpen() == 0
					|| elevator.getPercentOpen() == 100) {
                	controller.react();
                }
            }
        });

        ChangeListener buttonListener = new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				controller.react();
			}
        };
        
        upButtons = new JButton[Controller.LAST_FLOOR + 1];
        ImageIcon icon = createImageIcon("images/up.png", "Up");
        for (int i = 0; i < Controller.LAST_FLOOR; ++i) {
	        int xPos = FLOOR_WIDTH / 2;
	        int yPos = FLOOR_BASE + (Controller.LAST_FLOOR - i + 1) * FLOOR_HEIGHT;
        	JButton button = makeButton(buttonListener, null, icon, xPos, yPos, LEFT);
			button.setActionCommand("u" + i);
			button.setOpaque(true); // By pass look and feel effect.
	        upButtons[i] = button;
        }
    
        downButtons = new JButton[Controller.LAST_FLOOR + 1];
        icon = createImageIcon("images/down.png", "Down");
        for (int i = 1; i <= Controller.LAST_FLOOR; ++i) {
	        int xPos = FLOOR_WIDTH / 2;
	        int yPos = FLOOR_BASE + (Controller.LAST_FLOOR - i + 1) * FLOOR_HEIGHT;
        	JButton button = makeButton(buttonListener, null, icon, xPos, yPos, RIGHT);
			button.setActionCommand("d" + i);
			button.setOpaque(true); // By pass look and feel effect.
	        downButtons[i] = button;
        }
    
        floorButtons = new JButton[Controller.LAST_FLOOR + 1];
        for (int i = 0; i <= Controller.LAST_FLOOR; ++i) {
	        int xPos = FLOOR_WIDTH + ELEVATOR_WIDTH + FLOOR_WIDTH / 2;
	        int yPos = FLOOR_BASE + (Controller.LAST_FLOOR - i + 1) * FLOOR_HEIGHT;
        	JButton button = makeButton(buttonListener, Integer.toString(i), null, xPos, yPos, CENTER);
			button.setActionCommand("f" + i);
			button.setOpaque(true); // By pass look and feel effect.
	        floorButtons[i] = button;
        }
        
        engineAnimation.start();
    }

	/**
	 * @param listener
	 * @param text
	 * @param icon
	 * @param xPos
	 * @param yPos
	 * @param pos
	 * @return
	 */
	private JButton makeButton(ChangeListener listener, String text, ImageIcon icon, int xPos, int yPos, int pos) {
		JButton button = new JButton(text, icon);
		button.setMargin(text == null ? buttonMargin : floorButtonMargin);
		button.addChangeListener(listener);
		this.add(button);
		
        Dimension size = button.getPreferredSize();
        switch (pos) {
        	case LEFT:
        		xPos -= 5 + size.width;
        		break;
        	case RIGHT:
        		xPos += 5;
        		break;
        	case CENTER:
        		xPos -= size.width / 2;
        		break;
        }
        yPos -= 10 + size.height;
        button.setBounds(xPos, yPos, size.width, size.height);

        return button;
	}

	protected void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	
    	// Dimension size = getSize();
    	// System.out.println("Width: " + size.getWidth());
    	// System.out.println("Height: " + size.getHeight());

        // Draw the floors...
        g.setColor(Color.blue);
        for (int i = 0; i <= Controller.LAST_FLOOR; ++i) {
        	int yPos = FLOOR_BASE + (i + 1) * FLOOR_HEIGHT;
        	g.drawLine(0,   yPos, FLOOR_WIDTH - 2, yPos);
        }

        // ...a border around the elevator buttons...
    	int xMid = FLOOR_WIDTH + ELEVATOR_WIDTH + FLOOR_WIDTH / 2;
    	int width = 50;
    	int yBase = FLOOR_BASE + FLOOR_HEIGHT - width;
    	int height = (Controller.LAST_FLOOR) * FLOOR_HEIGHT + width;
    	g.drawRect(xMid - width / 2, yBase, width, height);

        
        // ...the cable engine...
        g.setColor(cableEngine.isSkidding() ? Color.red : Color.green);
        g.fillOval(cableX, 0, ENGINE_DIAMETER, ENGINE_DIAMETER);
        g.setColor(Color.black);
        g.drawOval(cableX, 0, ENGINE_DIAMETER, ENGINE_DIAMETER);

        // ...the cable engine status...
        CABLE_COMMAND cableEngineStatus = cableEngine.getCableStatus();
        if (cableEngineStatus == CABLE_WIND) {
        	g.drawLine(cableArrowX, cableArrowTop, cableArrowX,     cableArrowBot);
        	g.drawLine(cableArrowX, cableArrowTop, cableArrowX - 4, cableArrowTop + 4);
        	g.drawLine(cableArrowX, cableArrowTop, cableArrowX + 4, cableArrowTop + 4);
        }
        else if (cableEngineStatus == CABLE_UNWIND) {
        	g.drawLine(cableArrowX, cableArrowTop, cableArrowX,     cableArrowBot);
        	g.drawLine(cableArrowX, cableArrowBot, cableArrowX - 4, cableArrowBot - 4);
        	g.drawLine(cableArrowX, cableArrowBot, cableArrowX + 4, cableArrowBot - 4);
        }
        
        // ...and finally the elevator
        drawElevator(g, elevator);
	}

    protected void drawElevator(Graphics g, Elevator elevator) {
    	int xPos = FLOOR_WIDTH;
    	int yPos = FLOOR_BASE - ELEVATOR_HEIGHT 
				 + FLOOR_HEIGHT * (Controller.LAST_FLOOR - elevator.getFloor() + 1)
				 - FLOOR_HEIGHT * elevator.getPercentAbove() / 100;
    	int xRight = xPos + ELEVATOR_WIDTH;
    	int yBot = yPos + ELEVATOR_HEIGHT;
    	
    	// Elevator
    	g.setColor(Color.black);
    	drawFilledRect(g, xPos, yPos, ELEVATOR_WIDTH, ELEVATOR_HEIGHT);
    	g.drawRect(xPos + ELEVATOR_DEPTH, yPos, ELEVATOR_WIDTH - 2 * ELEVATOR_DEPTH, ELEVATOR_HEIGHT - ELEVATOR_DEPTH);
    	g.drawLine(xPos, yBot, xPos + ELEVATOR_DEPTH, yBot - ELEVATOR_DEPTH);
    	g.drawLine(xRight - ELEVATOR_DEPTH, yBot - ELEVATOR_DEPTH, xRight, yBot);
    	
    	// Doors
    	int width = ELEVATOR_WIDTH * (100 - elevator.getPercentOpen()) / 300;
    	int height = ELEVATOR_HEIGHT;
    	drawFilledRect(g, xPos, yPos, width, height);
    	drawFilledRect(g, xPos +   width, yPos, width, height);
    	drawFilledRect(g, xPos + 2*width, yPos, width, height);

    	// Door engine status
    	int arrowLeft = xPos + ELEVATOR_WIDTH / 3;
    	int arrowRight = xPos + 2 * ELEVATOR_WIDTH / 3;
    	int arrowY = yPos + ELEVATOR_HEIGHT + 8;
        DOOR_COMMAND doorEngineStatus = doorEngine.getDoorStatus();
        if (doorEngineStatus == DOOR_OPEN) {
        	g.drawLine(arrowLeft, arrowY, arrowRight, arrowY);
        	g.drawLine(arrowLeft, arrowY, arrowLeft + 4, arrowY - 4);
        	g.drawLine(arrowLeft, arrowY, arrowLeft + 4, arrowY + 4);
        }
        else if (doorEngineStatus == DOOR_CLOSE) {
        	g.drawLine(arrowLeft, arrowY, arrowRight, arrowY);
        	g.drawLine(arrowRight, arrowY, arrowRight - 4, arrowY - 4);
        	g.drawLine(arrowRight, arrowY, arrowRight - 4, arrowY + 4);
        }
    	
    	// Cable
    	g.drawLine(cableX, cableUpperY, cableX, yPos);
    }

	private void drawFilledRect(Graphics g, int xPos, int yPos, int width, int height) {
    	g.setColor(this.getBackground());
		g.fillRect(xPos          , yPos, width, height);
    	g.setColor(Color.black);
    	g.drawRect(xPos          , yPos, width, height);
	}

	/** Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createImageIcon(String path,
                                               String description) {
        java.net.URL imgURL = Building.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

	public boolean getUpButtonStatus(int i) {
		return upButtons[i].getModel().isArmed();
	}
	public boolean getDownButtonStatus(int i) {
		return downButtons[i].getModel().isArmed();
	}
	public boolean getFloorButtonStatus(int i) {
		return floorButtons[i].getModel().isArmed();
	}
	public CABLE_COMMAND getCableEngineStatus() {
		return cableEngine.getCableStatus();
	}
	public DOOR_COMMAND getDoorEngineStatus() {
		return doorEngine.getDoorStatus();
	}
	public int getCurrentFloor() {
		if (elevator.getPercentAbove() == 0)
			return elevator.getFloor();
		else
			return -1;
	}
	public DOOR_POSITION getDoorPosition() {
		if (elevator.getPercentOpen() == 0)
			return DOOR_CLOSED;
		else if (elevator.getPercentOpen() == 100)
			return DOOR_OPENED;
		else
			return DOOR_HALF;
	}

	public void turnOnUpButton(int i, boolean on) {
		upButtons[i].setBackground(on ? Color.red: null);
	}
	public void turnOnDownButton(int i, boolean on) {
		downButtons[i].setBackground(on ? Color.red : null);
	}
	public void turnOnFloorButton(int i, boolean on) {
		floorButtons[i].setBackground(on ? Color.red : null);
	}
	public void setCableEngineStatus(CABLE_COMMAND cableEngineCommand) {
		cableEngine.setStatus(cableEngineCommand);
	}
	public void setDoorEngineStatus(DOOR_COMMAND doorEngineCommand) {
		doorEngine.setStatus(doorEngineCommand);
	}
}
