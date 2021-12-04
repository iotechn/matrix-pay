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

请确定您已经将 JAVA_HOME 配置，并将mvn命令配置到PATH中，若出现找不到命令，或找不到JAVA_HOME，[请参考此文档](https://blog.csdn.net/weixin_44548718/article/details/108635409)

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

##### 3.3. 使用Spring集成

1. 写一个实现类，实现 com.dobbinsoft.fw.pay.config.PayProperties，此类用于MatrixPay获取配置文件。

2. ```java
   // 编写支付成功回调通知处理类，实现Handler即可
   package com.dobbinsoft.fw.pay.handler.MatrixPayCallbackHandler;
   
   public interface MatrixPayCallbackHandler {
   
       /**
        * 在校验回调之前调用，可以在此添加上下文
        * @param request
        */
       void beforeCheckSign(HttpServletRequest request);
   
       /**
        * 默认入参 PayOrderNotifyResult
        * @param result
        * @param request 原始请求
        * @return
        */
       Object handle(MatrixPayOrderNotifyResult result, HttpServletRequest request);
   
   }
   ```

3. ```java
   // 将所有Handler加入到IoC中，并指派回调通知链接。并将MatrixPayService加入IoC
   @Configuration
   public class PayConfig {
   
       @Bean
       public PayProperties payProperties() {
           return new PayMerchantPropertiesImpl();
       }
   
       @Bean
       public MatrixPayService matrixPayService() {
           return new MatrixPayServiceImpl(payProperties());
       }
   
       @Bean
       public CheckstandMatrixPayCallbackHandler checkstandMatrixPayCallbackHandler() {
           return new CheckstandMatrixPayCallbackHandler();
       }
   
       @Bean
       public ServletRegistrationBean servletRegistrationBean() {
           Map<String, MatrixPayCallbackHandler> urlHandlerMap = new HashMap<>();
           urlHandlerMap.put("/cb/unify", checkstandMatrixPayCallbackHandler());
           return new ServletRegistrationBean(new PayHttpCallbackServlet(matrixPayService(), urlHandlerMap), urlHandlerMap.keySet().toArray(new String[]{}));
       }
   
   }
   ```

4. 支付下单，matrixPayService.createOrder

#### 四、完整文档

Matrix Pay API文档：

#### 五、特别注意

Matrix 并没有接入支付全套API。仅仅接入了之前用到的API。

请查看以下列表是否满足您的业务需求：

1. 统一下单 createOrder
2. 查询订单 queryOrder
3. 关闭订单 closeOrder
4. 订单退款 refund
5. 退款查询 refundQuery
6. 当面付 micropay

...若需要其他接口，请自行适配，欢迎将您的贡献PR到本仓库。特别感谢！


#### 六、贡献 & 社区
Matrix Pay 在API适配中，也许您所需要适配的字段，映射存在偏差或框架存在BUG，您可以直接在仓库中发布Pull Request。本项目欢迎所有开发者一起维护，并永久开源。