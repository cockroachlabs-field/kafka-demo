<p>	
	<a href="https://github.com/cockroachlabs-field/kafka-demo/actions/workflows/maven.yml"><img src="https://github.com/cockroachlabs-field/kafka-demo/actions/workflows/maven.yml/badge.svg?branch=main" alt="">
</p>

<!-- TOC -->
* [About](#about)
* [Compatibility](#compatibility)
* [Setup](#setup)
  * [Prerequisites](#prerequisites)
  * [Install the JDK](#install-the-jdk)
  * [Database Setup](#database-setup)
  * [Kafka Setup](#kafka-setup)
  * [Building](#building)
    * [Clone the project](#clone-the-project)
    * [Build the artifact](#build-the-artifact)
* [Running](#running)
* [Terms of Use](#terms-of-use)
<!-- TOC -->

# About

CockroachDB and Kafka demo through the inbox and outbox patterns.

# Compatibility

- MacOS
- Linux
- JDK 21+ (LTS)
- CockroachDB v24.3+

# Setup

Things you need to build and run the modules locally.

## Prerequisites

- Java 21+ JDK
    - https://openjdk.org/projects/jdk/21/
    - https://www.oracle.com/java/technologies/downloads/#java21
- CockroachDB v24.3+
  - https://www.cockroachlabs.com/docs/releases/
- Kafka
  - https://kafka.apache.org
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

## Kafka Setup

You can either use a manged Kafka cluster or a local self-hosted setup. In the latter case,
just follow the [quickstart](https://kafka.apache.org/quickstart) guidelines to setup
a vanilla Kafka instance.

Ensure kafka is available to the app services and CockroachDB nodes. 
The default url is `kafka://localhost:9092`.

Example setup:

    curl https://dlcdn.apache.org/kafka/4.0.0/kafka_2.13-4.0.0.tgz -o kafka_2.13-4.0.0.tgz
    tar -xzf kafka_2.13-4.0.0.tgz
    ln -s kafka_2.13-4.0.0 current
    cd current
    KAFKA_CLUSTER_ID="$(bin/kafka-storage.sh random-uuid)"
    bin/kafka-storage.sh format -t $KAFKA_CLUSTER_ID -c config/server.properties

Depending on your network setup you may need to edit the following Socket 
properties in `config/server.properties`:

    listeners=PLAINTEXT://..
    advertised.listener=PLAINTEXT://

Start the daemon:

    bin/kafka-server-start.sh -daemon config/server.properties

Tail any of topics, in this case `orders-outbox` (other one is `orders-inbox`):

    bin/kafka-console-consumer.sh --topic orders-outbox --from-beginning --bootstrap-server localhost:9092 --property print.key=true

## Building

### Clone the project

    git clone git@github.com:cockroachlabs-field/kafka-demo.git && cd kafka-demo

### Build the artifact

    chmod +x mvnw
    ./mvnw clean install

# Running

Run the test starter script which will present a menu of options:

    ./run-test.sh

To run the app which just prints received inbox events, run:

    ./run-app.sh

You can configure the jdbc and kafka settings in the shell files.

# Terms of Use

Use of this project is entirely at your own risk and Cockroach Labs makes no guarantees or warranties about its operation.

See [MIT](LICENSE.txt) for terms and conditions.

