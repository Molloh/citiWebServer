package cn.edu.nju.polaris.vo;

import java.sql.Date;
import java.sql.Timestamp;

public class AccountVO {

    private Long id;

    private String companyName;

    private String location;

    private String scale;

    private Timestamp activeTime;

    private String firstIndustry;

    private String secondIndustry;

    private String email;

    public AccountVO() {
    }

    public AccountVO(Long id, String companyName, String location, String scale, Timestamp activeTime, String firstIndustry, String secondIndustry, String email) {
        this.id = id;
        this.companyName = companyName;
        this.location = location;
        this.scale = scale;
        this.activeTime = activeTime;
        this.firstIndustry = firstIndustry;
        this.secondIndustry = secondIndustry;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Timestamp getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(Timestamp activeTime) {
        this.activeTime = activeTime;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public String getFirstIndustry() {
        return firstIndustry;
    }

    public void setFirstIndustry(String firstIndustry) {
        this.firstIndustry = firstIndustry;
    }

    public String getSecondIndustry() {
        return secondIndustry;
    }

    public void setSecondIndustry(String secondIndustry) {
        this.secondIndustry = secondIndustry;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
