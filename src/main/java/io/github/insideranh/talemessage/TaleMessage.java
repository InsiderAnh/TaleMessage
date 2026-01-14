package io.github.insideranh.talemessage;

import com.hypixel.hytale.server.core.Message;

/**
 * TaleMessage - A MiniMessage-style formatting API for Hytale
 *
 * <p>Allows easy creation of colored and formatted messages using simple tags:</p>
 * <ul>
 *   <li>Colors: {@code <red>text</red>}, {@code <#FF5555>text</#FF5555>}</li>
 *   <li>Formatting: {@code <bold>text</bold>}, {@code <italic>text</italic>}</li>
 *   <li>Gradients: {@code <gradient:red:blue>text</gradient>}</li>
 *   <li>Nested: {@code <red><bold>text</bold></red>}</li>
 * </ul>
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * Message msg = TaleMessage.parse("<red>Hello <bold>World</bold></red>");
 * Message gradient = TaleMessage.parse("<gradient:red:blue:green>Rainbow text!</gradient>");
 * }</pre>
 *
 * @author InsiderAnh
 * @version 1.0.0
 */
public class TaleMessage {

    /**
     * Parse a MiniMessage formatted string into a Hytale Message.
     *
     * <p>Supported tags:</p>
     * <ul>
     *   <li><b>Named colors:</b> red, blue, green, yellow, gold, aqua, etc.</li>
     *   <li><b>Hex colors:</b> #FF5555 or FF5555 (with or without #)</li>
     *   <li><b>RGB colors:</b> 255,85,85 or 255, 85, 85 (R,G,B format with values 0-255)</li>
     *   <li><b>Bold:</b> {@code <bold>} or {@code <b>}</li>
     *   <li><b>Italic:</b> {@code <italic>}, {@code <i>}, or {@code <em>}</li>
     *   <li><b>Underline:</b> {@code <underline>} or {@code <u>}</li>
     *   <li><b>Monospace:</b> {@code <monospace>} or {@code <mono>}</li>
     *   <li><b>Gradient:</b> {@code <gradient:color1:color2:...>text</gradient>}</li>
     * </ul>
     *
     * <p>Tags can be closed explicitly ({@code </red>}) or with generic closing ({@code </>}).</p>
     * <p>Use {@code \<} and {@code \>} to escape angle brackets.</p>
     *
     * <p><b>Examples:</b></p>
     * <pre>{@code
     * // Named colors
     * Message msg = TaleMessage.parse("<red>Error: <bold>File not found</bold></red>");
     *
     * // Hex colors
     * Message hex = TaleMessage.parse("<#FF5555>Custom hex color</#FF5555>");
     *
     * // RGB colors (0-255 for each component)
     * Message rgb = TaleMessage.parse("<255,85,85>Red text</255,85,85>");
     * Message white = TaleMessage.parse("<255,255,255>White text</255,255,255>");
     * Message black = TaleMessage.parse("<0,0,0>Black text</0,0,0>");
     * Message custom = TaleMessage.parse("<128,64,200>Purple-ish</128,64,200>");
     *
     * // Gradients work with any color format
     * Message gradient1 = TaleMessage.parse("<gradient:red:blue>Named colors</gradient>");
     * Message gradient2 = TaleMessage.parse("<gradient:#FF0000:#0000FF>Hex colors</gradient>");
     * Message gradient3 = TaleMessage.parse("<gradient:255,0,0:0,0,255>RGB colors</gradient>");
     * }</pre>
     *
     * @param input the MiniMessage formatted string
     * @return a Message object with all formatting applied
     */
    public static Message parse(String input) {
        if (input == null || input.isEmpty()) {
            return Message.empty();
        }

        try {
            TagToken root = MiniMessageParser.parse(input);
            return MessageBuilder.build(root);
        } catch (Exception e) {
            // Fallback to raw text if parsing fails
            return Message.raw(input);
        }
    }

    /**
     * Strip all MiniMessage tags from a string, leaving only plain text.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * String plain = TaleMessage.strip("<red>Hello <bold>World</bold></red>");
     * // Returns: "Hello World"
     * }</pre>
     *
     * @param input the MiniMessage formatted string
     * @return the plain text without any formatting tags
     */
    public static String strip(String input) {
        if (input == null || input.isEmpty()) {
            return "";
        }

        StringBuilder result = new StringBuilder();
        int pos = 0;

        while (pos < input.length()) {
            char c = input.charAt(pos);

            // Handle escape sequences
            if (c == '\\' && pos + 1 < input.length() && (input.charAt(pos + 1) == '<' || input.charAt(pos + 1) == '>')) {
                result.append(input.charAt(pos + 1));
                pos += 2;
                continue;
            }

            // Skip tags
            if (c == '<') {
                int closePos = input.indexOf('>', pos);
                if (closePos != -1) {
                    pos = closePos + 1;
                    continue;
                }
            }

            result.append(c);
            pos++;
        }

        return result.toString();
    }

    /**
     * Escape angle brackets in a string to prevent them from being parsed as tags.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * String escaped = TaleMessage.escape("<html>");
     * // Returns: "\<html\>"
     * }</pre>
     *
     * @param input the string to escape
     * @return the escaped string
     */
    public static String escape(String input) {
        if (input == null || input.isEmpty()) {
            return "";
        }

        return input.replace("<", "\\<").replace(">", "\\>");
    }

    /**
     * Create a simple colored message.
     *
     * <p><b>Examples:</b></p>
     * <pre>{@code
     * Message msg = TaleMessage.colored("red", "Error occurred!");
     * Message hex = TaleMessage.colored("#FF5555", "Custom color");
     * }</pre>
     *
     * @param color the color name or hex code
     * @param text the text content
     * @return a colored Message
     */
    public static Message colored(String color, String text) {
        return parse("<" + color + ">" + text + "</" + color + ">");
    }

    /**
     * Create a gradient message.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Message gradient = TaleMessage.gradient("Rainbow Text", "red", "yellow", "green", "blue");
     * }</pre>
     *
     * @param text the text content
     * @param colors the gradient colors (minimum 2)
     * @return a gradient Message
     */
    public static Message gradient(String text, String... colors) {
        if (colors == null || colors.length < 2) {
            return Message.raw(text);
        }

        String colorList = String.join(":", colors);
        return parse("<gradient:" + colorList + ">" + text + "</gradient>");
    }

}