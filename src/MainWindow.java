import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;

public class MainWindow extends JFrame implements ActionListener{

	Server mainServer;
	JButton chooseB, connectB, destinationB, clearB , cancelB;
	JLabel peopleOnlineTxt, ipTxt, portTxt;
	JLabel onlineHintTxt;
	JLabel connectedStatus;
	JTextArea userConsole;
	JScrollPane consoleScroll;
	JLabel fileSizeTxt;
	JButton sendB;
	JLabel fileSizeLbl;
	JTextField portTF, ipTF;
	JLabel saveTxt, destinationTxt;
	DefaultCaret caret;
	Color textColor = new Color(230, 230, 230);
	Color backgroundColor = new Color(51, 51, 51);
	Color componentColor = new Color(102, 102, 102);
	int lineNumber = 0;
	
	public MainWindow(Server mainServer) {
		this.mainServer = mainServer;
		initJFrame();
		
		if(mainServer.isServer){
			connectB.setEnabled(false);
			ipTF.setEnabled(false);
			portTF.setEnabled(false);
		}
		
	}

	public void printMessage(String message) {
		if (userConsole.getText().isEmpty()) {
			userConsole.setText(message);
		}else{
			userConsole.append("\n" + message);
		}
	}

	public boolean isValidPort(String portStr) {
		int port;
		try {
			port = Integer.parseInt(portStr);
		} catch (NumberFormatException e) {
			return false;
		}

		if (port > 65535 || port < 0)
			return false;
		else
			return true;

	}

	public void statusConnected(String username){
		this.connectedStatus.setText("Connected To " + username);
	}
	
	public void statusDisconnected(){
		this.connectedStatus.setText("Not Connected To Anyone");
	}
	
	public boolean isValidIp(String ip) {
		if (ip.length() < 7 || ip.length() > 15) {
			return false;
		} else if ((ip.length() - ip.replace(".", "").length()) != 3) {
			return false;
		}

		try {
			String temp = ip;
			Integer.parseInt(temp.substring(0, temp.indexOf('.')));
			temp = ip.substring(temp.indexOf('.') + 1);
			Integer.parseInt(temp.substring(0, temp.indexOf('.')));
			temp = temp.substring(temp.indexOf('.') + 1);
			Integer.parseInt(temp.substring(0, temp.indexOf('.')));
			temp = temp.substring(temp.indexOf('.') + 1);
			Integer.parseInt(temp);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == sendB) {
			mainServer.askToSend(0);
		} else if (e.getSource() == chooseB) {
			mainServer.chooseFiles();
		} else if (e.getSource() == connectB) {
			if (!isValidIp(ipTF.getText())) {
				printMessage("Invalid IP Address");
				return;
			}

			if (!isValidPort(portTF.getText())) {
				printMessage("Invalid Port");
				return;
			}
			connectB.setEnabled(false);
			mainServer.connectToServer(Integer.parseInt(portTF.getText()), ipTF.getText());
		}else if(e.getSource() == destinationB){
			mainServer.chooseDestination();
		}else if(e.getSource() == clearB){
			mainServer.clearFileQueue();
			printMessage("Selected files cleared");
		}else if(e.getSource() == cancelB){
			mainServer.cancel();
		}

	}
	
