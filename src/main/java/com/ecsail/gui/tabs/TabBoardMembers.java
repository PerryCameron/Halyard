package com.ecsail.gui.tabs;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.ecsail.enums.Officer;
import com.ecsail.main.Launcher;
import com.ecsail.sql.SqlSelect;
import com.ecsail.structures.Object_Board;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
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
		
	VBox vboxLeft = new VBox();  // this is the vbox for organizing all the widgets
	HBox vboxBlue = new HBox();
	VBox vboxPink = new VBox(); // this creates a pink border around the table
	HBox officersTitleHBox = new HBox();
	HBox officersHBox = new HBox();
	HBox committeeTitleHBox = new HBox();
	HBox committeeHBox = new HBox();
	HBox boardMembersTitleBox = new HBox();
	HBox boardMembersHBox = new HBox();
	
	//Label year = new Label(selectedYear + " Officers");
	Label chairs = new Label("Committee Chairs");
	Label board = new Label("Board of Directors");
	
	/// experimental ///
//	String filename = "2020";
	Image slipImage = new Image(getClass().getResourceAsStream("/Stickers/" + selectedYear + ".png"));
	//Image slipImage = new Image(getClass().getResourceAsStream("/Stickers/" + filename + ".png"), 600, 600, false, false);
	ImageView imageView = new ImageView(slipImage);
	imageView.setFitWidth(300);
	imageView.setFitHeight(300);
	imageView.setPreserveRatio(true);
	//VBox image = new VBox();
	//image.getChildren().add(imageView);
	//// experimental ///
	
//	final Spinner<Integer> yearSpinner = new Spinner<Integer>();
//	SpinnerValueFactory<Integer> wetSlipValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1970, Integer.parseInt(selectedYear) + 1, Integer.parseInt(selectedYear));
//	yearSpinner.setValueFactory(wetSlipValueFactory);
//	yearSpinner.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL);
//	yearSpinner.focusedProperty().addListener((observable, oldValue, newValue) -> {
//		  if (!newValue) {
//			  selectedYear = yearSpinner.getEditor().getText();
//			  refreshBoardList();
//			  clearBoard(officerVBox1, officerVBox2, committeeVBox1, committeeVBox2,boardMembersVBox1,boardMembersVBox2,boardMembersVBox3);
//			  addOfficers(officerVBox1, officerVBox2);
//			  addChairmen(committeeVBox1, committeeVBox2);
//			  addBoard(boardMembersVBox1,boardMembersVBox2,boardMembersVBox3);
//			  System.out.println("Creating image: " + selectedYear + ".png");
//			  Image newImage = new Image(getClass().getResourceAsStream("/Stickers/" + selectedYear + ".png"));
//			  imageView.setImage(newImage);
//		  }
//		});

	ComboBox comboBox = new ComboBox();
	for(int i = Integer.parseInt(currentYear); i > 1969; i--) {
		comboBox.getItems().add(i);
	}

		comboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
			  selectedYear = newValue.toString();
			  refreshBoardList();
			  clearBoard(officerVBox1, officerVBox2, committeeVBox1, committeeVBox2,boardMembersVBox1,boardMembersVBox2,boardMembersVBox3);
			  addOfficers(officerVBox1, officerVBox2);
			  addChairmen(committeeVBox1, committeeVBox2);
			  addBoard(boardMembersVBox1,boardMembersVBox2,boardMembersVBox3);
			  System.out.println("Creating image: " + selectedYear + ".png");
			  Image newImage = new Image(getClass().getResourceAsStream("/Stickers/" + selectedYear + ".png"));
			  imageView.setImage(newImage);
		});

		year.setEffect(new DropShadow(2, Color.BLACK));
	chairs.setEffect(new DropShadow(2, Color.BLACK));
	board.setEffect(new DropShadow(2, Color.BLACK));
	
	boardMembersVBox1.setPrefWidth(150);
	boardMembersVBox2.setPrefWidth(150);
	boardMembersVBox3.setPrefWidth(150);
//	yearSpinner.setPrefWidth(145);
	comboBox.setPrefWidth(150);
	comboBox.setId("bigcombo-box");
//	comboBox.setStyle("-fx-font-size : 15pt");

	vboxLeft.setAlignment(Pos.TOP_CENTER);
	officersTitleHBox.setAlignment(Pos.CENTER);

	vboxLeft.setPadding(new Insets(15,0,0,40));
	boardMembersHBox.setPadding(new Insets(0,0,0,100));
	vboxBlue.setPadding(new Insets(10,10,10,10));
	vboxPink.setPadding(new Insets(3,3,3,3)); // spacing to make pink fram around table
	
	officerVBox1.getStyleClass().add("labels");
	committeeVBox1.getStyleClass().add("labels");
	officersHBox.getStyleClass().add("boardgeneral");
	committeeHBox.getStyleClass().add("boardgeneral");
	boardMembersHBox.getStyleClass().add("boardgeneral");
	officersTitleHBox.getStyleClass().add("title");
	committeeTitleHBox.getStyleClass().add("title");
	boardMembersTitleBox.getStyleClass().add("title");
	year.getStyleClass().add("title");
	chairs.getStyleClass().add("title");
	board.getStyleClass().add("title");


	vboxLeft.setSpacing(40);
	officersHBox.setSpacing(120);
	committeeHBox.setSpacing(30);
	boardMembersHBox.setSpacing(40);
	officersTitleHBox.setSpacing(190);
	officerVBox1.setSpacing(5);
	officerVBox2.setSpacing(5);
	committeeVBox1.setSpacing(5);
	committeeVBox2.setSpacing(5);
	boardMembersVBox1.setSpacing(5);
	boardMembersVBox2.setSpacing(5);
	boardMembersVBox3.setSpacing(5);
	vboxPink.setSpacing(10);

	VBox.setVgrow(vboxPink, Priority.ALWAYS);
	HBox.setHgrow(vboxPink,Priority.ALWAYS);

	vboxPink.setId("box-pink");
	vboxLeft.setId("box-pink");

	comboBox.getSelectionModel().selectFirst();
//	vboxGrey.setId("slip-box");

//	boardMembersHBox.setStyle("-fx-background-color: blue");
//	committeeHBox.setStyle("-fx-background-color: blue");
//	officersHBox.setStyle("-fx-background-color: blue");

	addOfficers(officerVBox1, officerVBox2);
	addChairmen(committeeVBox1, committeeVBox2);
	addBoard(boardMembersVBox1,boardMembersVBox2,boardMembersVBox3);
	

	
	officersHBox.getChildren().addAll(officerVBox1,officerVBox2);
	committeeHBox.getChildren().addAll(committeeVBox1,committeeVBox2);
	boardMembersHBox.getChildren().addAll(boardMembersVBox1,boardMembersVBox2,boardMembersVBox3);

	officersTitleHBox.getChildren().addAll(year);
	committeeTitleHBox.getChildren().addAll(chairs);
	boardMembersTitleBox.getChildren().addAll(board);
	vboxLeft.getChildren().addAll(comboBox, imageView);
	vboxPink.getChildren().addAll(officersTitleHBox, officersHBox, committeeTitleHBox, committeeHBox, boardMembersTitleBox,boardMembersHBox);
	//Pane screenPane = new Pane();
	vboxBlue.setId("box-blue");
	//screenPane.setId("slip-fonts");



	vboxBlue.getChildren().addAll(vboxLeft,vboxPink);
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
				Launcher.createMembershipTabForBOD(msid, selectedYear);
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
}
