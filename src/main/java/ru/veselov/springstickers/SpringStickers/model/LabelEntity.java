package ru.veselov.springstickers.SpringStickers.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
@Data
@NoArgsConstructor
@Entity
@Table(name = "articles")
public class LabelEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty(message = "Should be not empty")
    @Column(name = "article")
    @Size(min = 1,max = 16)
    private String article;
    @NotEmpty(message ="Should be not empty" )
    @Column(name = "name")
    @Size(min=1,max = 25)
    private String name;
    @NotEmpty(message = "should be not empty")
    @Column(name = "range")
    @Size(min=1,max = 25)
    private String range;
    @NotEmpty(message = "should be not empty")
    @Column(name="pinout")
    @Size(min=1,max = 25)
    private String pinout;

    @NotEmpty(message = "should be not empty")
    @Column(name = "manufacturer")
    @Size(min=1,max=25)
    private String manufacturer;

    @OneToMany (mappedBy = "label")
    private Set<SerialEntity> serials;

    public LabelEntity(String article, String name, String range, String pinout, String manufacturer) {
        this.article = article;
        this.name = name;
        this.range = range;
        this.pinout = pinout;
        this.manufacturer = manufacturer;
    }

}
