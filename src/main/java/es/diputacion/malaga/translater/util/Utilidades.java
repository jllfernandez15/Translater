package es.diputacion.malaga.translater.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import jxl.Workbook;

public class Utilidades {

	/** The claves file. */
	private final static String CLAVES_FILE = "." + File.separator
			+ "Plantillas" + File.separator + "claves.xls";

	/**
	 * 
	 */
	public static String[] MASK;

	/**
	 * @return
	 */
	public static Hashtable<String, String> getKeys() {
		Hashtable<String, String> result = new Hashtable<String, String>();

		Workbook workbook = ExcelUtil.getBook(CLAVES_FILE);

		ArrayList<Object[]> arr = ExcelUtil.parserSheet(workbook, 0);

		for (int posRows = 1; posRows < arr.size() - 1; posRows++) {
			Object[] data = (Object[]) arr.get(posRows);
			result.put((String) data[0], (String) data[1]);
		}// for each row

		return result;
	}

	/**
	 * @param value
	 * @param column
	 * @param keys
	 * @return
	 */
	public static Object getSinonimous(Object[] value, int column,
			Hashtable<String, String> keys) {
		Object result = value[column];
		if ("raiz".equals((String) value[column])) {
			String k = keys.get((String) value[7]);
			if (null != k) {
				result = k;
			}
		}
		return result;
	}

	/**
	 * @param mask
	 * @param value
	 * @return
	 */
	public static boolean hasFiltered(String mask, Object value) {
		boolean result = Boolean.TRUE;
		if (isNotEmpty(mask)) {
			result = mask.equals(value);
		}
		return result;
	}

	/**
	 * @param value
	 * @return
	 */
	public static boolean isNotEmpty(String value) {
		return null != value && !("".equals(value));
	}

	/**
	 * @param value
	 * @return
	 */
	public static boolean isEmpty(String value) {
		return !isNotEmpty(value);
	}

	/**
	 * @param data
	 * @return
	 */
	public static boolean notAccData(Object[] data) {
		return isEmpty((String)data[18]);
	}

	/**
	 * @param s
	 */
	public static void log(String s) {
		System.out.println(s);
	}

	/**
	 * @return
	 */
	public static String[] getCodePeticiones(String xlsFile) {

		String[] result = null;
		Vector<String> vct = new Vector<String>();

		Hashtable<String, String> hash = new Hashtable<String, String>();

		Workbook workbook = ExcelUtil.getBook(xlsFile);

		ArrayList<Object[]> arr = ExcelUtil.parserSheet(workbook, 0);

		String codPet, defaultCode = "";
		for (int posRows = 1; posRows < arr.size() - 1; posRows++) {
			Object[] data = (Object[]) arr.get(posRows);
			codPet = (String) data[0];

			if ("".equals(codPet)) {
				codPet = defaultCode;
			} else {
				defaultCode = codPet;
			}

			hash.put(codPet, codPet);

		}// for each row

		Enumeration<String> enume = hash.elements();
		while (enume.hasMoreElements()) {
			vct.add(enume.nextElement());
		}

		result = new String[vct.size()];

		int posi = 0;
		for (int pos = vct.size() - 1; pos >= 0; pos--) {
			result[posi] = vct.get(pos);
			posi++;
		}
		return sort(result);
	}

	/**
	 * @param relation
	 * @return
	 */
	private static String[] sort(String[] relation) {
		int size = relation.length;
		String[] result = new String[size];
		int[] numns = new int[size];
		int pos = 0;
		for (String s : relation) {
			numns[pos] = Integer.valueOf(s).intValue();
			pos++;
		}

		int temp;

		for (int posX = 0; posX < size - 1; posX++) {
			for (int posY = posX + 1; posY < size; posY++) {
				if (numns[posX] > (numns[posY])) {
					temp = numns[posX];
					numns[posX] = numns[posY];
					numns[posY] = temp;
				}
			}
		}

		pos = 0;
		for (int i : numns) {
			result[pos] = String.valueOf(i);
			pos++;
		}
		return result;
	}

}
