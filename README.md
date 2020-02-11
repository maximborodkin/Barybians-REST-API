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
| POST | /api/v1/posts/ | Create new post |
| PUT | /api/v1/posts/{id} | Edit post by id |
| DELETE | /api/v1/posts/{id} | Delete post by id |
| POST | /api/v1/posts/{id}/likes | Add like to post by id |
| DELETE | /api/v1/posts/{id} | Delete like from post by id |
| GET | /api/v1/comments/{id} | Returns commment by id |
| GET | /api/v1/posts/{id}/comments | Returns all comments by post id |
| POST | /api/v1/comments | Create new comment |
| PUT | /api/v1/comments/{id} | Edit commment by id |
| DELETE | /api/v1/comments/{id} | Delete commment by id |
| GET | /api/v1/dialogs?firsuser&secondUser | Returns full dialog between firstUser and secondUser |
| GET | /api/v1/dialogs_list | Returns  dialogs list of authenticated user |
| POST | /api/v1/messages | Create new message |
| PUT | /api/v1/messages/{id} | Edit message by id |
| DELETE | /api/v1/messages/{id} | Delete message by id |

### Run from source code
> mvn spring-boot:run

### Build package
> mvn clean package spring-boot:repackage
