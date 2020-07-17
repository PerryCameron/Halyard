package com.ecsail.gui;

import com.ecsail.main.SqlSelect;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
// contents inside tab Welcome launched from Connect Database about line 229
public class BoxWelcome extends HBox {
	
	public BoxWelcome() {
		VBox vboxLeft = new VBox();
		VBox vboxRight = new VBox();
		
		int activeMembership = SqlSelect.getActiveMembershipCount();
		Text activeText = new Text("There are currently " + activeMembership + " active memberships");
		
		vboxLeft.getChildren().addAll(activeText);
		getChildren().addAll(vboxLeft,vboxRight);
		
	}

}
