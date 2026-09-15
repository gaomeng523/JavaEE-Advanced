# MEMORY.md — Spring-aop-demo 项目长期笔记

## 项目约定
- 包名根：`com.example.springaopdemo`（课件原文是 `com.example.demo`，落到本项目时统一替换）。
- 学习类项目：所有课件示例代码都保留在项目里，不删除，方便对照复习。
- 目录按课件章节拆分子包，避免不同章节的切点互相干扰：
  - `aspect` ← 2.2 / 3.2 / 3.3 / 3.5.2
  - `aspect.priority` ← 3.4 切面优先级
  - `proxy` ← 4.1 静态代理 + JDK/CGLIB 动态代理
  - `proxy.spring` ← 4.2 Spring AOP 原理验证
  - `annotation` ← 3.5.2.1 自定义注解
- 每个 demo 有独立入口 Controller，避免多个切面同时命中同一方法导致日志混乱。
  唯一例外：`controller.TestController` 同时被 AspectDemo / AspectDemo2 / MyAspectDemo 命中，这是课件原意。
- **注释规范（用户明确要求）**：注释只讲知识点本身，**不要写"课件 X.Y"之类的出处/章节号**。

## 环境信息（本机）
- JDK：`E:\jdk`（JAVA_HOME 已配置），Java 17。
- Maven：`C:\Users\a\apache-maven-3.9.6\bin\mvn.cmd`（**不在 PATH 中**，必须用全路径调用）。
- 本地仓库：`C:\Users\a\.m2\repository`。
- 工具调用注意：
  - Bash 工具的 PATH 是坏的（`ls`/`head`/`tail` 都 command not found），只能用 PowerShell 工具。
  - PowerShell 工具的 stdout 不回显，需要把结果写文件再用 Read 读；写文件时用
    `[System.IO.File]::WriteAllText(path, text, [System.Text.Encoding]::UTF8)`，
    用 `*>` / `*>>` 重定向会写成 UTF-16 导致 Read 报 "binary file"。
  - `Remove-Item` 删除文件有时不立即生效，删完要用 Glob 复核（Glob 结果最可靠）。

## 踩过的坑
- **Bean 名冲突**：两个不同包下的同名类（如 `aspect.AspectDemo2` 与 `aspect.priority.AspectDemo2`）
  在上组件扫描时默认 Bean 名都是 `aspectDemo2`，启动直接抛 `ConflictingBeanDefinitionException`。
  → 解决：3.4 那三个切面改名为 `PriorityAspectDemo1/2/3`（@Order 与编号一一对应）。
- **删源码后必须 `mvn clean`**：`target/classes` 里残留的旧 `.class` 仍会被组件扫描到，
  光删源文件不 clean 会继续报同样的冲突。验证命令：`mvn -B clean test`。

## 验证结果（2026-09-15）
- `mvn -B clean test` → BUILD SUCCESS，`contextLoads` 通过。
- 三个代理 main 全部跑通；CGLIB 代理类名显示为 `RealHouseSubject$$EnhancerByCGLIB$$...`（子类代理）。
- `SpringProxyMain` 中拿到的 Bean 类型为 `SpringHouseProxy$$SpringCGLIB$$0`，
  证实 Spring Boot 2.x+ 默认 `proxyTargetClass = true`（CGLIB）。
