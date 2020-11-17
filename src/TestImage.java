import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TestImage {

	private static void generateImage(String picsPath, String outputName) throws Exception {
		if (picsPath == null || picsPath.length() == 0) {
			System.out.println("图片所在目录不能为空！");
			return;
		}
		if (outputName == null || outputName.length() == 0) {
			System.out.println("合成图片名称不能为空！");
			return;
		}
		if (!new File(picsPath).isDirectory()) {
			System.out.println("图片所在目录不是文件夹！");
			return;
		}
		File desFile = new File(new File(picsPath), outputName + ".png");
		if (desFile.exists()) {
			System.out.println("合成图片已存在！");
			return;
		}
		System.out.println("开始合成图片...");
		System.out.println();
		List<String> extension = new ArrayList<>();
		extension.add(".png");
		extension.add(".jpg");
		List<File> allPicFiles = FileScanUtil.getAllPics(picsPath, extension);
		if (allPicFiles == null || allPicFiles.size() == 0) {
			System.out.println("合成失败，本目录下没有图片！");
		} else {
			List<BufferedImage> allImages = FileScanUtil.parseImageFile(allPicFiles);
			AppendImageUtil imageUtil = new AppendImageUtil();
			BufferedImage resultImage = imageUtil.mergeImage(allImages, 30);
			FileScanUtil.writeImageToFile(resultImage, desFile.getAbsolutePath());
			System.out.println("合成成功，检查：" + desFile.getAbsolutePath());
		}
		System.out.println();
	}

	public static void main(String[] args) throws Exception {
		Scanner scanner = new Scanner(System.in);
		System.out.println();
		System.out.println("输入图片所在文件夹：");
		String picsPath = scanner.next();
		System.out.println();
		System.out.println("输入合成后的图片名称，不需要扩展名，默认png格式：");
		String outputName = scanner.next();
		System.out.println();
		scanner.close();
		generateImage(picsPath, outputName);
	}
}
