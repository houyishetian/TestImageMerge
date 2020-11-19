import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageCompressUtil {

	public static BufferedImage compressImage(File imageFile, double scale) {
		BufferedImage bufferedImage;
		try {
			bufferedImage = ImageIO.read(imageFile);
			int width = bufferedImage.getWidth();
			int height = bufferedImage.getHeight();

			width = (int) (width * scale);
			height = (int) (height * scale);

			Image image = bufferedImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			Graphics graphics = outputImage.getGraphics();
			graphics.drawImage(image, 0, 0, null);
			graphics.dispose();
			return outputImage;
		} catch (IOException e) {
			System.out.println("Ñ¹ËõÍ¼Æ¬Ê±³ö´íÁË");
			e.printStackTrace();
		}
		return null;
	}
}
