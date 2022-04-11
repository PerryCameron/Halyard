package com.ecsail.gui.tabs;

import com.ecsail.gui.jotform.HBoxNewMembershipRow;
import com.ecsail.jotform.JotForm;
import com.ecsail.sql.select.SqlApi_key;
import com.ecsail.structures.ApiKeyDTO;
import com.ecsail.structures.jotform.JotFormSubmissionListDTO;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class TabJotForm extends Tab {

	public TabJotForm(String text) {
		super(text);
		ApiKeyDTO thisApi = SqlApi_key.getApiKeyByName("Jotform API");
		JotForm client = new JotForm(thisApi.getKey());
		VBox vboxGrey = new VBox();  // this is the vbox for organizing all the widgets
		VBox vboxBlue = new VBox();
		VBox vboxPink = new VBox(); // this creates a pink border around the table
		ArrayList<JotFormSubmissionListDTO>  list = new ArrayList<>();
		vboxBlue.setId("box-blue");
		vboxBlue.setPadding(new Insets(10,10,10,10));
		vboxPink.setPadding(new Insets(3,3,3,3)); // spacing to make pink from around table
		vboxPink.setId("box-pink");
		VBox.setVgrow(vboxPink, Priority.ALWAYS);
		vboxGrey.setPrefHeight(688);
		Button listButton = new Button("List");

		listButton.setOnAction((event) -> {
			JSONObject submissions = client.getFormSubmissions(213494856046160L);
			ArrayList<JSONObject> formSubmissions = addFormSubmissionsIntoArray(submissions);
			for(JSONObject a: formSubmissions) {
				list.add(addSubmissionAnswersIntoDTO(a));
				vboxGrey.getChildren().add(new HBoxNewMembershipRow(addSubmissionAnswersIntoDTO(a)));
			}
		});

		vboxGrey.getChildren().add(listButton);
		vboxBlue.getChildren().add(vboxPink);
		vboxPink.getChildren().add(vboxGrey);
		setContent(vboxBlue);
	}

	private ArrayList<JSONObject> addFormSubmissionsIntoArray(JSONObject submissions) {
		ArrayList<JSONObject> formSubmissions = new ArrayList<>();
		JSONArray content = (JSONArray) submissions.get("content");
		for(int i =0; i < content.length(); i++) {
			formSubmissions.add((JSONObject) content.get(i));
		}
		return formSubmissions;
	}

	private static JotFormSubmissionListDTO addSubmissionAnswersIntoDTO(JSONObject formSubmission) {
		JotFormSubmissionListDTO jotFormSubmissionDTO = new JotFormSubmissionListDTO(false,false,"",
				String.valueOf(formSubmission.get("created_at")),
				String.valueOf(formSubmission.get("ip")),
				Long.parseLong((String) formSubmission.get("form_id")),
				String.valueOf(formSubmission.get("created_at")),
				Long.parseLong((String) formSubmission.get("id")),
				String.valueOf(formSubmission.get("status")),
				"","","","","","","","");
		JSONObject answerObject = (JSONObject) formSubmission.get("answers");
		boolean hasAnswer = false;
		for (Object key : answerObject.keySet()) {
			JSONObject x = (JSONObject) answerObject.get((String) key);
			for (Object xkey : x.keySet()) {
				if (xkey.equals("answer"))
					hasAnswer = true;
			}
			if (hasAnswer) {
				if(x.get("name").equals("membershipType")) {
					jotFormSubmissionDTO.setMemType(String.valueOf(x.get("answer")));
				} else if(x.get("name").equals("primaryMember")) {
					JSONObject name = (JSONObject) x.get("answer");
					jotFormSubmissionDTO.setPrimaryFirstName(String.valueOf(name.get("first")));
					jotFormSubmissionDTO.setPrimaryLastName(String.valueOf(name.get("last")));
				} else if (x.get("name").equals("address")) {
					JSONObject address = (JSONObject) x.get("answer");
					jotFormSubmissionDTO.setAddressLine1(String.valueOf(address.get("addr_line1")));
					jotFormSubmissionDTO.setCity(String.valueOf(address.get("city")));
					jotFormSubmissionDTO.setState(String.valueOf(address.get("state")));
					jotFormSubmissionDTO.setPostal(String.valueOf(address.get("postal")));
				}
				hasAnswer = false;
			}
		}
		System.out.println(jotFormSubmissionDTO);
		return jotFormSubmissionDTO;
	}
}
