package com.xuxx.mall.shop.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.xuxx.mall.pojo.TbSeller;
import com.xuxx.mall.sellergoods.service.SellerService;

/**
 * 
 * @ClassName: UserDetailsServiceImpl 用户认证类4
 * @author xuxx
 * @date 2019-05-14 11:14:50
 * @since JDK 1.8
 *
 */
public class UserDetailsServiceImpl implements UserDetailsService {

	private SellerService sellerService;

	public void setSellerService(SellerService sellerService) {
		this.sellerService = sellerService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		List<GrantedAuthority> grantAuths = new ArrayList<GrantedAuthority>();
		grantAuths.add(new SimpleGrantedAuthority("ROLE_SELLER"));

		TbSeller seller = sellerService.findOne(username);
		if (seller != null) {
			if (seller.getStatus().equals("1")) {
				return new User(username, seller.getPassword(), grantAuths);
			} else {
				return null;
			}
		}
		throw new UsernameNotFoundException(username);
	}
}
