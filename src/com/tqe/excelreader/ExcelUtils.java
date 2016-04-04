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
 * 操作Excel工具类，建议首先将excel单元格设置成文本（设置单元格格式——数字——文本），
 * 否则取出的数据可能会发生格式的转变。
 * 目前仅支持xls文件，不支持xlsx。
 * 目前对于空字符串的约定：当从excel取出来的值是""时，执行往数据库插入数据时，需要加个判断，如果=="",则拼接sql可以处理成字段=''
 */
public class ExcelUtils {
	/*
	 * 获取excel File
	 * return java.io.file
	 */
	public static File getExcelFile(String excelDir){

		File excelFile = new File(excelDir);

		if(!excelFile.exists()){
			throw new RuntimeException(excelDir+" 文件不存在！");
		}
		if(!excelFile.getName().endsWith(".xls")){
			throw new RuntimeException(excelDir+" 文件类型不正确，只允许.xls结尾的文件！");
		}
		return excelFile;
	}

	/*
	 * 获取指定标签页名称的数据
	 */
	public static List<Map<String,String>> getExcelRecords(String excelDir,String sheetName){
		if(sheetName==null){
			throw new RuntimeException("标签页名称不能为空！");
		}
		return getExcelRecords(excelDir, sheetName, -1,0);
	}
	/*
	 * 获取指定标签页索引的数据
	 */
	public static List<Map<String,String>> getExcelRecords(String excelDir,int sheetIndex,int titleIndex){
		if(sheetIndex<0){
			throw new RuntimeException("指定标签页索引不合法，请输入非负数！");
		}
		return getExcelRecords(excelDir, null, sheetIndex,titleIndex);
	}
	/*
	 * 获取指定excel文件、指定标签页（标签页名称、标签页索引）的内容，返回值格式：List<List<Object>>
	 * 入参excel_file格式：bpm\\test1.xls,bsp\\test2.xls
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
            throw  new IllegalArgumentException("未能正常打开Excel文件！");
        }

		//获取指定标签页   
		HSSFSheet sheet = null;
		if(sheetName==null){
			sheet = workbook.getSheetAt(sheetIndex);
			if(sheet==null){
				throw new RuntimeException(sheetIndex+" 标签页不存在！");
			}
		}else{		
			sheet = workbook.getSheet(sheetName);
			if(sheet==null){
				throw new RuntimeException(sheetName+" 标签页不存在！");
			}
		}


		//总行数
		int rows = sheet.getPhysicalNumberOfRows();
		if (rows < 2) {
			throw new RuntimeException(excelFile.getName()+" 内容为空，请重新编辑！");
		}

		//不为空的单元格数  ，第一行是标题列
		HSSFRow keyRow = sheet.getRow(titleIndex);
		int cellNumber = keyRow.getPhysicalNumberOfCells();

		//循环遍历，第一行是标题，所以从 1 开始遍历（0表示第一行，1表示第二行）
		for (int r = titleIndex+1; r < rows; r++) {
			Map<String,String> record = new LinkedHashMap<String,String>();//使用HashMap的话，存进去的顺序与取出来的顺序是不一致的，此处需要使用LinkedHashMap
			HSSFRow row = sheet.getRow(r);
			String value = "";
			for (int c = 0; c < cellNumber; c++) //共cellNumber列
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
	 * 获取excel的sheet个数
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
	 * 根据sheetIndex获取sheetName
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
	 * 获取指定选项卡（索引）的行数
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
	 * 获取指定选项卡（名称）的行数
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
	 * 获取指定sheet、指定row、指定colum对应的cell的value
	 * return:List[title,value],第一个元素是title，第二个元素是value
	 * row/cell都是从0开始计算
	 */
	public static List<String> getCell(String excelDir,int sheetIndex,int rowIndex,int cellIndex){

		File excelFile = getExcelFile(excelDir);
		List<String> keyValueList = new ArrayList<String>();
		HSSFWorkbook workbook=null;
		String key = null;
		String value = null;
		try {
			workbook = new HSSFWorkbook(new FileInputStream(excelFile));
			key = workbook.getSheetAt(sheetIndex).getRow(0).getCell(cellIndex).getStringCellValue();//第一行（row 0）是标题列
			HSSFCell cell = workbook.getSheetAt(sheetIndex).getRow(rowIndex).getCell(cellIndex);
			if(cell==null){
				value = null;
			}else{
				if(cell.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){//如果是数字类型
					NumberFormat nf = NumberFormat.getInstance(); 
					nf.setGroupingUsed(false); 
					value = nf.format( new Double(cell.getNumericCellValue()));
				}else if(cell.getCellType()==HSSFCell.CELL_TYPE_STRING){	//如果是文本类型	
					value = cell.getStringCellValue();
				}else{//否则全部视为null
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
	 * 获取指定sheet、指定row、对应的row 数据
	 * return:List[title,value],第一个元素是title，第二个元素是value
	 * row/cell都是从0开始计算
	 */
	public static List<String> getRow(String excelDir,int sheetIndex,int rowIndex){

		File excelFile = getExcelFile(excelDir);
		List<String> rowList = new ArrayList<String>();
		Workbook workbook=null;
		try {
			InputStream is = new FileInputStream(new File(excelDir));
			workbook = WorkbookFactory.create(is);

			Row row = workbook.getSheetAt(sheetIndex).getRow(rowIndex);//第一行（row 0）是标题列
			
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


    public  static  boolean checkFileType(String filePath){

        boolean checkedFile = true;

        File excelFile = getExcelFile(filePath);

        HSSFWorkbook workbook=null;

        try {
            workbook = new HSSFWorkbook(new FileInputStream(excelFile));
        }  catch (IOException e) {
            checkedFile = false;
        }
        finally{
            try {
                if (workbook != null) {
                    workbook.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return checkedFile;
    }
}
