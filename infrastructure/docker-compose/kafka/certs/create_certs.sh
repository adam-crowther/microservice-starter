#!/bin/bash

set MSYS_NO_PATHCONV=1

set -o nounset \
    -o errexit \
    -o verbose \
    -o xtrace

# Generate CA key
openssl req -new -x509 -keyout snakeoil-ca-1.key -out snakeoil-ca-1.crt -days 365 -subj '//x=1/CN=ca1.test.acroteq.ch/OU=TEST/O=Acroteq/L=Zurich/S=ZH/C=CH' -passin pass:acroteq -passout pass:acroteq
# openssl req -new -x509 -keyout snakeoil-ca-2.key -out snakeoil-ca-2.crt -days 365 -subj '/CN=ca2.test.acroteq.ch/OU=TEST/O=Acroteq/L=Zurich/S=ZH/C=CH' -passin pass:acroteq -passout pass:acroteq

# Kafkacat
openssl genrsa -des3 -passout "pass:acroteq" -out kafkacat.client.key 1024
openssl req -passin "pass:acroteq" -passout "pass:acroteq" -key kafkacat.client.key -new -out kafkacat.client.req -subj '//x=1/CN=kafkaclient/OU=TEST/O=Acroteq/L=Zurich/S=ZH/C=CH'
openssl x509 -req -CA snakeoil-ca-1.crt -CAkey snakeoil-ca-1.key -in kafkacat.client.req -out kafkacat-ca1-signed.pem -days 9999 -CAcreateserial -passin "pass:acroteq"


for i in kafka-broker airline-approval airline-mdm customer-mdm order-service payment-service
do
	echo $i
	# Create keystores
	keytool -genkey -noprompt \
				 -alias $i \
				 -dname "CN=localhost, OU=TEST, O=Acroteq, L=Zurich, S=ZH, C=CH" \
				 -keystore $i.keystore.jks \
				 -keyalg RSA \
				 -storepass acroteq \
				 -keypass acroteq

	# Create CSR, sign the key and import back into keystore
	keytool -keystore $i.keystore.jks -alias $i -certreq -file $i.csr -storepass acroteq -keypass acroteq

	openssl x509 -req -CA snakeoil-ca-1.crt -CAkey snakeoil-ca-1.key -in $i.csr -out $i-ca1-signed.crt -days 9999 -CAcreateserial -passin pass:acroteq

	keytool -keystore $i.keystore.jks -alias CARoot -import -file snakeoil-ca-1.crt -storepass acroteq -keypass acroteq -noprompt

	keytool -keystore $i.keystore.jks -alias $i -import -file $i-ca1-signed.crt -storepass acroteq -keypass acroteq -noprompt

	# Create truststore and import the CA cert.
	keytool -keystore $i.truststore.jks -alias CARoot -import -file snakeoil-ca-1.crt -storepass acroteq -keypass acroteq -noprompt

  echo "acroteq" > ${i}_sslkey_creds
  echo "acroteq" > ${i}_keystore_creds
  echo "acroteq" > ${i}_truststore_creds
done
