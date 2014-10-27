package com.gary.framework.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
/**
 * 生成验证码图片
 * @author gary
 *
 */
public class RandImgCreater {
	
//    private static final String CODE_LIST = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
	//去掉L和1,不容易区分
    private static final String CODE_LIST = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz234567890";
    private HttpServletResponse response = null;
    private static final int HEIGHT = 20;
    private static final int FONT_NUM = 4;
    private int width = 0;
    private int iNum = 0;
    private String codeList = "";
    
    public RandImgCreater(HttpServletResponse response) {
        this.response = response;
        this.width = 13 * FONT_NUM + 12;
        this.iNum = FONT_NUM;
        this.codeList = CODE_LIST;
    }
    
    public RandImgCreater(HttpServletResponse response,int iNum,String codeList) {
        this.response = response;
        this.width = 13 * iNum + 12;
        this.iNum = iNum;
        this.codeList = codeList;
    }
    
    public String createRandImage(){
        BufferedImage image = new BufferedImage(width, HEIGHT,
                BufferedImage.TYPE_INT_RGB);
        
        Graphics g = image.getGraphics();
        
        Random random = new Random();
        
        g.setColor(new Color(58, 108, 140));
        g.fillRect(0, 0, width, HEIGHT);
        
        g.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        
        String sRand = "";
        for (int i = 0; i < iNum; i++){
            int rand = random.nextInt(codeList.length());
            String strRand = codeList.substring(rand,rand+1);
            sRand += strRand;
            g.setColor(new Color(255,255,255));
            g.drawString(strRand, 13 * i + 6, 16);
        }

        g.dispose();

        try{
            ImageIO.write(image, "JPEG", response.getOutputStream());
        }catch(Exception e){
            
        }
        
        return sRand;
    }
    
}