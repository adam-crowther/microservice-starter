#!/bin/bash

echo -e 'Existing kafka topics'
kafka-topics --bootstrap-server kafka-broker:9092 --list

echo -e 'Deleting kafka topics'
kafka-topics --bootstrap-server kafka-broker:9092 --topic airline-event --delete --if-exists
kafka-topics --bootstrap-server kafka-broker:9092 --topic airline-event-dlt --delete --if-exists
kafka-topics --bootstrap-server kafka-broker:9092 --topic customer-event --delete --if-exists
kafka-topics --bootstrap-server kafka-broker:9092 --topic customer-event-dlt --delete --if-exists
kafka-topics --bootstrap-server kafka-broker:9092 --topic payment-request --delete --if-exists
kafka-topics --bootstrap-server kafka-broker:9092 --topic payment-response --delete --if-exists
kafka-topics --bootstrap-server kafka-broker:9092 --topic airline-approval-request --delete --if-exists
kafka-topics --bootstrap-server kafka-broker:9092 --topic airline-approval-response --delete --if-exists

echo -e 'Creating kafka topics'
kafka-topics --bootstrap-server kafka-broker:9092 --topic airline-event --create --if-not-exists --replication-factor 1 --partitions 1
kafka-topics --bootstrap-server kafka-broker:9092 --topic airline-event-dlt --create --if-not-exists --replication-factor 1 --partitions 1
kafka-topics --bootstrap-server kafka-broker:9092 --topic customer-event --create --if-not-exists --replication-factor 1 --partitions 1
kafka-topics --bootstrap-server kafka-broker:9092 --topic customer-event-dlt --create --if-not-exists --replication-factor 1 --partitions 1
kafka-topics --bootstrap-server kafka-broker:9092 --topic payment-request --create --if-not-exists --replication-factor 1 --partitions 1
kafka-topics --bootstrap-server kafka-broker:9092 --topic payment-response --create --if-not-exists --replication-factor 1 --partitions 1
kafka-topics --bootstrap-server kafka-broker:9092 --topic airline-approval-request --create --if-not-exists --replication-factor 1 --partitions 1
kafka-topics --bootstrap-server kafka-broker:9092 --topic airline-approval-response --create --if-not-exists --replication-factor 1 --partitions 1

echo -e 'Successfully created the following topics:'
kafka-topics --bootstrap-server kafka-broker:9092 --list
