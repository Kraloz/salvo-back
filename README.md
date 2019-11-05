# salvo-back

Backend server for Battleship web game

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

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
In order to compile the frontend files into the backend server you will need to do:

- `cd frontend`
- `npm run build`
- `cd .. ; cd backend`
- `./gradlew bootRun`