# 集群搭建

Tips：
这里的集群都是伪集群，都放在同台机器。

- ## [ zookeeper 集群](http://zookeeper.apache.org/doc/r3.5.5/zookeeperStarted.html#sc_RunningReplicatedZooKeeper)
上面链接是官方例子。  
以下为简易步骤：  
> 1、下载文件 http://mirror.bit.edu.cn/apache/zookeeper/。  
2、上传服务器，解压 tar -zxvf [目标文件]。   
3、复制到 /usr/local 目录。  
4、在 zookeeper 根目录建一个data文件夹。  
5、分别复制 zookeeper 三个备份，为 zookeeper-1、zookeeper-2、zookeeper-3。  
6、官方例子是真集群，所以配置一样，但是这里我搭伪集群，所以配置信息都不一样，根据自己情况修改。修改配置文件 zoo.cfg，改端口、改 dataDir、添加集群列表，格式为 ip:集群间通讯端口:投票端口，通讯端口不能一样，投票端口要一致。  
```
tickTime=2000
dataDir=/var/lib/zookeeper
clientPort=2181
initLimit=5
syncLimit=2
server.1=zoo1:2888:3888
server.2=zoo2:2888:3888
server.3=zoo3:2888:3888
```
> 7、在 data 目录下建一个 myid 文件，设置当前 zookeeper 的id，比如：`echo 1 > myid`。  
8、分别运行三个 zookeeper，集群就搭建完毕了。

**简易原理：**
官方建议最少三个服务，且使用奇数的服务器数，原因是方便投票。集群的核心就是投票。  
当只有一个服务的时候是无法以集群模式运行。  
当有其他服务加入时，就会进行投票，票数过半方可生效，让 id 大的那个做为领导者，其余成为跟随者。   
当领导者挂掉后，剩余的服务进行投票，产生新的领导者。  
如果已经有领导者的，新加入的服务会成为跟随者。  
配置信息中，3888 是投票端口，2888是节点之间通讯的端口，clientPort 是外部注册、访问的端口。节点之间会进行类似心跳检测或者叫 `ping-pong` 机制，一旦没有回应，就会进行投票。

- ## [redis 集群](http://www.redis.cn/topics/cluster-tutorial.html)
上面链接为中文例子。  
以下为简易步骤：  
> 1、下载，解压，安装，需要先安装c/c++、ruby等环境。  
2、进入 src 目录，运行 make install PREFIX=/usr/local/redis-cluster/redis-1，依次建6个。  
3、拷贝 redis.conf 到各个实例的 bin 目录下。  
4、修改配置, 因为是单机版，port 端口不能一样，这里设置为 7000-7005，cluster-enabled 设置为 yes，开启集群模式，设置绑定的ip。默认是127.0.0.1，如果不修改，后面建集群的时候只能通过这个ip访问，如果你是放在虚拟机上运行，记得修改。  
5、全部启动起来.  
6、分槽。进入 src 目录 运行 `./redis-trib.rb create --replicas 1 127.0.0.1:7000 127.0.0.1:7001 127.0.0.1:7002 127.0.0.1:7003 127.0.0.1:7004 127.0.0.1:7005`。  
replicas 复制，也就是一个主有多少个从，这里设置为1。  
分不成功，要下载最新版本的ruby、gem安装redis。[如何卸载旧版本ruby](https://blog.whsir.com/post-2659.html)

**简易原理：**
redis 集群与上面 zookeeper 不一样，是一个无中心结构。没有使用一致性hash, 而是引入了 哈希槽的概念，也就是第六步分槽。有16384个槽,每个key通过CRC16校验后对16384取模来决定放置哪个槽。其次呢就是使用主从复制模型，达到高可用。

- ## [solr 集群](http://lucene.apache.org/solr/guide/7_7/getting-started-with-solrcloud.html)
以上为官方指引。  
以下为简易步骤：  
可以把 webapp 抽出来放在一个 tomcat 容器或者其他容器中，或者直接采用官方的整包。  
配置也有很多地方可以配，可以在bin/solr.in.sh 中配置或者在 server/solr/ 中的 solr.xml 中配置；如果是放在容器中，可以修改 web.xml 的配置。
直接运行，使用命令参数，比如:./solr start -cloud -s 'solrhome路径' -p '端口' -zkHost 'zookeeper集群列表'。  
换言之，如果采用配置，也是配置这些东西。  
### 手动配置
Tips：  
**需要依赖 zookeeper 集群。**
[下载地址](http://mirror.bit.edu.cn/apache/lucene/solr/)

> 1、 先下载，解压。  
2、 在 server/solr/ 目录下添加一个 core，比如“my_core”，拷贝 server/solr/configsets/basic_configs 里的 conf 到 server/solr/my_core，
在 my_core 目录下 添加 data目录。  
3、复制4个 solr 实例。  
4、分别修改端口、zookeeper集群的地址。  改 solr.in.sh 文件 (windows 为 solr.in.cmd 文件) 
```
RMI_PORT=18983           -- 默认的SOLR_PORT+10000  
SOLR_PORT=8983           -- 默认的为8983  
ZK_HOST=                 -- 你的zookeeper集群地址  
SOLR_HOME                -- home 地址,可以不修改，默认为 server/solr  
```

> 5、这里使用默认的 solr_home,所以配置文件放在 server/solr下，要把这些配置文件上传到 zookeeper管理。进入 server/scripts/cloud-scripts，里面有个 zkcli.sh。  
> ./zkcli.sh -zkhost '你的 zookeeper 集群地址' -cmd upconfig -confdir '你 core 的 conf 目录，任意一个 solr 实例都可以，因为都一样的' -confname '给这个配置取个名'。  
> 6、分别启动  
`./solr start -cloud`

注意：  
第一次启动时，实例中要有 collection，且将其目录下的 conf 上传到 zookeeper，否则会报错找不到配置。  

启动之后，就可以使用新版本前端 ui 界面生成新的 collection、修改逻辑分片数量等等。  

**简易原理：**
[官方说明](http://lucene.apache.org/solr/guide/7_7/how-solrcloud-works.html#how-solrcloud-works)  
solr cloud 有一个逻辑概念与实际概念的区分。上面的简易教程中起了四节点，这四个节点都是实际的，为了避免很复杂呢，一个节点只见了一个 core。默认是一个主，三个从。我们可以设置逻辑分片为2，就会自动成为两个主，2个从了。  
这个分片就是逻辑的分片，称之为 shared。shared 可以对应一个或多个节点，如果有多个节点，就要分主从，以到达高可用。节点之间通过投票选出主，这里称之为 leader 与 replica。  

整体来看，依然使用到了主从复制、使用 zookeeper 去做一个分布式锁的管理。  
