package com.neusoft.passwordserver.repository;

import com.neusoft.passwordserver.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by fpf
 * Created date 2018/7/26
 */
@Repository
public interface UserRepository  extends JpaRepository<SysUser, String> {
    /**
     *
     * @param username
     * @return
     */
    @Query(value = "select * from sys_user where username =?1",nativeQuery = true)
    List<SysUser> query(String username);
}
