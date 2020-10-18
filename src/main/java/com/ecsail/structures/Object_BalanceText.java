package com.ecsail.structures;

import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class Object_BalanceText {
	private TextField paidText;
	private TextField totalFeesText;
	private TextField creditText;
	private TextField balanceText;
	private Button commitButton;
	
	public Object_BalanceText(TextField paidText, TextField totalFeesText, TextField creditText,
			TextField balanceText, Button commitButton) {
		this.paidText = paidText;
		this.totalFeesText = totalFeesText;
		this.creditText = creditText;
		this.balanceText = balanceText;
		this.commitButton = commitButton;
	}

	public Object_BalanceText() {
		this.paidText = new TextField();
		this.totalFeesText = new TextField();
		this.creditText = new TextField();
		this.balanceText = new TextField();
		this.commitButton = new Button("Commit");
	}


	public TextField getPaidText() {
		return paidText;
	}

	public TextField getTotalFeesText() {
		return totalFeesText;
	}

	public TextField getCreditText() {
		return creditText;
	}

	public TextField getBalanceText() {
		return balanceText;
	}

	public Button getCommitButton() {
		return commitButton;
	}	
}
