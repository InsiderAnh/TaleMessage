package io.github.insideranh.talemessage.utils;

import io.github.insideranh.talemessage.placeholders.Placeholder;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class LanguageUtils {

    private static final Map<String, Pattern> PATTERN_CACHE = new ConcurrentHashMap<>();

    public static String replacePlaceholders(final String message, final Placeholder... placeholders) {
        if (message == null || message.isEmpty() || placeholders.length == 0) {
            return message;
        }

        final StringJoiner patternJoiner = new StringJoiner("|");
        final Map<String, String> valueMap = new HashMap<>(placeholders.length, 1.0f);

        Arrays.stream(placeholders).forEach(p -> {
            final String placeholder = p.getPlaceholder();
            valueMap.put(placeholder, p.getValue());
            patternJoiner.add("(" + Pattern.quote(placeholder) + ")");
        });

        final Pattern megaPattern = PATTERN_CACHE.computeIfAbsent(patternJoiner.toString(),
            Pattern::compile);

        final Matcher matcher = megaPattern.matcher(message);
        final StringBuffer result = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(result,
                Matcher.quoteReplacement(valueMap.get(matcher.group())));
        }
        matcher.appendTail(result);
        return result.toString();
    }

    public static List<String> replacePlaceholders(final List<String> messages, final Placeholder... placeholders) {
        if (messages == null || messages.isEmpty()) {
            return messages == null ? Collections.emptyList() : new ArrayList<>(messages);
        }

        final StringJoiner patternJoiner = new StringJoiner("|");
        final Map<String, String> valueMap = Arrays.stream(placeholders)
            .collect(Collectors.toMap(
                Placeholder::getPlaceholder,
                Placeholder::getValue,
                (a, b) -> b,
                () -> new HashMap<>(placeholders.length, 1.0f)
            ));

        Arrays.stream(placeholders)
            .map(Placeholder::getPlaceholder)
            .map(Pattern::quote)
            .map(quoted -> "(" + quoted + ")")
            .forEach(patternJoiner::add);

        final Pattern megaPattern = PATTERN_CACHE.computeIfAbsent(patternJoiner.toString(), Pattern::compile);

        return messages.stream()
            .parallel()
            .map(message -> {
                final Matcher matcher = megaPattern.matcher(message);
                final StringBuffer result = new StringBuffer();
                while (matcher.find()) {
                    matcher.appendReplacement(result,
                        Matcher.quoteReplacement(valueMap.get(matcher.group())));
                }
                matcher.appendTail(result);
                return result.toString();
            })
            .collect(Collectors.toList());
    }

}