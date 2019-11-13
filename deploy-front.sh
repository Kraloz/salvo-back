#!/usr/bin/env bash

cd .. ; cd frontend

if [ ! -d "./node_modules" ]
then
    npm install
else
    echo "OK - node_modules exist"
fi

npm run build
echo "frontend compiled, now you can run : ./gradlew bootRun"
echo -e "\n"
echo "LISTO EL POLLOOO"
