import java.io.*;
import java.util.*;
import java.util.Scanner;

class Blackjack
{
	public static void main(String[] args) {
		String opt;
		String n;
		Scanner s = new Scanner(System.in);
		boolean isPlayed = false;
		boolean exit = false;
		Game game;

		while(true){
			System.out.println("�п�J 1)�}�l�C�� -1)���} : ");
			opt = s.next();
			if(opt==1){
				isPlayed = true;
			}
			if(opt==-1){
				isPlayed = true;
			}
			if(opt!=1&&opt!=-1){
				System.out.println("�L���ﶵ!");
			}
			if(exit){
				System.out.println("�����C��!");
				break;
			}
			while(isPlayed){
				try{
					System.out.println("�п�J���a�H�� : ");
					n = s.next();
					if(Integer.parseInt(n) >= 1){
						game = new Game(Integer.parseInt(n));
						while(!game.isError){
							game.firstround();
							if(game.exit == 1){
								System.out.println("�^�D���");
								isPlayed = false;
								break;
							}
							if(!game.isError){
								game.battle();
							}
							if(game.isOver){
								System.out.println("Game Over!");
								isPlayed = false;
								break;
							}
						}
						if(game.isError){
							isPlayed = false;
							break;
						}
					}
					else{
						System.out.println("�H�ƶ��j�󵥩�1!");
						isPlayed = false;
						break;
					}									
				}
				catch(Exception e){
					System.out.println("error : " + e);
					isPlayed = false;
					break;
				}
			}
		}
	}
}