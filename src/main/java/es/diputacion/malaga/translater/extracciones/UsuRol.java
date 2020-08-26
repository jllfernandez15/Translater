package es.diputacion.malaga.translater.extracciones;

import java.util.ArrayList;
import java.util.Hashtable;

import jxl.Workbook;
import es.diputacion.malaga.translater.util.ExcelUtil;
import es.diputacion.malaga.translater.util.Utilidades;

public class UsuRol extends ExcelToScript {

	/**
	 */
	public UsuRol(String scriptPath, String scriptName, String xlsFile) {
		super(scriptPath, scriptName, xlsFile);
	}

	/**
	 * @return
	 */
	protected String getInsertDeclaration() {
		return "INSERT INTO USU_ROL (USU_ID, EJE, ORG, ROL_ID, APP_ID, TOD, ENT_NUM, PSD_NUM, FCH_PET, FEC_FIN, USU_SQL_CRE, FEC_CRE, USU_SQL_MOD, FEC_MOD) VALUES(";
	}

	/**
	 * @return 0 String / 1 int / 2 Date
	 */
	protected ArrayList<Integer> getTypes() {
		ArrayList<Integer> types = new ArrayList<Integer>();
		types.add(new Integer(0));// Its CHAR/VARCHAR
		types.add(new Integer(0));// Its CHAR/VARCHAR
		types.add(new Integer(0));// Its CHAR/VARCHAR
		types.add(new Integer(0));// Its CHAR/VARCHAR
		types.add(new Integer(0));// Its CHAR/VARCHAR

		types.add(new Integer(0));// Its CHAR/VARCHAR
		types.add(new Integer(0));// Its CHAR/VARCHAR
		types.add(new Integer(0));// Its CHAR/VARCHAR
		types.add(new Integer(0));// Its CHAR/VARCHAR
		types.add(new Integer(0));// Its CHAR/VARCHAR

		types.add(new Integer(0));// Its CHAR/VARCHAR
		types.add(new Integer(0));// Its CHAR/VARCHAR
		types.add(new Integer(0));// Its CHAR/VARCHAR
		types.add(new Integer(0));// Its CHAR/VARCHAR

		return types;
	}

	/**
	 * @param xlsFile
	 */
	protected void translate(String xlsFile) {

		// System.out.println(" Fichero Excel -->" + xlsFile);
		Workbook workbook = ExcelUtil.getBook(xlsFile);

		ArrayList<Object[]> arr = ExcelUtil.parserSheet(workbook, 0);

		Hashtable<String, String> keys = Utilidades.getKeys();
		StringBuffer insert = null;

		boolean filter = true;
		String opCode = "";

		for (int posRows = 1; posRows < arr.size(); posRows++) {
			insert = new StringBuffer(getInsertDeclaration());
			filter = true;

			Object[] data = (Object[]) arr.get(posRows);
			for (int posColumns = 0; posColumns < data.length; posColumns++) {
				if (posColumns == 15) {
					break;
				}

				Object value = data[posColumns];

				if (posColumns == 0) {
					if ("".equals(value)) {
						value = opCode;
					} else {
						opCode = (String) value;
					}
				}

				if (!Utilidades.hasFiltered(Utilidades.MASK[posColumns], value)) {
					filter = false;
					break;
				}

				if (posColumns == 0) {
					// First column not to Script
					continue;
				}

				if (posColumns == 3) {
					value = Utilidades.getSinonimous(data, posColumns, keys);
				}

				if ("NULL".equals(value.toString())) {
					insert.append("NULL");
				} else {
					switch (getTypes().get(posColumns)) {
					case 0: // String
						insert.append("'".concat(value.toString()).concat("'"));
						break;
					case 1: // int
						insert.append(value);
						break;
					case 2: // Date
						insert.append("'".concat(value.toString()).concat("'"));
						break;

					default:
						insert.append("'".concat(value.toString()).concat("'"));
					}
				}

				insert.append(",");
			} // for each column

			if (!filter)
				continue;

			printlnTxt(insert.substring(0, insert.length() - 2).concat(");"));

		}// for each row
	}

}
