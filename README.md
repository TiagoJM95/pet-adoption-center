MAIN DB
docker run --name petCenter -e POSTGRES_DB=petCenter -e POSTGRES_USER=root -e POSTGRES_PASSWORD=secret -p 5432:5432 -d postgres

TEST DB
docker run --name petCenterTest -e POSTGRES_DB=petCenterTest -e POSTGRES_USER=root -e POSTGRES_PASSWORD=secret -p 5455:5455 -d postgres