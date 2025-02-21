package com.brians.paymentgateway.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class PayStackResponse {
    private String status;
    private String message;
    private String sessionId;
    private String sessionUrl;

}
