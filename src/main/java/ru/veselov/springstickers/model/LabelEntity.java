package ru.veselov.springstickers.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "articles")
public class LabelEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty(message = "Should be not empty")
    @Column(name = "article")
    private String article;
    @NotEmpty(message ="Should be not empty" )
    @Column(name = "name")
    private String name;
    @NotEmpty(message = "should be not empty")
    @Column(name = "range")
    private String range;

    @Column(name="pinout")
    private String pinout;

    @Column(name = "manufacturer")
    private String manufacturer;

    public LabelEntity(String article, String name, String range, String pinout, String manufacturer) {
        this.article = article;
        this.name = name;
        this.range = range;
        this.pinout = pinout;
        this.manufacturer = manufacturer;
    }

    public LabelEntity() {
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getPinout() {
        return pinout;
    }

    public void setPinout(String pinout) {
        this.pinout = pinout;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public int getId(){
        return id;
    }
}
