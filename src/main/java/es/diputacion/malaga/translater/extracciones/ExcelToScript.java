package es.diputacion.malaga.translater.extracciones;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import es.diputacion.malaga.translater.util.Utilidades;

public abstract class ExcelToScript {

	/**
	 * /** The PrintStream.
	 */
	private PrintStream txt;

	public ExcelToScript(String scriptPath, String scriptsName, String xlsFile) {
		initScriptFile(scriptPath, scriptsName);

		translate(xlsFile);

		closeScriptFile();
	}

	/**
	 * @param scriptPath
	 * @param strFicheroScript
	 */
	private void initScriptFile(String scriptPath, String strFicheroScript) {

		try {

			File f = new File(scriptPath);
			f.mkdirs();
			f = null;

			FileOutputStream ftxt = new FileOutputStream(new File(scriptPath
					+ File.separator + strFicheroScript), true);
			txt = new PrintStream(ftxt);

		} catch (Exception e) {
			Utilidades.log("Error -->" + e);
			txt.close();
			txt = null;
		}
	}

	/**
	 * 
	 */
	private void closeScriptFile() {
		printlnTxt("");

		txt.close();
		txt = null;
	}

	/**
	 * @param strTxt
	 */
	protected void printlnTxt(String strTxt) {
		txt.println(strTxt);
	}

	/**
	 * @return
	 */
	protected abstract String getInsertDeclaration();

	/**
	 * @return
	 */
	protected abstract ArrayList<Integer> getTypes();

	/**
	 * @return
	 */
	protected abstract void translate(String xlsFile);
}
