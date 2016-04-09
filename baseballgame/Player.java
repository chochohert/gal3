package baseballgame;

class Player {
	String name;
	int level;
	int rank;
	int time;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public Player(String name) {
		this.name = name;
		this.level = 1;
		this.rank = 1;
		this.time=0;
	}
	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}
}
