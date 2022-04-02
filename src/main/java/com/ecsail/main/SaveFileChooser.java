package com.ecsail.main;

import java.io.File;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class SaveFileChooser {
	private File file;
	
	public SaveFileChooser(String directory, String fileName, String Description, String extention) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save");
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter(Description, extention));
        fileChooser.setInitialDirectory(new File(directory + "/"));
        fileChooser.setInitialFileName(fileName);
        this.file = fileChooser.showSaveDialog(Halyard.getPrimaryStage());
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

}
