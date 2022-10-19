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


}
