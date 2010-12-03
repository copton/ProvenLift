package simulator;


public class CableEngine extends Engine {

	public enum CABLE_COMMAND {
		CABLE_STOP, CABLE_WIND, CABLE_UNWIND
	}
	
	public void setStatus(CABLE_COMMAND status) {
		switch (status) {
		case CABLE_STOP:
			super.setStatus(COMMAND.STOP);
			break;
		case CABLE_WIND:
			super.setStatus(COMMAND.WIND);
			break;
		case CABLE_UNWIND:
			super.setStatus(COMMAND.UNWIND);
		default:
			break;
		}
	}
	
	public CABLE_COMMAND getCableStatus() {
		switch (getStatus()) {
		case STOP:
			return CABLE_COMMAND.CABLE_STOP;
		case WIND:
			return CABLE_COMMAND.CABLE_WIND;
		case UNWIND:
			return CABLE_COMMAND.CABLE_UNWIND;
		default:
			return CABLE_COMMAND.CABLE_STOP;
		}
	}
	
}
