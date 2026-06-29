# orange-io

`orange-io` 是一个 Kotlin Multiplatform 的 `io` 基础库，坐标如下：

- `groupId`: `com.orange`
- `artifactId`: `io`
- `version`: `1.0.0`

## JitPack 使用方式

仓库地址：

- [https://github.com/addskya/orange-io](https://github.com/addskya/orange-io)

### 1. 添加仓库

Gradle Kotlin DSL:

```kotlin
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
}
```

### 2. 添加依赖

如果使用 Git tag `v1.0.0`，则依赖写法为：

```kotlin
implementation("com.github.addskya:orange-io:1.0.0")
```

如果使用 commit hash，也可以写成：

```kotlin
implementation("com.github.addskya:orange-io:<commit>")
```

## 发布 GitHub Packages

当前仓库仍保留 GitHub Packages 发布能力，但仅在提供以下凭据时启用：

- `gpr.user`
- `gpr.key`

或环境变量：

- `GITHUB_ACTOR`
- `GITHUB_TOKEN`

执行命令：

```bash
./gradlew :io:publish
```

## 本地验证

```bash
./gradlew :io:build
```
