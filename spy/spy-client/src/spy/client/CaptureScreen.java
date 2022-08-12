/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spy.client;

/**
 *
 * @author Brothers
 */
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
public class CaptureScreen {

    /**
     * @param args the command line arguments
     */
    public File saveScreenshot(String fileName,String ipaddr) {

		try {
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			Rectangle screenRectangle = new Rectangle(screenSize);
			Robot robot = new Robot();
			BufferedImage image = robot.createScreenCapture(screenRectangle);
			File f=new File("C:/Users/Prajawal/Documents/spy/Screenshot/"+fileName);
                        ImageIO.write(image, "jpg",f );
                        image.flush();
			System.out.println("saved.."+fileName);
                        return f;
		} catch (Exception e) {
			e.printStackTrace();
                         return null;
		}

	}
    public void callcapture(){
        int count = 1;
        JOptionPane.showMessageDialog(null,"Screen Capturing Start....","Alert",JOptionPane.INFORMATION_MESSAGE);
        while (true) {
            SimpleDateFormat dttm=new SimpleDateFormat("dd-mm-yyyy-HH-mm-ss");
            //this.saveScreenshot("C:/screenshots/screen_"+dttm.format(new Date())+".png");
            count++;
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }//while
    }
     public ArrayList<String> readImageFiles(){ //read the image files to place in the list for later use
         File tempDir;
         ArrayList<String> imgList;
           imgList=new ArrayList<String>(); //create ArrayList object to store the image file paths
            tempDir=new File("C:/Users/Prajawal/Documents/spy/Screenshot/"); 
        File[] fileLst = tempDir.listFiles();
        for (int i = 0; i < fileLst.length; i++) {
         imgList.add(fileLst[i].getAbsolutePath());

         }
        return imgList;
    }
    
}
