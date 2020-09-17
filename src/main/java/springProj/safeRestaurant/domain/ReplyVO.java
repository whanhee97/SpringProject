package springProj.safeRestaurant.domain;

import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="reply")
@DynamicInsert
public class ReplyVO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long rno;

    @Column(name="bno")
    private long bno;

    @Column(name="content")
    private String content;

    @Column(name="writer")
    private String writer;

    @Column(name="regdate") @Temporal(TemporalType.DATE)
    private Date regdate;

    public long getRno() {
        return rno;
    }

    public void setRno(long rno) {
        this.rno = rno;
    }

    public long getBno() {
        return bno;
    }

    public void setBno(long bno) {
        this.bno = bno;
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
}
