package ru.veselov.springstickers.springstickers.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class LabelDTO {

    @NotEmpty(message = "Should be not empty")
    @Size(min = 1,max = 16)
    private String article;
    @NotEmpty(message ="Should be not empty" )

    @Size(min=1,max = 25)
    private String name;
    @NotEmpty(message = "should be not empty")

    @Size(min=1,max = 25)
    private String range;

    @NotEmpty(message = "should be not empty")
    @Size(min=1,max = 25)
    private String pinout;

    @NotEmpty(message = "should be not empty")
    @Size(min=1,max=25)
    private String manufacturer;

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
}
