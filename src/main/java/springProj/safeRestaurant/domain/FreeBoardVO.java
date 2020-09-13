package springProj.safeRestaurant.domain;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="freeboard")
@DynamicInsert // 넣을때 null인값을 제외시켜줌 date값을 입력하지 않으면 null들어가는데 제외 시켜주므로써 DB에서 자동으로 디폴트값으로 설정해줌
public class FreeBoardVO {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    long bno; // id

    @Column(name="title")
    String title; // 제목

    @Column(name="content")
    String content; // 내용

    @Column(name="writer")
    String writer; // 작성자

    @Column(name="regdate") @Temporal(TemporalType.DATE)
    Date regdate; // 작성일자

    @Column(name="viewcnt")
    long viewcnt; // 조회수

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
}

