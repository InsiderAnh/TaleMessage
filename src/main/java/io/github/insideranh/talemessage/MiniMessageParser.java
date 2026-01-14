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

}