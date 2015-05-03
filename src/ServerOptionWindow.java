import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

public class ServerOptionWindow extends JFrame implements ActionListener,
		KeyListener {

	JTextField portTF;
	JButton okB;
	JLabel invalidTxt;
	Color textColor = new Color(230, 230, 230);
	Color backgroundColor = new Color(51, 51, 51);
	Color componentColor = new Color(102, 102, 102);
	
	
	public ServerOptionWindow() {
		initJFrame();
	}

	public boolean isValidPort(String textFieldTxt) {
		int port;
		try {
			port = Integer.parseInt(textFieldTxt);
		} catch (NumberFormatException e) {
			return false;
		}

		if (port > 65535 || port < 0)
			return false;
		else
			return true;

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (isValidPort(portTF.getText())) {
			new Server(Integer.parseInt(portTF.getText()));
			this.dispose();
		} else {
			invalidTxt.setText("Invalid Port Number");
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == 10) {
			if (isValidPort(portTF.getText())) {
				new Server(Integer.parseInt(portTF.getText()));
				this.dispose();
			} else {
				invalidTxt.setText("Invalid Port Number");
			}

		}

	}

	public void initJFrame() {
		JPanel contentPane;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 358, 131);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		getContentPane().setBackground(backgroundColor);

		JLabel infoTxt = new JLabel("Enter server port:");
		infoTxt.setForeground(textColor);
		infoTxt.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 24));

		portTF = new JTextField();
		portTF.setBorder(null);
		portTF.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));
		portTF.setForeground(textColor);
		portTF.setBackground(componentColor);
		portTF.setColumns(10);

		okB = new JButton("Ok");
		okB.setBorder(null);
		okB.setForeground(textColor);
		okB.setBackground(componentColor);
		okB.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 12));

		invalidTxt = new JLabel("");
		invalidTxt.setForeground(textColor);
		invalidTxt.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 16));
		invalidTxt.setHorizontalAlignment(SwingConstants.CENTER);

		portTF.addKeyListener(this);
		okB.addActionListener(this);
		setResizable(false);
		super.setVisible(true);
		setLocationRelativeTo(null);

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane
				.setHorizontalGroup(gl_contentPane
						.createParallelGroup(Alignment.TRAILING)
						.addGroup(
								gl_contentPane
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												gl_contentPane
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																invalidTxt,
																Alignment.TRAILING,
																GroupLayout.DEFAULT_SIZE,
																319,
																Short.MAX_VALUE)
														.addGroup(
																Alignment.TRAILING,
																gl_contentPane
																		.createSequentialGroup()
																		.addComponent(
																				infoTxt,
																				GroupLayout.PREFERRED_SIZE,
																				193,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				portTF,
																				GroupLayout.PREFERRED_SIZE,
																				50,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.UNRELATED)
																		.addComponent(
																				okB,
																				GroupLayout.PREFERRED_SIZE,
																				62,
																				GroupLayout.PREFERRED_SIZE)))
										.addContainerGap()));
		gl_contentPane
				.setVerticalGroup(gl_contentPane
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_contentPane
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												gl_contentPane
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																infoTxt,
																GroupLayout.DEFAULT_SIZE,
																40,
																Short.MAX_VALUE)
														.addComponent(
																portTF,
																GroupLayout.PREFERRED_SIZE,
																33,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(okB))
										.addPreferredGap(
												ComponentPlacement.UNRELATED)
										.addComponent(invalidTxt,
												GroupLayout.PREFERRED_SIZE, 21,
												GroupLayout.PREFERRED_SIZE)));
		contentPane.setLayout(gl_contentPane);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}
