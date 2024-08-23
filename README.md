###
docker run --name petCenter -e POSTGRES_DB=petCenter -e POSTGRES_USER=root -e POSTGRES_PASSWORD=secret -p 5432:5432 -d postgres

AOP Before
        checkDbConnection();

Verificar as exceções pq podem ter que receber tipos variados de argumentos. BreedServiceImpl.getBreedsBySpecies() está martelado.