## Matrix Pay Logo

#### 一、Matrix 起源 (项目背景) 🍭 

> 在developer开发需要包含支付模块的系统时，通常会同时适配微信、支付宝、云闪付等第三方支付平台。通常，developer需要对多个平台进行适配。这些工作具有类似的对接流程，但是API却各成一派，大不相同。对接三家支付平台，就意味着您需要同时阅读三份API文档，维护三套代码。

MatrixPay(目标): 使用一套文档，一套API同时对接三家支付平台。

图片，以前的对接方式。 现在的对接方式

#### 二、Matrix API风格

> Matrix API本质是对支付平台官方或第三方API进行二次封装，让其有相同的入参返回值（包括错误码）。这样的好处是可以享受SDK本身升级带来的红利。
>
> Matrix 使用非常受欢迎的Java微信支付开发包 WxJava 相同的API风格，您可以无缝将自己的项目切换到Matrix Pay。并且对于熟悉 WxJava API的开发者更加友好。

#### 三、快速开始

##### 3.1. 下载代码

您可以在国内开源社区Gitee下载（推荐）：

您可以在国际开源社区Github下载：

分支约定

① master： 基本可用的最新版本

② develop：正在开发的版本，不保证能用

③ release：发行版，经过测试过，较稳定版本

##### 3.2. maven引入

请确定您已经将 JAVA_HOME 配置，并将mvn命令配置到PATH中，若出现找不到命令，或找不到JAVA_HOME，请参考此文档TODO

在项目根目录，打开命令行。并执行 ：

```shell
mvn install -Dmaven.test.skip=true
```

引入maven坐标到工程pom.xml文件中。

```xml
<groupId>com.dobbinsoft</groupId>
<artifactId>fw-pay</artifactId>
<version>1.0-SNAPSHOT</version>
```

##### 3.3. 使用SpringBoot集成

##### 3.4. 无框架集成

#### 四、完整文档

Matrix Pay 官网与联系：

Matrix Pay API文档：

#### 五、贡献 & 社区

Matrix Pay 在API适配中，也许您所需要适配的字段，映射存在偏差或框架存在BUG，您可以直接在仓库中发布PR。本项目欢迎所有开发者一起维护，并永久开源。