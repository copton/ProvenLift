package simulator;

import static simulator.Engine.COMMAND.*;

/**
 * @author lvoisin
 *
 * Model of the elevator engine.
 */
public class Engine {
	
	public enum COMMAND {
		STOP, WIND, UNWIND
	}
	
	private COMMAND status = STOP;
	private boolean skidding = false;
	private BuildingPanel viewer;
	
	/**
	 * @return Returns the status.
	 */
	public COMMAND getStatus() {
		return status;
	}
	
	/**
	 * @param status The status to set.
	 */
	public void setStatus(COMMAND status) {
		this.status = status;
		if (status == STOP) {
			setSkidding(false);
		}
		viewer.repaint();
	}

	/**
	 * @param panel
	 */
	public void setViewer(BuildingPanel viewer) {
		this.viewer = viewer;
	}
	
	/**
	 * @return true if the engine is skidding.
	 */
	public boolean isSkidding() {
		return skidding;
	}
	/**
	 * @param skidding The skidding to set.
	 */
	public void setSkidding(boolean skidding) {
		this.skidding = skidding;
	}
}
