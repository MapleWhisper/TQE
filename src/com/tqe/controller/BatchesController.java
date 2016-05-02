package com.tqe.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.tqe.base.BaseResult;
import com.tqe.base.vo.PageVO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import com.tqe.po.Batches;
import com.tqe.service.BatchesServiceImpl;


@Controller
@RequestMapping("/admin")
public class BatchesController extends BaseController {

    private static final Log logger = LogFactory.getLog(BatchesController.class);

	@Autowired
	private BatchesServiceImpl batchesService;
	
	@RequestMapping("/batches")
	public String batches(
            Model model,
            PageVO pageVO
    ){
		List<Batches> list = batchesService.findAll(pageVO);
		model.addAttribute("batchesList",list);
        model.addAttribute("condition",pageVO.getFilters());
		return "batches/batches";
	}
	
	@RequestMapping("/batches/add")
	public String addBatch(){
		return "batches/addBatch";
	}
	
	
	
	@RequestMapping("/batches/show/{id}")
	public String showBatch(@PathVariable int id,Model model){
		model.addAttribute("batches",batchesService.getByIdWithEvalTable(id));
        batchesService.reAnalyseStatistic(id);  //更新批次的平均得分
		return "batches/showBatches";
	}
	
	@RequestMapping("/batches/save")
	public String saveBatches(
            @ModelAttribute() Batches batches,
            Model model
    ){
        String s= "";
        if( (s = checkBatchDate(batches))!=null ){
            return sendError(model, s,logger);
        }
		batchesService.save(batches);
		
		return "redirect:/admin/batches";
	}
	
	@RequestMapping("/batches/update")
	public @ResponseBody
	BaseResult
	updateBatches(
            @ModelAttribute() Batches batches
    ) throws IOException{
        String s= "";
        if( (s = checkBatchDate(batches))!=null ){
            return BaseResult.createFailure(s);
        }
		Batches b = batchesService.getById(batches.getId());
		if(b!=null){
			b.setBeginDate(batches.getBeginDate());
			b.setEndDate(batches.getEndDate());
		}

		batchesService.update(b);
		return BaseResult.createSuccess("修改日期成功！");
	}

    /**
     *  检查批次时间的合法性
     *  1. 开始时间小于 截止时间
     *  2. 批次的开始 时间 必须在所有批次都结束评教才可以开始
     *  @return  如果没问题返回 null 否则返回错误信息
     */
    private String checkBatchDate(Batches batches) {
        if(batches==null){
            return null;
        }
        if(batches.getBeginDate()==null || batches.getEndDate()==null || batches.getBeginDate().getTime()>=batches.getEndDate().getTime()){
            return "起始时间不能为空 截止时间不能为空 起始时间必须要要小于截止时间";
        }
        //对新设置的批次时间 进行查询是否可用
        // 批次的开始时间和 结束时间都不能是 数据库已经存在批次的时间段内
        Batches conflictBatch = batchesService.checkDateConflict(batches);
        if(conflictBatch!=null){
            return getErrorMsg(conflictBatch);
        }
        return null;
    }

    private String getErrorMsg(Batches conflictBatch) {
        return "新日期和已存在的批次时间冲突:批次名:"+conflictBatch.getName()+" 冲突时间:"+sdf.format(conflictBatch.getBeginDate())
                +" ~ "+ sdf.format(conflictBatch.getEndDate())+" 请重新设置开始日期或者截止日期!";
    }


    /**
	 * 
	 * 设置评教批次的默认评价表
	 * @param bid 评教批次Id
	 * @param eid 评教表Id
	 * @param type student:学生 teacher:教师 lead:领导
	 * @return
	 */
	@RequestMapping("/batches/setEval/{type}/{bid}/{eid}")
	public String defaultEval(@PathVariable Integer bid,@PathVariable Integer eid,@PathVariable String type,Model model){
		
		if(bid==null || eid==null){
			return "redirect:/admin/batches";
		}
		Batches b = batchesService.getById(bid);
			
		if(StringUtils.hasText(type) && b!=null){		//如果有内容
			if(type.equals("student")){
				
				b.setStuEvalId(eid);
				batchesService.update(b);
				return "redirect:/admin/batches/show/"+bid;
			}
			if(type.equals("teacher")){
				b.setTeaEvalId(eid);
				batchesService.update(b);
				
				return "redirect:/admin/batches/show/"+bid;
			}
			if(type.equals("lead")){
				b.setLeadEvalId(eid);
				batchesService.update(b);
				return "redirect:/admin/batches/show/"+bid;
			}
			if(type.equals("teaStu")){
				b.setTeaStuEvalId(eid);
				batchesService.update(b);
				return "redirect:/admin/batches/show/"+bid;
			}
			
		}
		model.addAttribute("msg", "路径参数不正确:/batches/setEval/{type}/{bid}/{eid}\n");
		return "error";
		
			
	}


    @RequestMapping("/batches/delete")
    @ResponseBody
    public BaseResult batches(
            @RequestParam Integer bid
    ){
        if(bid==null || bid <=0){
            return BaseResult.createFailure("批次号为空: bid :"+bid);
        }
        try{
            batchesService.delete(bid);
        }catch (Exception e){
            return BaseResult.createFailure("删除批次失败！"+e.getMessage());
        }
        return BaseResult.createSuccess("删除批次成功");
    }

}
