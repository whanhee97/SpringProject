# SpringProject
프로젝트 명 
>안심식당 찾기 서비스
----------------------------
## 프로그램 실행 방법
https://start.spring.io<br>

Gradle로 빌드
 - Spring Boot: 2.3.X
 - JAVA 11
 - Packaging: Jar

Project Metadata
 - group: springProj
 - artifact: safeRestaurant

```
dependencies {
	implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'
	implementation 'org.springframework.boot:spring-boot-starter-web'
	implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
	implementation 'com.googlecode.json-simple:json-simple:1.1.1'
	compile 'commons-fileupload:commons-fileupload:1.4'
	compile 'commons-io:commons-io:2.6'
	runtimeOnly 'com.h2database:h2'
	testImplementation('org.springframework.boot:spring-boot-starter-test') {
		exclude group: 'org.junit.vintage', module: 'junit-vintage-engine'
	}
}
```

**resources/application.properties**
```
spring.datasource.url=jdbc:h2:tcp://localhost/~/test
spring.datasource.driver-class-name=org.h2.Driver
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update
multipart.maxFileSiz=10MB
multipart.maxRequestSize=10MB
```

**H2 데이터베이스 사용함**
 - 테이블 정보: **sql/ddl.sql** 참고
 
-------------------------------
## 주요기능
 - 메인화면   
 ![main](/capture/main.PNG)   
 - 로그인 된 화면   
 ![logedMain](/capture/logedMain.PNG)
### 회원기능
 - 회원가입   
 ![signUp](/capture/signUp.PNG) 
 - 로그인  
 ![login](/capture/login.PNG)   
 - 회원정보(탈퇴, 회원정보 수정, 찜목록, 내가 쓴 게시물)   
 ![userInfo](/capture/userInfo.PNG)  
 - 찜목록   
 ![Mypicks](/capture/Mypicks.PNG)  
 - 내가 쓴 게시물   
 ![MyBoard](/capture/MyBoard.PNG)  
 ### 안심식당
 > 웹 크롤링을 통한 안심식당 정보 제공
 >> 경기 데이터 드림 https://data.gg.go.kr/portal/data/service/selectServicePage.do?page=1&rows=10&sortColumn=&sortDirection=&infId=YBSSB8F816M3B966AB6K30423820&infSeq=3&order=&loc=&searchWord=%EC%95%88%EC%8B%AC%EC%8B%9D%EB%8B%B9
  ![restaurantList](/capture/restaurantList.PNG)     
   - 카카오맵 api를 통한 식당 지도 제공   
  ![restaurantInfo](/capture/restaurantInfo.PNG)
 
### 게시판
 - 게시판 등록, 수정, 삭제
 - 댓글 등록, 수정, 삭제
 - 첨부파일 업로드 기능 구현
<br> ![FreeBoard](/capture/FreeBoard.PNG)
<br> ![writeBoard](/capture/writeBoard.PNG)
<br> ![BoardNReply](/capture/BoardNReply.PNG)
<br> ![modifiyBoard](/capture/modifiyBoard.PNG)

--------------------------------------------
## 추후 추가 고려 기능
 - 실시간 채팅
 - 클라우드 서비스를 통한 서버 배포
