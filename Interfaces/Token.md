# Token

> LiveGet-Authorize Token接口

版本: `0.0.1`

接口文档:

- [Interface](https://github.com/LeavesCloud/LiveGet-Authorize-Server/blob/master/Interface.md)

- [Module](https://github.com/LeavesCloud/LiveGet-Authorize-Server/blob/master/Interfaces/Module.md)

其他文档:

- [Readme](https://github.com/LeavesCloud/LiveGet-Authorize-Server/blob/master/Readme.md)

- [Web](https://github.com/LeavesCloud/LiveGet-Authorize-Server/blob/master/Web.md)

## Info

> 查询授权码

### 概览

请求地址: `http://地址:端口/token/create`

请求方式: `GET`

返回格式: `JSON`

请求示例: `http://地址:端口/token/info?token=授权码&function=模块名称&object=子模块名称&password=验证密码`

### 参数说明

| 名称     | 必填 | 类型   | 默认值  | 描述       |
| -------- | ---- | ------ | ------- | ---------- |
| token    | 是   | String | 无      | 授权码     |
| function | 是   | String | 无      | 来源模块   |
| object   | 否   | String | default | 来源子模块 |
| password | 是   | String | 无      | 验证密码   |

### 返回示例

```json
// 授权码信息
{
  "code": 200,
  "type": "token_info",
  "uuid": "%uuid%",
  "token": "%token%",
  "function": "%function%",
  "object": "%object%",
  "save": "%save%",
  "over": "%over%",
  "machine": "%machine%",
  "lastUse": "%lastUse%",
  "time": "%date%"
}
```

```json
// 授权码不存在
{
  "code": 110,
  "type": "token_not_found",
  "message": "Can't find token.",
  "time": "%date%"
}
```

```json
// 模块不存在
{
  "code": 110,
  "type": "module_not_found",
  "message": "Can't find module.",
  "time": "%date%"
}
```

```json
// 模块过期
{
  "code": 110,
  "type": "module_overdue",
  "Message": "This module overdue.",
  "time": "%date%"
}
```

```json
// 验证密码错误
{
  "code": 110,
  "type": "error_password_invalid",
  "message": "Password invalid.",
  "time": "%date%"
}
```

## Create

> 创建授权码

### 概览

请求地址: `http://地址:端口/token/create`

请求方式: `GET`

返回格式: `JSON`

请求示例: `http://地址:端口/token/create?token=授权码&function=模块名称&object=子模块名称&qq=绑定QQ&time=时间(秒)&password=验证密码`

### 参数说明

| 名称     | 必填 | 类型   | 默认值          | 描述       |
| -------- | ---- | ------ | --------------- | ---------- |
| token    | 是   | String | 无              | 授权码     |
| function | 是   | String | 无              | 来源模块   |
| object   | 否   | String | default         | 来源子模块 |
| qq       | 是   | String | 无              | QQ号码     |
| time     | 否   | String | 当前时间+999999 | 有效时间   |
| password | 是   | String | 无              | 验证密码   |

### 返回示例

```json
// 创建成功后返回授权码信息
{
  "code": 200,
  "type": "token_info",
  "uuid": "%uuid%",
  "token": "%token%",
  "function": "%function%",
  "object": "%object%",
  "save": "%save%",
  "over": "%over%",
  "machine": "%machine%",
  "lastUse": "%lastUse%",
  "time": "%date%"
}

```

```json
// 授权码已存在
{
  "code": 110,
  "type": "token_existed",
  "message": "This token existed.",
  "time": "%date%"
}
```

```json
// 模块不存在
{
  "code": 110,
  "type": "module_not_found",
  "message": "Can't find module.",
  "time": "%date%"
}
```

```json
// 模块过期
{
  "code": 110,
  "type": "module_overdue",
  "Message": "This module overdue.",
  "time": "%date%"
}
```

```json
// 验证密码错误
{
  "code": 110,
  "type": "error_password_invalid",
  "message": "Password invalid.",
  "time": "%date%"
}
```



## Delete

> 删除授权码

### 概览

请求地址: `http://地址:端口/token/delete`

请求方式: `GET`

返回格式: `JSON`

请求示例: `http://地址:端口/token/delete?token=授权码&function=模块名称&object=子模块名称&password=验证密码`

### 参数说明

| 名称     | 必填 | 类型   | 默认值  | 描述       |
| -------- | ---- | ------ | ------- | ---------- |
| token    | 是   | String | 无      | 授权码     |
| function | 是   | String | 无      | 来源模块   |
| object   | 否   | String | default | 来源子模块 |
| password | 是   | String | 无      | 验证密码   |

### 返回示例

```json
// 删除授权码
{
  "code": 200,
  "type": "token_delete",
  "token": "%token%",
  "function": "%function%",
  "object": "%object%",
  "message": "Deleted token.",
  "time": "%date%"
}

```

```json
// 授权码不存在
{
  "code": 110,
  "type": "token_not_found",
  "message": "Can't find token.",
  "time": "%date%"
}
```

```json
// 模块不存在
{
  "code": 110,
  "type": "module_not_found",
  "message": "Can't find module.",
  "time": "%date%"
}
```

```json
// 模块过期
{
  "code": 110,
  "type": "module_overdue",
  "Message": "This module overdue.",
  "time": "%date%"
}
```

```json
// 验证密码错误
{
  "code": 110,
  "type": "error_password_invalid",
  "message": "Password invalid.",
  "time": "%date%"
}
```



## Verify

> 验证授权码

### 概览

请求地址: `http://地址:端口/token/verify`

请求方式: `GET`

返回格式: `JSON`

请求示例: `http://地址:端口/token/verify?token=授权码&machine=机器码&identification=身份码`

### 注意

- 根据授权码格式判断是否为子模块授权码，如 `子模块名称-授权码`

- 未绑定授权码第一次验证会自动绑定.

### 参数说明

| 名称           | 必填 | 类型   | 默认值 | 描述           |
| -------------- | ---- | ------ | ------ | -------------- |
| token          | 是   | String | 无     | 授权码         |
| machine        | 是   | String | 无     | 机器码         |
| identification | 是   | String | 无     | 来源模块身份码 |

### 返回示例

```json
// 验证通过
{
  "code": 200,
  "type": "token_verified",
  "message": "Token verified.",
  "time": "%date%"
}
```
```json
// 绑定机器码错误
{
  "code": 110,
  "type": "token_machine_error",
  "message": "Machine error.",
  "time": "%date%"
}
```
```json
// 授权码不存在
{
  "code": 110,
  "type": "token_not_found",
  "message": "Can't find token.",
  "time": "%date%"
}
```

```json
// 模块不存在
{
  "code": 110,
  "type": "module_not_found",
  "message": "Can't find module.",
  "time": "%date%"
}
```
```json
// 模块过期
{
  "code": 110,
  "type": "module_overdue",
  "Message": "This module overdue.",
  "time": "%date%"
}
```


## SetMachine

> 修改绑定机器码

### 概览

请求地址: `http://地址:端口/token/setMachine`

请求方式: `GET`

返回格式: `JSON`

请求示例: `http://地址:端口/token/setMachine?token=授权码&function=模块名称&object=子模块名称&machine=机器码&password=验证密码`

### 参数说明

| 名称     | 必填 | 类型   | 默认值  | 描述       |
| -------- | ---- | ------ | ------- | ---------- |
| token    | 是   | String | 无      | 授权码     |
| function | 是   | String | 无      | 模块名称   |
| object   | 否   | String | default | 子模块名称 |
| machine  | 否   | String | 无      | 机器码     |
| password | 是   | String | 无      | 验证密码   |

### 返回示例

```json
// 修改后返回授权码信息
{
  "code": 200,
  "type": "token_info",
  "uuid": "%uuid%",
  "token": "%token%",
  "function": "%function%",
  "object": "%object%",
  "save": "%save%",
  "over": "%over%",
  "machine": "%machine%",
  "lastUse": "%lastUse%",
  "time": "%date%"
}
```

```json
// 授权码不存在
{
  "code": 110,
  "type": "token_not_found",
  "message": "Can't find token.",
  "time": "%date%"
}
```

```json
// 模块不存在
{
  "code": 110,
  "type": "module_not_found",
  "message": "Can't find module.",
  "time": "%date%"
}
```

```json
// 验证密码错误
{
  "code": 110,
  "type": "error_password_invalid",
  "message": "Password invalid.",
  "time": "%date%"
}
```

## Renew

> 续费授权码

### 概览

请求地址: `http://地址:端口/token/renew`

请求方式: `GET`

返回格式: `JSON`

请求示例: `http://地址:端口/token/renew?token=授权码&function=模块名称&object=子模块名称&time=时间(秒)&password=验证密码`

### 注意

- 已过期授权码会将过期时间调整为 `当前时间 + 续费时间 `
- 未过期授权码会将过期时间调整为 `过期时间 + 续费时间`

### 参数说明

| 名称     | 必填 | 类型   | 默认值  | 描述       |
| -------- | ---- | ------ | ------- | ---------- |
| token    | 是   | String | 无      | 授权码     |
| function | 是   | String | 无      | 模块名称   |
| object   | 否   | String | default | 子模块名称 |
| time     | 是   | String | 无      | 续费时间   |
| password | 是   | String | 无      | 验证密码   |

### 返回示例

```json
// 修改后返回授权码信息
{
  "code": 200,
  "type": "token_info",
  "uuid": "%uuid%",
  "token": "%token%",
  "function": "%function%",
  "object": "%object%",
  "save": "%save%",
  "over": "%over%",
  "machine": "%machine%",
  "lastUse": "%lastUse%",
  "time": "%date%"
}
```

```json
// 授权码不存在
{
  "code": 110,
  "type": "token_not_found",
  "message": "Can't find token.",
  "time": "%date%"
}
```

```json
// 模块不存在
{
  "code": 110,
  "type": "module_not_found",
  "message": "Can't find module.",
  "time": "%date%"
}
```

```json
// 验证密码错误
{
  "code": 110,
  "type": "error_password_invalid",
  "message": "Password invalid.",
  "time": "%date%"
}
```