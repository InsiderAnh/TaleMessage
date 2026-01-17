package io.github.insideranh.talemessage;

import java.util.Stack;

public class MiniMessageParser {

    private final String input;
    private int pos;

    public MiniMessageParser(String input) {
        this.input = input;
        this.pos = 0;
    }

    public static TagToken parse(String input) {
        if (input == null || input.isEmpty()) {
            return new TagToken(TagToken.Type.TEXT, "");
        }

        MiniMessageParser parser = new MiniMessageParser(input);
        return parser.parseRoot();
    }

    private TagToken parseRoot() {
        TagToken root = new TagToken(TagToken.Type.TEXT, "");
        Stack<TagToken> stack = new Stack<>();
        stack.push(root);

        StringBuilder textBuffer = new StringBuilder();

        while (pos < input.length()) {
            char c = input.charAt(pos);

            if (c == '\\' && pos + 1 < input.length() && (input.charAt(pos + 1) == '<' || input.charAt(pos + 1) == '>')) {
                textBuffer.append(input.charAt(pos + 1));
                pos += 2;
                continue;
            }
            // Handle Minecraft color codes (&a, &b, &l, etc.)
            if (c == '&' && pos + 1 < input.length()) {
                char code = input.charAt(pos + 1);
                TagToken mcToken = createMinecraftColorToken(code);
                if (mcToken != null) {
                    if (!textBuffer.isEmpty()) {
                        stack.peek().addChild(new TagToken(TagToken.Type.TEXT, textBuffer.toString()));
                        textBuffer.setLength(0);
                    }
                    stack.peek().addChild(mcToken);
                    if (mcToken.getType() != TagToken.Type.TEXT) {
                        stack.push(mcToken);
                    }
                    pos += 2;
                    continue;
                }
            }

            if (c == '<') {
                if (!textBuffer.isEmpty()) {
                    stack.peek().addChild(new TagToken(TagToken.Type.TEXT, textBuffer.toString()));
                    textBuffer.setLength(0);
                }

                String tag = parseTag();
                if (tag != null) {
                    if (tag.startsWith("/")) {
                        if (stack.size() > 1) {
                            stack.pop();
                        }
                    } else {
                        TagToken tagToken = createTagToken(tag);
                        if (tagToken != null) {
                            stack.peek().addChild(tagToken);
                            if (tagToken.getType() != TagToken.Type.TEXT) {
                                stack.push(tagToken);
                            }
                        }
                    }
                }
            } else {
                textBuffer.append(c);
                pos++;
            }
        }

        if (!textBuffer.isEmpty()) {
            stack.peek().addChild(new TagToken(TagToken.Type.TEXT, textBuffer.toString()));
        }

        return root;
    }

    private String parseTag() {
        if (pos >= input.length() || input.charAt(pos) != '<') {
            return null;
        }

        int start = pos + 1;

        do {
            pos++;
        } while (pos < input.length() && input.charAt(pos) != '>');

        if (pos >= input.length()) {
            return null;
        }

        String tag = input.substring(start, pos);
        pos++;

        return tag;
    }

    private TagToken createTagToken(String tag) {
        if (tag == null || tag.isEmpty()) {
            return null;
        }

        String lowerTag = tag.toLowerCase();

        if (lowerTag.startsWith("/")) {
            return new TagToken(TagToken.Type.RESET, lowerTag.substring(1));
        }

        if (lowerTag.startsWith("gradient:") || lowerTag.startsWith("gradient ")) {
            String[] parts = tag.substring(9).split(":");
            return new TagToken(TagToken.Type.GRADIENT, "", parts);
        }

        if (lowerTag.startsWith("click:")) {
            String url = tag.substring(6).trim();
            return new TagToken(TagToken.Type.CLICK, url);
        }

        switch (lowerTag) {
            case "bold", "b" -> {
                return new TagToken(TagToken.Type.BOLD, "");
            }
            case "italic", "i", "em" -> {
                return new TagToken(TagToken.Type.ITALIC, "");
            }
            case "underline", "u" -> {
                return new TagToken(TagToken.Type.UNDERLINE, "");
            }
            case "monospace", "mono" -> {
                return new TagToken(TagToken.Type.MONOSPACE, "");
            }
        }

        if (ColorRegistry.getColor(tag) != null) {
            return new TagToken(TagToken.Type.COLOR, tag);
        }

        return new TagToken(TagToken.Type.TEXT, "<" + tag + ">");
    }

    private TagToken createMinecraftColorToken(char code) {
        return switch (Character.toLowerCase(code)) {
            // Colors
            case '0' -> new TagToken(TagToken.Type.COLOR, "black");
            case '1' -> new TagToken(TagToken.Type.COLOR, "dark_blue");
            case '2' -> new TagToken(TagToken.Type.COLOR, "dark_green");
            case '3' -> new TagToken(TagToken.Type.COLOR, "dark_aqua");
            case '4' -> new TagToken(TagToken.Type.COLOR, "dark_red");
            case '5' -> new TagToken(TagToken.Type.COLOR, "dark_purple");
            case '6' -> new TagToken(TagToken.Type.COLOR, "gold");
            case '7' -> new TagToken(TagToken.Type.COLOR, "gray");
            case '8' -> new TagToken(TagToken.Type.COLOR, "dark_gray");
            case '9' -> new TagToken(TagToken.Type.COLOR, "blue");
            case 'a' -> new TagToken(TagToken.Type.COLOR, "green");
            case 'b' -> new TagToken(TagToken.Type.COLOR, "aqua");
            case 'c' -> new TagToken(TagToken.Type.COLOR, "red");
            case 'd' -> new TagToken(TagToken.Type.COLOR, "light_purple");
            case 'e' -> new TagToken(TagToken.Type.COLOR, "yellow");
            case 'f' -> new TagToken(TagToken.Type.COLOR, "white");
            // Formatting
            case 'k' -> new TagToken(TagToken.Type.TEXT, "");
            case 'l' -> new TagToken(TagToken.Type.BOLD, "");
            case 'm' -> new TagToken(TagToken.Type.TEXT, "");
            case 'n' -> new TagToken(TagToken.Type.UNDERLINE, "");
            case 'o' -> new TagToken(TagToken.Type.ITALIC, "");
            case 'r' -> new TagToken(TagToken.Type.RESET, "");
            default -> null;
        };
    }

}