guest-book
--
Logs guess books entries

## Getting started


## Prerequisites
The following are required
- JDK11
- sbt 
- docker

## Installing
To install, test and run first clone the repo, next:
```
sbt test run
sbt run
```

## Integration tests
- Build the docker image 
- Run the docker image
- Execute the integration tests

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

### URLs
- [guest-book.tk](https://guest-book.tk)
- [www.guest-book.tk](https://www.guest-book.tk)

#### Usage
- To post logs:
```
curl -v -k -i \
      -H "Accept: application/json" \
      -H "Content-Type: application/json" \
      -X POST -d  \
      '{"name": "John Doe", "message": "Hello from John Doe!"}' \
      https://guest-book.tk/v1/log
```
- To fetch logs
```
curl -v -k https://www.guest-book.tk/v1/log
```

## Build With
the project is build inside a docker container. This will allow us to simplify CI and take advantage of 
- gcp [cloud-build](https://cloud.google.com/cloud-build/)
- gcp [container-registry](https://cloud.google.com/container-registry/)
- gcp [k8](https://kubernetes.io/) deployment 

