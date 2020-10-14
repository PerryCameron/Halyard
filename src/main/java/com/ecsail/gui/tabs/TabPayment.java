package com.ecsail.gui.tabs;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.ecsail.main.SqlExists;
import com.ecsail.main.SqlSelect;
import com.ecsail.structures.Object_Payment;

import javafx.geometry.Insets;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TabPayment extends Tab {
	private TableView<Object_Payment> paymentTableView;
	private ObservableList<Object_Payment> payments;
	String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
	public TabPayment(String text, int money_id) {
		super(text);
		this.payments = FXCollections.observableArrayList();
		if(SqlExists.paymentExists(money_id)) {
			System.out.println("loading up existing payments");
			// pull up payments from database
		} else {
			System.out.println("Creating a new entry");
			int pay_id = SqlSelect.getNumberOfPayments() + 1;
			payments.add(new Object_Payment(pay_id,money_id,0,"CH",date, 0));
			//payments.add(new Object_Payment(0,0,0,"CH",date, 0));
			// create an entry for our observable list
			System.out.println(payments.get(0).toString());
		}
		VBox vboxGrey = new VBox();  // this is the vbox for organizing all the widgets
		VBox vboxBlue = new VBox();
		VBox vboxPink = new VBox(); // this creates a pink border around the table
		
		vboxBlue.setId("box-blue");
		vboxBlue.setPadding(new Insets(5,5,5,5));
		vboxPink.setPadding(new Insets(3,3,3,3)); // spacing to make pink from around table
		vboxPink.setId("box-pink");
		//vboxGrey.setId("slip-box");
		//vboxGrey.setPrefHeight(688);
		
		vboxGrey.getChildren().add(new Label("Stubbed in Tab"));
		vboxBlue.getChildren().add(vboxPink);
		vboxPink.getChildren().add(vboxGrey);
		setContent(vboxBlue);
		
	}
}
