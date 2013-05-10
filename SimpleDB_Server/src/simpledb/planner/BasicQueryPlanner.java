package simpledb.planner;

import simpledb.tx.Transaction;
import simpledb.query.*;
import simpledb.parse.*;
import simpledb.server.SimpleDB;
import java.util.*;

/**
 * The simplest, most naive query planner possible.
 * @author Edward Sciore
 */
public class BasicQueryPlanner implements QueryPlanner {
   
   /**
    * Creates a query plan as follows.  It first takes
    * the product of all tables and views; it then selects on the predicate;
    * and finally it projects on the field list. 
    */
   public Plan createPlan(QueryData data, Transaction tx) {
      //Step 1: Create a plan for each mentioned table or view
      List<Plan> plans = new ArrayList<Plan>();
      for (String tblname : data.tables()) {
         String viewdef = SimpleDB.mdMgr().getViewDef(tblname, tx);
         if (viewdef != null)
            plans.add(SimpleDB.planner().createQueryPlan(viewdef, tx));
         else
            plans.add(new TablePlan(tblname, tx));
      }
      
      //Step 2: Create the product of all table plans
      Plan p = plans.remove(0);
      for (Plan nextplan : plans)
         p = new ProductPlan(p, nextplan);
      
      //TODO Insert our code here!
      
      System.out.println("Right before nested terms handling in BasicQueryPlanner");
      
      if(!data.pred().nestedTerms().isEmpty()){
    	  for(NestedTerm term : data.pred().nestedTerms()){
    		  if(term.getNegated()){
    			  p = new AntijoinPlan(p, createPlan(term.getQueryData(),tx), term.getNestedPred());
    		  } else{
    			  p = new SemijoinPlan(p, createPlan(term.getQueryData(),tx), term.getNestedPred());
    		  }
    	  }
      }
      
      System.out.println("Right after nested terms handling in BasicQueryPlanner");

      
      //Step 3: Add a selection plan for the predicate
      p = new SelectPlan(p, data.pred().simplePred());
      
      //Maybe?       p = new SelectPlan(p, data.pred().simpleTerms());

      
      //Step 4: Project on the field names
      p = new ProjectPlan(p, data.fields());
      return p;
   }
}
