# Get Started With Docker

- install Docker Desktop 
- Set up network for container-to-container communication:
  - ```docker network create sensor_server --driver bridge```
  - ```docker network inspect sensor_server```
- Setup RabbitMQ
  - ```docker run --hostname rabbitmq --name rabbit-server --network sensor_server -p 15672:15672 -p 5672:5672 rabbitmq:management```
  - go to ```http://localhost:15672/#/``` to see dashboard
- Build from Dockerfile
  - ```docker build -t sensor-service .```
  - ```docker run --rm -p 9000:9000 --network sensor_server --name sensor-service sensor-service```
