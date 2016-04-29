package com.tqe.service;

import java.util.*;


import com.tqe.base.vo.PageVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.tqe.dao.EvalTableDao;
import com.tqe.po.EvalTable;

@Service
public class EvalTableServiceImpl extends BaseService<EvalTable>{
	@Autowired
	private EvalTableDao evalTableDao;
	@Override
	public EvalTable getById(Integer id){
		EvalTable evalTable = evalTableDao.getById(id);
		if(evalTable==null){
			return null;
		}
		return evalTable.json2Object();
	}
	
	@Override
	public void save(EvalTable table) {
        checkEvalTableItemLevel(table);
		table.setJsonString(JSON.toJSONString(table));
		table.setCreateDate(new Date(System.currentTimeMillis()));
		evalTableDao.save(table);
	}

    public void update(EvalTable table) {
        checkEvalTableItemLevel(table);
        table.setJsonString("");
        table.setJsonString(JSON.toJSONString(table));
        evalTableDao.update(table);
    }

    private void checkEvalTableItemLevel(EvalTable e){
        for(EvalTable.EvalTableItem item :e.getTableItemList()){
            // 把评教表项的分隔符统一为空格分隔
            // 表项等级的数量必须是4个整数 从大到小 倒序
            String level = item.getLevel();
            level=level.replaceAll("，", ",");
            level=level.replaceAll(",", " ");

            List<String> levels = Arrays.asList(StringUtils.split(level," "));
            if(levels.size()!=4){
                throw  new IllegalArgumentException("评教表项的等级 必须为4个:"+level+"   tableItem:"+item);
            }
            List<Integer> levelsIntValList = new ArrayList<Integer>();
            for(String levelValue : levels){
                levelsIntValList.add(Integer.parseInt(levelValue));
            }
            Collections.sort(levelsIntValList, new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return o2.compareTo(o1);
                }
            });

            item.setLevel(StringUtils.join(levelsIntValList.iterator()," "));
        }
    }

	public List<EvalTable> findAll(PageVO pageVO) {
		List<EvalTable> list = evalTableDao.findAll(pageVO);
		for(EvalTable evalTable : list){
			evalTable.json2Object();
		}
		return list;
	}

	public void delete(int id) {
		evalTableDao.delete(id);
		
	}



}
