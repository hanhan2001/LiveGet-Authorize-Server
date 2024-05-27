# LiveGet-Authorize-Server

> LiveGet 验证服务器 + 网页服务器

版本: `0.0.1`

环境: `JAVA - 8`

存储方式: `Sqlite/Mysql`

其他文档:

- [Web](https://github.com/LeavesCloud/LiveGet-Authorize-Server/blob/master/Web.md)
- [Token](https://github.com/LeavesCloud/LiveGet-Authorize-Server/blob/master/Token.md)



## 配置文件

> 基本设置通过配置文件进行修改，避免平凡修改代码重启服务器
>
> - Configuration.yml -> 服务器基本设置配置文件
> - Message.yml -> 词条配置文件

配置文件并未支持高度自定义所以需要按照格式修改



## 命令

> 服务器控制台默认命令
>
> 暂未支持 tab 补全

| 名称    | 示例     | 描述                    |
| ------- | -------- | ----------------------- |
| reload  | /reload  | 重载服务器命令          |
| help    | /help    | 获取帮助命令            |
| jwt     | /jwt     | 获取新的 jwt 私匙和公匙 |
| pl      | /pl      | 获取服务器已加载插件    |
| plugins | /plugins | 获取服务器已加载插件    |
| stop    | /stop    | 关闭服务器              |

