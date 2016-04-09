package baseballgame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;

class StartBaseBallGame {

	private ArrayList<Player> player_list = new ArrayList<Player>();

	public ArrayList<Player> getPlayer_list() {
		return player_list;
	}

	public void setPlayer_list(ArrayList<Player> player_list) {
		this.player_list = player_list;
	}


	private ArrayList<Integer> computer;
	private ArrayList<Integer> you;
	static final Random rand = new Random();
	Scanner scan = new Scanner(System.in);
	long starttime;
	long endtime;
	int time;
	private int strike;
	private int ball;
	private int cnt;
	private int level;
	private String name;

	StartBaseBallGame(String name) {
		this.computer = new ArrayList<Integer>();
		this.you = new ArrayList<Integer>();
		this.strike = 0;
		this.ball = 0;
		this.cnt = 0;
		this.level = 1;
		this.name = name;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public ArrayList<Integer> getComputer() {
		return computer;
	}

	public void setComputer(ArrayList<Integer> computer) {
		this.computer = computer;
	}

	public ArrayList<Integer> getYou() {
		return you;
	}

	public void setYou(ArrayList<Integer> you) {
		this.you = you;
	}

	public int getStrike() {
		return strike;
	}

	public void setStrike(int strike) {
		this.strike = strike;
	}

	public int getBall() {
		return ball;
	}

	public void setBall(int ball) {
		this.ball = ball;
	}

	public int getCnt() {
		return cnt;
	}

	public void setCnt(int cnt) {
		this.cnt = cnt;
	}

	void outputFile() {
		PrintWriter pw = null;
		try {

			pw = new PrintWriter("c:\\gamerank.txt");

			pw.write("---------------순위---------------");
			pw.write("이름" + "\t" + "레벨" + "\t" + "걸린시간" + "\t");

			for (int i = 0; i < player_list.size(); i++) {
				pw.write(player_list.get(i).getName() + "\t");
				pw.write(player_list.get(i).getLevel() + "\t");
				pw.write(player_list.get(i).getTime() + "\t");
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			pw.close();
		}

	}
	
	void BubleSort() {// 삽입정렬

		Player pivot;

		for (int i = 0; i < player_list.size() - 1; i++) {
			for (int j = 0; j < player_list.size() - i - 1; j++) {
				if (player_list.get(j).getLevel() < player_list.get(j + 1).getLevel()) {
					pivot = player_list.get(j);
					player_list.set(j, player_list.get(j + 1));
					player_list.set(j + 1, pivot);
				}
			}
		}
	}

	void InputFile() {
		File f = new File("c:\\gamerank.txt");
		String line;
		String name;
		int rank;
		int level;
		int time;
		Player basket;
		BufferedReader fr = null;

		try {
			if (f.exists()) {
				try {
					fr = new BufferedReader(new FileReader("c:\\gamerank.txt"));
					while ((line = fr.readLine()) != null) {
						StringTokenizer token = new StringTokenizer(line, "\t");
						name = token.nextToken();
						level = Integer.parseInt(token.nextToken());
						rank = Integer.parseInt(token.nextToken());
						time = Integer.parseInt(token.nextToken());
						basket = new Player(name);
						basket.setLevel(level);
						basket.setRank(rank);
						basket.setTime(time);
						player_list.add(basket);
					}
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					fr.close();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void setComNumber() {
		for (int i = 0; i < getYou().size(); i++) {
			getComputer().add(rand.nextInt(9) + 1);
			if (i > 0) {
				for (int j = i - 1; j >= 0; j--) {
					if (getComputer().get(i) == getComputer().get(j)) {
						getComputer().remove(i);
						i--;
					}
				}
			}
		}
	}

	void InputNumber(String[] args) {

		for (int i = 0; i < args.length; i++) {
			you.add(Integer.parseInt(args[i]));
		}
	}
	
	void setrank() {

		int rank = 1;

		for (int i = 0; i < player_list.size(); i++) // 등수 매기기
		{
			for (int j = 0; j <= player_list.size() - 1; j++) {
				if (player_list.get(i).getLevel() < player_list.get(j).getLevel()) {
					rank++;
				}
			}

			player_list.get(i).setRank(rank);
			rank = 1;
		}
	}
	

	void matchBall() {
		System.out.println(getYou().size());
		for (int i = 0; i < getYou().size(); i++) {
			if (getComputer().get(i) == getYou().get(i))
				strike++;
		}

		for (int i = 0; i < getYou().size(); i++) {
			for (int j = 0; j < getYou().size(); j++) {
				if (getComputer().get(i) == getYou().get(j) && i != j)
					ball++;
			}
		}

	}

}