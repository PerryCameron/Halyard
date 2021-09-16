package com.ecsail.gui.boxes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.function.Function;
import com.ecsail.enums.MembershipType;
import com.ecsail.main.EditCell;
import com.ecsail.main.FixInput;
import com.ecsail.main.Paths;
import com.ecsail.sql.SqlDelete;
import com.ecsail.sql.SqlExists;
import com.ecsail.sql.SqlInsert;
import com.ecsail.sql.SqlSelect;
import com.ecsail.sql.SqlUpdate;
import com.ecsail.structures.Object_MemLabels;
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
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class BoxHistory extends HBox {
	Object_MembershipList membership;
	private Object_MemLabels labels;
	private TableView<Object_MembershipId> idTableView;
	private ObservableList<Object_MembershipId> id;
    LocalDate date;
    
	public BoxHistory(Object_MembershipList m, Object_MemLabels l) {
		this.membership = m;
		this.idTableView = new TableView<Object_MembershipId>();
		this.id = FXCollections.observableArrayList(new Callback<Object_MembershipId, Observable[]>() {
			@Override
			public Observable[] call(Object_MembershipId param) {
				return new Observable[] { param.isRenewProperty() };
			}
		});
		this.id.addAll(SqlSelect.getIds(m.getMsid()));
		this.labels = l;
		/////// OBJECT INSTANCE //////
		
		Button idAdd = new Button("Add");
		Button idDelete = new Button("Delete");
		HBox hboxControls = new HBox(); // holds phone buttons
		VBox vboxGrey = new VBox(); // this is here for the grey background to make nice apperence
		VBox vboxPink = new VBox(); // this creates a pink border around the table
		HBox hboxJoinDate = new HBox();
		HBox hboxButtons = new HBox();
		DatePicker joinDatePicker = new DatePicker();
		
		//// OBJECT ATTRIBUTES /////
		hboxJoinDate.setAlignment(Pos.CENTER_LEFT);
		
		hboxJoinDate.setSpacing(5);
		hboxButtons.setSpacing(5);
		vboxGrey.setSpacing(10); // spacing in between table and buttons
		hboxControls.setSpacing(60); // spacing between buttons and joinDatePicker
		
		idAdd.setPrefWidth(60);
		idDelete.setPrefWidth(60);
		//vboxGrey.setPrefWidth(460);
		
		vboxGrey.setPadding(new Insets(5, 5, 5, 5)); // spacing around table and buttons
		vboxPink.setPadding(new Insets(2, 2, 2, 2)); // spacing to make pink fram around table
		setPadding(new Insets(5, 5, 5, 5));  // creates space for blue frame
		
		vboxGrey.setId("box-grey");
		vboxPink.setId("box-pink");
		this.setId("box-blue");
		
		VBox.setVgrow(idTableView, Priority.ALWAYS);
		HBox.setHgrow(idTableView, Priority.ALWAYS);
		VBox.setVgrow(vboxPink, Priority.ALWAYS);
		HBox.setHgrow(this, Priority.ALWAYS);
		HBox.setHgrow(vboxGrey, Priority.ALWAYS);
		
		
		Collections.sort(id, Comparator.comparing(Object_MembershipId::getFiscal_Year).reversed());

		
		idTableView.setItems(id);
		idTableView.setFixedCellSize(30);
		idTableView.setEditable(true);
		//idTableView.minHeightProperty().bind(vboxGrey.prefHeightProperty());
		//idTableView.maxHeightProperty().bind(vboxGrey.prefHeightProperty());
		idTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY );
		
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if(membership.getJoinDate() != null) {
        date = LocalDate.parse(membership.getJoinDate(), formatter);
        } else {
        date = LocalDate.parse("1900-01-01", formatter);
        }
        joinDatePicker.setValue(date);

		// example for this column found at
		// https://gist.github.com/james-d/be5bbd6255a4640a5357#file-editcell-java-L109
		TableColumn<Object_MembershipId, String> Col1 = createColumn("Year", Object_MembershipId::fiscal_YearProperty);
		Col1.setOnEditCommit(new EventHandler<CellEditEvent<Object_MembershipId, String>>() {
			@Override
			public void handle(CellEditEvent<Object_MembershipId, String> t) {
				((Object_MembershipId) t.getTableView().getItems().get(t.getTablePosition().getRow()))
						.setFiscal_Year(t.getNewValue());
				Object_MembershipId thisId = ((Object_MembershipId) t.getTableView().getItems().get(t.getTablePosition().getRow()));
				int mid = thisId.getMid();
				if(!SqlUpdate.updateMembershipId(thisId, "fiscal_year", FixInput.changeEmptyStringToZero(t.getNewValue()))) {
					// if it does not update correctly lets set tableview back to defaults
					Object_MembershipId storedId = SqlSelect.getMembershipIdObject(mid);
					System.out.println("fiscal year=" + storedId.getFiscal_Year() + " memId=" + storedId.getMembership_id());
					thisId.setFiscal_Year(storedId.getFiscal_Year());
					thisId.setMembership_id(storedId.getMembership_id());
				}

			}
		});

		TableColumn<Object_MembershipId, String> Col2 = createColumn("Mem ID",
				Object_MembershipId::membership_idProperty);
		Col2.setOnEditCommit(new EventHandler<CellEditEvent<Object_MembershipId, String>>() {
			@Override
			public void handle(CellEditEvent<Object_MembershipId, String> t) {
				((Object_MembershipId) t.getTableView().getItems().get(t.getTablePosition().getRow()))
						.setMembership_id(t.getNewValue());
				Object_MembershipId thisId = ((Object_MembershipId) t.getTableView().getItems().get(t.getTablePosition().getRow()));
				int mid = thisId.getMid();
				if(!SqlUpdate.updateMembershipId(thisId, "membership_id", FixInput.changeEmptyStringToZero(t.getNewValue()))) {
					// if it does not update correctly lets set tableview back to defaults
					Object_MembershipId storedId = SqlSelect.getMembershipIdObject(mid);
					System.out.println("fiscal year=" + storedId.getFiscal_Year() + " memId=" + storedId.getMembership_id());
					thisId.setFiscal_Year(storedId.getFiscal_Year());
					thisId.setMembership_id(storedId.getMembership_id());
				}
			}
		});

		// example for this column found at
		// https://o7planning.org/en/11079/javafx-tableview-tutorial
		ObservableList<MembershipType> MembershipTypeList = FXCollections.observableArrayList(MembershipType.values());
		TableColumn<Object_MembershipId, MembershipType> Col3 = new TableColumn<Object_MembershipId, MembershipType>(
				"Mem Type");
		Col3.setCellValueFactory(
				new Callback<CellDataFeatures<Object_MembershipId, MembershipType>, ObservableValue<MembershipType>>() {

					@Override
					public ObservableValue<MembershipType> call(
							CellDataFeatures<Object_MembershipId, MembershipType> param) {
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
			SqlUpdate.updateMembershipId(thisId, "mem_type", newMembershipType.getCode());
			thisId.setMem_type(newMembershipType.getCode());
		});

		// example for this column found at
		// https://o7planning.org/en/11079/javafx-tableview-tutorial
		TableColumn<Object_MembershipId, Boolean> Col4 = new TableColumn<Object_MembershipId, Boolean>("Renewed");
		Col4.setCellValueFactory(
				new Callback<CellDataFeatures<Object_MembershipId, Boolean>, ObservableValue<Boolean>>() {
					@Override
					public ObservableValue<Boolean> call(CellDataFeatures<Object_MembershipId, Boolean> param) {
						Object_MembershipId id = param.getValue();
						SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(id.isRenew());
						// Note: singleCol.setOnEditCommit(): Not work for
						// CheckBoxTableCell.
						// When "isListed?" column change.
						booleanProp.addListener(new ChangeListener<Boolean>() {
							@Override
							public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
									Boolean newValue) {
								id.setIsRenew(newValue);
								// SqlUpdate.updateListed("phone_listed",phone.getPhone_ID(), newValue);
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
		
		TableColumn<Object_MembershipId, Boolean> Col5 = new TableColumn<Object_MembershipId, Boolean>("Renew Late");
		Col5.setCellValueFactory(
				new Callback<CellDataFeatures<Object_MembershipId, Boolean>, ObservableValue<Boolean>>() {
					@Override
					public ObservableValue<Boolean> call(CellDataFeatures<Object_MembershipId, Boolean> param) {
						Object_MembershipId id = param.getValue();
						SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(id.isLateRenew());
						// Note: singleCol.setOnEditCommit(): Not work for
						// CheckBoxTableCell.
						// When "isListed?" column change.
						booleanProp.addListener(new ChangeListener<Boolean>() {
							@Override
							public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
									Boolean newValue) {
								id.setIsLateRenew(newValue);

								SqlUpdate.updateMembershipId(id.getMid(), "LATE_RENEW", newValue);
							}
						});
						return booleanProp;
					}
				});
 
		Col5.setCellFactory(new Callback<TableColumn<Object_MembershipId, Boolean>, //
				TableCell<Object_MembershipId, Boolean>>() {
			@Override
			public TableCell<Object_MembershipId, Boolean> call(TableColumn<Object_MembershipId, Boolean> p) {
				CheckBoxTableCell<Object_MembershipId, Boolean> cell = new CheckBoxTableCell<Object_MembershipId, Boolean>();
				cell.setAlignment(Pos.CENTER);
				return cell;
			}
		});
		
		/// sets width of columns by percentage
		Col1.setMaxWidth( 1f * Integer.MAX_VALUE * 25 );   // Year
		Col2.setMaxWidth( 1f * Integer.MAX_VALUE * 15 );  // Mem Id
		Col3.setMaxWidth( 1f * Integer.MAX_VALUE * 30 );   // Mem Type
		Col4.setMaxWidth( 1f * Integer.MAX_VALUE * 15 );   // Renewed
		Col5.setMaxWidth( 1f * Integer.MAX_VALUE * 15 );   // Renew Late

		/////////////////// LISTENERS //////////////////////////////

		joinDatePicker.setOnAction((event -> {
			LocalDate date = joinDatePicker.getValue();
			SqlUpdate.updateMembership(membership.getMsid(), "JOIN_DATE", date);
			membership.setJoinDate(joinDatePicker.getValue().toString());
			labels.getJoinDate().setText(joinDatePicker.getValue().toString());
		}));
		
		idAdd.setOnAction((event) -> {
			Object_MembershipId newIdTuple = null;
			int mid = SqlSelect.getCount("membership_id", "mid") + 1; // get last mid number add 1
			// make sure any blank unused rows are removed before creating another
			// no need to specify a year, just remove any of them.
			if(SqlExists.membershipIdBlankRowExists()) {
				System.out.println("Found unused blank row: Deleted");
				SqlDelete.deleteBlankMembershipIdRow();	
			}

			if (!tupleExistsInTableView()) {  // prevent creating duplicate new tuples
				if (SqlExists.memberShipIdExists(m.getMsid())) {
					newIdTuple = new Object_MembershipId(mid, 0 + "", m.getMsid(), "0", true, m.getMemType(), false,
							false);
				} else {
					newIdTuple = new Object_MembershipId(mid, Paths.getYear(), m.getMsid(), "0", true, m.getMemType(),
							false, false);
				}
			SqlInsert.addMembershipId(newIdTuple);
			System.out.println("Added new row for MS_ID " + newIdTuple.getMs_id());
			id.add(newIdTuple);	
			} else { System.out.println("Duplicate new tuple: ignoring"); }
		});

		idDelete.setOnAction((event) -> {
			int selectedIndex = idTableView.getSelectionModel().getSelectedIndex();
			if (selectedIndex >= 0)
				if (SqlDelete.deleteMembershipId(id.get(selectedIndex))) // if it is properly deleted in our database
					idTableView.getItems().remove(selectedIndex); // remove it from our GUI
		});

		/////////////////// SET CONTENT ///////////////////////

		hboxJoinDate.getChildren().addAll(new Label("Join Date"), joinDatePicker);
		hboxButtons.getChildren().addAll(idAdd, idDelete);
		idTableView.getColumns().addAll(Arrays.asList(Col1, Col2, Col3, Col4, Col5));
		vboxPink.getChildren().add(idTableView); // adds pink border around table
		hboxControls.getChildren().addAll(hboxJoinDate, hboxButtons); // lines buttons up vertically
		vboxGrey.getChildren().addAll(hboxControls,vboxPink);
		getChildren().add(vboxGrey);

	} // end of constructor

	private boolean tupleExistsInTableView() {
		boolean tupleExists = false;
		for(Object_MembershipId i: id) {
			if(i.getFiscal_Year().equals("0") && i.getMembership_id().equals("0")) tupleExists=true;
		}
		return tupleExists;
	}

	//////////////////////// CLASS METHODS //////////////////////////
	private <T> TableColumn<T, String> createColumn(String title, Function<T, StringProperty> property) {
		TableColumn<T, String> col = new TableColumn<>(title);
		col.setCellValueFactory(cellData -> property.apply(cellData.getValue()));
		col.setCellFactory(column -> EditCell.createStringEditCell());
		return col;
	}
}
