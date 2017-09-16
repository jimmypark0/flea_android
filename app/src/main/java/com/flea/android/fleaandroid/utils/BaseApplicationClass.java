package com.flea.android.fleaandroid.utils;

import android.app.Application;

import com.estimote.sdk.Region;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by jerryjung on 2017. 9. 16..
 */

public class BaseApplicationClass extends Application {
    public static final Region ALL_ESTIMOTE_BEACONS_REGION = new Region("rid", null, null, null);
    public static final int ALL_ESTIMOTE_BEACONS_MINOR[] = {38547, 16501, 978};
    public static final int BEACONS_RSSI = -60;

    //
    public static final String TITLE =  "2017 취업박람회";
    public static final String ITRO =  "35년간 유학업계를 선도하며 \n" +
            "변함없이 안심유학의 길을 걸어온 종로유학원이 \n" +
            "오는 9월 16일(토)에 코엑스에서 \n" +
            "2017 해외유학박람회를 개최합니다.\n" +
            "많은 우수 학교들이 참가하는 이번 박람회를 통해 어학연수, 해외대학 진학, 초중고 유학 등 다양한 프로그램 정보를 만나실 수 있습니다.\n" +
            "\n" +
            "총 10개국 어학연수 및 유학 상담이 가능합니다. 국가별 전문가와 1:1 상담을 받아보세요.";

    public static final String TextViewTitle[] = {"우아한 형제들", "푸드테크", "마켓컬리"};
    public static final String TextViewDescription[] = {
            "‘좋은 음식을 먹고 싶은 곳에서’ 라는\n" +
            "비전 아래 배달의민족, 배민라이더스, 배민찬 등을 서비스하며 종합 ‘푸드테크’ 기업으로\n" +
            "나아가고 있습니다.\n" + "\n" +
            "우리는 ‘구성원을 행복하게 만들면 행복한 구성원이 더 좋은 서비스를 만든다’ 는 믿음으로 성장의 중심에는 코드 덩어리가 아닌 " +
            "\u0003가치를 만들고 스스로 가치를 높이며 일하는\n" +
            "우아한 개발자들이 있습니다.",

            "외식 배달매장에 필요한 다양한 채널의 주문 조회\n" + "및 배달 대행 요청 서비스.\n" +
            "원터치로 신속, 정확하게 입력할 수 있는 POS 솔루션 입니다.\n\n" +
            "주문에서 배달까지 푸드테크 중개플랫폼으로 한방에 처리하고 관리합니다.\n" +
            "배달 POS와 연동되는 매장관리 Total 서비스를 스마트폰을 통해서 관리 할 수 있는,\n" +
            "사장님 전용 서비스를 제공합니다.",

            "마켓컬리는 맛있는 음식이 삶의 행복이라고\n" + "굳게 믿는 사람들끼리 뜻을 합쳐 만든 팀입니다.\n" +
            "비싼 식자재만이 좋은 음식일 것이라고 막연하게 생각하고 있는\n" +
            "소비자에게는 진짜 맛을 소개해드리고 싶었고, 뚝심 하나로 산골 오지에서\n" +
            "수십 년간 묵묵히 장을 담그는 명인, 시들어서 버릴지언정 무농약을 고집하는 농부에게는\n" +
            "안정적인 판매 활로를 찾아드리고 싶었습니다.\n"};
    public static final String TextViewHashTags[] = {"#개발 #디자인 #기획 #해커톤 #오픈소스", "#개발 #기획 #회계", "#개발 #기획 #디자인"};

    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/NanumGothic.otf")
                .build());
    }
}
