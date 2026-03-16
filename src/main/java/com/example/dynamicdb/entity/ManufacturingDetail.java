package com.example.dynamicdb.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "manufacturing_detail")
@Getter
@Setter
public class ManufacturingDetail extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(name = "part_id")
    private Part part;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @Column(name = "cost")
    private Double cost;
}