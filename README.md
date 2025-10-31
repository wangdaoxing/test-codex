# Identity Service

基于 Spring Boot 3、JDK 21 与 Spring Data JDBC 的领域驱动身份管理示例，实现用户账号、身份、组织、角色与权限等核心模型。项目使用 PostgreSQL 作为数据存储，并通过 Flyway 管理数据库结构。

## 主要功能

- 组织（Organization）聚合：支持创建、重命名、调整编码与描述。
- 角色（Role）聚合：支持为组织创建角色、授权/回收权限。
- 用户账号（UserAccount）聚合：绑定组织、身份信息，支持邮箱修改、角色授权与状态管理。
- 领域服务与应用服务：通过 `application` 层暴露注册用户、分配角色等用例。
- 基础设施层：使用 Spring Data JDBC 的仓储实现，并提供自定义类型转换。

## 构建

```bash
mvn -DskipTests package
```

> 由于示例环境无法访问 Maven Central，如需编译请确保具备外部网络或使用私有制品库。

## 数据库

默认配置指向本地 PostgreSQL（`jdbc:postgresql://localhost:5432/identity_service`），请在 `application.yml` 中根据需要调整连接信息。
