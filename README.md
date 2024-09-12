###
docker run --name petCenter -e POSTGRES_DB=petCenter -e POSTGRES_USER=root -e POSTGRES_PASSWORD=secret -p 5432:5432 -d postgres

TODO:
        Auth;
        AdoptionForm;
        Embeddable de Info de Chip (implica criar entity de Vet);
        Em vez de null nos campos de opcionais valor default;
        CODE CLEANUP;
        Testes;