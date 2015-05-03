import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

import javax.swing.JFileChooser;

public class Server {

	final int bufferSize = 8196;
	ProgressBar bar;
	FileChooser fc = new FileChooser();
	File[] filesToSend;
	String fileDestination;
	MainWindow frame;

	String username;
	String sendFile;
	String fileName;
	String ip;
	long fileSize;
	Socket messageS;
	Socket fileS;
	ServerSocket server;
	boolean reading = false;
	boolean sending = false;
	boolean sendUsername = true;
	boolean connected;
	boolean isServer;
	int port;
	int filesSent = 0;
	long currentByte = 0;
	BigDecimal bd;
	String beginWord = "mA0cs/Mzar4YEOye6GUj2V5McPq8Cdr766R2g+MtdC8=";
	String endWord = "mA0cs/Mzar4YEOye6GUj2V5McPq8gdr766R2g+MtdC8=";
	InputStreamReader inReadMessage;
	BufferedReader brMessage;
	OutputStreamWriter outWriteMessage;
	BufferedWriter bwMessage;
	FileInputStream fis = null;
	FileOutputStream fos = null;

	public Server() {
		frame = new MainWindow(this);
		isServer = false;
		connected = false;
		fileDestination = System.getProperty("user.home") + "\\Desktop";
		frame.destinationTxt.setText(fileDestination);

		try {
			username = (InetAddress.getLocalHost().getHostName());
		} catch (UnknownHostException e) {
			frame.printMessage("Unable to retrieve computer username, using default");
			username = ("Person");
		}

		new Thread() {
			public void run() {
				while (!connected) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					while (connected) {
						sortMessages();
					}
				}

			}
		}.start();

	}

	public Server(int port) {
		isServer = true;
		frame = new MainWindow(this);
		this.port = port;
		connected = false;
		fileDestination = System.getProperty("user.home") + "\\Desktop";
		frame.destinationTxt.setText(fileDestination);
		try {
			server = new ServerSocket(port);
			listenForClient();
		} catch (IOException e) {
			frame.printMessage("Unable to start server on port " + port);
		}

		try {
			username = (InetAddress.getLocalHost().getHostName());
		} catch (UnknownHostException e) {
			frame.printMessage("Unable to retrieve computer username, using default");
			username = ("Person");
		}

		new Thread() {
			public void run() {
				while (!connected) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					while (connected) {
						sortMessages();
					}
				}

			}
		}.start();

	}

	public void chooseFiles() {
		try {
			fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			filesToSend = fc.chooseFiles();
			long totalSize = 0;
			for (File file : filesToSend) {
				frame.printMessage("You have selected: "
						+ file.getAbsolutePath());
				totalSize += file.length();
			}
			reduceSize(totalSize);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

	}

	public void chooseDestination() {
		try {
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			fileDestination = fc.chooseFile().getAbsolutePath();
			frame.destinationTxt.setText(fileDestination);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

	}

	public void reduceSize(long size) {
		Long gb = 10 * 1024 * 1024 * 1024L;
		Long mb = 10 * 1024 * 1024L;
		Long kb = 10 * 1024L;
		if (size > gb) {
			frame.fileSizeLbl.setText(size / 1024 / 1024 / 1024 + " GBs");
		} else if (size >= mb) {
			frame.fileSizeLbl.setText(size / 1024 / 1024 + " MBs");
		} else if (size >= kb) {
			frame.fileSizeLbl.setText(size / 1024 + " KBs");
		} else {
			frame.fileSizeLbl.setText(size + " Bytes");
		}
	}

	public void askToSend(int fileInArray) {
		if (!connected) {
			frame.printMessage("You are not connected to anyone");
			return;
		}

		if (filesToSend == null) {
			frame.printMessage("Select file(s) to send");
			return;
		}

		if (sending) {
			frame.printMessage("You are already sending a file");
		}

		if (reading) {
			frame.printMessage("You are downloading a file");
		}

		sendMessage("file" + filesToSend[fileInArray].getName() + "*"
				+ filesToSend[fileInArray].length());
	}

	public void listenForClient() {
		new Thread() {
			public void run() {
				try {
					messageS = server.accept();
					fileS = server.accept();
					connected();
				} catch (IOException e) {
					frame.printMessage("Unable to search for clients");
				}
			}
		}.start();
	}

	public String retrieveIp() {
		try {
			URL connection = new URL("http://checkip.amazonaws.com/");
			URLConnection con = connection.openConnection();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			return reader.readLine();
		} catch (IOException e) {
			return null;
		}
	}

	public void connectToServer(int port, String serverIp) {
		frame.printMessage("Attempting to connect to the server");
		new Thread() {
			public void run() {
				boolean firstError = true;
				while (!connected) {
					try {
						messageS = new Socket(serverIp, port);
						fileS = new Socket(serverIp, port);
						ip = serverIp;
						connected();
					} catch (IOException e) {
						if (firstError) {
							frame.printMessage("Server not responding, will keep retrying until connected");
							firstError = false;
						}
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		}.start();

	}

	public void connected() {
		try {
			inReadMessage = new InputStreamReader(messageS.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}

		brMessage = new BufferedReader(inReadMessage);

		try {
			outWriteMessage = new OutputStreamWriter(messageS.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}

		bwMessage = new BufferedWriter(outWriteMessage);

		connected = true;
		sendMessage(username);
	}

	public void disconnected() {
		connected = false;
		frame.printMessage("Partner has disconnected");
		frame.statusDisconnected();
	}

	public void setupUsername(String username) {
		frame.statusConnected(username);
		if (!isServer) {
			frame.printMessage("Connected to Server: " + ip + ":" + port);
		} else {
			frame.printMessage("Connected to " + username);
		}
		sendUsername = false;
	}

	public void sortMessages() {
		String message = readMessages();
		if (sendUsername) {
			frame.statusConnected(message);
			if (!isServer) {
				frame.printMessage("Connected to Server: " + ip + ":" + port);
			} else {
				frame.printMessage("Connected to " + message);
			}
			sendUsername = false;
		} else if (message.substring(0, 2).equals("ok")) {
			sendFile(filesToSend[filesSent]);
			filesSent++;
		} else if (message.substring(0, 4).equals("file")) {
			fileName = message.substring(4, message.indexOf('*'));
			fileSize = Long
					.parseLong(message.substring(message.indexOf('*') + 1));
			sendMessage("ok");
			lookForFiles();
			sendMessage("done");
		} else if (message.substring(0, 4).equals("done")) {
			if (filesSent < filesToSend.length)
				askToSend(filesSent);
			else
				clearFileQueue();
		} else if (message.substring(0, 10).equals("cancelRead")) {
			frame.printMessage(username + " canceled their download");
			sending = false;
		} else if (message.substring(0, 10).equals("cancelSend")) {
			frame.printMessage(username + " canceled their upload");
			reading = false;
		} else if (message.substring(0, 10).equals("percentage")) {
			frame.printMessage(message.substring(10) + "% uploaded");
		}
	}

	public void clearFileQueue() {
		filesSent = 0;
		filesToSend = null;
		frame.fileSizeLbl.setText("0 Bytes");
	}

	public String readMessages() {
		String message = null;

		try {
			do {
				message = brMessage.readLine();
			} while (message == "" || message == null || message == " "
					|| message == "\n");
		} catch (IOException e) {
			disconnected();
		}

		return message;
	}

	public void sendMessage(String message) {
		try {
			bwMessage.write(message);
			bwMessage.newLine();
			bwMessage.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendFile(File file) {
		new Thread() {
			public void run() {
				BufferedInputStream bis = null;
				BufferedOutputStream bos = null;

				byte[] buffer = new byte[bufferSize];

				try {
					fis = new FileInputStream(file);
				} catch (FileNotFoundException e) {
					frame.printMessage("File not found");
					return;
				}

				bis = new BufferedInputStream(fis);
				try {
					bos = new BufferedOutputStream(fileS.getOutputStream());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				int count;
				frame.printMessage("Sending file " + file.getAbsolutePath());
				sending = true;
				long bytesSent = 0;
				try {
					while (((count = bis.read(buffer)) > 0) && sending) {
						bos.write(buffer, 0, count);
						bos.flush();
						bytesSent += count;
					}

				} catch (IOException e) {
					frame.printMessage("Error reading file");
				}
				sending = false;
				if (bytesSent >= file.length())
					frame.printMessage("File Sent: " + file.getAbsolutePath());
			}
		}.start();
	}

	public void lookForFiles() {
		new Thread() {
			public void run() {

				BufferedInputStream bis = null;
				BufferedOutputStream bos = null;
				try {
					bis = new BufferedInputStream(fileS.getInputStream());
				} catch (IOException e) {
					e.printStackTrace();
					return;
				}

				try {
					fos = new FileOutputStream(fileDestination + "\\"
							+ fileName);
					bos = new BufferedOutputStream(fos);
				} catch (FileNotFoundException e) {
					frame.printMessage("File not found");
					e.printStackTrace();
				}

				byte[] bytes = new byte[bufferSize];

				int count;
				long remainingFileSize = fileSize;
				frame.printMessage("Recieving file: " + fileName);
				reading = true;
				currentByte = 0;
				updatePercentage();
				try {
					while (((count = bis.read(bytes)) > 0) && reading) {
						bos.write(bytes, 0, count);
						bos.flush();
						remainingFileSize -= count;
						currentByte += count;
						if (remainingFileSize == 0)
							break;
					}
				} catch (IOException e) {
					frame.printMessage("Error recieving file");
				}
				if (remainingFileSize <= 0)
					frame.printMessage("<Finished> Recieving file: " + fileName);
				reading = false;
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();

	}

	public void cancel() {
		if (sending)
			cancelSend();
		else if (reading)
			cancelRead();
		else
			frame.printMessage("You are not downloading anything");
	}

	public void cancelSend() {
		sending = false;
		frame.printMessage("Upload canceled");
		sendMessage("cancelSend");
	}

	public void cancelRead() {
		reading = false;
		frame.printMessage("Download canceled");
		sendMessage("cancelRead");
		try {
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new File(fileDestination + "\\" + fileName).delete();
	}

	public void updatePercentage() {
		new Thread() {
			public void run() {
				while (reading) {
					printCurrentPercentage(((float) currentByte / fileSize) * 100.);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}

	public void printCurrentPercentage(double percent) {
		bd = new BigDecimal(percent);
		frame.printMessage(bd.setScale(3, RoundingMode.HALF_UP).toPlainString()
				+ "% downloaded");
		sendMessage("percentage"
				+ bd.setScale(3, RoundingMode.HALF_UP).toPlainString());
	}
}