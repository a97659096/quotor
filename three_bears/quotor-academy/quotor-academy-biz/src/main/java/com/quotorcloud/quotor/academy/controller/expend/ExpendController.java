package com.quotorcloud.quotor.academy.controller.expend;


import com.quotorcloud.quotor.academy.api.dto.expend.ExpendDTO;
import com.quotorcloud.quotor.academy.service.expend.ExpendService;
import com.quotorcloud.quotor.common.core.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 支出信息表 前端控制器
 * </p>
 *
 * @author tianshihao
 * @since 2019-11-01
 */
@RestController
@RequestMapping("/expend")
public class ExpendController {

    @Autowired
    private ExpendService expendService;

    /**
     * 查询支出列表
     * @param expendDTO
     * @return
     */
    @GetMapping("list")
    public R listExpend(ExpendDTO expendDTO){
        return R.ok(expendService.listExpend(expendDTO));
    }

    /**
     * 保存支出信息
     * @param expendDTO
     * @return
     */
    @PostMapping("save")
    public R saveExpend(ExpendDTO expendDTO){
        return R.ok(expendService.saveExpend(expendDTO));
    }

    /**
     * 修改支出信息
     * @param expendDTO
     * @return
     */
    @PutMapping("update")
    public R updateExpend(ExpendDTO expendDTO){
        return R.ok(expendService.updateExpend(expendDTO));
    }

    /**
     * 删除支出信息
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public R deleteExpend(@PathVariable String id){
        return R.ok(expendService.removeExpend(id));
    }

    /**
     * 按id查询支出信息
     */
    @GetMapping("list/{id}")
    public R selectExpendId(@PathVariable String id){
        return R.ok(expendService.selectExpendById(id));
    }

    /**
     * 总支出查询
     */
    @GetMapping("list/total")
    public R selectExpendTotal(ExpendDTO expendDTO){
        return R.ok(expendService.selectWXStatement(expendDTO));
    }
}
