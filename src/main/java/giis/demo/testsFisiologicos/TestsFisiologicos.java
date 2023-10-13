package giis.demo.testsFisiologicos;

public class TestsFisiologicos {
	
	public static final int MUJER = 0;
	public static final int HOMBRE = 1;
	
	public static Double getTestCooper(double distance) {
		return (22.351 * distance) + 11.288;
	}
	
	public static Double getTestRockport(int peso, int edad, int sexo, double tiempo, int pulsaciones) {
		return 132.7 - (0.17 * peso) - (0.39 * edad) + (6.31 * sexo) - (3.27 * tiempo) - (0.156 * pulsaciones);
	}
	
}
