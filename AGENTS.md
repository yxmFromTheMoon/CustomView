# AGENTS.md

## 项目概述

这是一个用于学习和演示 Android 自定义 View 的多模块项目，代码同时使用 Kotlin 和 Java。

## 模块说明

- `app`：主应用，包含自定义 View 和相关演示页面。
- `baseLibrary`：基础组件及公共依赖。
- `frameLibrary`：业务框架组件，依赖 `baseLibrary`。
- `plugindemo`：独立的插件实验应用。

模块依赖关系：

`app -> frameLibrary -> baseLibrary`

## 技术环境

- Android Gradle Plugin：7.4.2
- Kotlin：1.7.22
- compileSdk / targetSdk：31
- minSdk：19
- Java / JVM：1.8
- 构建脚本使用 Groovy Gradle DSL。

除非任务明确要求，否则不要随意升级 Gradle、Kotlin、SDK 或第三方依赖版本。

## 开发规范

- 优先保持现有代码风格，不进行与任务无关的大规模重构。
- Kotlin 和 Java 均可使用；修改现有文件时保持其原有语言。
- 新增自定义 View 时，正确处理测量、布局、绘制和触摸事件。
- 自定义 View 不要在 `onDraw()` 中频繁创建对象，避免不必要的内存分配。
- 尺寸使用 `dp`，文字大小使用 `sp`，颜色和文案优先放入资源文件。
- 注意 View 生命周期、Context 引用和资源回收，避免内存泄漏。
- 保持对 Android 5.0（API 19）的兼容性。
- 不要提交 `local.properties`、构建产物或本机环境配置。
- 不要修改与当前任务无关的文件。

## 构建与验证

在 Windows 环境中使用 Gradle Wrapper：

```powershell
.\gradlew.bat :app:assembleDebug
.\gradlew.bat :app:testDebugUnitTest
```

修改其他模块时，可以运行：

```powershell
.\gradlew.bat :baseLibrary:testDebugUnitTest
.\gradlew.bat :frameLibrary:testDebugUnitTest
.\gradlew.bat :plugindemo:assembleDebug
```

涉及 UI、自定义 View、动画或触摸交互的修改，需要说明建议进行的真机或模拟器验证步骤。

## 完成任务时

- 简要说明修改了哪些内容。
- 列出实际执行过的构建或测试命令。
- 如果没有执行测试，明确说明原因。
- 提醒可能存在的兼容性、性能或 UI 风险。
