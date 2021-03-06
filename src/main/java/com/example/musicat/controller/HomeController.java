package com.example.musicat.controller;


import com.example.musicat.controller.form.JoinForm;
import com.example.musicat.domain.board.*;
import com.example.musicat.domain.member.FollowVO;
import com.example.musicat.domain.member.MemberVO;
import com.example.musicat.domain.music.Music;
import com.example.musicat.domain.music.Playlist;
import com.example.musicat.security.MemberAccount;
import com.example.musicat.service.board.ArticleService;
import com.example.musicat.service.board.BoardService;
import com.example.musicat.service.board.CategoryService;
import com.example.musicat.service.board.ReplyService;
import com.example.musicat.service.member.FollowService;
import com.example.musicat.service.member.GradeService;
import com.example.musicat.service.member.MemberService;
import com.example.musicat.service.music.MusicApiService;
import com.example.musicat.util.TemplateModelFactory;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


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

    @Autowired private MusicApiService musicApiService;
  
    @Autowired
    private static AuthenticationManager authenticationManager;

    @Autowired
    private TemplateModelFactory templateModelFactory;

    @GetMapping("/")
    public String mainPage(){
        return "redirect:/main";
    }

    @RequestMapping("/musicatlogin")
  	public String index(Model model, HttpServletRequest request) {

		if(request.getParameter("email") != null ) {
			log.info("????????? ?????? - ????????? ????????? ????????? : " + request.getParameter("email"));
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
            member = ((MemberAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberVo();
        }

        return member;
    }

  @GetMapping("/main")
	public String petopiaMain(Model model) { // //@AuthenticationPrincipal Member member HttpSession session, Authentication authentication

      List<ArticleVO> allArticleList = this.articleService.retrieveAllArticle();
      model.addAttribute("articleList", allArticleList);
      List<BestArticleVO> bestArticles = this.articleService.selectAllBestArticle();
      model.addAttribute("bestArticles", bestArticles);

      model.addAttribute("HomeContent", "fragments/viewMainContent");

      List<CategoryVO> categoryList = this.categoryService.retrieveCategoryBoardList();
      model.addAttribute("categoryBoardList", categoryList);
      CategoryVO categoryVo = new CategoryVO();
      model.addAttribute("categoryVo", categoryVo);

      MemberVO member = checkMemberNo();
      List<BoardVO> likeBoardList = this.boardService.retrieveLikeBoardList(member.getNo());
      model.addAttribute("likeBoardList", likeBoardList);


      templateModelFactory.setCurPlaylistModel(model);

      //musicApiService.showDetailPlaylist()

      return "view/home/viewHomeTemplate";

  }


	@GetMapping("/join1")
	public String join(Model model) {
		//MemberVO mVo = new MemberVO(); //MemberVO?????? ?????? ?????? ????????? ?????? ?????????????????? new ??????
        JoinForm joinForm = new JoinForm();
        model.addAttribute("form", joinForm); //model??? ?????????, addAttribute ??????????????? ???????????? ??????, "member"??? member??? ????????????, member ????????? ??????
        return "view/member/register"; // "view/member/register" ??? ????????? ?????????.
	}




//	@GetMapping("/ChangePwd")
//	public String changepwd() {
//		return "view/member/passwordChange";
//	}

    @GetMapping("/myPage/Playlist/{userNo}")
    public String myPage(Model model, @PathVariable int userNo) {
        MemberVO me = checkMemberNo();
        MemberVO member = new MemberVO();
        FollowVO follow = new FollowVO();
        List<Playlist> playlists = new ArrayList<>();
        int checkFollow = 0;
        try {
            member = memberService.retrieveMemberByManager(userNo);
            follow.setFollowing(followService.countFollowing(userNo));
            follow.setFollowed(followService.countFollowed(userNo));
            playlists = musicApiService.showPlaylist(userNo);
            log.info("me : " + me.getNo());
            log.info("oppose : " + userNo);
            checkFollow = followService.checkFollow(me.getNo(), userNo);

        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("fol : " + checkFollow);
        List<BoardVO> likeBoardList = this.boardService.retrieveLikeBoardList(member.getNo());
        model.addAttribute("likeBoardList", likeBoardList);

        List<CategoryVO> categoryList = this.categoryService.retrieveCategoryBoardList();
        model.addAttribute("categoryBoardList", categoryList);

        CategoryVO categoryVo = new CategoryVO();
        model.addAttribute("categoryVo", categoryVo);
        model.addAttribute("member", member);
        model.addAttribute("follow", follow);
        model.addAttribute("playlists", playlists);
        model.addAttribute("checkFollow", checkFollow);
        log.info("before html playlists : " + playlists);



        model.addAttribute("HomeContent", "fragments/viewMyPagePlaylist");

        templateModelFactory.setCurPlaylistModel(model);
        return "view/home/viewHomeTemplate";

    }

    @GetMapping("/myPage/Playlist/{userNo}/{playlistKey}")
    public String myPagePlaylistDetail(Model model, @PathVariable(name = "playlistKey") String playlistKey, @PathVariable(name = "userNo") int userNo) {
        MemberVO member = new MemberVO();

        //MemberVO member = HomeController.checkMemberNo();

        FollowVO follow = new FollowVO();

        List<Music> musics = null;
        try {
            member = memberService.retrieveMemberByManager(userNo);
            follow.setFollowing(followService.countFollowing(userNo));
            follow.setFollowed(followService.countFollowed(userNo));
            musics = musicApiService.showDetailPlaylist(playlistKey);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<BoardVO> likeBoardList = this.boardService.retrieveLikeBoardList(member.getNo());
        model.addAttribute("likeBoardList", likeBoardList);

        List<CategoryVO> categoryList = this.categoryService.retrieveCategoryBoardList();
        model.addAttribute("categoryBoardList", categoryList);

        CategoryVO categoryVo = new CategoryVO();
        model.addAttribute("categoryVo", categoryVo);
        model.addAttribute("member", member);
        model.addAttribute("follow", follow);
        model.addAttribute("musics", musics);
        model.addAttribute("playlistKey", playlistKey);
        model.addAttribute("HomeContent", "fragments/viewMyPagePlaylistDetail");
        return "view/home/viewHomeTemplate";

    }


    //????????? ????????? ?????? ------------------- ???????????? ?????? ??? ???????????? ?????????
    @GetMapping("/myPage/Board/{userNo}")
    public String myPageBoard(Model model, @PathVariable int userNo) {
        MemberVO me = HomeController.checkMemberNo();
        MemberVO member = new MemberVO();
        FollowVO follow = new FollowVO();
	      int checkFollow = 0;
        try {
            member = memberService.retrieveMemberByManager(userNo);
            follow.setFollowing(followService.countFollowing(userNo));
            follow.setFollowed(followService.countFollowed(userNo));
            checkFollow = followService.checkFollow(member.getNo(), userNo);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<BoardVO> likeBoardList = this.boardService.retrieveLikeBoardList(member.getNo());
        model.addAttribute("likeBoardList", likeBoardList);

        List<ArticleVO> articles = this.articleService.retrieveMyArticle(userNo);
        model.addAttribute("articles", articles);

        List<CategoryVO> categoryList = this.categoryService.retrieveCategoryBoardList();
        model.addAttribute("categoryBoardList", categoryList);

        CategoryVO categoryVo = new CategoryVO();
        model.addAttribute("categoryVo", categoryVo);

        model.addAttribute("member", member);
        model.addAttribute("follow", follow);
	      model.addAttribute("checkFollow", checkFollow);
        log.info("?????? ?????? ????????? Board " + member.getNo());
        model.addAttribute("HomeContent", "fragments/viewMyPageBoard");

        templateModelFactory.setCurPlaylistModel(model);
        return "view/home/viewHomeTemplate";

    }

    //????????? ?????? ??????--------------------- ????????? ????????? ??????, ????????? ?????? ?????????
    @GetMapping("/myPage/Reply/{userNo}")
    public String myPageReply(Model model, @PathVariable int userNo) {
        MemberVO me = HomeController.checkMemberNo();
        MemberVO member = new MemberVO();
        FollowVO follow = new FollowVO();
	      int checkFollow = 0;
        try {
            member = memberService.retrieveMemberByManager(userNo);
            follow.setFollowing(followService.countFollowing(userNo));
            follow.setFollowed(followService.countFollowed(userNo));
	          checkFollow = followService.checkFollow(member.getNo(), userNo);
            log.info("?????? ?????? ????????? Reply " + member.getNo());
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<ReplyVO> replyListOneMember = this.replyService.retrieveReplyOneMember(userNo);
        model.addAttribute("replyListOneMember", replyListOneMember);
        log.info(replyListOneMember.toString());

        List<BoardVO> likeBoardList = this.boardService.retrieveLikeBoardList(member.getNo());
        model.addAttribute("likeBoardList", likeBoardList);

        List<CategoryVO> categoryList = this.categoryService.retrieveCategoryBoardList();
        model.addAttribute("categoryBoardList", categoryList);

        CategoryVO categoryVo = new CategoryVO();
        model.addAttribute("categoryVo", categoryVo);

        model.addAttribute("member", member);

        model.addAttribute("follow", follow);
      
        model.addAttribute("checkFollow", checkFollow);

        model.addAttribute("HomeContent", "fragments/viewMyPageReply");

        templateModelFactory.setCurPlaylistModel(model);
        return "view/home/viewHomeTemplate";

    }

    //?????? ?????? ????????? ??????
    @GetMapping("/myPage/Like/{userNo}")
    public String myPageLike(Model model, @PathVariable int userNo) {
        MemberVO me = HomeController.checkMemberNo();
        MemberVO member = new MemberVO();
        FollowVO follow = new FollowVO();
	      int checkFollow = 0;
        try {
            member = memberService.retrieveMemberByManager(userNo);
            follow.setFollowing(followService.countFollowing(userNo));
            follow.setFollowed(followService.countFollowed(userNo));
	          checkFollow = followService.checkFollow(member.getNo(), userNo);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<BoardVO> likeBoardList = this.boardService.retrieveLikeBoardList(member.getNo());
        model.addAttribute("likeBoardList", likeBoardList);

        List<ArticleVO> likeArticle = this.articleService.retrieveMyLikeArticle(userNo);
        model.addAttribute("likeArticle", likeArticle);

        List<CategoryVO> categoryList = this.categoryService.retrieveCategoryBoardList();
        model.addAttribute("categoryBoardList", categoryList);

        CategoryVO categoryVo = new CategoryVO();
        model.addAttribute("categoryVo", categoryVo);

        model.addAttribute("member", member);
        model.addAttribute("follow", follow);
	      model.addAttribute("checkFollow", checkFollow);
        model.addAttribute("HomeContent", "fragments/viewMyPageLike");

        templateModelFactory.setCurPlaylistModel(model);
        return "view/home/viewHomeTemplate";

    }


    // ?????????????????? ?????? ??? ??????
    @GetMapping("/addPlaylistForm/{memberNo}")
    public String addPlaylistForm(Model model, @PathVariable("memberNo") int memberNo) {
        model.addAttribute("memberNo", memberNo);
        return "view/etc/createPlaylist";
    }

    @GetMapping("/test")
    public String test(){
        return "view/board/mrtest";
    }

    // ?????????????????? ?????? ??? ??????
    @GetMapping("/changePlaylistForm/{id}")
    public String changePlaylist(Model model, @PathVariable("id") String id) {
        Playlist playlist = musicApiService.getOnePlaylist(id);
        log.info("playlist : " + playlist.getPlaylistImage());
        model.addAttribute("playlist", playlist);
        return "view/etc/changePlaylist";
    }

    // ??? ?????? ?????????????????? ?????? ??? ??????
    @GetMapping("/selectPlaylist/{musicNo}/{memberNo}")
    public String selectPlaylistForm(Model model, @PathVariable("musicNo") int musicNo, @PathVariable("memberNo") int memberNo) {
        log.info("controller ??????");
        List<Playlist> playlists = this.musicApiService.showPlaylist(memberNo);
        log.info("playlist?????? ?????? : " + playlists.toString());
        model.addAttribute("playlistsByMember", playlists);
        model.addAttribute("musicNo", musicNo);
        return "view/etc/addMusicToPlaylist";
    }
}
