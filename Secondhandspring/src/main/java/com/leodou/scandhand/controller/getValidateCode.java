package com.leodou.scandhand.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leodou.scandhand.tool.CreateValidateCode;

@RestController
@RequestMapping("/code")
public class getValidateCode {
	private CreateValidateCode code = new CreateValidateCode();
	
	@GetMapping("/getCodeImage")
	public void getCodeImage(HttpServletResponse response)throws IOException{

		OutputStream outputStream = response.getOutputStream();
        String generateVerifyCode = code.getString();
        System.out.println(generateVerifyCode);
        BufferedImage image = code.getImage();
        ImageIO.write(image, "png", outputStream);
        outputStream.flush();
        outputStream.close();
		
	}
	
	@GetMapping("/getCode")
	public String getCodeImage(){
		return code.getString();
	}
	
	@PostMapping("/roll")
	public void roll() {
		code = CreateValidateCode.Instance();
	}
}


