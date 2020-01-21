package com.example.sky87.gangwon.util;

import com.example.sky87.gangwon.R;

/**
 * Created by sky87 on 2016-06-25.
 */
public class Contact {
    private static final String APIKey = "4249705a45736b7937374856464b72";

    public static final String UserName = "UserName";
    public static final String ProFileImage = "ProFileImage";
    public static final String ProFileGender = "ProFileGender";
    public static final String ProFileBirth = "ProFileBirth";
    public static final String ProFileEmail = "ProFileEmail";
    public static final String DB_FLAG = "DB_FLAG";

    private static final int Home = 0;
    private static final int Restaurants = 1;
    private static final int Wifi = 2;
    private static final int Parking = 3;
    private static final int Pension = 4;
    private static final int Hotel = 5;

    public static final String valuetest1to5 = "/1/5/";
    private static final String value1to1000 = "/1/1000/";
    private static final String value1001to2000 = "/1001/2000/";
    private static final String value2001to3000 = "/2001/3000/";
    private static final String value3001to4000 = "/3001/4000/";
    private static final String value4001to5000 = "/4001/5000/";
    private static final String value5001to6000 = "/5001/6000/";
    public static final String KeyUrl = "http://data.gwd.go.kr/apiservice/" + APIKey + "/json/";

    private static final String RestaurantFlag = "sgs-restaurants";
    private static final String RestaurantUrl = KeyUrl + RestaurantFlag;
    private static final String WifiFlag = "sgs-restaurants";
    private static final String WifiUrl = KeyUrl + WifiFlag;
    private static final String ParkingFlag = "mogaahf_oldata-parking_lot-yangyang";
    private static final String ParkingUrl = KeyUrl + ParkingFlag;
    private static final String PensionFlag = "mogaahf_oldata-lodge_pension";
    private static final String PensionUrl = KeyUrl + PensionFlag;
    private static final String HotelFlag = "localdata-tourism-staying_hotel";
    private static final String HotelFlag2 = "localdata-tourism-staying_tourism_hotel";
    private static final String HotelUrl = KeyUrl + HotelFlag;

    public static final String[] Flag_list = {"home", "restaurant", "wifi", "parking", "pension", "hotel"};
    public static final int[] Flag_Deli = {Home, Restaurants, Wifi, Parking, Pension, Hotel};
    public static final int[] Flag_layout = {
            R.layout.activity_onefragment,
            R.layout.activity_restaurant_fragment,
            R.layout.activity_wifi_fragment,
            R.layout.activity_parking_fragment,
            R.layout.activity_pension_fragment,
            R.layout.activity_hotel_fragment
    };
    public static final int[] Flag_Click = {
            Home,
            R.id.restaurant_layout,
            R.id.wifi_layout,
            R.id.parking_layout,
            R.id.pension_layout,
            R.id.hotel_layout,
    };
    public static final String[] URL = {
            "",
            RestaurantUrl,
            WifiUrl,
            ParkingUrl,
            PensionUrl,
            HotelUrl
    };
    public static final String[] FLAG = {
            "",
            RestaurantFlag,
            WifiFlag,
            ParkingFlag,
            PensionFlag,
            HotelFlag
    };

    public static final String[] Value = {
            value1to1000,
            value1001to2000,
            value2001to3000,
            value3001to4000,
            value4001to5000,
            value5001to6000
    };

    public static final String[] HomeTAG = {};
    private static final String[] HomeRest = {};
    private static final String[] RestaurantTAG = {
            "CNTNTS_AREA",              //0.지자체
            "CNTNTS_AREA2",             //1.시군
            "CNTNTS_SN",                //2.번호
            "CNTNTS_TITLE",             //3.업소명
            "TOBIZ_NM",                 //4.업종명
            "CNTNTS_ADRES",             //5.소재지 도로명주소
            "CNTNTS_ADRES2",            //6.소재지 지번주소
            "CNTNTS_TELNO",             //7.연락처
            "CNTNTS_MENU",              //8.주요메뉴
            "REPRSNT_PERSON_NM",        //9.대표자명
            "CNTNTS_PRKPLCE_AT",        //10.주차장보유여부
            "PAY_METHD",                //11.결제방법
            "CNTNTS_URL",               //12.홈페이지주소
            "CNTNTS_TXT",               //13.주변관광정보
            "LAT",                      //14.경도(WGS48좌표)
            "LNG",                      //15.위도(WGS48좌표)
            "CNTNTS_UPDE"};             //16.데이터기준일자

