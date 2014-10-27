package com.gary.framework.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 清理指定目录下,符合扩展名条件的文件,删除每行开头结尾的空白(空格,制表符,换页符等)和空行
 * @author gary
 *
 */
public class CleanHTMLFile {
	//清理指定扩展名
	static String[] targetExtName = {"html", "jsp", "ftl", "vm"};
	
	public static void main(String[] args) throws IOException {
		cleanDir(new File("E:\\test"));
	}
	
	/**
	 * 清理目录
	 * @param dir
	 */
	public static void cleanDir(File dir){
		File[] subFile = dir.listFiles();
		for (int i = 0; i < subFile.length; i++) {
			//如果是dir,递归清理目录
			if(subFile[i].isDirectory()){
				cleanDir(subFile[i]);
			}else{
				System.out.println("文件:" + subFile[i].getAbsolutePath());
				String fileName = subFile[i].getName();
				//是否需要清理
				boolean needClean = false;
				//扩展名
				String extName = getFileExtName(fileName); 
				for (int j = 0; j < targetExtName.length; j++) {
					if(targetExtName[j].equalsIgnoreCase(extName)){
						needClean = true;
					}
				}
				if(needClean){
					System.out.println("操作:清理");
					cleanFile(subFile[i]);
				}else{
					System.out.println("操作:忽略");
				}
			}
		}
	}
	
	/**
	 * 清理文件
	 * @param fileFullPath
	 * @throws IOException 
	 */
	public static void cleanFile(File file){
		FileWriter fw = null;
		try {
			fw = new FileWriter(file.getAbsoluteFile()+".tmp", false);
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		BufferedWriter bw = new BufferedWriter(fw);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            //每次读取一行
            while ((tempString = reader.readLine()) != null) {
            	if(!isBlank(tempString)){
            		bw.write(cleanHTML(tempString));
            		bw.newLine();
            	}
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        	//将数据更新至文件
        	try {
				bw.flush();
				bw.close();
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
            
            File tmpFile = new File(file.getAbsoluteFile()+".tmp");
            file.delete();
            tmpFile.renameTo(file);
        }
	}
	
	/**
	 * 删除开头结尾的空白(空格,制表符,换页符等)
	 * @param str
	 * @return
	 */
	public static String cleanHTML(String str){
		Pattern pattern = Pattern.compile("^\\s*|\\s*$",Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        return matcher.replaceAll("");
	}
	
	/**
	 * 获取文件扩展名
	 * @param fileName
	 * @return
	 */
	public static String getFileExtName(String fileName){
		return fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
	}
	
	/**
	 * 字符串是否为空
	 * @param str
	 * @return
	 */
	public static boolean isBlank(String str){
		int strLen;
		if ((str == null) || ((strLen = str.length()) == 0)){
			return true;
		}
		for (int i = 0; i < strLen; ++i) {
			if (!(Character.isWhitespace(str.charAt(i)))) {
				return false;
			}
		}
		return true;
	}
	
}
