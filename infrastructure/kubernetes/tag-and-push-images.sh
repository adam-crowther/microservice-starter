gcloud auth login

docker tag com.ticketing/order.service:$1 europe-west4-docker.pkg.dev/ticketing-346904/ticketing-repository/order.service:$1
docker tag com.ticketing/payment.service:$1 europe-west4-docker.pkg.dev/ticketing-346904/ticketing-repository/payment.service:$1
docker tag com.ticketing/airline.service:$1 europe-west4-docker.pkg.dev/ticketing-346904/ticketing-repository/airline.service:$1
docker tag com.ticketing/customer.service:$1 europe-west4-docker.pkg.dev/ticketing-346904/ticketing-repository/customer.service:$1

docker push europe-west4-docker.pkg.dev/ticketing-346904/ticketing-repository/order.service:$1
docker push europe-west4-docker.pkg.dev/ticketing-346904/ticketing-repository/payment.service:$1
docker push europe-west4-docker.pkg.dev/ticketing-346904/ticketing-repository/airline.service:$1
docker push europe-west4-docker.pkg.dev/ticketing-346904/ticketing-repository/customer.service:$1

