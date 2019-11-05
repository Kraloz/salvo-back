#!/usr/bin/env bash

cd .. ; cd frontend
npm run build
cd .. ; cd backend
echo "frontend compiled, now you can run : ./gradlew bootRun"
echo -e "\n"
echo "LISTO EL POLLOOO"