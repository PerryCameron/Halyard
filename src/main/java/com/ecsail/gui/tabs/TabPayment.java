package com.ecsail.gui.tabs;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.function.Function;

import com.ecsail.enums.PaymentType;
import com.ecsail.main.EditCell;
import com.ecsail.main.SqlDelete;
import com.ecsail.main.SqlExists;
import com.ecsail.main.SqlInsert;
import com.ecsail.main.SqlSelect;
import com.ecsail.main.SqlUpdate;
import com.ecsail.structures.Object_BalanceText;
import com.ecsail.structures.Object_Money;
import com.ecsail.structures.Object_Payment;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class TabPayment extends Tab {
	private TableView<Object_Payment> paymentTableView;
	private ObservableList<Object_Payment> payments;
	private Object_Money fiscalRecord;
	private Object_BalanceText balanceTextField;
	String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));

	public TabPayment(String text, Object_Money m, Object_BalanceText o) {
		super(text);
		this.fiscalRecord = m;
		this.balanceTextField = o;
		System.out.println("Started Payment tab with money_id=" + fiscalRecord.getMoney_id());
		if(SqlExists.paymentExists(fiscalRecord.getMoney_id())) {
			this.payments = SqlSelect.getPayments(fiscalRecord.getMoney_id());
			System.out.println("A record for money_id=" + fiscalRecord.getMoney_id() + " exists. Opening Payment");
			// pull up payments from database
		} else {
			this.payments = FXCollections.observableArrayList();
			System.out.println("Creating a new entry");
			int pay_id = SqlSelect.getNumberOfPayments() + 1;
			payments.add(new Object_Payment(pay_id,fiscalRecord.getMoney_id(),"0","CH",date, "0",1));
			SqlInsert.addRecord(payments.get(payments.size() - 1));
			System.out.println(payments.get(0).toString());
		}
		VBox vboxGrey = new VBox();  // this is the vbox for organizing all the widgets
		VBox vboxBlue = new VBox();
		VBox vboxPink = new VBox(); // this creates a pink border around the table
		HBox hboxButton = new HBox();
		Button paymentAdd = new Button("Add");
		Button paymentDelete = new Button("Delete");
		paymentTableView = new TableView<Object_Payment>();
		
		////////////////////// ATTRIBUTES ////////////////////////////
		 
		vboxBlue.setId("box-blue");
		vboxBlue.setPadding(new Insets(5,5,5,5));
		vboxPink.setPadding(new Insets(3,3,7,3)); // spacing to make pink from around table
		hboxButton.setPadding(new Insets(0,0,0,130));
		vboxPink.setId("box-pink");
		hboxButton.setSpacing(5);
		vboxGrey.setSpacing(5);
		
		paymentTableView.setItems(payments);
		paymentTableView.setPrefWidth(225);
		paymentTableView.setPrefHeight(115);
		paymentTableView.setFixedCellSize(30);
		
		if(fiscalRecord.isCommitted()) {
		paymentTableView.setEditable(false);
		paymentAdd.setDisable(true);
		paymentDelete.setDisable(true);
		} else {
		paymentTableView.setEditable(true);
		paymentAdd.setDisable(false);
		paymentDelete.setDisable(false);
		}
	
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
                        int pay_id = ((Object_Payment) t.getTableView().getItems().get(t.getTablePosition().getRow())).getPay_id();
                        SqlUpdate.updatePayment(pay_id, "amount", t.getNewValue());
                        int totalAmount = SqlSelect.getTotalAmount(fiscalRecord.getMoney_id());
                        System.out.println("Total Amount=" + totalAmount);
                        // used balanceTextfields.getPaid() to write to.
                        balanceTextField.getPaidText().setText(totalAmount + "");
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
            SqlUpdate.updatePayment(thisPayment.getPay_id(), "payment_type", newPaymentType.getCode());
            // need to update paid from here
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
                        int pay_id = ((Object_Payment) t.getTableView().getItems().get(t.getTablePosition().getRow())).getPay_id();
                        SqlUpdate.updatePayment(pay_id, "CHECKNUMBER", t.getNewValue());
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
                        int pay_id = ((Object_Payment) t.getTableView().getItems().get(t.getTablePosition().getRow())).getPay_id();
                        SqlUpdate.updatePayment(pay_id, "payment_date", t.getNewValue());
                        //	SqlUpdate.updatePhone("phone", phone_id, t.getNewValue());
                    }
                }
            );
		/////////////////// LISTENERS //////////////////////////////
		
		paymentAdd.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				int pay_id = SqlSelect.getNumberOfPayments() + 1; // get last pay_id number
				//if (SqlInsert.addRecord(phone_id, person.getP_id(), true, "new phone", "")) // if added with no errors
				payments.add(new Object_Payment(pay_id,fiscalRecord.getMoney_id(),null,"CH",date, "0",1)); // lets add it to our GUI
				SqlInsert.addRecord(payments.get(payments.size() -1));
				//System.out.println("Added new record with pay_id=" + pay_id + " money_id=" + fiscalRecord.getMoney_id());
				}
			});
        
        paymentDelete.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	int selectedIndex = paymentTableView.getSelectionModel().getSelectedIndex();
            		if(selectedIndex >= 0)
            	//		if(SqlDelete.deletePhone(phone.get(selectedIndex)))  // if it is properly deleted in our database		
            	SqlDelete.deletePayment(payments.get(selectedIndex));		
            	paymentTableView.getItems().remove(selectedIndex); // remove it from our GUI
                int totalAmount = SqlSelect.getTotalAmount(fiscalRecord.getMoney_id());
                balanceTextField.getPaidText().setText(totalAmount + "");
            }
        }); 
        
        fiscalRecord.committedProperty().addListener((obs, notCommited, isCommited) -> {
        	if(isCommited) {
        		paymentTableView.setEditable(false);
        		paymentAdd.setDisable(true);
        		paymentDelete.setDisable(true);
        	}
        	if(notCommited) {
        		paymentTableView.setEditable(true);
        		paymentAdd.setDisable(false);
        		paymentDelete.setDisable(false);
        	}
        	//System.out.println("obs=" + obs + " notCommited=" + notCommited + " isCommited=" + isCommited);
        } );
        
        /////////////////// SET CONTENT //////////////////////////////
        
        
		hboxButton.getChildren().addAll(paymentAdd,paymentDelete);
        paymentTableView.getColumns().addAll(Arrays.asList(Col1,Col2,Col3,Col4));
		vboxGrey.getChildren().addAll(paymentTableView,hboxButton);
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
