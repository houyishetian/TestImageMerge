import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class FileScanUtil {

	public static List<File> getAllPics(String path, List<String> extension) {
		try {
			if (path == null || path.length() == 0) {
				System.out.println("file path is illegal!");
				return null;
			}
			File file = new File(path);
			if (file == null || !file.isDirectory()) {
				System.out.println("file not exist or file is not a directory!");
				return null;
			}
			File[] subFiles = file.listFiles();
			ArrayList<File> result = new ArrayList<>();
			for (File item : subFiles) {
				if (!item.isFile())
					continue;
				if (extension != null && !extension.isEmpty()) {
					String fileName = item.getName();
					int lastIndexOfDot = fileName.lastIndexOf(".");
					String extensionName = fileName.substring(lastIndexOfDot < 0 ? 0 : lastIndexOfDot).toLowerCase();
					for (String extensionItem : extension) {
						if (extensionItem != null && extensionName.equals(extensionItem.toLowerCase())) {
							result.add(item);
							break;
						}
					}
				} else {
					result.add(item);
				}
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<BufferedImage> parseImageFile(List<File> files, float scale) {
		try {
			List<BufferedImage> result = new ArrayList<>();
			for (File item : files) {
				result.add(ImageCompressUtil.compressImage(item, scale));
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void writeImageToFile(BufferedImage imageBuffer, String desPath) {
		if (imageBuffer == null || desPath == null || desPath.length() == 0) {
			System.out.println("param is Illegal!");
		}
		try {
			ImageIO.write(imageBuffer, "png", new File(desPath));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
