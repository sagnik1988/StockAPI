package com.assesment.stock.dto;

import com.assesment.stock.model.Tick;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Statistics {

    long timestamp;
    int count;
    double sum;
    double avg;
    double max;
    double min;

    public Statistics(Tick tick) {
        timestamp=tick.getTimestamp();
        count=1;
        sum=max=min=tick.getPrice();
    }

    public void incrementCounter() {
        count++;
    }

    public void updateCounter(int counter) {
        count+=counter;
    }

    public  void updateSum(double stockPrice) {
        sum+= stockPrice;
    }

    public void updateMax(double stockPrice) {
        max = Math.max(max, stockPrice);
    }

    public void updateMin(double stockPrice) {
        min = Math.min(min, stockPrice);
    }

    public void updateStat(Statistics oldStat) {
        updateCounter(oldStat.getCount());
        updateSum(oldStat.getSum());
        updateMax(oldStat.getMax());
        updateMin(oldStat.getMin());
    }
}
