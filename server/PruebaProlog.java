import org.jpl7.*;


public class PruebaProlog {

	public static void main(String[] args) {
		
		
		
		Query e1 = new Query("consult",	new Term[] { new Atom ( "E:\\eclipse workspace\\Prueba-Prolog\\src\\test.pl" ) } );
		
		
		
		System.out.println(" consult " + (e1.hasSolution() ? "Succeded" : "Failed"));
	}

}
