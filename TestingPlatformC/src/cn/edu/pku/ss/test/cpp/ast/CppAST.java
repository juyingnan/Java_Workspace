package cn.edu.pku.ss.test.cpp.ast;

public class CppAST {

	public  static final String[] Operators = new String[]{
		"",
		/**
		 * multiply *
		 */
		"*",
		/**
		 * divide /
		 */
		"/",

		/**
		 * modulo %
		 */
		"%",

		/**
		 * plus +
		 */
		"+",

		/**
		 * minus -
		 */
		"-",

		/**
		 * shift left <<
		 */
		"<<",

		/**
		 * shift right >>
		 */
		">>",

		/**
		 * less than <
		 */
		"<",

		/**
		 * greater than >
		 */
		">",

		/**
		 * less than or equals <=
		 */
		"<=",

		/**
		 * greater than or equals >=
		 */
		">=",

		/**
		 * binary and &
		 */
		"&",

		/**
		 * binary Xor ^
		 */
		"^",

		/**
		 * binary Or |
		 */
		"|",

		/**
		 * logical and &&
		 */
		"&&",

		/**
		 * logical or ||
		 */
		"||",

		/**
		 * assignment =
		 */
		"=",

		/**
		 * multiply assignment *=
		 */
		"*=",

		/**
		 * divide assignemnt /=
		 */
		"/=",

		/**
		 * modulo assignment %=
		 */
		"%=",

		/**
		 * plus assignment +=
		 */
		"+=",

		/**
		 * minus assignment -=
		 */
		"-=",

		/**
		 * shift left assignment <<=
		 */
		"<<=",

		/**
		 * shift right assign >>=
		 */
		">>=",

		/**
		 * binary and assign &=
		 */
		"&=",

		/**
		 * binary Xor assign ^=
		 */
		"^=",

		/**
		 * binary Or assign |=
		 */
		"|=",

		/**
		 * equals ==
		 */
		"==",

		/**
		 * not equals !=
		 */
		"!=",

		/**
		 * For c==, only.
		 * <code>op_pmdot</code> pointer-to-member field dereference.
		 */
		//TODO: Check operator
		"->",

		/**
		 * For c++, only.
		 * <code>op_pmarrow</code> pointer-to-member pointer dereference.
		 */
		"->",

		/**
		 * For g++, only.
		 * <code>op_max</code> represents >?
		 */
		">?",

		/**
		 * For g++, only.
		 * <code>op_min</code> represents <?
		 */
		"<?",
		
		/**
		 * For gcc compilers, only.
		 * <code>op_ellipses</code> represents ... as used for case ranges.
		 */
		"..."
	};
	
	public final static String[] ElaboratedTypeSpecifier = new String[]{
		/**
		 * Enumeration.
		 */
		"enum",

		/**
		 * Structure.
		 */
		"struct",

		/**
		 * Union.
		 */
		"union"

	};
	
}
