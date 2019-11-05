#!/usr/bin/env bash

cd .. ; cd frontend

if [ ! -d "./node_modules" ]
then
    npm install
else
    echo "OK - node_odules exist"
fi

npm run build
cd .. ; cd backend
echo "frontend compiled, now you can run : ./gradlew bootRun"
echo -e "\n"
echo "LISTO EL POLLOOO"
