package com.example.musicat.controller;

import java.lang.reflect.Member;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import com.example.musicat.controller.form.JoinForm;
import com.example.musicat.domain.board.*;
import com.example.musicat.domain.member.FollowVO;

import com.example.musicat.security.MemberAccount;
import com.example.musicat.service.board.BoardService;
import com.example.musicat.service.board.ReplyService;
import com.example.musicat.service.member.FollowService;
import com.example.musicat.service.member.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

//import com.example.musicat.config.SessionConfig;
import com.example.musicat.service.board.ArticleService;
import com.example.musicat.service.board.CategoryService;
import com.example.musicat.service.member.MemberService;
import com.example.musicat.domain.member.MemberVO;

import lombok.extern.java.Log;


@Controller
@Log
public class HomeController {


    @Autowired
    private GradeService gradeService;

	@Autowired
	private MemberService memberService;
	
	@Autowired
	private CategoryService categoryService;

    @Autowired
    private BoardService boardService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ReplyService replyService;

    @Autowired
    private FollowService followService;

    @Autowired
    private static AuthenticationManager authenticationManager;



    @GetMapping("/")
    public String mainPage(){
        return "redirect:/main";
    }

    @RequestMapping("/musicatlogin")
  	public String index(Model model, HttpServletRequest request) {

		if(request.getParameter("email") != null ) {
			log.info("로그인 실패 - 이전에 입력한 이메일 : " + request.getParameter("email"));
			model.addAttribute(request.getParameter("email"));
		}
		return "view/member/login";
	}





//	@PostMapping("/")
//	public String selfOut(@RequestParam("memberNo") int no, @RequestParam("password") String password) {
//		this.memberService.modifyMember(no, password);
//		return "redirect:/musicatlogin";
//	}


