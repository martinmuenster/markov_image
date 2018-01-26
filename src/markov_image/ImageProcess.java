package markov_image;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class ImageProcess {
	public ArrayList<Integer> shades[];
	public int width;
	public int height;
	public BufferedImage img;

	public void setupNodes() throws IOException {
		shades = new ArrayList[256*256*256];
	    img = null;
	    File f = null;
	    
	    f = new File("image4.jpg");
	    img = ImageIO.read(f);
	    width = img.getWidth();
	    height = img.getHeight();
	    
	    for (int i = 0; i < width; i++) {
	    	for (int j = 0; j < height; j++) {
	    		int pixel = getColor(i,j);
	    		
	    	    if (shades[pixel] == null) {
	    	    	ArrayList<Integer> neighbors = new ArrayList<Integer>();
	    	    	shades[pixel] = neighbors;
	    	    }
	    	    /*
	    	    shades[pixel].add(getGrey(i-1,j));
	    	    shades[pixel].add(getGrey(i+1,j));
	    	    shades[pixel].add(getGrey(i,j+1));
	    	    shades[pixel].add(getGrey(i,j-1));
	    	    */
	    		if (i > 0) 
	    			shades[pixel].add(getColor(i-1,j));
	    		if (i < width-1) 
	    			shades[pixel].add(getColor(i+1,j));
	    		if (j > 0) 
	    			shades[pixel].add(getColor(i,j-1));
	    		if (j < height-1) 
	    			shades[pixel].add(getColor(i,j+1));
	    	    
	    	}
	    }
	    //System.out.println(shades[2].toString());
	}
	
	public int getGrey(int x, int y) {
		int pixelVal = img.getRGB(x,y);
	    int grey = (pixelVal>>8) & 0xff;
	    return grey;
	}
	
	public int getColor(int x, int y) {

		int pixelVal = img.getRGB(x,y);
		pixelVal = pixelVal & 0x00ffffff;
		return pixelVal;
	}
	      
	public void constructImage() {
		System.out.println("Percent Done:");
		int last = getColor(0,0);
		Random r = new Random();
		r.setSeed(System.nanoTime());
		for (int i = 0; i < width; i++) {
			if (i%100 == 0)
				System.out.println((float) i/ (float) width);
	    	for (int j = 0; j < height; j++) {
	    		ArrayList<Integer> temp = new ArrayList<Integer>();
	    		temp.addAll(shades[last]);
	    		if (i > 0)
	    			temp.addAll(shades[getColor(i-1,j)]);
	    		if (i < width-1)
	    			temp.addAll(shades[getColor(i+1,j)]);
	    		if (j > 0)
	    			temp.addAll(shades[getColor(i,j-1)]);
	    		if (j < height-1)
	    			temp.addAll(shades[getColor(i,j+1)]);
	    		Collections.shuffle(temp, r);
	    		int curr = temp.get(0);
	    		int pixel = (curr) | (255<<24);
	    		img.setRGB(i,j,pixel);
	    		last = curr;
	    		
	    	}
		}
	    try{
	    	File f = new File("end11.jpg");
	        ImageIO.write(img, "jpg", f);
	    }
	    catch(IOException e){
	        System.out.println(e);
	      }
	    System.out.println("Completed");
	}
	    
	    
	   
	
}
