# JitPack Installation Guide

## Quick Start

### Gradle

```gradle
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    compileOnly 'com.github.InsiderAnh:TaleMessage:1.0.0'
}
```

### Maven

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.github.InsiderAnh</groupId>
        <artifactId>TaleMessage</artifactId>
        <version>1.0.0</version>
        <scope>provided</scope>
    </dependency>
</dependencies>
```

## Version Options

- **Release**: `1.0.0`
- **Commit**: `de050de` (first 7 chars of commit hash)
- **Branch**: `main-SNAPSHOT`

Examples:
```gradle
compileOnly 'com.github.InsiderAnh:TaleMessage:1.0.0'        // Release
compileOnly 'com.github.InsiderAnh:TaleMessage:main-SNAPSHOT' // Latest
compileOnly 'com.github.InsiderAnh:TaleMessage:de050de'      // Specific commit
```

## Troubleshooting

**Dependency not downloading?**
- Verify JitPack repository is added
- Clear Gradle cache: `./gradlew clean --refresh-dependencies`
- Check build status: https://jitpack.io/#InsiderAnh/TaleMessage

**Build takes time?**
JitPack builds on first request. New releases may take a few minutes.


