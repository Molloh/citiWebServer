package cn.edu.nju.polaris.entity;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue
    private Long id; // 用户id 自动生成

    @Column(name = "a_id")
    private Long companyId; // 公司id

    @Column(name = "username")
    private String userName;// 用户名 用户输入，会检测是否重复，同时作为登录名

    @Column(name = "password")
    private String password; // 密码

    @Column(name = "type")
    private String type; // 用户类型 两类：ADMIN / NORMAL

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", companyId='" + companyId + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
