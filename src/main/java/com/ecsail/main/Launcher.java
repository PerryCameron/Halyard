package com.ecsail.main;

import java.io.IOException;

import com.ecsail.gui.boxes.BoxWelcome;
import com.ecsail.gui.dialogues.Dialogue_EnvelopePDF;
import com.ecsail.gui.dialogues.Dialogue_RenewalForm;
import com.ecsail.gui.tabs.TabRoster;
import com.ecsail.gui.tabs.TabDeposits;
import com.ecsail.gui.tabs.TabLogin;
import com.ecsail.gui.tabs.TabBoardMembers;
import com.ecsail.gui.tabs.TabBoatView;
import com.ecsail.gui.tabs.TabBoats;
import com.ecsail.gui.tabs.TabDefinedFee;
import com.ecsail.gui.tabs.TabMembership;
import com.ecsail.gui.tabs.TabNewYearGenerator;
import com.ecsail.gui.tabs.TabNotes;
import com.ecsail.gui.tabs.TabPeople;
import com.ecsail.gui.tabs.TabSlips;
import com.ecsail.gui.tabs.TabStub;
import com.ecsail.gui.tabs.TabWelcome;
import com.ecsail.sql.Sql_SelectMembership;
import com.ecsail.sql.SqlSelect;
import com.ecsail.structures.Object_Boat;
import com.ecsail.structures.Object_MembershipList;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;

public class Launcher extends Pane {
static TabPane tabPane;

	public Launcher() { 
		tabPane = new TabPane();
		tabPane.setId("toolbar-box");
		getChildren().add(tabPane);
		tabPane.setPrefHeight(744);
		tabPane.setMaxHeight(744);
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
		System.out.println("Tab name is '" + tabName + "'");
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
		if(!tabOpen("Boats"))
		tabPane.getTabs().add(new TabBoats("Boats"));
		tabPane.getSelectionModel().select(getTabIndex("Boats"));
	}
	
	public static void openNotesTab() {
		if(!tabOpen("Notes"))
		tabPane.getTabs().add(new TabNotes("Notes"));
		tabPane.getSelectionModel().select(getTabIndex("Notes"));
	}
	
	public static void openPeopleTab() {
		if(!tabOpen("People"))
		tabPane.getTabs().add(new TabPeople("People"));
		tabPane.getSelectionModel().select(getTabIndex("People"));
	}
	
	public static void openFeeTab() {
		System.out.println("this is the defined fee tab");
		tabPane.getTabs().add(new TabDefinedFee("Fees"));
		tabPane.getSelectionModel().select(getTabIndex("Fees"));
	}
	
	public static void openSlipsTab() {
		if(!tabOpen("Slips"))
		tabPane.getTabs().add(new TabSlips("Slips"));
		tabPane.getSelectionModel().select(getTabIndex("Slips"));
	}
	
	public static void openRosterTab() {
		if(!tabOpen("Roster")) // is the tab already open??
		tabPane.getTabs().add(new TabRoster(Main.activememberships, Main.selectedYear));
		tabPane.getSelectionModel().select(getTabIndex("Roster"));
	}
	
	public static void openBoatViewTab(Object_Boat b) {
		if(!tabOpen("Boat"))
			tabPane.getTabs().add(new TabBoatView("Boat " + b.getBoat_id(), b));
			tabPane.getSelectionModel().select(getTabIndex("Boat " + b.getBoat_id()));
	}
	
	public static void openWelcomeTab(BoxWelcome boxWelcome) {
		tabPane.getTabs().add(new TabWelcome(boxWelcome));
	}
	
	public static void openLoginTab() {
		tabPane.getTabs().add(new TabLogin("Log in"));
	}
	
	public static void createRenewalForms() {
		new Dialogue_RenewalForm();
	}
	
	public static void openEnvelopesDialogue() {
		new Dialogue_EnvelopePDF();
	}
	
