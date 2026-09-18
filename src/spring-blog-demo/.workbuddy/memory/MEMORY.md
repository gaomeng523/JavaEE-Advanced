# spring-blog-demo 项目约定（长期）

## 技术栈
- Spring Boot 3.5.7 / Java 17 / MyBatis-Plus 3.5.5 / jjwt 0.11.5 / MySQL 8
- 包根：`com.example.springblogdemo`
- 分层：controller / service(+impl) / mapper / pojo(dataobject|request|response) / common(advice|constants|enums|exception|interceptor|utils) / config

## 项目约定（2026-09-18 确立）
1. **依赖注入统一用构造器注入**，不用 `@Autowired` 字段注入。
2. **参数校验**：Controller 类上必须加 `@Validated`，方法参数上的 `@NotNull` 才会生效；请求体对象内的字段用 `@NotBlank`（不要用 `@NotNull`，挡不住空串）。
3. **当前登录人**：一律通过 `@RequestAttribute(Constant.CURRENT_USER_ID)` 从拦截器解析的 token 中取，**绝不接受前端传 userId**。
4. **逻辑删除**：用实体字段上的 `@TableLogic(value="0", delval="1")`，不用 yml 全局配置。
5. **时间字段**：实体上标 `@TableField(fill = FieldFill.INSERT / INSERT_UPDATE)`，由 `config/MyMetaObjectHandler` 自动填充，业务代码不手动 set。
6. **响应结构**：Controller 直接返回业务对象，由 `ResponseAdvice` 统一包成 `{code, errMsg, data}`。
7. **业务异常**：抛 `BlogException("中文提示")`，由 `ExceptionAdvice` 统一转成 `Result.fail`。
8. **常量集中**在 `common/constants/Constant`，不写魔法字符串（如请求头名 `User-Token`）。
9. **前端**：`js/common.js` 用 `ajaxSetup` 全局注入 `User-Token`，用 `ajaxError` 统一处理 401；所有 `$.ajax` 不要在业务页重复写 headers。
10. **方法命名**：查询详情统一 `getBlogDetail`（不要 `getBlogDetal`）；参数名统一 `blogId`。

## editormd 使用要点（踩过的坑，复用价值高）
`static/blog-editormd/` 是 editor.md 编辑器，API 有几个反直觉的地方：
- `editormd.markdownToHTML(id, opts)` **没有返回值**，是直接把渲染结果 append 到 `#id` 容器里。想拿 HTML 得先渲染到隐藏容器再取 `.html()`。
- **不要传 `htmlDecode` 参数**。源码 `sanitize: (settings.htmlDecode) ? false : true`，传值等于关闭 HTML 转义 → 存储型 XSS。
- `editor.config(obj)` 不是「追加配置」，内部会 `recreate()` 销毁重建编辑器。`onload` 必须写在初始化参数里。
- `getMarkdown()`/`setMarkdown()` 依赖 `this.cm`，CodeMirror 是异步加载的，onload 前调用会抛错 → 需要 `editorReady` 标志位保护。
- 渲染产物的 h1-h4/ul/ol/blockquote/pre/table 都需要自己补 CSS，项目原有样式只覆盖了 `p`。

## 前端约定
- `js/common.js` 是全站公共脚本，提供：`logout()` / `getLoginUserId()` / `isLogin()` / `requireLogin(redirectBack)` / `renderNavAuth()` / `escapeHtml()` / `toSummary()`，并统一配置 `User-Token` 请求头、统一处理 401。
- 拼接用户输入到 HTML 时必须过 `escapeHtml()`。
- 控制元素显隐用 `addClass/removeClass("hide")`，**不要用 `.show()`** —— 它会把 `display` 写成 `block`，破坏 CSS 里原有的 `display:flex`。
- **请求头统一用 `$(document).ajaxSend()` 动态注入 `User-Token`**（每次请求现读 localStorage）。
  **绝对不要用 `ajaxSetup({headers: {"User-Token": localStorage.getItem(...)}})`** —— 它只在页面加载时求值一次，
  值是静态字符串，会导致「清不干净的旧 token」和「登录后仍发空 token」两类故障，且极易引发 401 死循环。
