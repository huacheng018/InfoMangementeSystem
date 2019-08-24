# InfoMangementeSystem
学生管理系统
一、MySQL表结构
先说说表的结构

1、全部设置成UTF-8编码。
2、Id为主键，用的是自增长，类似腾讯的qq自动生成机制，注册时自动赋予。
3、登陆时用的是id号和password（密码）登陆
4、gender为性别，0为男，1为女
5、为了方便管理，学生和管理员共用一张表，authority表示权限，0为学生，1为管理员。
6、name表示用户名（自定义，也不怕重复报错），age表示年龄，major表示专业，academy表示学院。
7、headPath用来存储头像的文件名，而非地址。因为最后调用必须动态调用,过于真实
8、密码password用了MD5加密后再作存储

现有数据
以下四条数据，密码都为 123

项目原本为初始的尝试项目，初始为javaWeb，后因时间不足和对Mavae的不熟悉，而无法再搬回Maven，只能将就继续用，在原基础添砖加瓦

1、Utils工具包
BaseDao：封装了数据库的 增 删 查 改，以便Dao层方便调用
BaseServlet：封装了servlet的get和post，servlet包的子类只需继承BaseServlet然后定义方法名，而不用重写get和post
JdbcUtil：封装了jdbc数据库链接，直接调用即可
db.properties：properties文件，用于存储链接数据库的URL、name、password、和driverClass
MD5Utils：封装MD5加密函数，函数来源链接：
https://blog.csdn.net/qq_30683329/article/details/80879058

2、Servlet包
MangemerServlet：管理事件相应专用
PageServlet：分页显示列表专用
AddHeadPictureServlet：学生添加头像专用
StudentServlet：学生事件响应专用

3、filter包
AEncodingFilter：编码拦截器
BSensitiveWordFilter：敏感词拦截器（还没完工）
VisitFilter：地址栏非法访问拦截器，亲测有毒，有待完善

4、entity包
存放Student（学生用户）和PageBean（分页）的类对象

5、dao层
用于对Student数据增删查改的具体封装方法

四、其他说明
先放主要学习链接：https://www.bilibili.com/video/av54529855/?p=1

从最初的学习到初始项目结构，都是从这个视频里学的，再从各处博客视频添砖加瓦

完成情况：除了权限验证，Maven管理，websocket。其他基本都完成。

项目优点：都在代码注释里，已经写的十分详细，阅读基本没压力

项目缺点：
1、因为初始结构的问题，时间十分紧迫又没法大改（每个Class前的javadoc都有创建时间），所以cookie和session的应用基本为0，以至于非法地址拦截Filter没法用得上.
2、View层没分包。因为时间紧，其他关联又有点复杂，最后收尾时间不够不敢分包。
3、权限的初始方案是打算把非法地址拦截VisitFilter做好，防止从地址栏直接访问，直接从根源上解决非法访问和权限验证的问题，但最后一天时间也不够，没做出来（被某师兄说浮躁。。。）。
4、界面过于，，，等建模时间过了一定好好啃Bootstrap,,,,,,建模真的过于头大。
