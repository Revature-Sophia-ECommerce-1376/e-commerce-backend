# Revazon

![Project Image](src/main/resources/revazon-nav-bar-image.png)

## Run the Application

```html
	sh build.sh
```

## Run the Application with Docker

```html
	mvn clean package
	docker-compose up
```
- Open DBeaver or a similar PostgreSQL client and run data.sql inside src/main/resources

## Setting up 3rd-Party Web Services

- Create an account with auth0 and create an api. Replace the issuer-uri, audience, and client-origin-url in application.yml

- Sign into AWS and create an S3 bucket. Replace the appropriate variables in application.yml
