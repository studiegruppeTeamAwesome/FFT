package logic;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CSVWriter {
	private BufferedWriter writer;
	private String separator;

	public CSVWriter(String fileName, String separator) throws IOException {
		writer = new BufferedWriter(new FileWriter(fileName + ".csv"));
		this.separator = separator;
	}

	public void writeArray(Object[][] rows) throws IOException {
		for (int i = 0; i < rows.length; i++) {
			writeLine(rows[i]);
		}
	}

	public void writeLine(Object[] line) throws IOException {
		for (int i = 0; i < line.length; i++) {
			if (i != 0)
				writer.write(separator);
			writer.write(line[i].toString());
		}
		writer.newLine();
	}

	public void flush() throws IOException {
		writer.flush();
	}

	public void close() throws IOException {
		writer.close();
	}

}