- **401 要分层处理**：全局 `ajaxError` 只负责「必须登录的操作」的兜底；
  对于**只影响局部展示**的请求（如首页卡片的 `/user/getUserInfo`），在页面自己的 `error` 回调里就地处理
  （清失效凭证 + 恢复默认文案），**不要惊动全局跳转** —— 否则匿名访客逛首页会被踢去登录页。
- **`blog_login.html` 只在带 `redirect` 参数时才自动跳走**。无条件「已登录就跳首页」会形成
  `跳首页 → 首页 401 → 跳回登录页 → 又判定已登录 → 再跳走` 的死循环，用户永远登不上。
- **按权限显隐的按钮，显示条件必须和后端鉴权条件一致（不能更宽松）**。
  典型坑：详情页的编辑/删除按钮原来只比 `userId`，漏了 `isLogin()`。
  一旦 localStorage 残留 `loginUserId` 而 token 已失效，按钮会显示但点了必然 401。
  正确写法：`isLogin() && String(blogInfo.userId) === String(getLoginUserId())`。
- **元素被隐藏时要在页面上解释原因**，否则用户会当成 bug 反馈。
  例：未登录访问他人博客没有编辑按钮，要有「登录后可管理自己的博客」这类提示（见 `.content .login-tip`）。
- **导航栏登录态统一用 `renderNavAuth()`**：页面里放 `<span id="navAuth">`，允许匿名访问的页面初始写「登录」，必须登录的页面初始留空。不要在每个页面里写死「注销」。
  （排查登录问题时，导航栏显示「登录」还是「注销」是判断当前登录态最快的方式。）
- **`blog_edit.html` / `blog_update.html` 的按钮区（`.content-edit .push`）宽度分配是 58% / 18% / 18% + 2% margin**。原来 `#title` 占 78% 已吃掉几乎全部空间，再加按钮必须同步重算宽度。
- **`WebConfig` 里受保护的接口清单**：`/blog/addBlog`、`/blog/updateBlog`、`/blog/deleteBlog`、`/user/getUserInfo`。
  读接口（列表、详情、作者信息）是公开的。所以页面在调用 `/user/getUserInfo` 前**必须先 `isLogin()` 判断**，
  否则匿名访客逛首页会被 401 统一处理踢去登录页。
- **所有提交类按钮都要加「进行中」闸门**（`deleting` / `submitting` / `logining` 布尔标志）。
  典型坑：删除连点两次，第二个请求因记录已被逻辑删除而返回「博客不存在」，
  在已经判断成功、即将跳转的界面上弹出一个莫名其妙的报错。
- **业务失败 = HTTP 200 + `code=-1`**（`ResultCodeEnum.FAIL`，注意不是 500），
  所以 `success` 回调里必须判断 `code == 200 && data == true`，不能只看 HTTP 状态。
- **参数校验失败 = HTTP 400**，错误消息在 `error.responseJSON.errMsg`。
- **`ajaxSetup` 的请求头是页面加载时一次性写死的** → 已废弃，见上一条改用 `ajaxSend`。
- **前端不允许出现写死的假数据**（用户名、统计数字等）。没有数据来源就显示 `-` 或明确文案，并注明原因。
  **能用后端算的（count / 聚合）就不要拉全表到前端过滤** —— 那会把每篇记录的完整正文都传进浏览器。
- **接口清单（`WebConfig` 拦截器）**：受保护 = `addBlog`/`updateBlog`/`deleteBlog`/`getUserInfo`；
  公开 = `getList`/`getBlogDetail`/`getAuthorInfo`/`countByUser`/`login`。
  加新接口的判断标准：**这个接口会按「当前登录人」返回数据、或修改数据吗？会就加进拦截。**

## 已知设计缺口（未实现，非 bug）
- `blog_detail.html` 左侧卡片的「分类」行后端完全没有对应概念，显示 `-`。
- `/blog/getList` 不支持按 userId 过滤，列表页没有「只看我的博客」筛选。


## 环境备注
- JDK：`E:\jdk`（JAVA_HOME）
- Maven 本地仓库：`E:\Maven\.m2\repository`
- 本机 bash 工具的基础命令（ls/grep/head 等）不可用，PowerShell 输出也不回传，需要读文件请直接用 Read/Glob/Grep 工具。
- 项目无 mvnw；未确认存在可执行的 mvn 命令，编译验证需用户在 IDE 或命令行自行完成。
