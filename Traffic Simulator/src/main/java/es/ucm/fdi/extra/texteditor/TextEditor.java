package es.ucm.fdi.extra.texteditor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.border.Border;

import es.ucm.fdi.control.SimulatorAction;

public class TextEditor extends JPanel {

	private static final long serialVersionUID = 1L;

	private JFileChooser fc;
	private JTextArea textArea;
	private String name;
	private boolean editable;

	public TextEditor(String n, boolean e, JFileChooser f) {
		super(new BorderLayout());
		name = n;
		editable = e;
		fc = f;
		initGUI();
	}

	private void initGUI() {
		this.setPreferredSize(new Dimension(300, 200));

		textArea = new JTextArea("");
		textArea.setEnabled(editable);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);

		Border b = BorderFactory.createLineBorder(Color.black, 2);
		textArea.setBorder(BorderFactory.createTitledBorder(b, name));

		if (editable) {
			JPopupMenu optionsPopup = new JPopupMenu();

			SimulatorAction loadOption = new SimulatorAction("Load", "Cargar eventos",
					KeyEvent.VK_L, () -> loadFile());

			SimulatorAction saveOption = new SimulatorAction("Save", "Guardar eventos",
					KeyEvent.VK_S, () -> saveFile());

			SimulatorAction clearOption = new SimulatorAction("Clear", "Vaciar eventos",
					KeyEvent.VK_B, () -> textArea.setText(""));

			optionsPopup.add(loadOption);
			optionsPopup.add(saveOption);
			optionsPopup.add(clearOption);

			textArea.addMouseListener(new MouseListener() {

				@Override
				public void mousePressed(MouseEvent e) {
					showPopup(e);
				}

				@Override
				public void mouseReleased(MouseEvent e) {
					showPopup(e);
				}

				private void showPopup(MouseEvent e) {
					if (e.isPopupTrigger() && optionsPopup.isEnabled()) {
						optionsPopup.show(e.getComponent(), e.getX(), e.getY());
					}
				}

				@Override
				public void mouseExited(MouseEvent e) {
				}

				@Override
				public void mouseEntered(MouseEvent e) {
				}

				@Override
				public void mouseClicked(MouseEvent e) {
				}
			});

		}

		JScrollPane scroll = new JScrollPane(textArea);
		this.add(scroll);
	}

	/**
	 * Abre un selector de fichero y guarda el contenido del TextEditor en la
	 * carpeta seleccionada.
	 * 
	 * @param text
	 *            El editor de texto del que queremos guardar la información.
	 */
	public void saveFile() {
		int returnVal = fc.showSaveDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			writeFile(file, textArea.getText());
		}
	}
	/**
	 * Abre un selector de fichero y carga en el TextEditor que pasan como
	 * parámetro el fichero seleccionado.
	 * 
	 * @param text
	 *            El editor de texto que va a cargar la información.
	 * @throws NoSuchElementException
	 *             Si no es capaz de abrir el archivo seleccionado. Esto sucede
	 *             por ejemplo en los archivos de formato .pdf.
	 */
	public void loadFile() {
		int returnVal = fc.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			String s = readFile(file);
			textArea.setText(s);
		}
	}
	/**
	 * Lee un fichero y devulve el string generado en la lectura del archivo
	 * completo.
	 * 
	 * @param file
	 *            El fichero a leer.
	 */
	public static String readFile(File file) {
		String s = "";
		try {
			s = new Scanner(file).useDelimiter("\\A").next();
		} catch (FileNotFoundException e) {
			JPanel fatherPane = new JPanel();
			JOptionPane.showMessageDialog(fatherPane, "No se ha podido cargar el fichero de entrada\n" + 
					"Puedes introducir otro fichero desde el selector del simulador",
								"Fichero introducido no válido",
								JOptionPane.ERROR_MESSAGE);

					}

		return s;
	}
	/**
	 * Escribe el contenido (String) que le pasemos en el fichero introducido.
	 * 
	 * @param file
	 *            Fichero en el que escribir.
	 * @param content
	 *            Contenido a escribir.
	 */
	public static void writeFile(File file, String content) {
		try {
			PrintWriter pw = new PrintWriter(file);
			pw.print(content);
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Accedente para visualizar el contenido del TextEditor.
	 */
	public String getText() {
		return textArea.getText();
	}
	/**
	 * Permite modificar el contenido del TextEditor.
	 */
	public void setText(String s) {
		textArea.setText(s);
	}

	/**
	 * @see http://www.codejava.net/java-se/swing/redirect-standard-output-streams-to-jtextarea
	 */
	public OutputStream flujoEscritura() {
		return new OutputStream() {
			public void write(int arg) throws IOException {
				textArea.append(String.valueOf((char) arg));
				textArea.setCaretPosition(textArea.getDocument().getLength());
			}
		};
	}
	/** Devuelve un flujo de lectura al contenido del editor de texto. */
	public InputStream flujoLectura() {
		return new ByteArrayInputStream(textArea.getText().getBytes());
	}
	
	public void setEnabled(boolean enabled) {
		textArea.setEnabled(enabled);
	}
}
