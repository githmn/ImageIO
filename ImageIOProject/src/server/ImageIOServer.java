package server;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.imageio.ImageIO;

public class ImageIOServer {

	public static void main(String args[]) throws Exception{
		new ImageIOServer().exec();
	}

	private static final int SERVER_PORT = 10514;

	private void exec() throws Exception{

		ServerSocket servSock = new ServerSocket(ImageIOServer.SERVER_PORT);
		
		int cnt = 0;
		
		while(true){

			Socket clntSock = null;

			try{

				clntSock = servSock.accept();

				InputStream is = clntSock.getInputStream();
				OutputStream os = clntSock.getOutputStream();

				BufferedImage srcImg = ImageIO.read(is);
				
				if(srcImg == null){
					os.write(1);
					continue;
				} else {
					os.write(0);
				}
				
				ImageIO.write(srcImg, "bmp", new File("./resources/server/dst/"+(cnt++)+".png"));

			}catch(Exception e){
				e.printStackTrace();
			}finally{
				if(clntSock != null){
					clntSock.close();
				}
			}
			
		}

	}
}
