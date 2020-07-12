package com.assesment.stock.Exception;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockApiError {

    private String message;
    private int code;
}
