
public class PairBean {
	public int lineNum;
	public int columnNum;

	public boolean valid(int productValue) {
		return lineNum > 0 && columnNum > 0 && columnNum * lineNum <= productValue;
	}
}
