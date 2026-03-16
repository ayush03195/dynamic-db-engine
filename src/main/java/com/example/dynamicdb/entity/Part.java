package com.example.dynamicdb.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "part")
@Getter
@Setter
public class Part extends BaseEntity {

    @Column(name = "part_number")
    private String partNumber;

    @Column(name = "description")
    private String description;
}
