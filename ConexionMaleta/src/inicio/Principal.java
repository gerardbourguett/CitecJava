package inicio;

import java.util.Scanner;

import jssc.*;

public class Principal {
	
	//instanciaremos un objeto del tipo SerialPort, necesario por la librería 
	//ya que es el  stream necesario para la comunicación
	
	static SerialPort puerto;
	static int mascara;
	
	public static void main(String[] args) throws SerialPortException {
		Scanner entrada = new Scanner(System.in);
		String cadena; 
		
		puerto = new SerialPort("/dev/ttyUSB0");
		puerto.openPort();
		puerto.setParams(9600, 8, 1, 0);
		mascara = SerialPort.MASK_RXCHAR;
		puerto.setEventsMask(mascara);
		puerto.addEventListener(new EntradaSerial());
		
		System.out.println("Texto a enviar: ");
		cadena = entrada.nextLine();
		puerto.writeString(cadena);
		System.out.println("Enviaste "+cadena);
	}
	
	static class EntradaSerial implements SerialPortEventListener{
		public void serialEvent(SerialPortEvent event) {
			if(event.isRXCHAR()) {
				try {
					String buffer = puerto.readString();
					System.out.println("RECIBIENDO: "+buffer);
				} catch(SerialPortException ex) {
					System.out.println(ex);
				}
			}
		}
	}
}
