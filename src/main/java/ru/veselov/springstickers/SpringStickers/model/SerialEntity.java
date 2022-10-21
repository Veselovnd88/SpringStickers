package ru.veselov.springstickers.SpringStickers.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "serial_num")
public class SerialEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "num")
    private String num;

    @Column(name="created_at")
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name="id", nullable = false)
    private LabelEntity label;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public LabelEntity getLabel() {
        return label;
    }

    public void setLabel(LabelEntity label) {
        this.label = label;
    }

}
