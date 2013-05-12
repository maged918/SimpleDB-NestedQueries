package simpledb.query;

import com.sun.org.apache.bcel.internal.generic.FDIV;
import simpledb.parse.QueryData;
import simpledb.record.Schema;
import simpledb.tx.Transaction;

public class NestedTerm implements Term {
	private QueryData nestedQuery;
	private boolean negated;
	private Predicate nestedPred;
	private String fldname;

	public NestedTerm(String fldname, QueryData nestedQuery, boolean negated) {
		this.nestedQuery = nestedQuery;
		this.negated = negated;
		this.fldname = fldname;
		//this.schema = schema; //Probably not very important?
		String nestedfldname = nestedQuery.fields().iterator().next();
		Expression exp1 = new FieldNameExpression(fldname);
		Expression exp2 = new FieldNameExpression(nestedfldname);
		nestedPred = new Predicate(new BasicTerm(exp1, exp2));
	}

	public QueryData getQueryData() {
		return nestedQuery;
	}

	public Predicate getNestedPred() {
		return nestedPred;
	}

	public boolean getNegated() {
		return negated;
	}

	public int reductionFactor(Plan p) {
		return 1; // some default
	}

	public Constant equatesWithConstant(String fldname) {
		return null;
	}

	public String equatesWithField(String fldname) {
		return null;
	}

	public boolean appliesTo(Schema sch) {
		return sch.fields().contains(fldname);
	}

	public boolean isSatisfied(Scan s, Transaction tx) {
		return false;
	}

	public String toString() {
		String op = negated ? "NOT IN" : "IN";
		return fldname + " " + op + "(" + nestedQuery + ")";
	}

	@Override
	public boolean isSatisfied(Scan s) {
		// TODO Auto-generated method stub
		return false;
	}
}