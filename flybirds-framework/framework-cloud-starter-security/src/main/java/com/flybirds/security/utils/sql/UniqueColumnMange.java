package com.flybirds.security.utils.sql;

import com.flybirds.common.util.str.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author :米饭饭一族
 */
@Component
public class UniqueColumnMange {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 唯一性校验
     * @param modelName
     * @param columnVal
     * @param paramVal
     * @return
     */
    public Boolean uniqueColumnService(String modelName, String columnVal, String paramVal){
        boolean ifExistVal = false;
        try{
            if (StringUtils.isBlank(paramVal)) {
                return ifExistVal;
            }
            String querySql = "select count(1) as counts from "+""+modelName+""+" where "+""+columnVal+""+" = "+" '"+paramVal+"'"+"" ;
            List<Map<String, Object>> list = jdbcTemplate.queryForList(querySql);
            Map<String, Object> map = list.get(0);
            if(Integer.parseInt(map.get("counts").toString())>0){
                ifExistVal = true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return ifExistVal;
    }

}
