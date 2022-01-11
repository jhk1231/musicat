package com.example.musicat.controller;

import com.example.musicat.domain.music.Music;
import com.example.musicat.service.music.MusicApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Validated
@RestController
public class MusicController {

    private final MusicApiService musicApiService;

    @Autowired
    public MusicController(MusicApiService musicApiService) {
        this.musicApiService = musicApiService;
    }

    @GetMapping("musictest/{id}")
    public void musicTest(@PathVariable Long id) {
        System.out.println("musictest entered");
        musicApiService.retrieveMusicById(id);
    }

//    @PostMapping("musicpost")
//    public Music upload(@RequestParam(value = "audio") MultipartFile file, @RequestParam(value = "image") MultipartFile imagefile,
//                        @RequestParam(value = "title") @Size(min = 1, max = 1) String title, @RequestParam(value = "memberNo") int memberNo,
//                        @RequestParam(value = "articleNo") int articleNo) throws HttpClientErrorException {
//        return musicApiService.registerMusic(file, imagefile, title, memberNo, articleNo);
//    }

    @PostMapping("musicpost")
    public ModelAndView upload(@RequestParam(value = "audio") MultipartFile file, @RequestParam(value = "image") MultipartFile imagefile,
                               @RequestParam(value = "title") @Size(min = 1, max = 1) String title
            , @RequestParam(value = "memberNo") int memberNo) throws HttpClientErrorException {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("view/board/musicRegisterCheck");

        Music music = musicApiService.registerMusic(file, imagefile, title, memberNo);
        mv.addObject("music", music);
        return mv;
    }

    @DeleteMapping("musicDeleteArticle")
    public String deleteMusicArticle(int articleNo) {
        musicApiService.deleteMusicByArticleNo(articleNo);
        return "success";
    }

    @DeleteMapping("musicDelete")
    public Map<String, String> deleteMusic(Long musicId) {
        musicApiService.deleteByMusicId(musicId);
        Map<String, String> map = new HashMap<String, String>();
        //map.put("result", "hello");
        map.put("success", Integer.toString(1));
        return map;
    }

    @PutMapping("musicConnectArticle/{musicId}/{articleNo}")
    public void connectToArticle(@PathVariable(name="musicId") Long musicId, @PathVariable(name="articleNo") int articleNo) {
        log.info("musicId : " + musicId);
        log.info("articleNo : " + articleNo);

        musicApiService.connectToArticle(musicId, articleNo);
    }

    @GetMapping("retrieveMusics/{articleNo}")
    public List<Music> retrieveMusics(@PathVariable(name="articleNo") int articleNo){
        List<Music> musicList = musicApiService.retrieveMusics(articleNo);
        log.info(musicList.toString());
        return musicList;
    }
}