	public void initJFrame() {
		JPanel contentPane;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 787, 346);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setBackground(backgroundColor);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		
		chooseB = new JButton("Choose File(s)");
		chooseB.setBorder(null);
		chooseB.setBackground(componentColor);
		chooseB.setForeground(textColor);
		chooseB.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 16));
		
		userConsole = new JTextArea();
		userConsole.setForeground(textColor);
		userConsole.setBackground(componentColor);
		userConsole.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 12));
		
		fileSizeTxt = new JLabel("Selected Files Size: ");
		fileSizeTxt.setForeground(textColor);
		fileSizeTxt.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));
		
		fileSizeLbl = new JLabel("0 Bytes");
		fileSizeLbl.setForeground(textColor);
		fileSizeLbl.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));
		
		sendB = new JButton("Send");
		sendB.setBorder(null);
		sendB.setBackground(componentColor);
		sendB.setForeground(textColor);
		sendB.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 16));
		
		connectedStatus = new JLabel("Not Connected To Anyone");
		connectedStatus.setForeground(textColor);
		connectedStatus.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 24));
		
		ipTxt = new JLabel("Server IP");
		ipTxt.setForeground(textColor);
		ipTxt.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 18));
		
		portTxt = new JLabel("Server Port");
		portTxt.setForeground(textColor);
		portTxt.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 18));
		
		ipTF = new JTextField();
		ipTF.setBorder(null);
		ipTF.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 12));
		ipTF.setForeground(textColor);
		ipTF.setBackground(componentColor);
		ipTF.setColumns(10);
		
		portTF = new JTextField();
		portTF.setBorder(null);
		portTF.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 12));
		portTF.setBackground(componentColor);
		portTF.setForeground(textColor);
		portTF.setColumns(10);
		
		connectB = new JButton("Connect");
		connectB.setBorder(null);
		connectB.setBackground(componentColor);
		connectB.setForeground(textColor);
		connectB.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));
		
		saveTxt = new JLabel("Save Files Here:");
		saveTxt.setForeground(textColor);
		saveTxt.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));
		
		destinationTxt = new JLabel("");
		destinationTxt.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 11));
		destinationTxt.setForeground(textColor);
		
		destinationB = new JButton("Save File Where");
		destinationB.setBorder(null);
		destinationB.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 16));
		destinationB.setForeground(textColor);
		destinationB.setBackground(componentColor);
		
		clearB = new JButton("Clear Files");
		clearB.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 16));
		clearB.setBorder(null);
		clearB.setForeground(textColor);
		clearB.setBackground(componentColor);
		
		cancelB = new JButton("Cancel");
		cancelB.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 16));
		cancelB.setBorder(null);
		cancelB.setForeground(textColor);
		cancelB.setBackground(componentColor);
		
		setResizable(false);
		setVisible(true);
		setLocationRelativeTo(null);
        userConsole.setLineWrap(true);
        userConsole.setWrapStyleWord(true);
        userConsole.setEditable(false);
		caret = (DefaultCaret) userConsole.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        consoleScroll = new JScrollPane();
		consoleScroll.setViewportView(userConsole);
		chooseB.addActionListener(this);
		sendB.addActionListener(this);
		connectB.addActionListener(this);
		destinationB.addActionListener(this);
		cancelB.addActionListener(this);
		clearB.addActionListener(this);
		
		contentPane.add(consoleScroll);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		consoleScroll.setViewportView(userConsole);
		contentPane.setLayout(gl_contentPane);
		gl_contentPane.setHorizontalGroup(
				gl_contentPane.createParallelGroup(Alignment.TRAILING)
					.addGroup(gl_contentPane.createSequentialGroup()
						.addContainerGap()
						.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
							.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(clearB, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(cancelB, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED, 246, Short.MAX_VALUE)
								.addComponent(connectedStatus))
							.addGroup(gl_contentPane.createSequentialGroup()
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(saveTxt, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(destinationTxt, GroupLayout.PREFERRED_SIZE, 385, GroupLayout.PREFERRED_SIZE))
									.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(fileSizeTxt)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(fileSizeLbl, GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE))
									.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(chooseB, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(sendB, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(destinationB, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)))
								.addPreferredGap(ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(ipTxt, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(ipTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(portTxt, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
											.addComponent(connectB, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
											.addComponent(portTF, Alignment.TRAILING)))))
							.addComponent(consoleScroll, GroupLayout.PREFERRED_SIZE, 741, GroupLayout.PREFERRED_SIZE))
						.addContainerGap())
			);
			gl_contentPane.setVerticalGroup(
				gl_contentPane.createParallelGroup(Alignment.TRAILING)
					.addGroup(gl_contentPane.createSequentialGroup()
						.addContainerGap()
						.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
							.addGroup(gl_contentPane.createSequentialGroup()
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_contentPane.createSequentialGroup()
										.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
											.addComponent(ipTxt, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
											.addComponent(ipTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
											.addComponent(portTxt, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
											.addComponent(portTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
									.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
										.addComponent(chooseB, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
										.addComponent(sendB, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
										.addComponent(destinationB, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)))
								.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
									.addGroup(gl_contentPane.createSequentialGroup()
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(connectB, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED, 25, Short.MAX_VALUE))
									.addGroup(gl_contentPane.createSequentialGroup()
										.addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
											.addComponent(saveTxt, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
											.addComponent(destinationTxt, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
											.addComponent(fileSizeTxt, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
											.addComponent(fileSizeLbl, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(ComponentPlacement.RELATED)))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(cancelB, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
								.addGap(16))
							.addGroup(gl_contentPane.createSequentialGroup()
								.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
									.addComponent(connectedStatus, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
									.addComponent(clearB, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
								.addGap(11)))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(consoleScroll, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE))
			);
		

	}

}
