package cn.edu.nju.polaris.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "subjects")
public class Subjects {

    @Id
    @Column(name = "subjects_id")
    private String subjectsId;

    @Column(name = "subjects_name")
    private String subjectsName;

    @Column(name = "direction")
    private String direction;

    @Column(name = "type")
    private String type;

    public String getSubjectsId() {
        return subjectsId;
    }

    public void setSubjectsId(String subjectsId) {
        this.subjectsId = subjectsId;
    }

    public String getSubjectsName() {
        return subjectsName;
    }

    public void setSubjectsName(String subjectsName) {
        this.subjectsName = subjectsName;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Subjects{" +
                "subjectsId='" + subjectsId + '\'' +
                ", subjectsName='" + subjectsName + '\'' +
                ", direction='" + direction + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
