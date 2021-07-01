package utilities;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Utillidades {

	public static void showMessageWarning(String mensaje, String title) {
		JOptionPane.showMessageDialog(null, mensaje, title, JOptionPane.WARNING_MESSAGE,new ImageIcon("D:\\asus\\Desktop\\ESCRITORIO\\PROGRAMACION III V\\S_O_Proyecto 6\\src\\img\\logoadmir.png"));
	}
	
	public static void showMessageInformation(String mensaje, String title) {
		JOptionPane.showMessageDialog(null, mensaje, title, JOptionPane.INFORMATION_MESSAGE,new ImageIcon("D:\\asus\\Desktop\\ESCRITORIO\\PROGRAMACION III V\\S_O_Proyecto 6\\src\\img\\logoadmir.png"));
	}

	public static void showMessageError(String mensaje, String title) {
		JOptionPane.showMessageDialog(null, mensaje, title, JOptionPane.ERROR_MESSAGE, new ImageIcon("D:\\\\asus\\\\Desktop\\\\ESCRITORIO\\\\PROGRAMACION III V\\\\S_O_Proyecto 6\\\\src\\\\img\\logoError.png"));
	}
	public static void inputNumber(String mensaje, String title) {
		JOptionPane.showMessageDialog(null, mensaje, title, JOptionPane.ERROR_MESSAGE);
	}

	public static Integer convertData(String data) {
		try {
			if (data != null && !data.isEmpty()) {
				return Integer.parseInt(data);
			}
		} catch (NumberFormatException e) {
			showMessageError("No se puede convertir la cadena: "+data+" a numero ", "Error");
		}
		return null;
	}
	
	public static Short convertStringToShort(String data) {
		try {
			if (data != null && !data.isEmpty()) {
				return Short.parseShort(data);
			}
		} catch (NumberFormatException e) {
			showMessageError("No se puede convertir la cadena: "+data+" a numero ", "Error");
		}
		return null;
	}
	
	public static Integer convertStringToInt(String data) {
		try {
			if (data != null && !data.isEmpty()) {
				return Integer.parseInt(data);
			}
		} catch (NumberFormatException e) {
			showMessageError("No se puede convertir la cadena: "+data+" a numero ", "Error");
		}
		return (Integer) null;
	}
	
	
	public static Byte convertStringToByte(String data) {
		try {
			if (data != null && !data.isEmpty()) {
				return Byte.parseByte(data);
			}
		} catch (NumberFormatException e) {
			showMessageError("No se puede convertir la cadena: "+data+" a numero ", "Error");
		}
		return null;
	}
	
	public static boolean isNumeric(String cadena){
		try {
			Integer.parseInt(cadena);
			return true;
		} catch (NumberFormatException nfe){
			return false;
		}
	}
}