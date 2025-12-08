# Keycloak Firebase Scrypt - Docker MySQL Custom Configuration

Add any custom MySQL configuration files to this directory.

* e.g. add a file named `custom-mysql.cnf` with the following content:
```
[mysqld]
server_id=1234
enforce_gtid_consistency=ON
gtid_mode=ON
```