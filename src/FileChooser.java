import java.io.File;
import java.util.ArrayList;

import javax.swing.JFileChooser;


public class FileChooser extends JFileChooser{
	
	
	public FileChooser(){
		super.setMultiSelectionEnabled(true);
	}
	
	
	public File chooseFile(){
		super.showOpenDialog(null);
		return super.getSelectedFile();
	}
	
	public File[] chooseFiles(){
		super.showOpenDialog(null);
		return super.getSelectedFiles();
	}
	
	public File chooseFolder(){
		setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		showOpenDialog(null);
		setFileSelectionMode(JFileChooser.FILES_ONLY);
		return super.getCurrentDirectory();
	}

}
