# salvo-back

Backend server for Battleship web game

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

This proyect uses **Java 11**


### Installing

First you will need to clone this repo and 
[frontend repo](https://github.com/Kraloz/salvo-front/) in the **same directory level**

```
salvo-project/
 |
 |----backend/
 |----frontend/
```
In order to compile the frontend files into the backend server run the script:

`sudo chmod +x deploy-front.sh` - Makes the script executable

`./deploy-front.sh` - Run the script

**Windows users:** you will need _git bash_ or _cygwin_ in order to run the scripts

## Run server

`./gradlew bootRun`
