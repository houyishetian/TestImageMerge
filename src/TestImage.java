import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TestImage {

	public static void main(String[] args) throws Exception {
		String path = "C:\\Users\\lisonglin\\Desktop\\test";
		String desPic = path + File.separator + "merged.png";
		List<String> extensions = new ArrayList<>();
		extensions.add(".png");
		extensions.add(".jpg");

		List<BufferedImage> list = FileScanUtil.parseImageFile(FileScanUtil.getAllPics(path, extensions));
		if (list != null && list.size() != 0) {
			FileScanUtil.writeImageToFile(AppendImageUtil.mergeImage(list, 30), desPic);
		}
		System.out.println("end!");
	}
}
