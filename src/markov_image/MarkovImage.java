package markov_image;

import java.io.IOException;

public class MarkovImage {
	public static void main(String args[]) {
		ImageProcess i = new ImageProcess();
		try {
			i.setupNodes();
			i.constructImage();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
