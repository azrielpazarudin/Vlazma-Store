package com.vlazma.Utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ROBody {
    private String origin;
    private String destination;
    private int weight;
    private String courier;
}
