package com.quotorcloud.quotor.academy.service.expend;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quotorcloud.quotor.academy.api.dto.expend.ExpendDTO;
import com.quotorcloud.quotor.academy.api.entity.expend.Expend;
import com.baomidou.mybatisplus.extension.service.IService;
import com.quotorcloud.quotor.academy.api.vo.expend.ExpendVO;

/**
 * <p>
 * 支出信息表 服务类
 * </p>
 *
 * @author tianshihao
 * @since 2019-11-01
 */
public interface ExpendService extends IService<Expend> {

    Boolean saveExpend(ExpendDTO expendDTO);

    JSONObject listExpend(ExpendDTO expendDTO);

    Boolean removeExpend(String id);

    Boolean updateExpend(ExpendDTO expendDTO);

    ExpendVO selectExpendById(String id);

    /**
     * 计算总支出金额
     * @param expendDTO
     * @return
     */
    JSONObject countTotalExpend(ExpendDTO expendDTO);

    /**
     * 查询近一个月，近一年，近一周的数据
     */
    JSONObject selectWXStatement(ExpendDTO expendDTO);

    JSONObject listExpendApp(Page page, ExpendDTO expendDTO);


}
