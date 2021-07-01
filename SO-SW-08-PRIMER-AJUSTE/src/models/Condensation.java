package models;

import java.util.LinkedList;

public class Condensation { //cada iteracion
	private LinkedList<Partition> partitions;
	
	
	public Condensation() {
		partitions = new LinkedList<>();
	}
	
	public void addPartition(Partition partition){
		partitions.add(partition);
	}

	public LinkedList<Partition> getPartitions() {
		return partitions;
	}
	
}
