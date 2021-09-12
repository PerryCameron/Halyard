package com.ecsail.gui.tabs;

import com.ecsail.main.Paths;
import com.ecsail.pdf.PDF_SlipChart;
import com.ecsail.main.Launcher;
import com.ecsail.sql.Sql_SelectMembership;
import com.ecsail.structures.Object_MembershipList;

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

public class TabSlips extends Tab {

	//private ObservableList<Object_Slip> slips;
	private ObservableList<Object_MembershipList> slipmemberships;
	private ObservableList<Object_MembershipList> subleaserMemberships;
	
	private Text d40 = null ,d39 = null ,a35= null ,a34= null ,b93= null ,b94= null ,c157= null ,c156 = null;
	private Text d38 = null ,d37 = null ,a33= null ,a32= null ,b91= null ,b92= null ,c155= null ,c154 = null;
	private Text d36 = null ,d35 = null ,a31= null ,a30= null ,b89= null ,b90= null ,c153= null ,c152 = null;
	private Text d34 = null ,d33 = null ,a29= null ,a28= null ,b87= null ,b88= null ,c151= null ,c150 = null;
	private Text d32 = null ,d31 = null ,a27= null ,a26= null ,b85= null ,b86= null ,c149= null ,c148 = null;
	private Text d30 = null ,d29 = null ,a25= null ,a24= null ,b83= null ,b84= null ,c147= null ,c146 = null;
	private Text d28 = null ,d27 = null ,a23= null ,a22= null ,b81= null ,b82= null ,c145= null ,c144 = null;
	private Text d26 = null ,d25 = null ,a21= null ,a20= null ,b79= null ,b80= null ,c143= null ,c142 = null;
	private Text d24 = null ,d23 = null ,a19= null ,a18= null ,b77= null ,b78= null ,c141= null ,c140 = null;
	private Text d22 = null ,d21 = null ,a17= null ,a16= null ,b75= null ,b76= null ,c139= null ,c138 = null;
	private Text d20 = null ,d19 = null ,a15= null ,a14= null ,b73= null ,b74= null ,c137= null ,c136 = null;
	private Text d18 = null ,d17 = null ,a13= null ,a12= null ,b71= null ,b72= null ,c135= null ,c134 = null;
	private Text d16 = null ,d15 = null ,a11= null ,a10= null ,b69= null ,b70= null ,c133= null ,c132 = null;
	private Text d14 = null ,d13 = null ,a9= null  ,b67= null ,b68= null ,c131= null ,c130 = null;
	private Text d12 = null ,d11 = null ,a8= null  ,b65= null ,b66= null ,c129= null ,c128 = null;
	private Text d10 = null ,d9 = null ,a7= null  ,b63= null ,b64= null ,c127= null ,c126 = null;
	private Text d8 = null ,d7 = null ,a6= null  ,b61= null ,b62= null ,c125= null ,c124 = null;
	private Text d6 = null ,d5 = null ,a5= null  ,b59= null ,b60= null ,c123= null ,c122 = null;
	private Text d4 = null ,d3 = null ,a4= null  ,b57= null ,b58= null ,c121= null ,c120 = null;
	private Text d2 = null ,d1 = null ,a3= null  ,b55= null ,b56= null ,c119= null ,c118 = null;
	private Text a2= null  ,b53 = null ,b54= null ,c117= null ,c116 = null;
	private Text a1= null  ,b51 = null ,b52= null ,c115= null ,c114 = null;
	//private Text f6= null  ,f5 = null ,f4= null ,f3= null ,f2 = null,f1 = null;
	private Text f6= null  ,f5 = null ,f4= null ,f3= null; // ,f2 = null;
	private Text b50= null, b48= null, h482= null, h483= null, h484= null, h485 = null;
	
	private int col1 = 20;
	private int col2 = 125;
	private int col3 = 280;
	private int col4 = 385; //
	private int col5 = 540;
	private int col6 = 643; //
	private int col7 = 800;
	private int col8 = 902; //
	
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
	
	//Rotate rotatef1 = new Rotate();
	Rotate rotatef2 = new Rotate();
	Rotate rotatef3 = new Rotate();
	Rotate rotatef4 = new Rotate();
	Rotate rotatef5 = new Rotate();
	Rotate rotatef6 = new Rotate();
	
