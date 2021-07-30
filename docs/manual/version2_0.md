EasyReport(ver2.0) 用戶操作手冊
================================================

##   目錄

* [開發環境(Development Environment)](#user-content-1開發環境development-environment)
* [安裝與部署(Installation & Deployment)](#user-content-2安裝與部署installation--deployment)
	* [從源代碼安裝(From Source Code)](#user-content-21-從源代碼安裝from-source-code)
	* [從發布包安裝(From Release Packages)](#user-content-22-從發布包安裝from-release-packages)
	* [定時任務程序部署(Scheduled Task Deamon)](#user-content-23-定時任務程序部署scheduled-task-deamon)
* [使用說明(User Guide)](#user-content-3使用說明user-guide)
	* [預備知識(Prerequisites)](#user-content-31-預備知識prerequisites)
	* [數據源設置(DataSource Configuration)](#user-content-32-數據源設置datasource-configuration)
	* [配置管理(Configuration)](#user-content-33-配置管理configuration)
	* [報表設計(Reporting Design)](#user-content-34-報表設計reporting-design)
	* [定時任務配置管理(Scheduled Task Configruation)](#user-content-35-定時任務配置管理scheduled-task-configruation)
	* [示例(Examples)](#user-content-36-示例examples)
	* [相關參考(Referrence links)](#user-content-37-相關參考referrence-links)
* [開發者(For Developers)](#user-content-4開發者for-developers)
	* [報表引擎接口(Reporting Engine API)](#user-content-41-報表引擎接口reporting-engine-api)
	* [自定義報表開發(Customsized Reporting Development)](#user-content-42-自定義報表開發customsized-reporting-development)
* [常見問題(FAQ)](#user-content-5常見問題faq)

## 1.開發環境(Development Environment)
* [jdk1.8][]
* [maven3][]
* [idea][] or [eclipsejee][]
* [lombok][]
* [tomcat8+][]
* [MySQL5+][]

## 2.安裝與部署(Installation & Deployment)
系統默認登錄用戶:**admin** 密碼:**123456**

### 2.1 從源代碼安裝(From Source Code)
首先確定安裝好[jdk1.8][]與[maven3][]，並配置好maven倉庫，然後按如下步驟操作：
* step1:git clone https://github.com/xianrendzw/EasyReport.git
* step2:mvn -DskipTests package
* step3:經過step2, 如果沒有報錯，mvn spring-boot:run -pl easyreport-web 啟動程序，然後就可以通過瀏覽器localhost:8080查看

### 2.2 定時任務程序部署(Scheduled Task Deamon)
有時需要把報表定時（每天、每月，每季度等）以郵件形式發布給相關的人員，因此需要定時任務調度程序，常用的調度程序也很多（linux:at,crontab;windows:計劃任務）,本工具實現一個簡單的調度程序。
**說明：**該程序是可選的，如果不需要定時把報表以郵件方式發布，則可不部署該程序。  具體安裝與部署步驟如下:
* step1:cd yourgitrepository/EasyReport/easyreport-scheduler
* step2:修改 src\main\resources\${env}\resource.properties 數據庫連接，用戶與密碼
* step3:mvn clean package -Dmaven.test.skip=true -P${env} (${env}變量說明:dev表示開發環境,prod表示生產，test表示測試)
* step4:經過step3之後會在target目錄生成easyreport-scheduler.jar文件。然後在linux中執行如下shell命令:
```shell
nohup java -jar easyreport-scheduler.jar >easyreport2.log 2>&1 &
```

## 3.使用說明(User Guide)

### 3.1 預備知識(Prerequisites)
簡單的說，報表就是用表格、圖表等格式來動態顯示數據。它是數據可視化的重要部分。尤其在當今大數據泛濫的時代，到處都需要各種各樣的報表。在使用該工具之前您應該先瞭解一下數據倉庫、維度、度量、[事實表](http://www.cnblogs.com/wufengtinghai/archive/2013/05/04/3060265.html)等相關概念，這將會對你製作報表有一定的幫助。

本工具只是簡單的從數據庫(MySQL,Oracle,SQLServer,HBase等)中的事實表讀取數據，並轉換成HTML表格形式展示。不支持CUBE、鑽取、切片等復雜OLAP相關的功能。

### 3.2 數據源設置(DataSource Configuration)
在製作報表前需要先設置數據源，本工具只支持在單一數據源（即數據庫）生成報表。![ds-1][]

### 3.3 配置管理(Configuration)
配置管理主要於在製作報表時自動匹配一些常用的列名對應的中文描述。如:dt,date（日期）、title(標題）等。![config-1][]

### 3.4 報表設計(Reporting Design)
通常，只要把數據源配置成功就可以開始報表設計了，報表設計主要分兩個步驟：基本設置與查詢參數設置。且必須先把基本設置保存後方可進行查詢參數設置 ，查詢參數設置是可選的，主要看報表設計者的意圖。

#### 3.4.1 基本設置(Basic Settings)
![rp-1][]
報表的基本設置由4部分組成(如上圖所示）：**報表樹型列表、報表基本屬性、報表SQL查詢語句、報表元數據列配置**。
在設計報表之前，先簡單介紹幾個名詞，我們從數據倉庫概念了解到維度與度量這兩個概念，事實上一條SQL語句查詢的結果就是一張二維表格，即由行與列組成的表格，在統計分析時，我們把有些列稱為維度列，有些列稱為度量列。有時事實表裡有好幾個維度與度量列，但是SQL查詢結果只能是二維表格，它不能把維度層次化，展示方式固定而不能靈活變動，這樣在觀察與分析數據時多有不便，因此一些報表工具就解決了這些問題。本工具把事實表中的維度列與度量列進行再次劃分如下表所示：

類型      | 子類型
----- | ------
維度列  | 佈局維度列、簡稱佈局列
      | 一般維度列、簡稱維度列
度量列  | 統計列
      | 計算列

1. 佈局列主要用於報表展示方式上，如果佈局列為橫向展示，則報表在繪制時會把佈局列的內容繪製表報表表頭，維度列的內容繪制報表表體的左邊;如果佈局列為縱向展示，則報表在繪制時會把佈局列的內容繪製表報表表體的左邊，維度列的內容繪制報表表頭。

2. 計算列是根據SQL查詢結果中列的值再根據其配置的計算表達式動態運算出來的，它不存在於SQL語句或事實表中,其中使用的表達式引擎為[aviator][]。

瞭解了上述基本知識後，我們來看看一張報表的主要設計流程:
**1.創建報表樹型目錄列表**
![rp-2][]
**2.點擊1新建根節點，也可以在樹列表中右鍵創建子節點**
![rp-3][]
**3.選擇指定的目錄,設置基本信息，如報表名稱，數據源，佈局與統計列展示方式**
**4.輸入報表SQL查詢語句**
**5.執行SQL查詢語句並獲取報表的列信息**
**6.配置報表的列**
**7.新增並保存基本設置信息到數據庫**
![rp-4][]
新增成功後，就可以雙擊樹列表中報表名稱節點或點擊報表預覽按鈕預覽報表。如覺得報表展示的不夠友好，可以通過修改佈局列與統計列的展示方式來改變報表顯示。
![rp-5][]
上圖是日期為佈局列且橫向顯示的報表預覽結果。我們可以修改一下相關配置讓報表展示更直觀些。
![rp-6][]
由於列名dt已經在配置管理設置了默認標題，因此在執行SQL後會自動匹配它的標題，您也可以把其他的列名增加配置管理項中，這樣下次設計報表時就會自動匹配默認標題。現在看修改後報表展示。
![rp-7][]

#### 3.4.2 查詢參數(Query Parameter)
有時候報表需要根據指定條件動態生成，如要查看不同城市空氣質量情況，這個時候，我就需要創建一個查詢參數變量。
![rp-8][]
其中表單控件用於報表查詢參數顯示形式，主要有下拉單選框(select)、下單多選框(select mul)、復選框(checkbox)及文本框(textbox)四種。下圖1處為查詢參數列表。
![rp-9][]
當查詢參的表單控件為下拉單選或多選時，內容來源有兩種不同的形式。

內容來源 | 內容 | 備注
-------- | ---- | ----
SQL語句  | select col1 as name,col2 as text from table ... | 只包含兩列且列名必須為**name**與**text**，name列的值對應下拉框的value屬性，text列的值對應下拉框的text屬性
文本字符串 | name1,text1\|name2,text2\|... 或name1\|name2\|... | 多個值必須用’\|’分隔，如果name與text值相同則只選擇一個並用’\|’分開也可

#### 3.4.3 內置變量與函數(Build-in variables & functions)

有些常用的查詢參數不需要用戶每次都創建，因此集成在工具內，這些參數變量稱為**內置變量**。

有些報表的SQL語句很復雜，有時需要根據參數動態生成或需要用模板引擎([velocity][])生成，因此需要一些能在模板引擎中應用的函數，這些函數稱為**內置函數**。

1.內置變量(區分大小寫）

變量名 | 說明 | 返回值說明
-----  | ---- | --------
startTime|開始日期|2015-02-04(默認結束日期的前七天，這個可以由報表基本設置的顯示天數修改)
endTime|結束日期|2015-02-10（默認為當前天）
intStartTime|整型開始日期|20150204
intEndTime|整型結束日期|20150210
utcStartTime|UTC開始日期|2015-02-04（UTC日期，中國為UTC+8區）
utcEndTime|UTC結束日期|2015-02-10（UTC日期）
utcIntStartTime|UTC整型開始日期|20150204
utcIntEndTime|UTC整型結束日期|20150204

2.內置函數

* 日期函數
![rp-10][]
* 字符串函數，請參考[org.apache.commons.lang3.StringUtils][]類

#### 3.4.4 圖表顯示（Charting)
![rp-11][]
點擊報表的圖示展示按鈕，出現如下界面：
![rp-12][]
如果要查看多個城市也可以通過對比來顯示：
![rp-13][]
如果統計列只有一列時，圖表顯示就可以支持二個維度同時全部展示：
![rp-14][]

### 3.5 定時任務配置管理(Scheduled Task Configruation)


### 3.6 示例(Examples)

示例中的所有數據來源於:[pm25.in][]、[aqistudy][],如果您需要運行示例中的報表，需求在mysql中創建名為**china_weather_air**的數據庫，
然後解壓yourgitrepository/EasyReport/docs/db/mysql.zip,並執行china_weather_air_mysql.sql創建表結構與導入初始數據。

1. 最簡單報表,直接對應數據庫二維表結構
配置：
![ex-src-1][]
報表：
![ex-1][]
2. 帶內置變量與查詢參數的報表
配置：
![ex-src-2][]
查詢參數：
![ex-param-2][]
報表：
	a. 佈局列橫向，統計列橫向
![ex-2-1][]
	b. 佈局列縱向，統計列橫向
![ex-2-2][]
	c. 佈局列橫向，統計列縱向
![ex-2-3][]
	d. 佈局列縱向，統計列縱向
![ex-2-4][]
3. 多佈局列報表
配置：
![ex-src-3][]
報表：
	a. 佈局列橫向，統計列橫向
![ex-3-1][]
	b. 佈局列縱向，統計列橫向
![ex-3-2][]
	c. 佈局列橫向，統計列縱向
![ex-3-3][]
	d. 佈局列縱向，統計列縱向
![ex-3-4][]
4. 統計列可選報表
配置：
![ex-src-4][]
報表：
![ex-4-1][]
5. 報表列的排序
配置：
![ex-src-5][]
報表：
![ex-5][]
6. 按百分比格式顯示的列
配置：
![ex-src-6][]
報表：
![ex-6][]
7. 合並報表左邊相同維度列
合並前：
![ex-7-2][]
合並後：
![ex-7-1][]

### 3.7 相關參考(Referrence Links)

* 報表SQL中使用的模板引擎:[velocity][]
* 計算列中使用的表達式引擎:[aviator][]、[aviator-doc][]
* 所有示例中的數據來源:[pm25.in][]、[aqistudy][]
* 圖表控件:[echarts][]、[highcharts][]
* 前端報表表格及排序相關js插件：[tablesorter][]、[DataTables][]

## 4.開發者(For Developers)

該系統總體架構圖如下:
![dev-1][]

### 4.1 報表引擎接口(Reporting Engine API)
### 4.2 自定義報表開發(Customsized Reporting Development)
## 5.常見問題(FAQ)

[jdk1.8]: http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
[jre1.8]: http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html
[maven3]: http://maven.apache.org/download.cgi
[idea]: https://www.jetbrains.com/idea/
[eclipsejee]: http://www.eclipse.org/downloads/eclipse-packages/
[lombok]: https://projectlombok.org/download.html
[tomcat8+]: http://tomcat.apache.org/
[MySQL5+]: http://dev.mysql.com/downloads/mysql/
[velocity]: http://velocity.apache.org/engine/1.7/user-guide.html
[aviator]: https://code.google.com/p/aviator/wiki/User_Guide_zh
[aviator-doc]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/files/Aviator-2.3.0用戶指南.pdf
[org.apache.commons.lang3.StringUtils]: http://commons.apache.org/proper/commons-lang/javadocs/api-release/org/apache/commons/lang3/StringUtils.html
[pm25.in]: http://www.pm25.in
[aqistudy]: http://aqistudy.sinaapp.com/historydata/index.php
[echarts]: http://echarts.baidu.com/index.html
[highcharts]: http://www.highcharts.com/
[tablesorter]: http://mottie.github.io/tablesorter/docs/
[DataTables]: http://www.datatables.net/
[release]: https://github.com/xianrendzw/EasyReport/releases
[ds-1]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/ds-1.png
[config-1]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/config-1.png
[rp-1]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/rp-1.png
[rp-2]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/rp-2.png
[rp-3]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/rp-3.png
[rp-4]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/rp-4.png
[rp-5]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/rp-5.png
[rp-6]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/rp-6.png
[rp-7]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/rp-7.png
[rp-8]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/rp-8.png
[rp-9]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/rp-9.png
[rp-10]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/rp-10.png
[rp-11]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/rp-11.png
[rp-12]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/rp-12.png
[rp-13]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/rp-13.png
[rp-14]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/rp-14.png
[dev-1]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/dev-1.png
[ex-src-1]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/ex-src-1.png
[ex-1]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/ex-1.png
[ex-src-2]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/ex-src-2.png
[ex-param-2]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/ex-param-2.png
[ex-2-1]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/ex-2-1.png
[ex-2-2]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/ex-2-2.png
[ex-2-3]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/ex-2-3.png
[ex-2-4]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/ex-2-4.png
[ex-src-3]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/ex-src-3.png
[ex-3-1]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/ex-3-1.png
[ex-3-2]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/ex-3-2.png
[ex-3-3]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/ex-3-3.png
[ex-3-4]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/ex-3-4.png
[ex-src-4]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/ex-src-4.png
[ex-4-1]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/ex-4-1.png
[ex-src-5]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/ex-src-5.png
[ex-5]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/ex-5.png
[ex-src-6]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/ex-src-6.png
[ex-6]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/ex-6.png
[ex-7-1]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/ex-7-1.png
[ex-7-2]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/ex-7-2.png
[mysql.zip]: https://github.com/xianrendzw/EasyReport/blob/master/docs/db/mysql.zip?raw=true

