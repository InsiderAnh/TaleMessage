# TaleMessage

[![](https://jitpack.io/v/InsiderAnh/TaleMessage.svg)](https://jitpack.io/#InsiderAnh/TaleMessage)

A MiniMessage-style utility for Hytale that allows easy creation of formatted messages using HTML-style tags.

## Features

- Simple MiniMessage-style syntax
- Support for named colors, hexadecimal, and RGB
- Formatting: bold, italic, underline, monospace
- Multi-point color gradients
- Nested tags
- Escape characters
- Standalone API (no server required)

## Installation

### Using JitPack


Add JitPack repository and the dependency to your project:

**Gradle:**
```gradle
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    compileOnly 'com.github.InsiderAnh:TaleMessage:1.0.0'
}
```

**Maven:**
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

> **Note:** Use `compileOnly` (Gradle) or `provided` scope (Maven) since Hytale server already provides the core Message classes.

### Manual Installation

Download the JAR from [Releases](https://github.com/InsiderAnh/TaleMessage/releases):

**Gradle:**
```gradle
dependencies {
    compileOnly files('libs/TaleMessage-1.0.0.jar')
}
```

**Maven:**
```xml
<dependency>
    <groupId>io.github.insideranh</groupId>
    <artifactId>talemessage</artifactId>
    <version>1.0.2</version>
    <scope>system</scope>
    <systemPath>${project.basedir}/libs/TaleMessage-1.0.0.jar</systemPath>
</dependency>
```

## Basic Usage

### Simple Colors

```java
import io.github.insideranh.talemessage.TaleMessage;
import com.hypixel.hytale.server.core.Message;

// Named colors
Message red = TaleMessage.parse("<red>Red text</red>");
Message green = TaleMessage.parse("<green>Green text</green>");
Message gold = TaleMessage.parse("<gold>Gold text</gold>");

// Hexadecimal colors
Message hex = TaleMessage.parse("<#FF5555>Custom color</#FF5555>");
Message hex2 = TaleMessage.parse("<FF5555>Also works without #</FF5555>");

// RGB colors (values 0-255 for R,G,B)
Message rgb = TaleMessage.parse("<255,85,85>Red in RGB</255,85,85>");
Message white = TaleMessage.parse("<255,255,255>White</255,255,255>");
Message black = TaleMessage.parse("<0,0,0>Black</0,0,0>");
Message custom = TaleMessage.parse("<128,64,200>Custom color</128,64,200>");
```

### Formatting

```java
// Bold
Message bold = TaleMessage.parse("<bold>Bold text</bold>");
Message b = TaleMessage.parse("<b>Also works</b>");

// Italic
Message italic = TaleMessage.parse("<italic>Italic text</italic>");
Message i = TaleMessage.parse("<i>Also works</i>");

// Underline
Message underline = TaleMessage.parse("<underline>Underlined text</underline>");
Message u = TaleMessage.parse("<u>Also works</u>");

// Monospace
Message mono = TaleMessage.parse("<monospace>Monospaced text</monospace>");
```

### Minecraft Color Codes

TaleMessage also supports legacy Minecraft color codes with `&`:

```java
// Color codes (&0-&f)
Message legacy = TaleMessage.parse("&aGreen text &c&lBold red");

// Format codes
Message format = TaleMessage.parse("&lBold &nUnderline &oItalic &rReset");

// Mix with MiniMessage tags
Message mixed = TaleMessage.parse(
    "&a<bold>Welcome!</bold> " +
    "<gradient:aqua:blue>Enjoy your stay</gradient> &7(v1.0.0)"
);
```

**Supported codes:**
- Colors: `&0-&f` (black, dark blue, dark green, etc.)
- Bold: `&l`
- Italic: `&o`
- Underline: `&n`
- Reset: `&r`

### Nested Tags

```java
// Combine color and formatting
Message combined = TaleMessage.parse("<red><bold>Critical error!</bold></red>");

// Multiple levels
Message complex = TaleMessage.parse(
    "<blue><bold>Server:</bold> <white>Online</white></blue>"
);
```

### Gradients

```java
// Simple gradient (2 colors)
Message gradient = TaleMessage.parse("<gradient:red:blue>Gradient text</gradient>");

// Multi-color gradient
Message rainbow = TaleMessage.parse(
    "<gradient:red:yellow:green:blue:purple>Rainbow!</gradient>"
);

// Gradient with formatting
Message gradBold = TaleMessage.parse(
    "<gradient:blue:purple><bold>Bold gradient</bold></gradient>"
);
```

### Clickable Links

```java
// Simple link
Message link = TaleMessage.parse("<click:https://facebook.com>Click to open Facebook</click>");

// Link with colors
Message coloredLink = TaleMessage.parse(
    "<aqua><click:https://github.com>Visit our GitHub</click></aqua>"
);

// Link with formatting
Message styledLink = TaleMessage.parse(
    "<gold><bold><click:https://example.com>Click here!</click></bold></gold>"
);

// Complex example
Message info = TaleMessage.parse(
    "<green>World seed: <click:https://example.com/seed><gold>12345</gold></click></green>"
);
```

### Helper Methods

```java
// Quick colored message
Message msg = TaleMessage.colored("aqua", "Quick message");

// Quick gradient
Message grad = TaleMessage.gradient("Text", "red", "yellow", "green");

// Strip tags
String plain = TaleMessage.strip("<red>Hello <bold>World</bold></red>");
// Result: "Hello World"

// Escape special characters
String escaped = TaleMessage.escape("<html>");
// Result: "\<html\>"
```

## Supported Colors

### Minecraft Colors

| Name | Color | Hex |
|------|-------|-----|
| `black` | Black | #000000 |
| `dark_blue` | Dark Blue | #0000AA |
| `dark_green` | Dark Green | #00AA00 |
| `dark_aqua` | Dark Aqua | #00AAAA |
| `dark_red` | Dark Red | #AA0000 |
| `dark_purple` | Dark Purple | #AA00AA |
| `gold` | Gold | #FFAA00 |
| `gray` | Gray | #AAAAAA |
| `dark_gray` | Dark Gray | #555555 |
| `blue` | Blue | #5555FF |
| `green` | Green | #55FF55 |
| `aqua` | Aqua | #55FFFF |
| `red` | Red | #FF5555 |
| `light_purple` | Light Purple | #FF55FF |
| `yellow` | Yellow | #FFFF55 |
| `white` | White | #FFFFFF |

### Additional Colors

- `orange`, `pink`, `purple`, `cyan`, `magenta`, `lime`, `brown`

### Hexadecimal Colors

Use any hexadecimal color with `#RRGGBB` or `#RRGGBBAA`:

```java
Message custom = TaleMessage.parse("<#FF5733>Custom color</#FF5733>");
Message withoutHash = TaleMessage.parse("<FF5733>Also works without #</FF5733>");
```

### RGB Colors

Use RGB values directly with `<R,G,B>` format where each value is between 0-255:

```java
// Basic colors
Message red = TaleMessage.parse("<255,0,0>Pure red</255,0,0>");
Message green = TaleMessage.parse("<0,255,0>Pure green</0,255,0>");
Message blue = TaleMessage.parse("<0,0,255>Pure blue</0,0,255>");

// White and black
Message white = TaleMessage.parse("<255,255,255>White</255,255,255>");
Message black = TaleMessage.parse("<0,0,0>Black</0,0,0>");

// Grays
Message gray = TaleMessage.parse("<128,128,128>Gray</128,128,128>");
Message lightGray = TaleMessage.parse("<192,192,192>Light gray</192,192,192>");

// Custom colors
Message orange = TaleMessage.parse("<255,165,0>Orange</255,165,0>");
Message purple = TaleMessage.parse("<128,0,128>Purple</128,0,128>");
Message custom = TaleMessage.parse("<87,142,200>Custom color</87,142,200>");

// Also works with spaces
Message withSpaces = TaleMessage.parse("<255, 85, 85>With spaces</255, 85, 85>");
```

## Advanced Examples

### Error Message

```java
Message error = TaleMessage.parse(
    "<red><bold>❌ Error:</bold></red> " +
    "<white>Could not load file <gold>config.yml</gold></white>"
);

// With RGB
Message errorRGB = TaleMessage.parse(
    "<255,0,0><bold>❌ Error:</bold></255,0,0> " +
    "<white>File not found</white>"
);
```

### Success Message

```java
Message success = TaleMessage.parse(
    "<green><bold>✓ Success:</bold></green> " +
    "<white>You earned <gold><bold>100</bold></gold> points</white>"
);

// With mixed RGB and hex
Message successMixed = TaleMessage.parse(
    "<0,255,0><bold>✓ Success:</bold></0,255,0> " +
    "<255,255,255>You earned <#FFD700><bold>100</bold></#FFD700> points</255,255,255>"
);
```

### Server Info

```java
Message info = TaleMessage.parse(
    "<gradient:blue:cyan><bold>[ SERVER INFO ]</bold></gradient>\n" +
    "<white>Players: <green>50</green>/<green>100</green></white>\n" +
    "<white>TPS: <gold>20.0</gold></white>"
);
```

### Welcome Message

```java
Message welcome = TaleMessage.parse(
    "<gradient:gold:yellow>═══════════════════</gradient>\n" +
    "<green><bold>Welcome to the server!</bold></green>\n" +
    "<white>Enjoy your stay, <aqua>{{player}}</aqua></white>\n" +
    "<gradient:gold:yellow>═══════════════════</gradient>"
);
```

### Achievement

```java
Message achievement = TaleMessage.parse(
    "<gradient:gold:yellow>★★★</gradient> " +
    "<bold><yellow>Achievement Unlocked!</yellow></bold> " +
    "<gradient:gold:yellow>★★★</gradient>\n" +
    "<white>You earned: <green><bold>First Victory</bold></green></white>"
);
```

### Formatted Chat

```java
Message chat = TaleMessage.parse(
    "<dark_gray>[</dark_gray><gradient:red:dark_red>Admin</gradient><dark_gray>]</dark_gray> " +
    "<red><bold>{{name}}</bold></red><dark_gray>:</dark_gray> " +
    "<white>{{message}}</white>"
);
```

### Server Announcement with Link

```java
Message announcement = TaleMessage.parse(
    "<gradient:blue:aqua><bold>━━━━━━━━━━━━━━━━━━━━━━</bold></gradient>\n" +
    "<yellow><bold>Server Update Available!</bold></yellow>\n" +
    "<white>Version 2.0 is now available.</white>\n" +
    "<gray>Read more: <click:https://example.com/changelog><aqua><u>Click here</u></aqua></click></gray>\n" +
    "<gradient:blue:aqua><bold>━━━━━━━━━━━━━━━━━━━━━━</bold></gradient>"
);
```

### World Info with Seed Link

```java
Message worldInfo = TaleMessage.parse(
    "<green><bold>World Information</bold></green>\n" +
    "<gray>• Name: <white>Survival World</white></gray>\n" +
    "<gray>• Seed: <click:https://example.com/seed/12345><gold>12345</gold></click> <dark_gray>(Click to copy)</dark_gray></gray>\n" +
    "<gray>• Difficulty: <red>Hard</red></gray>"
);
```

## API Reference

### `TaleMessage.parse(String)`

Parses a MiniMessage formatted string and returns a Hytale `Message` object.

**Parameters:**
- `input` - MiniMessage formatted string

**Returns:** `Message` - Hytale Message object with formatting applied

### `TaleMessage.strip(String)`

Removes all formatting tags, leaving only plain text.

**Parameters:**
- `input` - MiniMessage formatted string

**Returns:** `String` - Plain text without formatting

### `TaleMessage.escape(String)`

Escapes `<` and `>` characters so they are not interpreted as tags.

**Parameters:**
- `input` - String to escape

**Returns:** `String` - String with escaped characters

### `TaleMessage.colored(String, String)`

Creates a simple colored message.

**Parameters:**
- `color` - Color name or hex code
- `text` - Text content

**Returns:** `Message` - Colored message

**Example:**
```java
Message msg = TaleMessage.colored("red", "Error occurred!");
Message hex = TaleMessage.colored("#FF5555", "Custom color");
```

### `TaleMessage.gradient(String, String...)`

Creates a gradient message.

**Parameters:**
- `text` - Text content
- `colors` - Gradient colors (minimum 2)

**Returns:** `Message` - Gradient message

**Example:**
```java
Message gradient = TaleMessage.gradient("Rainbow Text", "red", "yellow", "green", "blue");
```

## Build

```bash
./gradlew build
```

The JAR will be generated in `build/libs/TaleMessage-1.0.0.jar`

## License

This project is licensed under terms that allow use in Hytale projects.

## Author

InsiderAnh - [GitHub](https://github.com/InsiderAnh)

## Contributing

Contributions are welcome. Please open an issue or pull request.

---

**Note:** This is an independent project not officially affiliated with Hytale or Hypixel Studios.

