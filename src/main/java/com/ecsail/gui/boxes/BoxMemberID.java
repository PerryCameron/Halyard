package com.ecsail.gui.boxes;

import java.util.Arrays;
import java.util.function.Function;

import com.ecsail.enums.MembershipType;
import com.ecsail.main.EditCell;
import com.ecsail.main.Paths;
import com.ecsail.main.SqlDelete;
import com.ecsail.main.SqlExists;
import com.ecsail.main.SqlInsert;
import com.ecsail.main.SqlSelect;
import com.ecsail.main.SqlUpdate;
import com.ecsail.structures.Object_MembershipId;
import com.ecsail.structures.Object_MembershipList;

import javafx.beans.Observable;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class BoxMemberID extends HBox {
	
	private TableView<Object_MembershipId> idTableView;
	private ObservableList<Object_MembershipId> id;
	
	public BoxMemberID(Object_MembershipList m) {
		
		this.id = FXCollections.observableArrayList(new Callback<Object_MembershipId, Observable[]>() {
			@Override
			public Observable[] call(Object_MembershipId param) {
				return new Observable[] { param.isRenewProperty() };
			}
		});
		this.id.addAll(SqlSelect.getIds(m.getMsid()));
		/////// OBJECT INSTANCE //////	
		VBox vbox1 = new VBox(); // holds phone buttons
		Button idAdd = new Button("Add");
		Button idDelete = new Button("Delete");
		HBox hboxGrey = new HBox(); // this is here for the grey background to make nice apperence
		VBox vboxPink = new VBox(); // this creates a pink border around the table

		//// OBJECT ATTRIBUTES /////
		idAdd.setPrefWidth(60);
		idDelete.setPrefWidth(60);
		vbox1.setSpacing(5); // spacing between buttons
		hboxGrey.setPrefWidth(480);
		hboxGrey.setSpacing(10);  // spacing in between table and buttons
		hboxGrey.setId("box-grey");
		vboxPink.setId("box-pink");
		hboxGrey.setPadding(new Insets(5,5,5,5));  // spacing around table and buttons
		vboxPink.setPadding(new Insets(2,2,2,2)); // spacing to make pink fram around table

		///// TABLEVIE INSTANCE CREATION AND ATTRIBUTES /////
		idTableView = new TableView<Object_MembershipId>();
		idTableView.setItems(id);
		idTableView.setPrefWidth(352);
		idTableView.setPrefHeight(140);
		idTableView.setFixedCellSize(30);
		idTableView.setEditable(true);
			
		// example for this column found at https://gist.github.com/james-d/be5bbd6255a4640a5357#file-editcell-java-L109
		TableColumn<Object_MembershipId, String> Col1 = createColumn("Year", Object_MembershipId::fiscal_YearProperty);
		Col1.setPrefWidth(70);
        Col1.setOnEditCommit(
                new EventHandler<CellEditEvent<Object_MembershipId, String>>() {
                    @Override
                    public void handle(CellEditEvent<Object_MembershipId, String> t) {
                        ((Object_MembershipId) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                                ).setFiscal_Year(t.getNewValue());
                        int mid = ((Object_MembershipId) t.getTableView().getItems().get(t.getTablePosition().getRow())).getMid();
                       // 	SqlUpdate.updatePhone("phone", phone_id, t.getNewValue());
                        SqlUpdate.updateMembershipId(mid, "fiscal_year", t.getNewValue());
                    }
                }
            );
        
		TableColumn<Object_MembershipId, String> Col2 = createColumn("Membership ID", Object_MembershipId::membership_idProperty);
		Col2.setPrefWidth(100);
        Col2.setOnEditCommit(
                new EventHandler<CellEditEvent<Object_MembershipId, String>>() {
                    @Override
                    public void handle(CellEditEvent<Object_MembershipId, String> t) {
                        ((Object_MembershipId) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                                ).setMembership_id(t.getNewValue());
                        int mid = ((Object_MembershipId) t.getTableView().getItems().get(t.getTablePosition().getRow())).getMid();
                        SqlUpdate.updateMembershipId(mid, "membership_id", t.getNewValue());
                    }
                }
            );
        
     // example for this column found at https://o7planning.org/en/11079/javafx-tableview-tutorial
        ObservableList<MembershipType> MembershipTypeList = FXCollections.observableArrayList(MembershipType.values());
		TableColumn<Object_MembershipId, MembershipType> Col3 = new TableColumn<Object_MembershipId, MembershipType>("Mem Type");
		Col3.setPrefWidth(90);
		Col3.setCellValueFactory(new Callback<CellDataFeatures<Object_MembershipId, MembershipType>, ObservableValue<MembershipType>>() {
        	 
            @Override
            public ObservableValue<MembershipType> call(CellDataFeatures<Object_MembershipId, MembershipType> param) {
            	Object_MembershipId thisId = param.getValue();
                String membershipCode = thisId.getMem_type();
                /// careful with capitals
                MembershipType membershipType = MembershipType.getByCode(membershipCode);
                return new SimpleObjectProperty<MembershipType>(membershipType);
            }
        });
        
        Col3.setCellFactory(ComboBoxTableCell.forTableColumn(MembershipTypeList));
        
		Col3.setOnEditCommit((CellEditEvent<Object_MembershipId, MembershipType> event) -> {
            TablePosition<Object_MembershipId, MembershipType> pos = event.getTablePosition();
            MembershipType newMembershipType = event.getNewValue();
            int row = pos.getRow();
            Object_MembershipId thisId = event.getTableView().getItems().get(row);
            SqlUpdate.updateMembershipId(thisId.getMid(), "mem_type", newMembershipType.getCode());
            thisId.setMem_type(newMembershipType.getCode());
        });
        
     // example for this column found at https://o7planning.org/en/11079/javafx-tableview-tutorial
		TableColumn<Object_MembershipId, Boolean> Col4 = new TableColumn<Object_MembershipId, Boolean>("Renewed");
		Col4.setPrefWidth(90);
		Col4.setCellValueFactory(new Callback<CellDataFeatures<Object_MembershipId, Boolean>, ObservableValue<Boolean>>() {
	            @Override
	            public ObservableValue<Boolean> call(CellDataFeatures<Object_MembershipId, Boolean> param) {
	            	Object_MembershipId id = param.getValue();
	                SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(id.isIsRenew());
	                // Note: singleCol.setOnEditCommit(): Not work for
	                // CheckBoxTableCell.
	                // When "isListed?" column change.
	                booleanProp.addListener(new ChangeListener<Boolean>() {
	                    @Override
	                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
	                            Boolean newValue) {
	                        id.setIsRenew(newValue);
	                        //SqlUpdate.updateListed("phone_listed",phone.getPhone_ID(), newValue);
	                        SqlUpdate.updateMembershipId(id.getMid(), "RENEW", newValue);
	                    }
	                });
	                return booleanProp;
	            }
	        });
	 
		Col4.setCellFactory(new Callback<TableColumn<Object_MembershipId, Boolean>, //
	        TableCell<Object_MembershipId, Boolean>>() {
	            @Override
	            public TableCell<Object_MembershipId, Boolean> call(TableColumn<Object_MembershipId, Boolean> p) {
	                CheckBoxTableCell<Object_MembershipId, Boolean> cell = new CheckBoxTableCell<Object_MembershipId, Boolean>();
	                cell.setAlignment(Pos.CENTER);
	                return cell;
	            }
	        });
		

		
		/////////////////// LISTENERS //////////////////////////////
		
				idAdd.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent e) {
						int mid = SqlSelect.getCount("membership_id", "mid") + 1; // get last mid number add 1
						Object_MembershipId newIdTuple = null;
						if (SqlExists.memberShipIdExists(m.getMsid())) {
							//Object_MembershipId thisId = SqlSelect.getCount(m.getMsid()); // retrieves oldest year																// record for member
							//System.out.println("mem id is" + thisId.getMembership_id());
							//int fiscalYear = Integer.parseInt(Main.selectedYear); // gets year and subtracts
							newIdTuple = new Object_MembershipId(mid, 0 + "", m.getMsid(),
									"0",true,m.getMemType(),false);
						} else {
							newIdTuple = new Object_MembershipId(mid, Paths.getYear(), m.getMsid(),"0",true,m.getMemType(),false);
						}
						SqlInsert.addMembershipId(newIdTuple);
						id.add(newIdTuple);
					}
				});
        
        idDelete.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	int selectedIndex = idTableView.getSelectionModel().getSelectedIndex();
            		if(selectedIndex >= 0)
            			if(SqlDelete.deleteMembershipId(id.get(selectedIndex)))  // if it is properly deleted in our database
            				idTableView.getItems().remove(selectedIndex); // remove it from our GUI
            }
        });
		
		///////////////////  SET CONTENT  ///////////////////////
		
		idTableView.getColumns().addAll(Arrays.asList(Col1,Col2,Col3,Col4));
		vboxPink.getChildren().add(idTableView);  // adds pink border around table
		vbox1.getChildren().addAll(idAdd, idDelete); // lines buttons up vertically
		hboxGrey.getChildren().addAll(vboxPink,vbox1);
		getChildren().add(hboxGrey);
		
	}  // end of constructor
	
	////////////////////////  CLASS METHODS //////////////////////////
    private <T> TableColumn<T, String> createColumn(String title, Function<T, StringProperty> property) {
        TableColumn<T, String> col = new TableColumn<>(title);
        col.setCellValueFactory(cellData -> property.apply(cellData.getValue()));
        col.setCellFactory(column -> EditCell.createStringEditCell());
        return col ;
    }
} 
