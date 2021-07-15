package models;

public class Condensacion {
	
	private String nameCondensation;
	private Particion particion1;
	private Particion particion2;
	private Particion particion3;
	private int totalSize;
	private Particion particion;
	
	public Condensacion(String namString,Particion particion1, Particion particion2) {
		super();
		this.nameCondensation = namString;
		this.particion1 = particion1;
		this.particion2 = particion2;
	}
	
	public void setNum(int num) {
		particion.setNumCondensed(num);
	}
	public int getNum() {
		return particion.getNumCondensed();
	}
	public Particion getParticion1() {
		return particion1;
	}
	public void setParticion1(Particion particion1) {
		this.particion1 = particion1;
	}
	public Particion getParticion2() {
		return particion2;
	}
	public void setParticion2(Particion particion2) {
		this.particion2 = particion2;
	}
	public Particion getParticion3() {
		return particion3;
	}
	public String getNameCondensation() {
		return nameCondensation;
	}
	public void agregarTerceraParticion(Particion particion3) {
		this.particion3 = particion3;
	}
	
	public Particion getparticion() {
		return particion;
	}
	
	public Particion getParticion(int number) {
		if (particion3 != null) {
			totalSize = particion1.getSizeTotalParticion() + particion2.getSizeTotalParticion() 
			+ particion3.getSizeTotalParticion() ;
		}else {
			totalSize = particion1.getSizeTotalParticion() + particion2.getSizeTotalParticion();
		}
		particion = new Particion("Part"+ number, totalSize );
		particion.setGap(true);
		return particion;
	}
	
	@Override
	public String toString() {
		if (particion3 != null) {
			return "Nombre: " + nameCondensation + " [ Par 1: " + particion1.getNameParticion() 
			+ " - Par 2: " + particion2.getNameParticion() + " - Par 3 " + particion3.getNameParticion() + "]";
		}
		return "Nombre " + nameCondensation + " [Par 1: " + particion1.getNameParticion() 
			+ " - Par 2: " + particion2.getNameParticion()+ "]";
		
	}
}
