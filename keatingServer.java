package keatingDeployment;

import java.awt.Dimension;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class keatingServer {
	
	private static final int PORT = 1236;
	private static JTextArea responseArea;
	private final static String newline = "\n";

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(290, 350);
		frame.setTitle("Server");
		frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		
		JPanel responsePanel = new JPanel();
		
		responseArea = new JTextArea(); //Where data is displayed
		JScrollPane scrollPane = new JScrollPane(responseArea); 
		responseArea.setEditable(false);
		scrollPane.setPreferredSize(new Dimension(250, 350));
		scrollPane.setViewportView(responseArea);
		responsePanel.add(scrollPane);
		
		frame.add(responsePanel);
		
		frame.setVisible(true);
		
		try {
			ServerSocket serverSocket = new ServerSocket(PORT);
			
			while(true) {
				Socket socket = serverSocket.accept();
				DataInputStream inputFromClient = new DataInputStream(socket.getInputStream());
				DataOutputStream outputToClient = new DataOutputStream(socket.getOutputStream());
				
				String message = "";
				
				int num = inputFromClient.readInt();
				int count = 0;
				
				responseArea.append("Number sent is " + num + "\n");
				
				 boolean flag = false; //Math to check for prime number
				 for(int i = 1; i <= num/2; i++) {
					 if(num % i == 0) {
						 count++;
					 }
					 
					 if (count > 1) {
						 message = num + " is not a prime number" + "\n";
					 }
					 else {
						 message = num + " is a prime number" + "\n";
					 }
				 }
				
				outputToClient.writeChars(message);
				outputToClient.flush();
				outputToClient.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 

	}

}
