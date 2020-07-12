package com.assesment.stock.controller;

import com.assesment.stock.Exception.ApiException;
import com.assesment.stock.model.GlobalStatistics;
import com.assesment.stock.model.Tick;
import com.assesment.stock.service.StockService;
import com.assesment.stock.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class StockAPI {

    @Autowired
    StockService stockService;

    /**
     *  Validates input Tick and afterwards persists the same in the system.
     *  Also updates the instrument specific and global statistics
     *
     * @param tick
     * @return ResponseEntity
     */
    @PostMapping(value = "/tick")
    public ResponseEntity postTick(@RequestBody Tick tick) {

        // verify if the request has a instrument name populated in it.
        if(tick.getInstrument() == null || tick.getInstrument().isEmpty()) {
            throw new ApiException.InvalidInputException("Invalid Instrument Identifier. Please send the valid Instrument Identifier");
        }

        // verify if the request has a price populated in it.
        if(tick.getPrice() == null) {
            throw new ApiException.InvalidInputException("Invalid Price. Please send the valid Price");
        }

        // verify if the request has a timestamp populated in it.
        if(tick.getTimestamp() == null) {
            throw new ApiException.InvalidInputException("Invalid Timestamp. Please send the valid Timestamp");
        }

        // verify if the request timestamp is not of future dates or times.
        if(tick.getTimestamp() != null && DateTimeUtil.timeInFuture(tick.getTimestamp())) {
            throw new ApiException.InvalidInputException("Invalid Timestamp. Timestamp can not be in future");
        }

        // saves the Tick in the system and returns a boolean based of success
        if (stockService.saveStock(tick)) {
            // The tick is not older than 60 seconds and the same got persisted in the system so returning status code 201.
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            // The tick is  older than 60 seconds, so the same didn't get persisted in the system so returning status code 204.
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

    }

    /**
     * Returns the aggregated statistics for all ticks across all instruments which happened in last 60 seconds.
     *
     * @return ResponseEntity
     */
    @GetMapping(value = "/statistics")
    public ResponseEntity getGlobalStatistics() {

        GlobalStatistics globalStatistics = stockService.getGlobalStatistics();
        return ResponseEntity.status(HttpStatus.OK).body(globalStatistics);
    }

    /**
     * Returns the aggregated statistics for all ticks for a specific instruments which happened in last 60 seconds.
     *
     * @param instrument_identifier
     * @return
     */
    @GetMapping( value = "/statistics/{instrument_identifier}")
    public ResponseEntity getSpecificStatistics(@PathVariable String instrument_identifier) {

        GlobalStatistics globalStatistics = stockService.getSpecificStatistics(instrument_identifier);
        return ResponseEntity.status(HttpStatus.OK).body(globalStatistics);
    }

}
