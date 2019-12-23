package com.quotorcloud.quotor.academy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.quotorcloud.quotor.academy.api.entity.ListBox;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ListBoxMapper extends BaseMapper<ListBox> {

    List<ListBox> selectListBox(@Param("module") String module,@Param("tag") String tag, @Param("shopId") String shopId);

}
