使用手冊
================================================


### 1 功能介紹
本工具從數據庫(MySQL,Oracle,SQLServer,HBase等)的表中讀取數據，轉換成報表。支持多級索引以及復雜的自定義配置。![raw-table][]

比如可以把上面的數據庫表的內容，免開發配置出如下報表頁面：![report-ex1][]

此外，本工具還支持復雜的自定義條件篩選、排序、數據量計算表達式等。
### 2 數據源設置
首先要配置好數據來源，然後基於數據源做出報表。![ds-1][]

### 3 報表設計
數據源配置成功就可以開始報表設計了
首先寫一個SQL查詢語句，把想要展示的信息查詢出來，比如：

select area, year(dt), quality, pm25, pm10, o3 from fact_air_cn where area='北京市' or area='上海市' or area='三亞市' 
![design-1][]

點擊執行SQL，查詢出的內容要分成兩類，一類是“索引”，一類是數值。索引可以顯示在左側或者上側，可以有多級索引。比如我想分地區、年份、空氣質量類型來查看pm2.5, pm10, o3的數值：
![design-2][]
地區和年份的索引顯示在左側，空氣質量類型顯示在上側。

保存後點擊預覽報表即可顯示：![design-3][]

這樣，一份報表就做好了。


### 4 高級功能介紹：

#### 1 數值列可選
只選擇感興趣的數值列，讓有限的頁面空間顯示更多我們感興趣的內容
![column-select][]

#### 2 加入維度篩選
針對一個或多個維度列，生成下拉選擇框，只顯示感興趣的維度列。
![dem-select-1][]

上面的城市選項，需要在“查詢參數”tab下設置，而且下拉框的內容可以從數據庫裡查出來，也可以自己按照name1,text1|name2,text2|... 格式輸入。
同時sql語句裡要加上對應的篩選項名，比如：where area in ('${area}')
![dem-select-2][]

#### 3 加入日期篩選
報表的內容一般每天都會更新，所以日期的篩選很重要，本工具內置了startTime(七天前)，endTime(今天)兩個變量，可以很方便地滾動查看前7天的數據表內容。
Sql語句裡也要加上對應的篩選項名，比如：where dt > '${startTime}' and dt < '${endTime}'，
startTime，endTime是內置參數，不需要想配置城市篩選項那樣定義值
![date-select][]

#### 4 報表的另一個重要方面是排序方式，按需排序後可以方便地發現重要信息
在設計報表頁面，每一列都有排序方式設置，比如我想按照自己的順序對城市名稱進行排序，可以這麼操作： 
![set-sort-1][]
顯示的報表如下：
![set-sort-2][]
#### 5 通過報表右上角的圖標，點擊即可導出excel文件



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
[raw-table]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/raw-table.jpg
[report-ex1]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/report-ex1.jpg
[design-1]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/design-1.jpg
[design-2]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/design-2.jpg
[design-3]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/design-3.jpg
[column-select]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/column-select.jpg
[dem-select-1]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/dem-select-1.jpg
[dem-select-2]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/dem-select-2.jpg
[date-select]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/date-select.jpg
[set-sort-1]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/set-sort-1.jpg
[set-sort-2]: https://raw.githubusercontent.com/xianrendzw/EasyReport/master/docs/assets/imgs/set-sort-2.jpg


