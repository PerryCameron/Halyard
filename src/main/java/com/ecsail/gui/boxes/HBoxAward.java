package com.ecsail.gui.boxes;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.function.Function;

import com.ecsail.enums.Awards;
import com.ecsail.main.EditCell;
import com.ecsail.sql.SqlDelete;
import com.ecsail.sql.SqlInsert;
import com.ecsail.sql.select.SqlAward;
import com.ecsail.sql.select.SqlSelect;
import com.ecsail.sql.SqlUpdate;
import com.ecsail.structures.Object_Award;
import com.ecsail.structures.Object_Person;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TablePosition;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class HBoxAward extends HBox {

	private Object_Person person;
	private ObservableList<Object_Award> award;
	private TableView<Object_Award> awardTableView;
	private String currentYear;
	
	@SuppressWarnings("unchecked")
	public HBoxAward(Object_Person p) {
		this.person = p;
		this.currentYear = new SimpleDateFormat("yyyy").format(new Date());
		this.award =  SqlAward.getAwards(person);
		
		///////////////// OBJECT INSTANCE ///////////////////
		Button awardAdd = new Button("Add");
		Button awardDelete = new Button("Delete");
		VBox vboxButtons = new VBox(); // holds officer buttons
		HBox hboxGrey = new HBox(); // this is here for the grey background to make nice apperence
		VBox vboxPink = new VBox(); // this creates a pink border around the table
		awardTableView = new TableView<Object_Award>();

		/////////////////  ATTRIBUTES  /////////////////////
		awardAdd.setPrefWidth(60);
		awardDelete.setPrefWidth(60);
		vboxButtons.setPrefWidth(80);
		
		HBox.setHgrow(hboxGrey, Priority.ALWAYS);
		HBox.setHgrow(vboxPink, Priority.ALWAYS);
		HBox.setHgrow(awardTableView, Priority.ALWAYS);
		VBox.setVgrow(awardTableView, Priority.ALWAYS);
		
		hboxGrey.setSpacing(10);  // spacing in between table and buttons
		vboxButtons.setSpacing(5);
		
		hboxGrey.setId("box-grey");
		vboxPink.setId("box-pink");
		this.setId("box-blue");
		
		hboxGrey.setPadding(new Insets(5,5,5,5));  // spacing around table and buttons
		vboxPink.setPadding(new Insets(2,2,2,2)); // spacing to make pink fram around table
		
		///////////////// TABLE VIEW ///////////////////////

			awardTableView.setItems(award);
			awardTableView.setFixedCellSize(30);
			awardTableView.setEditable(true);
			awardTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY );
			
			
			TableColumn<Object_Award, String> Col1 = createColumn("Year", Object_Award::awardYearProperty);
			Col1.setSortType(TableColumn.SortType.DESCENDING);
	        Col1.setOnEditCommit(
	                new EventHandler<CellEditEvent<Object_Award, String>>() {
	                    @Override
	                    public void handle(CellEditEvent<Object_Award, String> t) {
	                        ((Object_Award) t.getTableView().getItems().get(
	                                t.getTablePosition().getRow())
	                                ).setAwardYear(t.getNewValue());
	                        SqlUpdate.updateAward("award_year",t.getRowValue().getAwardId(), t.getNewValue());  // have to get by money id and pid eventually
	                    }
	                }
	            );
	        
	        
	        ObservableList<Awards> awardsList = FXCollections.observableArrayList(Awards.values());
	    	final TableColumn<Object_Award, Awards> Col2 = new TableColumn<Object_Award, Awards>("Award Type");
	        Col2.setCellValueFactory(new Callback<CellDataFeatures<Object_Award, Awards>, ObservableValue<Awards>>() {
	        	 
	            @Override
	            public ObservableValue<Awards> call(CellDataFeatures<Object_Award, Awards> param) {
	                Object_Award thisAward = param.getValue();
	                String awardCode = thisAward.getAwardType();
	                Awards type = Awards.getByCode(awardCode);
	                return new SimpleObjectProperty<Awards>(type);
	            }
	        });
	        
	        Col2.setCellFactory(ComboBoxTableCell.forTableColumn(awardsList));
	 
	        Col2.setOnEditCommit((CellEditEvent<Object_Award, Awards> event) -> {
	            TablePosition<Object_Award, Awards> pos = event.getTablePosition();
	            Awards newAward = event.getNewValue();
	            int row = pos.getRow();
	            Object_Award thisAward = event.getTableView().getItems().get(row);
	            SqlUpdate.updateAward("award_type",thisAward.getAwardId(), newAward.getCode());
	            thisAward.setAwardType(newAward.getCode());
	        });
	        
			/// sets width of columns by percentage
			Col1.setMaxWidth( 1f * Integer.MAX_VALUE * 20);   // Phone
			Col2.setMaxWidth( 1f * Integer.MAX_VALUE * 50 );  // Type
	        
	        
	        ////////////////////// LISTENERS /////////////////////////
	    	    
	        awardAdd.setOnAction((event) -> {
				// get next primary key for awards table
				int awards_id = SqlSelect.getCount("awards","award_id") + 1; // gets last memo_id number
				// Create new award object
				Object_Award a = new Object_Award(awards_id,person.getP_id(),currentYear,"New Award");
				// Add info from award object to SQL database
				if(SqlInsert.addAwardRecord(a));
					// create a new row in tableView to match SQL insert from above
					award.add(a);
				Collections.sort(award, Comparator.comparing(Object_Award::getAwardId).reversed());
				// this line prevents strange buggy behaviour
				awardTableView.layout();
				// edit the year cell after creating
				awardTableView.edit(0, Col1);
	        });
	        
	        awardDelete.setOnAction(new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {
	            	int selectedIndex = awardTableView.getSelectionModel().getSelectedIndex();
	            	if(selectedIndex >= 0)
						if(SqlDelete.deleteAward(award.get(selectedIndex)))
							awardTableView.getItems().remove(selectedIndex);
	            }
	        });
	        
	        /////////////////// SET CONTENT //////////////////
	        
			vboxButtons.getChildren().addAll(awardAdd, awardDelete);
			awardTableView.getColumns().addAll(Col1,Col2);
			awardTableView.getSortOrder().addAll(Col1);
			vboxPink.getChildren().add(awardTableView);
			awardTableView.sort();
			hboxGrey.getChildren().addAll(vboxPink,vboxButtons);
			getChildren().add(hboxGrey);
		
	} // CONSTRUCTOR END
	
	///////////////// CLASS METHODS /////////////////
	
    private <T> TableColumn<T, String> createColumn(String title, Function<T, StringProperty> property) {
        TableColumn<T, String> col = new TableColumn<>(title);
        col.setCellValueFactory(cellData -> property.apply(cellData.getValue()));
        col.setCellFactory(column -> EditCell.createStringEditCell());
        return col ;
    }

}
