# auth-server

-------------Command to create .jks file-------------

keytool -genkeypair -alias auth-server-default -keyalg RSA -keypass auth-server-default-pass -keystore auth-server-default.jks -storepass auth-server-default-pass

The command will generate a file called auth-server-default.jks which contains our keys -the Public and Private keys.
Also make sure keypass and storepass are the same.
