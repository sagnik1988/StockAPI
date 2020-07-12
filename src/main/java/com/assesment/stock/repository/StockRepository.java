package com.assesment.stock.repository;

import com.assesment.stock.dto.Statistics;
import com.assesment.stock.util.DateTimeUtil;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

@Repository
public class StockRepository {

    private static ConcurrentHashMap<Integer, Statistics> globalStatisticsRepository;

    private static ConcurrentHashMap<String, ConcurrentHashMap<Integer, Statistics>> specificStatisticsRepository;

    public StockRepository() {

        globalStatisticsRepository = new ConcurrentHashMap<Integer, Statistics>(60, .75f,60);
        specificStatisticsRepository = new ConcurrentHashMap<>();
    }

    /**
     * Merge the Statistics using merge() function of concurrentHashMap.
     * If the Key does exists then put the key and value in the map.
     * If the key exists then compare the timestamps of oldValue with newValue
     *      if similar update the stat
     *      if different the remove the oldVal and put the newVal
     */
    public void mergeStat(Integer seconds, String companyName, Statistics currentStat) {
        // company specific stat
        mergeCompanySpecificStat(seconds, companyName, currentStat);
        // global stat
        mergeGlobalStat(seconds, currentStat);
    }

    public void mergeCompanySpecificStat(Integer seconds, String companyName, Statistics currentStat) {
        ConcurrentHashMap<Integer, Statistics> tempStatisticsRepository = new ConcurrentHashMap<>();
        tempStatisticsRepository.put(seconds, currentStat);
        specificStatisticsRepository.merge(companyName, tempStatisticsRepository, (oldRepo, newRepo) -> {
            oldRepo.merge(seconds, currentStat, remappingFunction);
            return oldRepo;
        });
    }

    public void mergeGlobalStat(Integer seconds, Statistics currentStat) {
        globalStatisticsRepository.merge(seconds, currentStat, remappingFunction);
    }

    private BiFunction<Statistics, Statistics, Statistics> remappingFunction = (oldStat, newStat) -> {
        if (oldStat.getTimestamp() == newStat.getTimestamp()) {
            newStat.updateStat(oldStat);
        }
        return newStat;
    };

    public Set<Map.Entry<Integer, Statistics>> getSpecificEntrySet(String companyName) {
        if (specificStatisticsRepository.get(companyName) != null) {
            return specificStatisticsRepository.get(companyName).entrySet();
        } else {
            return null;
        }

    }

    public Set<Map.Entry<Integer, Statistics>> getEntrySet() {
        return globalStatisticsRepository.entrySet();
    }
}
