package com.gary.framework.util;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 文件工具
 * @author gary
 *
 */
public class FileUtil {
	
	private static final Logger log = LoggerFactory.getLogger(FileUtil.class);

	/**
	 * 计算文件大小
	 * @param fileSize
	 * @return
	 */
	public static String getFileSize(long fileSize){
		if (fileSize < 1024) {
			return fileSize + "B";
		} else {
			long size = fileSize / 1024;
			if (size < 1024) {
				return size + "KB";
			} else {
				size = size / 1024;
				if(size < 1024){
					return size + "MB";
				}else{
					size = size / 1024;
					return size + "GB";
				}
			}
		}
	}
	
	/**
	 * 生成文件名
	 * @param fileName 原文件名
	 * @return
	 */
	public static String generateFileName(String fileName){
		int point = fileName.lastIndexOf(".");
		if(point != -1){
			return CommonUtil.generateId() + fileName.substring(point, fileName.length());
		}else{
			return CommonUtil.generateId();
		}
	}
	
	/**
	 * 根据扩展名和头信息判断是否为图片文件
	 * @param extName
	 * @param file
	 * @return
	 */
	public static boolean isImage(String extName, File file){
		if(isImageByExtName(extName) && isImageByHead(file)){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 根据扩展名字符串判断是否为图片文件
	 * @param extName
	 * @return
	 */
	public static boolean isImageByExtName(String extName){
		if(!(extName.equalsIgnoreCase("jpg") || 
				extName.equalsIgnoreCase("jpeg") || 
				extName.equalsIgnoreCase("png") || 
				extName.equalsIgnoreCase("bmp") || 
				extName.equalsIgnoreCase("gif"))){
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * 根据头信息判断是否是图片文件
	 * @param file
	 * @return
	 */
	public static boolean isImageByHead(File file) {
		InputStream imgFile = null;
		byte[] b = new byte[10];
		int l = -1;
		try {
			imgFile =new FileInputStream(file);
			l = imgFile.read(b);
			imgFile.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		if (l == 10) {
			byte b0 = b[0];
			byte b1 = b[1];
			byte b2 = b[2];
			byte b3 = b[3];
//			byte b4 = b[4];
//			byte b5 = b[5];
			byte b6 = b[6];
			byte b7 = b[7];
			byte b8 = b[8];
			byte b9 = b[9];
			if (b0 == (byte) 'G' && b1 == (byte) 'I' && b2 == (byte) 'F') {
				return true;
			} else if (b1 == 0x50 && b2 == 0x4E && b3 == 0x47) {
				return true;
			} else if (b6 == 0x4A && b7 == 0x46 && b8 == 0x49 && b9 == 0x46) {
				return true;
			} else if (b6 == 0x45 && b7 == 0x78 && b8 == 0x69 && b9 == 0x66) {
				return true;
			}else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	
    /**
     * 根据宽,高信息判断是否为图片文件 
     * @param file
     * @return
     */
    public static final boolean isImageByWidthHeight(File file){   
        boolean flag = false;   
        try{   
            BufferedImage bufreader = ImageIO.read(file);   
            int width = bufreader.getWidth();   
            int height = bufreader.getHeight();   
            if(width==0 || height==0){   
                flag = false;   
            }else {   
                flag = true;   
            }   
        }   
        catch (IOException e) {   
            flag = false;   
        }catch (Exception e) {   
            flag = false;   
        }   
        return flag;   
    }  
    
    /**
     * 以字节为单位读取文件，常用于读二进制文件，如图片、声音、影像等文件。
     * @param fileName
     */
    public static void readFileByBytes(String fileName) {
        InputStream in = null;
        try {
        	log.debug("以字节为单位读取文件内容，一次读多个字节：");
            // 一次读多个字节
            byte[] tempbytes = new byte[100];
            int byteread = 0;
            in = new FileInputStream(fileName);
            FileUtil.showAvailableBytes(in);
            // 读入多个字节到字节数组中，byteread为一次读入的字节数
            while ((byteread = in.read(tempbytes)) != -1) {
                System.out.write(tempbytes, 0, byteread);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e1) {
                }
            }
        }
    }

    /**
     * 以字符为单位读取文件，常用于读文本，数字等类型的文件
     * @param fileName
     */
    public static void readFileByChars(String fileName) {
        Reader reader = null;
        try {
            log.debug("以字符为单位读取文件内容，一次读多个字节：");
            // 一次读多个字符
            char[] tempchars = new char[30];
            int charread = 0;
            reader = new InputStreamReader(new FileInputStream(fileName));
            // 读入多个字符到字符数组中，charread为一次读取字符数
            while ((charread = reader.read(tempchars)) != -1) {
                // 同样屏蔽掉\r不显示
                if ((charread == tempchars.length)
                        && (tempchars[tempchars.length - 1] != '\r')) {
                    System.out.print(tempchars);
                } else {
                    for (int i = 0; i < charread; i++) {
                        if (tempchars[i] == '\r') {
                            continue;
                        } else {
                            System.out.print(tempchars[i]);
                        }
                    }
                }
            }

        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
    }

    /**
     * 以行为单位读取文件，常用于读面向行的格式化文件
     * @param fileName
     */
    public static void readFileByLines(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            log.debug("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                log.debug("line " + line + ": " + tempString);
                line++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
    }

    /**
     * 随机读取文件内容
     * @param fileName
     */
    public static void readFileByRandomAccess(String fileName) {
        RandomAccessFile randomFile = null;
        try {
            log.debug("随机读取一段文件内容：");
            // 打开一个随机访问文件流，按只读方式
            randomFile = new RandomAccessFile(fileName, "r");
            // 文件长度，字节数
            long fileLength = randomFile.length();
            // 读文件的起始位置
            int beginIndex = (fileLength > 4) ? 4 : 0;
            // 将读文件的开始位置移到beginIndex位置。
            randomFile.seek(beginIndex);
            byte[] bytes = new byte[10];
            int byteread = 0;
            // 一次读10个字节，如果文件内容不足10个字节，则读剩下的字节。
            // 将一次读取的字节数赋给byteread
            while ((byteread = randomFile.read(bytes)) != -1) {
                System.out.write(bytes, 0, byteread);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (randomFile != null) {
                try {
                    randomFile.close();
                } catch (IOException e1) {
                }
            }
        }
    }

    /**
     * 显示输入流中还剩的字节数
     * @param in
     */
    private static void showAvailableBytes(InputStream in) {
        try {
            log.debug("当前字节输入流中的字节数为:" + in.available());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 写入文件
     * @param path
     * @param name
     * @param content
     */
	public void writeFile(String path, String name, String content) {
		if (content == null) {
			content = "";
		}
		try {
			File f = new File(path);
			if (!f.exists()) {
				f.mkdirs();
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter(path
					+ name));
			try {
				writer.write(content);
			} finally {
				writer.close();
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
    
	/**
	 * 文件复制
	 * @param srcFileName 源文件
	 * @param destFileName 目标文件
	 * @param overlay 是否允许覆盖
	 * @return
	 * @throws IOException
	 */
	public static boolean copyFile(String srcFileName, String destFileName,
			boolean overlay) throws IOException {
		File srcFile = new File(srcFileName);
		// 判断原文件是否存在
		if (!srcFile.exists()) {
			log.debug("复制文件失败，原文件" + srcFileName + "不存在");
			return false;
		} else if (!srcFile.isFile()) {
			log.debug("复制文件失败，原文件" + srcFileName + "不是一个文件");
			return false;
		}

		File destFile = new File(destFileName);
		if (destFile.exists()) {
			// 表示如果目标文件存在，且允许覆盖
			if (overlay) {
				log.debug("目标文件存在，准备删除");
				if (!FileUtil.delete(destFileName)) {
					log.debug("复制文件失败，删除目标文件" + destFileName + "时出现错误");
					return false;
				}
			} else {
				log.debug("复制文件失败，目标文件" + destFileName + "已存在");
				return false;
			}
		} else {
			// 如果目标文件所在目录不存在
			if (!destFile.getParentFile().exists()) {
				log.debug("目标目录不存在，创建");
				if (!destFile.getParentFile().mkdirs()) {
					log.debug("创建目录失败");
					return false;
				}
			}
		}
		InputStream in = null;
		OutputStream out = null;
		int byteRead = 0;
		
		try {
			in = new FileInputStream(srcFile);
			out = new FileOutputStream(destFile);
			
			byte[] buffer = new byte[1024];
			while((byteRead = in.read(buffer))!=-1){
				out.write(buffer, 0, byteRead);
			}
			return true;
		} catch (IOException e) {
			log.debug("复制文件失败" + e.getMessage());
			return false;
		} finally {
			if(in != null){
				in.close();
			}
			if(out != null){
				out.close();
			}
		}
	}
	
	/**
	 * 文件复制
	 * @param dir 目录
	 * @param file 原文件
	 * @param newFileName 新文件名
	 */
	public static void copyFile(File file, File dir, String newFileName) {
		BufferedOutputStream bos = null;
	    BufferedInputStream bis = null;

	    try {
	    	FileInputStream fis = new FileInputStream(file);
	    	bis = new BufferedInputStream(fis);

	    	FileOutputStream fos = new FileOutputStream(new File(dir,newFileName));
	    	bos = new BufferedOutputStream(fos);

	    	byte[] buf = new byte[4096];

	    	int len = -1;
	    	while ((len = bis.read(buf)) != -1){
	    		bos.write(buf, 0, len);
	    	}
	    } catch (FileNotFoundException e) {
		} catch (IOException eio) {
		} finally {
			try {
				if (null != bis){
					bis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
	    	try{
	    		if (null != bos){
	    			bos.close();
	    		}
	    	} catch (IOException e) {
	    		e.printStackTrace();
	    	}
	    }
	}
	
	/**
	 * 目录复制
	 * @param srcDirName
	 * @param destDirName
	 * @param overlay
	 * @return
	 * @throws IOException
	 */
	public static boolean copyDirectory(String srcDirName, String destDirName,
			boolean overlay) throws IOException {
		File srcDir = new File(srcDirName);
		if(!srcDir.exists()){
			log.debug("复制目录失败，目录" + srcDirName + "存在");
			return false;
		}else if(!srcDir.isDirectory()){
			log.debug("复制目录失败，" + srcDirName + "不是目录");
			return false;
		}
		//如果目标文件不是以文件分割符结尾，自动添加
		if(!destDirName.endsWith(File.separator)){
			destDirName = destDirName + File.separator;
		}
		
		File destDir = new File(destDirName);
		
		if(destDir.exists()){
			if(overlay){
				log.debug("目标目录已存在，准备删除");
				if(!FileUtil.delete(destDirName)){
					log.debug("删除目录失败");
					return false;
				}
			}else{
				log.debug("复制目录失败" + destDirName + "已存在");
				return false;
			}
		}else{
			log.debug("目标目录不存在，准备创建");
			if(!destDir.mkdirs()){
				log.debug("创建目录失败");
				return false;
			}
		}
		
		boolean flag = true;
		File[] files = srcDir.listFiles();
		for (File file : files) {
			if(file.isFile()){
				flag = FileUtil.copyFile(file.getAbsolutePath(), 
						destDirName + file.getName(), false);
				if(!flag){
					break;
				}
			}
			if(file.isDirectory()){
				flag = FileUtil.copyDirectory(file.getAbsolutePath(), destDirName + file.getName(), false);
				if(!flag){
					break;
				}
			}
		}
		if (!flag) {
			log.debug("复制目录" + srcDirName + "到" + destDirName + "失败");
			return false;
		}
		log.debug("复制目录"+srcDirName + "到" + destDirName + "成功");
		return true;
	}

	/**
	 * 删除
	 * @param destFileName
	 * @return
	 */
	public static boolean delete(String destFileName) {
		File file = new File(destFileName);
		if(!file.exists()){
			log.debug("删除失败");
			return false;
		}else{
			if(file.isFile()){
				return FileUtil.deleteFile(destFileName);
			}else{
				return FileUtil.deleteDirectory(destFileName);
			}
		}
	}
	
	/**
	 * 删除文件
	 * @param fileName
	 * @return
	 */
	public static boolean deleteFile(String fileName){
		File file = new File(fileName);
		if(file.exists() && file.isFile()){
			if(file.delete()){
				log.debug("删除单个文件"+ fileName + "成功");
				return true;
			}else{
				log.debug("删除单个文件"+ fileName + "失败");
				return false;
			}
		}else{
			log.debug("删除单个文件"+ fileName + "失败，文件不存在");
			return false;
		}
	}
	
	/**
	 * 删除目录
	 * @param dirName
	 * @return
	 */
	public static boolean deleteDirectory(String dirName){
		if(!dirName.endsWith(File.separator)){
			dirName = dirName + File.separator;
		}
		
		File dirFile = new File(dirName);
		if(!dirFile.exists()||(!dirFile.isDirectory())){
			log.debug("删除目录失败" + dirName + "不存在或不是目录");
			return false;
		}
		
		boolean flag = true;
		File[] files = dirFile.listFiles();
		for (File file : files) {
			if(file.isFile()){
				flag = FileUtil.deleteFile(file.getAbsolutePath());
				if(!flag){
					break;
				}
			}else if(file.isDirectory()){
				flag = FileUtil.deleteDirectory(file.getAbsolutePath());
				if(!flag){
					break;
				}
			}
		}
		if(!flag){
			log.debug("删除目录失败");
			return false;
		}
		//删除当前目录
		if(dirFile.delete()){
			log.debug("删除目录"+dirName + "成功");
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 保存图片到本地 
	 * @param picUrl
	 * @parma dir
	 * @return
	 */
	public static String savePic(String picUrl, String dir){
		String fileName = FileUtil.generateFileName(picUrl);
		try{
			URL url = new URL(picUrl);
			InputStream in = url.openStream();   
			BufferedImage srcImage =  ImageIO.read(url.openStream());
	 
			File img = new File(dir + fileName);
			log.debug("file:" + img.getAbsolutePath());
			
			ImageIO.write(srcImage, "jpeg", img);
	        in.close();
    	} catch (IOException e) {   
    		e.printStackTrace();   
    	}
    	return fileName;
	}
	
	/**
	 * 保存图片到本地 
	 * @param picUrl
	 * @parma dir
	 * @return
	 */
	public static String savePic(String picUrl, String dir, int width, int height){
		String fileName = FileUtil.generateFileName(picUrl);
		try{
			URL url = new URL(picUrl);
			InputStream in = url.openStream();   
			BufferedImage srcImage =  ImageIO.read(url.openStream());
			
			//检查参数
			if(width > 0 && height > 0){
				srcImage = resize(srcImage, width, height);
			}
			
			File img = new File(dir + fileName);
			log.debug("file:" + img.getAbsolutePath());
			
			ImageIO.write(srcImage, "jpeg", img);
			in.close();
		} catch (IOException e) {   
			e.printStackTrace();   
		}
		return fileName;
	}
	
	/**
	 * 改变图片大小
	 * @param source
	 * @param targetW
	 * @param targetH
	 * @return
	 */
	public static BufferedImage resize(BufferedImage source, int targetW,
			int targetH) {
		// targetW，targetH分别表示目标长和宽
		int type = source.getType();
		BufferedImage target = null;
		double sx = (double) targetW / source.getWidth();
		double sy = (double) targetH / source.getHeight();

		// 这里想实现在targetW,targetH范围内实现等比缩放
		//如果不需要等比缩放,则将下面的if else语句注释即可
		if (sx > sy) {
			sx = sy;
			targetW = (int) (sx * source.getWidth());
		} else {
			sy = sx;
			targetH = (int) (sy * source.getHeight());
		}

		// handmade
		if (type == BufferedImage.TYPE_CUSTOM) {
			ColorModel cm = source.getColorModel();
			WritableRaster raster = cm.createCompatibleWritableRaster(targetW,
					targetH);
			boolean alphaPremultiplied = cm.isAlphaPremultiplied();
			target = new BufferedImage(cm, raster, alphaPremultiplied, null);
		} else
			target = new BufferedImage(targetW, targetH, type);
		Graphics2D g = target.createGraphics();

		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BICUBIC);

		g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));
		g.dispose();
		return target;
	}

	public static String savePicByBuffer(String picUrl, String dir){
    	String fileName = FileUtil.generateFileName(picUrl);
		try{
			URL url = new URL(picUrl);
			BufferedInputStream in = new BufferedInputStream(url.openStream());   
			
			File img = new File(dir + fileName);
			System.out.println("file:" + img.getAbsolutePath());
			BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(img));   
			byte[] buf = new byte[2048];   
			while(in.read(buf) != -1){
				out.write(buf);   
			}   
	        in.close();   
	        out.close();   
    	} catch (MalformedURLException e) {   
    		e.printStackTrace();   
    	} catch (IOException e) {   
    		e.printStackTrace();   
    	}
    	return fileName;
	}
	
}
