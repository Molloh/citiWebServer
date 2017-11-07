package cn.edu.nju.polaris.vo;

public class UserInfoVO {

    private Long companyId;

    private String userName;

    private String type;

    public UserInfoVO() {
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "UserInfoVO{" +
                "companyId=" + companyId +
                ", userName='" + userName + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
