package com.ecsail.gui.tabs;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.ecsail.enums.Officer;
import com.ecsail.main.Paths;
import com.ecsail.main.SqlSelect;
import com.ecsail.main.TabLauncher;
import com.ecsail.structures.Object_Board;
import com.ecsail.structures.Object_MembershipList;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Tab;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class TabBoardMembers extends Tab {
	
	VBox boardMembersVBox1 = new VBox();
	VBox boardMembersVBox2 = new VBox();
	VBox boardMembersVBox3 = new VBox();
	VBox committeeVBox1 = new VBox();  // titles
	VBox committeeVBox2 = new VBox();
	VBox officerVBox1 = new VBox();  // titles
	VBox officerVBox2 = new VBox();
	private ObservableList<Object_Board> board;
	String selectedYear;
	String currentYear;
	Label year;
	
	public TabBoardMembers(String text) {
		super(text);
		this.selectedYear = new SimpleDateFormat("yyyy").format(new Date());  // lets start at the current year
		this.board =  SqlSelect.getBoard(selectedYear);
		this.currentYear = selectedYear;  // save the current year for later
		this.year = new Label(selectedYear + " Officers");
		
	VBox vboxGrey = new VBox();  // this is the vbox for organizing all the widgets
	VBox vboxBlue = new VBox();
	VBox vboxPink = new VBox(); // this creates a pink border around the table
	HBox offciersTitleHBox = new HBox();
	HBox officersHBox = new HBox();
	HBox committeeTitleHBox = new HBox();
	HBox committeeHBox = new HBox();
	HBox boardMembersTitleBox = new HBox();
	HBox boardMembersHBox = new HBox();
	
	//Label year = new Label(selectedYear + " Officers");
	Label chairs = new Label("Committee Chairs");
	Label board = new Label("Board of Directors");
	
	final Spinner<Integer> yearSpinner = new Spinner<Integer>();
	SpinnerValueFactory<Integer> wetSlipValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1970, Integer.parseInt(selectedYear) + 1, Integer.parseInt(selectedYear));
	yearSpinner.setValueFactory(wetSlipValueFactory);
	yearSpinner.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL);
	yearSpinner.focusedProperty().addListener((observable, oldValue, newValue) -> {
		  if (!newValue) {
			  selectedYear = yearSpinner.getEditor().getText();
			  refreshBoardList();
			  clearBoard(officerVBox1, officerVBox2, committeeVBox1, committeeVBox2,boardMembersVBox1,boardMembersVBox2,boardMembersVBox3);
			  addOfficers(officerVBox1, officerVBox2);
			  addChairmen(committeeVBox1, committeeVBox2);
			  addBoard(boardMembersVBox1,boardMembersVBox2,boardMembersVBox3);
		  }
		});
	
	year.setEffect(new DropShadow(2, Color.BLACK));
	chairs.setEffect(new DropShadow(2, Color.BLACK));
	board.setEffect(new DropShadow(2, Color.BLACK));
	
	boardMembersVBox1.setPrefWidth(150);
	boardMembersVBox2.setPrefWidth(150);
	boardMembersVBox3.setPrefWidth(150);
	
	
	boardMembersHBox.setPadding(new Insets(0,0,0,100));
	offciersTitleHBox.setPadding(new Insets(0,0,0,320));
	
	officerVBox1.getStyleClass().add("labels");
	committeeVBox1.getStyleClass().add("labels");
	officersHBox.getStyleClass().add("boardgeneral");
	committeeHBox.getStyleClass().add("boardgeneral");
	boardMembersHBox.getStyleClass().add("boardgeneral");
	offciersTitleHBox.getStyleClass().add("title");
	committeeTitleHBox.getStyleClass().add("title");
	boardMembersTitleBox.getStyleClass().add("title");
	year.getStyleClass().add("title");
	chairs.getStyleClass().add("title");
	board.getStyleClass().add("title");
	
	officersHBox.setSpacing(120);
	committeeHBox.setSpacing(30);
	boardMembersHBox.setSpacing(40);
	vboxPink.setSpacing(10);
	yearSpinner.setPrefWidth(145);
	offciersTitleHBox.setSpacing(190);
	vboxBlue.setPadding(new Insets(10,10,10,10));
	vboxPink.setPadding(new Insets(3,3,3,3)); // spacing to make pink fram around table
	vboxPink.setPrefHeight(695);
	vboxPink.setId("box-pink");
	vboxGrey.setId("slip-box");
	
	addOfficers(officerVBox1, officerVBox2);
	addChairmen(committeeVBox1, committeeVBox2);
	addBoard(boardMembersVBox1,boardMembersVBox2,boardMembersVBox3);
		
	officersHBox.getChildren().addAll(officerVBox1);
	committeeHBox.getChildren().addAll(committeeVBox1);
	officersHBox.getChildren().add(officerVBox2);
	committeeHBox.getChildren().add(committeeVBox2);
	boardMembersHBox.getChildren().addAll(boardMembersVBox1,boardMembersVBox2,boardMembersVBox3);

	offciersTitleHBox.getChildren().addAll(year, yearSpinner);
	committeeTitleHBox.getChildren().addAll(chairs);
	boardMembersTitleBox.getChildren().addAll(board);
	vboxPink.getChildren().addAll(offciersTitleHBox, officersHBox, committeeTitleHBox, committeeHBox, boardMembersTitleBox,boardMembersHBox);
	Pane screenPane = new Pane();
	vboxBlue.setId("box-blue");
	screenPane.setId("slip-fonts");

	vboxGrey.getChildren().add(screenPane);
	vboxBlue.getChildren().add(vboxPink);
	vboxPink.getChildren().add(vboxGrey);
	setContent(vboxBlue);
	}
	
	private void refreshBoardList() {
		year.setText(selectedYear + " Officers");
		board.clear();
		board =  SqlSelect.getBoard(selectedYear);
	}
	
	private void clearBoard(VBox officerVBox1, VBox officerVBox2, VBox committeeVBox1, VBox committeeVBox2,VBox boardMembersVBox1,VBox boardMembersVBox2,VBox boardMembersVBox3) {
		officerVBox1.getChildren().clear();
		officerVBox2.getChildren().clear();
		committeeVBox1.getChildren().clear();
		committeeVBox2.getChildren().clear();
		boardMembersVBox1.getChildren().clear();
		boardMembersVBox2.getChildren().clear();
		boardMembersVBox3.getChildren().clear();
	}
	
	private void addBoard(VBox boardMembersVBox1,VBox boardMembersVBox2,VBox boardMembersVBox3) {
		getBoard(selectedYear, boardMembersVBox1);  /// add a listener here
		getBoard(incrementSelectedYear(1), boardMembersVBox2);
		getBoard(incrementSelectedYear(2), boardMembersVBox3);	
	}
	
	private String incrementSelectedYear(int increment) {
		return (Integer.parseInt(selectedYear) + increment) + "";
	}
	
	
	private void addOfficers(VBox officerVBox1, VBox officerVBox2) {
		Boolean start = true;
		for (Officer off : Officer.values()) {
			if(start)
				if (off.getCode().equals("HM"))  /// this is the marker where to stop
					start = false;
			if(start) {  // we are only going to use officers
				if (!off.getCode().equals("BM")) { // except we don't want boardmen
					String officer = getOfficer(off.getCode()); // so we don't have to iterate this twice
					if (!officer.equals("")) { // lets get rid of blank ones too
						officerVBox1.getChildren().add(new Text(off.getText())); // this is our labels
						officerVBox2.getChildren()
								.add(setMouseListener(new Text(officer), getOfficerMSID(off.getCode())));
					}
				}
			}
		}
	}
	
	
	private void addChairmen(VBox committeeVBox1, VBox committeeVBox2) {
		Boolean start = false;
		for (Officer off : Officer.values()) {
			if (!start) // initial condition
				if (off.getCode().equals("HM"))
					start = true; // change condition when we reach HM
			if (start) { // we are only going to use the chairmen
				if (!off.getCode().equals("BM")) { // except we don't want boardmen
					String officer = getOfficer(off.getCode()); // so we don't have to iterate this twice
					if (!officer.equals("")) { // lets get rid of blank ones too
						committeeVBox1.getChildren().add(new Text(off.getText())); // this is our labels
						committeeVBox2.getChildren()
								.add(setMouseListener(new Text(officer), getOfficerMSID(off.getCode())));
					}
				}
			}
		}
	}
	
	private String getOfficer(String offType) {
		@SuppressWarnings("unused")
		int count = 0;
		String officerName = "";
		for(Object_Board bm: board) {
			if(offType.equals(bm.getOfficer_type()))
				officerName = bm.getFname() + " " + bm.getLname();
			
		count++;
		}	
		return officerName;
	}
	
	private int getOfficerMSID(String offType) {
		int msid = 0;
		for(Object_Board bm: board) {
			if(offType.equals(bm.getOfficer_type()))
				msid = bm.getMs_id();
		}	
		return msid;
	}
	
	private void getBoard(String year, VBox fillHBox) {
		Text yearText = new Text(year);
		setMouseListener(yearText);
		yearText.getStyleClass().add("title");
		fillHBox.getChildren().add(yearText);
		for(Object_Board bm: board) {
				if(bm.getBoard_year().equals(year))
					fillHBox.getChildren().add(setMouseListener(new Text((bm.getFname() + " " + bm.getLname())), bm.getMs_id()));
		}	
	}
	
	private Text setMouseListener(Text text, int msid) {
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
			if (e.getClickCount() == 2)  {
				createTab(msid);
			}
		});
		return text;
	}
	
	private Text setMouseListener(Text text) { // overload for changing years easily
		if (Integer.parseInt(text.getText()) <= Integer.parseInt(currentYear)) {  // lets not go into the future
			Color color = (Color) text.getFill();
			if (color == Color.CORNFLOWERBLUE) {
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
				if (e.getClickCount() == 1) {

					selectedYear = text.getText();
					refreshBoardList();
					clearBoard(officerVBox1, officerVBox2, committeeVBox1, committeeVBox2, boardMembersVBox1,
							boardMembersVBox2, boardMembersVBox3);
					addOfficers(officerVBox1, officerVBox2);
					addChairmen(committeeVBox1, committeeVBox2);
					addBoard(boardMembersVBox1, boardMembersVBox2, boardMembersVBox3);
				}

			});
		}
		return text;
	}
	
	private static void createTab(int ms_id)  {
		Object_MembershipList membership;
		if(SqlSelect.isActive(ms_id)) { // membership is active and in our object tree
		membership = SqlSelect.getMembershipFromList(ms_id,Paths.getYear());
		} else { // membership is not active and needs to be pulled from the SQL Database
		membership = SqlSelect.getInactiveMembershipFromList(ms_id);
		}	
		TabLauncher.createTab(membership);
	}
	
}
