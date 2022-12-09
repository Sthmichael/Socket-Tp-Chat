package serveur;
import java.net.Socket;
import java.net.ServerSocket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Scanner;

public class Serveur {
	public static void main(String[] args) {
		
		
		final ServerSocket serveurSocket;
		final Socket clientSocket;
		final BufferedReader in;
		final PrintWriter out;
		final Scanner sc =new Scanner(System.in);
		try {
			serveurSocket = new ServerSocket(5000);
			
			System.out.println("");
			System.out.println("		.........................Examen chat 2.0.1 Test final..................");
			System.out.println("		                         ----------------------------");
			System.out.println("");
			System.out.println("");
			System.out.println("En attente de connexion ...........");

			clientSocket = serveurSocket.accept();
			String IP = clientSocket.getRemoteSocketAddress().toString();
			System.out.println("");
			System.out.println("	                                      -..................-");
			System.out.println("	                                          -..........-");
			System.out.println("	                                             ......");
			System.out.println("Connexion Etablie avec l'adresse IP="+ IP);
			out= new PrintWriter(clientSocket.getOutputStream());
			in = new BufferedReader (new InputStreamReader (clientSocket.getInputStream()));

	

			Thread envoi= new Thread(new Runnable() {
				String msg;
				
				@Override 
				public void run() {
					while(true) {
						System.out.println("");
						msg = sc.next();
						out.println(msg);
						out.flush();
					}
				}
			});
			envoi.start();
			
			Thread recevoir= new Thread(new Runnable() {
				
				@Override
				public void run() {
					
				String msg;
				try{
					msg = in.readLine();
					while(msg!=null) {
						System.out.println("Client : "+msg);
						System.out.println("");
						msg= in.readLine();
					}
					System.out.println("Client deconect");
					out.close();
					clientSocket.close();
					serveurSocket.close();
				}catch (IOException e) {
					e.printStackTrace();
				}
			} 
			
			
		});
			recevoir.start();
		}catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}

