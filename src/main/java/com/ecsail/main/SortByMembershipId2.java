package com.ecsail.main;

import java.util.Comparator;

import com.ecsail.structures.Object_MembershipId;


public class SortByMembershipId2 implements Comparator<Object_MembershipId> {
	
	public int compare(Object_MembershipId a, Object_MembershipId b) {
		return Integer.parseInt(a.getMembership_id()) - Integer.parseInt(b.getMembership_id());
	}
}
