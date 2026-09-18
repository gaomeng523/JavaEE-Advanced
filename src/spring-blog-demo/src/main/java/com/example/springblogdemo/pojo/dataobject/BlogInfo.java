package com.example.springblogdemo.pojo.dataobject;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.util.Date;

@Data
public class BlogInfo {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String title;
    private String content;
    private Integer userId;

    /**
     * 逻辑删除标记：0 未删除，1 已删除
     *
     * <p>{@code @TableLogic} 让 MyBatis-Plus 自动接管这个字段：
     * 查询时自动追加 {@code delete_flag = 0}，调用 deleteById 时自动改成 update 语句。</p>
     */
    @TableLogic(value = "0", delval = "1")
    private Integer deleteFlag;

    /**
     * 创建时间，插入时自动填充（见 {@code MyMetaObjectHandler}）
     * FieldFill.INSERT 表示只在 insert 生效，后续 update 不覆盖
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新时间，插入和更新时都会自动填充
     * FieldFill.INSERT_UPDATE 表示 insert 和 update 都要回填，保证列表页展示的永远是最新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
