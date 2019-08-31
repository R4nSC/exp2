package lang.c.parse;

import java.io.PrintStream;

import lang.*;
import lang.c.*;

public class Factor extends CParseRule {
	// factor ::= plusFactor | minusFactor | unsignedFactor
	private CParseRule factor;
	
	public Factor(CParseContext pcx) {
	}
	public static boolean isFirst(CToken tk) {
		return PlusFactor.isFirst(tk) | MinusFactor.isFirst(tk) | UnsignedFactor.isFirst(tk);
	}
	public void parse(CParseContext pcx) throws FatalErrorException {
		// ここにやってくるときは、必ずisFirst()が満たされている
		CTokenizer ct = pcx.getTokenizer();
		CToken tk = ct.getCurrentToken(pcx);
		if(PlusFactor.isFirst(tk)) {
			factor = new PlusFactor(pcx);
			factor.parse(pcx);
		} else if(MinusFactor.isFirst(tk)) {
			factor = new MinusFactor(pcx);
			factor.parse(pcx);
		} else if(UnsignedFactor.isFirst(tk)) {
			factor = new UnsignedFactor(pcx);
			factor.parse(pcx);
		}
	}

	public void semanticCheck(CParseContext pcx) throws FatalErrorException {
		if (factor != null) {
			factor.semanticCheck(pcx);
			setCType(factor.getCType());		// factor の型をそのままコピー
			setConstant(factor.isConstant());	// factor は常に定数
		} 
	}

	public void codeGen(CParseContext pcx) throws FatalErrorException {
		PrintStream o = pcx.getIOContext().getOutStream();
		o.println(";;; factor starts");
		if (factor != null) { factor.codeGen(pcx); }
		o.println(";;; factor completes");
	}
}

class PlusFactor extends CParseRule {
	// plusFactor ::= PLUS unsignedFactor
	private CToken op;
	private CParseRule unsignedFactor;
	
	public PlusFactor(CParseContext pcx) {
	}
	public static boolean isFirst(CToken tk) {
		return tk.getType() == CToken.TK_PLUS;
	}
	public void parse(CParseContext pcx) throws FatalErrorException {
		// ここにやってくるときは、必ずisFirst()が満たされている
		CTokenizer ct = pcx.getTokenizer();
		op = ct.getCurrentToken(pcx);
		CToken tk = ct.getNextToken(pcx);
		if(UnsignedFactor.isFirst(tk)) {
			unsignedFactor = new UnsignedFactor(pcx);
			unsignedFactor.parse(pcx);
		} 
	}

	public void semanticCheck(CParseContext pcx) throws FatalErrorException {
		if (unsignedFactor != null) {
			unsignedFactor.semanticCheck(pcx);
			setCType(unsignedFactor.getCType());		// unsignedFactor の型をそのままコピー
			setConstant(unsignedFactor.isConstant());	// unsignedFactor は常に定数
		} 
	}

	public void codeGen(CParseContext pcx) throws FatalErrorException {
		PrintStream o = pcx.getIOContext().getOutStream();
		o.println(";;; plusFactor starts");
		if (unsignedFactor != null) { unsignedFactor.codeGen(pcx); }
		o.println(";;; plusFactor completes");
	}
}

class MinusFactor extends CParseRule {
	// minusFactor ::= MINUS unsignedFactor
	private CToken op;
	private CParseRule unsignedFactor;
	
	public MinusFactor(CParseContext pcx) {
	}
	public static boolean isFirst(CToken tk) {
		return tk.getType() == CToken.TK_MINUS;
	}
	public void parse(CParseContext pcx) throws FatalErrorException {
		// ここにやってくるときは、必ずisFirst()が満たされている
		CTokenizer ct = pcx.getTokenizer();
		op = ct.getCurrentToken(pcx);
		CToken tk = ct.getNextToken(pcx);
		if(UnsignedFactor.isFirst(tk)) {
			unsignedFactor = new UnsignedFactor(pcx);
			unsignedFactor.parse(pcx);
		} 
	}

	public void semanticCheck(CParseContext pcx) throws FatalErrorException {
		if (unsignedFactor != null) {
			unsignedFactor.semanticCheck(pcx);
			setCType(unsignedFactor.getCType());		// unsignedFactor の型をそのままコピー
			setConstant(unsignedFactor.isConstant());	// unsignedFactor は常に定数
		} 
	}

	public void codeGen(CParseContext pcx) throws FatalErrorException {
		PrintStream o = pcx.getIOContext().getOutStream();
		if (unsignedFactor != null) { 
			unsignedFactor.codeGen(pcx);
			o.println("\tMOV\t-(R6), R0\t; minusFactor: スタックから数字を取り出して、符号反転<" + op.toString() + ">");
			o.println("\tMOV\t#0, R1\t; minusFactor:");
			o.println("\tSUB\tR0, R1\t; minusFactor:");
			o.println("\tMOV\tR1, (R6)+\t; minusFactor:");
		}
	}
}
