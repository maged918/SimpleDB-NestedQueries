package simpledb.query;

import simpledb.record.Schema;
//This code is very similar to that of semijoin. 
//The main difference is in the scan’s next method. 
//The method examines each record of s1, looking for matching s2 records. 
//If no match is found it returns true; otherwise, it considers the next s1 record. 

public class AntijoinPlan implements Plan {
	private Plan p1, p2;
	private Predicate pred;

	public AntijoinPlan(Plan p1, Plan p2, Predicate pred) {
		this.p1 = p1;
		this.p2 = p2;
		this.pred = pred;
	}

	public Scan open() {
		Scan s1 = p1.open();
		Scan s2 = p2.open();
		return new AntijoinScan(s1, s2, pred);
	}

	public int blocksAccessed() {
		return p1.blocksAccessed() + (p1.recordsOutput() * p2.blocksAccessed());
	}

	public int recordsOutput() {
		// assumes that every record matches
		return 0;
	}

	public int distinctValues(String fldname) {
		return p1.distinctValues(fldname);
	}

	public Schema schema() {
		return p1.schema();
	}
}