package com.flybirds.system.sysSocial.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.flybirds.mybatis.core.basemapper.MpBaseMapper;
import com.flybirds.system.sysSocial.entity.SysSocialUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper
public interface SocialUserMapper extends MpBaseMapper<SysSocialUser> {

    default List<SysSocialUser> selectListByTypeAndUnionId(Integer userType, Collection<Integer> types, String unionId) {
        return selectList(new QueryWrapper<SysSocialUser>().eq("user_type", userType)
                .in("type", types).eq("union_id", unionId));
    }

    default List<SysSocialUser> selectListByTypeAndUserId(Integer userType, Collection<Integer> types, Long userId) {
        return selectList(new QueryWrapper<SysSocialUser>().eq("user_type", userType)
                .in("type", types).eq("user_id", userId));
    }

    default List<SysSocialUser> selectListByUserId(Integer userType, Long userId) {
        return selectList(new QueryWrapper<SysSocialUser>().eq("user_type", userType).eq("user_id", userId));
    }

}
