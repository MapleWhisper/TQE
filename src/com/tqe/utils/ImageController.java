package com.tqe.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 用户生成验证码图片
 * @author 广路
 *
 */
@Controller()
public class ImageController {

	@RequestMapping("/valifImage")
	public void validateImage(HttpSession session , HttpServletResponse response){
		BufferedImage image = new BufferedImage(68, 22, BufferedImage.TYPE_INT_RGB);
		
		//获取 图形的绘制
		Graphics g = image.getGraphics();

		Random rd = new Random();
		
		//给图片设置背景颜色
		Color c = Color.white;
		g.setColor(c);
		g.fillRect(0, 0, 68, 22);

		StringBuffer sb = new StringBuffer();
		
		//图片显示的内容
		int x=0,y=0,ans=0;
		while(x-y<=0){
			x = rd.nextInt(10);
			y = rd.nextInt(10);
		}
		
		// 0 +
		// 1 -
		// 2 *
		int op = rd.nextInt(3);
		if(op==0){
			ans = x + y;
			sb.append(x).append("+").append(y).append("=?");
		}else if(op==1){
			ans = x - y;
			sb.append(x).append("-").append(y).append("=?");
		}else{
			ans = x * y ;
			sb.append(x).append("X").append(y).append("=?");
		}
		

		

		g.setColor( Color.BLACK );  

		g.setFont(new Font("Arial", Font.BOLD , 20));// 输出的   字体和大小  
		
		g.drawString(sb.toString(),  3, 18);//写什么数字，在图片   的什么位置画
		
		session.setAttribute("verificationCode", ans+"");
		try {
			ImageIO.write(image, "JPG", response.getOutputStream());
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
