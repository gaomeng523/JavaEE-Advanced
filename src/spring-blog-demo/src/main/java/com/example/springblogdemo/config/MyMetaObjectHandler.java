package com.example.springblogdemo.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * MyBatis-Plus 字段自动填充处理器
 *
 * <p>实体上标注了 {@code @TableField(fill = ...)} 的字段，会在 insert / update 时
 * 由这里统一回填，业务代码就不用每次都手动 setCreateTime 了。</p>
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    /** 插入时填充：创建时间和更新时间都设为当前时间 */
    @Override
    public void insertFill(MetaObject metaObject) {
        Date now = new Date();
        // strictInsertFill 只有在字段当前为 null 时才会填充，避免覆盖业务里手动设置的值
        this.strictInsertFill(metaObject, "createTime", Date.class, now);
        this.strictInsertFill(metaObject, "updateTime", Date.class, now);
    }

    /** 更新时填充：只刷新更新时间，创建时间保持不变 */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "updateTime", Date.class, new Date());
    }
}
