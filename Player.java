import java.util.ArrayList;
import java.util.List;

public class Player {

	private static int ID = 0;
	private int id;
	private boolean isActive;
	private int currentPosition;
	private int chocolateMoney;
	private int roundEarnings;
	private Steal roundSteal;
	private boolean isStealActive;
	private List<Trap> activatedTraps;
	private List<Company> companiesInvested;
	
	public Player() {
		this.id = Player.ID++;
		this.isActive = true;
		this.currentPosition = 0;
		this.chocolateMoney = 1000;
		this.roundEarnings = 0;
		this.isStealActive = false;
		this.activatedTraps = new ArrayList<Trap>();
		this.companiesInvested = new ArrayList<Company>();
	}

	int getCurrentPosition() {
		return currentPosition;
	}

	void setCurrentPosition(int currentPosition) {
		this.currentPosition = currentPosition;
	}

	int getChocolateMoney() {
		return chocolateMoney;
	}

	void setChocolateMoney(int chocolateMoney) {
		if(chocolateMoney < 0) {
			this.chocolateMoney = 0;
			return;
		}
		this.chocolateMoney = chocolateMoney;
	}
	
	public void addTrap(Trap trap) {

		this.activatedTraps.add(trap);
	}

	List<Trap> getActivatedTraps() {
		return new ArrayList<Trap>(this.activatedTraps);
	}
	
	public void clearTraps() {
		this.activatedTraps.clear();
	}
	
	public void addCompany(Company companies) {

		this.companiesInvested.add(companies);
	}
	
	List<Company> getCompaniesInvested() {
		return new ArrayList<Company>(this.companiesInvested);
	}
	
	public void clearCompaniesInvested() {
		this.companiesInvested.clear();
	}

	boolean isActive() {
		return isActive;
	}

	void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public String toString() {
		return "Player [id=" + id + ", currentPosition=" + currentPosition + ", chocolateMoney=" + chocolateMoney + "]";
	}

	int getRoundEarnings() {
		return roundEarnings;
	}

	void setRoundEarnings(int roundEarnings) {
		this.roundEarnings = roundEarnings;
	}

	public void removeHazartenBosTrap() {
		Trap trap = this.activatedTraps.stream().findAny().get();
		this.activatedTraps.remove(trap);
	}

	Steal getRoundSteal() {
		return roundSteal;
	}

	void setRoundSteal(Steal roundSteal) {
		this.roundSteal = roundSteal;
	}

	boolean isStealActive() {
		return isStealActive;
	}

	void setStealActive(boolean isStealActive) {
		this.isStealActive = isStealActive;
	}

	int getId() {
		return id;
	}

	
}
