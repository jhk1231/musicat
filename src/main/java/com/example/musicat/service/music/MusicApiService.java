package com.example.musicat.service.music;

import com.example.musicat.domain.music.Music;
import com.example.musicat.exception.RestErrorHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.core.pattern.MdcPatternConverter;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.print.attribute.standard.Media;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class MusicApiService {
    private final String URI_MUSICS_ID = "http://localhost:20000/api/musics/find/{id}";
    private final String URI_MUSICS_UPLOAD = "http://localhost:20000/api/musics/uploadFile";
    private final String URI_MUSICS_TEST = "http://localhost:20000/api/posttest";

    private final RestTemplate restTemplate;

    /*******************************************************************
     * RestTemplate Error Handling
     ******************************************************************/
    public MusicApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        //this.restTemplate.setErrorHandler(new RestErrorHandler());
    }

    public void retrieveMusicById(Long id) {
        Map<String, Long> params = new HashMap<String, Long>();
        params.put("id", id);

        System.out.println("call getforentity");
        ResponseEntity<String> responseEntity =  restTemplate.getForEntity(
                URI_MUSICS_ID, String.class, params);
        String fileName = responseEntity.getBody();
        System.out.println("respise Entity : " + responseEntity);
        System.out.println("fileName : " + fileName);
    }

    public Music registerMusic(MultipartFile file, MultipartFile imagefile, String title, int memberNo) throws HttpClientErrorException {

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        System.out.println("file : " + file.getOriginalFilename());

        ByteArrayResource byteArray = null;
        try {
            byteArray = new ByteArrayResource(file.getBytes()) {
                @Override
                public String getFilename() throws IllegalStateException {
                    return URLEncoder.encode(file.getName(), StandardCharsets.UTF_8);
                }
            };
        } catch (Exception e) {
            e.printStackTrace();
        }

        body.add("audio", byteArray);

        System.out.println("imageFile : " + imagefile.getOriginalFilename());
        ByteArrayResource byteArray2 = null;
        try {
            byteArray2 = new ByteArrayResource(imagefile.getBytes()) {
                @Override
                public String getFilename() throws IllegalStateException {
                    return URLEncoder.encode(imagefile.getName(), StandardCharsets.UTF_8);
                }
            };
        } catch (Exception e) {
            e.printStackTrace();
        }

        body.add("image", byteArray2);
        body.add("title", title);
        body.add("memberNo", memberNo);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<Music> response = restTemplate.postForEntity(URI_MUSICS_UPLOAD, requestEntity, Music.class);

        System.out.println("status code : " + response.getStatusCode());

        return response.getBody();
    }

    public void deleteMusicByArticleNo(int articleNo) {
        Map<String, Integer> params = new HashMap<String, Integer>();
        params.put("articleNo", articleNo);
        restTemplate.delete("http://localhost:20000/api/musics/deleteByArticleNo/{articleNo}", params);
    }

    public void deleteByMusicId(Long musicId) {
        Map<String, Long> params = new HashMap<String, Long>();
        params.put("musicId", musicId);
        restTemplate.delete("http://localhost:20000/api/musics/deleteById/{musicId}", params);
    }

    public void connectToArticle(Long musicId, int articleNo) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("musicId", musicId);
        params.put("articleNo", articleNo);
        log.info(params.toString());
        restTemplate.put("http://localhost:20000/api/musics/connectToArticle/{musicId}/{articleNo}",String.class, params);
    }

    public List<Music> retrieveMusics(int articleNo){
        ResponseEntity<List> response = restTemplate.getForEntity("http://localhost:20000/api/musics/findMusics/{articleNo}", List.class, articleNo);
        List<Music> musicList = response.getBody();

        log.info(musicList.toString());
        return musicList;
    }
}
