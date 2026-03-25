package com.h.collection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movies {
    private int id;
    private String name;
    private String actor;
    private double score;
}
