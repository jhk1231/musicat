package com.example.musicat.controller;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.web.multipart.MultipartFile;
import com.example.musicat.domain.board.Board;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

// 게시글 작성
@Getter @Setter
@NoArgsConstructor
public class ArticleForm {

    @NotNull(message = "게시판을 선택해 주세요")
    private int boardNo;
    @NotBlank(message = "제목을 입력해 주세요")
    private String subject;
    @NotBlank(message = "내용을 입력해 주세요")
    private String content;

    //private List<Board> boardList; //작성 게시글 목록
    private int displayNotice; //공지 설정


    private Long fileNo;
    private MultipartFile importAttacheFile;
    private List<MultipartFile> imageFiles;
}
