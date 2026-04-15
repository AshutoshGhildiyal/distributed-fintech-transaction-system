# distributed-fintech-transaction-system
Event-driven fintech backend using Spring Boot Microservices , Kafka orchestration, JWT-secured API Gateway, fraud detection, and distributed transaction processing.


Architecture Overview

A[client] -> B[API Gateway JWT Validation]

B --> C[User Service]
B --> D[Account Service]
B --> E[Transaction Service]

E --> F[Kafka transaction.events]
F --> G[Fraud Service]

G --> H[Kafka fraud.alerts]

H --> E 

E --> I[SAFE -> DEBIT + CREDIT]
E --> J[FRAUD -> Reject Transaction]

D --> K[Ledger + Idempotency + Freeze]



MICROSERVICES

> User Service --> User registration, login, JWT generation, KYC management.
> Account Service --> Account creation, debit, credit, freeze, ledger tracking.
> Transaction Service --> Transfer intitiation, transaction lifecycle management.
> Fraud Service --> Fraud Rule Engine, transaction risk evaluation
> API Gateway --> JWT Validation, request routing.






CORE FEATURES -

* JWT Authentication through API Gateway.
* Kafka-based asynchronous transaction.
* Fraud detection engine with rule based evaluation.
* Account freeze on suspicious activity.
* Idempotent debit/credit operations.
* Ledger entry tracking for financial operations.
* Transaction status lifecycle management.
* Dockerized Kafka + PostgreSQL infrastructure.





Event Flow
1. Transaction Service creates transaction in FRAUD_CHECK_PENDING
2. Transaction event is published to Kafka topic 'transaction.events'.
3. Fraud Service evaluates transaction.
4. Fraud decision is published to kafka topic 'fraud.decision'.
5. Transaction Service consumes fraud decision.


Safe Transaction 
-> Debit sender account.
-> Credit sender account.
-> Update transaction status - SUCCESS

Fraud Transaction
-> Reject Transaction.
-> Freeze Sender Account.
-> Update transaction status - REJECTED



Transaction Lifecycle

INTITIATED 
-> FRAUD CHECK PENDING
-> SUCCESS/REJECTED


Fraud Rules Implemented
* High amount transaction detection.
* Velocity rule structure prepared for extension.



Tech Stack
* Java 21
* Spring Boot
* Spring Security
* Spring Cloud Gateway
* Apache Kafka
* PostgreSQL
* Hibernate/JPA
* Docker Compose


Infrastructure

Dockerized Components 
* Kafka
* Zookeeper
* PostgreSQL




SAMPLE API's


User Login
POST /users/login
Create Transaction
POST /transactions/transfer
Get Transaction Status
GET /transactions/{id}
Debit Account
POST /accounts/debit



Backend Concepts 
* Event driven architecture.
* Distributed transaction orchestration.
* API Gateway authentication.
* Idempotency in payment systems.
* Fraud engine design.
* Async consistency handling.



