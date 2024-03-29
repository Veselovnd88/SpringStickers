package ru.veselov.springstickers.springstickers.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "serial_num")
public class SerialEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int serial_id;

    @Column(name = "num")
    private String num;

    @Column(name="created_at")
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name="id", nullable = false)
    private LabelEntity label;

}
