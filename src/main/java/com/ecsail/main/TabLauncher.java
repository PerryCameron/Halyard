package com.ecsail.main;

import com.ecsail.gui.tabs.TabActiveMembershipList;
import com.ecsail.gui.tabs.TabDeposits;
import com.ecsail.gui.tabs.TabBoardMembers;
import com.ecsail.gui.tabs.TabMembership;
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
	
	public static void openPeopleTab() {
		tabPane.getTabs().add(new TabPeopleList("People List"));
		tabPane.getSelectionModel().select(getTabIndex("People List"));
	}
	
	public static void openSlipsTab() {
		tabPane.getTabs().add(new TabSlips("Slips",Main.activememberships));
		tabPane.getSelectionModel().select(getTabIndex("Slips"));
	}
	
	public static void openMembershipListTab() {
		if(!tabOpen("Membership List (Active)")) // is the tab already open??
		tabPane.getTabs().add(new TabActiveMembershipList(Main.activememberships));
		tabPane.getSelectionModel().select(getTabIndex("Membership List (Active)"));
	}
	
	public static void openWelcomeTab(VBox vboxGrey) {
		tabPane.getTabs().add(new TabWelcome(vboxGrey));
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
		membership = SqlSelect.getMembershipFromList(ms_id);
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
	
	// creates a membership tab from the slip chart

	
	// creates a tab from anywhere a membership may or may not be active

	
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
