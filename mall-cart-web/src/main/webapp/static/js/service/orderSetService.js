// 结算页服务层
app.service('orderSetService', function($http) {
	// 获取当前登录账号的收货地址
	this.findAddressList = function() {
		return $http.get('address/findListByLoginUser');
	}

	// 提交订单
	this.submitOrder = function(order) {
		return $http.post('order/add', order);
	}
});