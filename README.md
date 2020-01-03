# Barybians REST API

### RESTfull api for Barybians social network based on Spring Boot and MySQL

##### Database scheme
![Diagram](https://github.com/maximborodkin/Barybians-REST-API/blob/master/Barybians_DB.png)

##### Supported paths

| Method | Endpoint | Description |
| - | - | - |
| POST   | /api/v1/auth/login | Autohorize and returns Return JWT token |
| POST   | /api/v1/auth/register | Register user and returns JWT token |
| GET | /api/v1/users | Returns list of all users |
| GET | /api/v1/users/{identifier} | Returns requested user. As identifier might be id or username |
| GET | /api/v1/users?search | Returns all user by search parameter |
| DELETE | /api/v1/users/{identifier} | Delete requested user. As identifier might be id or username |
| GET | /api/v1/roles | Returns list of roles |
| GET | /api/v1/posts/{id} | Returns post by id |
| GET | /api/v1/comments/{identifier} | Returns commment by id |
| GET | /api/v1/users/{identifier} | Returns requested user. As identifier might be id or username |
| GET | /api/v1/dialogs/{id} | Returns  dialogs list of requested user |
| GET | /api/v1/dialogs?firsuser&secondUser | Returns full dialog between firstUser and secondUser |
