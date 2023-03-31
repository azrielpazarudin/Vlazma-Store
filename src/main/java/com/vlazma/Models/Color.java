package com.vlazma.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Color {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column (name="id",nullable = false)
   private Integer id;

   @Column (name="color_name",length=255,nullable = false)
   private String colorName;

}
