package com.ecsail.gui;

import com.ecsail.main.CreateMembership;
import com.ecsail.main.Note;
import com.ecsail.main.SqlSelect;
import com.ecsail.structures.Object_MemLabels;
import com.ecsail.structures.Object_MembershipList;
import com.ecsail.structures.Object_Memo;
import com.ecsail.structures.Object_Person;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TabMembership extends Tab {
	private Object_MembershipList membership;
	private Object_MemLabels labels; // allows labels to be easily changed from another class
	private ObservableList<Object_Memo> memos;
	private ObservableList<Object_Person> people;  // has to be in this class because we pull up two instances
	private final int PRIMARY = 1;
	private final int SECONDARY = 2;
	private final int DEPENDANT = 3;
	
	public TabMembership(Object_MembershipList me) { 
		super();
		this.membership = me;
		System.out.println(me.toString());
		this.setText(setTabLabel());
        this.memos = SqlSelect.getMemos(membership.getMsid());
        this.labels = new Object_MemLabels();
        this.people = SqlSelect.getPeople(membership.getMsid());
		
		////////// OBJECTS /////////////
        Note note = new Note(memos,membership.getMsid());
		VBox windowSizeVBox = new VBox(); // provides a container for the scroll pane, so it can respect a size
		VBox containerVBox = new VBox();
		ScrollPane mainScrollPane = new ScrollPane();
		AnchorPane anchorPane = new AnchorPane();
		
        VBox mainVBox = new VBox();
        HBox hbox1 = new BoxMembership(membership, labels);  // holds membershipID, Type and Active
        HBox hbox2 = new HBox();  // holds PersonVBoxes (2 instances require a genereic HBox
        HBox hbox3 = new BoxAddress(membership);  // holds address, city, state, zip
        HBox hbox4 = new HBox();
        TabPane peopleTabPane = new TabPane();
        TabPane fiscalTabPane = new TabPane();
        TabPane informationTabPane = new TabPane();

        //////////// PROPERTIES ///////////////
        
        fiscalTabPane.setTabClosingPolicy(TabClosingPolicy.SELECTED_TAB);
        peopleTabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        informationTabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        mainVBox.setId("box-blue");
        mainScrollPane.setId("box-blue");
        mainScrollPane.setPrefViewportHeight(680);
        mainScrollPane.setFitToHeight(false);
		windowSizeVBox.setPrefHeight(690);
		
        hbox1.setSpacing(100);  // membership HBox
        hbox1.setId("box-pink");
        hbox1.setPadding(new Insets(15,15,0,15));
        hbox1.setAlignment(Pos.TOP_CENTER);
        hbox1.setPrefWidth(995);
        
        hbox2.setSpacing(10);   // holds PersonVBox
        hbox2.setId("box-pink");
        hbox2.setPadding(new Insets(5,15,15,15));
        hbox2.setAlignment(Pos.TOP_CENTER);
        
        hbox3.setSpacing(30);   // hold address HBox
        hbox3.setId("box-pink");
        hbox3.setPadding(new Insets(15,15,15,15));
        hbox3.setAlignment(Pos.TOP_CENTER);
        
        hbox4.setId("box-pink");  // holds boats and notes
        hbox4.setPadding(new Insets(0,15,10,15));
        hbox4.setAlignment(Pos.TOP_CENTER);
        mainVBox.setPrefWidth(1024);
        mainVBox.setPadding(new Insets(10,10,10,10));
        peopleTabPane.setPrefWidth(472);
        
        AnchorPane.setTopAnchor(mainScrollPane, 0.0);
        AnchorPane.setBottomAnchor(mainScrollPane, 0.0);
        AnchorPane.setLeftAnchor(mainScrollPane, 0.0);
        AnchorPane.setRightAnchor(mainScrollPane, 0.0);

        ////////// SETTING CONTENT /////////////////
        
		peopleTabPane.getTabs().add(new Tab("Primary Member", getPrimaryMember()));
        if(hasPerson(SECONDARY)) 
        	peopleTabPane.getTabs().add(new Tab("Secondary Member", getSecondaryMember()));
		if(hasPerson(DEPENDANT)) 
			addDependentTabs(peopleTabPane);	
		peopleTabPane.getTabs().add(new Tab("Add", new BoxAddPerson(peopleTabPane, note, membership)));
		fiscalTabPane.getTabs().add(new Tab("Slip", new BoxSlip(membership, this)));
		fiscalTabPane.getTabs().add(new Tab("Fiscal", new BoxFiscalList(membership, fiscalTabPane, people, note)));
		informationTabPane.getTabs().add(new Tab("Boats", new BoxBoat(membership)));
		informationTabPane.getTabs().add(new Tab("Notes", new BoxNotes(note)));
		informationTabPane.getTabs().add(new Tab("Properties", new BoxProperties(membership, labels, this)));
		informationTabPane.getTabs().add(new Tab("Attachments", new BoxAttachment(membership)));
		hbox2.getChildren().addAll(peopleTabPane, fiscalTabPane);  // new BoxInformation(membership)
		hbox4.getChildren().addAll(informationTabPane);
		containerVBox.getChildren().addAll(hbox1,hbox2,hbox3,hbox4);
		mainScrollPane.setContent(containerVBox);
		anchorPane.getChildren().add(mainScrollPane);
        mainVBox.getChildren().addAll(anchorPane);  // box blue
        windowSizeVBox.getChildren().add(mainVBox);
        setContent(windowSizeVBox);
	}
	
	///////////////////////// 	CLASS METHODS //////////////////////////////
	
	private void addDependentTabs(TabPane peopleTabPane) {
		int count = 0;
		int count2 = 1;
		for (Object_Person per : people) {
			if (per.getMemberType() == DEPENDANT) { // if this record is a child of the primary member
				peopleTabPane.getTabs().add(new Tab("Dependent " + count2, new BoxPerson(people.get(count),membership)));
				count2++; // child number
			}
			count++; // array element
		}
	}
	
	private int getPerson(int membertype) {  /// selects a person by membertype
		int element = 0;
		int loop = 0;
		for(Object_Person per : people) {
			if(per.getMemberType() == membertype) 
				element=loop;
			loop++;
		}
		return element;
	}
	
	private boolean hasPerson(int memberType) {
		boolean type = false;
		for(Object_Person per : people) {
			if(per.getMemberType() == memberType) 
				type = true;
		}
		return type;
	}

	private BoxPerson getSecondaryMember() {
		BoxPerson secondaryMember = new BoxPerson(people.get(getPerson(SECONDARY)), membership); // gets by membertype
		return secondaryMember;
	}
	
	private BoxPerson getPrimaryMember() {
		BoxPerson primaryMember;
		if (isNewMembership()) 
			primaryMember = new BoxPerson(CreateMembership.createUser(membership.getMsid()), membership);// create new primary
		else
			primaryMember = new BoxPerson(people.get(getPerson(PRIMARY)), membership); // load the primary member
		return primaryMember;
	}
	
	private boolean isNewMembership() {
		boolean newMembership = false;
		if(people.size() == 0)
			newMembership = true;
		return newMembership;
	}
	
	private String setTabLabel() {
		String tabLabel;
		if(membership.getMembershipId() == 0) {
			tabLabel = "MSID " + membership.getMsid();
		} else {
			tabLabel= "Membership " + membership.getMembershipId();
		}
		return tabLabel;
	}

}
