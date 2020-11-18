1. 
```cmd
> 选择第一个会生成公钥和私钥
gpg --gen-key

 
gpg: checking the trustdb
gpg: 3 marginal(s) needed, 1 complete(s) needed, PGP trust model
gpg: depth: 0  valid:   2  signed:   0  trust: 0-, 0q, 0n, 0m, 0f, 2u
pub   2048R/B714D989 2020-11-18
      Key fingerprint = B103 B270 9B42 D04F 12C1  26FB 7495 280B B714 D989
uid                  zhangll <524631266@qq.com>
sub   2048R/246857A0 2020-11-18


gpg --list-keys

// 如下这一步不需要,只要在本地生成gpg就可以了
gpg --keyserver hkp://subkeys.pgp.net --send-keys xxxxxxx
用如下命令确认有没有公钥
gpg --keyserver hkp://keyserver.ubuntu.com:11371 --recv-keys  xxxxxxx
``` 

2. settings
```xml
<profiles>
    <profile>
      <id>gpg</id>
      <properties>
          <!-- 如果电脑安装的是gpg2，不存在gpg命令，所以需要指定执行gpg2，否则会报错 -->
          <gpg.executable>gpg</gpg.executable>
          <gpg.passphrase>xxxx</gpg.passphrase>
      </properties>
    </profile>
</profiles>

<activeProfiles>
        <activeProfile>gpg</activeProfile>
    </activeProfiles>
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

4. 会出现

```xml
  * No public key: Key with id: (697b68e76252cf01) was not able to be located on &lt;a href=http://pool.sks-keyservers.net:11371/&gt;http://pool.sks-keyservers.net:11371/&lt;/a&gt;. Upload your public key and try the operation again.
[ERROR]     * No public key: Key with id: (697b68e76252cf01) was not able to be located on &lt;a href=http://keyserver.ubuntu.com:11371/&gt;http://keyserver.ubuntu.com:11371/&lt;/a&gt;. Upload your public key and try the operation again.
[ERROR]     * No public key: Key with id: (697b68e76252cf01) was not able to be located on &lt;a href=http://keys.openpgp.org:11371/&gt;http://keys.openpgp.org:11371/&lt;/a&gt;. Upload your public key and try the operation again.


```

从上面我们发现有三个 key-servers 是 Sonatype公司要用到的，Sonatype公司会在上面任意一个key-servers上进行搜索公钥，具体的key-servers 为： 
其实最好都要试一边.有些时候网络或服务问题,没有成功即使无响应
```xml
http://keys.openpgp.org:11371 
http://keyserver.ubuntu.com:11371 
http://pool.sks-keyservers.net:11371
```
正确的只是 使用hkp协议加端口在任意一个服务中注册
```cmd
gpg --keyserver hkp://keyserver.ubuntu.com:11371  --send-keys 3B8360078551E62C4E3F258BA08C67DDE0285045
```
3B8360078551E62C4E3F258BA08C67DDE0285045 号码哪里看

```xml
 gpg --list-key zhangll

 gpg --edit-key zhangll
    gpg> fpr
    pub   2048R/E0285045 2020-11-18 zhangll <524631266@qq.com>
     Primary key fingerprint: 3B83 6007 8551 E62C 4E3F  258B A08C 67DD E028 5045
     输入 fpr
```