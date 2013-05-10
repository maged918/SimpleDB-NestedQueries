package simpledb.query;

import simpledb.record.Schema;
//The only suspect method is recordsOutput.  
//Our “equal distribution” assumption means that every record will match some record from p2,
//which is not realistic for the cases when semijoin is used.

public class SemijoinPlan implements Plan{
	private Plan p1, p2;
	private Predicate pred;

	public SemijoinPlan(Plan p1, Plan p2, Predicate pred) {
		this.p1 = p1;
		this.p2 = p2;
		this.pred = pred;
	}

	public Scan open() {
		Scan s1 = p1.open();
		Scan s2 = p2.open();
		return new SemijoinScan(s1, s2, pred);
	}

	public int blocksAccessed() {
		return p1.blocksAccessed() + (p1.recordsOutput() * p2.blocksAccessed());
	}

	public int recordsOutput() {
		// assumes that every record matches
		return p1.recordsOutput();
	}

	public int distinctValues(String fldname) {
		return Math.min(p1.distinctValues(fldname), recordsOutput());
	}

	public Schema schema() {
		return p1.schema();
	}
}