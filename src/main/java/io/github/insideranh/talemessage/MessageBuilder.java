package io.github.insideranh.talemessage;

import com.hypixel.hytale.server.core.Message;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class MessageBuilder {

    public static Message build(TagToken token) {
        if (token == null) {
            return Message.empty();
        }

        return buildRecursive(token, new FormatState());
    }

    private static Message buildRecursive(TagToken token, FormatState state) {
        Message message = Message.empty();

        FormatState newState = state.copy();
        applyTokenFormatting(token, newState);

        if (token.getType() == TagToken.Type.TEXT) {
            if (token.getContent() != null && !token.getContent().isEmpty()) {
                message = Message.raw(token.getContent());
                applyFormatting(message, newState);
            }

            for (TagToken child : token.getChildren()) {
                message.insert(buildRecursive(child, newState));
            }
        } else if (token.getType() == TagToken.Type.GRADIENT) {
            message = buildGradient(token, newState);
        } else {
            for (TagToken child : token.getChildren()) {
                message.insert(buildRecursive(child, newState));
            }
        }

        return message;
    }

    private static void applyTokenFormatting(TagToken token, FormatState state) {
        switch (token.getType()) {
            case COLOR:
                state.color = ColorRegistry.getColor(token.getContent());
                break;
            case BOLD:
                state.bold = true;
                break;
            case ITALIC:
                state.italic = true;
                break;
            case UNDERLINE:
                state.underline = true;
                break;
            case MONOSPACE:
                state.monospace = true;
                break;
        }
    }

    private static void applyFormatting(Message message, FormatState state) {
        if (state.color != null) {
            message.color(state.color);
        }
        if (state.bold) {
            message.bold(true);
        }
        if (state.italic) {
            message.italic(true);
        }
        if (state.underline) {
            message.monospace(true);
        }
        if (state.monospace) {
            message.monospace(true);
        }
    }

    private static Message buildGradient(TagToken token, FormatState state) {
        String text = extractText(token);
        if (text.isEmpty()) {
            return Message.empty();
        }

        String[] colorArgs = token.getArgs();
        if (colorArgs.length < 2) {
            Message msg = Message.raw(text);
            applyFormatting(msg, state);
            return msg;
        }

        List<Color> colors = new ArrayList<>();
        for (String colorArg : colorArgs) {
            Color color = ColorRegistry.getColor(colorArg.trim());
            if (color != null) {
                colors.add(color);
            }
        }

        if (colors.size() < 2) {
            Message msg = Message.raw(text);
            applyFormatting(msg, state);
            return msg;
        }

        Message root = Message.empty();
        int textLength = text.length();

        for (int i = 0; i < textLength; i++) {
            char c = text.charAt(i);
            float progress = textLength > 1 ? (float) i / (textLength - 1) : 0;

            float segmentSize = 1.0f / (colors.size() - 1);
            int segmentIndex = Math.min((int) (progress / segmentSize), colors.size() - 2);
            float segmentProgress = (progress - segmentIndex * segmentSize) / segmentSize;

            Color interpolated = ColorRegistry.interpolate(
                colors.get(segmentIndex),
                colors.get(segmentIndex + 1),
                segmentProgress
            );

            Message charMsg = Message.raw(String.valueOf(c));
            charMsg.color(interpolated);

            if (state.bold) charMsg.bold(true);
            if (state.italic) charMsg.italic(true);
            if (state.monospace) charMsg.monospace(true);

            root.insert(charMsg);
        }

        return root;
    }

    private static String extractText(TagToken token) {
        StringBuilder sb = new StringBuilder();
        extractTextRecursive(token, sb);
        return sb.toString();
    }

    private static void extractTextRecursive(TagToken token, StringBuilder sb) {
        if (token.getType() == TagToken.Type.TEXT) {
            sb.append(token.getContent());
        }

        for (TagToken child : token.getChildren()) {
            extractTextRecursive(child, sb);
        }
    }

    private static class FormatState {
        Color color;
        boolean bold;
        boolean italic;
        boolean underline;
        boolean monospace;

        FormatState copy() {
            FormatState copy = new FormatState();
            copy.color = this.color;
            copy.bold = this.bold;
            copy.italic = this.italic;
            copy.underline = this.underline;
            copy.monospace = this.monospace;
            return copy;
        }
    }

}