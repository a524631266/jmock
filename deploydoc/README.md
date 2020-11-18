1. 
```cmd
gpg --gen-key

gpg --list-keys

gpg --keyserver hkp://subkeys.pgp.net --send-keys xxxxxxx
``` 

2. settings
```xml
<profiles>
    <profile>
      <id>gpg</id>
      <properties>
          <!-- 由于我电脑安装的是gpg2，不存在gpg命令，所以需要指定执行gpg2，否则会报错 -->
          <gpg.executable>gpg</gpg.executable>
          <gpg.passphrase>xxxx</gpg.passphrase>
      </properties>
    </profile>
</profiles>
```

```xml
<server>
<id>sonatype-nexus-snapshots</id>
  <username>xxx</username>
  <password>xxxx</password>
</server>
<server>
  <id>sonatype-nexus-staging</id>
  <username>xxx</username>
  <password>xxxx</password>
</server>
```

3. mvn
```cmd
$ mvn clean javadoc:jar deploy -P release
```