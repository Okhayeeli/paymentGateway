// -> product name, price, quantity, currency
// -> session Id  and url

package com.brians.paymentgateway.services;

import com.brians.paymentgateway.config.ApiConstants;
import com.brians.paymentgateway.dto.ProductRequest;
import com.brians.paymentgateway.dto.PaystackResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.net.http.HttpHeaders;

import static com.brians.paymentgateway.config.ApiConstants.PAYSTACK_INITIALIZE_PAY;

@Service
public class PaystackService {

    @Value("${paystack.secret.key}")
    private String secretKey;

    String initializerURL = PAYSTACK_INITIALIZE_PAY;


    public PaystackResponse checkOutProducts(ProductRequest productRequest) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", )
    }
}
