#!/bin/bash

#db_url="jdbc:postgresql://localhost:26257/kafka_demo?sslmode=disable"
#db_user=root
#db_password=cockroach
bootstrap_servers="localhost:9092"
spring_profile="default"

####################################
# Do not edit past this line
####################################

PS3='Please select test class: '

unset options i
while IFS= read -r -d $'\0' f; do
  options[i++]="$f"
done < <(find src/test/java -name '*Test.java'  -print0 )

select opt in "${options[@]}" "Quit"; do
  case $opt in
    *.java)
      echo "Test class $opt selected"
      break
      ;;
    "Quit")
      exit 0
      ;;
    *)
      echo "Try again!"
      ;;
  esac
done

testClass=$(echo $opt --| awk -F'/' '{print $NF}' | sed 's/\.[^.]*$//')

if [ -n "$db_url" ]; then
./mvnw -DskipTests=false -Dgroups=it-test -Dtest=$testClass \
  -Dspring.datasource.url="${db_url}" \
  -Dspring.datasource.username=${db_user} \
  -Dspring.datasource.password=${db_password} \
  -Dspring.profiles.active="${spring_profile}" \
  -Dspring.kafka.bootstrap-servers="${bootstrap_servers}" \
  -Dspring.flyway.placeholders.cdc-sink-url="kafka://${bootstrap_servers}" \
  test
else
./mvnw -DskipTests=false -Dgroups=it-test -Dtest=$testClass \
  -Dspring.profiles.active="${spring_profile}" \
  -Dspring.kafka.bootstrap-servers="${bootstrap_servers}" \
  -Dspring.flyway.placeholders.cdc-sink-url="kafka://${bootstrap_servers}" \
  test
fi
