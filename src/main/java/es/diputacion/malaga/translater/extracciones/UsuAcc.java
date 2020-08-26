package es.diputacion.malaga.translater.extracciones;

import java.util.ArrayList;

import jxl.Workbook;
import es.diputacion.malaga.translater.util.ExcelUtil;
import es.diputacion.malaga.translater.util.Utilidades;

public class UsuAcc extends ExcelToScript {

	public UsuAcc(String scriptPath, String scriptsName, String xlsFile) {
		super(scriptPath, scriptsName, xlsFile);
	}

	/**
	 * @return
	 */
	protected String getInsertDeclaration() {
		return "INSERT INTO USU_ACC (USU_ID, ACC_ID, PSD_NUM, FEC_FIN, USU_SQL_CRE, FEC_CRE, USU_SQL_MOD, FEC_MOD) VALUES(";
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

		return types;
	}

	/**
	 * @param xlsFile
	 */
	protected void translate(String xlsFile) {

		Workbook workbook = ExcelUtil.getBook(xlsFile);

		ArrayList<Object[]> arr = ExcelUtil.parserSheet(workbook, 0);

		StringBuffer insert = null;

		for (int posRows = 1; posRows < arr.size(); posRows++) {
			Object[] data = (Object[]) arr.get(posRows);

			if (Utilidades.notAccData(data)) {
				break;
			}
			insert = new StringBuffer(getInsertDeclaration());

			for (int posColumns = 18; posColumns < data.length; posColumns++) {
				Object value = data[posColumns];

				if ("NULL".equals(value.toString())) {
					insert.append("NULL");
				} else {
					switch (getTypes().get(posColumns - 18)) {
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

			printlnTxt(insert.substring(0, insert.length() - 2).concat(");"));

		}// for each row
	}

}
