package com.ecsail.sql.select;

import com.ecsail.sql.SqlExists;
import com.ecsail.structures.Object_MembershipList;
import javafx.collections.ObservableList;

public class SqlMembershipList {
    /// not a pure SQL FUNCTION was having difficulties narrowing it down pure SQL.
    public static int getNumberOfReturningMembershipsForYear(int fiscalYear) {
        ObservableList<Object_MembershipList> rosters = SqlMembership.getReturnMembers(fiscalYear);
        int count = 0;
        for(Object_MembershipList r: rosters) {
            if(SqlExists.memberShipIdExists(r.getMsid(), ((fiscalYear - 1) + ""))) {
                if(!SqlMembership_Id.isActive(r.getMsid(), ((fiscalYear - 1) + "")));
                count++;
            } else {  // record doesn't exist
            count++;
            }
        }
        return count;
    }
}
