package com.example.shariqkhan.wfdsa.Model;

/**
 * Created by Codiansoft on 12/15/2017.
 */

public class LeaderShipModel {
    String getRoleid;
    String getRoleName;

    public LeaderShipModel(String getRoleid, String getRoleName) {
        this.getRoleid = getRoleid;
        this.getRoleName = getRoleName;
    }
    public LeaderShipModel(){}

    public String getGetRoleid() {
        return getRoleid;
    }

    public void setGetRoleid(String getRoleid) {
        this.getRoleid = getRoleid;
    }

    public String getGetRoleName() {
        return getRoleName;
    }

    public void setGetRoleName(String getRoleName) {
        this.getRoleName = getRoleName;
    }
}
