MAIN DB
docker run --name petCenter -e POSTGRES_DB=petCenter -e POSTGRES_USER=root -e POSTGRES_PASSWORD=secret -p 5432:5432 -d postgres

REDIS
docker run -d --name petRedis -p 6379:6379  redis