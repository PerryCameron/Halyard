package com.ecsail.main;

import java.io.IOException;

import com.ecsail.gui.dialogues.Dialogue_EnvelopePDF;
import com.ecsail.gui.dialogues.Dialogue_RenewalForm;
import com.ecsail.gui.tabs.TabRoster;
import com.ecsail.gui.tabs.TabDeposits;
import com.ecsail.gui.tabs.TabBoardMembers;
import com.ecsail.gui.tabs.TabBoats;
import com.ecsail.gui.tabs.TabDefinedFee;
import com.ecsail.gui.tabs.TabMembership;
import com.ecsail.gui.tabs.TabNewYearGenerator;
import com.ecsail.gui.tabs.TabNotes;
import com.ecsail.gui.tabs.TabPeopleList;
import com.ecsail.gui.tabs.TabSlips;
import com.ecsail.gui.tabs.TabStub;
import com.ecsail.gui.tabs.TabWelcome;
import com.ecsail.structures.Object_MembershipList;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class TabLauncher extends Pane {
static TabPane tabPane;
//private ObservableList<Object_MembershipList> activememberships;

	public TabLauncher() { 
		tabPane = new TabPane();
		tabPane.setId("toolbar-box");
		getChildren().add(tabPane);
	}
	
	public static boolean tabOpen(String tabName) {
		boolean thisTab = false;
		for(Tab tab: tabPane.getTabs()) {
			if(tab.getText().equals(tabName))
				thisTab = true;
		}
		return thisTab;
	}
	
	public static void closeTab(String tabName) {
		for(Tab tab: tabPane.getTabs()) {
			if(tab.getText().equals(tabName))
				tabPane.getTabs().remove(tab);
		}
	}
	
	public static void closeActiveTab() {
		tabPane.getTabs().remove(tabPane.getSelectionModel().getSelectedIndex());
	}
	
	public static int getTabIndex(String tabName) {
		int result = 0;
		int count = 0;
		for(Tab tab: tabPane.getTabs()) {
			if(tab.getText().equals(tabName))
				result = count;
			count++;	
		}
		return result;
	}
	
	public static void openBoatsTab() {
		tabPane.getTabs().add(new TabBoats("Boats"));
		tabPane.getSelectionModel().select(getTabIndex("Boats"));
	}
	
	public static void openNotesTab() {
		tabPane.getTabs().add(new TabNotes("Notes"));
		tabPane.getSelectionModel().select(getTabIndex("Notes"));
	}
	
	public static void openPeopleTab() {
		tabPane.getTabs().add(new TabPeopleList("People List"));
		tabPane.getSelectionModel().select(getTabIndex("People List"));
	}
	
	public static void openFeeTab() {
		tabPane.getTabs().add(new TabDefinedFee("Fees"));
		tabPane.getSelectionModel().select(getTabIndex("Fees"));
	}
	
	public static void openSlipsTab() {
		tabPane.getTabs().add(new TabSlips("Slips",Main.activememberships));
		tabPane.getSelectionModel().select(getTabIndex("Slips"));
	}
	
	public static void openMembershipListTab() {
		if(!tabOpen("Roster")) // is the tab already open??
		tabPane.getTabs().add(new TabRoster(Main.activememberships, Main.selectedYear));
		tabPane.getSelectionModel().select(getTabIndex("Roster"));
	}
	
	public static void openWelcomeTab(VBox vboxGrey) {
		tabPane.getTabs().add(new TabWelcome(vboxGrey));
	}
	
	public static void createRenewalForms() {
		new Dialogue_RenewalForm();
	}
	
	public static void openEnvelopesDialogue() {
		new Dialogue_EnvelopePDF();
	}
	
	
	
	// creates a membership tab from the membership list
	public static void createTab(int membershipID, int ms_id)  {
		String tabLabel= "Membership " + membershipID;
		if(!tabOpen(tabLabel)) // is the tab already open??
			tabPane.getTabs().add(new TabMembership(getMembership(ms_id)));
		tabPane.getSelectionModel().select(getTabIndex(tabLabel)); // focus on tab we are wanting
	}
	
	public static void createTab(int ms_id) {  // overload
		Object_MembershipList membership;
		if(SqlSelect.isActive(ms_id)) { // membership is active and in our object tree
		membership = getMembership(ms_id);
		} else { // membership is not active and needs to be pulled from the SQL Database
		membership = SqlSelect.getMembershipFromList(ms_id,Paths.getYear());
		}	
		tabPane.getTabs().add(new TabMembership(membership));
	}
	
	public static void createTab(Object_MembershipList membership) {
		tabPane.getTabs().add(new TabMembership(membership));
	}
	
	public static void openBoardTab() {
		tabPane.getTabs().add(new TabBoardMembers("Board"));
		tabPane.getSelectionModel().select(getTabIndex("Board"));
	}
	
	public static void openTabStub() {
		tabPane.getTabs().add(new TabStub("Stub Tab"));
	}
	
	public static void openTabBatchedPaidDues() {
		tabPane.getTabs().add(new TabDeposits("Deposits"));
		tabPane.getSelectionModel().select(getTabIndex("Deposits"));
	}
	
	public static void openTabNewYearGenerator() {
		try {
			TabNewYearGenerator.makeItSo();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// gets a row with ms_id
	
	public static void removeMembershipRow(int ms_id) {
		int count = 0;
		int element = 0;
		for(Object_MembershipList mem: Main.activememberships) {
			if(mem.getMsid() == ms_id) element = count;
			count++;
		}
		Main.activememberships.remove(element);
	}

	
	// gets a specific membership with and ms_id
	public static Object_MembershipList getMembership(int ms_id) {
		Object_MembershipList membership = null;
		int element = 0;
		for(Object_MembershipList mem: Main.activememberships) {
			if(mem.getMsid() == ms_id) membership = Main.activememberships.get(element);
			element++;
		}
		return membership;
	}
	
	public static Object_MembershipList getSubleaser(int ms_id) {  // ms_id here is the subleasee
		Object_MembershipList membership = null;
		int element = 0;
		for(Object_MembershipList mem: Main.activememberships) {
			if(mem.getSubleaser() == ms_id) membership = Main.activememberships.get(element);
			element++;
		}
		return membership;  // returns membership of subleaser
	 }

}
