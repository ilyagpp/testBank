Test task REST API for backing service.
Designed with Spring Boot 3.1.4, Java 17, Spring DATA JPA, H2 Database.

Design and implement a RESTful API, backing service and data model to create bank accounts
and transfer money between them. Interaction with API will be using HTTP requests.
Requirements:
• Accounts are created by supplying a name and four-digit PIN code. Account number is automatically created.
• Once account is created one can Deposit, Withdraw or Transfer money between accounts.
• Any operation which deducts funds from the account needs to include the correct PIN code.
• A specific call will fetch all the accounts: the name and their current balance.
• APIs will use JSON payloads when applicable.
• Use in-memory database as a backing store.

Recommendations:
• Use any technologies you feel comfortable with, but it is recommended you set up
your project using Spring Boot.
• Make use of best practices when implementing your code to ensure a high-quality
result. Use dependency injection, avoid statics, immutable types, etc…
• However, keep it simple and to the point. Do not over-engineer. e.g. there is no need
to add authorization layers.

Technical documentation on SWAGGER
http://localhost:8080/swagger-ui/index.html

Create "Transfer" operation:
curl -X 'POST' \
'http://localhost:8080/api/operation' \
-H 'accept: application/json' \
-H 'Content-Type: application/json' \
-d '{
"pin": 7777,
"operationType": "transfer",
"accountTo": 40800000055000012346,
"accountFrom": 40800000055000012347,
"amount": 2.50
}'
REST URL
http://localhost:8080/api/operation

Create a new account:
curl -X 'POST' \
'http://localhost:8080/api/account' \
-H 'accept: application/json' \
-H 'Content-Type: application/json' \
-d '{
"name": "Andrey",
"pin": 1345
}'

Request URL
http://localhost:8080/api/account

