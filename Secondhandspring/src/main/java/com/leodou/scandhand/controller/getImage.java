package com.leodou.scandhand.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
//import java.util.Optional;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.leodou.scandhand.tool.FileUploadUtil;

@RestController
@RequestMapping("/Image")
public class getImage {
	private BufferedImage image = null;
	private File file = null;
	
	@GetMapping("/getImage/{goodsid}")
	public void getImages(HttpServletResponse response,@PathVariable("goodsid") int goodsid)throws IOException{
		try {
			OutputStream outputStream = response.getOutputStream();
			file = new File("D:\\Srping Boot\\picture\\" + goodsid + ".jpg");
			image = ImageIO.read(file);
	        ImageIO.write(image, "jpg", outputStream);
	        outputStream.flush();
	        outputStream.close();
		}catch(IOException e){
			System.out.println(e);
		}
		
	}
	
	@GetMapping("/getLicenseImage/{stuid}")
	public void getLicenseImages(HttpServletResponse response,@PathVariable("stuid") int stuid)throws IOException{
		try {
			OutputStream outputStream = response.getOutputStream();
			file = new File("D:\\Srping Boot\\License\\" + stuid + ".jpg");
			image = ImageIO.read(file);
	        ImageIO.write(image, "jpg", outputStream);
	        outputStream.flush();
	        outputStream.close();
		}catch(IOException e){
			System.out.println(e);
		}
		
	}
	
	@GetMapping("/getIdentityImage/{stuid}")
	public void getIdentityImages(HttpServletResponse response,@PathVariable("stuid") int stuid)throws IOException{
		try {
			OutputStream outputStream = response.getOutputStream();
			file = new File("D:\\Srping Boot\\Identity\\" + stuid + ".jpg");
			image = ImageIO.read(file);
	        ImageIO.write(image, "jpg", outputStream);
	        outputStream.flush();
	        outputStream.close();
		}catch(IOException e){
			System.out.println(e);
		}
		
	}
	
	@PostMapping("/upload/{goodsid}")
	public String upload(@RequestParam("file") MultipartFile multipartFile,@PathVariable("goodsid") int goodsid) {
        // replaceAll 用来替换windows中的\\ 为 /
        return FileUploadUtil.upload(multipartFile,goodsid).replaceAll("\\\\", "/");
    }
	
	
	@PostMapping("/uploadLicense/{aplyid}")
	public String uploadLicense(@RequestParam("file") MultipartFile multipartFile,@PathVariable("aplyid") int aplyid) {
        // replaceAll 用来替换windows中的\\ 为 /
        return FileUploadUtil.uploadLicense(multipartFile,aplyid).replaceAll("\\\\", "/");
    }
	
	
	@PostMapping("/uploadIdentity/{aplyid}")
	public String uploadIdentity(@RequestParam("file") MultipartFile multipartFile,@PathVariable("aplyid") int aplyid) {
        // replaceAll 用来替换windows中的\\ 为 /
        return FileUploadUtil.uploadIdentity(multipartFile,aplyid).replaceAll("\\\\", "/");
    }
}
