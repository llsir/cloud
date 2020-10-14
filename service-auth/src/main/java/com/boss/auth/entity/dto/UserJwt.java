package com.boss.auth.entity.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by admin on 2018/3/20.
 */
@Data
@ToString
public class UserJwt extends User {

    private Long id;
    private String name;
    private String utype;

    public UserJwt(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public UserJwt(String username, String password, boolean accountNonExpired, boolean accountNonLocked ) {
        super(username, password, true, accountNonExpired, true, accountNonLocked, new ArrayList<>());
    }


}
