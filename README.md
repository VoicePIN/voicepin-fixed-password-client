# Getting Started

[Getting started with VoicePIN Server](https://github.com/VoicePIN/voicepin-fixed-password-wiki/wiki)

[Example of usage of this client](https://github.com/VoicePIN/voicepin-fixed-password-client/blob/master/src/main/java/com/voicepin/fixedpassword/client/example/RestClientApp.java)

# Gradle dependency

Add VoicePIN.com Maven repository to `build.gradle`:

    repositories {
        maven { url 'https://nexus.voicepin.com/repository/maven-releases'}
    }

Add voicepin-fixed-password-client dependency:

    dependencies {
        compile 'com.voicepin.fixedpassword:voicepin-fixed-password-client:1.0.0'
    }

# Maven dependency

Add VoicePIN.com Maven repository to `pom.xml`:

    <project>
        ...
        <repositories>
            <repository>
                <id>VoicePIN.com</id>
                <url>https://nexus.voicepin.com/repository/maven-releases</url>
            </repository>
        </repositories>
    </project>

Add voicepin-fixed-password-client dependency:

    <project>
        ...
        <dependencies>
            ...
            <dependency>
                <groupId>com.voicepin.fixedpassword</groupId>
                <artifactId>voicepin-fixed-password-client</artifactId>
                <version>1.0.0</version>
            </dependency>
        </dependencies>
    </project>
