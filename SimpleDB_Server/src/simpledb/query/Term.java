package simpledb.query;

import simpledb.record.Schema;

public interface Term {

	boolean isSatisfied(Scan s);

	Constant equatesWithConstant(String fldname);

	int reductionFactor(Plan p);

	boolean appliesTo(Schema sch);

	String equatesWithField(String fldname);

}
