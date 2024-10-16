package com.petadoption.center.util;

public class Messages {

    // NOT FOUND MESSAGES
    public static final String USER_NOT_FOUND = "User with id {%s} was not found.";
    public static final String ORG_NOT_FOUND = "Organization with id {%s} was not found.";
    public static final String PET_NOT_FOUND = "Pet with id {%s} was not found.";
    public static final String BREED_NOT_FOUND = "Breed with id {%s} was not found.";
    public static final String SPECIES_NOT_FOUND = "Species with id {%s} was not found.";
    public static final String SPECIES_NOT_FOUND_NAME = "Species with name {%s} was not found.";
    public static final String COLOR_NOT_FOUND = "Color with id {%s} was not found.";
    public static final String INTEREST_NOT_FOUND = "Interest with id {%s} was not found.";
    public static final String ADOPTION_FORM_NOT_FOUND = "Adoption form with id {%s} was not found.";

    // DELETE MESSAGES
    public static final String USER_DELETE_MESSAGE = "User with id {%s} deleted successfully";
    public static final String ORG_DELETE_MESSAGE = "Organization with id {%s} deleted successfully";
    public static final String PET_DELETE_MESSAGE = "Pet with id {%s} deleted successfully";
    public static final String BREED_DELETE_MESSAGE = "Breed with id {%s} deleted successfully";
    public static final String SPECIES_DELETE_MESSAGE = "Species with id {%s} deleted successfully";
    public static final String COLOR_DELETE_MESSAGE = "Color with id {%s} deleted successfully";
    public static final String INTEREST_DELETE_MESSAGE = "Interest with id {%s} deleted successfully";
    public static final String ADOPTION_FORM_DELETE_MESSAGE = "Adoption form with id {%s} deleted successfully";

    // STATUS MESSAGES
    public static final String INVALID_STATUS_CHANGE_SAME = "This status is already set.";
    public static final String INVALID_STATUS_CHANGE_FINAL = "Status change is no longer allowed.";
    public static final String INVALID_STATUS_CHANGE = "Invalid status change.";

    // MISC EXCEPTION MESSAGES
    public static final String DB_CONNECTION_ERROR = "Something went wrong with the connection to the database.";
    public static final String BREED_SPECIES_MISMATCH = "The breed {%s} does not belong to the species {%s}.";

    // LOGGER MESSAGES
    public static final String LOGGER_NOT_FOUND = "ModelNotFoundException - Entity was not found: {} ";
    public static final String LOGGER_DB_CONNECTION = "DatabaseConnectionException - Problem connecting to DB: {} ";
    public static final String LOGGER_EXCEPTION = "Exception - Uncaught Exception: {} ";
    public static final String LOGGER_NO_BODY = "HttpMessageNotReadableException - No body provided: {} ";
    public static final String LOGGER_DUPLICATE = "DataIntegrityViolationException - DB unique constraints violation: {} ";
    public static final String LOGGER_VALIDATION = "MethodArgumentNotValidException - Provided body failed validation: {} ";
    public static final String LOGGER_LIST_VALIDATION = "HandlerMethodValidationException - Provided list failed validation: {} ";
    public static final String LOGGER_MISMATCH = "BreedMismatchException - Breed does not belong to species: {} ";
    public static final String LOGGER_STATUS = "InvalidStatusChangeException - New value provided for status is not possible: {} ";

    // VALIDATION MESSAGES
    public static final String BLANK_FIELD = "This field cannot be blank";
    public static final String CHARACTERS_LIMIT = "Must be less than 100 characters";
    public static final String STREET_CHARACTERS = "Special characters allowed are  , . - ";
    public static final String ONLY_LETTERS = "Only letters are allowed";
    public static final String ONLY_NUMBERS = "Only numbers are allowed";
    public static final String ONLY_UUID = "Only alphanumeric characters and - are allowed";
    public static final String POSTAL_CODE_FORMAT = "Postal Code must be in the format XXXX-XXX";
    public static final String POSTAL_CODE_SIZE = "Postal Code must be less than 9 characters";
    public static final String PHONE_NUMBER_FORMAT = "Phone number must be only numbers";
    public static final String PHONE_NUMBER_SIZE = "Phone number must be less than 10 characters";
    public static final String LETTERS_AND_NUMBERS = "Only letters and numbers are allowed";
    public static final String WEBSITE_URL = "Special characters allowed are  . - ";
    public static final String FACEBOOK_VALID = "Special characters allowed are  . - / ";
    public static final String INSTAGRAM_VALID = "Special characters allowed are  . - @ ";
    public static final String TWITTER_VALID = "Special characters allowed are  . - / ";
    public static final String YOUTUBE_VALID = "Special characters allowed are  . - / ";
    public static final String REQUIRED_FIELD = "This field is required.";
    public static final String AGE_INVALID = "Invalid age description: {0}";
    public static final String COAT_INVALID = "Invalid coat description: {0}";
    public static final String SIZE_INVALID = "Invalid size description: {0}";
    public static final String GENDER_INVALID = "Invalid gender description: {0}";
    public static final String STATUS_INVALID = "Invalid status description: {0}";

    //PET SERVICE MESSAGES
    public static final String ADDED_TO_FAVORITE_SUCCESS = "Pet with id {%s} successfully added to favorites";
    public static final String REMOVED_FROM_FAVORITE_SUCCESS = "Pet with id {%s} successfully removed from favorites";
    public static final String PET_LIST_ADDED_SUCCESS = "All the pets in the provided list were successfully created";
}