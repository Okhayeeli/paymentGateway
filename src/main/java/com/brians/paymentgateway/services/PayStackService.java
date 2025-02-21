// -> product name, price, quantity, currency
// -> session Id  and url

package com.brians.paymentgateway.services;

import com.brians.paymentgateway.dto.ProductRequest;
import com.brians.paymentgateway.dto.PayStackResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

import static com.brians.paymentgateway.constants.ApiConstants.PAYSTACK_INITIALIZE_PAY;
import static com.brians.paymentgateway.constants.ApiConstants.PAYSTACK_VERIFY;

@Service
public class PayStackService {

    private final WebClient webClient;

    String initializerURL = PAYSTACK_INITIALIZE_PAY;
    String verifyPayment = PAYSTACK_VERIFY;

    public PayStackService(@Qualifier("payStackWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public PayStackResponse checkOut(ProductRequest productRequest) {
        try{
            Map<String, Object> paymentRequest = Map.of(
                    "email", productRequest.getEmail(),
                    "amount", productRequest.getAmount()  * 100,
                    "currency", productRequest.getCurrency() != null ? productRequest.getCurrency() : "NGN",
                    "callback_url", "http://localhost:8030/api/payment/verify",
                    "metadata", Map.of(
                            "name", productRequest.getName(),
                            "quantity", productRequest.getQuantity()
                    )
            );
            Map response = webClient.post()
                    .uri(initializerURL)
                    .bodyValue(paymentRequest)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            if(response != null && response.get("status") != null && response.get("status").equals(true)){
                Map data = (Map)response.get("data");
                return PayStackResponse.builder()
                        .status("SUCCESS")
                        .message("Payment initialized successfully")
                        .sessionId((String) data.get("reference"))
                        .sessionUrl((String) data.get("authorization_url"))
                        .build();
            }
            return PayStackResponse.builder()
                    .status("FAILED")
                    .message("Failed to intialize payment")
                    .build();

        }catch (Exception e){
            return PayStackResponse.builder()
                    .status("ERROR")
                    .message("payment Failed: " + e.getMessage())
                    .build();
        }
    }
    public PayStackResponse verifyPayment(String reference) {
        try{
            Map response = webClient.get()
                    .uri(verifyPayment + "/" + reference)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            if(response != null && response.get("status") != null && response.get("status").equals(true)){
                Map data = (Map)response.get("data");
                String paymentStatus = (String)data.get("status");

                return PayStackResponse.builder()
                        .status("SUCCESS")
                        .message("Payment verified: " + paymentStatus)
                        .sessionId("reference")
                        .build();
            }
            return PayStackResponse.builder()
                    .status("FAILED")
                    .message("Payment verification failed")
                    .build();
        } catch (Exception e){
            return PayStackResponse.builder()
                    .status("ERROR")
                    .message("Verification failed: " + e.getMessage())
                    .build();
        }
    }
}
