import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.ValidationEvent;

public class AppendImageUtil {

	private static PairBean getLineNumAndColumnNum(int totalNum) {
		if (totalNum <= 0) {
			System.out.println("totalNum is illegal!");
			return null;
		}
		PairBean bean = new PairBean();
		bean.columnNum = 5;
		bean.lineNum = totalNum % 5 == 0 ? totalNum / 5 : (totalNum / 5 + 1);
		return bean;
	}

	private static <T> List<List<T>> groupData(List<T> imgs) {
		if (imgs == null || imgs.size() == 0) {
			System.out.println("illegal params");
			return null;
		}
		PairBean groupInfo = getLineNumAndColumnNum(imgs.size());
		List<List<T>> result = new ArrayList<List<T>>();
		for (int i = 1; i <= groupInfo.lineNum; i++) {
			int startIndex = 0;
			int endIndex = 0;
			if (i != groupInfo.lineNum) {
				// �����һ��
				startIndex = (i - 1) * groupInfo.columnNum;
				endIndex = i * groupInfo.columnNum;
			} else {
				// ���һ��
				startIndex = (i - 1) * groupInfo.columnNum;
				endIndex = imgs.size();
			}
			List<T> eachGroup = new ArrayList<>();
			for (int j = startIndex; j < endIndex; j++) {
				eachGroup.add(imgs.get(j));
			}
			result.add(eachGroup);
		}
		return result;
	}

	/**
	 * �ϲ���������ͼƬ��һ��ͼƬ
	 *
	 * @param isHorizontal true����ˮƽ�ϲ���fasle����ֱ�ϲ�
	 * @param imgs         ���ϲ���ͼƬ����
	 * @return
	 * @throws IOException
	 */
	public static BufferedImage mergeImage(List<BufferedImage> imgs, int marginPxBetweenImage) throws IOException {
		if (imgs == null || imgs.isEmpty()) {
			System.out.println("imput images is null!");
			return null;
		}
		List<List<BufferedImage>> groupResult = groupData(imgs);
		if (groupResult == null || groupResult.isEmpty()) {
			System.out.println("group list failed!");
			return null;
		}
		// ���ÿ����ͼƬ�ļ������50��ǿ�Ƹĳ�30
		marginPxBetweenImage = (marginPxBetweenImage > 0 && marginPxBetweenImage < 50) ? marginPxBetweenImage : 30;

		// �ҵ�����ͼƬ�����ֵ�����ֵ
		int maxWidth = 0, maxHeight = 0;
		for (BufferedImage item : imgs) {
			int currentWidth = item.getWidth();
			int currentHeight = item.getHeight();
			if (currentWidth > maxWidth) {
				maxWidth = currentWidth;
			}
			if (currentHeight > maxHeight) {
				maxHeight = currentHeight;
			}
		}
		PairBean pairBean = getLineNumAndColumnNum(imgs.size());
		// ����ÿ�еĸ��������������ٸ����������Ϊ��׼����������ʹÿ��ͼƬ�ڸ��Եĸ��������
		int desWith = pairBean.columnNum * maxWidth + pairBean.columnNum * marginPxBetweenImage;
		int desHeight = pairBean.lineNum * maxHeight + pairBean.lineNum * marginPxBetweenImage;
		BufferedImage destImage = new BufferedImage(desWith, desHeight, BufferedImage.TYPE_INT_ARGB);

		// ����д��ͼƬ
		int currentImageX = 0, currentImageY = 0;
		for (List<BufferedImage> currentLineImages : groupResult) {
			for (BufferedImage currentImage : currentLineImages) {
				// ��ͼƬ��ȡ�� int[] ��
				int width = currentImage.getWidth();
				int height = currentImage.getHeight();
				int[] imageData = new int[width * height];
				currentImage.getRGB(0, 0, width, height, imageData, 0, width);
				// Ϊ�˱�֤���У���������ƫ����
				int startX = currentImageX + (maxWidth - width) / 2;
				int startY = currentImageY + (maxHeight - height) / 2;
				// ��ʼд��desImage
				destImage.setRGB(startX, startY, width, height, imageData, 0, width);
				currentImageX = currentImageX + maxWidth + marginPxBetweenImage;
			}
			currentImageX = 0;
			currentImageY = currentImageY + maxHeight + marginPxBetweenImage;
		}
		return destImage;
	}
}