    private static final String[] getUsingTagRest = {
            "CNTNTS_TITLE",             //3.업소명
            "CNTNTS_ADRES",             //5.소재지 도로명주소
            "CNTNTS_TELNO",             //7.연락처
            "CNTNTS_MENU",              //8.주요메뉴
            "CNTNTS_PRKPLCE_AT",        //10.주차장보유여부
            "CNTNTS_TXT",               //13.주변관광정보
    };

    private static final String[] WifiTAG = {
            "INIT_SIDO",                //1.시도명
            "GUGUN_NM",                 //2.시군구명
            "INIT_AREA_NM",             //3.설치장소명
            "INIT_AREA_DETAIL",         //4.설치장소상세
            "INIT_SISUL_TY",            //5.설치시설구분
            "SERVICE_NM",               //6.서비스제공사명
            "WIFI_SSID",                //7.와이파이SSID
            "INIT_YM",                  //8.설치년월
            "LOCPLC_ROADNM_ADDR",       //9.소재지도로명주소
            "LOCPLC_LOTNO_ADDR",        //10.소재지지번주소
            "MANAGE_ORG_NM",            //11.관리기관명
            "ORG_TEL",                  //12.관리기관전화번호
            "LAT",                      //13.위도
            "LNG",                      //14.경도
            "DATA_BASE_DT"              //15.데이터기준일자
    };

    private static final String[] getUsingTagWifi = {
            "INIT_AREA_NM",             //3.설치장소명
            "LOCPLC_ROADNM_ADDR",       //9.소재지도로명주소
            "INIT_AREA_DETAIL",         //4.설치장소상세
    };

    private static final String[] ParkingTAG = {
            "SIDO",                 //0.시도명
            "GUGUN_NM",             //1.시군구명
            "P_NUMBER",             //2.주차장관리번호
            "PARKING_NM",           //3.주차장명
            "PARKING_PART",         //4.주차장구분
            "PARKING_TYPE",         //5.주차장유형
            "LOCPLC_LOTNO_ADDR",    //6.소재지 지번주소
            "LOCPLC_ROADNM_ADDR",   //7.소재지 도로명주소
            "P_SU",                 //8.주차구획수
            "P_AREA_PART",          //9.급지구분
            "P_NO_PART",            //10.부제시행구분
            "P_USE_WEEK",           //11.운영요일
            "P_STIME",              //12.평일 운영시작시각
            "P_ETIME",              //13.평일 운영종료시각
            "P_SAT_STIME",          //14.토요일 운영시작시각
            "P_SAT_ETIME",          //15.토요일 운영종료시각
            "P_HOLI_STIME",         //16.공휴일 운영시작시각
            "P_HOLI_ETIME",         //17.공휴일 운영종료시각
            "P_PAY",                //18.요금정보
            "P_BASE_TIME",          //19.주차기본시간
            "P_BASE_PAY",           //20.주차기본요금
            "P_ADD_TIME",           //21.추가단위시간
            "P_ADD_PAY",            //22.추가단위요금
            "P_1DAY_TIME",          //23.1일주차권요금적용시간
            "P_1DAY_PAY",           //24.1일주차권요금
            "P_MONTH_PAY",          //25.월정기권요금
            "PAYMENT_MTD",          //26.결제방법
            "PARKING_ETC",          //27.특기사항
            "MANAGE_ORG_NM",        //28.관리기관명
            "CONTACT",              //29.연락처
            "LAT",                  //30.위도(WGS84)좌표
            "LNG",                  //31.경도(WGS84)좌표
            "DATA_BASE_DT"};        //32.데이터기준일자

