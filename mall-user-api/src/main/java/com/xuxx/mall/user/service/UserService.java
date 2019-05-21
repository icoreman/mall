package com.xuxx.mall.user.service;
import java.util.List;

import com.xuxx.entity.PageResult;
import com.xuxx.mall.pojo.TbUser;

/**
 * 
 * @ClassName: UserService
 *
 * @author xuxx
 * @date 2019-05-21 09:25:20
 * @since  JDK 1.8
 *
 */
public interface UserService {

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<TbUser> findAll();
	
	
	/**
	 * 返回分页列表
	 * @return
	 */
	public PageResult<TbUser> findPage(int pageNum,int pageSize);
	
	
	/**
	 * 增加
	*/
	public void add(TbUser user);
	
	
	/**
	 * 修改
	 */
	public void update(TbUser user);
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public TbUser findOne(Long id);
	
	
	/**
	 * 批量删除
	 * @param ids
	 */
	public void delete(Long [] ids);

	/**
	 * 分页
	 * @param pageNum 当前页 码
	 * @param pageSize 每页记录数
	 * @return
	 */
	public PageResult<TbUser> findPage(TbUser user, int pageNum,int pageSize);
	
	
	/**
	 * 发送短信验证码
	 * @param phone
	 */
	public String createSmsCode(String phone);
	
	/**
	 * 校验验证码
	 * @param phone
	 * @param code
	 * @return
	 */
	public boolean checkSmsCode(String phone,String code);
	
}
