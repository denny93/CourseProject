import java.util.Random;

public class Company {

	private String name;
	private int minInvest;
	private int invest;
	private double koeficient;
	private int riskMinus;
	private int riskPlus;
	
	public Company(String name, int minInvest, double koeficient, int riskMinus, int riskPlus) {
		this.name = name;
		this.minInvest = minInvest;
		this.invest = 0;
		this.koeficient = koeficient;
		this.riskMinus = riskMinus;
		this.riskPlus = riskPlus;
	}
	
	void payInvestments(Player player) {
		
		int chance = new Random().nextInt(this.riskPlus - this.riskMinus + 1) - this.riskMinus;
		if(chance <= 0) {
			System.out.println(player + " loses his investitions in " + this);
		}
		else {
			System.out.println(player + " wins his investitions in " + this);
			player.setChocolateMoney((int) (player.getChocolateMoney() + this.invest * this.koeficient));
		}
	}

	String getName() {
		return name;
	}

	void setName(String name) {
		this.name = name;
	}

	int getMinInvest() {
		return minInvest;
	}

	void setMinInvest(int minInvest) {
		this.minInvest = minInvest;
	}

	double getKoeficient() {
		return koeficient;
	}

	void setKoeficient(double koeficient) {
		this.koeficient = koeficient;
	}

	int getRiskMinus() {
		return riskMinus;
	}

	void setRiskMinus(int riskMinus) {
		this.riskMinus = riskMinus;
	}

	int getRiskPlus() {
		return riskPlus;
	}

	void setRiskPlus(int riskPlus) {
		this.riskPlus = riskPlus;
	}

	int getInvest() {
		return invest;
	}

	void setInvest(int invest) {
		this.invest = invest;
	}

	@Override
	public String toString() {
		return "Company [name=" + name + ", minInvest=" + minInvest + ", invest=" + invest + ", koeficient="
				+ koeficient + ", riskMinus=" + riskMinus + ", riskPlus=" + riskPlus + "]";
	}
	
	
}
