package com.stackroute.datamunger.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.stackroute.datamunger.query.DataTypeDefinitions;
import com.stackroute.datamunger.query.Header;

public class CsvQueryProcessor extends QueryProcessingEngine {
	File fileName;

	/*
	 * Parameterized constructor to initialize filename. As you are trying to
	 * perform file reading, hence you need to be ready to handle the IO Exceptions.
	 */

	public CsvQueryProcessor(String fileName) throws FileNotFoundException {
		super();
		this.fileName = new File(fileName);
		FileInputStream f=new FileInputStream(fileName);

	}

	/*
	 * Implementation of getHeader() method. We will have to extract the headers
	 * from the first line of the file.
	 */

	@Override
	public Header getHeader() throws IOException {

		BufferedReader bfr = new BufferedReader(new FileReader(fileName));
		String text = bfr.readLine();
		String[] ar = text.split(",");
		if (ar.length != -1) {
			Header hd = new Header(ar);
			return hd;
		} else {
			return null;
		}
	}

	/**
	 * This method will be used in the upcoming assignments
	 */
	@Override
	public void getDataRow() {

	}

	/*
	 * Implementation of getColumnType() method. To find out the data types, we will
	 * read the first line from the file and extract the field values from it. In
	 * the previous assignment, we have tried to convert a specific field value to
	 * Integer or Double. However, in this assignment, we are going to use Regular
	 * Expression to find the appropriate data type of a field. Integers: should
	 * contain only digits without decimal point Double: should contain digits as
	 * well as decimal point Date: Dates can be written in many formats in the CSV
	 * file. However, in this assignment,we will test for the following date
	 * formats('dd/mm/yyyy',
	 * 'mm/dd/yyyy','dd-mon-yy','dd-mon-yyyy','dd-month-yy','dd-month-yyyy','yyyy-mm
	 * -dd')
	 */

	@Override
	public DataTypeDefinitions getColumnType() throws IOException {
		try {
			int i = 0;
			BufferedReader bf = new BufferedReader(new FileReader(fileName));

			String text = bf.readLine();
			int len = text.split(",").length;
			text = bf.readLine();
			String[] ar = text.split(",");
			String[] arr = new String[len];
			for (String s : ar) {
				try {
					int x = Integer.parseInt(s);
					arr[i] = "java.lang.Integer";
				} catch (Exception e1) {
					try {
						double x = Double.parseDouble(s);
						arr[i] = "java.lang.Double";
					} catch (Exception e3) {
						try {

						if (s.matches("^[0-9]{2}/[0-9]{2}/[0-9]{4}$") || s.matches("^[0-9]{2}-[a-z]{3}-[0-9]{2}$")
								|| s.matches("^[0-9]{2}-[a-z]{3}-[0-9]{4}$")
								|| s.matches("^[0-9]{2}-[a-z]{3,9}-[0-9]{2}$")
								|| s.matches("^[0-9]{2}-[a-z]{3,9}-[0-9]{4}$")
								|| s.matches("^[0-9]{4}-[0-9]{2}-[0-9]{2}$")) {

							arr[i] = "java.util.Date";
						}
						else {

						arr[i] = "java.lang.String";
						}
						}
						catch(Exception e) {
							arr[i]="java.lang.String";
						}

					}
				}

				finally {
					i++;
				}
			}
			for (i = 0; i < arr.length; i++) {
				if (arr[i] == null)
					arr[i] = "java.lang.Object";
			}

			DataTypeDefinitions df = new DataTypeDefinitions(arr);
			bf.close();

			return df;

		}

		catch (Exception e) {
			DataTypeDefinitions df = new DataTypeDefinitions(new String[0]);
			return df;

		}

	}
}