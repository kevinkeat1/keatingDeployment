package keatingDeployment;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class keatingClient {
	
	private static JTextField numberTextField;
	private static JTextArea responseArea;
	private final static String newline = "\n";

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 500);
		frame.setTitle("Client");
		frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

		JPanel clientPanel = new JPanel();
		
		JLabel numLabel =new JLabel("Enter number to check if prime:"); //User enters number here
		numLabel.setMaximumSize(new Dimension(100, 30));
		clientPanel.add(numLabel);
		
		numberTextField = new JTextField();
		numberTextField.setPreferredSize(new Dimension(150, 30));
		clientPanel.add(numberTextField);
		
		frame.add(clientPanel);
		
		JPanel responsePanel = new JPanel();
		
		responseArea = new JTextArea(); //Where data is displayed
		JScrollPane scrollPane = new JScrollPane(responseArea); 
		responseArea.setEditable(false);
		scrollPane.setPreferredSize(new Dimension(270, 250));
		scrollPane.setViewportView(responseArea);
		responsePanel.add(scrollPane);
		
		frame.add(responsePanel);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(new JLabel());
		
		JButton transmitBtn = new JButton("Transmit");
		transmitBtn.addActionListener(transmitActionListener());
		transmitBtn.setPreferredSize(new Dimension(140, 30));
		buttonPanel.add(transmitBtn);
		frame.add(buttonPanel);

		
		frame.setVisible(true);


	}

	private static ActionListener transmitActionListener() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					int num = Integer.parseInt(numberTextField.getText().trim());
					Socket socket = new Socket("localhost", 1236); // Create a socket to connect to the server
				    DataInputStream fromServer = new DataInputStream(socket.getInputStream());
				    DataOutputStream toServer = new DataOutputStream(socket.getOutputStream());
				    
				    toServer.writeInt(num);
				    toServer.flush();
				    String message;
				    while ((message = fromServer.readLine()) != null) {
				    	responseArea.append(message);
				    }
				    System.out.println(fromServer.readLine());
				    
				} catch (IOException ex) {
					responseArea.append(ex.toString() + "\n");
				}
				
			}
		};
	}

}
