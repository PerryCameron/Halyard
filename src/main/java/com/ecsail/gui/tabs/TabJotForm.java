package com.ecsail.gui.tabs;

import com.ecsail.jotform.JotForm;

import com.ecsail.sql.select.SqlApi_key;
import com.ecsail.structures.ApiKeyDTO;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TabJotForm extends Tab {

	public TabJotForm(String text) {
		super(text);
		ApiKeyDTO thisApi = SqlApi_key.getApiKeyByName("Jotform API");
		JotForm client = new JotForm(thisApi.getKey());
		VBox vboxGrey = new VBox();  // this is the vbox for organizing all the widgets
		VBox vboxBlue = new VBox();
		VBox vboxPink = new VBox(); // this creates a pink border around the table
		
		vboxBlue.setId("box-blue");
		vboxBlue.setPadding(new Insets(10,10,10,10));
		vboxPink.setPadding(new Insets(3,3,3,3)); // spacing to make pink from around table
		vboxPink.setId("box-pink");
		VBox.setVgrow(vboxPink, Priority.ALWAYS);
		vboxGrey.setPrefHeight(688);

		JSONObject formsResponse = client.getForms();

		try {
			JSONArray forms = formsResponse.getJSONArray("content");

			for (int i=0; i<forms.length(); i++){
				JSONObject form = forms.getJSONObject(i);

				System.out.println(form.get("title") + " (Total:" +form.get("count") + " New:" + form.get("new") + ")");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		vboxGrey.getChildren().add(new Label("Stubbed in Tab"));
		vboxBlue.getChildren().add(vboxPink);
		vboxPink.getChildren().add(vboxGrey);
		setContent(vboxBlue);
	}
}
