package com.petadoption.center.testUtils;

public class ConstantsURL {

    private static final String URL = "/api/v1/";

    public static final String PET_GET_BY_ID_URL = URL + "pet/id/{id}";
    public static final String PET_SEARCH_URL = URL + "pet/search";
    public static final String PET_ADD_SINGLE_URL = URL + "pet/addSingle";
    public static final String PET_ADD_LIST_URL = URL + "pet/addList";
    public static final String PET_UPDATE_URL = URL + "pet/update/{id}";
    public static final String PET_DELETE_URL = URL + "pet/delete/{id}";

    public static final String USER_GET_ALL_OR_CREATE_URL = URL + "user/";
    public static final String USER_GET_BY_ID_URL = URL + "user/id/{id}";
    public static final String USER_UPDATE_URL = URL + "user/update/{id}";
    public static final String USER_DELETE_URL = URL + "user/delete/{id}";

    public static final String BREED_GET_ALL_OR_CREATE_URL = URL + "breed/";
    public static final String BREED_GET_BY_ID_URL = URL + "breed/id/{id}";
    public static final String BREED_UPDATE_URL = URL + "breed/update/{id}";
    public static final String BREED_DELETE_URL = URL + "breed/delete/{id}";

    public static final String COLOR_GET_ALL_OR_CREATE_URL = URL + "color/";
    public static final String COLOR_GET_BY_ID_URL = URL + "color/id/{id}";
    public static final String COLOR_DELETE_URL = URL + "color/delete/{id}";

    public static final String ORG_GET_ALL_OR_CREATE_URL = URL + "organization/";
    public static final String ORG_GET_BY_ID_URL = URL + "organization/id/{id}";
    public static final String ORG_UPDATE_URL = URL + "organization/update/{id}";
    public static final String ORG_DELETE_URL = URL + "organization/delete/{id}";

    public static final String ADOPTION_FORM_GET_ALL_OR_CREATE_URL = URL + "adoption-form/";
    public static final String ADOPTION_FORM_GET_BY_ID_URL = URL + "adoption-form/id/{id}";
    public static final String ADOPTION_FORM_UPDATE_URL = URL + "adoption-form/update/{id}";
    public static final String ADOPTION_FORM_DELETE_URL = URL + "adoption-form/delete/{id}";

    public static final String SPECIES_GET_ALL_OR_CREATE_URL = URL + "species/";
    public static final String SPECIES_GET_BY_ID_URL = URL + "species/id/{id}";
    public static final String SPECIES_UPDATE_URL = URL + "species/update/{id}";
    public static final String SPECIES_DELETE_URL = URL + "species/delete/{id}";
}