    private static final String[] getUsingTagPark = {
            "PARKING_NM",           //3.주차장명
            "LOCPLC_LOTNO_ADDR",    //6.소재지 지번주소
            "CONTACT",              //29.연락처
            "P_USE_WEEK",           //11.운영요일
            "P_PAY",                //18.요금정보
            "P_1DAY_PAY",           //24.1일주차권요금

    };
    private static final String[] PensionTAG = {
            "SIDO",                 //0.시도명
            "GUGUN_NM",             //1.시군구명
            "COMP_NM",              //2.업소명
            "COMPTY_NM",            //3.업종명
            "LOCPLC_ROADNM_ADDR",   //4.소재지도로명주소
            "CONTACT",              //5.연락처
            "ROOM_NUM",             //6.객실수
            "ETC_SISUL",            //7.부대시설
            "PARKINGTF",            //8.주차장보유여부
            "PAYMENT_MTD",          //9.결제방법
            "HOMEPAGE_ADDR",        //10.홈페이지주소
            "TOURINFO",             //11.주변관광정보
            "LOCPLC_LOTNO_ADDR",    //12.소재지지번주소
            "LNG",                  //13.경도(WGS84)좌표
            "LAT",                  //14.위도(WGS84)좌표
            "DATA_BASE_DT"};        //15.데이터기준일자
    private static final String[] getUsingTagPension = {
            "COMP_NM",              //2.업소명
            "LOCPLC_LOTNO_ADDR",    //12.소재지지번주소
            "CONTACT",              //5.연락처
            "ETC_SISUL",            //7.부대시설
            "PARKINGTF",            //8.주차장보유여부

    };

    private static final String[] HotelNormalTAG = {
            "NO",                       //0.번호
            "SIGUN_CD",                 //1.시군코드
            "SIGUN_NM",                 //2.시군명
            "BIZPLC_NM",                //3.사업장명
            "LOCPLC_LOTNO_ADDR",        //4.소재지지번주소
            "LOCPLC_ROADNM_ADDR",       //5.소재지도로명주소
            "LICENSG_DE",               //6.인허가일자
            "BSN_STATE_NM",             //7.영업상태명
            "CLSBIZ_DE",                //8.폐업일자
            "SUSPNBIZ_BEGIN_DE",        //9.휴업시작일자
            "SUSPNBIZ_END_DE",          //10.휴업종료일자
            "REOPENBIZ_DE",             //11.재개업일자
            "LOCPLC_AR",                //12.소재지면적
            "LOCPLC_ZIP_CD",            //13.소재지우편번호
            "BULDNG_POSESN_DIV_NM",     //14.건물소유구분명
            "YY",                       //15.년도
            "MULTI_USE_BIZESSEL_YN",    //16.다중이용업소여부
            "WSTROOM_CNT",              //17.양실수
            "SANITTN_INDUTYPE_NM",      //18.위생업종명
            "SANITTN_BIZCOND_NM",       //19.위생업태명
            "KORROOM_CNT",              //20.한실수
            "WGS84_LOGT",               //21.WGS84경도
            "WGS84_LAT",                //22.WGS84위도
            "X_CRDNT",                  //23.X좌표
            "Y_CRDNT",                  //24.Y좌표
            "DATA_COLCT_DE",            //25.데이터수집일자
            "ETL_LDADNG_DTM",           //26.ETL적재일시
            "LAT",                      //27.위도
            "LNG"};                     //28.경도

    private static final String[] HotelTourTAG = {
            "NO",                       //0.번호
            "BIZPLC_NM",                //1.사업장명
            "LOCPLC_LOTNO_ADDR",        //2.소재지지번주소
            "LOCPLC_ROADNM_ADDR",       //3.소재지도로명주소
            "LICENSG_DE",               //4.인허가일자
            "BSN_STATE_NM",             //5.영업상태명
            "CLSBIZ_DE",                //6.폐업일자
            "SUSPNBIZ_BEGIN_DE",        //7.휴업시작일자
            "SUSPNBIZ_END_DE",          //8.휴업종료일자
            "REOPENBIZ_DE",             //9.재개업일자
            "LOCPLC_AR",                //10.소재지면적
            "LOCPLC_ZIP_CD",            //11.소재지우편번호
            "BULDNG_POSESN_DIV_NM",     //12.건물소유구분명
            "YY",                       //13.년도
            "MULTI_USE_BIZESSEL_YN",    //14.다중이용업소여부
            "WSTROOM_CNT",              //15.양실수
            "SANITTN_INDUTYPE_NM",      //16.위생업종명
            "SANITTN_BIZCOND_NM",       //17.위생업태명
            "KORROOM_CNT",              //18.한실수
            "DATA_COLCT_DE",            //19.데이터수집일자
            "LAT",                      //20.위도
            "LNG",                      //21.경도
    };

