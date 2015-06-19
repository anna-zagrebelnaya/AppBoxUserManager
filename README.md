# app-box-connector

Header must contain "Authorization: [developer_token]"

Three operations are available:
- create user: POST /app-box-connector/users
(for this operation header must also contain "Content-Type: application/json"
- get current user: GET /app-box-connector/users/current
- delete user: DELETE /app-box-connector/users/[ID]

