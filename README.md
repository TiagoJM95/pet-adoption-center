###
docker run --name petCenter -e POSTGRES_DB=petCenter -e POSTGRES_USER=root -e POSTGRES_PASSWORD=secret -p 5432:5432 -d postgres

TODO:
        AOP BeforeEach - checkDbConnection();
        Excepções todas com Messages;
        Mudar Utils para "DBConnection";
        Mudar endpoints de Pet para filtrar por species e location;
        Add pets em lista;
        Sort de Pets por date;
        Pageable em todos os endpoints;
        Validations;
        CODE CLEANUP;
        Testes;