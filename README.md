# horx web开发框架
horx web开发框架（horx web development framework，horx-wdf）旨在减少搭建开发框架的麻烦，提供一套拿来即用的开发框架。本框架使用Java语言，集成了Spring、MyBatis等框架，提供用户、权限、异常处理、日志等功能，可在单机、集群、微服务等环境下部署。

## 代码库
- 框架代码：github [https://github.com/horxorg/horx-wdf.git](https://github.com/horxorg/horx-wdf.git)  码云Gitee [https://gitee.com/horxorg/horx-wdf.git](https://gitee.com/horxorg/horx-wdf.git)
- 示例代码：github [https://github.com/horxorg/horx-wdf-sample.git](https://github.com/horxorg/horx-wdf-sample.git)  码云Gitee [https://gitee.com/horxorg/horx-wdf-sample.git](https://gitee.com/horxorg/horx-wdf-sample.git)
- 文档：[国内访问](http://horxorg.gitee.io/horx-wdf-docs/index.html)  [国外访问](http://horxorg.github.io/horx-wdf-docs/index.html)

## 技术体系
- 后端技术：SSM（Spring MVC、Spring、Mybatis），也支持SpringBoot、微服务
- 展示层技术：FreeMarker，也可以支持vm、jsp等其他模板，但需要自行根据FreeMarker模板文件开发
- 前端技术：jquery、[layui](https://www.layui.com)

## 环境要求
- JDK：JDK8及以上版本
- Tomcat：Tomcat7及以上版本
- 数据库：MySQL 5.6.4，Oracle10g及以上版本
- 浏览器：支持HTML5的主流浏览器均支持，推荐谷歌、火狐等支持HTML5的较新版本

## 主要特性
- 提供一个完整的、可运行的系统</code>：通过简单的配置，系统即可运行起来。可以在此基础上，开发自己的业务
- 支持多种数据库：目前支持MySQL、Oracle数据库。通过扩展数据库方言，可支持更多种类的数据库
- 支持多种部署方式：支持单机部署、集群部署、微服务分布式部署
- 支持微服务：目前支持Dubbo、SpringCloud微服务
- 提供多种扩展接口，可以开发自己的特定实现：系统提供了很多默认实现，但面对各种各种的业务场景，不可能全部覆盖到。比如，用户密码加密方式，系统提供了明文、md5等密码存储方式，用户还可以通过扩展接口实现自己的密码加密机制。这样，用户只需要扩展接口实现，而不是修改系统代码来实现自己的特性
- 简化代码：比如通过多种注解（@Annotation），使代码更简洁，代码量更少；使用MyBatis时，不必再写繁琐的insert、update、resultMap语句
- 提供多级授权功能：理论上，每一级机构均可设置自己的管理员，进行角色管理、用户管理、授权等操作
- 提供数据权限功能：通过数据权限管理，可以简化数据权限控制的代码开发。通过配置，可以设置角色、用户的数据权限。在代码上，可以用注解的方式接收数据权限结果，形成数据权限查询条件，开发人员一般只需要把数据权限查询条件组织进SQL语句中</p>
- 集成Spring Session：用户会话信息持久化到数据库，可运行在集群环境下；重启应用时，用户会话不丢失
- 提供访问日志、数据操作日志记录功能：支持把访问日志、数据操作日志记录到文件、数据库，用户也可以自行扩展，实现自己的存储方式

## 协议
本软件遵循 Apache License Version 2.0

## 联系方式
horxorg@163.com
