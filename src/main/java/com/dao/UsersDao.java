package com.dao;



import com.Users.Users;

import java.util.List;

public interface UsersDao {
    public Users get_id(int id);

    /**
     * 查询所有
     * @return
     */
    public List<Users> get_all();

    /**
     * 添加
     * @param user
     * @return
     */
    public int insert(Users user);

    /**
     * 删除
     * @param id
     * @return
     */
    public int delete(int id);

    /**
     * 修改
     * @param user
     * @return
     */
    public int update(Users user);

}
