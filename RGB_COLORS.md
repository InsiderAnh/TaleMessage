# RGB Color Support

This document demonstrates RGB color support in TaleMessage.

## Supported Formats

### 1. Named Colors
```java
Message msg = TaleMessage.parse("<red>Red</red>");
```

### 2. Hexadecimal Colors
```java
// With #
Message msg1 = TaleMessage.parse("<#FF5555>Hex color</#FF5555>");

// Without #
Message msg2 = TaleMessage.parse("<FF5555>Hex color</FF5555>");
```

### 3. RGB Colors ✨ NEW
```java
// Basic format: <R,G,B>
Message red = TaleMessage.parse("<255,0,0>Pure red</255,0,0>");
Message green = TaleMessage.parse("<0,255,0>Pure green</0,255,0>");
Message blue = TaleMessage.parse("<0,0,255>Pure blue</0,0,255>");

// White and black
Message white = TaleMessage.parse("<255,255,255>White</255,255,255>");
Message black = TaleMessage.parse("<0,0,0>Black</0,0,0>");

// With spaces (also valid)
Message custom = TaleMessage.parse("<128, 64, 200>Purple</128, 64, 200>");

// Custom colors
Message orange = TaleMessage.parse("<255,165,0>Orange</255,165,0>");
Message pink = TaleMessage.parse("<255,192,203>Pink</255,192,203>");
Message brown = TaleMessage.parse("<139,69,19>Brown</139,69,19>");
```

## Mixing Formats

You can mix all three formats in the same message:

```java
Message mixed = TaleMessage.parse(
    "<red>Named</red> " +
    "<#00FF00>Hex</#00FF00> " +
    "<0,0,255>RGB</0,0,255>"
);
```

## Gradients with RGB

Gradients also accept RGB colors:

```java
// RGB only
Message gradient1 = TaleMessage.parse(
    "<gradient:255,0,0:0,0,255>Red to Blue</gradient>"
);

// Mixed
Message gradient2 = TaleMessage.parse(
    "<gradient:red:#00FF00:0,0,255>Named, Hex and RGB</gradient>"
);

// Full rainbow with RGB
Message rainbow = TaleMessage.parse(
    "<gradient:255,0,0:255,255,0:0,255,0:0,255,255:0,0,255:255,0,255>RGB Rainbow!</gradient>"
);
```

## Use Cases

### 1. Exact colors for branding
```java
// Use exact brand colors
Message brand = TaleMessage.parse(
    "<87,142,200>Corporate blue</87,142,200>"
);
```

### 2. Custom shades
```java
// Create specific shades
Message darkRed = TaleMessage.parse("<139,0,0>Dark red</139,0,0>");
Message lightBlue = TaleMessage.parse("<173,216,230>Light blue</173,216,230>");
```

### 3. Custom grays
```java
Message gray10 = TaleMessage.parse("<26,26,26>10% Gray</26,26,26>");
Message gray50 = TaleMessage.parse("<128,128,128>50% Gray</128,128,128>");
Message gray90 = TaleMessage.parse("<230,230,230>90% Gray</230,230,230>");
```

## Advantages of RGB Format

✅ **Precision**: Control exactly the color you want  
✅ **Flexibility**: 16.7 million possible colors  
✅ **Familiar**: Standard format in design and development  
✅ **Compatible**: Works with gradients and all formats  
✅ **Readable**: Easier to understand than hexadecimal for some  

## Format Comparison

| Format | Example | Advantage |
|--------|---------|-----------|
| Named | `<red>` | Quick and easy to remember |
| Hex | `<#FF5555>` | Compact, web standard |
| RGB | `<255,85,85>` | Intuitive, separated values |

## Important Notes

- RGB values must be between **0 and 255**
- You can use spaces: `<255, 85, 85>` or without: `<255,85,85>`
- The closing tag must match: `<255,0,0>text</255,0,0>`
- Works in gradients, nested tags, and with other formats

