# About

CockroachDB and Kafka demo through the inbox and outbox patterns.

# Compatibility

- MacOS
- Linux
- JDK 21+ (LTS)
- CockroachDB v23+

# Setup

Things you need to build and run the modules locally.

## Prerequisites

- Java 21+ JDK
    - https://openjdk.org/projects/jdk/21/
    - https://www.oracle.com/java/technologies/downloads/#java21
- Git
    - https://git-scm.com/downloads/mac

## Install the JDK

MacOS (using sdkman):

    curl -s "https://get.sdkman.io" | bash
    sdk list java
    sdk install java 21.0 (use TAB to pick edition)  

Ubuntu:

    sudo apt-get install openjdk-21-jdk

## Database Setup

See [start a local cluster](https://www.cockroachlabs.com/docs/v24.2/start-a-local-cluster)
for setup instructions. You can also use CockroachDB Cloud (basic, standard or advanced).

Then create the database, for an insecure cluster:

    cockroach sql --insecure -e "create database kafka_demo"

alternatively, for a secure cluster:

    cockroach sql --certs-dir=certs -e "CREATE DATABASE kafka_demo; ALTER ROLE root WITH PASSWORD 'cockroach'"

An [enterprise license](https://www.cockroachlabs.com/docs/stable/licensing-faqs.html#obtain-a-license) is needed for some of the chapters that 
use enterprise features like follower reads and CDC.

## Building

### Clone the project

    git clone git@github.com:cockroachlabs-field/kafka-demo.git && cd kafka-demo

### Build the artifact

    chmod +x mvnw
    ./mvnw clean install

# Running

Run the test starter script which will present a menu of options:

    ./run-test.sh

# Terms of Use

Use of this project is entirely at your own risk and Cockroach Labs makes no guarantees or warranties about its operation.

See [MIT](LICENSE.txt) for terms and conditions.

