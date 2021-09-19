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
    const val TECH_LIST = BASE_ROUTS + "GetTechList"
    const val DISTRIBUTOR_LIST = BASE_ROUTS + "GetDistributorList"
    const val BOOK_NUMBER_LIST = BASE_ROUTS + "GetBookNoList"
    const val PRODUCT_TYPE_LIST = BASE_ROUTS + "GetGrntItemsTypeList"
    const val ACTIVE_TYPE_LIST = BASE_ROUTS + "GetGrntTypeList"
    const val COBON_LIST = BASE_ROUTS + "GetCobonList"

    //endregion


    //shared Preference
    const val PRIVATE_MODE = 0
    const val sharedPrefFile = "bella city"

    //shared Preference Key

    const val IS_USER_LOGIN = "login_key"
    const val USER_DATA_KEY = "user_data_key"


}