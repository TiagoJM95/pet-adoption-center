###
docker run --name petCenter -e POSTGRES_DB=petCenter -e POSTGRES_USER=root -e POSTGRES_PASSWORD=secret -p 5432:5432 -d postgres

TODO:
        Auth;
        Permitir pesquisa de raças puras (secondaryBreed = none);
        CODE CLEANUP;
        Testes;