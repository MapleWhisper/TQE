package com.tqe.excelreader;



import java.io.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/*
 * ����Excel�����࣬�������Ƚ�excel��Ԫ�����ó��ı������õ�Ԫ���ʽ�������֡����ı�����
 * ����ȡ�������ݿ��ܻᷢ����ʽ��ת�䡣
 * Ŀǰ��֧��xls�ļ�����֧��xlsx��
 * Ŀǰ���ڿ��ַ�����Լ��������excelȡ������ֵ��""ʱ��ִ�������ݿ��������ʱ����Ҫ�Ӹ��жϣ����=="",��ƴ��sql���Դ�����ֶ�=''
 */
public class ExcelUtils {
	/*
	 * ��ȡexcel File
	 * return java.io.file
	 */
	public static File getExcelFile(String excelDir){

		File excelFile = new File(excelDir);

		if(!excelFile.exists()){
			throw new RuntimeException(excelDir+" �ļ������ڣ�");
		}
		if(!excelFile.getName().endsWith(".xls")){
			throw new RuntimeException(excelDir+" �ļ����Ͳ���ȷ��ֻ����.xls��β���ļ���");
		}
		return excelFile;
	}

	/*
	 * ��ȡָ����ǩҳ���Ƶ�����
	 */
	public static List<Map<String,String>> getExcelRecords(String excelDir,String sheetName){
		if(sheetName==null){
			throw new RuntimeException("��ǩҳ���Ʋ���Ϊ�գ�");
		}
		return getExcelRecords(excelDir, sheetName, -1,0);
	}
	/*
	 * ��ȡָ����ǩҳ����������
	 */
	public static List<Map<String,String>> getExcelRecords(String excelDir,int sheetIndex,int titleIndex){
		if(sheetIndex<0){
			throw new RuntimeException("ָ����ǩҳ�������Ϸ���������Ǹ�����");
		}
		return getExcelRecords(excelDir, null, sheetIndex,titleIndex);
	}
	/*
	 * ��ȡָ��excel�ļ���ָ����ǩҳ����ǩҳ���ơ���ǩҳ�����������ݣ�����ֵ��ʽ��List<List<Object>>
	 * ���excel_file��ʽ��bpm\\test1.xls,bsp\\test2.xls
	 */
	private static List<Map<String,String>> getExcelRecords(String excelDir,String sheetName,int sheetIndex,int titleIndex){

		File excelFile = getExcelFile(excelDir);
		List<Map<String,String>> recordList = new ArrayList<Map<String,String>>();
		HSSFWorkbook workbook=null;

		try {
			workbook = new HSSFWorkbook(new FileInputStream(excelFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
                if (workbook != null) {
                    workbook.close();
                }
            } catch (IOException e) {
				e.printStackTrace();
			}
		}

        if(workbook == null){
            throw  new IllegalArgumentException("δ��������Excel�ļ���");
        }

		//��ȡָ����ǩҳ   
		HSSFSheet sheet = null;
		if(sheetName==null){
			sheet = workbook.getSheetAt(sheetIndex);
			if(sheet==null){
				throw new RuntimeException(sheetIndex+" ��ǩҳ�����ڣ�");
			}
		}else{		
			sheet = workbook.getSheet(sheetName);
			if(sheet==null){
				throw new RuntimeException(sheetName+" ��ǩҳ�����ڣ�");
			}
		}


		//������
		int rows = sheet.getPhysicalNumberOfRows();
		if (rows < 2) {
			throw new RuntimeException(excelFile.getName()+" ����Ϊ�գ������±༭��");
		}

		//��Ϊ�յĵ�Ԫ����  ����һ���Ǳ�����
		HSSFRow keyRow = sheet.getRow(titleIndex);
		int cellNumber = keyRow.getPhysicalNumberOfCells();

		//ѭ����������һ���Ǳ��⣬���Դ� 1 ��ʼ������0��ʾ��һ�У�1��ʾ�ڶ��У�
		for (int r = titleIndex+1; r < rows; r++) {
			Map<String,String> record = new LinkedHashMap<String,String>();//ʹ��HashMap�Ļ������ȥ��˳����ȡ������˳���ǲ�һ�µģ��˴���Ҫʹ��LinkedHashMap
			HSSFRow row = sheet.getRow(r);
			String value = "";
			for (int c = 0; c < cellNumber; c++) //��cellNumber��
			{
				value = null;
				HSSFCell cell = row.getCell(c);
				if(cell!=null){
					cell.setCellType(Cell.CELL_TYPE_STRING);
					value  =  cell.getStringCellValue();
				}
				
				record.put(keyRow.getCell(c).getStringCellValue(),value);
			}
			recordList.add(record);
		}	
		try {
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return recordList;
	}
	/*
	 * ��ȡexcel��sheet����
	 */
	public static int getNumberOfSheets(String excelDir){
		File excelFile = getExcelFile(excelDir);
		HSSFWorkbook workbook=null;
		int numberOfSheets = 0;
		try {
			workbook = new HSSFWorkbook(new FileInputStream(excelFile));
			numberOfSheets = workbook.getNumberOfSheets();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				workbook.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return numberOfSheets;
	}

	/*
	 * ����sheetIndex��ȡsheetName
	 */
	public static String getSheetNameBySheetIndex(String excelDir,int sheetIndex){
		File excelFile = getExcelFile(excelDir);
		HSSFWorkbook workbook=null;
		String sheetName = null;
		try {
			workbook = new HSSFWorkbook(new FileInputStream(excelFile));
			sheetName = workbook.getSheetName(sheetIndex);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				workbook.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sheetName;
	}

	/*
	 * ��ȡָ��ѡ���������������
	 */
	public static int getRowsOfSheet(String excelDir,int sheetIndex){
		File excelFile = getExcelFile(excelDir);
		HSSFWorkbook workbook=null;
		int rowsOfSheet = 0;
		try {
			workbook = new HSSFWorkbook(new FileInputStream(excelFile));
			rowsOfSheet = workbook.getSheetAt(sheetIndex).getPhysicalNumberOfRows();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				workbook.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return rowsOfSheet;
	}
	/*
	 * ��ȡָ��ѡ������ƣ�������
	 */
	public static int getRowsOfSheet(String excelDir,String sheetName){
		File excelFile = getExcelFile(excelDir);
		HSSFWorkbook workbook=null;
		int rowsOfSheet = 0;
		try {
			workbook = new HSSFWorkbook(new FileInputStream(excelFile));
			rowsOfSheet = workbook.getSheet(sheetName).getPhysicalNumberOfRows();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				workbook.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return rowsOfSheet;
	}
	/*
	 * ��ȡָ��sheet��ָ��row��ָ��colum��Ӧ��cell��value
	 * return:List[title,value],��һ��Ԫ����title���ڶ���Ԫ����value
	 * row/cell���Ǵ�0��ʼ����
	 */
	public static List<String> getCell(String excelDir,int sheetIndex,int rowIndex,int cellIndex){

		File excelFile = getExcelFile(excelDir);
		List<String> keyValueList = new ArrayList<String>();
		HSSFWorkbook workbook=null;
		String key = null;
		String value = null;
		try {
			workbook = new HSSFWorkbook(new FileInputStream(excelFile));
			key = workbook.getSheetAt(sheetIndex).getRow(0).getCell(cellIndex).getStringCellValue();//��һ�У�row 0���Ǳ�����
			HSSFCell cell = workbook.getSheetAt(sheetIndex).getRow(rowIndex).getCell(cellIndex);
			if(cell==null){
				value = null;
			}else{
				if(cell.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){//�������������
					NumberFormat nf = NumberFormat.getInstance(); 
					nf.setGroupingUsed(false); 
					value = nf.format( new Double(cell.getNumericCellValue()));
				}else if(cell.getCellType()==HSSFCell.CELL_TYPE_STRING){	//������ı�����	
					value = cell.getStringCellValue();
				}else{//����ȫ����Ϊnull
					value=null;
				}
			}
			keyValueList.add(key);
			keyValueList.add(value);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				workbook.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return keyValueList;
	}
	
	/*
	 * ��ȡָ��sheet��ָ��row����Ӧ��row ����
	 * return:List[title,value],��һ��Ԫ����title���ڶ���Ԫ����value
	 * row/cell���Ǵ�0��ʼ����
	 */
	public static List<String> getRow(String excelDir,int sheetIndex,int rowIndex){

		File excelFile = getExcelFile(excelDir);
		List<String> rowList = new ArrayList<String>();
		Workbook workbook=null;
		try {
			InputStream is = new FileInputStream(new File(excelDir));
			workbook = WorkbookFactory.create(is);

			Row row = workbook.getSheetAt(sheetIndex).getRow(rowIndex);//��һ�У�row 0���Ǳ�����
			
			if(row != null){
				for( int i= 0;i<row.getPhysicalNumberOfCells();i++){
					rowList.add(row.getCell(i).getStringCellValue());
				}
				
			}
				
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} finally{
			try {
                if(workbook!=null){
                    workbook.close();
                }
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return rowList;
	}
}
