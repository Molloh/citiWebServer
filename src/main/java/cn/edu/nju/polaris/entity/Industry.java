package cn.edu.nju.polaris.entity;

import javax.persistence.*;

@Entity
@Table(name = "industry")
public class Industry {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "first_industry")
    private String firstIndustry;

    @Column(name = "second_industry")
    private String secondIndustry;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Industry{" +
                "id=" + id +
                ", firstIndustry='" + firstIndustry + '\'' +
                ", secondIndustry='" + secondIndustry + '\'' +
                '}';
    }
}
