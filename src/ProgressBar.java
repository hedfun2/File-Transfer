import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.GroupLayout.Alignment;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

public class ProgressBar extends JFrame {

	JProgressBar progressBar;
	JTextArea progressTxt;
	long fileSize;
	long currentByte;
	Timer time = new Timer(50, new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			updateBar();

		}
	});

	public ProgressBar(long fileSize) {
		this.fileSize = fileSize;
		initJFrame();
		time.start();

	}

	public void setCurrentByte(long currentByte) {
		this.currentByte = currentByte;
	}

	public void updateBar() {
		try {
			System.out.println(currentByte + "   " + fileSize);
			progressBar.setValue((int) ((currentByte / fileSize)*100));
		} catch (NullPointerException e) {
			System.err.println("Error Updating Progress Bar");
		}
	}

	public void initJFrame() {
		JPanel contentPane;

		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 497, 176);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setLocationRelativeTo(null);

		progressBar = new JProgressBar();
		progressBar.setMaximum(100);
		progressBar.setMinimum(100);
		JScrollPane scrollPane = new JScrollPane();

		progressTxt = new JTextArea();
		scrollPane.setViewportView(progressTxt);

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		contentPane.setLayout(gl_contentPane);
		gl_contentPane
				.setHorizontalGroup(gl_contentPane
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								Alignment.TRAILING,
								gl_contentPane
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												gl_contentPane
														.createParallelGroup(
																Alignment.TRAILING)
														.addComponent(
																scrollPane,
																Alignment.LEADING,
																GroupLayout.DEFAULT_SIZE,
																453,
																Short.MAX_VALUE)
														.addComponent(
																progressBar,
																Alignment.LEADING,
																GroupLayout.DEFAULT_SIZE,
																451,
																Short.MAX_VALUE))
										.addContainerGap()));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_contentPane
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(progressBar, GroupLayout.PREFERRED_SIZE,
								24, GroupLayout.PREFERRED_SIZE)
						.addGap(18)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE,
								70, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)));

	}

}
