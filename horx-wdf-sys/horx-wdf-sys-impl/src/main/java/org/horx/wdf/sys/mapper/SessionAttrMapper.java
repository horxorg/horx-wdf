package org.horx.wdf.sys.mapper;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.UpdateProvider;
import org.horx.wdf.common.mybatis.provider.InsertSqlProvider;
import org.horx.wdf.common.mybatis.provider.UpdateSqlProvider;
import org.horx.wdf.sys.domain.SessionAttr;

import java.util.List;

/**
 * 会话属性Mapper。
 * @since 1.0
 */
@Mapper
public interface SessionAttrMapper {
    @InsertProvider(type = InsertSqlProvider.class, method = "insertSql")
    int insert(SessionAttr po);

    @UpdateProvider(type = UpdateSqlProvider.class, method = "updateSql")
    int update(@Param("po") SessionAttr po);

    int updateAttrValue(SessionAttr po);

    int deleteByAttrKey(@Param("sessionId") Long sessionId, @Param("attrKey") String attrKey);

    int deleteBySessionId(@Param("sessionId") Long sessionId);

    int deleteExpired();

    List<SessionAttr> select(@Param("sessionId") Long sessionId);

    SessionAttr selectByAttrKey(@Param("sessionId") Long sessionId, @Param("attrKey") String attrKey);
}
