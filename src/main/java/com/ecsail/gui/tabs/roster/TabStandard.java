package com.ecsail.gui.tabs.roster;

import com.ecsail.structures.RosterRadioButtonsDTO;

import javafx.geometry.Insets;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TabStandard extends Tab {
	
	public TabStandard(RosterRadioButtonsDTO rb) {
		HBox hboxFrame = new HBox();
		HBox hboxMain = new HBox();
		VBox vboxRadioButton1 = new VBox();
		VBox vboxRadioButton2 = new VBox();
		Image mainIcon = new Image(getClass().getResourceAsStream("/icons/baseline_change_history_black_18dp.png"));
		ImageView imageView = new ImageView();
		////////////////// ATTRIBUTES ////////////////////////
		hboxFrame.setId("box-blue");
		hboxFrame.setPadding(new Insets(2,2,2,5));
		imageView.setImage(mainIcon);
		this.setGraphic(imageView);
		hboxMain.setId("box-pink");
		vboxRadioButton1.setPrefWidth(130);
		vboxRadioButton2.setPrefWidth(164);
		vboxRadioButton1.setSpacing(5);
		vboxRadioButton2.setSpacing(5);
		vboxRadioButton1.setPadding(new Insets(8,5,5,5));
		vboxRadioButton2.setPadding(new Insets(8,5,5,5));
		setId("rostertab-pane");
		//getStyleClass().add("roster-tab-pane");
        ////////////////////SET CONTENT //////////////////////
		
		vboxRadioButton1.getChildren().addAll(rb.getRadioAll(),rb.getRadioActive(),rb.getRadioNonRenew());
		vboxRadioButton2.getChildren().addAll(rb.getRadioNewMembers(),rb.getRadioReturnMembers(), rb.getRadioLatePaymentMembers());
		
		hboxMain.getChildren().addAll(vboxRadioButton1,vboxRadioButton2);
		hboxFrame.getChildren().add(hboxMain);
		setContent(hboxFrame);
	}

}
