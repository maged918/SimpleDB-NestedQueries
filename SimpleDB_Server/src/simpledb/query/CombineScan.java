package simpledb.query;
//You need to create another new class called CombineScan
//A technical difficulty occurs in testing for matching records, 
//namely that the predicate’s isSatisfied method only works for a single scan,
//not two scans. Thus we create a new class CombineScan, whose job is to simply 
//turn s1 and s2 into a single scan by implementing the get methods. 
public class CombineScan implements Scan {
	private Scan s1, s2;

	public CombineScan(Scan s1, Scan s2) {
		this.s1 = s1;
		this.s2 = s2;
	}

	public void beforeFirst() {
	}

	public void close() {
	}

	public boolean next() {
		return false;
	}

	public int getInt(String fldname) {
		if (s1.hasField(fldname))
			return s1.getInt(fldname);
		else
			return s2.getInt(fldname);
	}

	public String getString(String fldname) {
		if (s1.hasField(fldname))
			return s1.getString(fldname);
		else
			return s2.getString(fldname);
	}

	public Constant getVal(String fldname) {
		if (s1.hasField(fldname))
			return s1.getVal(fldname);
		else
			return s2.getVal(fldname);
	}

	public boolean hasField(String fldname) {
		return s1.hasField(fldname) || s2.hasField(fldname);
	}

	public boolean wasNull() {
		return false;
	}
}