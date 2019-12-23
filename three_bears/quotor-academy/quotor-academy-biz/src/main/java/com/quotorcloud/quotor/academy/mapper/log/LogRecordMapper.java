package com.quotorcloud.quotor.academy.mapper.log;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.quotorcloud.quotor.academy.api.dto.log.ColumnComment;
import com.quotorcloud.quotor.academy.api.entity.log.LogRecord;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 日志记录表 Mapper 接口
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-22
 */
public interface LogRecordMapper extends BaseMapper<LogRecord> {

    public static class OperationLogMapperProvider{
        public String selectAnyTalbeSQL(Map<String,String> map) {
            return map.get("sql");
        }
    }

    //查询任意表的字段与备注
    @Select("SELECT COLUMN_NAME `column`,column_comment `comment` FROM INFORMATION_SCHEMA.Columns WHERE table_name=#{table}")
    List<ColumnComment> selectColumnCommentByTable(String logTable);

    //查询任意sql
    @SelectProvider(type=OperationLogMapperProvider.class,method="selectAnyTalbeSQL")
    public Map<String,Object> selectAnyTalbe(@Param("sql")String sql);
}
