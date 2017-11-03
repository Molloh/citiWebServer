package cn.edu.nju.polaris.entity;

import javax.persistence.*;

@Entity
@Table(name = "industry_index")
public class IndustryIndex {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "category")
    private String category;

    @Column(name = "first_industry")
    private String firstIndustry;

    @Column(name = "second_industry")
    private String secondIndustry;

    @Column(name = "scale")
    private String scale;

    @Column(name = "index_name")
    private String indexName;

    @Column(name = "excellent_index")
    private Double excellentIndex;

    @Column(name = "fine_index")
    private Double fineIndex;

    @Column(name = "average_index")
    private Double averageIndex;

    @Column(name = "low_index")
    private Double lowIndex;

    @Column(name = "bad_index")
    private Double badIndex;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public Double getExcellentIndex() {
        return excellentIndex;
    }

    public void setExcellentIndex(Double excellentIndex) {
        this.excellentIndex = excellentIndex;
    }

    public Double getFineIndex() {
        return fineIndex;
    }

    public void setFineIndex(Double fineIndex) {
        this.fineIndex = fineIndex;
    }

    public Double getAverageIndex() {
        return averageIndex;
    }

    public void setAverageIndex(Double averageIndex) {
        this.averageIndex = averageIndex;
    }

    public Double getLowIndex() {
        return lowIndex;
    }

    public void setLowIndex(Double lowIndex) {
        this.lowIndex = lowIndex;
    }

    public Double getBadIndex() {
        return badIndex;
    }

    public void setBadIndex(Double badIndex) {
        this.badIndex = badIndex;
    }

    @Override
    public String toString() {
        return "IndustryIndex{" +
                "id=" + id +
                ", category='" + category + '\'' +
                ", firstIndustry='" + firstIndustry + '\'' +
                ", secondIndustry='" + secondIndustry + '\'' +
                ", scale='" + scale + '\'' +
                ", indexName='" + indexName + '\'' +
                ", excellentIndex=" + excellentIndex +
                ", fineIndex=" + fineIndex +
                ", averageIndex=" + averageIndex +
                ", lowIndex=" + lowIndex +
                ", badIndex=" + badIndex +
                '}';
    }
}
