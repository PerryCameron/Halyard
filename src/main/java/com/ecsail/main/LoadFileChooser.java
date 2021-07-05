package com.ecsail.main;

import java.io.File;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class LoadFileChooser {
	private File file;
	
	public LoadFileChooser(String directory) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image");
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        //fileChooser.getExtensionFilters().addAll(new ExtensionFilter(Description, extention));
        fileChooser.setInitialDirectory(new File(directory + "/"));
        //fileChooser.setInitialFileName(fileName);
        this.file = fileChooser.showOpenDialog(Main.getPrimaryStage());  
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

}
