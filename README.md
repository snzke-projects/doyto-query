DoytoQuery --- A CRUD Framework in Java
---
[![License](http://img.shields.io/:license-apache-brightgreen.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)
[![Sonar Stats](https://sonarcloud.io/api/project_badges/measure?project=win.doyto%3Adoyto-query&metric=alert_status)](https://sonarcloud.io/dashboard?id=win.doyto%3Adoyto-query)
[![Coverage Status](https://sonarcloud.io/api/project_badges/measure?project=win.doyto%3Adoyto-query&metric=coverage)](https://sonarcloud.io/component_measures?id=win.doyto%3Adoyto-query&metric=coverage)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/win.doyto/doyto-query/badge.svg)](https://maven-badges.herokuapp.com/maven-central/win.doyto/doyto-query/)

### Core idea
SQL should and could be generated by a query object (not an entity object).

### Target
Do single-table query without writing one line SQL.

### Features
- Generate SQL by a query object
- Support cache
- Support extension via EntityAspect
- Support crud for dynamic table name

### Usage
Fields in query object with different suffix lead to different SQL clauses which combined by AND.

- equals
```
username = "f0rb"
leads to 
'WHERE username = #{username}' (for mybatis)
or
'WHERE username = ?' with an argument list ["f0rb"]
```

- Like
```
usernameLike = "f0rb"
leads to 
'WHERE username LIKE #{usernameLike}'
or
'WHERE username LIKE ?' with an argument list ["f0rb"]
```

- Or
```
usernameOrEmailLike = "f0rb"
leads to 
'WHERE (username LIKE #{usernameOrEmailLike} OR username LIKE #{usernameOrEmailLike})'
or
'WHERE (username LIKE ? OR email LIKE ?' with an argument list ["f0rb", "f0rb"]
```

- In
```
idIn = [1, 2, 3]
leads to 
'WHERE id IN (#{idIn[0], #{idIn[1], #{idIn[2]})'
or
'WHERE id IN (?, ?, ?)' with an argument list [1, 2, 3]
```

- Lt
```
idLt = 5
leads to 
'WHERE id < #{idLt}'
or
'WHERE id < ?' with an argument list [5]
```

- NotIn, Le, Gt, Ge, etc.

- Multi fields
```
idLt = 5, usernameLike = "test"
leads to 
'WHERE id < #{idLt} AND username LIKE #{usernameLike}'
or
'WHERE id < ? AND username LIKE ?' with an argument list [5, "test"]
```

- Paging
```
pageNumber = 2, pageSize = 10
leads to
LIMIT 10 OFFSET 20
```

- Sorting
```
pageNumber = 2, pageSize = 10, sort = "id,desc;createTime,asc"
leads to
ORDER BY id desc, createTime asc LIMIT 10 OFFSET 20
```