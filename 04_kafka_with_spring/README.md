# Kafka with SpringBoot

Based on: https://www.confluent.io/blog/apache-kafka-spring-boot-application/

## Start Kafka cluster

```
docker-compose -f kafka-single-node.yml up -d
```

Kafdrop IU is on [http://localhost:9000/](http://localhost:9000/)

## Build and run the SpringBoot app

```
./gradlew bootRun

# in another terminal:
curl http://localhost:8080/api/fibonacci -d "n=10"
# check logs in first terminal
```

## Clean up

```
docker-compose -f kafka-single-node.yml down
```