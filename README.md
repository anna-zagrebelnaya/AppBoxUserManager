# app-box-connector

Header must contain "Authorization: <developer_token>"

Three operations are available:
- create user: /app-box-connector/users/create
(for this operation header must also contain "Content-Type: application/json"
- get current user: /app-box-connector/users/current
- delete user: /app-box-connector/users/delete/<ID>

