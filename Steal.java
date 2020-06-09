
public class Steal extends Pole {

	private boolean isActive;
	private StealType type;
	
	@Override
	public String displayField() {
		return "|St|";
	}

	boolean isActive() {
		return isActive;
	}

	void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	StealType getType() {
		return type;
	}

	void setType(StealType type) {
		this.type = type;
	}
}
