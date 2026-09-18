/**
 * 全站公共脚本
 * 必须在 jquery.min.js 之后引入
 */

/**
 * 全局设置 ajax 请求头
 *
 * 后端拦截器会从 User-Token 请求头中读取 JWT。
 * 用 ajaxSetup 统一配置，就不必给每个 $.ajax 单独写 headers 了。
 *
 * 【关键】这里必须用 beforeSend 每次请求时「现读」localStorage，
 * 绝对不能写成 ajaxSetup({ headers: { "User-Token": localStorage.getItem(...) } })。
 *
 * 原因：ajaxSetup 只在页面加载时执行一次，headers 里的值是那一刻算出来的静态字符串。
 * 一旦写死，就会出现两种故障：
 *   1. 登录后跳转前的请求，仍带着登录前的空 token；
 *   2. 401 处理里删掉 localStorage 后，header 的值还是旧的，永远清不干净。
 * 动态读取则天然避免这些问题：谁在什么时候改了 localStorage，下一个请求就跟着变。
 */
$(document).ajaxSend(function (event, xhr) {
    var token = localStorage.getItem("userToken");
    if (token) {
        // 只在确实有 token 时才带上这个头，避免发送无意义的空 header
        xhr.setRequestHeader("User-Token", token);
    }
});

/**
 * 401 统一处理
 *
 * 用一个标志位防止并发请求同时失败时重复弹窗、重复跳转。
 *
 * 注意这里是「登录态失效」的兜底：本地有 token 但后端不认（过期 / 被改 / 签名不对）。
 * 清除本地凭证并回登录页，避免用户对着一个永远失败的操作反复点击。
 */
var redirecting401 = false;

$(document).ajaxError(function (event, xhr) {
    if (xhr.status !== 401 || redirecting401) {
        return;
    }
    redirecting401 = true;
    alert("登录已过期，请重新登录");
    localStorage.removeItem("userToken");
    localStorage.removeItem("loginUserId");
    location.href = "blog_login.html";
});

/**
 * 获取当前登录用户 id
 * 注意 localStorage 里存的始终是字符串，需要时请自行 parseInt
 */
function getLoginUserId() {
    return localStorage.getItem("loginUserId");
}

/**
 * 判断是否已登录
 */
function isLogin() {
    return !!localStorage.getItem("userToken");
}

/**
 * 注销：清空本地凭证并回到登录页
 */
function logout() {
    localStorage.removeItem("userToken");
    localStorage.removeItem("loginUserId");
    // 不需要再手动清请求头：ajaxSend 是每次请求现读 localStorage，
    // 这里删掉之后，下一个请求自动就不带 User-Token 了
    location.href = "blog_login.html";
}

/**
 * 页面级登录守卫
 *
 * 供「写博客」「更新博客」这类必须登录才能访问的页面在加载时调用。
 * 好处是未登录用户不会看到一个空编辑器、点提交才报错。
 *
 * 注意：这只是交互层的引导，后端拦截器才是真正的防线。
 *
 * @param {Boolean} redirectBack 是否在登录后跳回当前页
 */
function requireLogin(redirectBack) {
    if (isLogin()) {
        return true;
    }
    alert("请先登录");
    if (redirectBack) {
        // 把当前地址带过去，登录后可以跳回来
        location.href = "blog_login.html?redirect=" + encodeURIComponent(location.pathname + location.search);
    } else {
        location.href = "blog_login.html";
    }
    return false;
}

/**
 * 渲染顶部导航栏的登录态区域
 *
 * 页面里统一放一个 <span id="navAuth">，初始内容按「未登录」写。
 * 已登录时本函数把它替换成「注销」。
 *
 * 为什么不在 HTML 里直接写死「注销」：
 * 列表页、详情页都是允许匿名访问的，访客点「注销」会莫名其妙跳到登录页。
 *
 * 为什么用 DOM API 而不是 innerHTML 拼字符串：
 * 这里内容完全固定、不含任何用户输入，两种写法都安全，
 * 但直接创建节点可以让浏览器帮忙挂上事件，不必再依赖 onclick 属性里的全局函数名。
 */
function renderNavAuth() {
    var $navAuth = $("#navAuth");
    if ($navAuth.length === 0) {
        // 页面没有这个容器（比如登录页），静默跳过
        return;
    }

    if (!isLogin()) {
        // 未登录：保持一个指向登录页的入口即可，HTML 里已经是这个状态
        return;
    }

    var $logout = $('<a class="nav-link" href="#">注销</a>');
    $logout.on("click", function (e) {
        e.preventDefault();
        logout();
    });
    $navAuth.empty().append($logout);
}

/**
 * HTML 转义，防止博客内容里的尖括号被当成标签渲染
 * 这是防御 XSS 的最基本手段
 */
function escapeHtml(str) {
    if (str === null || str === undefined) {
        return "";
    }
    return String(str)
        .replace(/&/g, "&amp;")
        .replace(/</g, "&lt;")
        .replace(/>/g, "&gt;")
        .replace(/"/g, "&quot;")
        .replace(/'/g, "&#39;");
}

/**
 * 去掉 markdown 语法符号，用于列表页展示摘要
 */
function toSummary(content, length) {
    if (!content) {
        return "";
    }
    var plain = String(content)
        .replace(/[#*`>\-\[\]()!]/g, " ")
        .replace(/\s+/g, " ")
        .trim();
    var max = length || 120;
    return plain.length > max ? plain.substring(0, max) + "..." : plain;
}
