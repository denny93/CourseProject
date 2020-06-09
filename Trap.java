import java.util.Random;

public class Trap extends Pole {
	
	private boolean isActive;
	private TrapType type;
	
	public void specialEffect(Player player) {
		if(this.type.equals(TrapType.DANUCHNA_REVIZIQ)) {
			player.setRoundEarnings( (int)(player.getRoundEarnings() * 0.9) );
		}
		
		if(this.type.equals(TrapType.RAZVOD_PO_KOTESHKI)) {
			int dice = new Random().nextInt(10) + 1;
			if (dice == 8 || dice == 2 ) {
				player.setRoundEarnings(0);
			}
		}
	}

	
	public Trap() {
		this.isActive = false;
	}


	@Override
	public String displayField() {
		return "|T|";
	}


	boolean isActive() {
		return isActive;
	}


	void setActive(boolean isActive) {
		this.isActive = isActive;
	}


	TrapType getType() {
		return type;
	}


	void setType(TrapType type) {
		this.type = type;
	}

}
