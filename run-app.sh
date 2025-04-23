#!/bin/bash

#db_url="jdbc:postgresql://localhost:26257/kafka_demo?sslmode=disable"
#db_user=root
#db_password=cockroach

spring_profile="default"

####################################
# Do not edit past this line
####################################

if [ -n "$db_url" ]; then
  java -jar target/kafka-demo.jar \
  -Dspring.datasource.url="${db_url}" \
  -Dspring.datasource.username=${db_user} \
  -Dspring.datasource.password=${db_password} \
  -Dspring.profiles.active="${spring_profile}" \
  $*
else
  java -jar target/kafka-demo.jar \
  -Dspring.profiles.active="${spring_profile}" \
  $*
fi
