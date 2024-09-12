###
docker run --name petCenter -e POSTGRES_DB=petCenter -e POSTGRES_USER=root -e POSTGRES_PASSWORD=secret -p 5432:5432 -d postgres

TODO:
        Remover find**ById de public;
        Enums atiram excepções;
        Enums criar ranges (ages, size)
        Auth;
        AdoptionForm;
        Potencialmente adiciona Embeddable de Info de Chip;
        Em vez de null nos campos de opcionais valor default ou obrigar frontend a mandar "none";
        No add pets em lista não adicionar nenhum se pelo menos um não passar os checks;
        CODE CLEANUP;
        Testes;