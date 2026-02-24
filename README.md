
# 💰 Wallet Service

Simple Wallet Service built with **Spring Boot** for handling financial transactions such as:

- Top Up
- Payment
- Transfer
- Refund
- Reversal

This service is designed using production‑grade patterns including idempotency protection, mutation ledger logging, hybrid auditing, and optimistic locking.

---

# 🏗️ High Level Architecture

Client → Header Interceptor → Controller → Service → Repository → Database

---

# ⚙️ Tech Stack

- Java 21
- Spring Boot 3.x
- Spring Data JPA
- PostgreSQL
- Flyway
- Lombok

---

# 🔐 Header Specification

All transaction APIs require headers:

| Header | Required | Description |
|--------|----------|-------------|
| X-Request-Id | ✅ | Idempotency key |
| X-Channel | ✅ | Channel source |

Example:

X-Request-Id: REQ-TOPUP-001  
X-Channel: MOBILE

---

# 🧠 Idempotency Design

Duplicate protection key:

request_id + channel

Prevents duplicate financial processing.

---

# 📥 Request Wrapper

All APIs use:

{
"data": { ... }
}

DTO:

public record BaseDataRequest<T>(T data) {}

---

# 🧾 Transaction Types

| Type | Effect |
|------|--------|
| TOPUP | Credit |
| PAYMENT | Debit |
| TRANSFER | Debit/Credit |
| REFUND | Credit |
| REVERSAL | Credit |

---

# 🗄️ Database Schema

## account

id = customer_id (UUID)

## transaction

Stores financial transaction records.

## account_mutation

Immutable ledger logs.

---

# 🧠 Auditing

Hybrid auditing:

- createdBy → Spring Auditing
- updatedBy → Spring Auditing
- createdTime → Lifecycle hook
- updatedTime → Lifecycle hook

---

# 🚀 API CURL SAMPLE

## Create Account

curl --location --request POST 'http://localhost:8080/api/v1/accounts'

## Topup

curl --location 'http://localhost:8080/api/v1/transactions' \
--header 'Content-Type: application/json' \
--header 'X-Request-Id: REQ-TOPUP-001' \
--header 'X-Channel: MOBILE' \
--data '{
"data": {
"referenceNo": "TRX-TOPUP-001",
"accountId": "0634fa38-fe33-4945-9113-7b5adebd7b4e",
"amount": 100000,
"type": "TOPUP"
}
}'

---

# 🛠️ Run Locally

./gradlew bootRun

---

# 📌 Notes

- Account ID = Customer ID
- 1 Customer = 1 Wallet
- Mutation table = Ledger

---

# 👨‍💻 Author

Adhimas Panji Kuncoro Bekti
