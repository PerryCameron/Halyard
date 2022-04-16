package com.ecsail.gui.tabs;

import com.ecsail.main.HalyardPaths;
import com.ecsail.pdf.PDF_SlipChart;
import com.ecsail.main.Launcher;
import com.ecsail.sql.select.SqlMembershipList;
import com.ecsail.sql.select.SqlSlip;
import com.ecsail.structures.MembershipListDTO;

import com.ecsail.structures.SlipDTO;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;

import java.util.ArrayList;
import java.util.HashMap;

public class TabSlips extends Tab {

	//private ObservableList<Object_Slip> slips;
	private ObservableList<MembershipListDTO> slipmemberships;
	private ObservableList<MembershipListDTO> subleaserMemberships;
	private MembershipListDTO blankObject = new MembershipListDTO(0,0,0,"","","","Racing","",0,"","","","","");
	// a list of all the slips
	private ArrayList<SlipDTO> slips;
	private HashMap<String,Text> slipsHash = new HashMap<>();

	// starting point for each colume x-axis
	private int col[] = { 20,125,280,385,540,643,800,902 };

	
	private int row1 = 19; // d40
	private int row2 = 45;  // d38
	private int row3 = row1 + 42;  // d36
	private int row4 = row2 + 42;  // d34
	private int row5 = row3 + 42;  // d32
	private int row6 = row4 + 42;  // d30
	private int row7 = row5 + 42;  // d28
	private int row8 = row6 + 42;  // d26
	private int row9 = row7 + 42;  // d24
	private int row10 = row8 + 42; // d22
	private int row11 = row9 + 42; // d20
	private int row12 = row10 + 42; // d18
	private int row13 = row11 + 42; // d16
	private int row14 = row12 + 42; // d14
	private int row15 = row13 + 42; // d12
	private int row16 = row14 + 42; // d10
	private int row17 = row15 + 42; // d08
	private int row18 = row16 + 42; // d06
	private int row19 = row17 + 42; // d04
	private int row20 = row18 + 42; // d02
	private int row21 = row19 + 42; // a02
	private int row22 = row20 + 42; // a01
	private int row23 = row21 + 42; // b50
	private int row24 = row22 + 42; // b48
	

	
	public TabSlips(String text) {
		super(text);
		this.slipmemberships = SqlMembershipList.getSlipRoster(HalyardPaths.getYear());
		this.subleaserMemberships = FXCollections.observableArrayList();
		this.slips = SqlSlip.getSlips();
		for(SlipDTO s: slips) {
			slipsHash.put(s.getSlipNumber(),new Text(""));
		}


		fillSlips(); // must be filled the first time.
		Pane screenPane = new Pane();
		VBox vboxGrey = new VBox();  // this is the vbox for organizing all the widgets
		VBox vboxBlue = new VBox();
		VBox vboxPink = new VBox(); // this creates a pink border around the table
		Button createPdfButton = new Button("Create PDF");
		
		///// LISTENERS //////
		/// this listens for a focus on the slips tab and refreshes data everytime.
		this.selectedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
		    if (newValue) {  // focus Gained
		    	screenPane.getChildren().clear();
		    	fillSlips();
		    	screenPane.getChildren().add(addDocks(10,10,col[0]));
		    	screenPane.getChildren().add(addDocks(7,11,col[2]));
		    	screenPane.getChildren().add(addDocks(11,12,col[4]));
		    	screenPane.getChildren().add(addDocks(11,11,col[6]));
		    	screenPane.getChildren().add(createPdfButton);
				slipsHash.forEach((k,v) -> screenPane.getChildren().add(v));
		    }
		});
		
		createPdfButton.setOnAction((event) -> {
        	new PDF_SlipChart(HalyardPaths.getYear());
        });
				
		///////////////// ATTRIBUTES /////////////////
		
		createPdfButton.setId("mediumbuttontext");
		vboxBlue.setId("box-blue");
		screenPane.setId("slip-fonts");
		vboxBlue.setPadding(new Insets(10,10,10,10));
		vboxPink.setPadding(new Insets(3,3,3,3)); // spacing to make pink fram around table
		vboxPink.setId("box-pink");
		vboxGrey.setId("slip-box");
		
		//vboxGrey.setPrefHeight(688);
		//rotatef1.setAngle(314);
		
		HBox.setHgrow(vboxGrey, Priority.ALWAYS);
		VBox.setVgrow(vboxGrey, Priority.ALWAYS);
		HBox.setHgrow(vboxPink, Priority.ALWAYS);
		VBox.setVgrow(vboxPink, Priority.ALWAYS);
		
		setRotation();

		createPdfButton.setLayoutX(750);
		createPdfButton.setLayoutY(600);
		
		//////////////////  SET CONTENT ///////////////
		vboxGrey.getChildren().add(screenPane);
		vboxBlue.getChildren().add(vboxPink);
		vboxPink.getChildren().add(vboxGrey);
		setContent(vboxBlue);

	}



	private void rotate45(int i, int i2, String f03) {
		Rotate rotate;
		rotate = new Rotate();
		rotate.setAngle(314);
		rotate.setPivotX(i);
		rotate.setPivotY(i2);
		slipsHash.get(f03).getTransforms().addAll(rotate);
	}


	// places all Text() objects, in a method so it can be refreshed.
	private Group addDocks(int leftDock, int rightDock, int start) {
	    Group group = new Group();
	    Rotate rotate = new Rotate();
	    Rectangle rect;
	    int dockWidth = 80;
	    int dockHeight = 10;
	    int stemWidth = 20;
	    int gapDistance = 42;

	    // draw left docks
	    int y = 23;
	    for(int i = 0; i < leftDock; i++) {
	    rect = new Rectangle(start,y,dockWidth,dockHeight);  // y position always starts at 23
	    rect.setFill(Color.BLACK);
	    rect.setStroke(Color.BLACK);
	    group.getChildren().add(rect);
	    y+=gapDistance;
	    }

	    // draw right docks 
	    y = 23;  // set back to default start point for right dock
	    for(int i = 0; i < rightDock; i++) {
	    rect = new Rectangle(start + dockWidth + stemWidth,y,dockWidth,dockHeight);  // y position always starts at 23
	    rect.setFill(Color.BLACK);
	    rect.setStroke(Color.BLACK);
	    group.getChildren().add(rect);
	    y+=gapDistance;
	    }

		// draw angled right dock
		int x = 50;
		//int ay = 546; //587
		for(int ay = 546; ay < 711; ay+=41) {
			rect = new Rectangle(x + dockWidth + stemWidth, ay, dockWidth, dockHeight);  // y position always starts at 23
			rect.setFill(Color.BLACK);
			rect.setStroke(Color.BLACK);
			rotate.setAngle(250);
			rotate.setPivotX(x);
			rotate.setPivotY(ay);
			rect.getTransforms().addAll(rotate);
			group.getChildren().add(rect);
		}

	    // draw stem
	    rect = new Rectangle(start + dockWidth,23,stemWidth,y - 21);  // y position always starts at 23
	    rect.setFill(Color.BLACK);
	    rect.setStroke(Color.BLACK);
	    group.getChildren().addAll(rect,getOctagon(110.0,470.0,42.0));

	    // draw angled stem
	    x = 130;
	    y = 505;
	    rect = new Rectangle(x,y,stemWidth,230);  // y position always starts at 23
	    rect.setFill(Color.BLACK);
	    rect.setStroke(Color.BLACK);
	    rotate.setAngle(315);
	    rotate.setPivotX(x); 
	    rotate.setPivotY(y);
	    rect.getTransforms().addAll(rotate);
	    group.getChildren().add(rect);
	    return group;
	}
	
	private Polygon getOctagon(double x, double y, double size) {
		Polygon oct = new Polygon();
		setPolygonSides(oct, x, y, size, 8);
	    return oct;
	}
	
	private static void setPolygonSides(Polygon polygon, double centerX, double centerY, double radius, int sides) {
	    polygon.getPoints().clear();
	    final double angleStep = Math.PI * 2 / sides;
	    double angle = 0; // assumes one point is located directly beneat the center point
	    for (int i = 0; i < sides; i++, angle += angleStep) {
	        polygon.getPoints().addAll(
	                Math.sin(angle) * radius + centerX, // x coordinate of the corner
	                Math.cos(angle) * radius + centerY // y coordinate of the corner
	        );
	    }
	}

	private void createAngledText(Pane screenPane, int x, int y) {
		Text staticDock;
		for(int i = 0; i < 3; i++) {
			staticDock = new Text(x, y, "48 Hour dock");
			staticDock.setRotate(-45);
			screenPane.getChildren().add(staticDock);
			x+=29;
			y+=29;
		}
	}

	private void fillSlips() {
		for (SlipDTO mem : slips) {
			switch (mem.getSlipNumber()) {
				case "D40":
					placeText(col[0], row1, "D40");
					break;
				case "D39":
					placeText(col[1], row1, "D39");
					break;
				case "A35":
					placeText(col[2], row1, "A35");
					break;
				case "A34":
					placeText(col[3], row1, "A34");
					break;
				case "B93":
					placeText(col[4], row1, "B93");
					break;
				case "B94":
					placeText(col[5], row1, "B94");
					break;
				case "C157":
					placeText(col[6], row1, "C157");
					break;
				case "C156":
					placeText(col[7], row1, "C156");
					break;
				case "D38":
					placeText(col[0], row2, "D38");
					break;
				case "D37":
					placeText(col[1], row2, "D37");
					break;
				case "A33":
					placeText(col[2], row2, "A33");
					break;
				case "A32":
					placeText(col[3], row2, "A32");
					break;
				case "B91":
					placeText(col[4], row2, "B91");
					break;
				case "B92":
					placeText(col[5], row2, "B92");
					break;
				case "C155":
					placeText(col[6], row2, "C155");
					break;
				case "C154":
					placeText(col[7], row2, "C154");
					break;
				case "D36":
					placeText(col[0], row3, "D36");
					break;
				case "D35":
					placeText(col[1], row3, "D35");
					break;
				case "A31":
					placeText(col[2], row3, "A31");
					break;
				case "A30":
					placeText(col[3], row3, "A30");
					break;
				case "B89":
					placeText(col[4], row3, "B89");
					break;
				case "B90":
					placeText(col[5], row3, "B90");
					break;
				case "C153":
					placeText(col[6], row3, "C153");
					break;
				case "C152":
					placeText(col[7], row3, "C152");
					break;
				case "D34":
					placeText(col[0], row4, "D34");
					break;
				case "D33":
					placeText(col[1], row4, "D33");
					break;
				case "A29":
					placeText(col[2], row4, "A29");
					break;
				case "A28":
					placeText(col[3], row4, "A28");
					break;
				case "B87":
					placeText(col[4], row4, "B87");
					break;
				case "B88":
					placeText(col[5], row4, "B88");
					break;
				case "C151":
					placeText(col[6], row4, "C151");
					break;
				case "C150":
					placeText(col[7], row4, "C150");
					break;
				case "D32":
					placeText(col[0], row5, "D32");
					break;
				case "D31":
					placeText(col[1], row5, "D31");
					break;
				case "A27":
					placeText(col[2], row5, "A27");
					break;
				case "A26":
					placeText(col[3], row5, "A26");
					break;
				case "B85":
					placeText(col[4], row5, "B85");
					break;
				case "B86":
					placeText(col[5], row5, "B86");
					break;
				case "C149":
					placeText(col[6], row5, "C149");
					break;
				case "C148":
					placeText(col[7], row5, "C148");
					break;
				case "D30":
					placeText(col[0], row6, "D30");
					break;
				case "D29":
					placeText(col[1], row6, "D29");
					break;
				case "A25":
					placeText(col[2], row6, "A25");
					break;
				case "A24":
					placeText(col[3], row6, "A24");
					break;
				case "B83":
					placeText(col[4], row6, "B83");
					break;
				case "B84":
					placeText(col[5], row6, "B84");
					break;
				case "C147":
					placeText(col[6], row6, "C147");
					break;
				case "C146":
					placeText(col[7], row6, "C146");
					break;
				case "D28":  // problem starts here?
					placeText(col[0], row7, "D28");
					break;
				case "D27":
					placeText(col[1], row7, "D27");
					break;
				case "A23":
					placeText(col[2], row7, "A23");
					break;
				case "A22":
					placeText(col[3], row7, "A22");
					break;
				case "B81":
					placeText(col[4], row7, "B81");
					break;
				case "B82":
					placeText(col[5], row7, "B82");
					break;
				case "C145":
					placeText(col[6], row7, "C145");
					break;
				case "C144":
					placeText(col[7], row7, "C144");
					break;
				case "D26":
					placeText(col[0], row8, "D26");
					break;
				case "D25":
					placeText(col[1], row8, "D25");
					break;
				case "A21":
					placeText(col[2], row8, "A21");
					break;
				case "A20":
					placeText(col[3], row8, "A20");
					break;
				case "B79":
					placeText(col[4], row8, "B79");
					break;
				case "B80":
					placeText(col[5], row8, "B80");
					break;
				case "C143":
					placeText(col[6], row8, "C143");
					break;
				case "C142":
					placeText(col[7], row8, "C142");
					break;
				case "D24":
					placeText(col[0], row9, "D24");
					break;
				case "D23":
					placeText(col[1], row9, "D23");
					break;
				case "A19":
					placeText(col[2], row9, "A19");
					break;
				case "A18":
					placeText(col[3], row9, "A18");
					break;
				case "B77":
					placeText(col[4], row9, "B77");
					break;
				case "B78":
					placeText(col[5], row9, "B78");
					break;
				case "C141":
					placeText(col[6], row9, "C141");
					break;
				case "C140":
					placeText(col[7], row9, "C140");
					break;
				case "D22":
					placeText(col[0], row10, "D22");
					break;
				case "D21":
					placeText(col[1], row10, "D21");
					break;
				case "A17":
					placeText(col[2], row10, "A17");
					break;
				case "A16":
					placeText(col[3], row10, "A16");
					break;
				case "B75":
					placeText(col[4], row10, "B75");
					break;
				case "B76":
					placeText(col[5], row10, "B76");
					break;
				case "C139":
					placeText(col[6], row10, "C139");
					break;
				case "C138":
					placeText(col[7], row10, "C138");
					break;
				case "D20":
					placeText(col[0], row11, "D20");
					break;
				case "D19":
					placeText(col[1], row11, "D19");
					break;
				case "A15":
					placeText(col[2], row11, "A15");
					break;
				case "A14":
					placeText(col[3], row11, "A14");
					break;
				case "B73":
					placeText(col[4], row11, "B73");
					break;
				case "B74":
					placeText(col[5], row11, "B74");
					break;
				case "C137":
					placeText(col[6], row11, "C137");
					break;
				case "C136":
					placeText(col[7], row11, "C136");
					break;
				case "D18":
					placeText(col[0], row12, "D18");
					break;
				case "D17":
					placeText(col[1], row12, "D17");
					break;
				case "A13":
					placeText(col[2], row12, "A13");
					break;
				case "A12":
					placeText(col[3], row12, "A12");
					break;
				case "B71":
					placeText(col[4], row12, "B71");
					break;
				case "B72":
					placeText(col[5], row12, "B72");
					break;
				case "C135":
					placeText(col[6], row12, "C135");
					break;
				case "C134":
					placeText(col[7], row12, "C134");
					break;
				case "D16":
					placeText(col[0], row13, "D16");
					break;
				case "D15":
					placeText(col[1], row13, "D15");
					break;
				case "A11":
					placeText(col[2], row13, "A11");
					break;
				case "A10":
					placeText(col[3], row13, "A10");
					break;
				case "B69":
					placeText(col[4], row13, "B69");
					break;
				case "B70":
					placeText(col[5], row13, "B70");
					break;
				case "C133":
					placeText(col[6], row13, "C133");
					break;
				case "C132":
					placeText(col[7], row13, "C132");
					break;
				case "D14":
					placeText(col[0], row14, "D14");
					break;
				case "D13":
					placeText(col[1], row14, "D13");
					break;
				case "A09":
					placeText(col[3], row14, "A09");
					break;
				case "B67":
					placeText(col[4], row14, "B67");
					break;
				case "B68":
					placeText(col[5], row14, "B68");
					break;
				case "C131":
					placeText(col[6], row14, "C131");
					break;
				case "C130":
					placeText(col[7], row14, "C130");
					break;
				case "D12":
					placeText(col[0], row15, "D12");
					break;
				case "D11":
					placeText(col[1], row15, "D11");
					break;
				case "A08":
					placeText(col[3], row15, "A08");
					break;
				case "B65":
					placeText(col[4], row15, "B65");
					break;
				case "B66":
					placeText(col[5], row15, "B66");
					break;
				case "C129":
					placeText(col[6], row15, "C129");
					break;
				case "C128":
					placeText(col[7], row15, "C128");
					break;
				case "D10":
					placeText(col[0], row16, "D10");
					break;
				case "D09":
					placeText(col[1], row16, "D09");
					break;
				case "A07":
					placeText(col[3], row16, "A07");
					break;
				case "B63":
					placeText(col[4], row16, "B63");
					break;
				case "B64":
					placeText(col[5], row16, "B64");
					break;
				case "C127":
					placeText(col[6], row16, "C127");
					break;
				case "C126":
					placeText(col[7], row16, "C126");
					break;
				case "D08":
					placeText(col[0], row17, "D08");
					break;
				case "D07":
					placeText(col[1], row17, "D07");
					break;
				case "A06":
					placeText(col[3], row17, "A06");
					;
					break;
				case "B61":
					placeText(col[4], row17, "B61");
					break;
				case "B62":
					placeText(col[5], row17, "B62");
					break;
				case "C125":
					placeText(col[6], row17, "C125");
					break;
				case "C124":
					placeText(col[7], row17, "C124");
					break;
				case "D06":
					placeText(col[0], row18, "D06");
					break;
				case "D05":
					placeText(col[1], row18, "D05");
					break;
				case "A05":
					placeText(col[3], row18, "A05");
					break;
				case "B59":
					placeText(col[4], row18, "B59");
					break;
				case "B60":
					placeText(col[5], row18, "B60");
					break;
				case "C123":
					placeText(col[6], row18, "C123");
					break;
				case "C122":
					placeText(col[7], row18, "C122");
					break;
				case "D04":
					placeText(col[0], row19, "D04");
					break;
				case "D03":
					placeText(col[1], row19, "D03");
					break;
				case "A04":
					placeText(col[3], row19, "A04");
					break;
				case "B57":
					placeText(col[4], row19, "B57");
					break;
				case "B58":
					placeText(col[5], row19, "B58");
					break;
				case "C121":
					placeText(col[6], row19, "C121");
					break;
				case "C120":
					placeText(col[7], row19, "C120");
					break;
				case "D02":
					placeText(col[0], row20, "D02");
					break;
				case "D01":
					placeText(col[1], row20, "D01");
					break;
				case "A03":
					placeText(col[3], row20, "A03");
					break;
				case "B55":
					placeText(col[4], row20, "B55");
					break;
				case "B56":
					placeText(col[5], row20, "B56");
					break;
				case "C119":
					placeText(col[6], row20, "C119");
					break;
				case "C118":
					placeText(col[7], row20, "C118");
					break;
				case "A02":
					placeText(col[3], row21, "A02");
					break;
				case "B53":
					placeText(col[4], row21, "B53");
					break;
				case "B54":
					placeText(col[5], row21, "B54");
					break;
				case "C117":
					placeText(col[6], row21, "C117");
					break;
				case "C116":
					placeText(col[7], row21, "C116");
					break;
				case "A01":
					placeText(col[3], row22, "A01");
					break;
				case "B51":
					placeText(col[4], row22, "B51");
					break;
				case "B52":
					placeText(col[5], row22, "B52");
					break;
				case "C115":
					placeText(col[6], row22, "C115");
					break;
				case "C114":
					placeText(col[7], row22, "C114");
					break;
				case "B50":
					placeText(col[5], row23, "B50");
					break;
				case "B48":
					placeText(col[5], row24, "B48");
					break;
				case "F10":
					placeText(176, 511, "F10");
					break;
				case "F09":
					placeText(194, 529, "F09");
					break;
				case "F08":
					placeText(205, 540, "F08");
					break;
				case "F07":
					placeText(223, 558, "F07");
					break;
				case "F06":
					placeText(234, 569, "F06");
					break;
				case "F05":
					placeText(252, 587, "F05");
					break;
				case "F04":
					placeText(263, 598, "F04");
					break;
				case "F03":
					placeText(281, 616, "F03");
					break;
				case "F02":
					placeText(292, 627, "F02");
					break;
				case "F01":
					placeText(310, 645, "F01");
					break;
				default:
					break;
			}
		}
	}

	private void setRotation() {
		String fDocks[] = {"F10","F09","F08","F07","F06","F05","F04","F03","F02","F01"};
		for(String s: fDocks) {
			rotate45((int) slipsHash.get(s).getX(), (int) slipsHash.get(s).getY(), s);
		}
	}

	private void placeText(int col, int row, String slip) {
//		if(mem.getSubleaser() != 0) {  /// this slip is subleased
//			subleaserMemberships.add(SqlMembershipList.getMembershipFromList(mem.getSubleaser(), HalyardPaths.getYear()));
//			slipsHash.get(slip).setText(slip + " " + subleaserMemberships.get(subleaserMemberships.size() - 1).getLname());
//		} else {
//			slipsHash.get(slip).setText(slip + " " + mem.getLname());
//		}
//		setMouseListener(slipsHash.get(slip), mem.getMsid(), mem.getSubleaser());
		slipsHash.get(slip).setX(col);
		slipsHash.get(slip).setY(row);
		slipsHash.get(slip).setText(slip);
	}


	
	private void setMouseListener(Text text, int msid, int submsid) {
		Color color = (Color) text.getFill();
		if (color == Color.CORNFLOWERBLUE) {  // blue if it is a sublease
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
			if (e.getClickCount() == 2) {
				if (color == Color.CORNFLOWERBLUE) {
					// this is a sublease
					Launcher.launchTabFromSlips(submsid);
				} else {
					Launcher.launchTabFromSlips(msid);
				}
			}
		});
	}
}
