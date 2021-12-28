package com.example.musicat.controller;

import com.example.musicat.domain.board.Article;
import com.example.musicat.domain.board.Board;
import com.example.musicat.domain.member.Member;
import com.example.musicat.service.board.ArticleService;
import com.example.musicat.service.board.ArticleServiceImpl;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.h2.engine.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleServiceImpl articleService;

//    @Autowired
//    private ArticleService articleService;

    /**
     * 전체글 조회
     */
    @GetMapping
    public ModelAndView findAll() {
        ModelAndView mv = new ModelAndView();
        List articles = articleService.findAll();
        mv.addObject("articles", articles);
        mv.setViewName("/board/allArticles.html");
        return mv;
    }

    /**
     * 세부 조회
     * @param articleNo 게시글 번호
     * @return
     */
    @GetMapping("/{articleNo}")
    public ModelAndView findOne(@PathVariable Integer articleNo) {
        ModelAndView mv = new ModelAndView();
        Article findArticle = articleService.findOne(articleNo);
        mv.addObject("article", findArticle);
        mv.setViewName("/board/detailArticle.html");
        return mv;
    }

    /**
     * 게시판 별 조회 ( BoardController로 빼야 된다. )
     * @param boardNo 게시판 번호
     * @return
     */
    @GetMapping("/board/{boardNo}/articles")
    private ModelAndView findByBoardNo(@PathVariable int boardNo) {
        ModelAndView mv = new ModelAndView();
        List<Article> articles = articleService.findByBoardNo(boardNo);
        mv.addObject("articles", articles);
        mv.setViewName("/board/listBoard.html");
        return mv;
    }

    /**
     * 작성 폼 이동
     * @return
     */
    @GetMapping("/addArticle")
    public String saveForm(Model model) throws Exception{
        List<BoardEx> boardList = new ArrayList<>(); //임시로 만든 static class
        for (int i = 0; i < 6; i++) {
            BoardEx bx = new BoardEx();
            bx.setBoardNo(i);
            bx.setBoardName("게시판" + i);
            boardList.add(bx);
        }
        model.addAttribute("gradeNo", 1);
        model.addAttribute("boardList", boardList);
        log.info("before form");
        model.addAttribute("articleForm", new ArticleForm());
        model.addAttribute("HomeContent", "/view/board/writeArticleForm");
//        return "view/home/viewHomeTemplate";
        log.info("after form");
        return "/view/board/writeArticleForm";
    }

    /**
     * 게시글 등록
     * @param form 입력된 값
     * @return
     */
    @PostMapping("/addArticle")
    public RedirectView save(@Validated @ModelAttribute ArticleForm form) {
        RedirectView rv = new RedirectView();
        //        Member findMember = em.find(Member.class, memberNo); // MemberNo를 어떻게 받는지 모름..
//        Board findBoard = em.find(Board.class, form.getBoardNo());
        String nickname = "임시";

//        Article insertArticle = Article.createArticle(Member, Board, nickname, form);
//        fileManager.uploadFiles(); // 여기서 File.addArticle 실행할 것

//        int articleNo = articleService.save(insertArticle);
//        rv.setUrl("/article/" + articleNo);
        return rv;
    }


    /**
     * 수정 폼 이동
     * @param articleNo 수정할 게시글
     * @return
     */
    @GetMapping("/updateArticle/{articleNo}")
    public ModelAndView updateForm(@PathVariable Integer articleNo) {
        ModelAndView mv = new ModelAndView();
        Article findArticle = articleService.findOne(articleNo);
        mv.addObject("article", findArticle);
        mv.setViewName("/board/updateArticleForm.hmtl");
        return mv;
    }

    /**
     * 게시글 수정
     * @param articleNo 수정 된 게시글 번호
     * @param form 수정 값
     * @return
     */
    @PostMapping("/updateArticle/{articleNo}")
    public RedirectView update(@PathVariable int articleNo, @RequestBody ArticleForm form){
        RedirectView rv = new RedirectView();
        Article findArticle = articleService.findOne(articleNo);
//        Board findBoard = em.findOne(form.getBoardNo());
//        Article.updateArticle(findArticle, findBoard, form);
        rv.setUrl("/article/detail/" + articleNo);
        return rv;
    }

    /**
     * 게시글 삭제
     * @param articleNo 삭제할 게시글 번호
     * @param boardNo 삭제되는 게시글이 속해 있던 게시판 번호
     * @return
     */
    @PostMapping("/removeArticle/{articleNo}")
    public RedirectView remove(@PathVariable Integer articleNo, @RequestParam("boardNo") int boardNo) {
        RedirectView rv = new RedirectView();
        articleService.remove(articleNo);
        rv.setUrl("/article/" + boardNo);
        return rv;
    }

    static class BoardEx{
        private int boardNo;
        private String boardName;

        public int getBoardNo() {
            return boardNo;
        }

        public void setBoardNo(int boardNo) {
            this.boardNo = boardNo;
        }

        public String getBoardName() {
            return boardName;
        }

        public void setBoardName(String boardName) {
            this.boardName = boardName;
        }
    }

}
