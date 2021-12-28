package com.example.musicat.domain.board;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "files")
public class File {

    @Id @GeneratedValue
    @Column(name = "file_no")
    private Integer no;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_no")
    private Article article;

    @Column(name = "originalfilename")
    private String originalFileName;

    @Column(name = "systemfilename")
    private String systemFileName;

    @Column(name = "filesize")
    private Long fileSize;

    @Column(name = "filetype")
    private int fileType;

    @Column(name = "writedate", nullable = false, insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp writedate;

    //==연관관계 편의 메소드==//
    public void addArticle(Article article) {
        this.article = article;
        article.getFileList().add(this);
    }

    //==생성 메소드==//
    public static File createFile(String originalFileName, String systemFileName, Long fileSize, int fileType){
        File file = new File();
        file.originalFileName = originalFileName;
        file.systemFileName = systemFileName;
        file.fileSize = fileSize;
        file.fileType = fileType;
        return file;
    }

    @Override
    public String toString() {
        return "File{" +
                "no=" + no +
                ", originalFileName='" + originalFileName + '\'' +
                ", systemFileName='" + systemFileName + '\'' +
                ", fileSize=" + fileSize +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        File file = (File) o;
        return getFileSize() == file.getFileSize() && getFileType() == file.getFileType() && Objects.equals(getNo(), file.getNo()) && Objects.equals(getArticle(), file.getArticle()) && Objects.equals(getOriginalFileName(), file.getOriginalFileName()) && Objects.equals(getSystemFileName(), file.getSystemFileName()) && Objects.equals(getWritedate(), file.getWritedate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNo(), getArticle(), getOriginalFileName(), getSystemFileName(), getFileSize(), getFileType(), getWritedate());
    }
}
