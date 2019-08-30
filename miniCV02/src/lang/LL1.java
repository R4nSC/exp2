package lang;

// LL(1)文法に従う構文規則を解析するためのインタフェース(isFirst()メソッドをstaticに定義する必要あり)
public interface LL1<Tkn> {
// インタフェースに static とは書けないのでコメントアウトしてあるが、そういうメソッドを忘れずに実装して欲しい
//	public abstract static boolean isFirst(Tkn tk);
}
