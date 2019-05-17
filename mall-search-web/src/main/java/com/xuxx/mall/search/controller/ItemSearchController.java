package com.xuxx.mall.search.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xuxx.mall.search.service.ItemSearchService;

/**
 * 
 * @ClassName: ItemSearchController
 *
 * @author xuxx
 * @date 2019-05-17 14:32:26
 * @since  JDK 1.8
 *
 */
@RestController
@RequestMapping("/itemsearch")
public class ItemSearchController {
	
	@Autowired
	private ItemSearchService itemSearchService;
	
	@RequestMapping("/search")
	public Map search(@RequestBody Map searchMap){
		return itemSearchService.search(searchMap);		
	}

}
