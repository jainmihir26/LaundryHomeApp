package com.example.stet.Helper;

public class Urls {
    //final  public static String mainUrl="http://192.168.0.107:8001/apis/";
    private final static String mainUrl="http://192.168.0.102:8000/apis/";
    final public static String urlLogin = mainUrl+"login/";
    final public static String mapsUrl=mainUrl+"add_address/";
    final public static String verifyOtpUrl=mainUrl+"verify_otp/";
    final public static String registerUrl=mainUrl+"register/";
    final public static String dataFromCartUrl=mainUrl+"put_cart/";
    final public static String PickDropUrl=mainUrl+"put_order/";
    final public static String promotionUrl=mainUrl+"fetch_promo/";
    final public static String PriceObjUrl=mainUrl+"get_price_list/";
    public static String logoutUrl=mainUrl+"logout/";
    public static String getOrderUrl=mainUrl+"fetch_order/";
    public static String generateOtpUrl=mainUrl+"generate_otp/";
    public static String changePassword=mainUrl+"change_password/";
    final public static String FetchAddress=mainUrl+"fetch_addresses/";
    final public static String RemoveAddress=mainUrl+"remove_address/";
    public static String changeMe=mainUrl+"change_url/";
    public static String updateProfile=mainUrl+"update_profile/";
    public static String PaymentConf=mainUrl+"payment_status_by_api/";
    public static String otpForUpdatePhone=mainUrl+"otp_for_update_phone/";
    public static String updatePhoneNo=mainUrl+"update_phone_no/";

}