	public TabSlips(String text) {
		super(text);
		this.slipmemberships = Sql_SelectMembership.getSlipRoster(Paths.getYear());
		this.subleaserMemberships = FXCollections.observableArrayList();
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
		    	getStaticSlips();  // slips that don't change such as 48 hour docks
		    	screenPane.getChildren().add(addDocks(10,10,col1));
		    	screenPane.getChildren().add(addDocks(7,11,col3));
		    	screenPane.getChildren().add(addDocks(11,12,col5));
		    	screenPane.getChildren().add(addDocks(11,11,col7));
		    	screenPane.getChildren().add(createPdfButton);
		    	//addDocks(screenPane,10,10,20);
		    	addAllSlips(screenPane);
		        //System.out.println("Refreshing Slips");
		    	//setContent(createPdfButton);
		    }
		});
		
		createPdfButton.setOnAction((event) -> {
        	new PDF_SlipChart(Paths.getYear());
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
		
		rotatef2.setAngle(314);
		rotatef3.setAngle(314);
		rotatef4.setAngle(314);
		rotatef5.setAngle(314);
		rotatef6.setAngle(314);
		createPdfButton.setLayoutX(750);
		createPdfButton.setLayoutY(600);
		
		//////////////////  SET CONTENT ///////////////
		//addAllDocks(screenPane);
		//addAllSlips(screenPane);
		//// What is this?  //screenPane.getChildren().addAll(d4,d3,a-1,a-2,b57,b58,c121,c120);
		vboxGrey.getChildren().add(screenPane);
		vboxBlue.getChildren().add(vboxPink);
		vboxPink.getChildren().add(vboxGrey);
		setContent(vboxBlue);

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

	    // draw stem
	    rect = new Rectangle(start + dockWidth,23,stemWidth,y - 21);  // y position always starts at 23
	    rect.setFill(Color.BLACK);
	    rect.setStroke(Color.BLACK);
	    group.getChildren().addAll(rect,getOctagon(110.0,470.0,42.0));

	    // draw angled stem
	    int x = 130;
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
	
	private void addAllSlips(Pane screenPane) {
		screenPane.getChildren().addAll(d40,d39,a35,a34,b93,b94,c157,c156);
		screenPane.getChildren().addAll(d38,d37,a33,a32,b91,b92,c155,c154);
		screenPane.getChildren().addAll(d36,d35,a31,a30,b89,b90,c153,c152);
		screenPane.getChildren().addAll(d34,d33,a29,a28,b87,b88,c151,c150);
		screenPane.getChildren().addAll(d32,d31,a27,a26,b85,b86,c149,c148);
		screenPane.getChildren().addAll(d30,d29,a25,a24,b83,b84,c147,c146);
		screenPane.getChildren().addAll(d28,d27,a23,a22,b81,b82,c145,c144);
		screenPane.getChildren().addAll(d26,d25,a21,a20,b79,b80,c143,c142);
		screenPane.getChildren().addAll(d24,d23,a19,a18,b77,b78,c141,c140);
		screenPane.getChildren().addAll(d22,d21,a17,a16,b75,b76,c139,c138);
		screenPane.getChildren().addAll(d20,d19,a15,a14,b73,b74,c137,c136);
		screenPane.getChildren().addAll(d18,d17,a13,a12,b71,b72,c135,c134);
		screenPane.getChildren().addAll(d16,d15,a11,a10,b69,b70,c133,c132);
		screenPane.getChildren().addAll(d14,d13,a9,b67,b68,c131,c130);
		screenPane.getChildren().addAll(d12,d11,a8,b65,b66,c129,c128);
		screenPane.getChildren().addAll(d10,d9,a7,b63,b64,c127,c126);
		screenPane.getChildren().addAll(d8,d7,a6,b61,b62,c125,c124);
		screenPane.getChildren().addAll(d6,d5,a5,b59,b60,c123,c122);
		screenPane.getChildren().addAll(d4,d3,a4,b57,b58,c121,c120);
		screenPane.getChildren().addAll(d2,d1,a3,b55,b56,c119,c118);
		screenPane.getChildren().addAll(a2,b53,b54,c117,c116);
		screenPane.getChildren().addAll(a1,b51,b52,c115,c114);
		screenPane.getChildren().addAll(b48,b50);
		//screenPane.getChildren().addAll(f1,f2,f3,f4,f5,f6);
		screenPane.getChildren().addAll(f3,f4,f5,f6);
		screenPane.getChildren().addAll(h482,h483,h484,h485);
	}

	private void getStaticSlips() {
		b50 = new Text(col6,row23, "B50 Racing");
		b48 = new Text(col6,row24, "B48 Racing");
		h482 = new Text(252,576, "48 Hour dock");
		h482.setRotate(-45);
		h483 = new Text(271,596, "48 Hour dock");
		h483.setRotate(-45);
		h484 = new Text(281,605, "48 Hour dock");
		h484.setRotate(-45);
		h485 = new Text(299,624, "48 Hour dock");
		h485.setRotate(-45);
	}
	
	private void fillSlips() {
		for (Object_MembershipList mem : slipmemberships) {
			if (mem.getSlip() != null) {
				switch (mem.getSlip()) {
				case "D40":
					d40 = getLastName(col1, row1, "D40", mem.getLname(), mem.getSubleaser());
					setMouseListener(d40, mem.getMsid(), mem.getSubleaser());
					break;
				case "D39":
					d39 = getLastName(col2, row1, "D39", mem.getLname(), mem.getSubleaser());
					setMouseListener(d39, mem.getMsid(), mem.getSubleaser());
					break;
				case "A35":
					a35 = getLastName(col3, row1, "A35", mem.getLname(), mem.getSubleaser());
					setMouseListener(a35, mem.getMsid(), mem.getSubleaser());
					break;
				case "A34":
					a34 = getLastName(col4, row1, "A34", mem.getLname(), mem.getSubleaser());
					setMouseListener(a34, mem.getMsid(), mem.getSubleaser());
					break;
				case "B93": //
					b93 = getLastName(col5, row1, "B93", mem.getLname(), mem.getSubleaser());
					setMouseListener(b93, mem.getMsid(), mem.getSubleaser());
					break;
				case "B94":
					b94 = getLastName(col6, row1, "B94", mem.getLname(), mem.getSubleaser());
					setMouseListener(b94, mem.getMsid(), mem.getSubleaser());
					break;
				case "C157":
					c157 = getLastName(col7, row1, "C157", mem.getLname(), mem.getSubleaser());
					setMouseListener(c157, mem.getMsid(), mem.getSubleaser());
					break;
				case "C156":
					c156 = getLastName(col8, row1, "C156", mem.getLname(), mem.getSubleaser());
					setMouseListener(c156, mem.getMsid(), mem.getSubleaser());
					break;
				case "D38":
					d38 = getLastName(col1, row2, "D38", mem.getLname(), mem.getSubleaser());
					setMouseListener(d38, mem.getMsid(), mem.getSubleaser());
					break;
				case "D37":
					d37 = getLastName(col2, row2, "D37", mem.getLname(), mem.getSubleaser());
					setMouseListener(d37, mem.getMsid(), mem.getSubleaser());
					break;
				case "A33":
					a33 = getLastName(col3, row2, "A33", mem.getLname(), mem.getSubleaser());
					setMouseListener(a33, mem.getMsid(), mem.getSubleaser());
					break;
				case "A32":
					a32 = getLastName(col4, row2, "A32", mem.getLname(), mem.getSubleaser());
					setMouseListener(a32, mem.getMsid(), mem.getSubleaser());
					break;
				case "B91":
					b91 = getLastName(col5, row2, "B91", mem.getLname(), mem.getSubleaser());
					setMouseListener(b91, mem.getMsid(), mem.getSubleaser());
					break;
				case "B92":
					b92 = getLastName(col6, row2, "B92", mem.getLname(), mem.getSubleaser());
					setMouseListener(b92, mem.getMsid(), mem.getSubleaser());
					break;
				case "C155":
					c155 = getLastName(col7, row2, "C155", mem.getLname(), mem.getSubleaser());
					setMouseListener(c155, mem.getMsid(), mem.getSubleaser());
					break;
				case "C154":
					c154 = getLastName(col8, row2, "C154", mem.getLname(), mem.getSubleaser());
					setMouseListener(c154, mem.getMsid(), mem.getSubleaser());
					break;
				case "D36":
					d36 = getLastName(col1, row3, "D36", mem.getLname(), mem.getSubleaser());
					setMouseListener(d36, mem.getMsid(), mem.getSubleaser());
					break;
				case "D35":
					d35 = getLastName(col2, row3, "D35", mem.getLname(), mem.getSubleaser());
					setMouseListener(d35, mem.getMsid(), mem.getSubleaser());
					break;
				case "A31":
					a31 = getLastName(col3, row3, "A31", mem.getLname(), mem.getSubleaser());
					setMouseListener(a31, mem.getMsid(), mem.getSubleaser());
					break;
				case "A30":
					a30 = getLastName(col4, row3, "A30", mem.getLname(), mem.getSubleaser());
					setMouseListener(a30, mem.getMsid(), mem.getSubleaser());
					break;
				case "B89":
					b89 = getLastName(col5, row3, "B89", mem.getLname(), mem.getSubleaser());
					setMouseListener(b89, mem.getMsid(), mem.getSubleaser());
					break;
				case "B90":
					b90 = getLastName(col6, row3, "B90", mem.getLname(), mem.getSubleaser());
					setMouseListener(b90, mem.getMsid(), mem.getSubleaser());
					break;
				case "C153":
					c153 = getLastName(col7, row3, "C153", mem.getLname(), mem.getSubleaser());
					setMouseListener(c153, mem.getMsid(), mem.getSubleaser());
					break;
				case "C152":
					c152 = getLastName(col8, row3, "C152", mem.getLname(), mem.getSubleaser());
					setMouseListener(c152, mem.getMsid(), mem.getSubleaser());
					break;
				case "D34":
					d34 = getLastName(col1, row4, "D34", mem.getLname(), mem.getSubleaser());
					setMouseListener(d34, mem.getMsid(), mem.getSubleaser());
					break;
				case "D33":
					d33 = getLastName(col2, row4, "D33", mem.getLname(), mem.getSubleaser());
					setMouseListener(d33, mem.getMsid(), mem.getSubleaser());
					break;
				case "A29":
					a29 = getLastName(col3, row4, "A29", mem.getLname(), mem.getSubleaser());
					setMouseListener(a29, mem.getMsid(), mem.getSubleaser());
					break;
				case "A28":
					a28 = getLastName(col4, row4, "A28", mem.getLname(), mem.getSubleaser());
					setMouseListener(a28, mem.getMsid(), mem.getSubleaser());
					break;
				case "B87":
					b87 = getLastName(col5, row4, "B87", mem.getLname(), mem.getSubleaser());
					setMouseListener(b87, mem.getMsid(), mem.getSubleaser());
					break;
				case "B88":
					b88 = getLastName(col6, row4, "B88", mem.getLname(), mem.getSubleaser());
					setMouseListener(b88, mem.getMsid(), mem.getSubleaser());
					break;
				case "C151":
					c151 = getLastName(col7, row4, "C151", mem.getLname(), mem.getSubleaser());
					setMouseListener(c151, mem.getMsid(), mem.getSubleaser());
					break;
				case "C150":
					c150 = getLastName(col8, row4, "C150", mem.getLname(), mem.getSubleaser());
					setMouseListener(c150, mem.getMsid(), mem.getSubleaser());
					break;
				case "D32":
					d32 = getLastName(col1, row5, "D32", mem.getLname(), mem.getSubleaser());
					setMouseListener(d32, mem.getMsid(), mem.getSubleaser());
					break;
				case "D31":
					d31 = getLastName(col2, row5, "D31", mem.getLname(), mem.getSubleaser());
					setMouseListener(d31, mem.getMsid(), mem.getSubleaser());
					break;
				case "A27":
					a27 = getLastName(col3, row5, "A27", mem.getLname(), mem.getSubleaser());
					setMouseListener(a27, mem.getMsid(), mem.getSubleaser());
					break;
				case "A26":
					a26 = getLastName(col4, row5, "A26", mem.getLname(), mem.getSubleaser());
					setMouseListener(a26, mem.getMsid(), mem.getSubleaser());
					break;
				case "B85":
					b85 = getLastName(col5, row5, "B85", mem.getLname(), mem.getSubleaser());
					setMouseListener(b85, mem.getMsid(), mem.getSubleaser());
					break;
				case "B86":
					b86 = getLastName(col6, row5, "B86", mem.getLname(), mem.getSubleaser());
					setMouseListener(b86, mem.getMsid(), mem.getSubleaser());
					break;
				case "C149":
					c149 = getLastName(col7, row5, "C149", mem.getLname(), mem.getSubleaser());
					setMouseListener(c149, mem.getMsid(), mem.getSubleaser());
					break;
				case "C148":
					c148 = getLastName(col8, row5, "C148", mem.getLname(), mem.getSubleaser());
					setMouseListener(c148, mem.getMsid(), mem.getSubleaser());
					break;
				case "D30":
					d30 = getLastName(col1, row6, "D30", mem.getLname(), mem.getSubleaser());
					setMouseListener(d30, mem.getMsid(), mem.getSubleaser());
					break;
				case "D29":
					d29 = getLastName(col2, row6, "D29", mem.getLname(), mem.getSubleaser());
					setMouseListener(d29, mem.getMsid(), mem.getSubleaser());
					break;
				case "A25":
					a25 = getLastName(col3, row6, "A25", mem.getLname(), mem.getSubleaser());
					setMouseListener(a25, mem.getMsid(), mem.getSubleaser());
					break;
				case "A24":
					a24 = getLastName(col4, row6, "A24", mem.getLname(), mem.getSubleaser());
					setMouseListener(a24, mem.getMsid(), mem.getSubleaser());
					break;
				case "B83":
					b83 = getLastName(col5, row6, "B83", mem.getLname(), mem.getSubleaser());
					setMouseListener(b83, mem.getMsid(), mem.getSubleaser());
					break;
				case "B84":
					b84 = getLastName(col6, row6, "B84", mem.getLname(), mem.getSubleaser());
					setMouseListener(b84, mem.getMsid(), mem.getSubleaser());
					break;
				case "C147":
					c147 = getLastName(col7, row6, "C147", mem.getLname(), mem.getSubleaser());
					setMouseListener(c147, mem.getMsid(), mem.getSubleaser());
					break;
				case "C146":
					c146 = getLastName(col8, row6, "C146", mem.getLname(), mem.getSubleaser());
					setMouseListener(c146, mem.getMsid(), mem.getSubleaser());
					break;
				case "D28":  // problem starts here?
					d28 = getLastName(col1, row7, "D28", mem.getLname(), mem.getSubleaser());
					setMouseListener(d28, mem.getMsid(), mem.getSubleaser());
					break;
				case "D27":
					d27 = getLastName(col2, row7, "D27", mem.getLname(), mem.getSubleaser());
					setMouseListener(d27, mem.getMsid(), mem.getSubleaser());
					break;
				case "A23":
					a23 = getLastName(col3, row7, "A23", mem.getLname(), mem.getSubleaser());
					setMouseListener(a23, mem.getMsid(), mem.getSubleaser());
					break;
				case "A22":
					a22 = getLastName(col4, row7, "A22", mem.getLname(), mem.getSubleaser());
					setMouseListener(a22, mem.getMsid(), mem.getSubleaser());
					break;
				case "B81":
					b81 = getLastName(col5, row7, "B81", mem.getLname(), mem.getSubleaser());
					setMouseListener(b81, mem.getMsid(), mem.getSubleaser());
					break;
				case "B82":
					b82 = getLastName(col6, row7, "B82", mem.getLname(), mem.getSubleaser());
					setMouseListener(b82, mem.getMsid(), mem.getSubleaser());
					break;
				case "C145":
					c145 = getLastName(col7, row7, "C145", mem.getLname(), mem.getSubleaser());
					setMouseListener(c145, mem.getMsid(), mem.getSubleaser());
					break;
				case "C144":
					c144 = getLastName(col8, row7, "C144", mem.getLname(), mem.getSubleaser());
					setMouseListener(c144, mem.getMsid(), mem.getSubleaser());
					break;
				case "D26":
					d26 = getLastName(col1, row8, "D26", mem.getLname(), mem.getSubleaser());
					setMouseListener(d26, mem.getMsid(), mem.getSubleaser());
					break;
				case "D25":
					d25 = getLastName(col2, row8, "D25", mem.getLname(), mem.getSubleaser());
					setMouseListener(d25, mem.getMsid(), mem.getSubleaser());
					break;
				case "A21":
					a21 = getLastName(col3, row8, "A21", mem.getLname(), mem.getSubleaser());
					setMouseListener(a21, mem.getMsid(), mem.getSubleaser());
					break;
				case "A20":
					a20 = getLastName(col4, row8, "A20", mem.getLname(), mem.getSubleaser());
					setMouseListener(a20, mem.getMsid(), mem.getSubleaser());
					break;
				case "B79":
					b79 = getLastName(col5, row8, "B79", mem.getLname(), mem.getSubleaser());
					setMouseListener(b79, mem.getMsid(), mem.getSubleaser());
					break;
				case "B80":
					b80 = getLastName(col6, row8, "B80", mem.getLname(), mem.getSubleaser());
					setMouseListener(b80, mem.getMsid(), mem.getSubleaser());
					break;
				case "C143":
					c143 = getLastName(col7, row8, "C143", mem.getLname(), mem.getSubleaser());
					setMouseListener(c143, mem.getMsid(), mem.getSubleaser());
					break;
				case "C142":
					c142 = getLastName(col8, row8, "C142", mem.getLname(), mem.getSubleaser());
					setMouseListener(c142, mem.getMsid(), mem.getSubleaser());
					break;
				case "D24":
					d24 = getLastName(col1, row9, "D24", mem.getLname(), mem.getSubleaser());
					setMouseListener(d24, mem.getMsid(), mem.getSubleaser());
					break;
				case "D23":
					d23 = getLastName(col2, row9, "D23", mem.getLname(), mem.getSubleaser());
					setMouseListener(d23, mem.getMsid(), mem.getSubleaser());
					break;
				case "A19":
					a19 = getLastName(col3, row9, "A19", mem.getLname(), mem.getSubleaser());
					setMouseListener(a19, mem.getMsid(), mem.getSubleaser());
					break;
				case "A18":
					a18 = getLastName(col4, row9, "A18", mem.getLname(), mem.getSubleaser());
					setMouseListener(a18, mem.getMsid(), mem.getSubleaser());
					break;
				case "B77":
					b77 = getLastName(col5, row9, "B77", mem.getLname(), mem.getSubleaser());
					setMouseListener(b77, mem.getMsid(), mem.getSubleaser());
					break;
				case "B78":
					b78 = getLastName(col6, row9, "B78", mem.getLname(), mem.getSubleaser());
					setMouseListener(b78, mem.getMsid(), mem.getSubleaser());
					break;
				case "C141":
					c141 = getLastName(col7, row9, "C141", mem.getLname(), mem.getSubleaser());
					setMouseListener(c141, mem.getMsid(), mem.getSubleaser());
					break;
				case "C140":
					c140 = getLastName(col8, row9, "C140", mem.getLname(), mem.getSubleaser());
					setMouseListener(c140, mem.getMsid(), mem.getSubleaser());
					break;
				case "D22":
					d22 = getLastName(col1, row10, "D22", mem.getLname(), mem.getSubleaser());
					setMouseListener(d22, mem.getMsid(), mem.getSubleaser());
					break;
				case "D21":
					d21 = getLastName(col2, row10, "D21", mem.getLname(), mem.getSubleaser());
					setMouseListener(d21, mem.getMsid(), mem.getSubleaser());
					break;
				case "A17":
					a17 = getLastName(col3, row10, "A17", mem.getLname(), mem.getSubleaser());
					setMouseListener(a17, mem.getMsid(), mem.getSubleaser());
					break;
				case "A16":
					a16 = getLastName(col4, row10, "A16", mem.getLname(), mem.getSubleaser());
					setMouseListener(a16, mem.getMsid(), mem.getSubleaser());
					break;
				case "B75":
					b75 = getLastName(col5, row10, "B75", mem.getLname(), mem.getSubleaser());
					setMouseListener(b75, mem.getMsid(), mem.getSubleaser());
					break;
				case "B76":
					b76 = getLastName(col6, row10, "B76", mem.getLname(), mem.getSubleaser());
					setMouseListener(b76, mem.getMsid(), mem.getSubleaser());
					break;
				case "C139":
					c139 = getLastName(col7, row10, "C139", mem.getLname(), mem.getSubleaser());
					setMouseListener(c139, mem.getMsid(), mem.getSubleaser());
					break;
				case "C138":
					c138 = getLastName(col8, row10, "C138", mem.getLname(), mem.getSubleaser());
					setMouseListener(c138, mem.getMsid(), mem.getSubleaser());
					break;
				case "D20":
					d20 = getLastName(col1, row11, "D20", mem.getLname(), mem.getSubleaser());
					setMouseListener(d20, mem.getMsid(), mem.getSubleaser());
					break;
				case "D19":
					d19 = getLastName(col2, row11, "D19", mem.getLname(), mem.getSubleaser());
					setMouseListener(d19, mem.getMsid(), mem.getSubleaser());
					break;
				case "A15":
					a15 = getLastName(col3, row11, "A15", mem.getLname(), mem.getSubleaser());
					setMouseListener(a15, mem.getMsid(), mem.getSubleaser());
					break;
				case "A14":
					a14 = getLastName(col4, row11, "A14", mem.getLname(), mem.getSubleaser());
					setMouseListener(a14, mem.getMsid(), mem.getSubleaser());
					break;
				case "B73":
					b73 = getLastName(col5, row11, "B73", mem.getLname(), mem.getSubleaser());
					setMouseListener(b73, mem.getMsid(), mem.getSubleaser());
					break;
				case "B74":
					b74 = getLastName(col6, row11, "B74", mem.getLname(), mem.getSubleaser());
					setMouseListener(b74, mem.getMsid(), mem.getSubleaser());
					break;
				case "C137":
					c137 = getLastName(col7, row11, "C137", mem.getLname(), mem.getSubleaser());
					setMouseListener(c137, mem.getMsid(), mem.getSubleaser());
					break;
				case "C136":
					c136 = getLastName(col8, row11, "C136", mem.getLname(), mem.getSubleaser());
					setMouseListener(c136, mem.getMsid(), mem.getSubleaser());
					break;
				case "D18":
					d18 = getLastName(col1, row12, "D18", mem.getLname(), mem.getSubleaser());
					setMouseListener(d18, mem.getMsid(), mem.getSubleaser());
					break;
				case "D17":
					d17 = getLastName(col2, row12, "D17", mem.getLname(), mem.getSubleaser());
					setMouseListener(d17, mem.getMsid(), mem.getSubleaser());
					break;
				case "A13":
					a13 = getLastName(col3, row12, "A13", mem.getLname(), mem.getSubleaser());
					setMouseListener(a13, mem.getMsid(), mem.getSubleaser());
					break;
				case "A12":
					a12 = getLastName(col4, row12, "A12", mem.getLname(), mem.getSubleaser());
					setMouseListener(a12, mem.getMsid(), mem.getSubleaser());
					break;
				case "B71":
					b71 = getLastName(col5, row12, "B71", mem.getLname(), mem.getSubleaser());
					setMouseListener(b71, mem.getMsid(), mem.getSubleaser());
					break;
				case "B72":
					b72 = getLastName(col6, row12, "B72", mem.getLname(), mem.getSubleaser());
					setMouseListener(b72, mem.getMsid(), mem.getSubleaser());
					break;
				case "C135":
					c135 = getLastName(col7, row12, "C135", mem.getLname(), mem.getSubleaser());
					setMouseListener(c135, mem.getMsid(), mem.getSubleaser());
					break;
				case "C134":
					c134 = getLastName(col8, row12, "C134", mem.getLname(), mem.getSubleaser());
					setMouseListener(c134, mem.getMsid(), mem.getSubleaser());
					break;
				case "D16":
					d16 = getLastName(col1, row13, "D16", mem.getLname(), mem.getSubleaser());
					setMouseListener(d16, mem.getMsid(), mem.getSubleaser());
					break;
				case "D15":
					d15 = getLastName(col2, row13, "D15", mem.getLname(), mem.getSubleaser());
					setMouseListener(d15, mem.getMsid(), mem.getSubleaser());
					break;
				case "A11":
					a11 = getLastName(col3, row13, "A11", mem.getLname(), mem.getSubleaser());
					setMouseListener(a11, mem.getMsid(), mem.getSubleaser());
					break;
				case "A10":
					a10 = getLastName(col4, row13, "A10", mem.getLname(), mem.getSubleaser());
					setMouseListener(a10, mem.getMsid(), mem.getSubleaser());
					break;
				case "B69":
					b69 = getLastName(col5, row13, "B69", mem.getLname(), mem.getSubleaser());
					setMouseListener(b69, mem.getMsid(), mem.getSubleaser());
					break;
				case "B70":
					b70 = getLastName(col6, row13, "B70", mem.getLname(), mem.getSubleaser());
					setMouseListener(b70, mem.getMsid(), mem.getSubleaser());
					break;
				case "C133":
					c133 = getLastName(col7, row13, "C133", mem.getLname(), mem.getSubleaser());
					setMouseListener(c133, mem.getMsid(), mem.getSubleaser());
					break;
				case "C132":
					c132 = getLastName(col8, row13, "C132", mem.getLname(), mem.getSubleaser());
					setMouseListener(c132, mem.getMsid(), mem.getSubleaser());
					break;
				case "D14":
					d14 = getLastName(col1, row14, "D14", mem.getLname(), mem.getSubleaser());
					setMouseListener(d14, mem.getMsid(), mem.getSubleaser());
					break;
				case "D13":
					d13 = getLastName(col2, row14, "D13", mem.getLname(), mem.getSubleaser());
					setMouseListener(d13, mem.getMsid(), mem.getSubleaser());
					break;
				case "A09":
					a9 = getLastName(col4, row14, "A09", mem.getLname(), mem.getSubleaser());
					setMouseListener(a9, mem.getMsid(), mem.getSubleaser());
					break;
				case "B67":
					b67 = getLastName(col5, row14, "B67", mem.getLname(), mem.getSubleaser());
					setMouseListener(b67, mem.getMsid(), mem.getSubleaser());
					break;
				case "B68":
					b68 = getLastName(col6, row14, "B68", mem.getLname(), mem.getSubleaser());
					setMouseListener(b68, mem.getMsid(), mem.getSubleaser());
					break;
				case "C131":
					c131 = getLastName(col7, row14, "C131", mem.getLname(), mem.getSubleaser());
					setMouseListener(c131, mem.getMsid(), mem.getSubleaser());
					break;
				case "C130":
					c130 = getLastName(col8, row14, "C130", mem.getLname(), mem.getSubleaser());
					setMouseListener(c130, mem.getMsid(), mem.getSubleaser());
					break;
				case "D12":
					d12 = getLastName(col1, row15, "D12", mem.getLname(), mem.getSubleaser());
					setMouseListener(d12, mem.getMsid(), mem.getSubleaser());
					break;
				case "D11":
					d11 = getLastName(col2, row15, "D11", mem.getLname(), mem.getSubleaser());
					setMouseListener(d11, mem.getMsid(), mem.getSubleaser());
					break;
				case "A08":
					a8 = getLastName(col4, row15, "A08", mem.getLname(), mem.getSubleaser());
					setMouseListener(a8, mem.getMsid(), mem.getSubleaser());
					break;
				case "B65":
					b65 = getLastName(col5, row15, "B65", mem.getLname(), mem.getSubleaser());
					setMouseListener(b65, mem.getMsid(), mem.getSubleaser());
					break;
				case "B66":
					b66 = getLastName(col6, row15, "B66", mem.getLname(), mem.getSubleaser());
					setMouseListener(b66, mem.getMsid(), mem.getSubleaser());
					break;
				case "C129":
					c129 = getLastName(col7, row15, "C129", mem.getLname(), mem.getSubleaser());
					setMouseListener(c129, mem.getMsid(), mem.getSubleaser());
					break;
				case "C128":
					c128 = getLastName(col8, row15, "C128", mem.getLname(), mem.getSubleaser());
					setMouseListener(c128, mem.getMsid(), mem.getSubleaser());
					break;
				case "D10":
					d10 = getLastName(col1, row16, "D10", mem.getLname(), mem.getSubleaser());
					setMouseListener(d10, mem.getMsid(), mem.getSubleaser());
					break;
				case "D09":
					d9 = getLastName(col2, row16, "D09", mem.getLname(), mem.getSubleaser());
					setMouseListener(d9, mem.getMsid(), mem.getSubleaser());
					break;
				case "A07":
					a7 = getLastName(col4, row16, "A07", mem.getLname(), mem.getSubleaser());
					setMouseListener(a7, mem.getMsid(), mem.getSubleaser());
					break;
				case "B63":
					b63 = getLastName(col5, row16, "B63", mem.getLname(), mem.getSubleaser());
					setMouseListener(b63, mem.getMsid(), mem.getSubleaser());
					break;
				case "B64":
					b64 = getLastName(col6, row16, "B64", mem.getLname(), mem.getSubleaser());
					setMouseListener(b64, mem.getMsid(), mem.getSubleaser());
					break;
				case "C127":
					c127 = getLastName(col7, row16, "C127", mem.getLname(), mem.getSubleaser());
					setMouseListener(c127, mem.getMsid(), mem.getSubleaser());
					break;
				case "C126":
					c126 = getLastName(col8, row16, "C126", mem.getLname(), mem.getSubleaser());
					setMouseListener(c126, mem.getMsid(), mem.getSubleaser());
					break;	
				case "D08":
					d8 = getLastName(col1, row17, "D08", mem.getLname(), mem.getSubleaser());
					setMouseListener(d8, mem.getMsid(), mem.getSubleaser());
					break;
				case "D07":
					d7 = getLastName(col2, row17, "D07", mem.getLname(), mem.getSubleaser());
					setMouseListener(d7, mem.getMsid(), mem.getSubleaser());
					break;
				case "A06":
					a6 = getLastName(col4, row17, "A06", mem.getLname(), mem.getSubleaser());
					setMouseListener(a6, mem.getMsid(), mem.getSubleaser());
					break;
				case "B61":
					b61 = getLastName(col5, row17, "B61", mem.getLname(), mem.getSubleaser());
					setMouseListener(b61, mem.getMsid(), mem.getSubleaser());
					break;
				case "B62":
					b62 = getLastName(col6, row17, "B62", mem.getLname(), mem.getSubleaser());
					setMouseListener(b62, mem.getMsid(), mem.getSubleaser());
					break;
				case "C125":
					c125 = getLastName(col7, row17, "C125", mem.getLname(), mem.getSubleaser());
					setMouseListener(c125, mem.getMsid(), mem.getSubleaser());
					break;
				case "C124":
					c124 = getLastName(col8, row17, "C124", mem.getLname(), mem.getSubleaser());
					setMouseListener(c124, mem.getMsid(), mem.getSubleaser());
					break;
				case "D06":
					d6 = getLastName(col1, row18, "D06", mem.getLname(), mem.getSubleaser());
					setMouseListener(d6, mem.getMsid(), mem.getSubleaser());
					break;
				case "D05":
					d5 = getLastName(col2, row18, "D05", mem.getLname(), mem.getSubleaser());
					setMouseListener(d5, mem.getMsid(), mem.getSubleaser());
					break;
				case "A05":
					a5 = getLastName(col4, row18, "A05", mem.getLname(), mem.getSubleaser());
					setMouseListener(a5, mem.getMsid(), mem.getSubleaser());
					break;
				case "B59":
					b59 = getLastName(col5, row18, "B59", mem.getLname(), mem.getSubleaser());
					setMouseListener(b59, mem.getMsid(), mem.getSubleaser());
					break;
				case "B60":
					b60 = getLastName(col6, row18, "B60", mem.getLname(), mem.getSubleaser());
					setMouseListener(b60, mem.getMsid(), mem.getSubleaser());
					break;
				case "C123":
					c123 = getLastName(col7, row18, "C123", mem.getLname(), mem.getSubleaser());
					setMouseListener(c123, mem.getMsid(), mem.getSubleaser());
					break;
				case "C122":
					c122 = getLastName(col8, row18, "C122", mem.getLname(), mem.getSubleaser());
					setMouseListener(c122, mem.getMsid(), mem.getSubleaser());
					break;
				case "D04":
					d4 = getLastName(col1, row19, "D04", mem.getLname(), mem.getSubleaser());
					setMouseListener(d4, mem.getMsid(), mem.getSubleaser());
					break;
				case "D03":
					d3 = getLastName(col2, row19, "D03", mem.getLname(), mem.getSubleaser());
					setMouseListener(d3, mem.getMsid(), mem.getSubleaser());
					break;
				case "A04":
					a4 = getLastName(col4, row19, "A04", mem.getLname(), mem.getSubleaser());
					setMouseListener(a4, mem.getMsid(), mem.getSubleaser());
					break;
				case "B57":
					b57 = getLastName(col5, row19, "B57", mem.getLname(), mem.getSubleaser());
					setMouseListener(b57, mem.getMsid(), mem.getSubleaser());
					break;
				case "B58":
					b58 = getLastName(col6, row19, "B58", mem.getLname(), mem.getSubleaser());
					setMouseListener(b58, mem.getMsid(), mem.getSubleaser());
					break;
				case "C121":
					c121 = getLastName(col7, row19, "C121", mem.getLname(), mem.getSubleaser());
					setMouseListener(c121, mem.getMsid(), mem.getSubleaser());
					break;
				case "C120":
					c120 = getLastName(col8, row19, "C120", mem.getLname(), mem.getSubleaser());
					setMouseListener(c120, mem.getMsid(), mem.getSubleaser());
					break;
				case "D02":
					d2 = getLastName(col1, row20, "D02", mem.getLname(), mem.getSubleaser());
					setMouseListener(d2, mem.getMsid(), mem.getSubleaser());
					break;
				case "D01":
					d1 = getLastName(col2, row20, "D01", mem.getLname(), mem.getSubleaser());
					setMouseListener(d1, mem.getMsid(), mem.getSubleaser());
					break;
				case "A03":
					a3 = getLastName(col4, row20, "A03", mem.getLname(), mem.getSubleaser());
					setMouseListener(a3, mem.getMsid(), mem.getSubleaser());
					break;
				case "B55":
					b55 = getLastName(col5, row20, "B55", mem.getLname(), mem.getSubleaser());
					setMouseListener(b55, mem.getMsid(), mem.getSubleaser());
					break;
				case "B56":
					b56 = getLastName(col6, row20, "B56", mem.getLname(), mem.getSubleaser());
					setMouseListener(b56, mem.getMsid(), mem.getSubleaser());
					break;
				case "C119":
					c119 = getLastName(col7, row20, "C119", mem.getLname(), mem.getSubleaser());
					setMouseListener(c119, mem.getMsid(), mem.getSubleaser());
					break;
				case "C118":
					c118 = getLastName(col8, row20, "C118", mem.getLname(), mem.getSubleaser());
					setMouseListener(c118, mem.getMsid(), mem.getSubleaser());
					break;
				case "A02":
					a2 = getLastName(col4, row21, "A02", mem.getLname(), mem.getSubleaser());
					setMouseListener(a2, mem.getMsid(), mem.getSubleaser());
					break;
				case "B53":
					b53 = getLastName(col5, row21, "B53", mem.getLname(), mem.getSubleaser());
					setMouseListener(b53, mem.getMsid(), mem.getSubleaser());
					break;
				case "B54":
					b54 = getLastName(col6, row21, "B54", mem.getLname(), mem.getSubleaser());
					setMouseListener(b54, mem.getMsid(), mem.getSubleaser());
					break;
				case "C117":
					c117 = getLastName(col7, row21, "C117", mem.getLname(), mem.getSubleaser());
					setMouseListener(c117, mem.getMsid(), mem.getSubleaser());
					break;
				case "C116":
					c116 = getLastName(col8, row21, "C116", mem.getLname(), mem.getSubleaser());
					setMouseListener(c116, mem.getMsid(), mem.getSubleaser());
					break;
				case "A01":
					a1 = getLastName(col4, row22, "A01", mem.getLname(), mem.getSubleaser());
					setMouseListener(a1, mem.getMsid(), mem.getSubleaser());
					break;
				case "B51":
					b51 = getLastName(col5, row22, "B51", mem.getLname(), mem.getSubleaser());
					setMouseListener(b51, mem.getMsid(), mem.getSubleaser());
					break;
				case "B52":
					b52 = getLastName(col6, row22, "B52", mem.getLname(), mem.getSubleaser());
					setMouseListener(b52, mem.getMsid(), mem.getSubleaser());
					break;
				case "C115":
					c115 = getLastName(col7, row22, "C115", mem.getLname(), mem.getSubleaser());
					setMouseListener(c115, mem.getMsid(), mem.getSubleaser());
					break;
				case "C114":
					c114 = getLastName(col8, row22, "C114", mem.getLname(), mem.getSubleaser());
					setMouseListener(c114, mem.getMsid(), mem.getSubleaser());
					break;	
				case "F06":
					rotatef6.setPivotX(176); 
				    rotatef6.setPivotY(511);
					f6 = getLastName(176,511, "F06", mem.getLname(), mem.getSubleaser());
					f6.getTransforms().addAll(rotatef6);
					setMouseListener(f6, mem.getMsid(), mem.getSubleaser());
					break;
				case "F05":
					rotatef5.setPivotX(194); 
				    rotatef5.setPivotY(530);
					f5 = getLastName(194,530, "F05", mem.getLname(), mem.getSubleaser());
					f5.getTransforms().addAll(rotatef5);
					setMouseListener(f5, mem.getMsid(), mem.getSubleaser());
					break;
				case "F04":
					rotatef4.setPivotX(205); 
				    rotatef4.setPivotY(541);
					f4 = getLastName(205,541, "F04", mem.getLname(), mem.getSubleaser());
					f4.getTransforms().addAll(rotatef4);
					setMouseListener(f4, mem.getMsid(), mem.getSubleaser());
					break;
				case "F03":
					rotatef3.setPivotX(223); 
				    rotatef3.setPivotY(559);
					f3 = getLastName(223,559, "F03", mem.getLname(), mem.getSubleaser());
					f3.getTransforms().addAll(rotatef3);
					setMouseListener(f3, mem.getMsid(), mem.getSubleaser());
					break;
			//	case "F02":
			//		rotatef2.setPivotX(234); 
			//	    rotatef2.setPivotY(570);
			//		f2 = getLastName(234,570, "F02", mem.getLname(), mem.getSubleaser());
			//		f2.getTransforms().addAll(rotatef2);
			//		setMouseListener(f2, mem.getMsid(), mem.getSubleaser());
			//		break;
			//	case "F01":
			//		rotatef1.setPivotX(252); 
			//	    rotatef1.setPivotY(588);
			//		f1 = getLastName(252,588, "F01", mem.getLname(), mem.getSubleaser());
			//		f1.getTransforms().addAll(rotatef1);
			//		setMouseListener(f1, mem.getMsid(), mem.getSubleaser());
			//		break;
				default:  
					break;
				}
			}
		}
	}
	
	//h485 = new Text(299,624, "48 Hour dock");
	//h485.setRotate(-45);

	private Text getLastName(int col, int row, String slip, String lName, int subleaser) {
		Text returnText = null;
		if(subleaser != 0) {  /// this slip is subleased
			//System.out.println("Found subleaser " + subleaser);
			subleaserMemberships.add(Sql_SelectMembership.getMembershipFromList(subleaser, Paths.getYear()));
			// gets the name of the subleaser
			returnText = new Text(col, row, slip + " " + subleaserMemberships.get(subleaserMemberships.size() - 1).getLname());
			returnText.setFill(Color.CORNFLOWERBLUE);
		} else {
			returnText = new Text(col, row, slip + " " + lName);
		}
		return returnText;  //new Text(col1, row1, "D40 " + mem.getLname());
	}
	
	private void setMouseListener(Text text, int msid, int submsid) {
		Color color = (Color) text.getFill();
		if(color == Color.CORNFLOWERBLUE) {  // blue if it is a sublease
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
					if(color == Color.CORNFLOWERBLUE) {
					// this is a sublease
					Launcher.launchTabFromSlips(submsid);
					} else {
					Launcher.launchTabFromSlips(msid);		
					}
			}
		});
	}
	
}
