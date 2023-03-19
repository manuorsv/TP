package es.ucm.fdi.ini;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

public class Demo {

	private final static String WRITE_DIRECTORY = "src/main/resources/writeStr/";
	private final static String READ_DIRECTORY = "src/main/resources/readStr/";
	private final static String FILE_NAME_WRITE = "output.ini";
	private final static String FILE_NAME_READ = "00_helloWorld.ini";

	public static void write() throws Exception {
		System.out.println("-> write test");

		Ini ini = new Ini();
		IniSection sec;

		sec = new IniSection("report_1");
		sec.addKeyComment("", "This is the header comment");
		sec.setValue("name", "Bart");
		sec.setValue("age", 14);
		sec.addKeyComment("age", "A comment just before age");
		sec.addKeyComment("age", "Yet another comment just before age");

		ini.addsection(sec);

		sec = new IniSection("report_2");
		sec.setValue("name", "Home");
		sec.setValue("age", 37);
		sec.setValue("address", "742 Evergreen Terrace");
		sec.addKeyComment("age", "A comment just before age");
		sec.addKeyComment("name", "A comment before name");

		ini.addsection(sec);

		File file = new File(WRITE_DIRECTORY + FILE_NAME_WRITE);
		OutputStream s = new FileOutputStream(file);
		ini.store(s);
		s.close();
		System.out.println("Check out " + FILE_NAME_WRITE);
		System.out.println();
	}

	public static void read() throws Exception {
		System.out.println("-> read test");

		File file = new File(READ_DIRECTORY + FILE_NAME_READ);
		InputStream s = new FileInputStream(file);
		Ini ini = new Ini(s);

		// write it ...
		System.out.println();
		System.out.println("This is the input ini file:");
		System.out.println();
		System.out.println(ini);

		// ... or traverse its sections and keys/values
		System.out.println();
		System.out.println("This is the how we traverse all sections/keys:");
		System.out.println();
		for (IniSection sec : ini.getSections()) {
			String tag = sec.getTag();
			Map<String, String> map = sec.getKeysMap();
			for (String key : map.keySet()) {
				System.out.println(tag + "/" + key + " = " + map.get(key));
			}
			System.out.println();
		}

		s.close();

		System.out.println();
	}

	public static void compare() throws Exception {
		System.out.println("-> compare test");

		Ini[] ini = new Ini[5];

		for (int i = 0; i < ini.length; i++) {
			ini[i] = new Ini(new FileInputStream(
					new File("resources/examples/ini/input" + i + ".ini")));
		}

		for (int i = 0; i < ini.length; i++) {
			for (int j = i + 1; j < ini.length; j++)
				System.out.println("are input" + i + ".ini and input" + j + ".ini equal? "
						+ ini[i].equals(ini[j]));
		}
		System.out.println();
	}

	public static void main(String[] args) throws Exception {
		read();
		// compare();
		// write();
	}
}
