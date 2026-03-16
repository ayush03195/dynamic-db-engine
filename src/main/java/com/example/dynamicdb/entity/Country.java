package com.example.dynamicdb.entity;

import com.example.dynamicdb.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "country")
@Getter
@Setter
public class Country extends  BaseEntity{

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;
}
