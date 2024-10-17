package com.petadoption.center.util;

public class Regex {

    public static final String PHONE_NUMBER_REGEX = "^(91|92|93|96)\\d{7}$|^2\\d{8}$";
    public static final String EMAIL_REGEX = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$";
    public static final String WEBSITE_REGEX = "^(https?://)?(www\\.)?([a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}(:\\d+)?(/.*)?$";
    public static final String NIF_REGEX = "^(1|2|3)\\d{8}$";
    public static final String NIPC_REGEX = "^(5|6|8|9)\\d{8}$";
}
