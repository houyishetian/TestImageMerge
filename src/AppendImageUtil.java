import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.ValidationEvent;

public class AppendImageUtil {

	private int columnCount = 5;

	public AppendImageUtil(int columnCount) {
		this.columnCount = columnCount;
	}

	public AppendImageUtil() {

	}

	private PairBean getLineNumAndColumnNum(int totalNum) {
		if (totalNum <= 0) {
			System.out.println("totalNum is illegal!");
			return null;
		}
		PairBean bean = new PairBean();
		bean.second = columnCount;
		bean.first = totalNum % columnCount == 0 ? totalNum / columnCount : (totalNum / columnCount + 1);
		return bean;
	}

	private <T> List<List<T>> groupData(List<T> imgs) {
		if (imgs == null || imgs.size() == 0) {
			System.out.println("illegal params");
			return null;
		}
		PairBean<Integer, Integer> groupInfo = getLineNumAndColumnNum(imgs.size());
		List<List<T>> result = new ArrayList<List<T>>();
		for (int i = 1; i <= groupInfo.first; i++) {
			int startIndex = 0;
			int endIndex = 0;
			if (i != groupInfo.first) {
				// �����һ��
				startIndex = (i - 1) * groupInfo.second;
				endIndex = i * groupInfo.second;
			} else {
				// ���һ��
				startIndex = (i - 1) * groupInfo.second;
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
	 */
	public BufferedImage mergeImage(List<BufferedImage> imgs, int marginPxBetweenImage) throws IOException {
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
		PairBean<Integer, Integer> pairBean = getLineNumAndColumnNum(imgs.size());
		// ����ÿ�еĸ��������������ٸ����������Ϊ��׼����������ʹÿ��ͼƬ�ڸ��Եĸ��������
		int realColumn = groupResult.get(0).size();
		int desWith = realColumn * maxWidth + realColumn * marginPxBetweenImage;
		int desHeight = pairBean.first * maxHeight + pairBean.first * marginPxBetweenImage;
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
