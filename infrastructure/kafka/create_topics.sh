#!/bin/bash

echo -e 'Existing kafka topics'
docker exec -it kafka-broker kafka-topics --bootstrap-server kafka-broker:9092 --list

echo -e 'Deleting kafka topics'
docker exec -it kafka-broker kafka-topics --bootstrap-server kafka-broker:9092 --topic airline-event --delete --if-exists
docker exec -it kafka-broker kafka-topics --bootstrap-server kafka-broker:9092 --topic airline-event-dlt --delete --if-exists
docker exec -it kafka-broker kafka-topics --bootstrap-server kafka-broker:9092 --topic customer-event --delete --if-exists
docker exec -it kafka-broker kafka-topics --bootstrap-server kafka-broker:9092 --topic customer-event-dlt --delete --if-exists
docker exec -it kafka-broker kafka-topics --bootstrap-server kafka-broker:9092 --topic payment-request --delete --if-exists
docker exec -it kafka-broker kafka-topics --bootstrap-server kafka-broker:9092 --topic payment-response --delete --if-exists
docker exec -it kafka-broker kafka-topics --bootstrap-server kafka-broker:9092 --topic airline-approval-request --delete --if-exists
docker exec -it kafka-broker kafka-topics --bootstrap-server kafka-broker:9092 --topic airline-approval-response --delete --if-exists

echo -e 'Creating kafka topics'
docker exec -it kafka-broker kafka-topics --bootstrap-server kafka-broker:9092 --topic airline-event --create --if-not-exists --replication-factor 1 --partitions 1
docker exec -it kafka-broker kafka-topics --bootstrap-server kafka-broker:9092 --topic airline-event-dlt --create --if-not-exists --replication-factor 1 --partitions 1
docker exec -it kafka-broker kafka-topics --bootstrap-server kafka-broker:9092 --topic customer-event --create --if-not-exists --replication-factor 1 --partitions 1
docker exec -it kafka-broker kafka-topics --bootstrap-server kafka-broker:9092 --topic customer-event-dlt --create --if-not-exists --replication-factor 1 --partitions 1
docker exec -it kafka-broker kafka-topics --bootstrap-server kafka-broker:9092 --topic payment-request --create --if-not-exists --replication-factor 1 --partitions 1
docker exec -it kafka-broker kafka-topics --bootstrap-server kafka-broker:9092 --topic payment-response --create --if-not-exists --replication-factor 1 --partitions 1
docker exec -it kafka-broker kafka-topics --bootstrap-server kafka-broker:9092 --topic airline-approval-request --create --if-not-exists --replication-factor 1 --partitions 1
docker exec -it kafka-broker kafka-topics --bootstrap-server kafka-broker:9092 --topic airline-approval-response --create --if-not-exists --replication-factor 1 --partitions 1

echo -e 'Successfully created the following topics:'
docker exec -it kafka-broker kafka-topics --bootstrap-server kafka-broker:9092 --list
