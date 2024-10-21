package com.petadoption.center.util;

public class Regex {

    public static final String ADDRESS_NAME_REGEX = "^[a-zA-ZÀ-ÿ0-9,\\s\\-]{1,50}$";
    public static final String ORG_NAME_REGEX = "^[a-zA-ZÀ-ÿ\\s\\-]{1,50}$";
    public static final String USER_NAME_REGEX = "^[a-zA-ZÀ-ÿ\\-]{1,50}$";
    public static final String PHONE_NUMBER_REGEX = "^(91|92|93|96)\\d{7}$|^2\\d{8}$";
    public static final String DATE_REGEX = "dd/MM/yyyy";
    public static final String EMAIL_REGEX = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$";
    public static final String WEBSITE_REGEX = "^(https?://)?(www\\.)?([a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}(:\\d+)?(/.*)?$";
    public static final String TWITTER_REGEX = "^[A-Za-z0-9_]{1,15}$";
    public static final String INSTAGRAM_REGEX = "^[A-Za-z0-9._]{1,30}$";
    public static final String NIF_REGEX = "^(1|2|3)\\d{8}$";
    public static final String NIPC_REGEX = "^(5|6|8|9)\\d{8}$";
    public static final String POSTAL_CODE_REGEX = "[0-9]{4}-[0-9]{3}";
}
