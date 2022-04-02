package com.ecsail.gui.dialogues;

import java.io.File;
import java.net.MalformedURLException;

import com.ecsail.main.Halyard;
import com.ecsail.main.HalyardPaths;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.effect.Light.Point;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Dialogue_ChoosePicture extends Stage {
	private ImageView imageView;
	private ImageView croppedImageView = new ImageView();
	private double startWidth = 400;
	private double croppedWidth = 191; // gives a picture width of 192
	private double croppedHeight = 221; // gives a picture height of 122
	Scene scene;
	public Dialogue_ChoosePicture() {

		VBox vboxGrey = new VBox(); // this is the vbox for organizing all the widgets
		VBox vboxBlue = new VBox();
		VBox vboxPink = new VBox(); // this creates a pink border around the table
		Button browseButton = new Button("Browse");
		/////////
		Pane root = new Pane();
		Image memberPhoto = new Image(getClass().getResourceAsStream(HalyardPaths.DEFAULTPHOTO));
		Image mainIcon = new Image(getClass().getResourceAsStream("/ECSC64.png"));
        ScrollPane scrollPane = new ScrollPane();
        final Rectangle selection = new Rectangle();
        final Point anchor = new Point();
        FileChooser fileChooser = new FileChooser();
        VBox vboxSideControls = new VBox();
        BorderPane borderPane = new BorderPane();
        ContextMenu contextMenu = new ContextMenu();
        MenuItem cropMenuItem = new MenuItem("Crop");
        VBox vboxMain = new VBox();
        VBox vboxControls = new VBox();
        Slider slider = new Slider();
        Group imageGroup = new Group();
        scene = new Scene(vboxBlue, 800, 800);
		//Scene scene = new Scene(vboxBlue, 600, 300);

		/////////////////// ATTRIBUTES ///////////////////
		vboxBlue.setId("box-blue");
		vboxBlue.setPadding(new Insets(10, 10, 10, 10));
		vboxPink.setPadding(new Insets(3, 3, 3, 3)); // spacing to make pink from around table
		vboxPink.setId("box-pink");
		
        /////////////////// ATTRIBUTES //////////////////
        croppedImageView.setImage(memberPhoto);
        
        slider.setMin(0);
        slider.setMax(1024);
        slider.setValue(500);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(50);
        slider.setMinorTickCount(5);
        slider.setBlockIncrement(10);
        startWidth = slider.getValue();
        vboxSideControls.setPrefSize(192, 222);
        vboxSideControls.setStyle("-fx-background-color: #201ac9;");  // blue
        //Image image = new Image(localUrl);
        imageView = new ImageView();
        imageView.setFitWidth(startWidth);
        imageView.setPreserveRatio(true);

		// vboxGrey.setId("slip-box");
		vboxGrey.setPrefHeight(688);
		vboxPink.setMaxHeight(Double.MAX_VALUE);
		scene.getStylesheets().add("stylesheet.css");

		setTitle("Photo Chooser");

		setAlwaysOnTop(true);
		
		//////////////// LISTENERS ///////////////////
        browseButton.setOnAction((event) -> {
        	File file = fileChooser.showOpenDialog(Halyard.getPrimaryStage());
    		if (file != null) {
                String localUrl = null;
				try {
					localUrl = file.toURI().toURL().toString();
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                Image image = new Image(localUrl);
                imageView.setImage(image);
            }
        });
        
        slider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
            	imageView.setFitWidth((double) new_val);
            }
        });
        
		imageGroup.setOnMousePressed(event -> {
			if (event.isPrimaryButtonDown()) {
				root.getChildren().remove(selection);
				anchor.setX(event.getX());
				anchor.setY(event.getY());
				selection.setX(event.getX());
				selection.setY(event.getY());
				selection.setFill(null); // transparent
				selection.setStroke(Color.RED); // border
				selection.getStrokeDashArray().add(5.0);
				root.getChildren().add(selection);
			}
			if (event.isSecondaryButtonDown()) {
				contextMenu.show(imageGroup, event.getScreenX(), event.getScreenY());
			}
		});
        
        imageGroup.setOnMouseDragged(event -> {
        	// will make 192 x 222 image
            selection.setWidth(croppedWidth);
            selection.setHeight(croppedHeight);
            selection.setX(event.getX());
            selection.setY(event.getY());
        });
         
        imageGroup.setOnMouseReleased(event -> {
            // Do what you want with selection's properties here
            System.out.printf("X: %.2f, Y: %.2f, Width: %.2f, Height: %.2f%n", 
                    selection.getX(), selection.getY(), selection.getWidth(), selection.getHeight());

        });
        
        cropMenuItem.setOnAction((event) -> {
                // get bounds for image crop
                Bounds selectionBounds = selection.getBoundsInLocal();

                // show bounds info
                System.out.println( "Selected area: " + selectionBounds);

                // crop the image
                crop( selectionBounds);
        });
        
        imageView.fitWidthProperty().addListener((obs, oldVal, newVal) -> {
	    	 setWidth((double) newVal + vboxControls.getWidth());
	    });

		
		//////////////// ADD CONTENT ///////////////////
        imageGroup.getChildren().add(imageView);
        scrollPane.setContent(imageGroup);
        contextMenu.getItems().add( cropMenuItem);
        vboxSideControls.getChildren().addAll(browseButton,croppedImageView);
        vboxMain.getChildren().addAll(scrollPane);

        root.getChildren().addAll(vboxMain);
        borderPane.setCenter(root);
        borderPane.setLeft(vboxSideControls);
        borderPane.setTop(slider);
        
        
		vboxGrey.getChildren().add(borderPane);
		vboxPink.getChildren().add(vboxGrey);
		vboxBlue.getChildren().add(vboxPink);
		getIcons().add(mainIcon);
		setScene(scene);
		show();
	}
	
    private void crop( Bounds bounds) {

        //FileChooser fileChooser = new FileChooser();
        //fileChooser.setTitle("Save Image");
        //File file = fileChooser.showSaveDialog( primaryStage);
        
       // if (file == null)
       //     return;

        int width = (int) bounds.getWidth();
        int height = (int) bounds.getHeight();

        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        parameters.setViewport(new Rectangle2D( bounds.getMinX(), bounds.getMinY(), width, height));

        WritableImage wi = new WritableImage( width, height);
        
        croppedImageView.setImage(imageView.snapshot(parameters, wi));  

       // save image (without alpha)
       // --------------------------------
       //BufferedImage bufImageARGB = SwingFXUtils.fromFXImage(wi, null);
       //BufferedImage bufImageRGB = new BufferedImage(bufImageARGB.getWidth(), bufImageARGB.getHeight(), BufferedImage.TYPE_3BYTE_BGR);

      // Graphics2D graphics = bufImageRGB.createGraphics();
       //graphics.drawImage(bufImageARGB, 0, 0, null);

      // try {
      //    ImageIO.write(bufImageRGB, "jpg", file); 
      //    System.out.println( "Image saved to " + file.getAbsolutePath());
       //   } catch (IOException e) {
       //   e.printStackTrace();
      //    }
      //    graphics.dispose();
    }
	
}
