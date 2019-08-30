package lang;

// コンパイラと呼ばれるものを作るときにParseRuleで必ず定義しなければならないメソッド群
public interface Compiler<Pctx> {
	public abstract void semanticCheck(Pctx pcx) throws FatalErrorException;
	public abstract void codeGen(Pctx pcx) throws FatalErrorException;
}

