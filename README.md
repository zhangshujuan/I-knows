## 赞服务项目相关说明
### H5项目编译运行
1 在gaoding项目下编译打包模块, mvn clean install
2 进入gaoding-h5项目运行, mvn jetty:run, mvn tomcat:run
3 修改局部项目,进入相应子项目下,执行mvn clean install即可
4 跳过测试 mvn clean install -Dmaven.test.skip=true

### 命名
1 velocity模板名称使用下划线分割

### Restful
https://github.com/aisuhua/restful-api-design-references

### 发布注意点
1 无论更新什么,都要重新执行maven,复制到target下
2 运行路径知道target下,更新pom版本,需要修改webapp软连接# I-knows
