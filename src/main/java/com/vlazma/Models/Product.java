package com.vlazma.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="id",nullable=false)
  private Integer id;

  @Column(name="name",length=255,nullable = false)
  private String name;

  @Column(name="description",columnDefinition = "TEXT")
  private String description;

  @Column(name="price",nullable = false)
  private Integer price;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name="category_id",referencedColumnName = "id")
  private Category category;

}
