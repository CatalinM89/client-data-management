## About The Project
This repository is created for hosting the client data management microservice responsable with the Customer and Address relationship management of our internal staff.

## Local setup
From the repository root directory, build the application
```
mvn clean install
```
Then build the application container 
```
docker build -t customer-data-management .
```
And start the container locally using
```
docker container run --name customer-data-management customer-data-management:latest
```
And finally stop the container using
```
docker container stop  customer-data-management 
```
## Technical references
https://docs.docker.com/engine/reference/commandline/container_run/
https://docs.aws.amazon.com/corretto/latest/corretto-17-ug/docker-install.html
## Contact
Catalin Moisa -- catalin.moisa89@gmail.com
