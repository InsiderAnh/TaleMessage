# TaleMessage Usage Examples

## Basic Examples

```java
import io.github.insideranh.talemessage.TaleMessage;
import com.hypixel.hytale.server.core.Message;

// 1. Simple colored text
Message red = TaleMessage.parse("<red>This is red text</red>");

// 2. Bold text
Message bold = TaleMessage.parse("<bold>Bold text</bold>");

// 3. Color and format combination
Message error = TaleMessage.parse("<red><bold>Error!</bold></red>");

// 4. Custom hexadecimal color
Message hex = TaleMessage.parse("<#FF5555>Custom hex color</#FF5555>");
Message hex2 = TaleMessage.parse("<FF5555>Hex without #</FF5555>");

// 5. RGB colors (values 0-255 for R,G,B)
Message rgb = TaleMessage.parse("<255,85,85>Red in RGB</255,85,85>");
Message white = TaleMessage.parse("<255,255,255>White</255,255,255>");
Message black = TaleMessage.parse("<0,0,0>Black</0,0,0>");
Message custom = TaleMessage.parse("<128,64,200>Custom RGB color</128,64,200>");

// 6. Multiple colors in a message
Message multi = TaleMessage.parse("<green>Success!</green> You earned <gold>100</gold> points");

// 7. Mixed color formats
Message mixed = TaleMessage.parse(
    "<red>Named</red> <#FF5555>Hex</#FF5555> <255,85,85>RGB</255,85,85>"
);

// 8. Simple gradient
Message gradient = TaleMessage.parse("<gradient:red:blue>Gradient text</gradient>");

// 9. Multi-color gradient (rainbow)
Message rainbow = TaleMessage.parse("<gradient:red:yellow:green:blue:purple>Rainbow!</gradient>");

// 10. Italic and underline
Message italic = TaleMessage.parse("<italic>Italic</italic> and <underline>underlined</underline>");

// 11. Monospace (for code)
Message code = TaleMessage.parse("<monospace>code.example()</monospace>");

// 12. Complex nested formatting
Message complex = TaleMessage.parse(
    "<blue><bold>Server:</bold></blue> " +
    "<white>Players online: <green>50</green>/<green>100</green></white>"
);
```

## Helper Methods

```java
// Quick colored message
Message quick = TaleMessage.colored("aqua", "Quick message");

// Quick gradient
Message grad = TaleMessage.gradient("Gradient text", "red", "yellow", "green");

// Strip tags (get plain text)
String plain = TaleMessage.strip("<red>Hello <bold>World</bold></red>");
// Result: "Hello World"

// Escape special characters
String escaped = TaleMessage.escape("<tag>");
// Result: "\<tag\>"
```

## Server Message Examples

### Welcome Message
```java
Message welcome = TaleMessage.parse(
    "<gradient:gold:yellow>═══════════════════</gradient>\n" +
    "<green><bold>Welcome to the server!</bold></green>\n" +
    "<white>Enjoy your stay, <aqua>{{player}}</aqua></white>\n" +
    "<gradient:gold:yellow>═══════════════════</gradient>"
);
```

### Error Message
```java
Message error = TaleMessage.parse(
    "<red><bold>❌ Error:</bold></red> " +
    "<white>You don't have permission to use this command.</white>"
);

// With RGB colors
Message errorRGB = TaleMessage.parse(
    "<255,0,0><bold>❌ Error:</bold></255,0,0> " +
    "<255,255,255>You don't have permission.</255,255,255>"
);
```

### Success Message
```java
Message success = TaleMessage.parse(
    "<green><bold>✓ Success:</bold></green> " +
    "<white>You bought <gold>Diamond Sword</gold> for <gold>100</gold> coins.</white>"
);

// With mixed RGB and hex
Message successMixed = TaleMessage.parse(
    "<0,255,0><bold>✓ Success:</bold></0,255,0> " +
    "<white>You bought <#FFD700>Diamond Sword</#FFD700> for <255,215,0>100</255,215,0> coins.</white>"
);
```

