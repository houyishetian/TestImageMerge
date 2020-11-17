import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TestImage {

	private static void generateImage(String picsPath, String outputName) throws Exception {
		if (picsPath == null || picsPath.length() == 0) {
			System.out.println("ͼƬ����Ŀ¼����Ϊ�գ�");
			return;
		}
		if (outputName == null || outputName.length() == 0) {
			System.out.println("�ϳ�ͼƬ���Ʋ���Ϊ�գ�");
			return;
		}
		if (!new File(picsPath).isDirectory()) {
			System.out.println("ͼƬ����Ŀ¼�����ļ��У�");
			return;
		}
		File desFile = new File(new File(picsPath), outputName + ".png");
		if (desFile.exists()) {
			System.out.println("�ϳ�ͼƬ�Ѵ��ڣ�");
			return;
		}
		System.out.println("��ʼ�ϳ�ͼƬ...");
		System.out.println();
		List<String> extension = new ArrayList<>();
		extension.add(".png");
		extension.add(".jpg");
		List<File> allPicFiles = FileScanUtil.getAllPics(picsPath, extension);
		if (allPicFiles == null || allPicFiles.size() == 0) {
			System.out.println("�ϳ�ʧ�ܣ���Ŀ¼��û��ͼƬ��");
		} else {
			List<BufferedImage> allImages = FileScanUtil.parseImageFile(allPicFiles);
			AppendImageUtil imageUtil = new AppendImageUtil();
			BufferedImage resultImage = imageUtil.mergeImage(allImages, 30);
			FileScanUtil.writeImageToFile(resultImage, desFile.getAbsolutePath());
			System.out.println("�ϳɳɹ�����飺" + desFile.getAbsolutePath());
		}
		System.out.println();
	}

	public static void main(String[] args) throws Exception {
		Scanner scanner = new Scanner(System.in);
		System.out.println();
		System.out.println("����ͼƬ�����ļ��У�");
		String picsPath = scanner.next();
		System.out.println();
		System.out.println("����ϳɺ��ͼƬ���ƣ�����Ҫ��չ����Ĭ��png��ʽ��");
		String outputName = scanner.next();
		System.out.println();
		scanner.close();
		generateImage(picsPath, outputName);
	}
}
