package com.assesment.stock.service;

import com.assesment.stock.model.GlobalStatistics;
import com.assesment.stock.model.Tick;
import com.assesment.stock.repository.StockRepository;
import com.assesment.stock.util.DateTimeUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(PowerMockRunner.class)
@PrepareForTest(DateTimeUtil.class)
public class StockServiceTest {

    StockService stockService;
    LocalDateTime localCurrentTime;

    @Before
    public void setUp() {
        this.stockService = new StockService();
        this.stockService.stockRepository = new StockRepository();
        // Saturday, July 11, 2020 12:10:58 AM GMT+02:00 DST
        long timestamp = 1594419058000L;
        localCurrentTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp),
                TimeZone.getDefault().toZoneId());
    }

    @Test
    public void saveOldStock() {

        // partial mocking -- of one particular static method and leaving others unchanged
        PowerMockito.stub(PowerMockito.method(DateTimeUtil.class, "getCurrentDateAndTime")).toReturn(localCurrentTime);
        Tick tick = new Tick("IBM.N", 143.82, 1478192204000L);
        boolean status = this.stockService.saveStock(tick);

        assertEquals(false, status);

    }

    @Test
    public void saveValidStock() {

        PowerMockito.stub(PowerMockito.method(DateTimeUtil.class, "getCurrentDateAndTime")).toReturn(localCurrentTime);
        Tick tick = new Tick("IBM.N", 143.82, 1594419010000L);
        boolean status = this.stockService.saveStock(tick);

        assertEquals(true, status);

    }

    @Test
    public void getGlobalStatisticsForOneRecord() {

        PowerMockito.stub(PowerMockito.method(DateTimeUtil.class, "getCurrentDateAndTime")).toReturn(localCurrentTime);
        Tick tick = new Tick("IBM.N", 143.82, 1594419010000L);
        boolean status = this.stockService.saveStock(tick);

        assertThat(status).isTrue();

        GlobalStatistics globalStatistics = this.stockService.getGlobalStatistics();
        System.out.println(globalStatistics);

        assertThat(globalStatistics).isNotNull();
        assertThat(globalStatistics.getCount()).isEqualTo(1);
        assertThat(globalStatistics.getSum()).isEqualTo(143.82);
        assertThat(globalStatistics.getAvg()).isEqualTo(143.82);
        assertThat(globalStatistics.getMax()).isEqualTo(143.82);
        assertThat(globalStatistics.getMin()).isEqualTo(143.82);
    }

    @Test
    public void getSpecificStatisticsForOneExistingRecord() {

        PowerMockito.stub(PowerMockito.method(DateTimeUtil.class, "getCurrentDateAndTime")).toReturn(localCurrentTime);
        Tick tick = new Tick("IBM.N", 143.82, 1594419010000L);
        boolean status = this.stockService.saveStock(tick);

        assertThat(status).isTrue();

        GlobalStatistics globalStatistics = this.stockService.getSpecificStatistics("IBM.N");
        System.out.println(globalStatistics);

        assertThat(globalStatistics).isNotNull();
        assertThat(globalStatistics.getCount()).isEqualTo(1);
        assertThat(globalStatistics.getSum()).isEqualTo(143.82);
        assertThat(globalStatistics.getAvg()).isEqualTo(143.82);
        assertThat(globalStatistics.getMax()).isEqualTo(143.82);
        assertThat(globalStatistics.getMin()).isEqualTo(143.82);
    }

    @Test
    public void getSpecificStatisticsForOneNonExistingRecord() {

        PowerMockito.stub(PowerMockito.method(DateTimeUtil.class, "getCurrentDateAndTime")).toReturn(localCurrentTime);
        Tick tick = new Tick("IBM.N", 143.82, 1594419010000L);
        boolean status = this.stockService.saveStock(tick);

        assertThat(status).isTrue();

        GlobalStatistics globalStatistics = this.stockService.getSpecificStatistics("MICROSOFT");
        System.out.println(globalStatistics);

        assertThat(globalStatistics).isNotNull();
        assertThat(globalStatistics.getCount()).isEqualTo(0);
        assertThat(globalStatistics.getSum()).isEqualTo(0.0);
        assertThat(globalStatistics.getAvg()).isEqualTo(0.0);
        assertThat(globalStatistics.getMax()).isEqualTo(0.0);
        assertThat(globalStatistics.getMin()).isEqualTo(0.0);
    }

    @Test
    public void getGlobalStatisticsForTwoRecord() {

        PowerMockito.stub(PowerMockito.method(DateTimeUtil.class, "getCurrentDateAndTime")).toReturn(localCurrentTime);
        Tick tick1 = new Tick("IBM.N", 143.82, 1594419010000L);
        Tick tick2 = new Tick("IBM.N", 43.82, 1594419015000L);
        boolean status1 = this.stockService.saveStock(tick1);
        boolean status2 = this.stockService.saveStock(tick2);

        assertThat(status1).isTrue();
        assertThat(status2).isTrue();

        GlobalStatistics globalStatistics = this.stockService.getGlobalStatistics();
        System.out.println(globalStatistics);

        assertThat(globalStatistics).isNotNull();
        assertThat(globalStatistics.getCount()).isEqualTo(2);
        assertThat(globalStatistics.getSum()).isEqualTo(187.64);
        assertThat(globalStatistics.getAvg()).isEqualTo(93.82);
        assertThat(globalStatistics.getMax()).isEqualTo(143.82);
        assertThat(globalStatistics.getMin()).isEqualTo(43.82);
    }

    @Test
    public void getGlobalStatisticsForTwoValidOneInvalidRecord() {

        PowerMockito.stub(PowerMockito.method(DateTimeUtil.class, "getCurrentDateAndTime")).toReturn(localCurrentTime);
        Tick tick1 = new Tick("IBM.N", 143.82, 1594419010000L);
        Tick tick2 = new Tick("IBM.N", 43.82, 1594419015000L);
        Tick tick3 = new Tick("IBM.N", 81.82, 1478192204000L);
        boolean status1 = this.stockService.saveStock(tick1);
        boolean status2 = this.stockService.saveStock(tick2);
        boolean status3 = this.stockService.saveStock(tick3);

        assertThat(status1).isTrue();
        assertThat(status2).isTrue();
        assertThat(status3).isFalse();

        GlobalStatistics globalStatistics = this.stockService.getGlobalStatistics();
        System.out.println(globalStatistics);

        assertThat(globalStatistics).isNotNull();
        assertThat(globalStatistics.getCount()).isEqualTo(2);
        assertThat(globalStatistics.getSum()).isEqualTo(187.64);
        assertThat(globalStatistics.getAvg()).isEqualTo(93.82);
        assertThat(globalStatistics.getMax()).isEqualTo(143.82);
        assertThat(globalStatistics.getMin()).isEqualTo(43.82);
    }

    @Test
    public void getGlobalStatisticsForMultipleValidRecordOfSameInstances() {

        PowerMockito.stub(PowerMockito.method(DateTimeUtil.class, "getCurrentDateAndTime")).toReturn(localCurrentTime);
        Tick tick1 = new Tick("IBM.N", 143.82, 1594419010000L);
        Tick tick2 = new Tick("IBM.N", 43.82, 1594419015000L);
        Tick tick3 = new Tick("IBM.N", 81.82, 1594419010000L);
        Tick tick4 = new Tick("IBM.N", 18.82, 1478192204000L);

        boolean status1 = this.stockService.saveStock(tick1);
        boolean status2 = this.stockService.saveStock(tick2);
        boolean status3 = this.stockService.saveStock(tick3);
        boolean status4 = this.stockService.saveStock(tick4);

        assertThat(status1).isTrue();
        assertThat(status2).isTrue();
        assertThat(status3).isTrue();
        assertThat(status4).isFalse();

        GlobalStatistics globalStatistics = this.stockService.getGlobalStatistics();
        System.out.println(globalStatistics);

        assertThat(globalStatistics).isNotNull();
        assertThat(globalStatistics.getCount()).isEqualTo(3);
        assertThat(globalStatistics.getSum()).isEqualTo(269.46);
        assertThat(globalStatistics.getAvg()).isEqualTo(89.82);
        assertThat(globalStatistics.getMax()).isEqualTo(143.82);
        assertThat(globalStatistics.getMin()).isEqualTo(43.82);
    }

    @Test
    public void getGlobalandSpecificStatisticsForMultipleValidAndInvalidRecord() {

        PowerMockito.stub(PowerMockito.method(DateTimeUtil.class, "getCurrentDateAndTime")).toReturn(localCurrentTime);
        Tick tick1 = new Tick("IBM.N", 143.82, 1594419010000L);
        Tick tick2 = new Tick("IBM.N", 43.82, 1594419015000L);
        Tick tick3 = new Tick("IBM.N", 81.82, 1478192204000L);
        Tick tick4 = new Tick("IBM.N", 72.81, 1594419035000L);
        Tick tick5 = new Tick("MICROSOFT", 144.82, 1594419020000L);
        Tick tick6 = new Tick("MICROSOFT", 44.82, 1594419025000L);
        Tick tick7 = new Tick("MICROSOFT", 84.82, 1478192204500L);
        Tick tick8 = new Tick("MICROSOFT", 74.81, 1594419045000L);

        boolean status1 = this.stockService.saveStock(tick1);
        boolean status2 = this.stockService.saveStock(tick2);
        boolean status3 = this.stockService.saveStock(tick3);
        boolean status4 = this.stockService.saveStock(tick4);
        boolean status5 = this.stockService.saveStock(tick5);
        boolean status6 = this.stockService.saveStock(tick6);
        boolean status7 = this.stockService.saveStock(tick7);
        boolean status8 = this.stockService.saveStock(tick8);

        assertThat(status1).isTrue();
        assertThat(status2).isTrue();
        assertThat(status3).isFalse();
        assertThat(status4).isTrue();
        assertThat(status5).isTrue();
        assertThat(status6).isTrue();
        assertThat(status7).isFalse();
        assertThat(status8).isTrue();

        GlobalStatistics globalStatistics = this.stockService.getGlobalStatistics();
        System.out.println(globalStatistics);

        assertThat(globalStatistics).isNotNull();
        assertThat(globalStatistics.getCount()).isEqualTo(6);
        assertThat(globalStatistics.getSum()).isEqualTo(524.9);
        assertThat(globalStatistics.getAvg()).isEqualTo(87.48);
        assertThat(globalStatistics.getMax()).isEqualTo(144.82);
        assertThat(globalStatistics.getMin()).isEqualTo(43.82);

        GlobalStatistics ibmSpecificStatistics = this.stockService.getSpecificStatistics("IBM.N");
        System.out.println("ibmSpecificStatistics" + ibmSpecificStatistics);

        assertThat(ibmSpecificStatistics).isNotNull();
        assertThat(ibmSpecificStatistics.getCount()).isEqualTo(3);
        assertThat(ibmSpecificStatistics.getSum()).isEqualTo(260.45);
        assertThat(ibmSpecificStatistics.getAvg()).isEqualTo(86.82);
        assertThat(ibmSpecificStatistics.getMax()).isEqualTo(143.82);
        assertThat(ibmSpecificStatistics.getMin()).isEqualTo(43.82);

        GlobalStatistics microsoftSpecificStatistics = this.stockService.getSpecificStatistics("MICROSOFT");
        System.out.println("microsoftSpecificStatistics" + microsoftSpecificStatistics);

        assertThat(microsoftSpecificStatistics).isNotNull();
        assertThat(microsoftSpecificStatistics.getCount()).isEqualTo(3);
        assertThat(microsoftSpecificStatistics.getSum()).isEqualTo(264.45);
        assertThat(microsoftSpecificStatistics.getAvg()).isEqualTo(88.15);
        assertThat(microsoftSpecificStatistics.getMax()).isEqualTo(144.82);
        assertThat(microsoftSpecificStatistics.getMin()).isEqualTo(44.82);
    }


    @Test
    public void getGlobalAndSpecificStatisticsForMultipleValidAndInvalidRecordSameInstacnes() {

        PowerMockito.stub(PowerMockito.method(DateTimeUtil.class, "getCurrentDateAndTime")).toReturn(localCurrentTime);
        Tick tick1 = new Tick("IBM.N", 143.82, 1594419010000L);
        Tick tick2 = new Tick("IBM.N", 43.82, 1594419015000L);
        Tick tick3 = new Tick("IBM.N", 81.82, 1478192204000L);
        Tick tick4 = new Tick("IBM.N", 72.81, 1594419035000L);
        Tick tick5 = new Tick("MICROSOFT", 144.82, 1594419010000L);
        Tick tick6 = new Tick("MICROSOFT", 44.82, 1594419015000L);
        // invalid data
        Tick tick7 = new Tick("MICROSOFT", 84.82, 1478192204000L);
        Tick tick8 = new Tick("MICROSOFT", 74.81, 1594419035000L);

        boolean status1 = this.stockService.saveStock(tick1);
        boolean status2 = this.stockService.saveStock(tick2);
        boolean status3 = this.stockService.saveStock(tick3);
        boolean status4 = this.stockService.saveStock(tick4);
        boolean status5 = this.stockService.saveStock(tick5);
        boolean status6 = this.stockService.saveStock(tick6);
        boolean status7 = this.stockService.saveStock(tick7);
        boolean status8 = this.stockService.saveStock(tick8);

        assertThat(status1).isTrue();
        assertThat(status2).isTrue();
        assertThat(status3).isFalse();
        assertThat(status4).isTrue();
        assertThat(status5).isTrue();
        assertThat(status6).isTrue();
        assertThat(status7).isFalse();
        assertThat(status8).isTrue();

        GlobalStatistics globalStatistics = this.stockService.getGlobalStatistics();
        System.out.println(globalStatistics);

        assertThat(globalStatistics).isNotNull();
        assertThat(globalStatistics.getCount()).isEqualTo(6);
        assertThat(globalStatistics.getSum()).isEqualTo(524.9);
        assertThat(globalStatistics.getAvg()).isEqualTo(87.48);
        assertThat(globalStatistics.getMax()).isEqualTo(144.82);
        assertThat(globalStatistics.getMin()).isEqualTo(43.82);

        GlobalStatistics ibmSpecificStatistics = this.stockService.getSpecificStatistics("IBM.N");
        System.out.println("ibmSpecificStatistics" + ibmSpecificStatistics);

        assertThat(ibmSpecificStatistics).isNotNull();
        assertThat(ibmSpecificStatistics.getCount()).isEqualTo(3);
        assertThat(ibmSpecificStatistics.getSum()).isEqualTo(260.45);
        assertThat(ibmSpecificStatistics.getAvg()).isEqualTo(86.82);
        assertThat(ibmSpecificStatistics.getMax()).isEqualTo(143.82);
        assertThat(ibmSpecificStatistics.getMin()).isEqualTo(43.82);

        GlobalStatistics microsoftSpecificStatistics = this.stockService.getSpecificStatistics("MICROSOFT");
        System.out.println("microsoftSpecificStatistics" + microsoftSpecificStatistics);

        assertThat(microsoftSpecificStatistics).isNotNull();
        assertThat(microsoftSpecificStatistics.getCount()).isEqualTo(3);
        assertThat(microsoftSpecificStatistics.getSum()).isEqualTo(264.45);
        assertThat(microsoftSpecificStatistics.getAvg()).isEqualTo(88.15);
        assertThat(microsoftSpecificStatistics.getMax()).isEqualTo(144.82);
        assertThat(microsoftSpecificStatistics.getMin()).isEqualTo(44.82);
    }


}