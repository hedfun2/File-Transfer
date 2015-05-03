import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

public class OptionJFrame extends JFrame implements ActionListener {

	JButton serverB, clientB;
	Color textColor = new Color(230, 230, 230);
	Color backgroundColor = new Color(51, 51, 51);
	Color componentColor = new Color(102, 102, 102);	
	
	public OptionJFrame() {
		initJFrame();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == serverB) {
			new ServerOptionWindow();
		} else if (e.getSource() == clientB) {
			new Server();
		}
		this.dispose();
	}

	public void initJFrame() {
		JPanel contentPane;
		setResizable(false);
		super.setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 198);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setBackground(backgroundColor);

		JLabel titleTxt = new JLabel("Are You:");
		titleTxt.setForeground(textColor);
		titleTxt.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 48));
		titleTxt.setHorizontalAlignment(SwingConstants.CENTER);

		serverB = new JButton("Server");
		serverB.setForeground(textColor);
		serverB.setBackground(componentColor);
		serverB.addActionListener(this);

		clientB = new JButton("Client");
		clientB.setForeground(textColor);
		clientB.setBackground(componentColor);
		clientB.addActionListener(this);

		JLabel lblprobablyThisOne = new JLabel("(probably this one)");
		lblprobablyThisOne.setForeground(textColor);
		lblprobablyThisOne.setFont(new Font("Microsoft Sans Serif", Font.PLAIN,
				11));
		lblprobablyThisOne.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel middleTxt = new JLabel("or");
		middleTxt.setForeground(textColor);
		middleTxt.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
		middleTxt.setHorizontalAlignment(SwingConstants.CENTER);
		
		setLocationRelativeTo(null);
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane
				.setHorizontalGroup(gl_contentPane
						.createParallelGroup(Alignment.TRAILING)
						.addGroup(
								gl_contentPane
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(titleTxt,
												GroupLayout.DEFAULT_SIZE, 404,
												Short.MAX_VALUE)
										.addContainerGap())
						.addGroup(
								gl_contentPane
										.createSequentialGroup()
										.addGap(68)
										.addComponent(serverB,
												GroupLayout.PREFERRED_SIZE, 96,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addComponent(middleTxt,
												GroupLayout.DEFAULT_SIZE, 88,
												Short.MAX_VALUE)
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												gl_contentPane
														.createParallelGroup(
																Alignment.TRAILING,
																false)
														.addComponent(
																lblprobablyThisOne,
																Alignment.LEADING,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addComponent(
																clientB,
																Alignment.LEADING,
																GroupLayout.PREFERRED_SIZE,
																96,
																GroupLayout.PREFERRED_SIZE))
										.addGap(66)));
		gl_contentPane
				.setVerticalGroup(gl_contentPane
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_contentPane
										.createSequentialGroup()
										.addComponent(titleTxt)
										.addPreferredGap(
												ComponentPlacement.UNRELATED)
										.addGroup(
												gl_contentPane
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																serverB,
																GroupLayout.PREFERRED_SIZE,
																52,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																clientB,
																GroupLayout.PREFERRED_SIZE,
																52,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(middleTxt))
										.addPreferredGap(
												ComponentPlacement.UNRELATED)
										.addComponent(lblprobablyThisOne)
										.addContainerGap(47, Short.MAX_VALUE)));
		contentPane.setLayout(gl_contentPane);
	}

}
