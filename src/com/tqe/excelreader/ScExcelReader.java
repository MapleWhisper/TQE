package com.tqe.excelreader;

import com.tqe.po.Course;
import com.tqe.po.SC;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * �û�ʵ�� Student-Course ѧ��ѡ��EXCEL�Ķ�ȡ
 */
@Component
public class ScExcelReader extends ExcelReader<SC> {

	/**
	 * ����ļ��Ƿ���sc�ļ�
	 */
	@Override
	public boolean checkFile(String excelDir) {
		List<String> list = ExcelUtils.getRow(excelDir, 0, 2);
		
		return list.contains("�γ̺�")&&list.contains("�����")&&list.contains("ѧ��ѧ��");
	}


}
