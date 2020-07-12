package com.assesment.stock.controller;

import com.assesment.stock.Exception.ExceptionHandler;
import com.assesment.stock.model.GlobalStatistics;
import com.assesment.stock.model.Tick;
import com.assesment.stock.service.StockService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.assesment.stock.common.StockAPITestConstants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class StockAPITest {

/*   @InjectMocks
    StockAPI stockAPI;

    @Mock
    StockService stockService;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(stockAPI).setControllerAdvice(new ExceptionHandler()).build();
        //        .alwaysDo(print()).build();
    }*/


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StockService stockService;


    @Test
    public void postTickWithEmptyInstrument() throws Exception {
        this.mockMvc.perform(post(CREATE_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(CREATE_ENDPOINT_INVALID_JSON_EMPTY_INSTRUMENT))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Invalid Instrument Identifier. Please send the valid Instrument Identifier"))
                .andExpect(jsonPath("code").value("400"));
    }


    @Test
    void postTickWithoutPrice() throws Exception {
        this.mockMvc.perform(post(CREATE_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(CREATE_ENDPOINT_INVALID_JSON_EMPTY_PRICE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Invalid Price. Please send the valid Price"))
                .andExpect(jsonPath("code").value("400"));
    }

    @Test
    void postTickWithoutTimeStamp() throws Exception {
        this.mockMvc.perform(post(CREATE_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(CREATE_ENDPOINT_INVALID_JSON_EMPTY_TIMESTAMP))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Invalid Timestamp. Please send the valid Timestamp"))
                .andExpect(jsonPath("code").value("400"));
    }

    @Test
    void postTickWithFutureTimeStamp() throws Exception {
        this.mockMvc.perform(post(CREATE_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(CREATE_ENDPOINT_INVALID_JSON_FUTURE_TIMESTAMP))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Invalid Timestamp. Timestamp can not be in future"))
                .andExpect(jsonPath("code").value("400"));
    }

    @Test
    void postTickWithValidTimeStamp() throws Exception {

        Mockito.when(stockService.saveStock(any(Tick.class))).thenReturn(true);

        this.mockMvc.perform(post(CREATE_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(CREATE_ENDPOINT_VALID_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void postTickWithTimeStampOlderThan60Seconds() throws Exception {

        Mockito.when(stockService.saveStock(any(Tick.class))).thenReturn(false);

        this.mockMvc.perform(post(CREATE_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(CREATE_ENDPOINT_INVALID_JSON_TIMESTAMP_OLDER_THAN_60_SECONDS))
                .andExpect(status().isNoContent());
    }

    @Test
    void getGlobalStatistics() throws Exception {

        GlobalStatistics globalStatistics =  new GlobalStatistics();
        globalStatistics.setCount(2);
        globalStatistics.setSum(187.64);
        globalStatistics.setAvg(93.82);
        globalStatistics.setMax(143.82);
        globalStatistics.setMin(43.82);

        Mockito.when(stockService.getGlobalStatistics()).thenReturn(globalStatistics);

        this.mockMvc.perform(get(GET_GLOBAL_STAT_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("count").value(2))
                .andExpect(jsonPath("sum").value(187.64))
                .andExpect(jsonPath("avg").value(93.82))
                .andExpect(jsonPath("max").value(143.82))
                .andExpect(jsonPath("min").value(43.82));
    }

    @Test
    void getSpecificStatistics() throws Exception {
        GlobalStatistics globalStatistics =  new GlobalStatistics();
        globalStatistics.setCount(2);
        globalStatistics.setSum(187.64);
        globalStatistics.setAvg(93.82);
        globalStatistics.setMax(143.82);
        globalStatistics.setMin(43.82);

        Mockito.when(stockService.getSpecificStatistics("IBM.N")).thenReturn(globalStatistics);

        this.mockMvc.perform(get(GET_SPECIFIC_STAT_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("count").value(2))
                .andExpect(jsonPath("sum").value(187.64))
                .andExpect(jsonPath("avg").value(93.82))
                .andExpect(jsonPath("max").value(143.82))
                .andExpect(jsonPath("min").value(43.82));
    }
}
