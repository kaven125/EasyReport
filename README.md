EasyReport
==========

A simple and easy to use Web Report System for java

EasyReport是一個簡單易用的Web報表工具,它的主要功能是把SQL語句查詢出的數據轉換成報表頁面，
同時支持表格的跨行(RowSpan)與跨列(ColSpan)配置。
同時它還支持報表Excel導出、圖表顯示及固定表頭與左邊列的功能。  

* mvn -DskipTests package
* mvn spring-boot:run -pl easyreport-web 
* ./gradlew build -x test
* ./gradlew bootRun -pl easyreport-web

然後就可以通過瀏覽器localhost:8080查看


## Release(發布說明)
### what's new?(ver2.1)
* 改進圖表報表圖表生成並增加圖表生成配置
* 定時任務功能完成
* 支持大數據產品查詢引擎(Hive,Presto,HBase,Drill,Impala等）
* 提供REST API服務接口
* 增加報表權限控制

### what's new?(ver2.0)
* 界面交互調整,前端js代碼全部重寫,方便向AMD模塊化轉換
* 報表引擎查詢支持CP30、Druid、DBCP2連接池
* JAVA部分代碼重構
* 加入用戶及權限管理模塊
* 數據訪問採用mybatis框架,方便二次開發
* 報表展現支持自定義生成模板

## [入門手冊][]
## [用戶參考][]
## 捐助
您的熱情,我的動力!開源是一種精神,也是一種生活...


[入門手冊]: https://github.com/xianrendzw/EasyReport/blob/master/docs/manual/user-guide.md
[用戶參考]: https://github.com/xianrendzw/EasyReport/blob/master/docs/manual/version2_0.md
