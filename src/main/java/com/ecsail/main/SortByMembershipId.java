package com.ecsail.main;

import java.util.Comparator;

import com.ecsail.structures.Object_MembershipList;

public class SortByMembershipId implements Comparator<Object_MembershipList> {
	
	public int compare(Object_MembershipList a, Object_MembershipList b) {
		return a.getMembershipId() - b.getMembershipId();
	}
}
