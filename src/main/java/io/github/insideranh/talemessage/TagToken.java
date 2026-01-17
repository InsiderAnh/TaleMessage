package io.github.insideranh.talemessage;

import java.util.ArrayList;
import java.util.List;

public class TagToken {

    public enum Type {
        TEXT,           // Plain text
        COLOR,          // <red>, <#FF5555>
        BOLD,           // <bold>, <b>
        ITALIC,         // <italic>, <i>
        UNDERLINE,      // <underline>, <u>
        MONOSPACE,      // <monospace>, <mono>
        GRADIENT,       // <gradient:color1:color2:...>
        CLICK,          // <click:URL>
        RESET           // </...> closing tags
    }

    private final Type type;
    private final String content;
    private final String[] args;
    private final List<TagToken> children;

    public TagToken(Type type, String content, String... args) {
        this.type = type;
        this.content = content;
        this.args = args;
        this.children = new ArrayList<>();
    }

    public Type getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    public String[] getArgs() {
        return args;
    }

    public List<TagToken> getChildren() {
        return children;
    }

    public void addChild(TagToken child) {
        children.add(child);
    }

    public boolean isFormatting() {
        return type == Type.BOLD || type == Type.ITALIC ||
               type == Type.UNDERLINE || type == Type.MONOSPACE;
    }

    public boolean isColor() {
        return type == Type.COLOR || type == Type.GRADIENT;
    }

    @Override
    public String toString() {
        return "TagToken{" +
                "type=" + type +
                ", content='" + content + '\'' +
                ", children=" + children.size() +
                '}';
    }

}