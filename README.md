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

## 将代码强制推送到 GitHub

如果你已经在本地（或本容器）完成代码修改，希望强制覆盖远程仓库，可以按照以下步骤操作：

1. **确认所有改动已提交**
   ```bash
   git status
   ```
   如果仍有未提交文件，使用 `git add . && git commit -m "your message"` 创建提交。

2. **查看当前分支**
   ```bash
   git branch --show-current
   ```
   假设输出为 `main`，后续命令将以该分支为例。

3. **添加或确认远程仓库**
   ```bash
   git remote -v
   ```
   若没有远程地址，可执行 `git remote add origin https://github.com/<your-account>/<repo>.git` 进行关联。

4. **强制推送覆盖远程**
   ```bash
   git push --force origin main
   ```
   该命令会用当前分支的历史覆盖远程同名分支，请务必确认这样做不会影响协作者。

> 提示：如果你只想将当前工作目录内容上传为一个新分支，也可以执行 `git push --force origin main:your-branch-name`。

## 数据库

默认配置指向本地 PostgreSQL（`jdbc:postgresql://localhost:5432/identity_service`），请在 `application.yml` 中根据需要调整连接信息。
