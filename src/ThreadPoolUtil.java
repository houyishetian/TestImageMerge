import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadPoolUtil {

	public List<BufferedImage> fixThreadPool(List<File> files, float scale) {
		ExecutorService pool = Executors.newCachedThreadPool();
		try {
			List<BufferedImage> result = new ArrayList<>();
			List<Future<BufferedImage>> futureList = new ArrayList<>();

			for (int i = 0; i < files.size(); i++) {
				File file = files.get(i);
				CompressImage compressImage = new CompressImage(file, scale);
				Future<BufferedImage> compressResult = pool.submit(compressImage);
				futureList.add(compressResult);
			}
			for (Future<BufferedImage> future : futureList) {
				result.add(future.get());
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		pool.shutdown();
		return null;
	}

	class CompressImage implements Callable<BufferedImage> {
		private File file;
		private float scale;

		public CompressImage(File file, float scale) {
			this.file = file;
			this.scale = scale;
		}

		@Override
		public BufferedImage call() throws Exception {
			return ImageCompressUtil.compressImage(file, scale);
		}
	}
}
