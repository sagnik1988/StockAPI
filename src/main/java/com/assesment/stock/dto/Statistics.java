package com.assesment.stock.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.io.Serializable;
import java.text.DecimalFormat;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GlobalStatistics implements Serializable {
    int count;
    @JsonIgnore
    double sum;
    double avg;
    double max;
    double min;

    public void updateCounter(int counter) {
        count+=counter;
    }

    public  void updateSum(double stockPrice) {
        sum = Math.round((sum+stockPrice)*1e2)/1e2;
    }

    public void updateMax(double stockPrice) {
        if(max>0) {
            max = Math.max(max, stockPrice);
        } else {
            max = Math.max(Double.MIN_VALUE, stockPrice);
        }
        max = Math.round((max)*1e2)/1e2;
    }

    public void updateMin(double stockPrice) {
        if (min>0) {
            min = Math.min(min, stockPrice);
        } else {
            min = Math.min(Double.MAX_VALUE, stockPrice);
        }
        min = Math.round((min)*1e2)/1e2;
    }

    public void updateAvg() {
        avg = Math.round((sum/count)*1e2)/1e2;
    }
}
