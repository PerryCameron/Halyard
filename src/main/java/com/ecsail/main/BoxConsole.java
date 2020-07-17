package com.ecsail.main;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class BoxConsole extends HBox {
	
	private static ScrollPane consoleScrollPane;
	static TextFlow consoleTextArea;
	
	public BoxConsole() {
		consoleScrollPane = new ScrollPane();
		consoleTextArea = new TextFlow();
		consoleScrollPane.setPrefSize(1024, 100);
		consoleScrollPane.setContent(consoleTextArea);
		setPrefWidth(970);
		setPadding(new Insets(5,5,15,5));
		getChildren().addAll(consoleScrollPane);
	}
	
	public static void setInfoLine(String line, String chosenColor) {
		ObservableList<Node> list = consoleTextArea.getChildren();
		Text thisLine = new Text(line);
		if(chosenColor.equals("orange")) {
		thisLine.setFill(Color.CHOCOLATE);	
		} else { 
		thisLine.setFill(Color.BLACK);
		}
		list.add(thisLine);
		list.add(new Text("\n"));
		consoleScrollPane.setVvalue(1.0);
	}
	
	public String setRegexColor(String line) {
		int count = 0;
		String regex2 = "\"([^\"]*)\"";  // matches any word surrounded by double quotes
		String regex3 = "'([^\"]*)'";  // matches any word surronded by single quotes
		String regex4 = ".[0-9]+,.*"; // matches any character followed by a any number of digits next to a comma
		String regex5 = "^[\\(].*";
		ObservableList<Node> consoleList = consoleTextArea.getChildren(); // this is displayed in the console
		ArrayList<Text> textList = new ArrayList<Text>(); // used to build text object
		List<String> splitList = new ArrayList<String>();
		Pattern regex = Pattern.compile("[^\\s\"']+|\"[^\"]*\"|'[^']*'");
		Matcher regexMatcher = regex.matcher(line);
		while (regexMatcher.find()) {
			splitList.add(regexMatcher.group());
		}
		for (String sl : splitList) {
			// System.out.println(sl);  // useful for testing
			switch (sl.toLowerCase()) {
			case "select":
			case "update":
			case "right":
			case "join":
			case "using":
			case "insert":
			case "from":
			case "is":
			case "where":
			case "delete":
			case "and":
			case "into":
			case "values":
			case "true":
			case "false":
			case "set":
				textList.add(new Text(sl.toUpperCase() + " ")); // adds return to each statement
				textList.get(count).setFill(Color.CORNFLOWERBLUE);
				break;
			default:
				boolean isBetweenDoubleQuotes = Pattern.matches(regex2, sl);
				boolean isBetweenSingleQuotes = Pattern.matches(regex3, sl);
				boolean isNumberNextToComma = Pattern.matches(regex4, sl);
				boolean stringStartsWithParenthesis = Pattern.matches(regex5, sl);
				if(isBetweenDoubleQuotes) {
					textList.add(new Text(sl + " "));
					textList.get(count).setFill(Color.CHOCOLATE);
				} else if (isBetweenSingleQuotes) {
					textList.add(new Text(sl + " "));
					textList.get(count).setFill(Color.CHOCOLATE);
				} else if (isNumberNextToComma) {  // ok have to do special stuff here
					if(stringStartsWithParenthesis) {
						sl = sl.substring(1);  // removes starting character if it is a parenthesis
						textList.add(new Text("("));
						consoleList.add(textList.get(count));
						count++;		
						//System.out.println("Starts with parenthesis");
					}
					String[] newSl = sl.split(","); // ok lets take out the commas and color the words
					for(String str : newSl) {
						textList.add(new Text(str));
						textList.get(count).setFill(Color.CHOCOLATE);
						consoleList.add(textList.get(count));
						count++;
						textList.add(new Text(","));
						consoleList.add(textList.get(count));
						count++;
					}
					textList.add(new Text("")); // just a filler so it doesn't screw count up
					//textList.get(count).setFill(Color.CHOCOLATE);
				} else {
					textList.add(new Text(sl + " "));
				}
			}
			consoleList.add(textList.get(count));
			consoleScrollPane.setVvalue(1.0);
			count++;
		}
		textList.add(new Text("\n"));  // adds return to each statement
		consoleList.add(textList.get(count)); // adds return to each statement
		//System.out.println(line);
		return line;
	}

}
