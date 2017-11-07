package cn.edu.nju.polaris.vo;

public class AccountInfoVO {

    private String location;

    private String scale;

    private String firstIndustry;

    private String secondIndustry;

    private String email;

    public AccountInfoVO() {
    }

    public AccountInfoVO(String location, String scale, String firstIndustry, String secondIndustry, String email) {
        this.location = location;
        this.scale = scale;
        this.firstIndustry = firstIndustry;
        this.secondIndustry = secondIndustry;
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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
