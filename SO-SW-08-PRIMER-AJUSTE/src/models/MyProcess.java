package models;

public class MyProcess {
	private String name;
	private int time;
	private int size;
	private boolean attended;
	
	public MyProcess(String name, int time, int size) {
		this.name = name;
		this.time = time;
		this.size = size;
		this.attended =false;
	}

	public String getName() {
		return name;
	}

	public int getSize() {
		return size;
	}

	public int getTime() {
		return time;
	}
	
	public void setTime(int time) {
		this.time = time;
	}
	
	public void setSize(int size) {
		this.size = size;
	}
	
	public boolean isAttended() {
		return attended;
	}

	public void setAttended(boolean attended) {
		this.attended = attended;
	}

	@Override
	public String toString() {
		return String.format("%1$-20s%2$-20s%3$-20s", name, size, time);
	}
	
	public Object[] toVector() {
        return new Object[]{name, size, time};
    }
//	public String toString() {
//        String formatLine = "%1$-12s%2$-18s%3$-18s%4$-12s%5$-12s%6$-4s%7$-2s%8$-3s";
//        String stringDonor = String.format(formatLine, this.id, this.name, this.lastName, this.cellphone, this.bornDate, this.age, this.bloodGroup, this.rH);
//        return stringDonor;
	
}