### Server Information
```java
Message info = TaleMessage.parse(
    "<gradient:blue:cyan><bold>[ SERVER INFO ]</bold></gradient>\n" +
    "<white>Players: <green>50</green>/<green>100</green></white>\n" +
    "<white>TPS: <gold>20.0</gold></white>\n" +
    "<white>Version: <aqua>1.0.0</aqua></white>"
);
```

### Announcement
```java
Message announcement = TaleMessage.parse(
    "<gradient:yellow:gold><bold>【 ANNOUNCEMENT 】</bold></gradient>\n" +
    "<white>The <light_purple><bold>Clan War</bold></light_purple> event " +
    "will start in <red>5 minutes</red>!</white>"
);
```

### Achievement Message
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

## Available Colors

### Minecraft Colors
- `black`, `dark_blue`, `dark_green`, `dark_aqua`
- `dark_red`, `dark_purple`, `gold`, `gray`
- `dark_gray`, `blue`, `green`, `aqua`
- `red`, `light_purple`, `yellow`, `white`

### Additional Colors
- `orange`, `pink`, `purple`, `cyan`
- `magenta`, `lime`, `brown`

### Hexadecimal Colors
Use `#RRGGBB` or `RRGGBB` format:
```java
Message hex1 = TaleMessage.parse("<#FF5733>With #</#FF5733>");
Message hex2 = TaleMessage.parse("<FF5733>Without #</FF5733>");
```

### RGB Colors
Use `R,G,B` format where each value is between 0-255:
```java
// Basic colors
Message red = TaleMessage.parse("<255,0,0>Red</255,0,0>");
Message green = TaleMessage.parse("<0,255,0>Green</0,255,0>");
Message blue = TaleMessage.parse("<0,0,255>Blue</0,0,255>");

// White and black
Message white = TaleMessage.parse("<255,255,255>White</255,255,255>");
Message black = TaleMessage.parse("<0,0,0>Black</0,0,0>");

// Any custom color
Message custom1 = TaleMessage.parse("<128,64,200>Custom purple</128,64,200>");
Message custom2 = TaleMessage.parse("<255,165,0>Orange</255,165,0>");
Message custom3 = TaleMessage.parse("<87, 142, 200>With spaces also works</87, 142, 200>");
```

### Formatting
- `<bold>` or `<b>` - Bold
- `<italic>`, `<i>`, or `<em>` - Italic
- `<underline>` or `<u>` - Underline
- `<monospace>` or `<mono>` - Monospaced

## Tips

1. **Nested tags**: You can nest multiple tags
   ```java
   "<red><bold><italic>Triple formatting!</italic></bold></red>"
   ```

2. **Tag closing**: You can use specific closing `</red>` or generic `</>`
   ```java
   "<red>Text</red>" or "<red>Text</>"
   ```

3. **Escape**: Use `\<` and `\>` for literal characters
   ```java
   "Code: \<html\>"
   ```

4. **Gradients**: Minimum 2 colors, unlimited maximum
   ```java
   "<gradient:color1:color2:color3:...>text</gradient>"
   ```

5. **Color formats**: You can use any of these formats
   ```java
   "<red>Named</red>"                          // Named color
   "<#FF5555>Hex with #</#FF5555>"            // Hex with #
   "<FF5555>Hex without #</FF5555>"           // Hex without #
   "<255,85,85>RGB</255,85,85>"               // RGB with values 0-255
   "<255, 85, 85>RGB with spaces</255, 85, 85>"  // RGB with spaces
   ```

6. **Format mixing**: You can mix different formats in the same message
   ```java
   "<red>Named</red> <#00FF00>Hex</#00FF00> <0,0,255>RGB</0,0,255>"
   ```

7. **Gradients with RGB**: Gradients accept any format
   ```java
   "<gradient:255,0,0:0,0,255>RGB gradient</gradient>"
   "<gradient:red:#00FF00:0,0,255>Mixed formats</gradient>"
   ```

