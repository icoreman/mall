//控制层 
app.controller('itemCatController', function($scope, $controller,
		itemCatService, typeTemplateService) {

	$controller('baseController', {
		$scope : $scope
	});// 继承

	// 读取列表数据绑定到表单中
	$scope.findAll = function() {
		itemCatService.findAll().success(function(response) {
			$scope.list = response;
		});
	}

	// 分页
	$scope.findPage = function(page, rows) {
		itemCatService.findPage(page, rows).success(function(response) {
			$scope.list = response.rows;
			$scope.paginationConf.totalItems = response.total;// 更新总记录数
		});
	}

	// 查询实体
	$scope.findOne = function(id) {
		itemCatService.findOne(id).success(function(response) {
			// 处理 模板
			var typeId = response.typeId;
			var typeTemplateList = $scope.typeTemplateList.data;
			for(var i = 0;i < typeTemplateList.length; i++) {
				if(typeTemplateList[i].id == typeId) {
					response.typeId = typeTemplateList[i];
				}
			}
			
			$scope.entity = response;
		});
	}

	// 保存
	$scope.save = function() {
		var serviceObject;
		if(!$scope.entity.typeId) {
			alert("请选择类型模板");
			return false;
		}
		$scope.entity.typeId = $scope.entity.typeId.id;
		if ($scope.entity.id != null) {// 如果有ID
			serviceObject = itemCatService.update($scope.entity); // 修改
		} else {
			$scope.entity.parentId = $scope.parentId;
			serviceObject = itemCatService.add($scope.entity);// 增加
		}
		serviceObject.success(function(response) {
			if (response.success) {
				// 重新查询
				$scope.findByParentId($scope.parentId);
			} else {
				alert(response.message);
			}
		});
	}

	// 批量删除
	$scope.dele = function() {
		// 获取选中的复选框
		itemCatService.dele($scope.selectIds).success(function(response) {
			if (response.success) {
				$scope.findByParentId($scope.parentId);// 刷新列表
				$scope.selectIds = [];
			}
		});
	}

	$scope.searchEntity = {};// 定义搜索对象

	// 搜索
	$scope.search = function(page, rows) {
		itemCatService.search(page, rows, $scope.searchEntity).success(
				function(response) {
					$scope.list = response.rows;
					$scope.paginationConf.totalItems = response.total;// 更新总记录数
				});
	}

	// 根据上级分类ID查询列表
	$scope.parentId = 0;
	$scope.findByParentId = function(parentId) {
		$scope.parentId = parentId;
		itemCatService.findByParentId(parentId).success(function(response) {
			$scope.list = response;
		});
	}

	$scope.grade = 1;// 当前级别
	// 设置级别
	$scope.setGrade = function(value) {
		$scope.grade = value;
	}

	$scope.selectList = function(p_entity) {
		if ($scope.grade == 1) {
			$scope.entity_1 = null;
			$scope.entity_2 = null;
		}
		if ($scope.grade == 2) {
			$scope.entity_1 = p_entity;
			$scope.entity_2 = null;
		}
		if ($scope.grade == 3) {
			$scope.entity_2 = p_entity;
		}

		$scope.findByParentId(p_entity.id);
	}
	
	$scope.typeTemplateList={data:[]};//类型模板列表
    
	//读取类型模板
	$scope.findTypeTemplateList=function(){
		typeTemplateService.selectOptionList().success(
			function(response){
				$scope.typeTemplateList={data:response};
			}
		);		
	}
});
