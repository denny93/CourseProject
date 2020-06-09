import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class IgralnoPole {

	private List<Pole> poleta;
	private List<Player> players;
	private List<Company> companies;
	private Scanner sc;
	
	public IgralnoPole(Scanner sc, List<Company> companies, List<Player> players) {
		this.poleta = new ArrayList<Pole>();
		this.poleta.add(new Start());
		this.companies = companies;
		this.sc = sc;
		
		for (int trapCount = 1; trapCount <= 7; trapCount++) {
			this.poleta.add(new Trap());
		}
		for (int chanceCount = 1; chanceCount <= 3; chanceCount++) {
			this.poleta.add(new Chance());
		}
		for (int investCount = 1; investCount <= 3; investCount++) {
			this.poleta.add(new Invest());
		}
		for (int partyHardCount = 1; partyHardCount <= 3; partyHardCount++) {
			this.poleta.add(new PartyHard());
		}
		for (int stealCount = 1; stealCount <= 3; stealCount++) {
			this.poleta.add(new Steal());
		}
		
		this.razburkaiPoletata();
		
		this.players = players;
	}
	
	public void play() {
		
		while(true) {
			
			this.players.forEach(player -> {
				player.setCurrentPosition(0);
				player.setRoundEarnings(0);
				player.clearTraps();
				player.clearCompaniesInvested();
				Steal steal = new Steal();
				int random = new Random().nextInt(3);
				if(random == 0) {
					steal.setType(StealType.GOLEMIQ_BANKOV_OBIR);
				}
				if(random == 1) {
					steal.setType(StealType.ZALOJNICI);
				}
				if(random == 2) {
					steal.setType(StealType.ZAVLADQVANE_NA_SVETA);
				}
				player.setRoundSteal(steal);
				System.out.println(player);
			});
			this.playRound();
			long activePlayers = this.players.stream().filter(player -> player.getChocolateMoney() > 0).count();
			if(activePlayers == 1) {
				System.out.println(this.players.stream().findAny() + " wins the game!");
				break;
			}
			this.players.forEach(player -> {
				player.getActivatedTraps().forEach(trap -> trap.specialEffect(player));
				player.getCompaniesInvested().forEach(company -> company.payInvestments(player));
				player.setChocolateMoney(player.getRoundEarnings());
				if(player.getChocolateMoney() <= 0) {
					player.setActive(false);
				}
			});
			activePlayers = this.players.stream().filter(player -> player.getChocolateMoney() > 0).count();
			if(activePlayers == 1) {
				System.out.println(this.players.stream().findAny() + " wins the game!");
				break;
			}
			this.razburkaiPoletata();
			this.poleta.forEach(field -> {
				if(field instanceof Trap) {
					((Trap) field).setActive(false);
					((Trap) field).setType(null);
				}
				if(field instanceof Steal) {
					((Steal) field).setActive(false);
				}
			});
		}
		
	}
	
	private void playRound() {
		
		while(true) {
			this.poleta.forEach(field -> System.out.print(field.displayField() + " "));
			System.out.println();
			this.players.forEach(player -> {
				if(player.getCurrentPosition() < 19) {
					System.out.println(player + " turn");
					int dice = this.rollDice(2);
					System.out.println("Player" + player.getId() + " rolled dice " + dice);
					if (player.getCurrentPosition() + dice > 19) {
						player.setCurrentPosition(19);
						System.out.println(" and is on position: " + player.getCurrentPosition());
					}
					else {
						player.setCurrentPosition(player.getCurrentPosition() + dice);
						System.out.println(" and is on position: " + player.getCurrentPosition());
						Pole currentField = this.poleta.get(player.getCurrentPosition());
						if (currentField instanceof Trap) {
							
							if ( ((Trap)currentField).isActive() ) {
								System.out.println("You activated a trap!");
								player.addTrap(((Trap)currentField));
								((Trap)currentField).setActive(false);
							}
							else {
								if ( player.getActivatedTraps().stream().anyMatch(trap -> trap.getType().equals(TrapType.PROPAGANDA)) ) {
									System.out.println("Yout have previously activated Propaganda trap so you can not set traps!");
								}
								else if (player.getChocolateMoney() < 50) {
									System.out.println("You do not have enough money to activate a trap!");
								}
								else {
									System.out.println("Jelaete li da zalojite kapan?");
									System.out.println("(N) : Ne blagodarq, ne vqrvam v zloto");
									System.out.println("(1) : Proglejdane (50 money)");
									if (player.getChocolateMoney() >= 100) {
										System.out.println("(2) : Danuchna reviziq (100 money)");
										System.out.println("(3) : Propaganda (100 money)");
										System.out.println("(4) : Hazarten bos (100 money)");
									}
									if (player.getChocolateMoney() >= 200) {
										System.out.println("(5) : Razvod po koteshki (200 money)");
									}
									String input = sc.next();
									switch(input) {
									
										case "N": System.out.println("Vie ne zalojihte kapan i priklyuchihte hoda si!");
										break;
										case "1": System.out.println("Vie zalojihte kapan Proglejdane!");
												  player.setChocolateMoney(player.getChocolateMoney() - 50);
												  ((Trap)currentField).setType(TrapType.PROGLEJDANE);
												  ((Trap)currentField).setActive(true);
										break;
										case "2": if(player.getChocolateMoney() >= 100) {
														System.out.println("Vie zalojihte kapan Danuchna reviziq!");
														player.setChocolateMoney(player.getChocolateMoney() - 100);
														((Trap)currentField).setType(TrapType.DANUCHNA_REVIZIQ);
														((Trap)currentField).setActive(true);
													}
										break;
										case "3": if(player.getChocolateMoney() >= 100) {
														System.out.println("Vie zalojihte kapan Propaganda!");
														player.setChocolateMoney(player.getChocolateMoney() - 100);
														((Trap)currentField).setType(TrapType.PROPAGANDA);
														((Trap)currentField).setActive(true);
													}
										break;
										case "4": if(player.getChocolateMoney() >= 100) {
														System.out.println("Vie zalojihte kapan Hazarten bos!");
														player.setChocolateMoney(player.getChocolateMoney() - 100);
														((Trap)currentField).setType(TrapType.HAZARTEN_BOS);
														((Trap)currentField).setActive(true);
													}
										break;
										case "5": if(player.getChocolateMoney() >= 200) {
														System.out.println("Vie zalojihte kapan Razvod po koteshki!");
														player.setChocolateMoney(player.getChocolateMoney() - 200);
														((Trap)currentField).setType(TrapType.RAZVOD_PO_KOTESHKI);
														((Trap)currentField).setActive(true);
													}
										break;
									}
								}
							}
						}
						if (currentField instanceof Chance) {
							
							if(player.isStealActive() && player.getRoundSteal().getType().equals(StealType.ZAVLADQVANE_NA_SVETA)) {
								player.setRoundEarnings(player.getRoundEarnings() + 100);
								System.out.println("player" + player.getId() + " poluchi Steal bonus");
							}
							int dice2 = this.rollDice(10);
							int dice3 = this.rollDice(100);
							if (dice2 % 2 == 0) {
								if ( player.getActivatedTraps().stream().anyMatch(trap -> trap.getType().equals(TrapType.HAZARTEN_BOS)) ) {
									System.out.println("Yout have previously activated Hazarten Bos trap so you can not have positive chance!");
									player.removeHazartenBosTrap();
								}
								else {
									System.out.println("Dnes e radosten den za vas!");
									if (dice3 <= 39) {
										System.out.println("Осиновявате група\r\n" + 
												"сирачета, за да си вдигнете\r\n" + 
												"социалното реноме.\r\n" + 
												"Социалните мрежи са във\r\n" + 
												"възторг, получавате\r\n" + 
												"окуражителни дарения от\r\n" + 
												"обществото.");
										player.setRoundEarnings(player.getRoundEarnings() + 50);
									}
									if (dice3 <= 65 && dice3 >= 40) {
										System.out.println("Хващате си младо гадже,\r\n" + 
												"малка котка с големи\r\n" + 
												"възможности. Получавате\r\n" + 
												"вечното уважение на\r\n" + 
												"кварталните пичове, както\r\n" + 
												"и легендарен статус на\r\n" + 
												"вечен играч.");
										player.setRoundEarnings(player.getRoundEarnings() + 100);
									}
									if (dice3 <= 79 && dice3 > 65) {
										System.out.println("Напускате университета и\r\n" + 
												"ставате милионер. Честито!");
										player.setRoundEarnings(player.getRoundEarnings() + 150);
									}
									if (dice3 <= 94 && dice3 >= 80) {
										System.out.println("Тийнейджъри представят\r\n" + 
												"гениална идея за\r\n" + 
												"рационализиране на\r\n" + 
												"производствените\r\n" + 
												"мощности. Получавате\r\n" + 
												"стабилен бонус.");
										player.setRoundEarnings(player.getRoundEarnings() + 200);
									}
									if (dice3 <= 100 && dice3 >= 95) {
										System.out.println("Наемате джудже за личен\r\n" + 
												"асистент, обществото е\r\n" + 
												"уверено че междувидовата\r\n" + 
												"сегрегация е в историята.\r\n" + 
												"Уважението към вас е\r\n" + 
												"безгранично.");
										player.setRoundEarnings(player.getRoundEarnings() + 250);
									}
								}
							}
							else {
								System.out.println("Ti iztegli kusata klechka!");
								if (dice3 <= 39) {
									System.out.println("Вдигате толкова голям купон, че\r\n" + 
											"не знаете къде се намирате на\r\n" + 
											"следващата седмица. С мъка\r\n" + 
											"установявате, че телевизорът Ви е\r\n" + 
											"откраднат.");
									player.setChocolateMoney(player.getChocolateMoney() - 50);
								}
								if (dice3 <= 65 && dice3 >= 40) {
									System.out.println("Вие сте баща на три абитуриентки,\r\n" + 
											"бъдете готови за стабилни\r\n" + 
											"разходи.");
									player.setChocolateMoney(player.getChocolateMoney() - 100);
								}
								if (dice3 <= 79 && dice3 > 65) {
									System.out.println("Най-добрият Ви служител\r\n" + 
											"получава повиквателна за\r\n" + 
											"казармата. Губите обучен\r\n" + 
											"персонал.");
									player.setChocolateMoney(player.getChocolateMoney() - 150);
								}
								if (dice3 <= 94 && dice3 >= 80) {
									System.out.println("На връщане от супермаркета,\r\n" + 
											"чудовище се опитва да ви изяде.\r\n" + 
											"Справяте се с неприятеля,\r\n" + 
											"използвайки карате, но се налага\r\n" + 
											"да пишете обяснения, които водят\r\n" + 
											"до пропускане на важна среща и\r\n" + 
											"загуба на бизнес партньор.");
									player.setChocolateMoney(player.getChocolateMoney() - 200);
								}
								if (dice3 <= 100 && dice3 >= 95) {
									System.out.println("Част от бизнесите Ви фалират,\r\n" + 
											"заради задаваща се епидемия от\r\n" + 
											"потна треска.");
									player.setChocolateMoney(player.getChocolateMoney() - 250);
								}
							}
							if (player.getChocolateMoney() <= 0) {
								System.out.println(player + " zagubi vsichkite si pari!");
								return;
							}
						}
						if (currentField instanceof Invest) {
							
							if(player.isStealActive() && player.getRoundSteal().getType().equals(StealType.ZALOJNICI)) {
								player.setRoundEarnings(player.getRoundEarnings() + 100);
								System.out.println("player" + player.getId() + " poluchi Steal bonus");
							}
							
							System.out.println("Jelaete li da investirate?");
							System.out.println("(N) : Ne blagodarq, ne mi se investira");
							for (int i = 0; i < this.companies.size(); i++) {
								if(player.getChocolateMoney() > this.companies.get(i).getMinInvest()) {
									System.out.println(i + " : " + this.companies.get(i));
								}
							}

							String input = sc.next();
								
							if (input.equalsIgnoreCase("N")) {
								System.out.println("You missed your chance to invest!");
							}
							else if( (input.equals("0") || input.equals("1") || input.equals("2") || input.equals("3") || input.equals("4") || input.equals("5"))
									&& player.getChocolateMoney() > this.companies.get(Integer.parseInt(input)).getMinInvest()) {
								System.out.println("How much you want to invest?" + " current money: " + player.getChocolateMoney());
								try {
									int invest = sc.nextInt();
									if(player.getChocolateMoney() > invest && invest >= this.companies.get(Integer.parseInt(input)).getMinInvest()) {
										System.out.println("You invested in " + this.companies.get(Integer.parseInt(input)));
										player.addCompany(this.companies.get(Integer.parseInt(input)));
										player.setChocolateMoney(player.getChocolateMoney() - invest);
									}
									else {
										System.out.println("You do not have enough money or your invest is too small!");
									}
								}
								catch(Exception e) {
									System.out.println("Bad input! You missed your chance to invest!");
								}

							}
							else {
								System.out.println("Bad input! You missed your chance to invest!");
							}
						}
						if (currentField instanceof PartyHard) {
							player.setChocolateMoney(player.getChocolateMoney() - 25);
							if (player.getChocolateMoney() <= 0) {
								System.out.println(player + " zagubi vsichkite si pari!");
								return;
							}
						}
						if (currentField instanceof Steal) {
							
							if( !((Steal)currentField).isActive() && !player.isStealActive()) {
								((Steal)currentField).setActive(true);
								player.setStealActive(true);
							}
							if(player.isStealActive() && player.getRoundSteal().getType().equals(StealType.GOLEMIQ_BANKOV_OBIR)) {
								player.setRoundEarnings(player.getRoundEarnings() + 100);
								System.out.println("player" + player.getId() + " poluchi Steal bonus");
							}
						}
					}
				}
			});
			long playersPlayingTheRound = this.players.stream().filter(player -> player.getCurrentPosition() < 19).count();
			if(playersPlayingTheRound == 0) {
				System.out.println("The round is over!");
				break;
			}
		}
	}

	public void razburkaiPoletata() {
		for (int i = 0; i < 1000; i++) {
			int swap1 = new Random().nextInt(this.poleta.size() - 1) + 1;
			int swap2 = new Random().nextInt(this.poleta.size() - 1) + 1;
			Pole swapField = this.poleta.get(swap1);
			this.poleta.set(swap1, this.poleta.get(swap2));
			this.poleta.set(swap2, swapField);		
		}
	}
	
	private int rollDice (int maxDiceValue) {		
		return new Random().nextInt(maxDiceValue) + 1;
	}

 }
