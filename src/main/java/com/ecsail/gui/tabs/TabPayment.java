package com.ecsail.gui.tabs;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Function;

import com.ecsail.enums.PaymentType;
import com.ecsail.main.EditCell;
import com.ecsail.main.SqlExists;
import com.ecsail.main.SqlSelect;
import com.ecsail.structures.Object_Payment;
import javafx.geometry.Insets;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;

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
			payments.add(new Object_Payment(pay_id,money_id,"","CH",date, "0"));
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
		//vboxGrey.setId("slip-box");/
		//vboxGrey.setPrefHeight(688);
		paymentTableView = new TableView<Object_Payment>();
		paymentTableView.setItems(payments);
		paymentTableView.setPrefWidth(235);
		paymentTableView.setPrefHeight(140);
		paymentTableView.setFixedCellSize(30);
		paymentTableView.setEditable(true);
		
	
		TableColumn<Object_Payment, String> Col1 = createColumn("Amount", Object_Payment::PaymentAmountProperty);
		Col1.setPrefWidth(60);
		Col1.setStyle( "-fx-alignment: CENTER-RIGHT;");
        Col1.setOnEditCommit(
                new EventHandler<CellEditEvent<Object_Payment, String>>() {
                    @Override
                    public void handle(CellEditEvent<Object_Payment, String> t) {
                        ((Object_Payment) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                                ).setPaymentAmount(t.getNewValue());
                        //int pay_id = ((Object_Payment) t.getTableView().getItems().get(t.getTablePosition().getRow())).getPay_id();
                        //	SqlUpdate.updatePhone("phone", phone_id, t.getNewValue());
                    }
                }
            );
        
        
        // example for this column found at https://o7planning.org/en/11079/javafx-tableview-tutorial
        ObservableList<PaymentType> paymentTypeList = FXCollections.observableArrayList(PaymentType.values());
		TableColumn<Object_Payment, PaymentType> Col2 = new TableColumn<Object_Payment, PaymentType>("Type");
		
		Col2.setPrefWidth(55);
		Col2.setStyle( "-fx-alignment: CENTER;");
		Col2.setCellValueFactory(new Callback<CellDataFeatures<Object_Payment, PaymentType>, ObservableValue<PaymentType>>() {
        	 
            @Override
            public ObservableValue<PaymentType> call(CellDataFeatures<Object_Payment, PaymentType> param) {
            	Object_Payment thisPayment = param.getValue();
                String paymentCode = thisPayment.getPaymentType();
                PaymentType paymentType = PaymentType.getByCode(paymentCode);
                return new SimpleObjectProperty<PaymentType>(paymentType);
            }
        });
 
		Col2.setCellFactory(ComboBoxTableCell.forTableColumn(paymentTypeList));
 
		Col2.setOnEditCommit((CellEditEvent<Object_Payment, PaymentType> event) -> {
            TablePosition<Object_Payment, PaymentType> pos = event.getTablePosition();
            PaymentType newPaymentType = event.getNewValue();
            int row = pos.getRow();
            Object_Payment thisPayment = event.getTableView().getItems().get(row);
            //SqlUpdate.updatePhone("phone_type", thisPhone.getPhone_ID(), newPhoneType.getCode());
            thisPayment.setPaymentType(newPaymentType.getCode());
        });
		
		TableColumn<Object_Payment, String> Col3 = createColumn("Check #", Object_Payment::checkNumberProperty);
		Col3.setPrefWidth(55);
		Col3.setStyle( "-fx-alignment: CENTER-LEFT;");
        Col3.setOnEditCommit(
                new EventHandler<CellEditEvent<Object_Payment, String>>() {
                    @Override
                    public void handle(CellEditEvent<Object_Payment, String> t) {
                        ((Object_Payment) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                                ).setCheckNumber(t.getNewValue());
                        //int pay_id = ((Object_Payment) t.getTableView().getItems().get(t.getTablePosition().getRow())).getPay_id();
                        //	SqlUpdate.updatePhone("phone", phone_id, t.getNewValue());
                    }
                }
            );
        
		TableColumn<Object_Payment, String> Col4 = createColumn("Date", Object_Payment::paymentDateProperty);
		Col4.setPrefWidth(70);
		Col4.setStyle( "-fx-alignment: CENTER-LEFT;");
        Col4.setOnEditCommit(
                new EventHandler<CellEditEvent<Object_Payment, String>>() {
                    @Override
                    public void handle(CellEditEvent<Object_Payment, String> t) {
                        ((Object_Payment) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                                ).setPaymentDate(t.getNewValue());
                        //int pay_id = ((Object_Payment) t.getTableView().getItems().get(t.getTablePosition().getRow())).getPay_id();
                        //	SqlUpdate.updatePhone("phone", phone_id, t.getNewValue());
                    }
                }
            );
         
		
        paymentTableView.getColumns().addAll(Col1,Col2,Col3,Col4);
		vboxGrey.getChildren().add(paymentTableView);
		vboxBlue.getChildren().add(vboxPink);
		vboxPink.getChildren().add(vboxGrey);
		setContent(vboxBlue);
		
	}
	
	////////////////////////  CLASS METHODS //////////////////////////
    private <T> TableColumn<T, String> createColumn(String title, Function<T, StringProperty> property) {
        TableColumn<T, String> col = new TableColumn<>(title);
        col.setCellValueFactory(cellData -> property.apply(cellData.getValue()));
        col.setCellFactory(column -> EditCell.createStringEditCell());
        return col ;
    }
}
