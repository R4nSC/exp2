package lang.c.parse;

import java.io.PrintStream;

import lang.*;
import lang.c.*;

public class UnsignedFactor extends CParseRule {
	// unsignedFactor ::= factorAmp | number | LPAR expression RPAR
	private CParseRule number;
	private CParseRule factorAmp;
	private CParseRule expression;
	
	public UnsignedFactor(CParseContext pcx) {
	}
	public static boolean isFirst(CToken tk) {
		return FactorAmp.isFirst(tk) | Number.isFirst(tk) | tk.getType() == CToken.TK_LPAR;
	}
	public void parse(CParseContext pcx) throws FatalErrorException {
		// ここにやってくるときは、必ずisFirst()が満たされている
		CTokenizer ct = pcx.getTokenizer();
		CToken tk = ct.getCurrentToken(pcx);
		if(Number.isFirst(tk)) {
			number = new Number(pcx);
			number.parse(pcx);
		} else if(FactorAmp.isFirst(tk)) {
			factorAmp = new FactorAmp(pcx);
			factorAmp.parse(pcx);
		} else if(tk.getType() == CToken.TK_LPAR) {
			tk = ct.getNextToken(pcx);
			if(Expression.isFirst(tk)) {
				expression = new Expression(pcx);
				expression.parse(pcx);
				tk = ct.getCurrentToken(pcx);
				if(tk.getType() != CToken.TK_RPAR) {
					pcx.fatalError("'('に対応する')'が足りません。");
				}
				ct.getNextToken(pcx);
			}
		}
	}

	public void semanticCheck(CParseContext pcx) throws FatalErrorException {
		if (number != null) {
			number.semanticCheck(pcx);
			setCType(number.getCType());		// number の型をそのままコピー
			setConstant(number.isConstant());	// number は常に定数
		} else if (factorAmp != null) {
			factorAmp.semanticCheck(pcx);
			setCType(factorAmp.getCType());
			setConstant(factorAmp.isConstant());
		} else if (expression != null) {
			expression.semanticCheck(pcx);
			setCType(expression.getCType());
			setConstant(expression.isConstant());
		}
	}

	public void codeGen(CParseContext pcx) throws FatalErrorException {
		PrintStream o = pcx.getIOContext().getOutStream();
		o.println(";;; unsignedFactor starts");
		if (number != null) { number.codeGen(pcx); }
		else if (factorAmp != null) { factorAmp.codeGen(pcx); }
		else if (expression != null) { expression.codeGen(pcx); }
		o.println(";;; unsignedFactor completes");
	}
}

class FactorAmp extends CParseRule {
	// factorAmp ::= Amp number
	private CParseRule number;
	public FactorAmp(CParseContext pcx) {
	}
	public static boolean isFirst(CToken tk) {
		return tk.getType() == CToken.TK_AMP;
	}
	public void parse(CParseContext pcx) throws FatalErrorException {
		// ここにやってくるときは、必ずisFirst()が満たされている
		CTokenizer ct = pcx.getTokenizer();
		CToken tk = ct.getNextToken(pcx);
		if(Number.isFirst(tk)) {
			number = new Number(pcx);
			number.parse(pcx);
		} else {
			pcx.fatalError("&のあとに数値がありません");
		}
	}

	public void semanticCheck(CParseContext pcx) throws FatalErrorException {
		if (number != null) {
			number.semanticCheck(pcx);
			if(number.getCType() == CType.getCType(CType.T_int)) {
				setCType(CType.getCType(CType.T_pint)); // 整数へのポインタ型に設定
			}
			setConstant(number.isConstant());
		}
	}

	public void codeGen(CParseContext pcx) throws FatalErrorException {
		PrintStream o = pcx.getIOContext().getOutStream();
		o.println(";;; factorAmp starts");
		if (number != null) { number.codeGen(pcx); }
		o.println(";;; factorAmp completes");
	}
}