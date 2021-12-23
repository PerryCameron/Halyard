package com.ecsail.main;

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
import com.ecsail.gui.tabs.TabNotes;
import com.ecsail.gui.tabs.TabPeople;
import com.ecsail.gui.tabs.TabSlips;
import com.ecsail.gui.tabs.TabStub;
import com.ecsail.gui.tabs.TabWelcome;
import com.ecsail.pdf.PDF_BoatReport;
import com.ecsail.sql.select.SqlMembership;
import com.ecsail.sql.select.SqlMembership_Id;
import com.ecsail.structures.Object_Boat;
import com.ecsail.structures.Object_MembershipList;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;

public class Launcher extends VBox {
static TabPane tabPane;

	public static void closeTabs() {
		tabPane.getTabs().clear();
	}

	public Launcher() { 
		tabPane = new TabPane();
		tabPane.setId("toolbar-box");
		getChildren().add(tabPane);

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
		Object_MembershipList membership = SqlMembership.getMembershipFromListWithoutMembershipId(msid);
		 if(!SqlMembership_Id.isRenewed(msid, HalyardPaths.getYear()))
		 Launcher.createInactiveMemberTab(membership);
		 else
		 Launcher.createActiveMembershipTab(membership);
	}
	// used for TabRoster and CreateMembership
	public static void createMembershipTabForRoster(int membershipID, int ms_id)  {
		Object_MembershipList membership;
		membership = getMembership(ms_id);
		createOrOpenTab(membership, "Membership");
	}
	
	// used in BoxSlip
	public static void createTabForBoxSlip(int ms_id) { 
		Object_MembershipList membership;
		if(SqlMembership_Id.isRenewed(ms_id, HalyardPaths.getYear())) { // membership is active and in our object tree
		membership = getMembership(ms_id);
		} else { // membership is not active and needs to be pulled from the SQL Database
		membership = SqlMembership.getMembershipFromList(ms_id, HalyardPaths.getYear());
		}
		Tab membershipTab = new TabMembership(membership);
		tabPane.getTabs().add(membershipTab);
		tabPane.getSelectionModel().select(membershipTab); // focus on tab we are wanting
	}

	// used for TabDeposits
	public static void createTabForDeposits(int ms_id, String year) {  // overload
		Object_MembershipList membership;
		membership = SqlMembership.getMembershipFromList(ms_id, year);
		createOrOpenTab(membership, "Membership");
	}

	public static void launchTabFromSlips(int ms_id) {
		Object_MembershipList membership = SqlMembership.getMembershipList(ms_id, HalyardPaths.getYear());
		createOrOpenTab(membership, "Membership");
	}
	
	// fills incomplete object with latest information and opens tab.
	public static void createActiveMembershipTab(Object_MembershipList membership) {
		membership = SqlMembership.getMembershipFromList(membership.getMsid(), HalyardPaths.getYear());
		createOrOpenTab(membership, "Membership");
	}
	
	public static void createInactiveMemberTab(Object_MembershipList membership) {
		createOrOpenTab(membership, "MSID");
	}
	
	public static void createMembershipTabForBOD(int msid, String selectedYear) {
		Object_MembershipList membership = SqlMembership.getMembershipList(msid, selectedYear);
		createOrOpenTab(membership, "Membership");
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

	////////////////  UTILITY METHODS ///////////////////////

	private static void createOrOpenTab(Object_MembershipList membership, String label) {
		String tabLabel = "";
		if(label.equals("Membership")) {
			tabLabel = "Membership " + membership.getMembershipId();
		} else if(label.equals("MSID")) {
			tabLabel= "MSID " + membership.getMsid();
		}

		if (!tabOpen(tabLabel)) // is the tab already open??
		{
			Tab newTab = new TabMembership(membership);
			tabPane.getTabs().add(newTab);
			tabPane.getSelectionModel().select(newTab);
		} else
			tabPane.getSelectionModel().select(getTabIndex(tabLabel)); // focus on tab we are wanting
	}

	public static boolean tabOpen(String tabName) {
		boolean thisTab = false;
		for(Tab tab: tabPane.getTabs()) {
			if(tab.getText().equals(tabName))
				thisTab = true;
		}
		return thisTab;
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

	public static void closeTab(String tabName) {
		for(Tab tab: tabPane.getTabs()) {
			if(tab.getText().equals(tabName))
				tabPane.getTabs().remove(tab);
		}
	}

	public static void closeActiveTab() {
		tabPane.getTabs().remove(tabPane.getSelectionModel().getSelectedIndex());
	}

    public static void createBoatReport() {
		new PDF_BoatReport();
    }
}
