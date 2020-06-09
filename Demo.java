
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Demo {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		List<Company> companies = new ArrayList<>();
		companies.add(new Company("Evel Co", 500, 0.2, -5, 100));
		companies.add(new Company("Bombs Away", 400, 0.5, -10, 50));
		companies.add(new Company("Clock Work Orange", 300, 1.5, -15, 35));
		companies.add(new Company("Maroders unated", 200, 2, -18, 50));
		companies.add(new Company("Fatcat incorporated", 100, 2.5, -25, 100));
		companies.add(new Company("Macrosoft", 50, 5, -20, 10));
		
		List<Player> players = new ArrayList<>();
		for (int i = 1; i <= 2; i++) {
			players.add(new Player());
		}
		
		IgralnoPole igralnoPole = new IgralnoPole(sc, companies, players);

		igralnoPole.play();
		
	}

}
