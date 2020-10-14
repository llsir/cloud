package com.boss.auth.service.imp;

import com.boss.auth.client.UcenterClient;
import com.boss.auth.entity.dto.UserJwt;
import com.boss.ucenter.entity.dto.UserExt;
import com.boss.ucenter.entity.po.Permission;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UcenterClient ucenterClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.isEmpty(username)) {
            return null;
        }
        UserExt userExt = ucenterClient.getUserExt(username);
        if(userExt == null){
            return null;
        }
        String password = userExt.getPassword();
        //用户被锁定
        if(userExt.getStatus().equals("103002")){
            UserJwt userDetails = new UserJwt(username,password,true,false);
            return userDetails;
        }
        //用户被注销
        if(userExt.getStatus().equals("103003")){
            UserJwt userDetails = new UserJwt(username,password,false,true);
            return userDetails;
        }
        //权限标识串
        List<Permission> permissions = userExt.getPermissions();
        List<String> permissionList = new ArrayList<>();
        for(Permission permission : permissions){
            permissionList.add(permission.getCode());
        }
        String user_permission_string  = StringUtils.join(permissionList,",");;
        UserJwt userDetails = new UserJwt(username,
                password,
                AuthorityUtils.commaSeparatedStringToAuthorityList(user_permission_string));
        userDetails.setId(userExt.getId());
        userDetails.setName(userExt.getName());
        userDetails.setUtype(userExt.getUtype());
        return userDetails;
    }
}
