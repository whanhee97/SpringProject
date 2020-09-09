package springProj.safeRestaurant.domain;

public class Restaurant {
    private Long safetyNo;
    private String logt; //경도
    private String lat; //위도
    private String sido; //시도명
    private String signgu; //시군구명
    private String name; //사업장명
    private String address; //주소
    private String detailAddress; //상세주소
    private String induType; // 업종
    private String detailType; // 업종 상세명
    private String tell; //전화번호
    private String DTD ; //데이터 수집일자
    private String doroName; //도로명 주소
    private String jibeon; //지번 주소
    private String zipNo; //우편번호

    public Long getSafetyNo() {
        return safetyNo;
    }

    public void setSafetyNo(Long safetyNo) {
        this.safetyNo = safetyNo;
    }

    public String getLogt() {
        return logt;
    }

    public void setLogt(String logt) {
        this.logt = logt;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getSido() {
        return sido;
    }

    public void setSido(String sido) {
        this.sido = sido;
    }

    public String getSigngu() {
        return signgu;
    }

    public void setSigngu(String signgu) {
        this.signgu = signgu;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public String getInduType() {
        return induType;
    }

    public void setInduType(String induType) {
        this.induType = induType;
    }

    public String getTell() {
        return tell;
    }

    public void setTell(String tell) {
        this.tell = tell;
    }

    public String getDTD() {
        return DTD;
    }

    public void setDTD(String DTD) {
        this.DTD = DTD;
    }

    public String getDoroName() {
        return doroName;
    }

    public void setDoroName(String doroName) {
        this.doroName = doroName;
    }

    public String getJibeon() {
        return jibeon;
    }

    public void setJibeon(String jibeon) {
        this.jibeon = jibeon;
    }

    public String getZipNo() {
        return zipNo;
    }

    public void setZipNo(String zipNo) {
        this.zipNo = zipNo;
    }

    public String getDetailType() {
        return detailType;
    }

    public void setDetailType(String detailType) {
        this.detailType = detailType;
    }
}
