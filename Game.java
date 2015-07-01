import java.util.*;
import java.io.*;
import java.util.Scanner;

class Game
{
	ArrayList<Player> players;
	Scanner s = new Scanner(System.in);
	Dealer dealer;
	String m; 
	String deckCount; 
	String bet;
	String opt; 
	String opt2;
	boolean isError = false, isFinish = false, isOver = false, isFirst = true;
	int exit = 0;
	Deck deck;
	
	public Game(int playerCount) {
		players = new ArrayList<Player>();
		try{
			System.out.println("請輸入所有玩家的籌碼 : ");
			m = s.next();
			if(Integer.parseInt(m) < 1){
				System.out.println("籌碼須大於0!");
				isError = true;
			}
			else{
				for (int i = 0; i < playerCount; i++) {
					System.out.println("請輸入玩家" + (i + 1) + "名稱 : ");
					players.add(new Player(s.next(), Integer.parseInt(m)));
				}
			}
			if(!isError){
				System.out.println("幾副牌 : ");
				deckCount = s.next();
				if(Integer.parseInt(deckCount) >= 1){
					deck = new Deck(Integer.parseInt(deckCount));
				}
				else{
					System.out.println("牌須大於等於1副!");
					isError = true;
				}
			}
		}
		catch(Exception e){
			System.out.println("error : " + e);
			isError = true;
		}
		dealer = new Dealer("莊家");
	}
	public void firstround(){
		isFinish = false;
		try{
			while(!isFirst){
				exit = 0;
				System.out.println("請輸入 1)繼續遊戲 -1)離開 : ");
				option2 = s.next();
				switch(option2){
					case "1":
						break;
					case "-1":
						exit = 1;
						return;
					default:
						exit = -1;
						System.out.println("無此選項!");
						break;
				}
				if(exit == 0){
					break;
				}
			}
			for(int i = 0; i < players.size(); i++){
				System.out.println("玩家" + players.get(i).name + "請下注 : ");
				bet = s.next();
				if(Integer.parseInt(bet) < 1){
					System.out.println("下注金額須大於0!");
					i--;
				}
				else if(Integer.parseInt(bet) > players.get(i).money){
					System.out.println("籌碼不夠!");
					i--;
				}
				else{
					players.get(i).bet = Integer.parseInt(bet);
				}
			}
		}
		catch(Exception e){
			System.out.println("error : " + e);
			isError = true;
		}
		if(!isError){
			if(isFirst){
				deck.shuffle();	
				System.out.println("洗牌!");
				isFirst = false;
			}
			for(int i = 0; i < 2; i++){
				for(int j = 0; j < players.size(); j++){
					players.get(j).handle(deck.deal());
					if(deck.cards.size() == 0){
						deck.reset();
						for(int k = 0; k < players.size(); k++){
							for(int t = 0; t < players.get(k).cards.size(); t++){
								for(int x = 0; x < deck.cards.size(); x++){
									if(deck.cards.get(x).suit == players.get(k).cards.get(t).suit && deck.cards.get(x).face == players.get(k).cards.get(t).face){
										deck.cards.remove(x);
									}
								}
							}
						}
						for(int k = 0; k < dealer.cards.size(); k++){
							deck.cards.remove(dealer.cards.get(k));
						}
					}
				}
				dealer.handle(deck.deal());
				if(deck.cards.size() == 0){
					deck.reset();
					for(int k = 0; k < players.size(); k++){
						for(int t = 0; t < players.get(k).cards.size(); t++){
							for(int x = 0; x < deck.cards.size(); x++){
								if(deck.cards.get(x).suit == players.get(k).cards.get(t).suit && deck.cards.get(x).face == players.get(k).cards.get(t).face){
									deck.cards.remove(x);
								}
							}
						}
					}
					for(int k = 0; k < dealer.cards.size(); k++){
						deck.cards.remove(dealer.cards.get(k));
					}
				}
			}
			System.out.println("發牌!\n");
			System.out.print("\t莊家 :\t");
			for(int j = 0; j < dealer.cards.size(); j++){
				if(j == 0){
					System.out.print("***\t");
				}
				else{
					System.out.println(dealer.cards.get(j).suit + dealer.cards.get(j).face);
				}
			}
			System.out.println();
			for(int i = 0; i < players.size(); i++){
				System.out.print(players.get(i).name + " :\t");
				for(int j = 0; j < players.get(i).cards.size(); j++){
					System.out.print(players.get(i).cards.get(j).suit + players.get(i).cards.get(j).face + "\t");
				}
				System.out.println("籌碼 : " + players.get(i).money);
			}
		}
		dealer.points();
		if(dealer.count == 21){
			System.out.println("莊家亮牌!");
			for(int j = 0; j < dealer.cards.size(); j++){
				System.out.print(dealer.cards.get(j).suit + dealer.cards.get(j).face + "\t");
			}
			System.out.println("");
			System.out.println("莊家blackjack!");
			for(int i = 0; i < players.size(); i++){
				players.get(i).points();
				System.out.println("玩家" + players.get(i).name + "總點數 : " + players.get(i).count);
				if(players.get(i).count == 21){
					System.out.println("玩家" + players.get(i).name + " blackjack!");
					System.out.println("玩家" + players.get(i).name + "籌碼不變 " + "剩餘" + players.get(i).money + "點");
				}
				else{
					players.get(i).money -= players.get(i).bet;
					System.out.println("玩家" + players.get(i).name + "籌碼-" + players.get(i).bet + "剩餘" + players.get(i).money + "點");
					if(players.get(i).money == 0){
						System.out.println("玩家" + players.get(i).name + "輸了!");
						players.remove(i);
					}
				}
			}
			isFinish = true;
			if(players.size() == 0){
				isOver = true;
			}
			dealer.cards.clear();
			for(int i = 0; i < players.size(); i++){
				players.get(i).cards.clear();
			}
		}
	}
	public void battle(){
		if(!isOver && !isFinish){
			boolean exit = false;
			System.out.println("玩家回合");
			System.out.println("===============");
			try{
				for(int a = 0; a < players.size(); a++){
					System.out.println("玩家" + players.get(a).name + "請選擇 1)hit 2)stay : ");
					option = s.next();
					switch(opt){
						case "1":
							Card temp = deck.deal();
							System.out.println(temp.suit + temp.face);
							players.get(a).handle(temp);
							if(deck.cards.size() == 0){
								deck.reset();
								for(int i = 0; i < players.size(); i++){
									for(int j = 0; j < players.get(j).cards.size(); j++){
										for(int x = 0; x < deck.cards.size(); x++){
											if(deck.cards.get(x).suit == players.get(i).cards.get(j).suit && deck.cards.get(x).face == players.get(i).cards.get(j).face){
												deck.cards.remove(x);
											}
										}
									}
								}
								for(int i = 0; i < dealer.cards.size(); i++){
									deck.cards.remove(dealer.cards.get(i));
								}
							}
							break;
						case "2":
							exit = true;
							break;
						default:
							System.out.println("無此選項!");
							break;
					}
					players.get(a).points();
					if(!exit && players.get(a).count <= 21){
						a--;
					}
					else if(players.get(a).count > 21){
						System.out.println("玩家" + players.get(a).name + "爆牌!");
					}
					else if(exit){
						exit = false;
					}
				}
			}
			catch(Exception e){
				System.out.println("error : " + e);
				isError = true;
			}
			System.out.println("莊家回合");
			System.out.println("===============");
			dealer.points();
			System.out.println("莊家亮牌!");
			for(int j = 0; j < dealer.cards.size(); j++){
				System.out.print(dealer.cards.get(j).suit + dealer.cards.get(j).face + "\t");
			}
			System.out.println("");
			while(dealer.count < 17){
				System.out.println("莊家點數小於17，繼續要牌!");
				Card temp = deck.deal();
				System.out.println(temp.suit + temp.face);
				dealer.handle(temp);
				if(deck.cards.size() == 0){
					deck.reset();
					for(int k = 0; k < players.size(); k++){
						for(int t = 0; t < players.get(k).cards.size(); t++){
							for(int x = 0; x < deck.cards.size(); x++){
								if(deck.cards.get(x).suit == players.get(k).cards.get(t).suit && deck.cards.get(x).face == players.get(k).cards.get(t).face){
									deck.cards.remove(x);
								}
							}
						}
					}
					for(int k = 0; k < dealer.cards.size(); k++){
						deck.cards.remove(dealer.cards.get(k));
					}
				}
				dealer.points();
				if(dealer.count > 21){
					System.out.println("莊家爆牌!");
				}
			}
			System.out.println("莊家閒家對決");
			System.out.println("===============");
			System.out.println("莊家總點數 : " + dealer.count);
			if(dealer.count > 21){
				System.out.println("莊家爆牌!");
				for(int i = 0; i < players.size(); i++){
					System.out.println("玩家" + players.get(i).name + "總點數 : " + players.get(i).count);
					if(players.get(i).count <= 21){
						if(players.get(i).count == 21){
							System.out.println("玩家" + players.get(i).name + " blackjack!");
						}
						players.get(i).money += players.get(i).bet;
						System.out.println("玩家" + players.get(i).name + "籌碼+" + players.get(i).bet + "剩餘" + players.get(i).money + "點");
					}
					else{
						System.out.println("玩家" + players.get(i).name + "爆牌!");
						players.get(i).money -= players.get(i).bet;
						System.out.println("玩家" + players.get(i).name + "籌碼-" + players.get(i).bet + "剩餘" + players.get(i).money + "點");
						if(players.get(i).money == 0){
							System.out.println("玩家" + players.get(i).name + "輸了!");
							players.remove(i);
							i--;
						}
					}
				}
			}
			else{
				if(dealer.count == 21){
					System.out.println("莊家blackjack!");
				}
				for(int i = 0; i < players.size(); i++){
					System.out.println("玩家" + players.get(i).name + "總點數 : " + players.get(i).count);
					if(players.get(i).count <= 21 && players.get(i).count > dealer.count){
						if(players.get(i).count == 21){
							System.out.println("玩家" + players.get(i).name + " blackjack!");
						}
						players.get(i).money += players.get(i).bet;
						System.out.println("玩家" + players.get(i).name + "籌碼+" + players.get(i).bet + "剩餘" + players.get(i).money + "點");
					}
					else if(players.get(i).count > 21 || players.get(i).count < dealer.count){
						if(players.get(i).count > 21){
							System.out.println("玩家" + players.get(i).name + "爆牌!");
						}
						players.get(i).money -= players.get(i).bet;
						System.out.println("玩家" + players.get(i).name + "籌碼-" + players.get(i).bet + "剩餘" + players.get(i).money + "點");
						if(players.get(i).money == 0){
							System.out.println("玩家" + players.get(i).name + "輸了!");
							players.remove(i);
							i--;
						}
					}
					else{
						if(players.get(i).count == 21){
							System.out.println("玩家" + players.get(i).name + " blackjack!");
						}
						System.out.println("玩家" + players.get(i).name + "籌碼不變 " + "剩餘" + players.get(i).money + "點");
					}
				}
			}
			dealer.cards.clear();
			for(int i = 0; i < players.size(); i++){
				players.get(i).cards.clear();
			}
			if(players.size() == 0){
				isOver = true;
			}
		}
	}
}