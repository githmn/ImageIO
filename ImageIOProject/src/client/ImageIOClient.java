package client;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.imageio.ImageIO;

public class ImageIOClient {

	public static void main(String args[]) throws Exception{
		new ImageIOClient().exec();
	}
	
	public static final String SERVER_HOST = "192.168.244.139";
	public static final int SERVER_PORT = 10514;
	
	private void exec() throws Exception{
		
		String srcDirPath = "./resources/client/src/";
		File srcDir = new File(srcDirPath);
		File srcFiles[] = srcDir.listFiles();
		
		for(File srcFile : srcFiles){
			System.out.println(srcFile.getName());
			Socket sock = null;
			
			try{
			sock = new Socket(ImageIOClient.SERVER_HOST, ImageIOClient.SERVER_PORT);

			InputStream is = sock.getInputStream();
			OutputStream os = sock.getOutputStream();
			
			BufferedImage srcImg = ImageIO.read(srcFile);
			
			if(
					srcImg.getType() == BufferedImage.TYPE_CUSTOM
					||
					srcImg.getType() == BufferedImage.TYPE_4BYTE_ABGR
					||
					srcImg.getType() == BufferedImage.TYPE_USHORT_GRAY
					){
				
				System.out.println("対応してない");
				continue;
			}
						
			ImageIO.write(srcImg, "bmp", os);
						
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				if(sock != null){
					sock.close();
				}
			}
			
		}
		
	}
	
}
