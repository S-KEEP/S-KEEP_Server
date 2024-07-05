#!/bin/bash
./gradlew clean build -x test
docker buildx build --platform linux/amd64 --load --tag jeongheumchoi/skeep-server:0.0.1 .
docker push jeongheumchoi/skeep-server:0.0.1
