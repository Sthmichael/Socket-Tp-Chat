package client;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Scanner;
import java.net.Socket;

public class Client {

	public static void main(String[] args) {
		
		final Socket clientSocket;
		final BufferedReader in;
		final PrintWriter out;
		final Scanner sc=new Scanner(System.in);

		try {
			clientSocket=new Socket("localhost",5000);
			System.out.println("Connexion Etablie");
			System.out.println("Veullez maintenant entre votre message");
			out=new PrintWriter(clientSocket.getOutputStream());
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			
			Thread envoyer= new Thread(new Runnable() { 
					String msg;

					@Override
					public void run() {
						while(true) {
							msg=sc.nextLine();
							out.println(msg);
							out.flush();
						}
						
					}
			});
			envoyer.start();
			
			Thread recevoir= new Thread(new Runnable() {
				String msg;
				@Override
				public void run() {
					try {
						msg=in.readLine();
						while(msg!=null) {
							System.out.println("");
							System.out.println("Serveur : "+msg);
							System.out.println("");
							msg=in.readLine();
						}
						System.out.println("Serveur deconect");
						out.close();
						clientSocket.close();
					}catch(IOException e) {
						e.printStackTrace();
					}
					
				}
				
			});
			recevoir.start();
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

}

