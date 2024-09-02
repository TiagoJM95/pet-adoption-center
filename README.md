###
docker run --name petCenter -e POSTGRES_DB=petCenter -e POSTGRES_USER=root -e POSTGRES_PASSWORD=secret -p 5432:5432 -d postgres

TODO:
        Remover find**ById de public;
        Enums atiram excepções;
        Enums criar ranges (ages, size)
        Auth;
        AdoptionForm;
        CODE CLEANUP;
        Testes;