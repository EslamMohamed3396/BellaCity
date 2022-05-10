package com.bellacity.utilities


object Constant {


    //region url and routs
    // const val URL = "http://bellabanha.com:801"
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
    const val TEXT_FROM_IMAGE = BASE_ROUTS + "GetTextFromImage"
    const val CHECK_SERIAL = BASE_ROUTS + "ValidateSerial"
    const val GRNT_ITEMS = BASE_ROUTS + "GetGrntItemList"
    const val ADD_GRNT = BASE_ROUTS + "AddGrnt"
    const val EDIT_GRNT = BASE_ROUTS + "EditGrnt"
    const val GET_GRNT = BASE_ROUTS + "GetGrntSimpleList"
    const val DELETE_GRNT = BASE_ROUTS + "DeleteGrnt"
    const val CHECK_COBON_LIMIT = BASE_ROUTS + "CheckCoubonLimit"
    const val SUPPLEMENT_ITEMS = BASE_ROUTS + "GetItemsList"
    const val GET_CLIENT_LIST = BASE_ROUTS + "GetClientList"
    const val GET_DELIVERY_AGENT_LIST = BASE_ROUTS + "GetDeliveryAgentsList"
    const val GET_DRIVER_LIST = BASE_ROUTS + "GetDriversList"
    const val GET_STORAGE_LIST = BASE_ROUTS + "GetItemsStorageList"
    const val GET_EXTRA_OPTIONS = BASE_ROUTS + "GetExtraOptions"
    const val CALCULATE_DISCOUNT = BASE_ROUTS + "CalculateInvoiceAmount"
    const val ADD_INVOICE = BASE_ROUTS + "AddInvoice"
    const val GET_INVOICES_LIST = BASE_ROUTS + "GetSimpleInvoiceList"
    const val GET_INVOICES_DETAILS = BASE_ROUTS + "GetInvoice"

    //endregion


    //shared Preference
    const val PRIVATE_MODE = 0
    const val sharedPrefFile = "bella_city"

    //shared Preference Key

    const val IS_USER_LOGIN = "login_key"
    const val USER_DATA_KEY = "user_data_key"

    //region error
    const val ERROR_DIALOG_REQUEST = 9004


    //CHOOSE TYPE
    //5, مبيعات البولي
    // 55, مبيعات قطعية
    //57, مبيعات الصرف الأبيض
    //89, مبيعات الرمادي
    // 81, مبيعات المشروعات صرف
    //84, مبيعات قطعية مشروعات
    //78, مبيعات المشروعات بولي
    //92, مبيعات المشروعات رمادي
    //34, تفعيل المعاينات
    const val MABE3AT_MASHRO3AT_BOLY = 78
    const val MABE3AT_MASHRO3AT_SARF = 81
    const val MABE3AT_MASHRO3AT_RAMADY = 92
    const val MABE3AT_MASHRO3AT_KATA3Y = 84

    const val MABE3AT_TOGARY_BOLY = 5
    const val MABE3AT_TOGARY_SARF = 57
    const val MABE3AT_TOGARY_RAMADY = 89
    const val MABE3AT_TOGARY_KATA3Y = 55

    const val MO3AYNAT = 34


    //screen Id

    var SCREEN_ID = 0

}