# Kafka with Avro & Schema Registry

Based on:
* http://cloudurable.com/blog/avro/index.html
* https://dzone.com/articles/kafka-avro-serialization-and-the-schema-registry
* https://www.confluent.io/blog/schema-registry-avro-in-spring-boot-application-tutorial/

## Start fast-data-dev docker container with Kafka and SchemaRegistry

```
docker run -d \
-p 2181:2181 -p 3030:3030 -p 8081-8083:8081-8083 -p 9581-9585:9581-9585 -p 9092:9092 \
-e ADV_HOST=localhost \
--restart unless-stopped \
--name lensesio \
lensesio/fast-data-dev:latest
```

Lenses.io UI is on [http://localhost:3030/](http://localhost:3030/)

## Build and run the SpringBoot app

```
./gradlew clean build
```

### Backward compatible schema evolution

Data written with an older schema is readable with a newer schema.

```
# start these is separat terminals:
./gradlew :consumerV2:bootRun
./gradlew :producerV1:bootRun
./gradlew :producerV2:bootRun

# publish with V1 schema
curl -X POST -d 'name=Roger&age=78' http://localhost:8001/v1/user/publish

# publish with V2 schema
curl -X POST -d 'name=David&age=75&nickName=Dave' http://localhost:8002/v2/user/publish

# check logs in the consumer terminal
```

### Forward compatible schema evolution

Data written with a newer schema is readable with an older schema

```
# start these is separat terminals:
./gradlew :consumerV1:bootRun
./gradlew :producerV1:bootRun
./gradlew :producerV2:bootRun

# publish with V1 schema
curl -X POST -d 'name=Roger&age=78' http://localhost:8001/v1/user/publish

# publish with V2 schema
curl -X POST -d 'name=David&age=75&nickName=Dave' http://localhost:8002/v2/user/publish

# check logs in the consumer terminal
```

## Clean up

```
docker exec -it lensesio kafka-topics --zookeeper localhost:2181 --list
docker exec -it lensesio kafka-topics --zookeeper localhost:2181 --delete --topic fcsAvroTest
docker exec -it lensesio kafka-topics --zookeeper localhost:2181 --list
docker stop lensesio
docker rm lensesio
```