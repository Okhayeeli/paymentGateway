# Paystack Payment Gateway Integration

## Overview
This project integrates Paystack's payment gateway into a Spring Boot application, allowing users to make payments and verify transactions.

## Features
- Initialize a payment session with Paystack
- Verify transactions
- Handle payment metadata
- Secure API using Spring Boot
- Uses WebClient for external API communication

## Technologies Used
- **Java 17**
- **Spring Boot**
- **Spring WebFlux (WebClient)**
- **Paystack API**
- **Postman (for API testing)**

## Setup and Installation

### Prerequisites
Ensure you have the following installed:
- Java 17+
- Maven
- Postman (optional, for testing)
- Paystack Account (for API keys)

### Steps to Run the Application
1. Clone the repository:
   ```sh
   git clone https://github.com/your-repo-url.git
   cd payment-gateway
   ```
2. Configure your **application.properties** file:
   ```properties
   paystack.api.secret-key=your_paystack_secret_key
   paystack.api.initialize-url=https://api.paystack.co/transaction/initialize
   paystack.api.verify-url=https://api.paystack.co/transaction/verify/
   ```
3. Build and run the project:
   ```sh
   mvn clean install
   mvn spring-boot:run
   ```

## API Endpoints

### 1. Initialize Payment
**Endpoint:** `POST /api/payment/checkout`

**Request Body example:**
```json
{
  "amount": 5000,
  "currency": "NGN",
  "name": "Test Product",
  "quantity": 1
}
```
**Response should be like:**
```json
{
  "status": "SUCCESS",
  "message": "Payment initialized successfully",
  "sessionId": "t23h7f8g9h2",
  "sessionUrl": "https://checkout.paystack.com/t23h7f8g9h2"
}
```

### 2. Verify Payment
**Endpoint:** `GET /api/payment/verify?reference={transaction_reference}`

**Responsexshould be like:**
```json
{
  "status": "SUCCESS",
  "message": "Payment verified: success",
  "sessionId": "t23h7f8g9h2"
}
```

## Testing with Postman
1. Start the Spring Boot server
2. Use Postman to send a `POST` request to `http://localhost:8080/api/payment/checkout`
3. Copy the `sessionId` from the response and verify it using `GET /api/payment/verify?reference={sessionId}`

## Troubleshooting
- **400 Bad Request:** Ensure that the `amount` is in kobo (multiply NGN by 100) and an `email` is included.
- **401 Unauthorized:** Verify your Paystack secret key.
- **500 Internal Server Error:** Check logs for API errors.

## License
This project is licensed under the MIT License.

## Author
Elizabeth [(https://github.com/Okhayeeli/)]

