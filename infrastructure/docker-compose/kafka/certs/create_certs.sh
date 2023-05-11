#!/bin/bash

set -o nounset \
    -o errexit

PASS=acroteq

create_certificates () {

  echo
  echo "=================================================================="
	echo "${1}"
  echo

  mkdir -p "${1}"

  # Generate a keystore
  keytool -genkey -noprompt \
         -alias "${1}" \
         -dname "CN=${1}, OU=TEST, O=Acroteq, L=Zurich, S=ZH, C=CH" \
         -keystore "${1}/${1}.keystore.jks" \
         -keyalg RSA \
         -storepass ${PASS} \
         -keypass ${PASS}

  # Create truststore and import the CA cert.
  keytool -keystore "${1}/${1}.truststore.jks" -alias CARoot -import -file ca.crt -storepass ${PASS} -keypass ${PASS} -noprompt

  # Generate a certificate
  keytool -keystore "${1}/${1}.keystore.jks" -alias "${1}" -certreq -file "${1}/${1}.csr" -storepass ${PASS} -keypass ${PASS}

  # Sign the certificate using the CA.
  openssl x509 -req -CA ca.crt -CAkey ca.key -in "${1}/${1}.csr" -out "${1}/${1}-ca1-signed.crt" -days 9999 -CAcreateserial -passin pass:${PASS}

  # Import CA Certificate into the keystore.
  keytool -keystore "${1}/${1}.keystore.jks" -alias CARoot -import -file ca.crt -storepass ${PASS} -keypass ${PASS} -noprompt

  # Import Signed Certificate to the keystore.
  keytool -keystore "${1}/${1}.keystore.jks" -alias "${1}" -import -file "${1}/${1}-ca1-signed.crt" -storepass ${PASS} -keypass ${PASS} -noprompt

  # Export the certificate into the binary DER file.
  keytool -exportcert -keystore "${1}/${1}.keystore.jks" -alias "${1}" -file "${1}/${1}.der" -storepass ${PASS} -keypass ${PASS} -noprompt

  echo "${PASS}" > "${1}/${1}_sslkey_creds"
  echo "${PASS}" > "${1}/${1}_keystore_creds"
  echo "${PASS}" > "${1}/${1}_truststore_creds"
}

# Create a certificate authority for signing.
openssl req -new -x509 -keyout ca.key -out ca.crt -days 365 -subj '//x=1/CN=ca1.test.acroteq.ch/OU=TEST/O=Acroteq/L=Zurich/S=ZH/C=CH' -passin pass:${PASS} -passout pass:${PASS}

create_certificates "kafka-broker"

# Create client keystores
for i in localhost airline-approval airline-mdm customer-mdm order-service payment-service schema-registry kafkacat
do
  create_certificates "${i}"
done
