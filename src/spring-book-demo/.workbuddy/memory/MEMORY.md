# spring-book-demo 项目约定

## 代码风格（用户偏好，改动时保持一致）
- 学习型项目，保留当前分层：controller / service / mapper(+XML) / entity / enums / dao(mock) / config / interceptor。
- Controller 用 `@RequestMapping`（不区分 GET/POST）；字段注入用 `@Autowired`；日志用 Lombok `@Slf4j`。
- **返回值约定（2026-09-11 起已改为统一返回体）**：所有 Controller 接口返回 `com.example.book.entity.Result<T>`（code/msg/data）。code：200 成功、400 参数不合法、401 未登录、500 服务端异常。前端 jQuery ajax 用 `if (result.code == 200)` 判断成功、失败弹 `result.msg`、取数据用 `result.data`。**改动接口时必须同步改前端**。
- 单表 SQL 用 `@Select`/`@Insert` 注解，动态 SQL（`<set>`、`<foreach>`）写在 `resources/mapper/*Mapper.xml`。
- 注释用中文、量少；只在关键分支写。
- 前端：jQuery + ajax + 字符串拼 HTML，不用框架。

## 已知技术选型
- Spring Boot 3.5.7 / JDK 17 / MySQL 8（库名 `book_test`）/ MyBatis。
- **mybatis-spring-boot-starter 必须用 3.0.5**（baseline 对齐 Boot 3.5.x）；3.0.3 的 baseline 是 Boot 3.2，不匹配。
- 数据库软删除：`status = 0` 表示已删除，查询一律带 `status <> 0`。

## 登录与鉴权（2026-09-11 完成）
- 登录成功写入：`session.setAttribute(LoginInterceptor.SESSION_USER_KEY, userInfo)`，key 常量值 `"session_user_key"`，value 是 `UserInfo`（返回前已 `setPassword("")`）。
- `interceptor/LoginInterceptor`：session 里取不到该 key 就拦截 —— ajax（带 `X-Requested-With: XMLHttpRequest`）返 401 + JSON 返回体；页面请求 `sendRedirect("/login.html")`。
- `config/WebConfig implements WebMvcConfigurer`：`addPathPatterns("/**")`，放行 `/login.html`、`/user/login`、`/css/**`、`/js/**`、`/pic/**`、`/error`、`/favicon.ico`。新增静态目录或免登录接口时必须同步加进放行名单。
- 前端三个页面的 `error` 回调里已有 `if (error.status == 401) location.href = "login.html"`，现在是活代码。

## 尚未做（后续可补）
- 全局异常处理（`@ControllerAdvice`）：目前异常仍靠 service 里 try/catch 吞掉后返回 Result.fail。
- 密码仍是明文存储与明文比较（`UserController` 里直接 `password.equals(userInfo.getPassword())`）。
- `UserService.getUserInfo(name, password)` 的 password 参数目前没用到（逻辑挪到了 Controller）。
- `BookService.getList()` + `BookDao.mockData()` + `BookController.getList` 是早期 mock 链路，已无人调用（保留作参考）。

## 环境验证方式（无法用 mvn 时）
- 机器上 `mvn` 不在 PATH，但 `~/.m2/repository` 有依赖。可用 JDK 17 的 `javac` + `@argfile` 编译验证：
  argfile 里写 `-cp "<分号分隔的 jar 列表>"` 和源文件列表。**注意 javac 的 argfile 会把反斜杠当转义符**，路径必须转成正斜杠（`C:/Users/...`），否则 classpath 静默失效。
