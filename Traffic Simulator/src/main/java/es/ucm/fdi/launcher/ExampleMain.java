
package es.ucm.fdi.launcher;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import es.ucm.fdi.control.Controller;
import es.ucm.fdi.control.SimWindow;
import es.ucm.fdi.ini.Ini;
import es.ucm.fdi.model.TrafficSimulator;
import es.ucm.fdi.model.TrafficSimulator.UpdateEvent;
import es.ucm.fdi.model.exceptions.SimulationFailedException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class ExampleMain {

	private final static Integer DEFAULT_TIME_VALUE = 10;
	private final static String DEFAULT_OUT_FILE = "outFile.ini";
	private final static String DEFAULT_SIM_MODE = "batch";

	public static Integer _timeLimit = DEFAULT_TIME_VALUE; // Duración de las
															// simulaciones a
															// ejecutar
	public static String _inFile = null; // Fichero de entrada del
														// que leer los datos
	public static String _outFile = DEFAULT_OUT_FILE; // Fichero en el que
														// escribir los datos
	public static String _simMode = DEFAULT_SIM_MODE; // Modo de ejecución

	public static void parseArgs(String[] args) {
		// Si hay algún error de parseo hace que todo termine (ver catch final)

		// define the valid command line options
		Options cmdLineOptions = buildOptions();

		// parse the command line as provided in args
		CommandLineParser parser = new DefaultParser();
		try {
			// Dados los argumentos los parsea con sus comandos correspondientes
			CommandLine line = parser.parse(cmdLineOptions, args);
			parseHelpOption(line, cmdLineOptions);
			parseInFileOption(line);
			parseOutFileOption(line);
			parseStepsOption(line);
			parseModeOption(line);

			// if there are some remaining arguments, then something wrong is
			// provided in the command line!
			String[] remaining = line.getArgs();
			if (remaining.length > 0) {
				String error = "Illegal arguments:";

				for (String o : remaining)
					error += (" " + o);

				throw new ParseException(error);
			}

		} catch (ParseException e) {
			// new Piece(...) might throw GameError exception
			System.err.println(e.getLocalizedMessage());
			System.exit(1);
		}

	}
	private static Options buildOptions() {
		Options cmdLineOptions = new Options();

		// Definimos las que van a ser las opciones/comandos válidos:
		cmdLineOptions.addOption(
				Option.builder("h").longOpt("help").desc("Print this message").build());
		cmdLineOptions.addOption(Option.builder("i").longOpt("input").hasArg()
				.desc("Events input file").build());
		cmdLineOptions.addOption(Option.builder("o").longOpt("output").hasArg()
				.desc("Output file, where reports are written.").build());
		cmdLineOptions.addOption(Option.builder("t").longOpt("ticks").hasArg()
				.desc("Ticks to execute the simulator's main loop (default value is "
						+ DEFAULT_TIME_VALUE + ").")
				.build());
		cmdLineOptions.addOption(Option.builder("m").longOpt("mode").hasArg().desc(
				"’batch’ for batch mode and ’gui’ for GUI mode (default value is ’batch’)")
				.build());

		return cmdLineOptions;
	}
	/**
	 * Si la linea de comandos introducidos contiene la opción de solicitar
	 * ayuda, muestra el mensaje de ayuda y termina la ejecución.
	 */
	private static void parseHelpOption(CommandLine line, Options cmdLineOptions) {
		if (line.hasOption("h")) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp(ExampleMain.class.getCanonicalName(), cmdLineOptions,
					true);
			System.exit(0);
		}
	}
	/**
	 * Parsea el nombre de fichero del que leer los datos. Presupone que este se
	 * sitúa en {@value #DEFAULT_READ_DIRECTORY}. Inicializa {@link #_inFile} a
	 * la ruta del fichero. Si no se introduce un nombre de fichero se toma por
	 * defecto {@value #DEFAULT_INI_FILE}.
	 * 
	 * @throws ParseException
	 *             Si no se parsea correctamente el fichero de entrada.
	 */
	private static void parseInFileOption(CommandLine line) throws ParseException {
		_inFile = line.getOptionValue("i");
	}
	/**
	 * Parsea el nombre de fichero de escritura. Presupone que este se sitúa en
	 * {@value #DEFAULT_WRITE_DIRECTORY}. Inicializa {@link #_outFile} a la ruta
	 * del fichero. Si no se introduce un nombre de fichero se toma por defecto
	 * {@value #DEFAULT_OUT_FILE}.
	 * 
	 * @throws ParseException
	 *             Si no se parsea correctamente el fichero de SALIDA.
	 */
	private static void parseOutFileOption(CommandLine line) throws ParseException {
		_outFile = line.getOptionValue("o", DEFAULT_OUT_FILE);
	}
	/**
	 * Parsea el tiempo de la simulación. Inicializa {@link #_timeLimit} a
	 * {@value #DEFAULT_TIME_VALUE} por defecto si no se introduce un tiempo en
	 * los comandos.
	 * 
	 * @throws ParseException
	 *             Si no se parsea bien el tiempo.
	 */
	private static void parseStepsOption(CommandLine line) throws ParseException {
		String t = line.getOptionValue("t", DEFAULT_TIME_VALUE.toString());
		try {
			_timeLimit = Integer.parseInt(t);
			assert (_timeLimit > 0);
		} catch (Exception e) {
			throw new ParseException("Invalid value for time limit: " + t);
		}
	}
	/**
	 * Parsea el modo de ejecución de la simulación. Si no se introduce nada lo
	 * inicializa a default ("batch").
	 * 
	 * @throws ParseException
	 *             Si no se parsea bien el modo.
	 *
	 */
	public static void parseModeOption(CommandLine line) throws ParseException {
		String m = line.getOptionValue("m", DEFAULT_SIM_MODE);
		if (m.equals(DEFAULT_SIM_MODE) && m.equals("gui")) {
			throw new ParseException("Invalid value for Sim Mode: " + m);
		} else {
			_simMode = m;
		}
	}
	/**
	 * This method run the simulator on all files that ends with .ini if the
	 * given path, and compares that output to the expected output. It assumes
	 * that for example "example.ini" the expected output is stored in
	 * "example.ini.eout". The simulator's output will be stored in
	 * "example.ini.out"
	 * 
	 * @throws IOException
	 */
	private static void test(String path) throws IOException {

		File dir = new File(path);

		if (!dir.exists()) {
			throw new FileNotFoundException(path);
		}

		File[] files = dir.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".ini");
			}
		});

		for (File file : files) {
			test(file.getAbsolutePath(), file.getAbsolutePath() + ".out",
					file.getAbsolutePath() + ".eout", DEFAULT_TIME_VALUE);
		}

	}
	/**
	 * Ejecuta la simulación partiendo de un fichero ".ini" y compara el fichero
	 * de salida ".out" con su homónimo pero terminado en ".eout". Muestra por
	 * controla si la salida esperada (eout) es igual a la salida de la
	 * simulación (out).
	 * 
	 * Presupone que el fichero a leer, el de salida generado y el de salida
	 * esperada están en el mismo repositorio.
	 * 
	 */
	private static void test(String inFile, String outFile, String expectedOutFile,
			int timeLimit) throws IOException {
		// Parámetros para el controlador que vamos a crear
		_outFile = outFile;
		_inFile = inFile;
		_timeLimit = timeLimit;

		try {
			// Ejecutamos la simulación
			startBatchMode();

			// Comparamos la salida generada y la esperada
			boolean equalOutput = (new Ini(_outFile)).equals(new Ini(expectedOutFile));

			// Mostramos el resultado obtenido
			System.out.println(
					"Result for: '" + _inFile.substring(_inFile.lastIndexOf('\\') + 1)
							+ "':\n" + "-> " + (equalOutput ? "OK!" : ("FAIL! :(")));

		} catch (SimulationFailedException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			// Si está aquí no es por startBatchMode, ha fallado la comparación
			// o en especial
			// _inFile.substring(_inFile.lastIndexOf('\\') + 1) (ver última
			// línea del try)

			System.out.println(
					"\nHa ocurrido un error comprobando la corrección de la simulación:\n");
			e.printStackTrace();
		}
	}
	/**
	 * Run the simulator in batch mode.
	 * @throws Exception 
	 */
	private static void startBatchMode() throws Exception {
		
		if(_inFile == null) {
			muestraMensajeError("An events file is missing");
		}
		else {
			try {
				Controller controller = new Controller(_inFile, _outFile, _timeLimit);
				controller.simulador().addSimulatorListener(new TrafficSimulator.Listener() {

				@Override
				public void update(UpdateEvent ue, String error) {
					switch (ue.getType()) {
						case ERROR :
							error(ue, error);
							break;
						case REGISTERED :
							registered(ue);
							break;
					}
				}
		
				public void reset(UpdateEvent ue) {
				}
				
				public void registered(UpdateEvent ue) {
					controller.leerDatosSimulacion();
					controller.run();
				}

				public void newEvent(UpdateEvent ue) {
				}

				public void error(UpdateEvent ue, String error) {
					muestraMensajeError(error);
				}

				public void advanced(UpdateEvent ue) {
				}
			});
			}catch(Exception e) {
				throw e;
			}
		}

	}
	/**
	 * Run the simulator in GUI mode.
	 * 
	 * @throws SimulationFailedException
	 *             If the simulation ended abruptly to notice the cause.
	 */
	private static void startGUIMode() throws SimulationFailedException {
		try {
			Controller controller = new Controller(_inFile, _timeLimit);
			SwingUtilities.invokeLater(() -> new SimWindow(controller));
		} catch (Exception e) {

			muestraMensajeError(e.getMessage());

			/*
			 * Construye una excepción en la que pone: Mira con estos parámetros
			 * no hemos podido ejecutar la simulación. El motivo ha sido este: y
			 * pinta el mensaje de la excepción.
			 */
		}
	}
	/**
	 * Parsea los argumentos introducidos y ejecuta la simulación partiendo de
	 * estos.
	 * 
	 * @throws IllegalArgumentException
	 *             Si no se parsean correctamente estos argumentos.
	 * @throws IOException
	 *             Si no se pueden leer los eventos en la ruta especificada.
	 */
	private static void start(String[] args) {
		try {
			parseArgs(args);
			if (_simMode.equals("batch")) {
				startBatchMode();
			} else if (_simMode.equals("gui")) {
				startGUIMode();
			}
		} catch (Exception e) {
			System.err.println(
					"Se ha producido un fallo durante el parseo de argumentos de entrada:\n"
							+ args);
		}
	}

	public static void main(String[] args)
			throws IOException, InvocationTargetException, InterruptedException {
		// example command lines:
		//
		// -i resources/examples/events/basic/ex1.ini
		// -i resources/examples/events/basic/ex1.ini -o ex1.out
		// -i resources/examples/events/basic/ex1.ini -t 20
		// -i resources/examples/events/basic/ex1.ini -o ex1.out -t 20
		// --help
		//

		// Call test in order to test the simulator on all examples in a
		// directory.
		//
		 test("src/main/resources/readStr/examples/basic/");
		 test("src/main/resources/readStr/examples/advanced/");

		// Call start to start the simulator from command line, etc.
		// start(args);
	}

	// Sólo para testeo
	public static int getTime() {
		return _timeLimit;
	}
	public static String getInFile() {
		return _inFile;
	}
	public static String getOutFile() {
		return _outFile;
	}

	private static void muestraMensajeError(String error) {
		
		JPanel fatherPane = new JPanel();
		JOptionPane.showMessageDialog(fatherPane,
				"Ha fallado la simulación con características:\n" + "-> tiempo: "
						+ _timeLimit + "\n" + "-> fichero de entrada: " + _inFile + "\n"
						+ "-> fichero de salida: " + _outFile + "\n" + "Motivo:\n"
						+ error,
				"Fallo en la simulación!", JOptionPane.ERROR_MESSAGE);
	}
}