	public static void createMembershipTabFromPeopleList(int msid)  {
		Object_MembershipList membership = Sql_SelectMembership.getMembershipFromListWithoutMembershipId(msid);
		 if(!SqlSelect.isRenewed(msid, Paths.getYear()))
		 Launcher.createInactiveMemberTab(membership);
		 else
		 Launcher.createActiveMembershipTab(membership);
	}
	// used for TabRoster and CreateMembership
	public static void createMembershipTabForRoster(int membershipID, int ms_id)  {
		Object_MembershipList membership;
		membership = getMembership(ms_id);
		String tabLabel= "Membership " + membership.getMembershipId();
		if(!tabOpen(tabLabel)) // is the tab already open??
		tabPane.getTabs().add(new TabMembership(membership));
		tabPane.getSelectionModel().select(getTabIndex(tabLabel)); // focus on tab we are wanting
	}
	
	
	// used in BoxSlip
	public static void createTabForBoxSlip(int ms_id) { 
		Object_MembershipList membership;
		if(SqlSelect.isRenewed(ms_id, Paths.getYear())) { // membership is active and in our object tree
		membership = getMembership(ms_id);
		} else { // membership is not active and needs to be pulled from the SQL Database
		membership = Sql_SelectMembership.getMembershipFromList(ms_id,Paths.getYear());
		}	
		tabPane.getTabs().add(new TabMembership(membership));
	}
	
	// used for TabDeposits
	public static void createTabForDeposits(int ms_id, String year) {  // overload
		Object_MembershipList membership;
		membership = Sql_SelectMembership.getMembershipFromList(ms_id,year);
		String tabLabel= "Membership " + membership.getMembershipId();
		//System.out.println("Launcher: membership=" + membership.toString());
		if(!tabOpen(tabLabel)) // is the tab already open??
		tabPane.getTabs().add(new TabMembership(membership));
		tabPane.getSelectionModel().select(getTabIndex(tabLabel)); // focus on tab we are wanting
	}
	
	public static void launchTabFromSlips(int ms_id) {
		Object_MembershipList membership = Sql_SelectMembership.getMembershipList(ms_id, Paths.getYear());
		String tabLabel= "Membership " + membership.getMembershipId();
		if(!tabOpen(tabLabel)) // is the tab already open??
		tabPane.getTabs().add(new TabMembership(membership));
		tabPane.getSelectionModel().select(getTabIndex(tabLabel)); // focus on tab we are wanting
	}
	
	// fills incomplete object with latest information and opens tab.
	public static void createActiveMembershipTab(Object_MembershipList membership) {
		membership = Sql_SelectMembership.getMembershipFromList(membership.getMsid(), Paths.getYear());
		String tabLabel= "Membership " + membership.getMembershipId();
		if(!tabOpen(tabLabel)) 
		tabPane.getTabs().add(new TabMembership(membership));
		tabPane.getSelectionModel().select(getTabIndex(tabLabel)); // focus on tab we are wanting
	}
	
	public static void createInactiveMemberTab(Object_MembershipList membership) {
		String tabLabel= "MSID " + membership.getMsid();
		if(!tabOpen(tabLabel)) 
		tabPane.getTabs().add(new TabMembership(membership));
		tabPane.getSelectionModel().select(getTabIndex(tabLabel)); // focus on tab we are wanting
	}
	
	public static void createMembershipTabForBOD(int msid, String selectedYear) {
		Object_MembershipList membership = Sql_SelectMembership.getMembershipList(msid, selectedYear);
		String tabLabel= "Membership " + membership.getMembershipId();
		if(!tabOpen(tabLabel)) 
		tabPane.getTabs().add(new TabMembership(membership));
		tabPane.getSelectionModel().select(getTabIndex(tabLabel)); // focus on tab we are wanting
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

	public static TabPane getTabPane() {
		return tabPane;
	}

	public static void setTabPane(TabPane tabPane) {
		Launcher.tabPane = tabPane;
	}
}
