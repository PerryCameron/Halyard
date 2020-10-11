package com.ecsail.structures;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;


//SELECT m.commited, me.MEMBERSHIP_ID, p.l_name, p.f_name, m.TOTAL, m.CREDIT, m.PAID, m.BALANCE
//FROM money m
//INNER JOIN membership me on m.MS_ID=me.MS_ID
//INNER JOIN person p on me.P_ID=p.P_ID where m.FISCAL_YEAR=2020 and m.COMMITED=true;


public class Object_PaidDues extends Object_Money {
	private StringProperty f_name;
	private StringProperty l_name;
	private IntegerProperty membershipId;  // Member ID used in real life
	
	public Object_PaidDues(Integer money_id, Integer ms_id, Integer fiscal_year, Integer batch, Integer officer_credit,
			Integer extra_key, Integer kayac_shed_key, Integer sail_loft_key, Integer sail_school_loft_key,
			Integer beach, Integer wet_slip, Integer kayac_rack, Integer kayac_shed, Integer sail_loft,
			Integer sail_school_laser_loft, Integer winter_storage, Integer ysc_donation, Integer paid, Integer total,
			Integer credit, Integer balance, Integer dues, Boolean committed, Boolean closed, Integer other, Integer initiation, 
			Boolean supplemental, String f_name, String l_name, Integer membershipId) {
		
		super(money_id, ms_id, fiscal_year, batch, officer_credit, extra_key, kayac_shed_key, sail_loft_key,
				sail_school_loft_key, beach, wet_slip, kayac_rack, kayac_shed, sail_loft, sail_school_laser_loft,
				winter_storage, ysc_donation, paid, total, credit, balance, dues, committed, closed, other, initiation, supplemental);
		
		this.membershipId = new SimpleIntegerProperty(membershipId);
		this.f_name = new SimpleStringProperty(f_name);
		this.l_name = new SimpleStringProperty(l_name);
		// TODO Auto-generated constructor stub
	}

	public final IntegerProperty membershipIdProperty() {
		return this.membershipId;
	}
	

	public final int getMembershipId() {
		return this.membershipIdProperty().get();
	}

	public final void setMembershipId(final int membershipId) {
		this.membershipIdProperty().set(membershipId);
	}
	

	public final StringProperty f_nameProperty() {
		return this.f_name;
	}
	

	public final String getF_name() {
		return this.f_nameProperty().get();
	}
	

	public final void setF_name(final String f_name) {
		this.f_nameProperty().set(f_name);
	}
	

	public final StringProperty l_nameProperty() {
		return this.l_name;
	}
	

	public final String getL_name() {
		return this.l_nameProperty().get();
	}
	

	public final void setL_name(final String l_name) {
		this.l_nameProperty().set(l_name);
	}
	


	
}
