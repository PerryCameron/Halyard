package com.ecsail.gui.boxes;

import com.ecsail.main.SqlExists;
import com.ecsail.main.SqlInsert;
import com.ecsail.main.SqlSelect;
import com.ecsail.main.SqlUpdate;
import com.ecsail.main.TabLauncher;
import com.ecsail.structures.Object_MembershipList;
import com.ecsail.structures.Object_Slip;
import com.ecsail.structures.Object_WaitList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class BoxSlip extends HBox {
	
	private Object_MembershipList membership;
	private Object_WaitList waitList;
	private String errorMessage;
	private Object_Slip slip;
	private Label noSlipLabel;
	private HBox errorHBox;
	private Tab thisTab;
	private RadioButton rb1 = new RadioButton("Sublease Slip");
	private RadioButton rb2 = new RadioButton("Reassign Slip");
	private RadioButton rb3 = new RadioButton("Release Sublease");
	private HBox mainHBox = new HBox();  // holds controls and Image
	private VBox mainVBox = new VBox();  // holds hbox1,hbox2
	private TextField membershipIdTextField = new TextField();
	private HBox r1 = new HBox(); // for clearing contents
	private HBox r2 = new HBox(); // for clearing contents
	private HBox r3 = new HBox();
	private HBox hbox1 = new HBox();  // slip holder or none
	private HBox hbox2 = new HBox();  // sublease or none
	private HBox hbox3 = new HBox();  // Holds button and textfield
	private VBox assignVBox = new VBox(); // contains radio buttons, textfield and submit button
	private Button submitButton = new Button("Submit");
	private ToggleGroup group = new ToggleGroup();
	
	public BoxSlip(Object_MembershipList m, Tab tt) {
		this.membership = m;
		this.thisTab = tt;
	    this.errorMessage = ""; // error messaging
	    this.noSlipLabel = new Label("None");
	    VBox surroundVBox = new VBox();
	    this.errorHBox = new HBox();
	    CheckBox slipWaitCheckBox = new CheckBox("Slip Waitlist");
	    CheckBox kayakWaitCheckBox = new CheckBox("Kayak Waitlist");
	    CheckBox shedWaitCheckBox = new CheckBox("Shed Waitlist");
	    CheckBox wantsToSubleaseCheckBox = new CheckBox("Wants to Sublease");
	    CheckBox wantsToReleaseCheckBox = new CheckBox("Wants to Release");
	    CheckBox slipChangeCheckBox = new CheckBox("Slip Change");
	    VBox vboxWait = new VBox();
	    VBox vboxWaitFrame = new VBox();
	    VBox vboxWaitOuterFrame = new VBox();
		VBox vboxGrey = new VBox();  // this is the vbox for organizing all the widgets
		//HBox imageHBox = new BoxSlipImage(slip.getSlipNumber());
		
		noSlipLabel.setTextFill(Color.DARKCYAN);

		////////////  ATTRIBUTES //////////////////
		vboxWaitFrame.setId("box-blue");
		vboxWait.setId("box-grey");
		vboxWait.setSpacing(10);
		vboxWaitFrame.setPadding(new Insets(2,2,2,2));
		vboxWait.setPadding(new Insets(5,5,5,5));
		//vboxWaitFrame.setPrefWidth(50);
		vboxWaitOuterFrame.setPadding(new Insets(70,15,0,0));
		
		vboxWait.setSpacing(5);
		errorHBox.setPadding(new Insets(5, 15, 5, 15));  // first Name
		errorHBox.setAlignment(Pos.CENTER);
		errorHBox.setSpacing(13);
		hbox1.setSpacing(5);
		hbox1.setPadding(new Insets(0,15,5,15));
		hbox1.setAlignment(Pos.CENTER_LEFT);
		assignVBox.setPadding(new Insets(30,15,5,15));
		assignVBox.setPrefWidth(300);
		assignVBox.setAlignment(Pos.CENTER_LEFT);
		assignVBox.setSpacing(10);
		membershipIdTextField.setPrefWidth(40);
		mainHBox.setPrefWidth(460);  ///////////// sets the width
		mainVBox.setPrefWidth(200);
		mainHBox.setPadding(new Insets(15,15,0,5));
		hbox2.setSpacing(5);
		hbox3.setSpacing(5);
		hbox2.setPadding(new Insets(5,15,5,15));
		setSpacing(5);
		rb1.setToggleGroup(group); // sublease rb
		rb1.setSelected(true);
		rb2.setToggleGroup(group); // reassign rb
		rb3.setToggleGroup(group);
		rb1.setUserData("Sublease");
		rb2.setUserData("Reassign");
		rb3.setUserData("Release");
		submitButton.setPrefWidth(60);
		setPadding(new Insets(5, 5, 5, 5));  // creates space for blue frame
		vboxGrey.setId("box-grey");
		setId("box-blue");
		
		////////////// LISTENERS //////////////////////////

		/// this is for auto screen refreshing
		thisTab.selectedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean focusLost, Boolean focusGained) -> {
		    if (focusGained) {  // focus Gained
		    	refreshScreen();
		    }
		});
		
		// this is for deciding if text field should be displayed
		group.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
		    public void changed(ObservableValue<? extends Toggle> ov,
		        Toggle old_toggle, Toggle new_toggle) {
		            if (group.getSelectedToggle() != null) {
		            	String userData = (group.getSelectedToggle().getUserData().toString());
		            	switch(userData) 
		                { 
		                    case "Sublease": 
		                    	membershipIdTextField.setVisible(true); 
		                        break; 
		                    case "Reassign": 
		                    	membershipIdTextField.setVisible(true);
		                        break; 
		                    case "Release":
		                    	membershipIdTextField.setVisible(false);
		                    	// this will release the slip when user hits submit button 
		                        break; 
		                    default: 
		                        // do nothing for now, may add error handling?
		                } 
		            }                
		        }
		});
		
		submitButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				errorHBox.getChildren().clear();
				if(selectedRadioButton("Release"))
				releaseSlip();
				else if (selectedRadioButton("Sublease"))
				subleaseSlip();
				else if (selectedRadioButton("Reassign"))
				reassignSlip(getMsidFromTextField());
				refreshScreen();
			}
		});
		
		slipWaitCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            	SqlUpdate.updateWaitList(membership.getMsid(),"SLIPWAIT",newValue);
            }
        });
		kayakWaitCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            	SqlUpdate.updateWaitList(membership.getMsid(),"KAYAKRACKWAIT",newValue);
            }
        });
		shedWaitCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            	SqlUpdate.updateWaitList(membership.getMsid(),"SHEDWAIT",newValue);
            }
        });
		wantsToSubleaseCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            	SqlUpdate.updateWaitList(membership.getMsid(),"WANTSUBLEASE",newValue);
            }
        });
		wantsToReleaseCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            	SqlUpdate.updateWaitList(membership.getMsid(),"WANTRELEASE",newValue);
            }
        });
		slipChangeCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            	SqlUpdate.updateWaitList(membership.getMsid(),"WANTSLIPCHANGE",newValue);
            }
        });
		
		///////////// ACTIONS //////////////////////////////
		
		if(SqlExists.waitListExists(m.getMsid())) {
			waitList = SqlSelect.getWaitList(membership.getMsid());
		} else { //it doesn't exist
			waitList = new Object_WaitList(membership.getMsid(),false,false,false,false,false,false);
			SqlInsert.addWaitList(waitList);
		}
		slipWaitCheckBox.setSelected(waitList.isSlipWait());
		kayakWaitCheckBox.setSelected(waitList.isKayakWait());
		shedWaitCheckBox.setSelected(waitList.isShedWait());
		wantsToSubleaseCheckBox.setSelected(waitList.isWantToSublease());
		slipChangeCheckBox.setSelected(waitList.isWantSlipChange());
		
		/////////////  ASSIGN CHILDREN  /////////////////////
		displaySlip();
		addControls();
		vboxWait.getChildren().addAll(slipWaitCheckBox,kayakWaitCheckBox
				,shedWaitCheckBox,wantsToSubleaseCheckBox
				,wantsToReleaseCheckBox,slipChangeCheckBox);
		vboxWaitFrame.getChildren().add(vboxWait);
		vboxWaitOuterFrame.getChildren().add(vboxWaitFrame);
		assignVBox.getChildren().addAll(r1,r2,r3,hbox3);
		mainVBox.getChildren().addAll(hbox1,hbox2,assignVBox,vboxWaitOuterFrame); // add slip information
		surroundVBox.getChildren().addAll(errorHBox,mainHBox);  // put this in for error messages
		vboxGrey.getChildren().addAll(surroundVBox);
		getChildren().addAll(vboxGrey);
	}
	
	////////////  CLASS METHODS ///////////////
	private void refreshScreen() {
		clearControls();
		displaySlip();
		addControls();
	}
	
	private int getMsidFromTextField() {
		if(isInteger(membershipIdTextField.getText()))
		return SqlSelect.getMsidFromMembershipID(Integer.parseInt(membershipIdTextField.getText()));
		else return 0;
	}
	
	private boolean isInteger(String stringToTest) {
		try {
		Integer.parseInt(stringToTest);
		return true;
		} catch (NumberFormatException e) {
		return false;	
		}
	}
	
	private void reassignSlip(int ms_id) {
		if (membershipExists(ms_id)) { // member exists using membershipID
			checkForSublease(ms_id);  // releases sub-lease if it exists
			if (ownsSlip(ms_id)) // this member already has a slip
				printErrorMessage("Membership " + membershipIdTextField.getText() + " already has a slip");
			else {
				SqlUpdate.releaseSlip(membership); // Release all sub-leases in SQL
				SqlUpdate.reAssignSlip(ms_id, membership); // reassign slip to this ms_id in SQL
				setSlipToNone();
			}
		} else
			printErrorMessage("User " + membershipIdTextField.getText() + " does not exist");
	}
	
	private void checkForSublease(int ms_id) {
		if (isLeasingSlip(ms_id)) { // OK this member is already sub-leasing a slip, lets release it
			releaseSlipSublease(ms_id);
		}
	}
	
	private void releaseSlipSublease(int ms_id) {  // overloaded method
		   slip = SqlSelect.getSubleasedSlip(ms_id);
		   SqlUpdate.subleaserReleaseSlip(ms_id); // this releases the slip
	}
	
	private boolean membershipExists(int ms_id) {
		return SqlExists.memberShipExists(ms_id);
	}
	
	private void setSlipToNone() { // for reassignSlip()
		hbox1.getChildren().clear();
		hbox2.getChildren().clear();
		hbox1.getChildren().addAll(new Label("Slip Number:"), noSlipLabel);
		membership.setSlip("");
	}
	
	private void setSlipAsNone() { // for displayInformation()
		slip = new Object_Slip(0,membership.getMsid(),"none",0);
		hbox1.getChildren().addAll(new Label("Slip Number:"), noSlipLabel);
		setRadioButtonVisibility(false, false, false);
		hbox3.setVisible(false);
	}
	

	
	private void releaseSlip() {  // overloaded method
		SqlUpdate.releaseSlip(membership);
		r3.setVisible(false);
	}
	
	private boolean isLeasingSlip(int ms_id) {
		return SqlExists.slipRentExists(ms_id);
	}
	
	private void subleaseSlip() {
		int ms_id = getMsidFromTextField();
		if (membershipExists(ms_id)) { // member exists using membershipID
			if (ownsSlip(ms_id))  // this member already has a slip
				printErrorMessage("Membership " + membershipIdTextField.getText() + " already has a slip");
			else { 
				SqlUpdate.updateSlip(ms_id, membership);
				slip = SqlSelect.getSlip(membership.getMsid()); // added in because sublease changed it
				membership.setSlip(slip.getSlipNumber());
			}
		} else  // Member does not exist
			printErrorMessage("User " + membershipIdTextField.getText() + " does not exist");
	}
	
	private void printErrorMessage(String message) {
		Label errorLabel = new Label(message);
		errorLabel.setTextFill(Color.RED);
		errorHBox.getChildren().add(errorLabel);
	}
	
	private boolean selectedRadioButton(String selection) {
		return group.getSelectedToggle().getUserData().toString().equals(selection);
	}
	
	private void addControls() {
		hbox3.getChildren().addAll(membershipIdTextField, submitButton);	
		r1.getChildren().add(rb1); // in hbox so can remove
		r2.getChildren().add(rb2);
		r3.getChildren().add(rb3);
		mainHBox.getChildren().addAll(mainVBox, new BoxSlipImage(slip.getSlipNumber()));
		
	}
	
	private void clearControls() {
		hbox1.getChildren().clear();
		hbox2.getChildren().clear();
		hbox3.getChildren().clear();
		r1.getChildren().clear();
		r2.getChildren().clear();
		r3.getChildren().clear();
		mainHBox.getChildren().clear();
	}
	

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	private void setMouseListener(Text text, int msid) {
		Color color = (Color) text.getFill();
		if(color == Color.CORNFLOWERBLUE) {
			text.setOnMouseExited(ex -> {
				text.setFill(Color.CORNFLOWERBLUE);
			});
		} else {
			text.setOnMouseExited(ex -> {
				text.setFill(Color.BLACK);
			});
		}
		text.setOnMouseEntered(en -> {
			text.setFill(Color.RED);
				});

		text.setOnMouseClicked(e -> {
			if (e.getClickCount() == 1)  {
					TabLauncher.createTab(msid);
			}
		});
	}
	
	private boolean ownsSlip(int ms_id) {
		return SqlExists.slipExists(ms_id);
	}
	
	private void displaySlip() {
		if (hasSlip())
			checkIfLeasingOut();
		else if (isSubleasingSlip()) // does not own, but is sub-leasing
			setSubleasedSlip();
		else  // has no slip
		setSlipAsNone();
	}
	
	private void checkIfLeasingOut() {
		Label slipNumber = setMemberSlip();
		if(isLeasingOutSlip())
		displaySublease(slipNumber);
		else 
		displaySlipNumberLabel(slipNumber);
	}
	
	private void displaySlipNumberLabel(Label slipNumber) {
		hbox1.getChildren().addAll(new Label("Slip Number:"), slipNumber);  // member plus their slip number
	}

	private boolean isSubleasingSlip() {
		boolean sublease = false;
		if (SqlExists.slipRentExists(membership.getMsid()))
			sublease = true;
		return sublease;
	}
	
	private boolean isLeasingOutSlip() {
		boolean leasing = true;
		if (slip.getSubleased_to() == 0)
			leasing = false;
		return leasing;
	}
	
	private void setSubleasedSlip() {
		slip = SqlSelect.getSubleasedSlip(membership.getMsid());  // gets the slip information using the sub-slip attribute
		Label slipNumber = new Label(slip.getSlipNumber());
		slipNumber.setTextFill(Color.DARKCYAN);
		slipNumber.setStyle("-fx-font-weight: bold;");
		Text subLease = new Text("" + SqlSelect.getMembershipIDfromMsid(slip.getMs_id())); // Converts to membership ID
		subLease.setStyle("-fx-font-weight: bold;");
		setMouseListener(subLease, slip.getMs_id()); // need to get msid from
		hbox1.getChildren().addAll(new Text("Slip Number:"), slipNumber);
		hbox2.getChildren().addAll(new Text("Subleased from:"), subLease);
		setRadioButtonVisibility(false, false, false);
		hbox3.setVisible(false);
	}
	
	private Label setMemberSlip() {
		slip = SqlSelect.getSlip(membership.getMsid());
		Label slipNumber = new Label(slip.getSlipNumber());
		slipNumber.setTextFill(Color.BLUE);
		slipNumber.setStyle("-fx-font-weight: bold;");
		submitButton.setVisible(true);
		membershipIdTextField.setVisible(true);
		setRadioButtonVisibility(true, true, false);
		hbox3.setVisible(true);
		return slipNumber;
	}
	
	private void displaySublease(Label slipNumber) {
		Text subLease = new Text("" + SqlSelect.getMembershipIDfromMsid(slip.getSubleased_to()));
		subLease.setStyle("-fx-font-weight: bold;");
		setMouseListener(subLease, membership.getSubleaser());
		hbox1.getChildren().addAll(new Text("Slip Number:"), slipNumber);  // member plus their slip number
		hbox2.getChildren().addAll(new Text("Subleased to:"), subLease);  // subleased to (member)
		setRadioButtonVisibility(true, true, true);
	}
	
	private boolean hasSlip() {
		return SqlExists.slipExists((membership.getMsid()));
	}
	
	private void setRadioButtonVisibility(boolean rb1, boolean rb2, boolean rb3) {
		r1.setVisible(rb1);
		r2.setVisible(rb2);
		r3.setVisible(rb3);
	}
	
	
}
