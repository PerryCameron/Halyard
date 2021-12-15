package com.ecsail.main;

import com.ecsail.structures.Object_MembershipList;

public class EmailLinkCreator {


    final String url = "https://form.jotform.com/";
    final String formId = "213175957272058";
    final String memId = "?memid=";
    final String membershipType = "&membershipType=";
    final String fname = "&primaryMember[first]=";
    final String lname = "&primaryMember[last]=";
    final String address = "&address[addr_line1]=";
    final String city = "&address[city]=";
    final String state = "&address[city]=";
    final String zip = "&address[postal]=";
    final String email = "&primaryemail=";
    final String workCredit = "&workCredit=";
    final String winterStorage = "&winterStorage=";

    public EmailLinkCreator() {

    }

    public String createLink() {
        return url+formId;
    }

    public String createLinkData(Object_MembershipList ml) {
        String a = memId + ml.getMembershipId();
        String b = membershipType + ml.getMemType();
        String c = fname + ml.getFname();
        String d = lname + ml.getLname();
        String e = address + ml.getAddress();
        String f = city + ml.getCity();
        String g = state + ml.getState();
        String h = zip + ml.getZip();
        return a+b+c+d+e+f+g+h;
    }

}
