package lang;

// 字句へのアクセスルーチンを定義した抽象クラス
public abstract class Token {
	public abstract int		getType();
	public abstract String	getText();
	public abstract int		getLineNo();
	public abstract int		getColumnNo();
	public String	toExplainString() {
		return "[" + getLineNo() + "行目," + getColumnNo() + "文字目の'" + getText() + "']";
	}
}
