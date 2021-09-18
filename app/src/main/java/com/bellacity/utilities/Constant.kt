package com.bellacity.utilities


object Constant {


    //region url and routs
    const val URL = "http://173.212.218.180:22626"
    const val AUTHORIZATION = "Authorization"
    const val BEARER = "Bearer "

    const val BASE_ROUTS = "/api/BellaCity/"

    const val LOGIN = BASE_ROUTS + "Login"
    const val REFRESH_TOKEN = BASE_ROUTS + "RefreshToken"
    const val CHECK_LOGIN = BASE_ROUTS + "CheckLogin"

    const val PREVIOUS_PREVIEW = BASE_ROUTS + "GetGrntList"


    //endregion


    //shared Preference
    const val PRIVATE_MODE = 0
    const val sharedPrefFile = "bella city"

    //shared Preference Key
    const val USER_ID_DATA = "user_data_key"
    const val USER_ID_KEY = "user_id_key"
    const val USER_NAME_KEY = "user_name_key"
    const val USER_TOKEN = "user_name_key"
    const val LANGUAGE_KEY = "language_key"
    const val IS_USER_LOGIN = "login_key"
    const val USER_DATA_KEY = "user_data_key"
    const val LANG_APP = "lang"


}