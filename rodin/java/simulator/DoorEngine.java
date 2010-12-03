package simulator;

public class DoorEngine extends Engine {

	public enum DOOR_COMMAND {
		DOOR_STOP, DOOR_OPEN, DOOR_CLOSE
	}

	public void setStatus(DOOR_COMMAND status) {
		switch (status) {
		case DOOR_STOP:
			super.setStatus(COMMAND.STOP);
			break;
		case DOOR_CLOSE:
			super.setStatus(COMMAND.UNWIND);
			break;
		case DOOR_OPEN:
			super.setStatus(COMMAND.WIND);
		default:
			break;
		}
	}

	public DOOR_COMMAND getDoorStatus() {
		switch (getStatus()) {
		case STOP:
			return DOOR_COMMAND.DOOR_STOP;
		case WIND:
			return DOOR_COMMAND.DOOR_OPEN;
		case UNWIND:
			return DOOR_COMMAND.DOOR_CLOSE;
		default:
			return DOOR_COMMAND.DOOR_STOP;
		}
	}

}
