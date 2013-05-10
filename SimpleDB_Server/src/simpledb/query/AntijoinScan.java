package simpledb.query;

//Note that it uses the CombineScan class, just like SemijoinScan.
public class AntijoinScan implements Scan {
	private Scan s1, s2;
	private Predicate pred;

	public AntijoinScan(Scan s1, Scan s2, Predicate pred) {
		this.s1 = s1;
		this.s2 = s2;
		this.pred = pred;
	}

	public void beforeFirst() {
		s1.beforeFirst();
	}

	public boolean next() {
		while (s1.next()) {
			boolean found = false;
			s2.beforeFirst();
			while (s2.next()) {
				Scan s = new CombineScan(s1, s2);
				if (pred.isSatisfied(s)) {
					found = true;
					break;
				}
			}
			s2.close();
			if (!found)
				return true;
		}
		return false;
	}

	public void close() {
		s1.close();
	}

	public Constant getVal(String fldname) {
		return s1.getVal(fldname);
	}

	public int getInt(String fldname) {
		return s1.getInt(fldname);
	}

	public String getString(String fldname) {
		return s1.getString(fldname);
	}

	public boolean hasField(String fldname) {
		return s1.hasField(fldname);
	}

	public boolean wasNull() {
		//return s1.wasNull();
		//TODO See which type of plan this scan has, so you can call the method on the type of plan for this scan.
		return false;
	}
}