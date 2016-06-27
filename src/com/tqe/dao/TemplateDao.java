package com.tqe.dao;

import com.tqe.po.Template;
import com.tqe.po.TemplateItem;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TemplateDao extends BaseDao<TemplateDao>{

    @Select("select * from template")
    List<Template> findAll();

    @Select("select * from template where id = #{id}")
    Template getById(Integer id);

    @Select("select * from template where type = #{type}")
    Template getByType(String type);



    @Select("select * from templateitem where typeId = #{typeId}")
    List<TemplateItem> findItemsByTypeId(Integer typeId);

    @Insert("INSERT INTO `tqe`.`templateitem` (`id`, `typeId`, `values`) VALUES (null, #{typeId}, #{values});")
    void saveItem(TemplateItem item);

    @Select("select * from templateitem where id  = #{itemId}")
    TemplateItem getItemById(Integer itemId);

    @Update("UPDATE `tqe`.`templateitem` SET `values`=#{values} WHERE  `id`=#{id};")
    void updateItem(TemplateItem templateItem);
}
