package lang;

// 構文規則を解析するための抽象クラス(具象クラスにおいてparse()メソッドを自分で記述)
public abstract class ParseRule<Pctx> {
	public abstract void parse(Pctx pcx) throws FatalErrorException;
	public abstract void semanticCheck(Pctx pcx) throws FatalErrorException;
	public abstract void codeGen(Pctx pcx) throws FatalErrorException;
}
