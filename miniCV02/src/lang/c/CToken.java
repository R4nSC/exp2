package lang.c;

import lang.SimpleToken;

// 字句として認識しなければならないカテゴリを定義するクラス(何行目、何文字目、綴りの情報を記録)
public class CToken extends SimpleToken {
	public static final int TK_PLUS			= 2;				// +
	public static final int TK_MINUS 		= 3;				// -
	public static final int TK_AMP			= 4;				// &

	public CToken(int type, int lineNo, int colNo, String s) {
		super(type, lineNo, colNo, s);
	}
}
