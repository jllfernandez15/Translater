package es.diputacion.malaga.translater.util;

import java.io.File;
import java.util.ArrayList;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class ExcelUtil {

	/**
	 * @param book
	 * @return
	 */
	public static Workbook getBook(String book) {
		Workbook workbook = null;

		try {
			File xlsFile = new File(book);
			workbook = Workbook.getWorkbook(xlsFile);
			// Sheet[] arr = workbook.getSheets();

		} catch (Exception e) {
			System.out.println("Error --->" + e.toString());
		}

		return workbook;
	}

	public static Workbook getBookFromJar(String book) {
		Workbook workbook = null;

		try {
			String s = "";
			workbook = Workbook.getWorkbook(s.getClass().getResourceAsStream(
					book));

		} catch (Exception e) {
			System.out.println("Error --->" + e.toString());
		}

		return workbook;
	}

	/**
	 * @param workbook
	 * @param hoja
	 * @return
	 */
	public static ArrayList<Object[]> parserSheet(Workbook workbook, int hoja) {

		ArrayList<Object[]> result = new ArrayList<Object[]>();

		try {
			Sheet[] arr = workbook.getSheets();
			Sheet s = arr[hoja];

			int columns = s.getColumns();
			int rows = s.getRows();

			Object[] objs = null;

			for (int posRows = 0; posRows < rows; posRows++) {
				objs = new Object[columns];

				for (int posColumns = 0; posColumns < columns; posColumns++) {
					// Number number = new Number(0, pos, posRows);
					Cell cell = s.getCell(posColumns, posRows);
					Object data = cell.getContents();
					// System.out.println("strValue en Fila -->" + posRows
					// + " y columna " + posColumns + " : "
					// + data.toString());

					objs[posColumns] = data;
				}

				result.add(objs);
			}

		} catch (Exception e) {
			System.out.println("Error --->" + e.toString());
		}

		return result;
	}

}
