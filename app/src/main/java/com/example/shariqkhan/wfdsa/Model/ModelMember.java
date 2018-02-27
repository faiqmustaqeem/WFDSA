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
    public String title;
    public String designation;
    public String upload_image;
    public String firstname;
    private String wfdsa_title;
    private String region;

    public String getCompany_logo() {
        return company_logo;
    }

    public void setCompany_logo(String company_logo) {
        this.company_logo = company_logo;
    }

    public String getFlag_pic() {
        return flag_pic;
    }

    public void setFlag_pic(String flag_pic) {
        this.flag_pic = flag_pic;
    }

    public String company_logo;
    public String flag_pic;

    public String lastname;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getUpload_image() {
        return upload_image;
    }

    public void setUpload_image(String upload_image) {
        this.upload_image = upload_image;
    }

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

    public String getWfdsa_title() {
        return wfdsa_title;
    }

    public void setWfdsa_title(String wfdsa_title) {
        this.wfdsa_title = wfdsa_title;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
