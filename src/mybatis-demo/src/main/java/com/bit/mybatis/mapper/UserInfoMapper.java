package com.bit.mybatis.mapper;

import com.bit.mybatis.entity.UserInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
public interface UserInfoMapper {

//    @Select("select * from user_info")
//    @Select("select id, username, password, age, gender, phone, delete_flag as deleteFlag, " +
//            "create_time as createTime, update_time as updateTime " +
//            "from user_info ")
//    @Results(id = "BaseMap", value = {
//            @Result(column = "delete_flag", property = "deleteFlag"),
//            @Result(column = "create_time", property = "createTime"),
//            @Result(column = "update_time", property = "updateTime"),
//    })
    @Select("select id, username, password, age, gender, phone, delete_flag," +
            " create_time, update_time from user_info")
    List<UserInfo> selectAll();


//    @ResultMap("BaseMap")
    @Select("select id, username, password, age, gender, phone, delete_flag," +
            " create_time, update_time from user_info where id= ${id}")
    UserInfo selectById(Integer id);

    @Select("select * from user_info where age= #{age} and gender= #{gender}")
    UserInfo selectByAgeAndGender(@Param("gender") Integer gender, Integer age);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into user_info (username, `password`, age, gender)" +
            "values (#{username},#{password},#{age},#{gender})")
    Integer insertUser(UserInfo userInfo);

    @Insert("insert into user_info (username, `password`, age, gender) " +
            "values (#{userInfo.username},#{userInfo.password},#{userInfo.age},#{userInfo.gender})")
    Integer insertUser2(@Param("userInfo") UserInfo userInfo);

    @Delete("delete from user_info where id= #{id}")
    Integer deleteUserById(Integer id);


    @Update("update user_info set gender=#{gender} where id=#{id}")
    void updateUserById(Integer gender, Integer id);


    @Select("select * from user_info where username= #{userName}")
    List<UserInfo> selectUserByName(String userName);


    @Select("select * from user_info where username= '${userName}' and password = '${password}'")
    List<UserInfo> selectUserByNameAndPassword(String userName, String password);


    @Select("select * from user_info order by id ${order}")
    List<UserInfo> selectUserByOrder(String order);


    @Select("select * from user_info where username like CONCAT('%',#{likeUserName},'%')")
    List<UserInfo> selectUserByLikeUserName(String likeUserName);

    @Select("""
            <script>
               insert into user_info
               <trim suffixOverrides="," prefix="(" suffix=")">
               <if test="username!=null">username,</if>
               <if test="password!=null">password,</if>
               <if test="age!=null">age</if>
               </trim>
               values
               <trim suffixOverrides="," prefix="(" suffix=")">
               <if test="username!=null">#{username},</if>
               <if test="password!=null">#{password},</if>
               <if test="age!=null">#{age}</if>
               </trim>
              </script>
             """)
    Integer insertByCondition(UserInfo userInfo);
}
