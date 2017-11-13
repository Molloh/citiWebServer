package cn.edu.nju.polaris.vo;

public class SubjectVO {

    public String subjectId;

    public String subjectName;

    public String direction;

    public String type;

    public SubjectVO(String subjectId, String subjectName, String direction, String type) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.direction = direction;
        this.type = type;
    }

    @Override
    public String toString() {
        return "SubjectVO{" +
                "subjectId='" + subjectId + '\'' +
                ", subjectName='" + subjectName + '\'' +
                ", direction='" + direction + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
