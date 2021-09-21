package com.ecsail.gui.tabs;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ecsail.enums.MemberType;
import com.ecsail.gui.boxes.BoxPerson;
import com.ecsail.gui.boxes.BoxSearch;
import com.ecsail.main.HalyardPaths;
import com.ecsail.main.Launcher;
import com.ecsail.sql.Sql_SelectMembership;
import com.ecsail.sql.SqlExists;
import com.ecsail.sql.SqlSelect;
import com.ecsail.structures.Object_MembershipList;
import com.ecsail.structures.Object_Person;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class TabPeople extends Tab {

	//private Object_MembershipList membership;
	public static ObservableList<Object_Person> people;
	private static HBox personHBox = new HBox();
	TableColumn<Object_Person, Integer> Col1;
	TableColumn<Object_Person, Integer> Col2;
	TableColumn<Object_Person, MemberType> Col3;
	static TableColumn<Object_Person, String> Col4;
	static TableColumn<Object_Person, String> Col5;
	TableColumn<Object_Person, String> Col6;
	TableColumn<Object_Person, String> Col8;
	static TableView<Object_Person> personTableView;
	static int pick = 1;

	@SuppressWarnings("unchecked")
	public TabPeople(String text) {
		super(text);
		TabPeople.people = SqlSelect.getPeople();
		
		VBox vboxBlue = new VBox(); // main vbox
		VBox vbox2 = new VBox(); // sepearates box search and box people
		HBox hboxPink = new HBox();  // main hbox

		vboxBlue.setId("box-blue");
		hboxPink.setId("box-pink");
		vboxBlue.setPadding(new Insets(12,12,15,12));
		hboxPink.setPadding(new Insets(3,3,5,3));
		vboxBlue.setAlignment(Pos.TOP_CENTER);
		//vbox1.setPrefHeight(768);
		VBox.setVgrow(vboxBlue, Priority.ALWAYS);
		VBox.setVgrow(hboxPink, Priority.ALWAYS);
		hboxPink.setSpacing(10);
		vbox2.setSpacing(5);
		
		personTableView = new TableView<>();
		personTableView.setItems(people);
		personTableView.setPrefWidth(518);
		personTableView.setFixedCellSize(30);
		personTableView.setPrefHeight(680);
		
		Col1 = new TableColumn<Object_Person, Integer>("P ID");
		Col1.setCellValueFactory(new PropertyValueFactory<Object_Person, Integer>("p_id"));
		
		Col2 = new TableColumn<Object_Person, Integer>("MSID");
		Col2.setCellValueFactory(new PropertyValueFactory<Object_Person, Integer>("ms_id"));
		
		Col3 = new TableColumn<Object_Person, MemberType>("Member Type");
		Col3.setPrefWidth(120);
		Col3.setEditable(false);
		Col3.setCellValueFactory(new Callback<CellDataFeatures<Object_Person, MemberType>, ObservableValue<MemberType>>() {
			 
	        @Override
	        public ObservableValue<MemberType> call(CellDataFeatures<Object_Person, MemberType> param) {
	        	Object_Person person = param.getValue();
	            int memberCode = person.getMemberType();
	            MemberType keel = MemberType.getByCode(memberCode);
	            return new SimpleObjectProperty<MemberType>(keel);
	        }
	    });
			
		Col4 = new TableColumn<Object_Person, String>("First Name");
		Col4.setCellValueFactory(new PropertyValueFactory<Object_Person, String>("fname"));
		
		Col5 = new TableColumn<Object_Person, String>("Last Name");
		Col5.setCellValueFactory(new PropertyValueFactory<Object_Person, String>("lname"));

		Col6 = new TableColumn<Object_Person, String>("Occupation");
		Col6.setCellValueFactory(new PropertyValueFactory<Object_Person, String>("occupation"));
		
		Col8 = new TableColumn<Object_Person, String>("Active");
		Col8.setCellValueFactory(new PropertyValueFactory<Object_Person, String>("active"));
		
		personTableView.getColumns().addAll(Col1, Col2, Col3, Col4, Col5, Col6, Col8);
		
		personTableView.setRowFactory(tv -> {
	        TableRow<Object_Person> row = new TableRow<>();
	        row.setOnMouseClicked(event -> {
	            if (! row.isEmpty() && event.getButton()==MouseButton.PRIMARY 
	                 && event.getClickCount() == 2) {
	                Object_Person clickedRow = row.getItem();
					Launcher.createMembershipTabFromPeopleList(clickedRow.getMs_id());
					//System.out.println("clickedrow= " + clickedRow.getMs_id());
	            }
	            if (! row.isEmpty() && event.getButton()==MouseButton.PRIMARY 
		                 && event.getClickCount() == 1) {
		                Object_Person clickedRow = row.getItem();
		                createPersonBox(clickedRow);
						//System.out.println("clickedrow= " + clickedRow.getP_id());
		        }
	        });
	        return row ;
	    });
		
		vboxBlue.getChildren().add(hboxPink);
		vbox2.getChildren().addAll(new BoxSearch(personTableView), personHBox);
		hboxPink.getChildren().addAll(personTableView,vbox2);
		setContent(vboxBlue);
		
	}
	// creates array list of people objects populated from SQL database

	private static void createPersonBox(Object_Person person)  {
		Object_MembershipList membership = null;
		if(SqlExists.currentMembershipIdExists(person.getMs_id())) {
		membership = Sql_SelectMembership.getMembershipFromList(person.getMs_id(), HalyardPaths.getYear());
		} else {
		membership = Sql_SelectMembership.getMembershipFromListWithoutMembershipId(person.getMs_id());
		}
		personHBox.getChildren().clear();  // remove if exists
		//System.out.println("cleared the personHBox");
		personHBox.getChildren().add(new BoxPerson(person, membership,null)); // null is for tabpane not being used here.
	}

	public Object_Person getPersonByPid(int pid) {
		int index = 0;
		int count = 0;
		for(Object_Person person : people) {
			if(person.getP_id() == pid) {
				//System.out.println("Found pid " + pid);
				index = count; 
			}
			count++;
		}
		return people.get(index);  
	}
	
	public static int getIndexByPid(int pid) {
		int index = 0;
		int count = 0;
		for(Object_Person person : people) {
			if(person.getP_id() == pid) {
				//System.out.println("Found pid " + pid);
				index = count; 
			}
			count++;
		}
		return index; 
	}
	
	public static void searchLastName(String searchString) {
		int count = 0;
		Pattern p = Pattern.compile("^" +searchString, Pattern.MULTILINE);
		boolean flag = true;
		for (Object_Person o : personTableView.getItems()) {
		Matcher m = p.matcher(o.getLname().toLowerCase());
		
			while(m.find()) {
				//System.out.println(m.group() + " found on row " + count);
				if(flag) {
					pick = count;
					flag = false;
				}
			}
		count++;
			  //System.out.println(Col5.getCellData(o));
		}
		Platform.runLater(new Runnable()
		{
		    @Override
		    public void run()
		    {
		    	personTableView.requestFocus();
		    	personTableView.getSelectionModel().select(pick);
		    	personTableView.scrollTo(pick);
		    }
		});
	}
}
