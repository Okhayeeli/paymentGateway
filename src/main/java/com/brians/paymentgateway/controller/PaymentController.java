package com.brians.paymentgateway.controller;


import com.brians.paymentgateway.dto.PayStackResponse;
import com.brians.paymentgateway.dto.ProductRequest;
import com.brians.paymentgateway.services.PayStackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PayStackService paystackService;

    @PostMapping("/checkout")
    public ResponseEntity<PayStackResponse> checkOut(@RequestBody ProductRequest request) {
      PayStackResponse response = paystackService.checkOut(request);
      if ("SUCCESS".equals(response.getStatus())) {
          return ResponseEntity.ok(response);
      }
      return ResponseEntity.badRequest().body(response);
    }

    @GetMapping("/verify")
    public ResponseEntity<PayStackResponse> verifyPayment(@RequestParam String reference) {
        PayStackResponse response = paystackService.verifyPayment(reference);
        if ("SUCCESS".equals(response.getStatus())) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(response);
    }

}
