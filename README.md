# mall
a B2B2C mall system.

## 整体架构如图：

![系统架构](https://github.com/icoreman/mall/blob/master/src/mall%E6%9E%B6%E6%9E%84%E5%9B%BE.png)

<h2 id="如何单机运行此项目">如何单机运行此项目</h2>

需要有一定的 Java 分布式相关基础，这里就没写太细。  
1、 clone 项目。  
2、 安装相关中间件([极简易教程](https://www.jianshu.com/p/6309efb2a821))：redis、zookeeper、solr、fastfds、cas、activemq等。运行 `mall-parent/src/mall.sql` 建库。  
3、 在 `mall-common/src/main/resources` 项目中配置相关中间件的参数，配置 `mall-dao/src/main/resources/properties/db.peroperties` 中的数据库相关参数。  
4、 将 `mall-parent/src/fastdfs` 下的 jar 包放到本地的 maven 仓库或者私人仓库中，注意路径。  
5、 进入 `mall-parent`，运行 `mvn clean install`。  
6、 可以依次打包，放入容器中运行，或者依次运行命令 `tomcat7:run` 运行，因为有 tomcat 插件。  

## 如何建立集群
[简易教程](https://github.com/icoreman/mall/blob/master/src/cluster.md)

### 运营商管理后台：
- 对商家的审核、管理。
- 对商品品牌、规格、模板、分类、审核管理。
- 对广告内容的管理。
- ...

商品审核通过后，会调用 activemq，发起一个名为“mall_queue_solr_add_item”队列的消息，搜索服务监听此队列，将这个商品对应的 sku 列表同步至 solr。
商品下架、删除同理。  
审核通过同时会发起一个名为”mall_topic_freemarker_create_item_page“主题的消息，页面生成服务监听此主题，将生成商品的静态的页面。 

商家审核通过之后，调用 activemq，生成短信服务队列消息，短信服务监听此队列，给对应商家发送消息。

商品分类、规格、品牌列表都是缓存到 redis 中的，这三个数据，变化不会太大，读频繁。在其增、删、改时做缓存同步。 

### 广告内容服务：
目前只做了首页的广告，由于内容变化不大，读频繁，故缓存至 redis 中。

### 搜索服务：
主要是对 solr 的应用，展示搜索结果时需要加载对应商品的品牌列表、规格、扩展属性、分类等信息。

### 页面生成服务
由于商品详情页访问量会比较大，但是其内容变化不会很频繁，故使用 freemarker 将其生成为静态页面，使用 Nginx 增加并发访问量。
使用 activemq，监听“mall_topic_freemarker_create_item_page”主题，增量生成静态页面。

### 用户中心
用户注册、个人信息修改、订单查询等功能。注册调用 activemq，mq 发送短信队列消息，短信服务监听此队列，给注册用户发送验证码。

### 登录中心
单点登录， cas，目前配置的数据源为 mysql。 

### 购物车
功能做得很简单，是一个”瘦“购物车，添加商品、结算。未登录时，将购车信息存储至 cookie 中，登录后，合并 cookie 与 redis 缓存中的商品信息。

### 任务调度
使用 spring task，去定时执行一些任务，比如，支付超时、秒杀超时等。
思考：
数据量大得时候，这玩意儿还能用么？或者有其他的解决方案？

### 秒杀
一个很简单的秒杀方案，就是靠缓存。把秒杀数据缓存到 redis 中，使用任务调度检查秒杀超时，下架秒杀商品。 


由于支付、短信服务申请都很麻烦，以个人身份申请很难，所以，就没有做。 
 
- [ ] 完善秒杀方案。
- [ ] 将源配置为 redis，把用户账号密码缓存至 redis 中，提高登录速度。
- [ ] 日志管理，ELK 工具。  
- [ ] docker。  
- [ ] Jenkins 继续集成工具  
- [x] [如何单机运行此项目](如何单机运行此项目)。  
- [x] [分布式集群部署](https://github.com/icoreman/mall/blob/master/src/cluster.md)
