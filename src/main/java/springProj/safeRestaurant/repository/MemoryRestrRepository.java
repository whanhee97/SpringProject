package springProj.safeRestaurant.repository;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Repository;
import springProj.safeRestaurant.domain.Restaurant;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MemoryRestrRepository implements RestaurantRepository{

    private List<Restaurant> store = new ArrayList<>();

    public MemoryRestrRepository() throws Exception {
        this.store = this.getRestrList();
    }

    @Override
    public void save(Restaurant restaurant) {
        store.add(restaurant);
    }

    @Override
    public List<Restaurant> findAll() {
        return this.store;
    }

    private List<Restaurant> getRestrList() throws Exception { //파싱 받은 JSONArray(레스토랑 목록)를 레스토랑 객체 리스트로 만들어줌
        JSONArray restrList = this.ParseRestaurants(); //restrList는 JSON 배열이고 파싱한 레스토랑 리스트를 JSON배열에 담아옴
        List<Restaurant> list = new ArrayList<>();

        Long safetyNo;
        String logt; //경도
        String lat; //위도
        String sido; //시도명
        String signgu; //시군구명
        String name; //사업장명
        String address; //주소
        String detailAddress; //상세주소
        String induType; // 업종
        String detailType; // 업종 상세명
        String tell; //전화번호
        String DTD ; //데이터 수집일자
        String doroName; //도로명 주소
        String jibeon; //지번 주소
        String zipNo; //우편번호

        for(int i = 0; i< restrList.size(); i++){
            Restaurant temp = new Restaurant();
            JSONObject restrnt = (JSONObject) restrList.get(i);
            safetyNo = (Long)restrnt.get("SAFETY_RESTRNT_NO");
            lat = (String)restrnt.get("REFINE_WGS84_LAT");
            logt = (String)restrnt.get("REFINE_WGS84_LOGT");
            sido = (String)restrnt.get("SIDO_NM");
            signgu = (String)restrnt.get("SIGNGU_NM");
            name = (String)restrnt.get("BIZPLC_NM");
            address = (String)restrnt.get("ADDR");
            detailAddress = (String)restrnt.get("DETAIL_ADDR");
            induType = (String)restrnt.get("INDUTYPE_NM");
            detailType = (String)restrnt.get("INDUTYPE_DETAIL_NM");
            tell = (String)restrnt.get("TELNO");
            DTD = (String)restrnt.get("DATA_COLCT_DE");
            doroName = (String)restrnt.get("REFINE_ROADNM_ADDR");
            jibeon = (String)restrnt.get("REFINE_LOTNO_ADDR");
            zipNo = (String)restrnt.get("REFINE_ZIPNO");

            temp.setSafetyNo(safetyNo);
            temp.setLat(lat);
            temp.setLogt(logt);
            temp.setSido(sido);
            temp.setSigngu(signgu);
            temp.setName(name);
            temp.setAddress(address);
            temp.setDetailAddress(detailAddress);
            temp.setInduType(induType);
            temp.setDetailType(detailType);
            temp.setTell(tell);
            temp.setDTD(DTD);
            temp.setDoroName(doroName);
            temp.setJibeon(jibeon);
            temp.setZipNo(zipNo);

            list.add(temp);
        }
        return list;
    }

    private JSONArray ParseRestaurants() throws Exception {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(readUrl());
        JSONArray SafetyRestrntInfo = (JSONArray) jsonObject.get("SafetyRestrntInfo");
        JSONObject realObj = (JSONObject) SafetyRestrntInfo.get(1);
        JSONArray restaurants = (JSONArray) realObj.get("row");
        return restaurants;
    }

    private static String readUrl() throws Exception{
        BufferedReader reader = null;
        try {
            URL url = new URL("https://openapi.gg.go.kr/SafetyRestrntInfo?KEY=c2ac3f880cbc4f3fafd0e046fb0fb7f0&Type=json&pIndex=1&pSize=1000");

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setReadTimeout(1000);
            con.connect();

            reader = new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF8"));
            String inputLine = null;
            String result = "";
            while ((inputLine = reader.readLine()) != null){
                result += inputLine;
            }
            return result;

        }finally {
            if(reader != null)
                reader.close();
        }

    }
}
