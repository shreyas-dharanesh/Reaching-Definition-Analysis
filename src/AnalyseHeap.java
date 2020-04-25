
import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import soot.*;
import soot.jimple.internal.*;
import soot.jimple.AssignStmt;
import soot.jimple.DefinitionStmt;
import soot.jimple.FieldRef;
import soot.jimple.IdentityStmt;
import soot.jimple.InstanceFieldRef;
import soot.jimple.InstanceInvokeExpr;
import soot.jimple.IntConstant;
import soot.jimple.InvokeExpr;
import soot.jimple.InvokeStmt;
import soot.jimple.Jimple;
import soot.jimple.NullConstant;
import soot.jimple.StaticFieldRef;
import soot.jimple.Stmt;
import soot.jimple.StringConstant;
import soot.options.Options;
import soot.jimple.GotoStmt;
import soot.jimple.ArrayRef;

public class AnalyseHeap extends BodyTransformer {

	public static void main(String[] args) {

		String mainclass = "MergeSort";

		// setting classpath
		String javapath = System.getProperty("java.class.path");
		String jredir = System.getProperty("java.home") + "/lib/rt.jar";
		String path = javapath + File.pathSeparator + jredir;
		Scene.v().setSootClassPath(path);

		// adding an intra-procedural analysis phase to Soot
		AnalyseHeap analysis = new AnalyseHeap();
		PackManager.v().getPack("jtp").add(new Transform("jtp.AnalyseHeap", analysis));

		// load and set main class
		Options.v().set_app(true);
		SootClass appclass = Scene.v().loadClassAndSupport(mainclass);
		Scene.v().setMainClass(appclass);
		
		Scene.v().loadNecessaryClasses();

		// start working
		PackManager.v().runPacks();

		PackManager.v().writeOutput();
	}

	@Override
	protected void internalTransform(Body body, String phaseName, Map<String, String> options) {

		System.out.println("Processing method : " + body.getMethod().getSignature());
		Iterator<Unit> stmts = body.getUnits().iterator();
		HashStatement hs = new HashStatement();
		while (stmts.hasNext()) {
			Unit stmt = stmts.next();
			if (stmt.getClass() == JAssignStmt.class) {
				AbstractDefinitionStmt astmt = (AbstractDefinitionStmt) stmt;
				String output = "";
				output += stmt;
				output += " ---> [Entry] " + hs.toString();
				Value lhs = astmt.getLeftOp();
				Value rhs = astmt.getRightOp();
				hs.put(lhs.toString(), rhs.toString());
				output += " ---> [Exit] " + hs.toString();
				System.out.println(output);
			}
		}
		System.out.println("\n");
	}

}