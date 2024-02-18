# Get Started With Docker

- install Docker Desktop 
- Set up network for container-to-container communication:
  - ```docker network create sensor_server --driver bridge```
  - ```docker network inspect sensor_server```
- run redis connected to previously made network and listening to default port. "redis-stack" is the name of server on bridge. "-p 8001:8001" is the port for redis insights, which you can browse to in the browser.
  - ```docker run -d --name redis-stack -p 0.0.0.0:6379:6379 -p 8001:8001 --network redis_server redis/redis-stack:latest```
- run this SBT project in docker (sbt has built in docker plugin)
  - ```sbt docker:publishLocal```
  - ```docker run --rm -p 9000:9000 --network redis_server toptal-project:0.1```
- now that we have a dockerfile, we can do
  - ```docker build -t sensor-service .```
  - ```docker run --rm -p 9000:9000 --network sensor_server --name sensor-service sensor-service```
- Setup RabbitMQ
  - ```docker run --hostname rabbitmq --name rabbit-server --network sensor_server -p 15672:15672 -p 5672:5672 rabbitmq:management```
  - go to ```http://localhost:15672/#/``` to see dashboard