    public static MemberVO checkMemberNo() {

        MemberVO member = new MemberVO();

        String auth = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        if(auth.equals("[ROLE_ANONYMOUS]")) {
            member.setGradeNo(4);
            member.setNo(0);
        } else {
            member = (MemberVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
        return member;
    }

  @GetMapping("/main")
	public String petopiaMain(Model model, HttpSession session, Authentication authentication) { // //@AuthenticationPrincipal Member member

		List<ArticleVO> allArticleList = this.articleService.retrieveAllArticle();
		model.addAttribute("articleList", allArticleList);
        List<BestArticleVO> bestArticles = this.articleService.selectAllBestArticle();
        model.addAttribute("bestArticles", bestArticles);

        model.addAttribute("HomeContent","fragments/viewMainContent");

		List<CategoryVO> categoryList = this.categoryService.retrieveCategoryBoardList();
		model.addAttribute("categoryBoardList", categoryList);
		CategoryVO categoryVo = new CategoryVO();
		model.addAttribute("categoryVo", categoryVo);




        //로그인하지 않은 사용자일 경우 ( 로그인한 사용자 정보 처리는 SecurityConfig.java에서 )
//        String auth = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
//		if (auth.equals("[ROLE_ANONYMOUS]")) {
//
//            //익명 사용자에게 gradeNo 부여 ( 게시판 접근 시 필요 )
//            int gradeNo = gradeService.retrieveGradeNo(auth);
//            log.info("auth : " + auth + " gradeNo : " + gradeNo);
//
//            MemberVO member = new MemberVO();
//            member.setGrade(auth);
//            member.setGradeNo(gradeNo);
//
//            session.setAttribute("loginUser", member);
//            log.info("익명 사용자 - grade : " + member.getGrade() + " gradeNo : " + member.getGradeNo());

      //MemberVO member = new MemberVO();
      //MemberVO member = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      MemberVO member = checkMemberNo();

      List<BoardVO> likeBoardList = this.boardService.retrieveLikeBoardList(member.getNo());
      model.addAttribute("likeBoardList", likeBoardList);


      return "view/home/viewHomeTemplate";

    }

	
	@GetMapping("/join1")
	public String join(Model model) {
		//MemberVO mVo = new MemberVO(); //MemberVO라는 빈칸 양식 종이를 새로 가져올때마다 new 선언
        JoinForm joinForm = new JoinForm();
        model.addAttribute("form", joinForm); //model은 우편부, addAttribute 누군가에게 붙여주는 행동, "member"는 member가 속한이름, member 우편물 내용
		return "view/member/register"; // "view/member/register" 이 주소로 보낸다.
	}
	
	@GetMapping("/ChangePwd")
	public String changepwd() {
		return "view/member/passwordChange";
	}

    @GetMapping("/myPage/Playlist/{userNo}")
    public String myPage(Model model, @PathVariable int userNo) {
        MemberVO member = new MemberVO();
        FollowVO follow = new FollowVO();

        try {
            member = memberService.retrieveMemberByManager(userNo);
            follow.setFollowing(followService.countFollowing(userNo));
            follow.setFollowed(followService.countFollowed(userNo));
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<CategoryVO> categoryList = this.categoryService.retrieveCategoryBoardList();
        model.addAttribute("categoryBoardList", categoryList);

        CategoryVO categoryVo = new CategoryVO();
        model.addAttribute("categoryVo", categoryVo);
        model.addAttribute("member", member);
        model.addAttribute("follow", follow);

        model.addAttribute("HomeContent", "fragments/viewMyPagePlaylist");
        return "view/home/viewHomeTemplate";

    }

    @GetMapping("/myPage/Playlist/{userNo}/{playlistNo}")
    public String myPagePlaylistDetail(Model model, @PathVariable String playlistNo, @PathVariable int userNo) {
        MemberVO member = new MemberVO();
        try {
            member = memberService.retrieveMemberByManager(userNo);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<CategoryVO> categoryList = this.categoryService.retrieveCategoryBoardList();
        model.addAttribute("categoryBoardList", categoryList);

        CategoryVO categoryVo = new CategoryVO();
        model.addAttribute("categoryVo", categoryVo);
        model.addAttribute("member", member);
        model.addAttribute("HomeContent", "fragments/viewMyPagePlaylistDetail");
        return "view/home/viewHomeTemplate";

    }


    //작성한 게시글 조회 ------------------- 게시글별 댓글 수 추가하면 좋겠다
    @GetMapping("/myPage/Board/{userNo}")
    public String myPageBoard(Model model, @PathVariable int userNo) {
        MemberVO member = new MemberVO();
        try {
            member = memberService.retrieveMemberByManager(userNo);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<ArticleVO> articles = this.articleService.retrieveMyArticle(userNo);
        model.addAttribute("articles", articles);

        List<CategoryVO> categoryList = this.categoryService.retrieveCategoryBoardList();
        model.addAttribute("categoryBoardList", categoryList);

        CategoryVO categoryVo = new CategoryVO();
        model.addAttribute("categoryVo", categoryVo);

        model.addAttribute("member", member);
        log.info("대체 뭐가 문젠데 Board " + member.getNo());
        model.addAttribute("HomeContent", "fragments/viewMyPageBoard");
        return "view/home/viewHomeTemplate";

    }

    //작성한 댓글 조회--------------------- 작성자 이름에 링크, 게시글 제목 띄우기
    @GetMapping("/myPage/Reply/{userNo}")
    public String myPageReply(Model model, @PathVariable int userNo) {
        MemberVO member = new MemberVO();
        try {
            member = memberService.retrieveMemberByManager(userNo);
            log.info("대체 뭐가 문젠데 Reply " + member.getNo());
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<ReplyVO> replyListOneMember = this.replyService.retrieveReplyOneMember(userNo);
        model.addAttribute("replyListOneMember", replyListOneMember);
        log.info(replyListOneMember.toString());

        List<BoardVO> boardNameList = this.boardService.retrieveBoardNameList();
        model.addAttribute("boardNameList", boardNameList);
        log.info(boardNameList.toString());

        List<CategoryVO> categoryList = this.categoryService.retrieveCategoryBoardList();
        model.addAttribute("categoryBoardList", categoryList);

        CategoryVO categoryVo = new CategoryVO();
        model.addAttribute("categoryVo", categoryVo);

        model.addAttribute("member", member);



        model.addAttribute("HomeContent", "fragments/viewMyPageReply");
        return "view/home/viewHomeTemplate";

    }

    //추천 누른 게시글 조회
    @GetMapping("/myPage/Like/{userNo}")
    public String myPageLike(Model model, @PathVariable int userNo) {
        MemberVO member = new MemberVO();
        try {
            member = memberService.retrieveMemberByManager(userNo);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<ArticleVO> likeArticle = this.articleService.retrieveMyLikeArticle(userNo);
        model.addAttribute("likeArticle", likeArticle);

        List<CategoryVO> categoryList = this.categoryService.retrieveCategoryBoardList();
        model.addAttribute("categoryBoardList", categoryList);

        CategoryVO categoryVo = new CategoryVO();
        model.addAttribute("categoryVo", categoryVo);

        model.addAttribute("member", member);
        model.addAttribute("HomeContent", "fragments/viewMyPageLike");
        return "view/home/viewHomeTemplate";

    }


    @GetMapping("/test")
    public String test(){
        return "view/board/mrtest";
    }

}
