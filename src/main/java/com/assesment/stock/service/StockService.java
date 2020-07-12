package com.assesment.stock.service;

import com.assesment.stock.dto.Statistics;
import com.assesment.stock.model.GlobalStatistics;
import com.assesment.stock.model.Tick;
import com.assesment.stock.repository.StockRepository;
import com.assesment.stock.util.DateTimeUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Service
public class StockService {

    @Autowired
    StockRepository stockRepository;

    /**
     *  Validates input Tick and afterwards persists the same in the system.
     *  Also updates the instrument specific and global statistics
     *
     * @param tick
     * @return boolean
     */
    public boolean saveStock(final Tick tick) {
        if (DateTimeUtil.isIfOlderThan60Seconds(tick.getTimestamp())) {
            return false;
        } else {
            int secondValue = DateTimeUtil.getTimefromTimeStamp(tick.getTimestamp()).getSecond();
            Statistics currentSpecificStat = new Statistics(tick);
            Statistics currentGlobalStat = new Statistics(tick);
            //stockRepository.mergeStat(secondValue, tick.getInstrument(), currentStat);
            stockRepository.mergeCompanySpecificStat(secondValue, tick.getInstrument(), currentSpecificStat);
            // global stat
            stockRepository.mergeGlobalStat(secondValue, currentGlobalStat);
            return true;
        }
    }

    /**
     * Returns the aggregated statistics for all ticks for a specific instruments which happened in last 60 seconds.
     *
     * @param companyName
     * @return GlobalStatistics
     */
    public GlobalStatistics getSpecificStatistics(String companyName) {
        return getStatistics(stockRepository.getSpecificEntrySet(companyName));
    }

    /**
     * Returns the aggregated statistics for all ticks across all instruments which happened in last 60 seconds.
     *
     * @return GlobalStatistics
     */
    public GlobalStatistics getGlobalStatistics() {
        return getStatistics(stockRepository.getEntrySet());
    }

    private GlobalStatistics getStatistics(Set<Map.Entry<Integer, Statistics>> entrySet) {
        LocalDateTime currentTime = DateTimeUtil.getCurrentDateAndTime();
        GlobalStatistics globalStat = new GlobalStatistics();
        if (entrySet != null) {
            entrySet.stream()
                    .filter(entry -> !DateTimeUtil.findIfDifferenceIsGreaterThan60Seconds(currentTime,DateTimeUtil.getTimefromTimeStamp(entry.getValue().getTimestamp())))
                    .forEach(entry -> {
                        globalStat.updateCounter(entry.getValue().getCount());
                        globalStat.updateSum(entry.getValue().getSum());
                        globalStat.updateMax(entry.getValue().getMax());
                        globalStat.updateMin(entry.getValue().getMin());
                    });
            globalStat.updateAvg();
        }
        return globalStat;
    }

}
