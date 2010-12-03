package simulator;


/**
 * @author lvoisin
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Elevator {
	
	// Current floor
	private int floor = 0;
	
	// Percentage above current floor
	private int percentAbove = 0;
	
	// Percentage doors open
	private int percentOpen = 0;
	
	private BuildingPanel viewer;

	private Engine cableEngine;
	private Engine doorEngine;
	
	/**
	 * @param cableEngine
	 */
	public Elevator(Engine cableEngine, Engine doorEngine) {
		this.cableEngine = cableEngine;
		this.doorEngine = doorEngine;
	}
	/**
	 * @return Returns the floor.
	 */
	public int getFloor() {
		return floor;
	}
	/**
	 * @return Returns the percentAbove.
	 */
	public int getPercentAbove() {
		return percentAbove;
	}
	/**
	 * @return Returns the percentOpen.
	 */
	public int getPercentOpen() {
		return percentOpen;
	}

	public void setViewer(BuildingPanel viewer) {
		this.viewer = viewer;
	}
	
	public void move(int increment) {
		percentAbove += increment;
		while (percentAbove >= 100) {
			percentAbove -= 100;
			++ floor;
		}
		while (percentAbove < 0) {
			percentAbove += 100;
			-- floor;
		}
		enforcePhysicalConstraints();
		viewer.repaint();
	}

	private void enforcePhysicalConstraints() {
		boolean skidding = false;
		
		if (floor > Controller.LAST_FLOOR) {
			floor = Controller.LAST_FLOOR;
			skidding = true;
		}
		if (floor == Controller.LAST_FLOOR && percentAbove != 0) {
			percentAbove = 0;
			skidding = true;
		}
		if (floor < 0) {
			floor = 0;
			percentAbove = 0;
			skidding = true;
		}
		cableEngine.setSkidding(skidding);
	}

	public void open(int increment) {
		percentOpen += increment;
		if (percentOpen >= 100) {
			percentOpen = 100;
		}
		else if (percentOpen < 0) {
			percentOpen = 0;
		}
		viewer.repaint();
	}
	
}
