guest-book
--
Logs guess books entries

## Getting started


## Prerequisites
The following are required
- JDK10 or better
- sbt 
- docker

## Installing
To install, test and run first clone the repo, next:
```
sbt test run
sbt run
```

## Integration tests
- build the docker image 
- run the docker image
- execute the integration tests

From project root folder:
```
docker build -t kayvank/guest-book:v1.00123 .
docker run -p9000:9000 kayvank/guest-book:v1.00123 
```
From a separate terminal:
```
./integration-test.sh
```
*Note* this is a very simple integration test.

## Deployment
This application is deployed to k8 cluster using cloud-build 

### DNS 
- guess-book.tk
- www.guess-book.tk

## Build With
the project is build inside a docker container. This will allow us to simplify CI and take advantage of GCP cloud-biuld & deployment
