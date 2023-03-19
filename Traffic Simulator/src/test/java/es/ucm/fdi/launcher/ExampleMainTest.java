package es.ucm.fdi.launcher;

import org.apache.commons.cli.ParseException;
import org.junit.Test;
import static org.junit.Assert.*;
import es.ucm.fdi.launcher.ExampleMain;

import java.io.*;
import java.util.*;

public class ExampleMainTest {
	@Test
	public void parseArgsTest() {
		String[] args;
		String line;

		// Prueba 1
		line = "-i ex1.ini"; // Línea que parsea
		args = line.split("\\s");
		ExampleMain.parseArgs(args);
		assertEquals("1.-Fichero de entrada mal", "src/main/resources/readStr/ex1.ini",
				ExampleMain.getInFile());
		assertEquals("1.-Fichero de salida mal",
				"src/main/resources/writeStr/outFile.ini", ExampleMain.getOutFile());
		assertEquals("1.-Tiempo mal", 10, ExampleMain.getTime());

		// Prueba 2
		line = "-i ex1.ini -o ex1.out"; // Línea que parsea
		args = line.split("\\s");
		ExampleMain.parseArgs(args);
		assertEquals("2.-Fichero de entrada mal", "src/main/resources/readStr/ex1.ini",
				ExampleMain.getInFile());
		assertEquals("2.-Fichero de salida mal", "src/main/resources/writeStr/ex1.out",
				ExampleMain.getOutFile());
		assertEquals("2.-Tiempo mal", 10, ExampleMain.getTime());

		// Prueba 3
		line = "-i ex1.ini -t 20"; // Línea que parsea
		args = line.split("\\s");
		ExampleMain.parseArgs(args);
		assertEquals("3.-Fichero de entrada mal", "src/main/resources/readStr/ex1.ini",
				ExampleMain.getInFile());
		assertEquals("3.-Fichero de salida mal",
				"src/main/resources/writeStr/outFile.ini", ExampleMain.getOutFile());
		assertEquals("3.-Tiempo mal", 20, ExampleMain.getTime());

		// Prueba 4
		line = "-i ex1.ini -o ex1.out -t 20"; // Línea que parsea
		args = line.split("\\s");
		ExampleMain.parseArgs(args);
		assertEquals("4.-Fichero de entrada mal", "src/main/resources/readStr/ex1.ini",
				ExampleMain.getInFile());
		assertEquals("4.-Fichero de salida mal", "src/main/resources/writeStr/ex1.out",
				ExampleMain.getOutFile());
		assertEquals("4.-Tiempo mal", 20, ExampleMain.getTime());
	}
	// @Test es como que no termina el test (posiblemente por el System.exit()
	// de parseHelpOption
	public void helpOptionTest() {
		String[] args = "-h".split("\\s");

		ExampleMain.parseArgs(args);
	}

}
