# Legacy Bookstore Application

A Java web application built with Struts 1.x, Hibernate 3.6, and MySQL 5.7.

## Quick Start (Dev Container)

```bash
code .
# Ctrl+Shift+P → "Dev Containers: Reopen in Container"
ant clean build
```

### Codespaces Notes

- The app now reads database connection settings from `DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USER`, and `DB_PASSWORD`. Defaults stay compatible with the bundled Docker services.
- In Codespaces the dev container cannot resolve the MySQL service name, so `DB_HOST` is set to `127.0.0.1` in `.devcontainer/devcontainer.json`. Keep the MySQL service running (started by the devcontainer init command) and use the forwarded port 3306.

## Quick Start (Without Dev Container)

### Prerequisites

- Apache Ant 1.6.5+
- Apache Tomcat 6.x–8.x
- MySQL 5.7

### Install JDK 1.5

```bash
# Download JDK 1.5.0_22 from Archive.org
wget -O jdk-1_5_0_22-linux-amd64-rpm.bin \
    "https://archive.org/download/Java_5_update_22/jdk-1_5_0_22-linux-amd64-rpm.bin"

# Extract RPMs from the installer
chmod +x jdk-1_5_0_22-linux-amd64-rpm.bin
yes | ./jdk-1_5_0_22-linux-amd64-rpm.bin

# Extract JDK from RPMs (requires rpm2cpio and cpio)
for rpm in *.rpm; do rpm2cpio "$rpm" | cpio -idmv; done
sudo mv usr/java/jdk1.5.0_22 /usr/java/

# Unpack compressed JARs
/usr/java/jdk1.5.0_22/bin/unpack200 /usr/java/jdk1.5.0_22/jre/lib/rt.pack /usr/java/jdk1.5.0_22/jre/lib/rt.jar
/usr/java/jdk1.5.0_22/bin/unpack200 /usr/java/jdk1.5.0_22/jre/lib/jsse.pack /usr/java/jdk1.5.0_22/jre/lib/jsse.jar
/usr/java/jdk1.5.0_22/bin/unpack200 /usr/java/jdk1.5.0_22/jre/lib/charsets.pack /usr/java/jdk1.5.0_22/jre/lib/charsets.jar
/usr/java/jdk1.5.0_22/bin/unpack200 /usr/java/jdk1.5.0_22/jre/lib/ext/localedata.pack /usr/java/jdk1.5.0_22/jre/lib/ext/localedata.jar
/usr/java/jdk1.5.0_22/bin/unpack200 /usr/java/jdk1.5.0_22/lib/tools.pack /usr/java/jdk1.5.0_22/lib/tools.jar

# Set environment variables
export JAVA_HOME=/usr/java/jdk1.5.0_22
export PATH=$JAVA_HOME/bin:$PATH
```

### Setup and Run

```bash
# 1. Start MySQL and create the database
mysql -u root -p -e "CREATE DATABASE legacy_db;"
mysql -u root -p -e "CREATE USER 'legacy_user'@'localhost' IDENTIFIED BY 'legacy_pass';"
mysql -u root -p -e "GRANT ALL ON legacy_db.* TO 'legacy_user'@'localhost';"
mysql -u root -p legacy_db < config/mysql/01-create-tables.sql
mysql -u root -p legacy_db < config/mysql/02-seed-data.sql

# 2. Build the WAR
ant clean build

# 3. Deploy to Tomcat
cp dist/legacy-app.war $CATALINA_HOME/webapps/

# 4. Access
# http://localhost:8080/legacy-app/
```

## Database

```
Host: localhost:3306
Database: legacy_db
User: legacy_user
Password: legacy_pass
```

## Licence

Released under the [MIT License](LICENSE).
