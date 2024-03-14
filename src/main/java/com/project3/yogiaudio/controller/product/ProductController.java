package com.project3.yogiaudio.controller.product;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project3.yogiaudio.filedb.service.FiledbService;
import com.project3.yogiaudio.repository.entity.User;
import com.project3.yogiaudio.dto.common.PageVO;
import com.project3.yogiaudio.dto.music.MusicDTO;
import com.project3.yogiaudio.dto.common.Criteria;
import com.project3.yogiaudio.service.MusicService;
import com.project3.yogiaudio.service.UserService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
	private MusicService musicService;
	@Autowired
	private FiledbService filedbService;
	@Autowired
	private UserService userService;
	
	// http://localhost:80/product/main
	//메인페이지 호출하기
	@GetMapping("/main")
	public String productMainGET(HttpSession session,Model model,Criteria cri) throws Exception {
		
		PageVO pageVO = new PageVO();
		pageVO.setCri(cri);
		pageVO.setTotalCount(musicService.countdomesticListAll());		
	
		
		
		model.addAttribute("pageVO", pageVO);
		
		//국외음악 최신 , 인기순 출력
		List<MusicDTO> anewlist = musicService.newAboardMusicList();
		model.addAttribute("anewlist", anewlist);
		
		
		
		//국내음악 최신 , 인기순 출력
		List<MusicDTO> dnewresult = musicService.newMusicList(cri);
		model.addAttribute("dnewlist", dnewresult);
		
		List<MusicDTO> dlikeresult = musicService.likeMusicList(cri);
		model.addAttribute("dlikelist",dlikeresult);
		
		
		
		log.debug("메인페이지호출테스트");
		return"product/main";
	}
	
	
	// http://localhost:80/product/domestic-music
	//국내음악리스트 호출하기
	@GetMapping("/domestic-music")
	public String productDomesticGET(Model model, Criteria cri,MusicDTO dto) throws Exception {
		
		PageVO pageVO = new PageVO();
		pageVO.setCri(cri);
		pageVO.setTotalCount(musicService.countdomesticListAll());		
		
		model.addAttribute("pageVO", pageVO);
		
		
		//전체
		List<MusicDTO> result = musicService.domesticListAll(cri);
		
		model.addAttribute("domesticlist", result);
		
			
		
		
		log.debug("국내음악페이지출력");
		return "product/domestic";
		
	}
	

	
	
	
	//국외음악리스트 호출하기
	@GetMapping("/aboard-music")
	public String productAboardGET(Model model, Criteria cri, MusicDTO dto) throws Exception {
		
		PageVO pageVO = new PageVO();
		pageVO.setCri(cri);
		pageVO.setTotalCount(musicService.countaboardListAll());
		
		model.addAttribute("pageVO", pageVO);
		
		List<MusicDTO> result = musicService.aboardListAll(cri);
		
		model.addAttribute("aboardlist", result);
		
		
		log.debug("국외음악페이지출력");
		return "product/aboard";
	}
	
	
	//국내음악상세페이지
	// http://localhost:80/product/domestic-detail?musicno=&musicmajor=
	@GetMapping("/domestic-detail")
	public String domesticDetailGET(@RequestParam(value = "musicno") int musicno, @RequestParam(value = "musicmajor") String musicmajor,@RequestParam(value = "id") long id,Model model) {
		
		
		User udetail = userService.findById(id);
		model.addAttribute("udetail", udetail);
		
		
		MusicDTO result = musicService.domesticDetail(musicno,musicmajor);
		model.addAttribute("detail", result);
		
		
	    System.out.println("musicno: " + musicno); 
	    System.out.println("musicmajor: " + musicmajor);
	    System.out.println("result + :" + result);
		return"product/domesticdetail";
	}
	
	
	//국외음악상세페이지
	// http://localhost:80/product/aboard-detail?musicno=&musicmajor=
	@GetMapping("/aboard-detail")
	public String aboardDetailGET(@RequestParam(value = "musicno") int musicno, @RequestParam(value = "musicmajor") String musicmajor,Model model) {
		 System.out.println("musicno: " + musicno); 
		 System.out.println("musicmajor: " + musicmajor);
		return"product/aboarddetail";
	}
	
	
	//국내앨범자켓바꾸기 GET
	@GetMapping("/dalbum-update")
	public String albumUpdateGET() {
		log.debug("앨범수정페이지modal 실행!");
		return "product/dalbumupdate";
	}
	
	
	//국내앨범자켓바꾸기 POST
	@PostMapping("/dalbum-update")
	public String albumUpdatePOST(MusicDTO dto) {
		
		String filePath = filedbService.saveFiles(dto.getFiles());
		
		log.debug("앨범수정완료!");
		musicService.albumUpdate(dto, filePath);
		
		return "redirect:/product/domestic-music";
	}
	
	
	
	//음원등록하기 GET
	@GetMapping("/dmusic-update")
	public String musicUpdateGET() {
		log.debug("음원등록페이지modal 실행");
		return "product/dmusicupdate";
	}
	
	
	//음원등록하기 POST
	@PostMapping("/dmusic-update")
	public String musicUpdatePOST(MusicDTO dto) {
		
		String fileMusic = filedbService.saveFiles(dto.getFiles());
		musicService.musicUpdate(dto, fileMusic);
		
		return "redirect:/product/domestic-music";
		
	}
	
	

	
	
	
	
	
	
	
	
	
	
}