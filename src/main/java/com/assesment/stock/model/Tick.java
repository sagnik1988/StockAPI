package com.assesment.stock.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Tick implements Serializable {

    String instrument;
    Double price;
    Long timestamp;
}
