package springProj.safeRestaurant.domain;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="freeboard")
@DynamicInsert // 넣을때 null인값을 제외시켜줌 date값을 입력하지 않으면 null들어가는데 제외 시켜주므로써 DB에서 자동으로 디폴트값으로 설정해줌
public class FreeBoardVO {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long bno; // id

    @Column(name="title")
    private String title; // 제목

    @Column(name="content")
    private String content; // 내용

    @Column(name="writer")
    private String writer; // 작성자

    @Column(name="regdate") @Temporal(TemporalType.DATE)
    private Date regdate; // 작성일자

    @Column(name="viewcnt")
    private long viewcnt; // 조회수

    @Column(name="replycnt")
    private long replycnt; // 댓글수

    @Column(name="file_name")
    private String fileName;

    @Column(name="org_file_name")
    private String originFileName;

    @Transient // DB에 적용안됨
    private MultipartFile uploadFile;


    public String getOriginFileName() {
        return originFileName;
    }

    public void setOriginFileName(String originFileName) {
        this.originFileName = originFileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public MultipartFile getUploadFile() {
        return uploadFile;
    }

    public void setUploadFile(MultipartFile uploadFile) {
        this.uploadFile = uploadFile;
    }

    public long getBno() {
        return bno;
    }

    public void setBno(long bno) {
        this.bno = bno;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public Date getRegdate() {
        return regdate;
    }

    public void setRegdate(Date regdate) {
        this.regdate = regdate;
    }

    public long getViewcnt() {
        return viewcnt;
    }

    public void setViewcnt(long viewcnt) {
        this.viewcnt = viewcnt;
    }

    public long getReplycnt() {
        return replycnt;
    }

    public void setReplycnt(long replycnt) {
        this.replycnt = replycnt;
    }
}

