package lang.c;

import java.util.HashMap;

// 切り出すべき文字列がどの字句タイプになるのかを決めるルールを記述
public class CTokenRule extends HashMap<String, Object> {
	private static final long serialVersionUID = 1139476411716798082L;

	public CTokenRule() {
//		put("int",		new Integer(CToken.TK_INT));
	}
}