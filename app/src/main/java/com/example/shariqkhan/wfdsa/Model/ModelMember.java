package com.example.shariqkhan.wfdsa.Model;

/**
 * Created by ShariqKhan on 12/4/2017.
 */

public class ModelMember {
    public String companyName;
    public String companyAddress;
    public String memberEmail;
    public String memberName;
    public String memberPhone;
    public String memberFax;
    public String memberWeb;

    public String Country;

    public ModelMember() {
    }


    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getMemberEmail() {
        return memberEmail;
    }

    public void setMemberEmail(String memberEmail) {
        this.memberEmail = memberEmail;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberPhone() {
        return memberPhone;
    }

    public void setMemberPhone(String memberPhone) {
        this.memberPhone = memberPhone;
    }

    public String getMemberFax() {
        return memberFax;
    }

    public void setMemberFax(String memberFax) {
        this.memberFax = memberFax;
    }

    public String getMemberWeb() {
        return memberWeb;
    }

    public void setMemberWeb(String memberWeb) {
        this.memberWeb = memberWeb;
    }
}
