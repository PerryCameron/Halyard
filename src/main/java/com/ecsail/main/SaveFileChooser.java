package com.ecsail.main;

import java.io.File;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class SaveFileChooser {
	private File file;
	
	public SaveFileChooser(String directory, String fileName, String Description, String extention) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save");
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Excel Files", "*.xlsx"));
        fileChooser.setInitialDirectory(new File(Paths.ROSTERS + "/"));
        fileChooser.setInitialFileName(fileName);
        this.file = fileChooser.showSaveDialog(Main.getPrimaryStage());  
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

}
