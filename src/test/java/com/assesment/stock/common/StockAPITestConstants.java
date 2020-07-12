package com.assesment.stock.common;

import java.net.URI;

public final class StockAPITestConstants {

    public static final String CREATE_ENDPOINT = "/tick";
    public static final String CREATE_ENDPOINT_INVALID_JSON_EMPTY_INSTRUMENT = "{ \"price\":143.25,\"timestamp\":1594550681000 }";
    public static final String CREATE_ENDPOINT_INVALID_JSON_EMPTY_TIMESTAMP = "{ \"instrument\":\"IBM.N\",\"price\":143.25 }";

    public static final String CREATE_ENDPOINT_INVALID_JSON_EMPTY_PRICE = "{ \"instrument\":\"IBM.N\",\"timestamp\":1594550681000 }";
    public static final String CREATE_ENDPOINT_INVALID_JSON_FUTURE_TIMESTAMP = "{ \"instrument\":\"IBM.N\",\"price\":143.25,\"timestamp\":1626086050000 }";
    public static final String CREATE_ENDPOINT_VALID_JSON = "{ \"instrument\":\"IBM.N\",\"price\":143.25,\"timestamp\":1594565613000 }";
    public static final String CREATE_ENDPOINT_INVALID_JSON_TIMESTAMP_OLDER_THAN_60_SECONDS = "{ \"instrument\":\"IBM.N\",\"price\":143.25,\"timestamp\":1594550050000 }";

    public static final String GET_GLOBAL_STAT_ENDPOINT = "/statistics";
    public static final String GET_SPECIFIC_STAT_ENDPOINT = "/statistics/IBM.N";
}