    private static final String[] getUsingTagHotel = {
            "BIZPLC_NM",                //3.사업장명
            "LOCPLC_LOTNO_ADDR",        //4.소재지지번주소
    };

    public static final String[][] TAG = {
            HomeTAG,
            RestaurantTAG,
            WifiTAG,
            ParkingTAG,
            PensionTAG,
            HotelNormalTAG,
            HotelTourTAG
    };
    public static final String[][] UsingTAG = {
            HomeRest,
            getUsingTagRest,
            getUsingTagWifi,
            getUsingTagPark,
            getUsingTagPension,
            getUsingTagHotel
    };
    public static final int[] ListXML = {
            0,
            R.layout.rest_list_item,
            R.layout.rest_list_item,
            R.layout.rest_list_item,
            R.layout.rest_list_item,
            R.layout.rest_list_item
    };

    /* 재우 */
    public static final String[] getUsingtagName = {
            "",
            "CNTNTS_TITLE",  //레스토랑이름
            "INIT_AREA_NM", //와이파이이름
            "PARKING_NM",   //주차장이름
            "COMP_NM",      //펜션이름
            "BIZPLC_NM",    //호텔이름
    };
    public static final String[] getUsingtagtel = {
            "",
            "CNTNTS_TELNO", //레스토랑전화
            "ORG_TEL",      //와이파이전화
            "CONTACT",      //주차장전화
            "CONTACT",      //펜션전화
            "",             //호텔 전화
    };
    public static final String[] getUsingtagaddress = {
            "",
            "CNTNTS_ADRES",            //레스토랑 소재지 지번주소
            "LOCPLC_LOTNO_ADDR",     //와이파이 소재지 지번주소
            "LOCPLC_LOTNO_ADDR",    //주차장 소재지 지번주소
            "LOCPLC_LOTNO_ADDR",    //펜션 소재지지번주소
            "LOCPLC_LOTNO_ADDR",    //호텔소재지지번주소
    };
    public static final String[] getUsingtagetc = {
            "",
            "CNTNTS_MENU",         //레스토랑 주메뉴
            "INIT_SISUL_TY",    //와이파이 설치장소
            "P_USE_WEEK",    //주차장운행 요일
            "HOMEPAGE_ADDR",    //펜션 홈페이지 주소
            "BSN_STATE_NM",    //운영상태
    };
    public static final String[] getFolder = {
            "",
            "Restaurants",
            "Wifi",
            "Parking",
            "Pension",
            "Hotel"
    };
    public static final String[] getCity = {
            "Gangneung",
            "Goseong",
            "Donghae",
            "Samcheok",
            "Sokcho",
            "Yanggu",
            "Yangyang",
            "Yeongwol",
            "Wonju",
            "Inje",
            "Jeongseon",
            "Cheorwon",
            "Chuncheon",
            "Taebaek",
            "Pyeongchang",
            "Hongcheon",
            "Hwacheon",
            "Hoengseong"
    };
    public static final int[] floating_under_btn_id = {
            R.id.gangneung,
            R.id.goseong,
            R.id.donghae,
            R.id.samcheok,
            R.id.sokcho,
            R.id.yanggu,
            R.id.yangyang,
            R.id.yeongwol,
            R.id.wonju,
            R.id.inje,
            R.id.jungsung,
            R.id.cheorwon,
            R.id.chuncheon,
            R.id.taebaek,
            R.id.pyeongchang,
            R.id.hongcheon,
            R.id.hwacheon,
            R.id.hoengseong,
    };
}